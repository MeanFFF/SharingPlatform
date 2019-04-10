package com.platform.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class File {
    private Integer id;

    private Integer categoryId;

    private String name;

    private BigDecimal price;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String detail;

    public File(Integer id, Integer categoryId, String name, BigDecimal price, Integer status, Date createTime, Date updateTime) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public File(Integer id, Integer categoryId, String name, BigDecimal price, Integer status, Date createTime, Date updateTime, String detail) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.detail = detail;
    }

    public File() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }
}