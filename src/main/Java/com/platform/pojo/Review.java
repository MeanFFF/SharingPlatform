package com.platform.pojo;

import java.util.Date;

public class Review {
    private Integer id;

    private String content;

    private Integer userId;

    private Integer fileId;

    private Date createTime;

    private Date updateTime;

    public Review(Integer id, String content, Integer userId, Integer fileId, Date createTime, Date updateTime) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.fileId = fileId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Review() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}