package cn.mimukeji.backend.business.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class MonitorConfigData {
    private Long id;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Integer deleteStatus;

    private String serviceName;

    private String serviceUrl;

    private String serviceCheckType;

    private String serviceEnvironment;

    private String notifyEmails;

    private String notifyMobiles;

    private String webhookUrl;

    private String webhookKeyword;

    private String webhookGroupName;

    private String cron;

    private int timeout;

    private String network;

    private Integer enableStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getServiceCheckType() {
        return serviceCheckType;
    }

    public void setServiceCheckType(String serviceCheckType) {
        this.serviceCheckType = serviceCheckType;
    }

    public String getServiceEnvironment() {
        return serviceEnvironment;
    }

    public void setServiceEnvironment(String serviceEnvironment) {
        this.serviceEnvironment = serviceEnvironment;
    }

    public String getNotifyEmails() {
        return notifyEmails;
    }

    public void setNotifyEmails(String notifyEmails) {
        this.notifyEmails = notifyEmails;
    }

    public String getNotifyMobiles() {
        return notifyMobiles;
    }

    public void setNotifyMobiles(String notifyMobiles) {
        this.notifyMobiles = notifyMobiles;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public String getWebhookKeyword() {
        return webhookKeyword;
    }

    public void setWebhookKeyword(String webhookKeyword) {
        this.webhookKeyword = webhookKeyword;
    }

    public String getWebhookGroupName() {
        return webhookGroupName;
    }

    public void setWebhookGroupName(String webhookGroupName) {
        this.webhookGroupName = webhookGroupName;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }
}