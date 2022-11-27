package com.purpleprint.logserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <pre>
 * Class : RequestDTO
 * Comment: 클래스에 대한 간단 설명
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-27       전현정           최초 생성
 * </pre>
 *
 * @author 전현정(최초 작성자)
 * @version 1(클래스 버전)
 * @see
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO implements Serializable {

    private String x;
    private String z;
    private String id;
    private String time;

    @Override
    public String toString() {
        return "{" +
                "x='" + x + '\'' +
                ", z='" + z + '\'' +
                ", id='" + id + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
