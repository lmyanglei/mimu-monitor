package cn.mimukeji.backend.business.service;

import cn.mimukeji.backend.business.entity.MonitorConfigData;
import com.alibaba.fastjson.JSONArray;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface MonitorService {

    /**
     * 查询列表
     */
    public List<MonitorConfigData> selectList();

    /**
     * 查询单条
     */
    public MonitorConfigData selectMonitorConfigDataById(Integer id);

    /**
     * 更新单条
     */
    public int insertOrUpdateMonitorConfigData(MonitorConfigData monitorConfigData);

    /**
     * 删除多条
     */
    public int deleteMonitorConfigData(JSONArray ids);

    /**
     * 停用多条
     */
    public int disableMonitorConfigData(JSONArray ids);

    /**
     * 启用多条
     */
    public int enableMonitorConfigData(JSONArray ids);

    public void checkoutMicroService();

    public int dbCreateTableMonitorConfigData();

    public int dbInitInsertDataMonitorConfigData();
}