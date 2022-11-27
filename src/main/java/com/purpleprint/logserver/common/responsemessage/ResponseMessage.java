package com.purpleprint.logserver.common.responsemessage;

import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * <pre>
 * Class : ResponseMessage
 * Comment: 클래스에 대한 간단 설명
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-26       이상학           최초 생성
 * </pre>
 *
 * @author 이상학(최초 작성자)
 * @version 1(클래스 버전)
 */
public class ResponseMessage {
    private int httpStatus;
    private String message;
    private Map<String, Object> results;

    public ResponseMessage() {};

    public ResponseMessage(HttpStatus httpStatus, String message, Map<String, Object> results) {
        this.httpStatus = httpStatus.value();
        this.message = message;
        this.results = results;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getResults() {
        return results;
    }

    public void setResults(Map<String, Object> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "httpStatus=" + httpStatus +
                ", message='" + message + '\'' +
                ", results=" + results +
                '}';
    }
}
