package cn.mimukeji.util;


public class Constants {

	public static final String FAIL_CODE = "10001";
	public static final String FAIL_MESSAGE = "失败";
	
	public static final String SUCCESS_CODE = "10000";
	public static final String SUCCESS_MESSAGE = "成功";
	public static final String SUCCESS_MESSAGE_LOGIN = "登录成功";

	public static final String ERROR_CODE_LOGIN = "90001";
	public static final String ERROR_MESSAGE_LOGIN = "登录失败";

	public static final String ERROR_CODE_EMAIL = "90002";
	public static final String ERROR_MESSAGE_EMAIL = "eamil地址不正确";
	
	public static final String ERROR_CODE_EMAIL_FORMAT = "90003";
	public static final String ERROR_MESSAGE_EMAIL_FORMAT = "eamil地址不正确，多个邮箱请以英文逗号分隔";
	
	public static final String COOKIE_NAME_OA = "oa_token";
	public static final String COOKIE_NAME_PASSPORT = "sfut";
	public static final String COOKIE_NAME_BI = "bi_token";
	
	public static final String COOKIE_NAME_VISIT_SOURCE_KEY = "FANGTIANXIA_FINEBI_VISIT_SOURCE";
	public static final String COOKIE_NAME_VISIT_SOURCE_ED = "ED";
  
	/**
	 * 公共链接，使用此用户登录，模拟无权限
	 */
	public static final String CONST_publicUserId = "public";
	
	/**
	 * 判断public用户是否登录
	 */
	public static final String CONST_SESSION_KEY_FR_IS_FIRST_KEY_PUBLIC = "fr_isfirst_key_public";

	/**
	 * 判断非public用户是否登录
	 */
	public static final String CONST_SESSION_KEY_FR_IS_FIRST_KEY = "fr_isfirst_key";

	public static final String SPLIT_CHAR = "|";
}
