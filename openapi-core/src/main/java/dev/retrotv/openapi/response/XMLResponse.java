package dev.retrotv.openapi.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import dev.retrotv.openapi.enums.ContentType;
import dev.retrotv.openapi.exception.ParseFailException;

import java.util.Map;

public class XMLResponse extends Response {
    XmlMapper mapper = new XmlMapper();

    XMLResponse() {
        super(ContentType.XML);
    }

    public Map<String, Object> getMap() {
        try {
            return mapper.readValue(this.body, new TypeReference<Map<String, Object>>(){});
        } catch (JsonProcessingException e) {
            throw new ParseFailException("응답을 파싱하는 도중 오류가 발생했습니다.", e);
        }
    }

    public JsonNode getJsonNode() {
        try {
            return mapper.readTree(this.body);
        } catch (Exception e) {
            throw new ParseFailException("응답을 파싱하는 도중 오류가 발생했습니다.", e);
        }
    }
}
