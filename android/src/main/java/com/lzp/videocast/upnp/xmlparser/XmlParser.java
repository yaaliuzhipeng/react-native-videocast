package com.lzp.videocast.upnp.xmlparser;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;

public class XmlParser {

    public static XmlDeviceDescriptionDocument parseDeviceDescriptionDocument(String xmlcontent) throws Exception {
        InputStream stream = new ByteArrayInputStream(xmlcontent.getBytes(StandardCharsets.UTF_8));
        Element rootElement = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream).getDocumentElement();

        XmlDeviceDescriptionDocument ddd = new XmlDeviceDescriptionDocument();

        //获取 specVersion 结点
        XmlSpecVersion specVersion = parseXmlSpecVersion(rootElement);
        ddd.setSpecVersion(specVersion);

        //获取 device 结点
        NodeList deviceNodes = rootElement.getElementsByTagName(XmlCons.device);
        for (int i = 0; i < deviceNodes.getLength(); i++){ //这里遍历的是所有名为 device 的结点
            XmlDevice device = new XmlDevice();

            Element deviceNode = (Element) deviceNodes.item(i);
            NodeList childNodes = deviceNode.getChildNodes();
            for(int n = 0; n < childNodes.getLength(); n++){ //这里开始遍历 device下面的子节点
                Node node = childNodes.item(n);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element cnode = (Element) node;
                    String cnodeName = cnode.getNodeName();
                    if(cnodeName.equalsIgnoreCase(XmlCons.deviceType)){
                        if(cnode.hasChildNodes()){
                            device.setDeviceType(cnode.getFirstChild().getNodeValue());
                        }else{
                            device.setDeviceType("");
                        }
                    }
                    if(cnodeName.equalsIgnoreCase(XmlCons.friendlyName)){
                        if (cnode.hasChildNodes()){
                            device.setFriendlyName(cnode.getFirstChild().getNodeValue());
                        }else{
                            device.setFriendlyName("");
                        }
                    }
                    if(cnodeName.equalsIgnoreCase(XmlCons.manufacturer)){
                        if (cnode.hasChildNodes()){
                            device.setManufacturer(cnode.getFirstChild().getNodeValue());
                        }else{
                            device.setManufacturer("");
                        }
                    }
                    if(cnodeName.equalsIgnoreCase(XmlCons.manufacturerURL)){
                        if(cnode.hasChildNodes()){
                            device.setManufacturerURL(cnode.getFirstChild().getNodeValue());
                        }else{
                            device.setManufacturerURL("");
                        }
                    }
                    if(cnodeName.equalsIgnoreCase(XmlCons.modelDescription)){
                        if(cnode.hasChildNodes()){
                            device.setModelDescription(cnode.getFirstChild().getNodeValue());
                        }else{
                            device.setModelDescription("");
                        }
                    }
                    if(cnodeName.equalsIgnoreCase(XmlCons.modelName)){
                        if(cnode.hasChildNodes()){
                            device.setModelName(cnode.getFirstChild().getNodeValue());
                        }else{
                            device.setModelName("");
                        }
                    }
                    if(cnodeName.equalsIgnoreCase(XmlCons.modelNumber)){
                        if(cnode.hasChildNodes()){
                            device.setModelNumber(cnode.getFirstChild().getNodeValue());
                        }else{
                            device.setModelNumber("");
                        }
                    }
                    if(cnodeName.equalsIgnoreCase(XmlCons.modelURL)){
                        if(cnode.hasChildNodes()){
                            device.setModelURL(cnode.getFirstChild().getNodeValue());
                        }else{
                            device.setModelURL("");
                        }
                    }
                    if(cnodeName.equalsIgnoreCase(XmlCons.serialNumber)){
                        if(cnode.hasChildNodes()){
                            device.setSerialNumber(cnode.getFirstChild().getNodeValue());
                        }else{
                            device.setSerialNumber("");
                        }
                    }
                    if(cnodeName.equalsIgnoreCase(XmlCons.UDN)){
                        if(cnode.hasChildNodes()){
                            device.setUDN(cnode.getFirstChild().getNodeValue());
                        }else{
                            device.setUDN("");
                        }
                    }
                    if(cnodeName.equalsIgnoreCase(XmlCons.serviceList)){
                        NodeList serviceNodes = cnode.getChildNodes();
                        ArrayList<XmlDeviceService> serviceList = new ArrayList<>();
                        for(int s = 0; s < serviceNodes.getLength(); s++){
                            if(serviceNodes.item(s).getNodeType() == Node.ELEMENT_NODE){
                                Element serviceNode = (Element) serviceNodes.item(s);
                                XmlDeviceService deviceService = new XmlDeviceService();
                                NodeList serviceChildNodes = serviceNode.getChildNodes();
                                for (int sc = 0; sc < serviceChildNodes.getLength(); sc++){
                                    Node scnode = serviceChildNodes.item(sc);
                                    if(scnode.getNodeType() == Node.ELEMENT_NODE){
                                        Element escnode = (Element) scnode;
                                        String escnodeName = escnode.getNodeName();

                                        if(escnodeName.equalsIgnoreCase(XmlCons.serviceType)){
                                            if(escnode.hasChildNodes()){
                                                deviceService.setServiceType(escnode.getFirstChild().getNodeValue());
                                            }else{
                                                deviceService.setServiceType("");
                                            }
                                        }
                                        if(escnodeName.equalsIgnoreCase(XmlCons.serviceId)){
                                            if(escnode.hasChildNodes()){
                                                deviceService.setServiceId(escnode.getFirstChild().getNodeValue());
                                            }else{
                                                deviceService.setServiceId("");
                                            }
                                        }
                                        if(escnodeName.equalsIgnoreCase(XmlCons.SCPDURL)){
                                            if(escnode.hasChildNodes()){
                                                deviceService.setSCPDURL(escnode.getFirstChild().getNodeValue());
                                            }else{
                                                deviceService.setSCPDURL("");
                                            }
                                        }
                                        if(escnodeName.equalsIgnoreCase(XmlCons.controlURL)){
                                            if(escnode.hasChildNodes()){
                                                deviceService.setControlURL(escnode.getFirstChild().getNodeValue());
                                            }else{
                                                deviceService.setControlURL("");
                                            }
                                        }
                                        if(escnodeName.equalsIgnoreCase(XmlCons.eventSubURL)){
                                            if(escnode.hasChildNodes()){
                                                deviceService.setEventSubURL(escnode.getFirstChild().getNodeValue());
                                            }else{
                                                deviceService.setEventSubURL("");
                                            }
                                        }
                                    }
                                }
                                serviceList.add(deviceService);
                            }
                        }
                        device.setServiceList(serviceList);
                    }
                }else{
                    // it's element_node type in most case
                }
            }

