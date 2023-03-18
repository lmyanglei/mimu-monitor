package cn.mimukeji.util;

import org.apache.commons.lang.StringUtils;


public class CheckUtil {
	
    /**
     * 是否是email
     * 
     * @param str
     * @return
     */
	public static boolean checkEmail(String str){
		boolean returnValue = false;
		String regex = "\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}";
		if(StringUtils.isNotEmpty(str) && str.matches(regex)){
			returnValue = true;
		}
		return returnValue;
	}
	
	/**
	 * unix时间戳与当前时间比较，是否在指定时间内；
	 * 当前指定时间为：3600秒
	 * @param unixTimestamp
	 * @return
	 */
	public static boolean checkUnixTimestamp(Long unixTimestamp){
        boolean returnValue = false;
        
        if(Math.abs(UnixTimestampUtil.current() - unixTimestamp) < 3600){
            returnValue = true;
        }  
        return returnValue;
    }
}
