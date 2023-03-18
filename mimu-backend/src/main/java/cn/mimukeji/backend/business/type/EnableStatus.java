package cn.mimukeji.backend.business.type;

public enum EnableStatus {
	ENABLE("1", "启用"),
	DISABLE("0", "停用"),
  ERROR_SYSTEM_ERROR("99999", "未知错误");
  
  private final String code;

  private final String name;

  private EnableStatus(String code, String name) {
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
  
  public static EnableStatus fromCode(String code) {
    for (EnableStatus s : EnableStatus.values()) {
      if (s.getCode() == code)
        return s;
    }
    return null;
  }
  
      public static void main(String[] args) {
          for(EnableStatus resultType: EnableStatus.values()){
              System.out.println(resultType.getCode() + ":" + resultType.getName());
          }
    }
}
