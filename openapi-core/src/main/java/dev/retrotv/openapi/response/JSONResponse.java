package dev.retrotv.openapi.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.retrotv.openapi.enums.ContentType;
import lombok.NonNull;

public class JSONResponse extends Response {
    public JSONResponse(@NonNull String body) {
        super(new ObjectMapper(), ContentType.JSON, body);
    }
}
