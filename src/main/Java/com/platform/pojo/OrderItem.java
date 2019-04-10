package com.platform.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class OrderItem {
    private Integer id;

    private Integer userId;

    private Long orderNo;

    private Integer fileId;

    private String fileName;

    private BigDecimal price;

    private Date createTime;

    private Date updateTime;

    private String fileDetail;

    public OrderItem(Integer id, Integer userId, Long orderNo, Integer fileId, String fileName, BigDecimal price, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.orderNo = orderNo;
        this.fileId = fileId;
        this.fileName = fileName;
        this.price = price;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public OrderItem(Integer id, Integer userId, Long orderNo, Integer fileId, String fileName, BigDecimal price, Date createTime, Date updateTime, String fileDetail) {
        this.id = id;
        this.userId = userId;
        this.orderNo = orderNo;
        this.fileId = fileId;
        this.fileName = fileName;
        this.price = price;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.fileDetail = fileDetail;
    }

    public OrderItem() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getFileDetail() {
        return fileDetail;
    }

    public void setFileDetail(String fileDetail) {
        this.fileDetail = fileDetail == null ? null : fileDetail.trim();
    }
}