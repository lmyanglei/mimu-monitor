package cn.mimukeji.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


@SuppressWarnings("unchecked")
public class ConfigUtil {
	
	public static final Logger logger = Logger.getLogger(ConfigUtil.class);
	
	private static Map<String,String> config = new HashMap<String,String>();
	
	public static final String configFileName = "com/muxiaomi/dev/test/conf/constants.properties";
	public static final String encoding = "UTF-8";
	
	public static final String CONFIG_KEY_loginCheckUrlOa="loginCheckUrlOa";
	public static final String CONFIG_KEY_loginCheckUrlPassport="loginCheckUrlPassport";
	public static final String CONFIG_KEY_loginCheckUrlBi="loginCheckUrlBi";
	public static final String CONFIG_KEY_loginCheckUrlED="loginCheckUrlED";
  
	
	public static final String CONFIG_KEY_loginCheckUserOa="loginCheckUserOa";
	public static final String CONFIG_KEY_loginCheckPasswordOa="loginCheckPasswordOa";
	
	public static final String CONFIG_KEY_loginUrl="loginUrl";
	public static final String CONFIG_KEY_adminUserId="adminUserId";
	
	public static final String CONFIG_KEY_adminUser="adminUser";
	public static final String CONFIG_KEY_publicUserId="publicUserId";

	public static final String CONFIG_KEY_isso="isso";

	// 管理员用户
	private static final List<String> adminList = new ArrayList<String>();
	
	static{
		reload();
	}

	public static List<String> getAdminlist() {
		return adminList;
	}

	public static Map<String, String> getConfig() {
		return config;
	}
	
	public static void reload(){
		config = PropertiesReaderUtils.getPropertiesMap(configFileName);
		
		// 添加管理员账户
		String adminUser = getConfigValue(CONFIG_KEY_adminUser);
		String[] adminUserArr = adminUser.split("[" + Constants.SPLIT_CHAR + "]");
		adminList.clear();
		adminList.addAll(Arrays.asList(adminUserArr));
		logger.info(adminUser);
	}
	
	public static String getConfigValue(String configKey){
		return config.get(configKey);
	}
}
