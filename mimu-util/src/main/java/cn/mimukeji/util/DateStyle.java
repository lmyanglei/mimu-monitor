package cn.mimukeji.util;

/**
 * 日期格式类型
 */
public enum DateStyle {
  
  /** "-"形式 */
  /**
   * MM-dd
   */
  MM_DD("MM-dd"),
  /**
   * yyyy-MM
   */
  YYYY_MM("yyyy-MM"),
  /**
   * yyyy-MM-dd
   */
  YYYY_MM_DD("yyyy-MM-dd"),
  /**
   * MM-dd HH:mm
   */
  MM_DD_HH_MM("MM-dd HH:mm"),
  /**
   * MM-dd HH:mm:ss
   */
  MM_DD_HH_MM_SS("MM-dd HH:mm:ss"),
  /**
   * yyyy-MM-dd HH:mm
   */
  YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"),
  /**
   * yyyy-MM-dd HH:mm:ss
   */
  YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
  
  /** "/"形式 */
  /**
   * MM/dd
   */
  MM_DD_EN("MM/dd"),
  /**
   * yyyy/M
   */
  YYYY_MM_EN("yyyy/MM"),
  /**
   * yyyy/MM/dd
   */
  YYYY_MM_DD_EN("yyyy/MM/dd"),
  /**
   * MM/dd HH:mm
   */
  MM_DD_HH_MM_EN("MM/dd HH:mm"),
  /**
   * MM/dd HH:mm:ss
   */
  MM_DD_HH_MM_SS_EN("MM/dd HH:mm:ss"),
  /**
   * yyyy/MM/dd HH:mm
   */
  YYYY_MM_DD_HH_MM_EN("yyyy/MM/dd HH:mm"),
  /**
   * yyyy/MM/dd HH:mm:ss
   */
  YYYY_MM_DD_HH_MM_SS_EN("yyyy/MM/dd HH:mm:ss"),
  
  /** "中文"形式 */
  /**
   * MM月dd日
   */
  MM_DD_CN("MM月dd日"),
  /**
   * yyyy年MM月
   */
  YYYY_MM_CN("yyyy年MM月"),
  /**
   * yyyy年MM月dd日
   */
  YYYY_MM_DD_CN("yyyy年MM月dd日"),
  /**
   * MM月dd日 HH:mm
   */
  MM_DD_HH_MM_CN("MM月dd日 HH:mm"),
  /**
   * MM月dd日 HH:mm:ss
   */
  MM_DD_HH_MM_SS_CN("MM月dd日 HH:mm:ss"),
  /**
   * yyyy年MM月dd日 HH:mm
   */
  YYYY_MM_DD_HH_MM_CN("yyyy年MM月dd日 HH:mm"),
  /**
   * yyyy年MM月dd日 HH:mm:ss
   */
  YYYY_MM_DD_HH_MM_SS_CN("yyyy年MM月dd日 HH:mm:ss"),
  
  /**
   * HH:mm:ss
   */
  HH_MM("HH:mm"), HH_MM_SS("HH:mm:ss"),
  
  /**
   * yyyyMMdd
   */
  YY_MM_DD_2("yyyyMMdd"),
  
  /**
   * yyyyMMddHH
   */
  YYYY_MM_DD_HH_1("yyyyMMddHH");
  
    
  /**
   * value
   */
  private String value;
  
  /**
   * @param value 
   */
  DateStyle(String value) {
    this.value = value;
  }
  
  /**
   * @return value
   */
  public String getValue() {
    return value;
  }
}