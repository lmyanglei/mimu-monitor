package cn.mimukeji.backend.business.type;

public enum DeleteStatus {
	NOT_DELETED("0", "未删除"),
	IS_DELETED("1", "已删除"),
  ERROR_SYSTEM_ERROR("99999", "未知错误");
  
  private final String code;
  
  private final String name;
  
  private DeleteStatus(String code, String name) {
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
  
  public static DeleteStatus fromCode(String code) {
    for (DeleteStatus s : DeleteStatus.values()) {
      if (s.getCode() == code)
        return s;
    }
    return null;
  }
  
      public static void main(String[] args) {
          for(DeleteStatus resultType:DeleteStatus.values()){
              System.out.println(resultType.getCode() + ":" + resultType.getName());
          }
    }
}
