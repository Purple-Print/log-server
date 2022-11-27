package com.purpleprint.logserver.exception;

/**
 * <pre>
 * Class : FileEmptyException
 * Comment: 클래스에 대한 간단 설명
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-27       이상학           최초 생성
 * </pre>
 *
 * @author 이상학(최초 작성자)
 * @version 1(클래스 버전)
 */
public class FileEmptyException extends RuntimeException {
    public FileEmptyException(String message) { super(message); }
}
