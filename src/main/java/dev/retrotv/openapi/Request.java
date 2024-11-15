package dev.retrotv.openapi;

import java.io.IOException;
import java.net.HttpURLConnection;

public interface Request {
    HttpURLConnection getHttpURLConnection() throws IOException;
}
