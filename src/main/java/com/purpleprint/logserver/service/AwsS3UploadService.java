package com.purpleprint.logserver.service;

import com.purpleprint.logserver.dto.UploadFileDTO;
import com.purpleprint.logserver.entity.Logfile;
import com.purpleprint.logserver.exception.FileEmptyException;
import com.purpleprint.logserver.repository.AwsS3UploadRepository;
import com.purpleprint.logserver.repository.LogfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

/**
 * <pre>
 * Class : AwsS3UploadService
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
@Service
public class AwsS3UploadService {

    private final AwsS3UploadRepository awsS3UploadRepository;
    private final LogfileRepository logfileRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    public AwsS3UploadService(AwsS3UploadRepository awsS3UploadRepository, LogfileRepository logfileRepository) {
        this.awsS3UploadRepository = awsS3UploadRepository;
        this.logfileRepository = logfileRepository;
    }

    public void validateFileExists(MultipartFile multipartFile) {
        if(multipartFile.isEmpty()) {
            System.out.println("파일이 존재하지 않습니다.");
            throw new FileEmptyException("파일이 존재하지 않습니다.");
        }
    }

    @Transactional
    public UploadFileDTO upload(String folderName, MultipartFile multipartFile) {

        LocalDate now = LocalDate.now();
        validateFileExists(multipartFile); //파일 존재 여부 확인

        System.out.println("multipartFile : " + multipartFile);

        String originalFileName = multipartFile.getOriginalFilename(); //파일명 + 확장자
        System.out.println("originalFileName : " + originalFileName);

        int pos = originalFileName.lastIndexOf("."); // .위치

        String extension = originalFileName.substring(pos + 1); //확장자
        String fileName = originalFileName.substring(0, pos); //파일명

        String conversionFileName = fileName + now + "." + extension; //변환된 파일명

        String s3Location = bucket + "/" + folderName;      //저장소

        String s3Key = folderName + "/" + conversionFileName;

        awsS3UploadRepository.awsUpload(s3Location, conversionFileName, multipartFile);

        String url = "https://purpleprint-bucket.s3.ap-northeast-2.amazonaws.com/" + folderName + "/" + conversionFileName;

        Logfile saveLogfile = null;

        saveLogfile = logfileRepository.save(new Logfile(
                0,
                url,
                s3Key
        ));

        return new UploadFileDTO(url, s3Key);
    }
}
