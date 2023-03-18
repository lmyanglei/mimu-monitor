package cn.mimukeji.backend.business.controllor;

import cn.mimukeji.backend.business.entity.MonitorConfigData;
import cn.mimukeji.backend.business.service.MonitorService;
import cn.mimukeji.common.bean.InterfaceResult;
import cn.mimukeji.common.type.ResultType;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("monitor")
public class MonitorControllor {

  @Autowired
  MonitorService monitorService;

  // 获取列表
  @GetMapping(value = "api/list")
  @ResponseBody
  public InterfaceResult list(HttpServletRequest request, HttpServletResponse response) throws IOException {
    InterfaceResult interfaceResult = new InterfaceResult();
    interfaceResult.failure();

    boolean isValid = true;

    if (isValid) {
      Map<String,Object> map = new HashMap<String,Object>();
      List<MonitorConfigData> list = monitorService.selectList();
      map.put("list", list);
      interfaceResult.success(map);
    }

    return interfaceResult;
  }

  // 获取详情
  @GetMapping(value = "api/select")
  @ResponseBody
  public InterfaceResult select(HttpServletRequest request, HttpServletResponse response
          ,Integer id) throws IOException {
    InterfaceResult interfaceResult = new InterfaceResult();
    interfaceResult.failure();

    boolean isValid = true;

    if (isValid) {
      Map<String,Object> map = new HashMap<String,Object>();
      MonitorConfigData item = monitorService.selectMonitorConfigDataById(id);
      map.put("item", item);
      interfaceResult.success(map);
    }

    return interfaceResult;
  }

  // 保存新增
  @PostMapping(value = "api/add")
  @ResponseBody
  public InterfaceResult add(HttpServletRequest request, HttpServletResponse response
          , @RequestBody MonitorConfigData monitorConfigData) throws IOException {
    return addOrEdit(request,response,monitorConfigData);
  }

  // 保存编辑
  @PostMapping(value = "api/edit")
  @ResponseBody
  public InterfaceResult edit(HttpServletRequest request, HttpServletResponse response
          , @RequestBody MonitorConfigData monitorConfigData) throws IOException {
    return addOrEdit(request,response,monitorConfigData);
  }

  // 新增或者编辑
  private InterfaceResult addOrEdit(HttpServletRequest request, HttpServletResponse response
          , @RequestBody MonitorConfigData monitorConfigData) throws IOException {
    InterfaceResult interfaceResult = new InterfaceResult();
    interfaceResult.failure();

    boolean isValid = true;

    if (isValid) {
      Map<String,Object> map = new HashMap<String,Object>();
      Integer result = monitorService.insertOrUpdateMonitorConfigData(monitorConfigData);
      map.put("result", result);
      interfaceResult.success(map);
    }

    return interfaceResult;
  }

  // 删除
  @PostMapping(value = "api/delete")
  @ResponseBody
  public InterfaceResult delete(HttpServletRequest request, HttpServletResponse response
          ,@RequestBody JSONObject ids) throws IOException {
    InterfaceResult interfaceResult = new InterfaceResult();
    interfaceResult.failure();

    boolean isValid = true;

    if (isValid) {
      Map<String,Object> map = new HashMap<String,Object>();
      Integer result = monitorService.deleteMonitorConfigData(ids.getJSONArray("ids"));
      map.put("result", result);
      interfaceResult.success(map);
    }

    return interfaceResult;
  }

  // 启用
  @PostMapping(value = "api/enable")
  @ResponseBody
  public InterfaceResult enable(HttpServletRequest request, HttpServletResponse response
          , @RequestBody JSONObject ids) throws IOException {
    InterfaceResult interfaceResult = new InterfaceResult();
    interfaceResult.failure();

    boolean isValid = true;

    if (isValid) {
      Map<String,Object> map = new HashMap<String,Object>();
      Integer result = monitorService.enableMonitorConfigData(ids.getJSONArray("ids"));
      map.put("result", result);
      interfaceResult.success(map);
    }

    return interfaceResult;
  }

  // 停用
  @PostMapping(value = "api/disable")
  @ResponseBody
  public InterfaceResult disable(HttpServletRequest request, HttpServletResponse response
          ,@RequestBody JSONObject ids) throws IOException {
    InterfaceResult interfaceResult = new InterfaceResult();
    interfaceResult.failure();

    boolean isValid = true;

    if (isValid) {
      Map<String,Object> map = new HashMap<String,Object>();
      Integer result = monitorService.disableMonitorConfigData(ids.getJSONArray("ids"));
      map.put("result", result);
      interfaceResult.success(map);
    }

    return interfaceResult;
  }

  // 创建 sqlite 数据库表，并初始化内容
  @PostMapping(value = "api/initdb")
  @ResponseBody
  public InterfaceResult initdb(HttpServletRequest request, HttpServletResponse response
          ) throws IOException {
    InterfaceResult interfaceResult = new InterfaceResult();
    interfaceResult.failure();

    boolean isValid = true;

    if (isValid) {
      Map<String,Object> map = new HashMap<String,Object>();
      Integer result = 0;
      result = monitorService.dbCreateTableMonitorConfigData();
      result = monitorService.dbInitInsertDataMonitorConfigData();
      map.put("result", result);
      interfaceResult.success(map);
    }

    return interfaceResult;
  }

  @RequestMapping({""})
  public String chaxunqita() {
    return "monitor/index";
  }
}
