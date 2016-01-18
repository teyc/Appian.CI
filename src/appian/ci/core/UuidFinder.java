package appian.ci.core;

import java.util.LinkedList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class UuidFinder extends DefaultHandler {
    
    private final UuidUtil uuidUtil;
    private final String uuidMatch = ".*(?i)(Uuid)";
    private final String fileName;
    
    public UuidFinder(String fileName) {
        uuids = new LinkedList<>();
        uuidUtil = new UuidUtil();
        this.fileName = fileName;
    }

    final LinkedList<String> uuids;
    String textNodeAccumulator;
    String currentNodeName;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentNodeName = qName;
        textNodeAccumulator = "";
     
        // <node uuid="..."> in processModels should not be reported 
        // as a precedent because it is already contained in the very
        // file itself.
        if (isProcessModelFile(fileName) && "node".equals(qName))
        {
            return;
        }
        
        for (int i = 0; i < attributes.getLength(); i++) {
            String attributeQName = attributes.getQName(i);
            String attributeValue = attributes.getValue(i);
            boolean isUuidAttribute = attributeQName.matches(uuidMatch);
            if (isUuidAttribute && uuidUtil.fromString(attributeValue) != null) {
                uuids.add(attributeValue);
            }
        }
    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException {
        String contents = new String(chars, start, length);
        textNodeAccumulator += contents;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
    
        boolean isUuidNode = qName.matches(uuidMatch);

        if (isUuidNode && uuidUtil.fromString(textNodeAccumulator) != null) {
            uuids.add(textNodeAccumulator);
        }

        textNodeAccumulator = "";

    }

    public List<String> getUuids() {
        return uuids;
    }

    private boolean isProcessModelFile(String fileName) {
        return fileName.contains("processModel\\") || fileName.contains("processModel/");
    }
}
