package com.purpleprint.logserver.service;

import com.purpleprint.logserver.model.LogModel;
import com.purpleprint.logserver.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
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

    public LogModel getLog(String key) {

        return logRepository.findById(key).orElse(null);
    }

    public List<LogModel> getLogs(int id) {

        return (List<LogModel>) logRepository.findAllByChildId(id).orElse(null);
    }

    public List<LogModel> addLogs(List<LogModel> list) {

        return (List<LogModel>) logRepository.saveAll(list);
    }

    @Scheduled(cron = "0 1 22 * * *")
    public void deleteLog() {

        logRepository.deleteAll();
    }

    public Page<LogModel> getAllLogs() {

        return (Page<LogModel>) logRepository.findAll();
    }

//    public void sendLogs(Map<String, Object> resultMap) {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        String serverURL = "http://34.64.214.200:8000/";
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<Map<String, Object>> response = restTemplate.postForEntity(serverURL, resultMap, );
//    }
}
