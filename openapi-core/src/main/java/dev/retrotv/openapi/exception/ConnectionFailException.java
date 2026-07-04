package dev.retrotv.openapi.exception;

/**
 * API 연결에 실패했을 때 발생하는 예외입니다.
 * @see java.lang.RuntimeException
 */
public class ConnectionFailException extends RuntimeException {

    /**
     * 기본 생성자
     */
    public ConnectionFailException() {
        super();
    }

    /**
     * 예외 메시지 포함 생성자
     * @param message 예외 메시지
     */
    public ConnectionFailException(String message) {
        super(message);
    }

    /**
     * 예외 메시지 및 원인 포함 생성자
     * @param message 예외 메시지
     * @param cause 원인 예외
     */
    public ConnectionFailException(String message, Throwable cause) {
        super(message, cause);
    }
}
