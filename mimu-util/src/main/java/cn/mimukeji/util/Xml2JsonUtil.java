package cn.mimukeji.util;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.XML;

public class Xml2JsonUtil {
  
  private static final Logger logger = Logger.getLogger(Xml2JsonUtil.class);
  
  
  /**
   * 将xml字符串转换为json对象返回
   * 
   * @param xmlString
   * @return
   */
  public static JSONObject parse(String xmlString) {
    try {
      JSONObject json = XML.toJSONObject(xmlString);
      return json;
    }
    catch (Exception e) {
      logger.info("将xml字符串转换为json对象返回失败" + xmlString);
      // TODO: handle exception
    }
    
    return null;
  }
}
