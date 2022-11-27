package com.purpleprint.logserver.repository;

import com.purpleprint.logserver.entity.Logfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 * Class : LogfileRepository
 * Comment: 클래스에 대한 간단 설명
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-28       이상학           최초 생성
 * </pre>
 *
 * @author 이상학(최초 작성자)
 * @version 1(클래스 버전)
 */
@Repository
public interface LogfileRepository extends JpaRepository <Logfile, Integer> {
}
