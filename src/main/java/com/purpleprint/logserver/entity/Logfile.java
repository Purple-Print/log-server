package com.purpleprint.logserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * <pre>
 * Class : Logfile
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
@Data   //lombok 사용시 class의 모든 필드에 대한 getter/setter/toString/equals 와 같은 함수 사용 가능
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만듦
@NoArgsConstructor  // 파라미터가 없는 기본 생성자를 생성
@DynamicInsert      // insert 시 null 인 필드 제외
@DynamicUpdate      // update 시 null인 필드 제외
@Entity
@Table(name = "tbl_logfile")
public class Logfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logfile_id")
    private int id;

    @Column(name = "logfile_url")
    private String url;

    @Column(name = "logfile_s3key")
    private String s3key;
}
