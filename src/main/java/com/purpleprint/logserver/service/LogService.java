package com.purpleprint.logserver.service;

import com.purpleprint.logserver.dto.AnalysisDTO;
import com.purpleprint.logserver.model.LogModel;
import com.purpleprint.logserver.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * <pre>
 * Class : LogService
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
@Service
public class LogService {

    private final LogRepository logRepository;

    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public LogModel addLogs(LogModel log) {


        return logRepository.save(log);
    }

    public void deleteLog() {

        logRepository.deleteAll();
    }

    public Page<LogModel> getAllLogs() {

        return (Page<LogModel>) logRepository.findAll();
    }

    public ResponseEntity<AnalysisDTO> sendLogs(Map<String, Object> resultMap) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        HttpEntity<?> requestEntity = new HttpEntity<>(resultMap, headers);

        String serverURL = "http://34.64.214.200:8000/";

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<AnalysisDTO> response = restTemplate.getForEntity(serverURL, AnalysisDTO.class, requestEntity);


        System.out.println("response : " + response);

        return response;
    }
}
