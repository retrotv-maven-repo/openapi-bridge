package dev.retrotv.common;

import dev.retrotv.openapi.common.DotEnv;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DotEnvTest {

    @Test
    void test() {
        DotEnv dotEnv = new DotEnv();
        String serviceKey = dotEnv.get("SERVICE_KEY");
        assertNotNull(serviceKey, "SERVICE_KEY 환경 변수가 설정되어 있지 않습니다.");
    }
}
