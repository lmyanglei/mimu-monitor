package cn.mimukeji.backend.business.type;

public enum PointChangeReasonType {
  DISPOSIT("1", "直接发放"),
  WITHDRWA("2", "兑换积分消费"),
    
  ERROR_SYSTEM_ERROR("99999", "未知错误");
  
  private final String code;
  
  private final String name;
  
  private PointChangeReasonType(String code, String name) {
    this.code = code;
    this.name = name;
  }
  
  public String getCode() {
    return code;
  }
  
  public String getName() {
    return name;
  }
  
  public String getCodeString() {
    return String.valueOf(code);
  }
  
  public static PointChangeReasonType fromCode(String code) {
    for (PointChangeReasonType s : PointChangeReasonType.values()) {
      if (s.getCode() == code)
        return s;
    }
    return null;
  }
  
      public static void main(String[] args) {
          for(PointChangeReasonType resultType:PointChangeReasonType.values()){
              System.out.println(resultType.getCode() + ":" + resultType.getName());
          }
    }
}
