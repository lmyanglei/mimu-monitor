package cn.mimukeji.backend.business.type;

public enum PointChangeType {
  ADD("1", "增加"),
  REDUCE("2", "减少"),
    
  ERROR_SYSTEM_ERROR("99999", "未知错误");
  
  private final String code;
  
  private final String name;
  
  private PointChangeType(String code, String name) {
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
  
  public static PointChangeType fromCode(String code) {
    for (PointChangeType s : PointChangeType.values()) {
      if (s.getCode() == code)
        return s;
    }
    return null;
  }
  
      public static void main(String[] args) {
          for(PointChangeType resultType:PointChangeType.values()){
              System.out.println(resultType.getCode() + ":" + resultType.getName());
          }
    }
}
