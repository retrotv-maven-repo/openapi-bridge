package dev.retrotv.openapi.response;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import dev.retrotv.openapi.enums.ContentType;
import lombok.NonNull;

public class XMLResponse extends Response {
    public XMLResponse(@NonNull String body) {
        super(new XmlMapper(), ContentType.XML, body);
    }
}
