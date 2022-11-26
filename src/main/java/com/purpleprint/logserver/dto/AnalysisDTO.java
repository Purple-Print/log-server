package com.purpleprint.logserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <pre>
 * Class : AnalysisDTO
 * Comment: 클래스에 대한 간단 설명
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-25       이상학           최초 생성
 * </pre>
 *
 * @author 이상학(최초 작성자)
 * @version 1(클래스 버전)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisDTO {

    private int id;
    private String finalFeedback;
    private int friend1;
    private String friend1Comment;
    private int friend2;
    private String friend2Comment;
    private int friend3;
    private String friend3Comment;
    private int friend4;
    private String friend4Comment;
    private String playPlace;
    private int childId;
    private String sendYn;
    private Date analysisAt;

}
