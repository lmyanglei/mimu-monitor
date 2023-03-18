package cn.mimukeji.util;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    /**
     * 编译一个正则表达式
     *
     * @param regex
     * @return
     */
    public static Pattern compile(String regex, boolean isInsensitive) {
        if (true == isInsensitive) {
            return Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        } else {
            return Pattern.compile(regex);
        }
    }

    /**
     * 返回一个mathcer
     *
     * @param regex
     * @param content
     * @return
     */
    public static Matcher matcher(String regex, String content) {
        return compile(regex, true).matcher(content);
    }

    /**
     * 匹配第一个
     *
     * @param regex
     * @param content
     * @return
     */
    public static String matchOne(String regex, String content) {
        String match = null;
        Matcher m = matcher(regex, content);
        while (m.find()) {
            match = m.group().trim();
            break;
        }
        return match;
    }

    /**
     * 匹配第一个
     *
     * @param regex
     * @param content
     * @param regexGroup 正则分组号
     * @return
     */
    public static String matchOne(String regex, String content, int regexGroup) {
        String match = null;
        Matcher m = matcher(regex, content);
        while (m.find()) {
            match = m.group(regexGroup).trim();
            break;
        }
        return match;
    }

    /**
     * 匹配所有
     *
     * @param regex      正则表达式
     * @param regexGroup 分组号
     * @param content    要进行匹配的内容
     * @return
     */
    public static List<String> matchAll(String regex, String content, int regexGroup) {
        List<String> list = new LinkedList<String>();
        Matcher m = matcher(regex, content);
        while (m.find()) {
            list.add(m.group(regexGroup));
        }
        return list;
    }

    public static List<String> matchAll(String regex, String content) {
        List<String> list = new LinkedList<String>();
        Matcher m = matcher(regex, content);
        while (m.find()) {
            list.add(m.group());
        }
        return list;
    }

    /**
     * 判断是否匹配，返回布尔值
     * 说明:
     *
     * @param regex
     * @param content
     * @return
     */
    public static boolean matches(String regex, String content) {
        Matcher matcher = matcher(regex, content);
        boolean bool = matcher.matches();
        return bool;
    }

    public static List<String> toList(String regex, String source) {
        List<String> list = new LinkedList<String>();
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(source);
        while (m.find()) {
            list.add(m.group(1));
        }
        return list;
    }
}
