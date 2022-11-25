package com.purpleprint.logserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purpleprint.logserver.model.LogModel;
import com.purpleprint.logserver.service.LogService;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.json.JsonObject;
import java.io.IOException;
import java.util.*;

/**
 * <pre>
 * Class : LogController
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
@RestController
public class LogController {

    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/logs")
    public Map<?, ?> getAllLogs() {

        Page<LogModel> getLogResult = logService.getAllLogs();

        List<LogModel> logList = getLogResult.getContent();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("coorperate", logList);

        return resultMap;
    }

    @GetMapping("/log/{key}")
    public LogModel getLog(@PathVariable("key") String id) {

        return logService.getLog(id);
    }

    @GetMapping("/child/{id}")
    public List<LogModel> getLogs(@PathVariable("id") int id) {

        return logService.getLogs(id);
    }

    @PostMapping("/log")
    public List<LogModel> addLogs(@RequestBody List<LogModel> list) {

        return logService.addLogs(list);
    }

//    @DeleteMapping("/logs")
//    public String deleteLog() {
//
//        int result = logService.deleteLog();
//
//        if(result == 0) {
//            return "삭제 실패";
//        } else{
//            return "삭제 성공";
//        }
//    }
}
