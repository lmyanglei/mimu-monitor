package cn.mimukeji.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

/**
 * 邮件发送工具类
 */
public class DingtalkUtil {

	/**
	 * 发送简单文本通知
	 *
	 * @param dingtalkWebHookUrl 发送地址
	 * @param subject 主题
	 * @param content 内容
	 */
	public static void sendDingtalkMessageText(String dingtalkWebHookUrl ,String subject, String content,String mobiles) throws IOException {
		sendDingtalkMessage("text",dingtalkWebHookUrl,subject,content,mobiles);
	}

	/**
	 * 发送简单文本通知
	 *
	 * @param dingtalkMessageType 消息类型，目前只支持 text
	 * @param dingtalkWebHookUrl 发送地址
	 * @param subject 主题
	 * @param content 内容
	 * @author BillDowney
	 * @date 2020年4月4日 上午12:40:42
	 */
	public static void sendDingtalkMessage(String dingtalkMessageType,String dingtalkWebHookUrl ,String subject, String content,String mobiles) throws IOException {
		if("text".equals(dingtalkMessageType)){
			JSONObject requestData = new JSONObject();

			// at
			if(StringUtils.isNotEmpty(mobiles)){
				String[] ms = mobiles.split(",");

				JSONArray atMobilesData = new JSONArray();
				for(int i = 0; i< ms.length; i++){
					atMobilesData.add(ms[i]);
				}

				JSONObject atData = new JSONObject();
				atData.put("atMobiles",atMobilesData);
				requestData.put("at",atData);
			}

			// msgtype
			requestData.put("msgtype","text");

			// text
			JSONObject contentData = new JSONObject();
			contentData.put("content",subject +"\n" + content);

			requestData.put("text",contentData);

			// send
			System.out.println(HttpUtil.getLocalIP());
			System.out.println(requestData.toJSONString());
			HttpUtil.httpPostWithJson(dingtalkWebHookUrl,requestData.toJSONString());
		}
	}

}
