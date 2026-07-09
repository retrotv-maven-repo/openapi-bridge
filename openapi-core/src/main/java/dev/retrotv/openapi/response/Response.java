package dev.retrotv.openapi.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.retrotv.openapi.enums.ContentType;
import dev.retrotv.openapi.exception.ParseFailException;
import lombok.Getter;
import lombok.NonNull;

import java.util.Map;

@Getter
public abstract class Response {
    protected final ObjectMapper mapper;
    protected final ContentType contentType;
    protected String body;
    protected int statusCode;

    Response(ObjectMapper mapper, ContentType contentType, String body) {
        this.mapper = mapper;
        this.contentType = contentType;
        this.body = body;
    }

    @NonNull
    public Map<String, Object> getMap() {
        try {
            return this.mapper.readValue(this.body, new TypeReference<Map<String, Object>>(){});
        } catch (JsonProcessingException e) {
            throw new ParseFailException("응답을 파싱하는 도중 오류가 발생했습니다.", e);
        }
    }

    @NonNull
    public JsonNode getJsonNode() {
        try {
            return this.mapper.readTree(this.body);
        } catch (JsonProcessingException e) {
            throw new ParseFailException("응답을 파싱하는 도중 오류가 발생했습니다.", e);
        }
    }
}
