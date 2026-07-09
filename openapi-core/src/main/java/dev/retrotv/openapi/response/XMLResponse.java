package dev.retrotv.openapi.response;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import dev.retrotv.openapi.enums.ContentType;

public class XMLResponse extends Response {
    public XMLResponse(String body) {
        super(new XmlMapper(), ContentType.XML, body);
    }
}
