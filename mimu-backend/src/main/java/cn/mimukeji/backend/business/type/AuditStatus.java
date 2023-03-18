package cn.mimukeji.backend.business.type;

public enum AuditStatus {
  WAIT_TO_AUDIT("1", "待审核"),
  AUDIT_SUCCESS("2", "审核通过"),
  AUDIT_FAILURE("3", "审核不通过"),
  ERROR_SYSTEM_ERROR("99999", "未知错误");
  
  private final String code;
  
  private final String name;
  
  private AuditStatus(String code, String name) {
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
  
  public static AuditStatus fromCode(String code) {
    for (AuditStatus s : AuditStatus.values()) {
      if (s.getCode() == code)
        return s;
    }
    return null;
  }
  
      public static void main(String[] args) {
          for(AuditStatus resultType:AuditStatus.values()){
              System.out.println(resultType.getCode() + ":" + resultType.getName());
          }
    }
}
