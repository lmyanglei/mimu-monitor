package cn.mimukeji.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;


public class ResponseUtil {
	/**
	 * 向前台返回json字符串的工具方法
	 * 
	 * @param response
	 * @param json
	 * @throws IOException
	 */
	public static void responseJson(HttpServletResponse response, String json)
			throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}
}
