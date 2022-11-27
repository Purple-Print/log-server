package com.purpleprint.logserver.controller;

import com.amazonaws.util.IOUtils;
import com.purpleprint.logserver.common.responsemessage.ResponseMessage;
import com.purpleprint.logserver.dto.AnalysisDTO;
import com.purpleprint.logserver.dto.FileReadDTO;
import com.purpleprint.logserver.dto.RequestDTO;
import com.purpleprint.logserver.model.LogModel;
import com.purpleprint.logserver.service.LogService;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
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

    //ai api 호출해서 받은 분석결과 db에 저장 로직
    @Scheduled(cron = "0 0 22 * * *")
    @GetMapping("/logs")
    public ResponseEntity<?> sendLogsAndGetAnalysisResult() {

        Page<LogModel> getLogResult = logService.getAllLogs();

        List<LogModel> logList = getLogResult.getContent();

        List<RequestDTO> requestLogList = new ArrayList<>();

        for (LogModel logs : logList) {
            RequestDTO requestDTO = new RequestDTO();
            requestDTO.setX(logs.getX().toString());
            requestDTO.setZ(logs.getZ().toString());
            requestDTO.setId(logs.getChildId().toString());
            requestDTO.setTime(logs.getTime());

            requestLogList.add(requestDTO);
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("coorperate", requestLogList);

        ResponseEntity<?> response = logService.sendLogs(resultMap);

//        boolean result = logService.setAnalysisData(response);

        return response;
    }

    //생성된 모든 로그 조회
    @PostMapping("/logs")
    public Map<String, Object> getAllLogs() {

        Page<LogModel> getLogResult = logService.getAllLogs();

        List<LogModel> logList = getLogResult.getContent();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("coorperate", logList);

        return resultMap;
    }

    // 로그 생성 및 time 형식 포맷
    @PostMapping("/log")
    public LogModel addLogs(@RequestBody LogModel log) {

        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));

        String dateFormatStringTime;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long longTime = Long.parseLong(log.getTime());
        Date formatDate = new Date(longTime);
        dateFormatStringTime = dateFormat.format(formatDate);
        log.setTime(dateFormatStringTime);
        System.out.println(log.getTime());

        return logService.addLogs(log);
    }

    //매일 22시 1분에 elasticsearch 로그 기록 삭제 후 파일에 저장, s3 스토리지에 업로드

    @Scheduled(cron = "0 30 7 * * *")
    @DeleteMapping("/logs")
    public ResponseEntity<?> deleteLog() throws IOException, ClassNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Page<LogModel> getLogResult = logService.getAllLogs();
        List<LogModel> logList = getLogResult.getContent();
`
        String rootLocation = System.getProperty("user.dir");

        System.out.println("rootLocation : " + rootLocation);
        FileReadDTO fileReadDTO = new FileReadDTO();
        String filePath = rootLocation + "\\src\\main\\resources\\logs.txt";
        System.out.println("filePath : " + filePath);
        String arr;
        File file = new File(filePath);

        //파일에 로그 정보 저장
        for (LogModel logs : logList) {
            fileReadDTO.setX(logs.getX());
            fileReadDTO.setZ(logs.getZ());
            fileReadDTO.setId(logs.getChildId());
            fileReadDTO.setTime(logs.getTime());

            arr = fileReadDTO.toString() + "\n";
            System.out.println("log : " + arr);

            try {
                arr = arr.substring("FileReadDTO(".length(), arr.indexOf(")")) + "\n";
                if(!file.exists()){ // 파일이 존재하지 않으면
                    file.createNewFile(); // 신규생성
                }
                Files.write(Paths.get(filePath), arr.getBytes(), StandardOpenOption.WRITE);
            }catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
        }

        FileItem fileItem = new DiskFileItem("logfile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());

        try {
            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);

        } catch (IOException ex) {

        }

        MultipartFile mFile = new CommonsMultipartFile(fileItem);
        logService.saveLogFile(mFile);

        //elasticsearch에 있는 로그 정보 삭제
        logService.deleteLog();

        Map<String, Object> responseMap = new HashMap<>();

        return ResponseEntity
                .ok()
                .body(new ResponseMessage(HttpStatus.OK, "log file uploaded and elasticsearch logs deleted successfully!", responseMap));

    }
}