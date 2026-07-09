package dev.retrotv.openapi.client;

import dev.retrotv.openapi.request.Request;
import dev.retrotv.openapi.response.Response;

public class SyncHttpClient extends HttpClient {
    public SyncHttpClient(Request request) {
        super(request);
    }

    public Response get() {
        return this.executeGet();
    }
}
