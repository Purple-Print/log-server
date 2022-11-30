package com.purpleprint.logserver.service;

import com.purpleprint.logserver.dto.AnalysisDTO;
import com.purpleprint.logserver.dto.FriendsDTO;
import com.purpleprint.logserver.entity.Analysis;
import com.purpleprint.logserver.model.LogModel;
import com.purpleprint.logserver.repository.AnalysisRepository;
import com.purpleprint.logserver.repository.AwsS3UploadRepository;
import com.purpleprint.logserver.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Date;
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
    private final AwsS3UploadService awsS3UploadService;
    private final AnalysisRepository analysisRepository;

    @Autowired
    public LogService(LogRepository logRepository, AwsS3UploadService awsS3UploadService, AnalysisRepository analysisRepository) {
        this.logRepository = logRepository;
        this.awsS3UploadService = awsS3UploadService;
        this.analysisRepository = analysisRepository;
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

    @Transactional
    public ResponseEntity<?> sendLogs(Map<String, Object> resultMap) {

        long currentTime = new Date().getTime();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        HttpEntity<?> requestEntity = new HttpEntity<>(resultMap, headers);

        System.out.println(resultMap);

        String serverURL = "http://13.209.6.160:8000/user-analysis";

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = restTemplate.postForEntity(serverURL, requestEntity, Map.class);

        System.out.println("response : " + response.getBody());

        List<Map<String, Object>> analysisList = (List<Map<String, Object>> ) response.getBody().get("result");

        for (Map<String, Object> analysis : analysisList) {

            List<Map<String, Object>> friends = (List<Map<String, Object>>) analysis.get("friends");

            Map<String, Object> bestFriends = friends.get(0);

            FriendsDTO friendsDTO = new FriendsDTO();

            if(!bestFriends.get("bestfriend_1").equals("") && bestFriends.get("bestfriend_1") != null) {
                friendsDTO.setFriend1(Integer.parseInt((String) bestFriends.get("bestfriend_1")));
                friendsDTO.setFriend1Comment(getComment(Integer.parseInt(String.valueOf(bestFriends.get("time")))));
            }

            if(!bestFriends.get("bestfriend_2").equals("") && bestFriends.get("bestfriend_2") != null) {
                friendsDTO.setFriend2(Integer.parseInt((String) bestFriends.get("bestfriend_2")));
                friendsDTO.setFriend2Comment(getComment(Integer.parseInt(String.valueOf(bestFriends.get("time2")))));
            }

            analysisRepository.save(new Analysis(
                    0,
                    friendsDTO.getFriend1(),
                    friendsDTO.getFriend1Comment(),
                    friendsDTO.getFriend2(),
                    friendsDTO.getFriend2Comment(),
                    (String) analysis.get("place"),
                    Integer.parseInt((String) analysis.get("childid")),
                    "N",
                    new Date(currentTime)
            ));

        }

        return response;
    }

    public String getComment(int playtime) {

        String playTime = "";

        if((playtime / (60 * 60)) != 0) {
            playTime = playTime + (playtime / (60 * 60)) + "시간 ";
        }

        if((playtime /60) != 0) {
            playTime = playTime + ((playtime / 60) -  (playtime / (60 * 60) * 60)) + "분 ";
        }

        playTime = playTime + (playtime  -(playtime / 60) * 60)  + "초 ";


        playTime = playTime + " 같이 놀았어요!";

        return playTime;
    }

    public void saveLogFile(MultipartFile file) {
        awsS3UploadService.upload("logfiles", file);
    }
}