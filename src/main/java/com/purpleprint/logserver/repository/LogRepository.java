package com.purpleprint.logserver.repository;

import com.purpleprint.logserver.model.LogModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <pre>
 * Class : LogRepository
 * Comment: 클래스에 대한 간단 설명
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-24       이상학           최초 생성
 * </pre>
 *
 * @author 이상학(최초 작성자)
 * @version 1(클래스 버전)
 */
@Repository
public interface LogRepository extends ElasticsearchRepository<LogModel, String> {

    Optional<Object> findAllByChildId(int id);
}
