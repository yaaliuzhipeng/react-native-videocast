package com.lzp.videocast.upnp.xmlparser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlBuilder {

    public static Document buildRequestActionEntity(String actionName, String serviceType, Map<String,String> arguments) throws Exception{
         String namespace = "soap";
         Document document = newDocument(true);

         Element  nsEnvelope = document.createElement(xns(namespace,XmlCons.Envelope));
         nsEnvelope.setAttribute(xns(namespace,XmlCons.encodingStyle), XmlCons.EncodingLink);
         nsEnvelope.setAttribute("xmlns:"+namespace,XmlCons.EnvelopeXmlnsLink);

         Element nsBody = document.createElement(xns(namespace,XmlCons.Body));

         Element nsAction = buildActionElement(document,actionName,serviceType,arguments );

         nsBody.appendChild(nsAction);
         nsEnvelope.appendChild(nsBody);
         document.appendChild(nsEnvelope);

         document.setXmlVersion("1.0");
         return document;
    }
    public static String convertToString(Document document) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT,"YES");
        transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        ByteArrayOutputStream bot = new ByteArrayOutputStream();
        transformer.transform(new DOMSource(document) , new StreamResult(bot));
        return bot.toString();
    }
    /**
       构建 action Element ;
       示例:
            <nu:SetAVTransportURI xmlns:nu="urn:schemas-upnp-org:service:AVTransport:1">
                <InstanceID>0</InstanceID>
                <CurrentURI>http://cos-lianailing.hashfun.cn/movies/2/8.mp4</CurrentURI>
                <CurrentURIMetaData>TestVideo</CurrentURIMetaData>
            </nu:SetAVTransportURI>
     */
    private static Element buildActionElement(Document document, String actionName, String serviceType, Map<String,String> arguments) throws Exception {
        String namespace = "st";
        // 构建action node
        // 示例: <nu:SetAVTransportURI xmlns:nu="#serviceType"> , serviceType => urn:schemas-upnp-org:service:AVTransport:1
        Element actionElement = document.createElement(xns(namespace,actionName));
        actionElement.setAttribute("xmlns:"+namespace,serviceType);
        for( Map.Entry<String,String> entry : arguments.entrySet()){
            //例如: <CurrentURI>http://.../x.mp4</CurrentURI>
            Element arg = document.createElement(entry.getKey());
            arg.setTextContent(entry.getValue());
            actionElement.appendChild(arg);
        }
        //构建参数
        return actionElement;
    }

    private static Document newDocument(boolean standalone) throws Exception{
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        document.setXmlStandalone(standalone);
        return document;
    }
    //构造具有命名空间前缀的node名, 示例:   ns:Body , nu:Play
    private static String xns(String namespace,String nodename) {
        return namespace+":"+nodename;
    }
}
