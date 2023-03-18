package cn.mimukeji.backend.business.type;

public enum UserTypeType {
	CUSTOMER("1", "消费者"),
	COMPANY("2", "经销商"),
	WHOLESALER("3", "管理员"),
	FACTORY("4", "供货商"),
	SYSTEM("5", "系统用户"),
    
  ERROR_SYSTEM_ERROR("99999", "未知错误");
  
  private final String code;
  
  private final String name;
  
  private UserTypeType(String code, String name) {
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
  
  public static UserTypeType fromCode(String code) {
    for (UserTypeType s : UserTypeType.values()) {
      if (s.getCode() == code)
        return s;
    }
    return null;
  }
  
      public static void main(String[] args) {
          for(UserTypeType resultType:UserTypeType.values()){
              System.out.println(resultType.getCode() + ":" + resultType.getName());
          }
    }
}
