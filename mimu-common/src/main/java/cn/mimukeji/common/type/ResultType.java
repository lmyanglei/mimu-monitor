package cn.mimukeji.common.type;

public enum ResultType {
  SUCCESS("1", "成功"),
  ERROR_SIGN("10001", "sign校验失败"),
  ERROR_PARAM_EMPTY("10002", "参数不能为空"),
  ERROR_PARAM_EMPTY_WX_JS_CODE("11001","参数不能为空，参数名：jsCode"),
  ERROR_DATA_EXIST("10003", "已存在此条数据"),
  ERROR_DATE("10004", "时间错误"),
  ERROR_DATE_FORMAT("10005", "时间格式错误"),
  ERROR_PAGE_NO("10006","pageNo为空或不合法"),
  ERROR_PAGE_SIZE("10007","pageSize为空或不合法"),
  ERROR_NEWCODE_EMPTY("10008","newcode不能为空"),
  ERROR_BEGIN_TIME_AFTER_END_TIME("10009","开始时间不能小于结束时间"),
  ERROR_BEGIN_TIME_TOO_LITTLE_THAN_END_TIME("10010","开始时间结束时间不能间隔太长"),
  ERROR_SOURCE_TYPE_EMPTY("10011","sourceType不能为空"),
  ERROR_TIMESTAMP("10012","时间戳有误"),
  ERROR_CANTBE_SAME_USERID("10013","双方id不能相同"),
  ERROR_POINT_TOO_LARGE("10014","积分过大"),
  ERROR_NOT_ENOUGH_POINT("10015","没有足够积分"),
  ERROR_CANNOT_AUDIT_ADMIN("10016","不能审核管理员"),
  ERROR_CHOOSE_FILE("10017","请选择文件"),
  ERROR_IMAGE_IS_ILLEGAL("10018","文件格式不合法"),
  ERROR_PHOTO_ID_IS_EMPTY("10019","照片ID不能为空"),
  ERROR_USER_ID_IS_EMPTY("10020","用户ID不能为空"),


  ERROR_SYSTEM_ERROR("99999", "未知错误");
  
  private final String code;
  
  private final String name;
  
  private ResultType(String code, String name) {
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
  
  public static ResultType fromCode(String code) {
    for (ResultType s : ResultType.values()) {
      if (s.getCode() == code)
        return s;
    }
    return null;
  }
  
      public static void main(String[] args) {
          for(ResultType resultType:ResultType.values()){
              System.out.println(resultType.getCode() + ":" + resultType.getName());
          }
    }
}
