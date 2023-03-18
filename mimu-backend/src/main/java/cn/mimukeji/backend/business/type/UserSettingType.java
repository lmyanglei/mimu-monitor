package cn.mimukeji.backend.business.type;

public enum UserSettingType {
	POINT_MIN("point_min", "400"),// 最少积分
	VIRTUAL_PERSON_MIN("virtual_person_min", "50"),// 最少人数
  ERROR_SYSTEM_ERROR("99999", "未知错误");
  
  private final String code;
  
  private final String name;
  
  private UserSettingType(String code, String name) {
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
  
  public static UserSettingType fromCode(String code) {
    for (UserSettingType s : UserSettingType.values()) {
      if (s.getCode() == code)
        return s;
    }
    return null;
  }
  
      public static void main(String[] args) {
          for(UserSettingType resultType:UserSettingType.values()){
              System.out.println(resultType.getCode() + ":" + resultType.getName());
          }
    }
}
