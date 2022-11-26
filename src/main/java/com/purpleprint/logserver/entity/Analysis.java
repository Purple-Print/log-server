package com.purpleprint.logserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * <pre>
 * Class : Analysis
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
@Entity
@Table(name = "tbl_analysis")
public class Analysis {

    @Id
    @Column(name = "analysis_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "final_feedback")
    private String finalFeedback;

    @Column(name = "friend1")
    private int friend1;

    @Column(name = "friend1_comment")
    private String friend1Comment;

    @Column(name = "friend2")
    private int friend2;

    @Column(name = "friend2_comment")
    private String friend2Comment;

    @Column(name = "friend3")
    private int friend3;

    @Column(name = "friend3_comment")
    private String friend3Comment;

    @Column(name = "friend4")
    private int friend4;

    @Column(name = "friend4_comment")
    private String friend4Comment;

    @Column(name = "concurrent_child")
    private int concurrentChild;

    @Column(name = "play_place")
    private String playPlace;

    @JoinColumn(name = "child_id")
    private int childId;

    @Column(name = "send_yn")
    private String sendYn;

    @Column(name = "analysis_at")
    private Date analysisAt;

}