            ddd.setDevice(device);
            break;
        }
        return ddd;
    }

    public static XmlScpdDocument parseScpdDocument(String xmlcontent) throws Exception {
        InputStream stream = new ByteArrayInputStream(xmlcontent.getBytes(StandardCharsets.UTF_8));
        Element rootElement = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream).getDocumentElement();

        XmlScpdDocument scpdDocument = new XmlScpdDocument();

        //获取 specVersion 结点
        XmlSpecVersion specVersion = parseXmlSpecVersion(rootElement);
        scpdDocument.setSpecVersion(specVersion);

        //获取 actionList 结点
        ArrayList<XmlAction> xmlActionList = parseXmlActions(rootElement);
        scpdDocument.setActionList(xmlActionList);

        //获取 serviceStateTable 结点
        ArrayList<XmlStateVariable> serviceStateTable = parseXmlServiceStateTable(rootElement);
        scpdDocument.setServiceStateTable(serviceStateTable);

        return scpdDocument;
    }

    private static XmlSpecVersion parseXmlSpecVersion(Element rootElement){
        NodeList specVersionNodes = rootElement.getElementsByTagName(XmlCons.specVersion);
        XmlSpecVersion specVersion = new XmlSpecVersion();
        for(int i = 0; i < specVersionNodes.getLength(); i++){
            NodeList svChildNodes = specVersionNodes.item(i).getChildNodes(); //<major> , <minor>
            for(int n = 0; n < svChildNodes.getLength(); n++){
                Node node = svChildNodes.item(n);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element enode = (Element) node;
                    if(enode.getNodeName().equalsIgnoreCase(XmlCons.major)){
                        if(enode.hasChildNodes()){
                            specVersion.setMajor(enode.getFirstChild().getNodeValue());
                        }else{
                            specVersion.setMajor("");
                        }
                    }
                    if(enode.getNodeName().equalsIgnoreCase(XmlCons.minor)){
                        if(enode.hasChildNodes()){
                            specVersion.setMinor(enode.getFirstChild().getNodeValue());
                        }else{
                            specVersion.setMinor("");
                        }
                    }
                }
            }
            break;
        }
        return specVersion;
    }

    private static ArrayList<XmlAction> parseXmlActions(Element rootElement){
        ArrayList<XmlAction> xmlActionList = new ArrayList<>();
        NodeList actionListNodes = rootElement.getElementsByTagName(XmlCons.actionList);
        if(actionListNodes.getLength() > 0){
            Element actionList = (Element) actionListNodes.item(0);
            NodeList actionNodes = actionList.getChildNodes();
            for(int i = 0; i < actionNodes.getLength(); i++){
                Node actionNode = actionNodes.item(i);
                if(actionNode.getNodeType() == Node.ELEMENT_NODE) {
                    XmlAction xmlAction = new XmlAction(); // XmlAction
                    Element eaction = (Element) actionNode;
                    NodeList eactionChildNodes = eaction.getChildNodes();
                    for (int n = 0; n < eactionChildNodes.getLength(); n++) { //有 name , argumentList
                        Node nd = eactionChildNodes.item(n);
                        if (nd.getNodeType() == Node.ELEMENT_NODE){
                            Element elnd = (Element) nd;
                            if(nd.getNodeName().equalsIgnoreCase(XmlCons.name)){
                                if(elnd.hasChildNodes()){
                                    xmlAction.setName(elnd.getFirstChild().getNodeValue());
                                }else{
                                    xmlAction.setName("");
                                }
                            }
                            if(nd.getNodeName().equalsIgnoreCase(XmlCons.argumentList)){
                                NodeList argumentNodes = elnd.getChildNodes();
                                ArrayList<XmlActionArgument> xmlActionArgumentList = new ArrayList<>(); //XmlActionArgument List
                                for(int m = 0; m < argumentNodes.getLength(); m++) {
                                    Node argumentNode = argumentNodes.item(m);
                                    if(argumentNode.getNodeType() == Node.ELEMENT_NODE) {
                                        XmlActionArgument xmlActionArgument = new XmlActionArgument(); // XmlActionArgument
                                        Element elArgument = (Element) argumentNode;
                                        NodeList elArguChildNodes = elArgument.getChildNodes();
                                        for (int j = 0; j < elArguChildNodes.getLength(); j++) { //有 name , direction ,relatedStateVariable
                                            Node elcnode = elArguChildNodes.item(j);
                                            if(elcnode.getNodeType() == Node.ELEMENT_NODE) {
                                                Element el = (Element) elcnode;
                                                if(el.getNodeName().equalsIgnoreCase(XmlCons.name)){
                                                    if(el.hasChildNodes()){
                                                        xmlActionArgument.setName(el.getFirstChild().getNodeValue());
                                                    }else{
                                                        xmlActionArgument.setName("");
                                                    }
                                                }
                                                if(el.getNodeName().equalsIgnoreCase(XmlCons.direction)){
                                                    if(el.hasChildNodes()){
                                                        xmlActionArgument.setDirection(el.getFirstChild().getNodeValue());
                                                    }else{
                                                        xmlActionArgument.setDirection("");
                                                    }
                                                }
                                                if(el.getNodeName().equalsIgnoreCase(XmlCons.relatedStateVariable)){
                                                    if(el.hasChildNodes()){
                                                        xmlActionArgument.setRelatedStateVariable(el.getFirstChild().getNodeValue());
                                                    }else{
                                                        xmlActionArgument.setRelatedStateVariable("");
                                                    }
                                                }
                                            }
                                        }
                                        xmlActionArgumentList.add(xmlActionArgument); //添加 XmlActionArgument 到 XmlActionArgumentList 列表
                                    }
                                }
                                xmlAction.setArgumentList(xmlActionArgumentList);
                            }
                        }
                    }
                    xmlActionList.add(xmlAction);
                }
            }
        }
        return xmlActionList;
    }

    private static ArrayList<XmlStateVariable> parseXmlServiceStateTable(Element rootElement) {
        ArrayList<XmlStateVariable> serviceStateTable = new ArrayList<>();
        NodeList stateVariableNodes = rootElement.getElementsByTagName(XmlCons.stateVariable);
        for(int i = 0; i < stateVariableNodes.getLength(); i++) {
            Node stateVariable = stateVariableNodes.item(i);
            if(stateVariable.getNodeType() == Node.ELEMENT_NODE){
                Element elStateVariable = (Element) stateVariable;
                XmlStateVariable xmlStateVariable = new XmlStateVariable(); //XmlStateVariable

                //获取并设置 attributes , 例: sendEvents
                NamedNodeMap attrs = stateVariable.getAttributes();
                HashMap<String,String> attrmap = new HashMap<>();
                Node sendEventAttr = attrs.getNamedItem(XmlCons.sendEvents);
                if(sendEventAttr != null && sendEventAttr.hasChildNodes()) {
                    attrmap.put(XmlCons.sendEvents, sendEventAttr.getFirstChild().getNodeValue());
                }
                xmlStateVariable.setAttributes(attrmap);

                //获取子结点
                NodeList childnodes = elStateVariable.getChildNodes();
                for (int n = 0; n < childnodes.getLength();n++) { // 子结点有 name ,dataType ,allowedValueList , allowedValueRange , defaultValue
                    if(childnodes.item(n).getNodeType() == Node.ELEMENT_NODE){
                        Element elcnode = (Element) childnodes.item(n);
                        if(elcnode.getNodeName().equalsIgnoreCase(XmlCons.name)){
                            if(elcnode.hasChildNodes()){
                                xmlStateVariable.setName(elcnode.getFirstChild().getNodeValue());
                            }else{
                                xmlStateVariable.setName("");
                            }
                        }
                        if(elcnode.getNodeName().equalsIgnoreCase(XmlCons.dataType)){
                            if(elcnode.hasChildNodes()){
                                xmlStateVariable.setDataType(elcnode.getFirstChild().getNodeValue());
                            }else{
                                xmlStateVariable.setDataType("");
                            }
                        }
                        if(elcnode.getNodeName().equalsIgnoreCase(XmlCons.allowedValueList)){
                            ArrayList<XmlAllowedValue> xmlAllowedValueList = new ArrayList<>();
                            if(elcnode.hasChildNodes()){
                                NodeList allowedValueNodes = elcnode.getChildNodes();
                                for(int j = 0;j < allowedValueNodes.getLength(); j++){
                                    if(allowedValueNodes.item(j).getNodeType() == Node.ELEMENT_NODE){
                                        Element eAllowedValueNode = (Element) allowedValueNodes.item(j);
                                        if(eAllowedValueNode.hasChildNodes()){
                                            XmlAllowedValue xmlAllowedValue = new XmlAllowedValue(eAllowedValueNode.getFirstChild().getNodeValue());
                                            xmlAllowedValueList.add(xmlAllowedValue);
                                        }
                                    }
                                }
                            }
                            xmlStateVariable.setAllowedValueList(xmlAllowedValueList);
                        }
                        if(elcnode.getNodeName().equalsIgnoreCase(XmlCons.allowedValueRange)){
                            XmlAllowedValueRange xmlAllowedValueRange = new XmlAllowedValueRange();
                            String minimum = null;
                            String maximum = null;
                            String step = null;
                            if(elcnode.hasChildNodes()){
                                NodeList valueRangeNodes = elcnode.getChildNodes();
                                for(int k = 0; k < valueRangeNodes.getLength(); k++){
                                    if(valueRangeNodes.item(k).getNodeType() == Node.ELEMENT_NODE){
                                        Element eRangeChildNode = (Element) valueRangeNodes.item(k);
                                        if(eRangeChildNode.getNodeName().equalsIgnoreCase(XmlCons.minimum)){
                                            if(eRangeChildNode.hasChildNodes()){
                                                minimum = eRangeChildNode.getFirstChild().getNodeValue();
                                            }else{
                                                minimum = "";
                                            }
                                        }
                                        if(eRangeChildNode.getNodeName().equalsIgnoreCase(XmlCons.maximum)){
                                            if(eRangeChildNode.hasChildNodes()){
                                                maximum = eRangeChildNode.getFirstChild().getNodeValue();
                                            }else{
                                                maximum = "";
                                            }
                                        }
                                        if(eRangeChildNode.getNodeName().equalsIgnoreCase(XmlCons.step)){
                                            if(eRangeChildNode.hasChildNodes()){
                                                step = eRangeChildNode.getFirstChild().getNodeValue();
                                            }else{
                                                step = "";
                                            }
                                        }
                                    }
                                }
                                if(maximum != null) {
                                    xmlAllowedValueRange.setMaximum(maximum);
                                }
                                if(minimum != null) {
                                    xmlAllowedValueRange.setMinimum(minimum);
                                }
                                if(step != null) {
                                    xmlAllowedValueRange.setStep(step);
                                }
                            }
                            xmlStateVariable.setAllowedValueRange(xmlAllowedValueRange);
                        }
                        if(elcnode.getNodeName().equalsIgnoreCase(XmlCons.defaultValue)){
                            if(elcnode.hasChildNodes()){
                                xmlStateVariable.setDefaultValue(elcnode.getFirstChild().getNodeValue());
                            }else{
                                xmlStateVariable.setDefaultValue("");
                            }
                        }
                    }
                }
                serviceStateTable.add(xmlStateVariable);
            }
        }
        return serviceStateTable;
    }
}
