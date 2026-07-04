package dev.retrotv.openapi.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DotEnv {
    private Path envPath = Paths.get(".env");

    public DotEnv() {
        this.envPath = Paths.get("../.env");
    }

    public String get(String key) {
        return parseEnvFileValue(key);
    }

    private String parseEnvFileValue(String key) {
        if (!Files.exists(envPath)) {
            return null;
        }

        try {
            List<String> lines = Files.readAllLines(envPath);
            for (String rawLine : lines) {
                String line = rawLine.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("=", 2);
                if (parts.length != 2) {
                    continue;
                }

                String parsedKey = parts[0].trim();
                if (!parsedKey.equals(key)) {
                    continue;
                }

                String value = parts[1].trim();

                // 양쪽 따옴표 제거: SERVICE_KEY="abc" 형태 지원
                if ((value.startsWith("\"") && value.endsWith("\"")) ||
                    (value.startsWith("'") && value.endsWith("'"))) {
                    value = value.substring(1, value.length() - 1);
                }

                return value;
            }
        } catch (IOException ignored) {
            // 기존 로직대로 null을 반환하게 두고, 아래 검증에서 예외 처리
        }

        return null;
    }
}
