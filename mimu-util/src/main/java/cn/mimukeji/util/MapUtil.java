package cn.mimukeji.util;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

public class MapUtil {

	public static Logger logger = Logger.getLogger(MapUtil.class.getName());
	
	public static void printMap(Map<String,String> map){
		Iterator iterator = map.keySet().iterator();
		while(iterator.hasNext()){
			String key = (String)iterator.next();
			String value = map.get(key);
			logger.info(key + "," + value);
		}
		
	}
}
