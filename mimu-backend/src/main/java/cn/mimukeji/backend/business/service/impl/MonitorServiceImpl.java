package cn.mimukeji.backend.business.service.impl;

import cn.mimukeji.backend.business.entity.MonitorConfigData;
import cn.mimukeji.backend.business.service.*;
import cn.mimukeji.backend.business.type.DeleteStatus;
import cn.mimukeji.backend.business.type.EnableStatus;
import cn.mimukeji.backend.business.type.ServiceCheckType;
import cn.mimukeji.backend.util.EmailUtil;
import cn.mimukeji.util.DateStyle;
import cn.mimukeji.util.DateUtil;
import cn.mimukeji.util.DingtalkUtil;
import cn.mimukeji.util.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Service("monitorService")
public class MonitorServiceImpl implements MonitorService {

    @Value("${monitor.network}")
    String network;

    @Autowired
    EmailUtil emailUtil;

    @Autowired
    CronService cronService;

    @Autowired
    @Qualifier(value = "monitorsqliteJdbcTemplate")
    private JdbcTemplate monitorsqliteJdbcTemplate;

    @Override
    public List<MonitorConfigData> selectList() {
        List<MonitorConfigData> list = new ArrayList<MonitorConfigData>();
        try{
            String sql = "";

            sql = " SELECT * FROM monitor_config_data t"
                + " where t.delete_status=?";
            list =  monitorsqliteJdbcTemplate.query(sql, new BeanPropertyRowMapper(MonitorConfigData.class)
                    , Integer.parseInt(DeleteStatus.NOT_DELETED.getCode()));

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public MonitorConfigData selectMonitorConfigDataById(Integer id) {
        MonitorConfigData monitorConfigData = null;
        try{
            String sql = "";

            sql = " SELECT * FROM monitor_config_data t"
                    + " where t.delete_status=?"
                    + " and t.id=?";
            List<MonitorConfigData> list =  monitorsqliteJdbcTemplate.query(sql, new BeanPropertyRowMapper(MonitorConfigData.class)
                    , Integer.parseInt(DeleteStatus.NOT_DELETED.getCode()),id);

            if(null  != list && list.size() > 0){
                monitorConfigData = list.get(0);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return monitorConfigData;
    }

    @Override
    public int insertOrUpdateMonitorConfigData(MonitorConfigData monitorConfigData) {
        int result  = -1;
        if(null != monitorConfigData){
            String sql = "";
            Date date = new Date();
            if(null != monitorConfigData.getId() && 0 != monitorConfigData.getId()){
                // 更新
                sql = " update monitor_config_data" +
                        " set " +
                        " update_time = ?" +
                        ", delete_status = ?" +
                        ", service_name = ?" +
                        ", service_url = ?" +
                        ", service_check_type = ?" +
                        ", service_environment = ?" +
                        ", notify_emails = ?" +
                        ", notify_mobiles = ?" +
                        ", webhook_url = ?" +
                        ", webhook_keyword = ?" +
                        ", webhook_group_name = ?" +
                        ", cron = ?" +
                        ", timeout = ?" +
                        ", network = ?" +
                        ", enable_status = ?" +
                        " where id=" + monitorConfigData.getId();
                Object[] args = new Object[]{
                        date
                        , monitorConfigData.getDeleteStatus()
                        , monitorConfigData.getServiceName()
                        , monitorConfigData.getServiceUrl()
                        , monitorConfigData.getServiceCheckType()
                        , monitorConfigData.getServiceEnvironment()
                        , monitorConfigData.getNotifyEmails()
                        , monitorConfigData.getNotifyMobiles()
                        , monitorConfigData.getWebhookUrl()
                        , monitorConfigData.getWebhookKeyword()
                        , monitorConfigData.getWebhookGroupName()
                        , monitorConfigData.getCron()
                        , monitorConfigData.getTimeout()
                        , monitorConfigData.getNetwork()
                        , monitorConfigData.getEnableStatus()};
                result =  monitorsqliteJdbcTemplate.update(sql,args);
            }else{
                // 插入
                monitorConfigData.setDeleteStatus(Integer.parseInt(DeleteStatus.NOT_DELETED.getCode()));
                monitorConfigData.setServiceCheckType(ServiceCheckType.REQUEST_SUCCESS.getCode());
                sql = " insert into monitor_config_data " +
                        " (create_time,update_time,delete_status,service_name,service_url" +
                        ",service_check_type,service_environment,notify_emails,notify_mobiles,webhook_url" +
                        ",webhook_keyword,webhook_group_name,cron,timeout,network" +
                        ",enable_status)" +
                        " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                Object[] args = new Object[]{
                        date
                        , date
                        , monitorConfigData.getDeleteStatus()
                        , monitorConfigData.getServiceName()
                        , monitorConfigData.getServiceUrl()
                        , monitorConfigData.getServiceCheckType()
                        , monitorConfigData.getServiceEnvironment()
                        , monitorConfigData.getNotifyEmails()
                        , monitorConfigData.getNotifyMobiles()
                        , monitorConfigData.getWebhookUrl()
                        , monitorConfigData.getWebhookKeyword()
                        , monitorConfigData.getWebhookGroupName()
                        , monitorConfigData.getCron()
                        , monitorConfigData.getTimeout()
                        , monitorConfigData.getNetwork()
                        , monitorConfigData.getEnableStatus()};
                result =  monitorsqliteJdbcTemplate.update(sql,args);
            }
        }

        return  result;
    }

    @Override
    public int deleteMonitorConfigData(JSONArray ids) {
        int result  = -1;

        if(null != ids){

            String sql = "";

            sql = " update monitor_config_data"
                    + " set delete_status=" + Integer.parseInt(DeleteStatus.IS_DELETED.getCode())
                    + " where id=?";

             monitorsqliteJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
                public int getBatchSize(){
                    return ids.size();
                }

                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    int id = ids.getInteger(i);
                    ps.setInt(1, id);
                }
            });
        }

        return  result;
    }

