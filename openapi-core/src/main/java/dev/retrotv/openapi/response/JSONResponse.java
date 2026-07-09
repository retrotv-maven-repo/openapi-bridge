package dev.retrotv.openapi.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.retrotv.openapi.enums.ContentType;

public class JSONResponse extends Response {
    public JSONResponse(String body) {
        super(new ObjectMapper(), ContentType.JSON, body);
    }
}
