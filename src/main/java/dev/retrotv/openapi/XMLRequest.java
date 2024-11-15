package dev.retrotv.openapi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class XMLRequest implements Request {
    private final OpenAPI api;

    public XMLRequest(OpenAPI api) {
        this.api = api;
    }

    @Override
    public HttpURLConnection getHttpURLConnection() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) buildURL().openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/xml");
        connection.setDoOutput(true);
        connection.setDoInput(true);

        return connection;
    }

    private URL buildURL() throws MalformedURLException {
        StringBuilder sb = new StringBuilder(api.getUrl());
        for (Query query : api.getQueries()) {
            sb.append(query.getQuery()).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);

        return new URL(sb.toString());
    }
}
