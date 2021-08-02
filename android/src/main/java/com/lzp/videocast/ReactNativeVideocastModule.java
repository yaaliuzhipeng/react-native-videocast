// ReactNativeVideocastModule.java

package com.lzp.videocast;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.AndroidRuntimeException;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.lzp.videocast.upnp.UpnpConstants;
import com.lzp.videocast.upnp.network.ActionConnection;
import com.lzp.videocast.upnp.network.Downloader;
import com.lzp.videocast.upnp.ssdp.SSDPCenter;
import com.lzp.videocast.upnp.ssdp.SSDPDataFactory;
import com.lzp.videocast.upnp.ssdp.SSDPDeviceResponse;
import com.lzp.videocast.upnp.ssdp.SSDPScanListener;
import com.lzp.videocast.upnp.xmlparser.XmlAction;
import com.lzp.videocast.upnp.xmlparser.XmlBuilder;
import com.lzp.videocast.upnp.xmlparser.XmlDeviceDescriptionDocument;
import com.lzp.videocast.upnp.xmlparser.XmlDeviceService;
import com.lzp.videocast.upnp.xmlparser.XmlParser;
import com.lzp.videocast.upnp.xmlparser.XmlScpdDocument;

import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class ReactNativeVideocastModule extends ReactContextBaseJavaModule {

    private static final String TAG = "VideoCastModule";
    private static ReactApplicationContext reactContext;
    private AsyncScanTask scanTask;

    public ReactNativeVideocastModule(ReactApplicationContext reactContext) {
        super(reactContext);
        ReactNativeVideocastModule.reactContext = reactContext;
    }

    private static ArrayList<SSDPDeviceResponse> deviceResponses;
    private static String choosedUUID = "";
    private static int instanceID = -1;
    private static XmlScpdDocument scpdDocument;
    private static String serviceType = "";
    private static String controlConnectionURL = "";

    @Override
    public String getName() {
        return "ReactNativeVideocast";
    }

    @ReactMethod
    public void sampleMethod(String stringArgument, int numberArgument, Callback callback) {
    }
    @ReactMethod
    public void scan( int timeout, Callback onDevicesChanged, Callback onError) {
        deviceResponses = new ArrayList<>();
        if (scanTask == null) {
            scanTask = new AsyncScanTask(timeout, onDevicesChanged, onError);
            scanTask.execute();
        } else {
            scanTask.cancel(true);
            scanTask = null;
            scanTask = new AsyncScanTask(timeout, onDevicesChanged, onError);
            scanTask.execute();
        }
    }

    @ReactMethod
    public void stopScan(){
        SSDPCenter.getInstance().stopScan();
        scanTask.cancel(true);
        scanTask = null;
    }

    @ReactMethod
    public void setCurrentURI(String uuid, String uri, Callback onSuccess, Callback onError) {
        Log.i("TAG", "执行 setCurrentURI");
        AsyncSetCurrentURITask task = new AsyncSetCurrentURITask(uuid,uri,onSuccess,onError);
        task.execute();
    }

    // play ,pause , next ,previous
    private void runSimpleAction(String actionName,HashMap<String,String> arugments,String uuid,Callback onSuccess, Callback onError) {
        if(instanceID < 0 || !choosedUUID.equals(uuid)) {
            WritableMap errMap = Arguments.createMap();
            Log.e("TAG", "runSimpleAction: instanceID < 0 或者 uuid不同" );
            errMap.putString("message", "当前会话已过期或无播放中资源、请重新选择视频播放");
            onError.invoke(errMap);
            return;
        }
        try {
            ArrayList<XmlAction> actions = scpdDocument.getActionList();
            //找到名为 @Param [ actionName ] 的 action
            XmlAction action = null;
            for (XmlAction act : actions) {
                if (act.getName().equalsIgnoreCase(actionName)) {
                    action = act;
                }
            }
            if (action == null) {
                WritableMap errMap = Arguments.createMap();
                errMap.putString("message", "该设备暂不支持Action : "+actionName);
                onError.invoke(errMap);
                return;
            }
            String soapMessage = XmlBuilder.convertToString(XmlBuilder.buildRequestActionEntity(
                    action.getName(),
                    serviceType,
                    arugments));
            HttpURLConnection httpURLConnection = new ActionConnection()
                    .setUrl(controlConnectionURL)
                    .setRequestMethod(ActionConnection.HTTP_POST)
                    .setSoapAction(serviceType, action.getName())
                    .build();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(soapMessage.getBytes());
            outputStream.flush();
            outputStream.close();
            Log.i(TAG, "runSimpleAction: SOAP Message: "+soapMessage);
            int code = httpURLConnection.getResponseCode();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while ( (line = reader.readLine()) != null) {
                builder.append(line);
            }
            if (code == HttpURLConnection.HTTP_OK) {
                Log.i("TAG", "action: "+action.getName()+" 响应成功 => \n"+builder.toString());
                onSuccess.invoke("响应成功");
            }else{
                Log.i("TAG", "action 响应失败 => \n"+builder.toString());
                //TODO: add error parser
                WritableMap errMap = Arguments.createMap();
                errMap.putString("message", "请求失败");
                onError.invoke(errMap);
            }
            reader.close();
            inputStream.close();
            httpURLConnection.disconnect();
        } catch (Exception e) {
            Log.e("TAG", "runSimpleAction: "+e.toString() );
            WritableMap errMap = Arguments.createMap();
            errMap.putString("message", e.toString());
            onError.invoke(errMap);
        }
    }

    @ReactMethod
    public void play(String uuid,Callback onSuccess,Callback onError){
        HashMap<String, String> arguments = new HashMap<>();
        arguments.put("InstanceID", String.valueOf(instanceID));
        arguments.put("Speed", "1");
        AsyncAction task = new AsyncAction(arguments,uuid,UpnpConstants.AcPlay,onSuccess,onError);
        task.execute();
    }
    @ReactMethod
    public void pause(String uuid,Callback onSuccess,Callback onError){
        HashMap<String, String> arguments = new HashMap<>();
        arguments.put("InstanceID", String.valueOf(instanceID));
        AsyncAction task = new AsyncAction(arguments,uuid,UpnpConstants.AcPause,onSuccess,onError);
        task.execute();
    }
    @ReactMethod
    public void next(String uuid,Callback onSuccess,Callback onError){
        HashMap<String, String> arguments = new HashMap<>();
        arguments.put("InstanceID", String.valueOf(instanceID));
        AsyncAction task = new AsyncAction(arguments,uuid,UpnpConstants.AcNext,onSuccess,onError);
        task.execute();
    }
    @ReactMethod
    public void previous(String uuid,Callback onSuccess,Callback onError){
        HashMap<String, String> arguments = new HashMap<>();
        arguments.put("InstanceID", String.valueOf(instanceID));
        AsyncAction task = new AsyncAction(arguments,uuid,UpnpConstants.AcPrevious,onSuccess,onError);
        task.execute();
    }

    //异步扫描任务
    private static class AsyncScanTask extends AsyncTask<Integer, ArrayList<SSDPDeviceResponse>, String> {
        private final int timeout;
        private final Callback onDevicesChanged;
        private final Callback onError;

        public AsyncScanTask(int timeout, Callback onDevicesChanged, Callback onError) {
            this.timeout = timeout;
            this.onDevicesChanged = onDevicesChanged;
            this.onError = onError;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... integers) {
            Log.i("TAG", "doInBackground: 开始执行异步扫描线程");
            SSDPCenter
                    .getInstance()
                    .initialize(reactContext, timeout)
                    .scan(UpnpConstants.TypeServerKind.UpnpAVTransport1, new SSDPScanListener() {
                        @Override
                        public void onNewMessage(String message, String host, int port) {
                            SSDPDataFactory.FilteredMulticastsocketResponse response = SSDPDataFactory.filterMulticastsocketReponse(message, UpnpConstants.TypeServerKind.UpnpAVTransport1);
                            if (response.isMatched()) {
                                boolean existed = false;
                                for (SSDPDeviceResponse dr : deviceResponses) {
                                    existed = dr.getLocation().equals(response.getLocation());
                                    if (existed) {
                                        break;
                                    }
                                }
                                if (!existed) {
                                    SSDPDeviceResponse ssdpDeviceResponse = new SSDPDeviceResponse(); //创建新SSDPDeviceResponse对象
                                    ssdpDeviceResponse.setUuid(UUID.randomUUID().toString());
                                    ssdpDeviceResponse.setLocation(response.getLocation());
                                    // download and parse document
                                    try {
                                        String xmlContent = Downloader.downloadXml(response.getLocation());
                                        XmlDeviceDescriptionDocument document = XmlParser.parseDeviceDescriptionDocument(xmlContent);
                                        ssdpDeviceResponse.setDescriptionDocument(document);
                                        deviceResponses.add(ssdpDeviceResponse);

                                        ArrayList<XmlDeviceService> services = document.getDevice().getServiceList();
                                        for (int i = 0; i < services.size(); i++) {
                                            XmlDeviceService serv = services.get(i);
                                            String servType = serv.getServiceType();
                                            if (servType.equalsIgnoreCase(UpnpConstants.TypeServerKind.UpnpAVTransport1)) {
                                                Log.i("TAG", "onNewMessage: \n" + message + "\n\n");
                                                Log.i("TAG", "当前设备名 => " + document.getDevice().getFriendlyName() + "  地址: " + host + ":" + port + "  SCPD URL => " + serv.getSCPDURL());
                                                publishProgress(deviceResponses);
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        WritableMap errMap = Arguments.createMap();
                                        errMap.putString("message","设备描述文档下载失败");
                                        onError.invoke();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onScanTimeout() {
                        }

                        @Override
                        public void onError(Error error) {

                        }
                    });
            return null;
        }

        @SafeVarargs
        @Override
        protected final void onProgressUpdate(ArrayList<SSDPDeviceResponse>... values) {
            super.onProgressUpdate(values);
            // 转换 deviceResponses 、抛出至js;  @Notice!! 这里只给出 设备名 以及 UUID
            WritableArray devices = Arguments.createArray();
            for (SSDPDeviceResponse dr : deviceResponses) {
                String uuid = dr.getUuid();
                String name = dr.getDescriptionDocument().getDevice().getFriendlyName();
                WritableMap map = Arguments.createMap();
                map.putString("uuid", uuid);
                map.putString("name", name);
                devices.pushMap(map);
            }
            if (onDevicesChanged != null) {
                onDevicesChanged.invoke(devices);
            }
        }
    }
    //异步SetCurrentURI任务
    private static class AsyncSetCurrentURITask extends AsyncTask<Integer, Void, String> {

        private final String uuid;
        private final String uri;
        private final Callback onSuccess;
        private final Callback onError;

        public AsyncSetCurrentURITask(String uuid,String uri, Callback onSuccess, Callback onError){
            this.uuid = uuid;
            this.uri = uri;
            this.onSuccess = onSuccess;
            this.onError = onError;
        }

        @Override
        protected String doInBackground(Integer... integers) {
            SSDPDeviceResponse ssdpDeviceResponse = null;
            for (SSDPDeviceResponse dr : deviceResponses) {
                if (dr.getUuid().equals(this.uuid)) {
                    choosedUUID = this.uuid;
                    ssdpDeviceResponse = dr;
                }
            }
            if (ssdpDeviceResponse == null) {
                WritableMap errMap = Arguments.createMap();
                errMap.putString("message", "设备已过期、请重新扫描并选取设备");
                onError.invoke(errMap);
                return null;
            }
            String rootURl = "";
            try {
                URL location = new URL(ssdpDeviceResponse.getLocation());
                rootURl = location.getProtocol() + "://" + location.getHost() + ":" + location.getPort();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Log.i("TAG", "setCurrentURI: 根地址: " + rootURl + "   播放链接是: " + uri);
            XmlDeviceDescriptionDocument ddd = ssdpDeviceResponse.getDescriptionDocument();

            XmlDeviceService deviceService = null;
            for (XmlDeviceService service : ddd.getDevice().getServiceList()) {
                if (service.getServiceType().equalsIgnoreCase(UpnpConstants.TypeServerKind.UpnpAVTransport1)) {
                    //找到 UpnpAVTransport1 服务
                    deviceService = service;
                    break;
                }
            }
            if (deviceService == null) {
                WritableMap errMap = Arguments.createMap();
                errMap.putString("message", "该设备暂不支持投屏、请选择其他可用设备");
                onError.invoke(errMap);
                return null;
            }

            serviceType = deviceService.getServiceType();
            //下载并解析 scpd 文档
            String scpdURL = joinRootAndFileAddress(rootURl,deviceService.getSCPDURL());
            String scpdContent = "";
            try {
                Log.i(TAG, "setCurrentURI: 开始下载 SCPD 文档");
                scpdContent = Downloader.downloadXml(scpdURL);
                scpdDocument = XmlParser.parseScpdDocument(scpdContent);
                ArrayList<XmlAction> actions = scpdDocument.getActionList();

                Log.i(TAG, "setCurrentURI: SCPD文档下载完成");
                //找到名为 SetAVTransportURI 的 action
                XmlAction action = null;
                for (XmlAction act : actions) {
                    if (act.getName().equalsIgnoreCase(UpnpConstants.AcSetAVTransportURI)) {
                        action = act;
                    }
                }
                if (action == null) {
                    WritableMap errMap = Arguments.createMap();
                    errMap.putString("message", "该设备暂不支持投屏、请选择其他可用设备");
                    onError.invoke(errMap);
                    return null;
                }
                //构建SOAPMessage
                HashMap<String, String> arugments = new HashMap<>();
                Random random = new Random(50);
                instanceID = random.nextInt(50);
                arugments.put("InstanceID", String.valueOf(instanceID));
                arugments.put("CurrentURI", uri);
                arugments.put("CurrentURIMetaData", "");
                String soapMessage = XmlBuilder.convertToString(XmlBuilder.buildRequestActionEntity(
                        action.getName(),
                        deviceService.getServiceType(),
                        arugments));
                Log.i("TAG", "setCurrentURI: action body => \n" + soapMessage);
                //构造control url
                controlConnectionURL = joinRootAndFileAddress(rootURl, deviceService.getControlURL());

                HttpURLConnection httpURLConnection = new ActionConnection()
                        .setUrl(controlConnectionURL)
                        .setRequestMethod(ActionConnection.HTTP_POST)
                        .setSoapAction(deviceService.getServiceType(), action.getName())
                        .build();
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(soapMessage.getBytes());
                outputStream.flush();
                outputStream.close();

                int code = httpURLConnection.getResponseCode();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                StringBuilder builder = new StringBuilder();
                while ( (line = reader.readLine()) != null) {
                    builder.append(line);
                }
                reader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                if (code == HttpURLConnection.HTTP_OK) {
                    Log.i("TAG", "setCurrentURI 响应成功 => \n"+builder.toString());
                    onSuccess.invoke("响应成功");
                    return null;
                }else{
                    WritableMap errMap = Arguments.createMap();
                    errMap.putString("message", "响应失败");
                    onError.invoke(errMap);
                    return null;
                }
            } catch (Exception e) {
                WritableMap errMap = Arguments.createMap();
                errMap.putString("message", "投屏失败、请选择其他可用设备");
                onError.invoke(errMap);
            }
            return null;
        }
    }
    //异步play任务
    @SuppressLint("StaticFieldLeak")
    private class AsyncAction extends AsyncTask<Integer,Void,String> {
        private final Callback onSuccess;
        private final Callback onError;
        private final HashMap<String, String> arguments;
        private final String uuid;
        private final String action;

        public AsyncAction(HashMap<String, String> arguments,String uuid,String action,Callback onSuccess,Callback onError){
            this.arguments = arguments;
            this.onSuccess = onSuccess;
            this.onError = onError;
            this.uuid = uuid;
            this.action = action;
        }
        @Override
        protected String doInBackground(Integer... integers) {
            runSimpleAction(this.action,this.arguments, this.uuid,this.onSuccess,this.onError);
            return null;
        }
    }

    private static String joinRootAndFileAddress(String root,String file) {
        if(file.charAt(0) == '/'){
            return root + file;
        }else{
            return root + "/" + file;
        }
    }
}
