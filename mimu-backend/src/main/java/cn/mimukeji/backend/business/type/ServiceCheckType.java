package cn.mimukeji.backend.business.type;

public enum ServiceCheckType {
  REQUEST_SUCCESS("1", "HTTP请求成功"),
  RESPONSE_OK("2", "HTTP返回内容ok"),

  ERROR_SYSTEM_ERROR("99999", "未知错误");

  private final String code;

  private final String name;

  private ServiceCheckType(String code, String name) {
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
  
  public static ServiceCheckType fromCode(String code) {
    for (ServiceCheckType s : ServiceCheckType.values()) {
      if (s.getCode() == code)
        return s;
    }
    return null;
  }
  
      public static void main(String[] args) {
          for(ServiceCheckType resultType: ServiceCheckType.values()){
              System.out.println(resultType.getCode() + ":" + resultType.getName());
          }
    }
}