    @Override
    public int enableMonitorConfigData(JSONArray ids) {
        return updateMonitorConfigDataEnableStatus(ids, Integer.parseInt(EnableStatus.ENABLE.getCode()));
    }

    @Override
    public int disableMonitorConfigData(JSONArray ids) {
        return updateMonitorConfigDataEnableStatus(ids, Integer.parseInt(EnableStatus.DISABLE.getCode()));
    }

    private int updateMonitorConfigDataEnableStatus(JSONArray ids,int enableStatus) {
        int result  = 1;

        if(null != ids){

            String sql = "";

            sql = " update monitor_config_data"
                    + " set enable_status=" + enableStatus
                    + " where id=?";

             monitorsqliteJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
                public int getBatchSize(){
                    return ids.size();
                }
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    int id = ids.getInteger(i);
                    ps.setInt(1, id);
                }
            });
        }

        return  result;
    }

    @Override
    public void checkoutMicroService() {

        try{
            String sql = "";

            sql="select t.id,t.service_url,t.service_name,t.service_check_type,service_environment" +
                    " ,notify_emails,notify_mobiles" +
                    " ,webhook_url,webhook_keyword,webhook_group_name" +
                    " ,cron,timeout,network from monitor_config_data t" +
                    " where t.delete_status=" + DeleteStatus.NOT_DELETED.getCode() +
                    " and t.enable_status=" + EnableStatus.ENABLE.getCode();

            List<Map<String,Object>> list =  monitorsqliteJdbcTemplate.queryForList(sql);
            if(null != list && list.size() > 0){
                list.forEach(item->{
                    if(null != item && item.size()>0){

                        boolean isSuccessful = false;

                        MonitorConfigData monitorConfigData = new MonitorConfigData();
                        monitorConfigData.setId(Long.parseLong(item.get("id").toString()));
                        monitorConfigData.setServiceEnvironment((String)item.get("service_environment"));
                        monitorConfigData.setServiceName(item.get("service_name").toString());
                        monitorConfigData.setServiceUrl(item.get("service_url").toString());
                        monitorConfigData.setServiceCheckType(item.get("service_check_type").toString());
                        monitorConfigData.setWebhookUrl(item.get("webhook_url").toString());
                        monitorConfigData.setWebhookKeyword(item.get("webhook_keyword").toString());
                        monitorConfigData.setWebhookGroupName(item.get("webhook_group_name").toString());
                        monitorConfigData.setNotifyEmails((String)item.get("notify_emails"));
                        monitorConfigData.setNotifyMobiles((String)item.get("notify_mobiles"));
                        monitorConfigData.setCron((String)item.get("cron"));
                        monitorConfigData.setTimeout((Integer)item.get("timeout"));
                        monitorConfigData.setNetwork((String)item.get("network"));

                        if(StringUtils.isNotEmpty(monitorConfigData.getNetwork()) && monitorConfigData.getNetwork().equalsIgnoreCase(network)
                                && StringUtils.isNotEmpty(monitorConfigData.getCron()) && cronService.isOnTime(monitorConfigData.getCron())
                            ){
                            // 仅监控同网络
                            // 仅监控 cron 符合的时间点
                            System.out.println(monitorConfigData.getServiceName() + DateUtil.dateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()));
                            if(ServiceCheckType.REQUEST_SUCCESS.getCode().equalsIgnoreCase(monitorConfigData.getServiceCheckType())){
                                try {
                                    int responseCode = 0;
                                    responseCode = HttpUtil.httpGetResponseCode(monitorConfigData.getServiceUrl(),monitorConfigData.getTimeout());
                                    if(200 == responseCode){
                                        isSuccessful = true;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    if(!isSuccessful){
                                        sendMessage(monitorConfigData);
                                    }
                                }
                            }else if(ServiceCheckType.RESPONSE_OK.getCode().equalsIgnoreCase(monitorConfigData.getServiceCheckType())){
                                try {
                                    String responseData = "";
                                    responseData = (String)HttpUtil.httpGet(monitorConfigData.getServiceUrl(),monitorConfigData.getTimeout());
                                    if(StringUtils.isNotEmpty(responseData) && responseData.contains("ok")){
                                        isSuccessful = true;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    if(!isSuccessful){
                                        sendMessage(monitorConfigData);
                                    }
                                }
                            }
                        }
                    }
                });
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendMessage(MonitorConfigData monitorConfigData) {

        String environmentName = "";
        if("prod".equals(monitorConfigData.getServiceEnvironment())){
            environmentName="正式站";
        }else if("test".equals(monitorConfigData.getServiceEnvironment())){
            environmentName="测试站";
        }else if("dev".equals(monitorConfigData.getServiceEnvironment())){
            environmentName="开发站";
        }

        String title = String.format("【失败】【%s】【%s】【%s】",environmentName,monitorConfigData.getServiceName(),monitorConfigData.getWebhookKeyword());
        String content = String.format("服务无法访问\n服务名称：%s\n站点类型：%s\n服务地址：%s",monitorConfigData.getServiceName()
                ,environmentName,monitorConfigData.getServiceUrl());

        // 邮件通知
        //emailUtil.sendSimpleMail(title,content,"lmyanglei@gmail.com","lmyanglei@gmail.com");

        // 钉钉群通知
        try {
            DingtalkUtil.sendDingtalkMessageText(monitorConfigData.getWebhookUrl(),title,content,monitorConfigData.getNotifyMobiles());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int dbCreateTableMonitorConfigData() {
        String sql = "";
        sql = "CREATE TABLE `monitor_config_data`  (\n" +
                " `id` bigint(20) NOT NULL  PRIMARY KEY,\n" +
                " `create_time` datetime NULL DEFAULT NULL,\n" +
                " `update_time` datetime NULL DEFAULT NULL,\n" +
                " `delete_status` int(11) NULL DEFAULT NULL,\n" +
                " `service_name` varchar(100),\n" +
                " `service_url` text,\n" +
                " `service_check_type` varchar(255),\n" +
                " `service_environment` varchar(255),\n" +
                " `notify_emails` text,\n" +
                " `notify_mobiles` text,\n" +
                " `webhook_url` varchar(255),\n" +
                " `webhook_keyword` varchar(255),\n" +
                " `webhook_group_name` varchar(255),\n" +
                " `cron` varchar(255) ,\n" +
                " `timeout` int(11) NULL DEFAULT NULL,\n" +
                " `network` varchar(255) ,\n" +
                " `enable_status` int(11) NULL DEFAULT 0\n" +
                ") ;";
        return monitorsqliteJdbcTemplate.update(sql);
    }

    @Override
    public int dbInitInsertDataMonitorConfigData() {
        String sql = "";
        sql = "INSERT INTO `monitor_config_data`\n" +
                "    (`id`, `create_time`, `update_time`, `delete_status`, `service_name`, `service_url`, `service_check_type`, `service_environment`, `notify_emails`, `notify_mobiles`, `webhook_url`, `webhook_keyword`, `webhook_group_name`, `cron`, `timeout`, `network`, `enable_status`)\n" +
                "    VALUES (1, '2022-06-26 11:43:43', '2022-06-26 11:43:47', 0, '百度（baidu）', 'http://www.baidu.com', '1', 'dev', 'lmyanglei@gmail.com', '1380138000', 'https://oapi.dingtalk.com/robot/send?access_token=420b1a020042e957f94705830dda5ce247efa4f82686f3435b576e22d956f800', '服务监控', '自己测试', '0 0/10 * * * ? ', 10000, 'inner', 0);\n";
        return monitorsqliteJdbcTemplate.update(sql);
    }
}