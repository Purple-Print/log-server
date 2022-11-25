package com.purpleprint.logserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * <pre>
 * Class : LogModel
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
@Document(indexName = "purpleprint")
public class LogModel {

    @Id
    private String key;

    @JsonProperty("x")
    private Float xcoord;

    @JsonProperty("z")
    private Float zcoord;

    @JsonProperty("id")
    private Integer childId;


    @JsonProperty("time")
    private String at;


    public Float getXcoord() {
        return xcoord;
    }

    public void setXcoord(Float xcoord) {
        this.xcoord = xcoord;
    }

    public Float getZcoord() {
        return zcoord;
    }

    public void setZcoord(Float zcoord) {
        this.zcoord = zcoord;
    }

    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }
}
