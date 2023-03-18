package cn.mimukeji.backend.business.type;

public enum CustomerStatus {
  IN_QUEUE_ING("1", "排队中"),
  COMPLETE_NORMAL("2", "排队完成，已经领取积分"),
  COMPLETE_TIME_OUT("3", "排队完成，超时未领取积分"),
  COMPLETE_NOT_ENOUGH_POINT("4", "排队完成，没有足够积分，重新排队"),
  ERROR_SYSTEM_ERROR("99999", "未知错误");
  
  private final String code;
  
  private final String name;
  
  private CustomerStatus(String code, String name) {
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
  
  public static CustomerStatus fromCode(String code) {
    for (CustomerStatus s : CustomerStatus.values()) {
      if (s.getCode() == code)
        return s;
    }
    return null;
  }
  
      public static void main(String[] args) {
          for(CustomerStatus resultType:CustomerStatus.values()){
              System.out.println(resultType.getCode() + ":" + resultType.getName());
          }
    }
}
