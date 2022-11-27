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
@Data   //lombok 사용시 class의 모든 필드에 대한 getter/setter/toString/equals 와 같은 함수 사용 가능
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만듦
@NoArgsConstructor  // 파라미터가 없는 기본 생성자를 생성
@Entity
@Table(name = "tbl_analysis")
public class Analysis {

    @Id
    @Column(name = "analysis_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "friend1")
    private int friend1;

    @Column(name = "friend1_comment")
    private String friend1Comment;

    @Column(name = "friend2")
    private int friend2;

    @Column(name = "friend2_comment")
    private String friend2Comment;

    @Column(name = "play_place")
    private String playPlace;

    @JoinColumn(name = "child_id")
    private int child;

    @Column(name = "send_yn")
    private String sendYn;

    @Column(name = "analysis_at")
    private Date analysisAt;

}