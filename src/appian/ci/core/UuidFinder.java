package appian.ci.core;

import java.util.LinkedList;
import java.util.List;
import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;
import org.xml.sax.SAXException;

public class UuidFinder extends HandlerBase {
    
    private final UuidUtil uuidUtil;

    public UuidFinder() {
        uuids = new LinkedList<>();
        uuidUtil = new UuidUtil();
    }

    final LinkedList<String> uuids;
    String textNodeAccumulator;
    String currentNodeName;

    @Override
    public void startElement(String nodeName, AttributeList al) throws SAXException {

        currentNodeName = nodeName;
        textNodeAccumulator = "";

    }

    public void characters(char[] chars, int start, int length) throws SAXException {
        String contents = new String(chars, start, length);
        textNodeAccumulator += contents;
    }

    public void endElement(String nodeName) throws SAXException {

        boolean isUuidNode = nodeName.matches(".*(?i)(Uuid)");

        if (isUuidNode && uuidUtil.fromString(textNodeAccumulator) != null) {
            uuids.add(textNodeAccumulator);
            isUuidNode = false;
        }

        textNodeAccumulator = "";

    }

    public List<String> getUuids() {
        return uuids;
    }
}
