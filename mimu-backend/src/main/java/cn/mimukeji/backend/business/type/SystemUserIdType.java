package cn.mimukeji.backend.business.type;

public enum SystemUserIdType {
  ORIGINAL_USERID("1", ""),
  TARGET_USERID("2", ""),
    
  ERROR_SYSTEM_ERROR("99999", "未知错误");
  
  private final String code;
  
  private final String name;
  
  private SystemUserIdType(String code, String name) {
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
  
  public static SystemUserIdType fromCode(String code) {
    for (SystemUserIdType s : SystemUserIdType.values()) {
      if (s.getCode() == code)
        return s;
    }
    return null;
  }
  
      public static void main(String[] args) {
          for(SystemUserIdType resultType:SystemUserIdType.values()){
              System.out.println(resultType.getCode() + ":" + resultType.getName());
          }
    }
}
