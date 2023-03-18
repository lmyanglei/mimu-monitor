package cn.mimukeji.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

public class SignUtil {
    
    /**
     * 不参与计算的 key
     */
    private static HashSet<String> exceptKeySets = new HashSet<String>();
    
    static{
        exceptKeySets.add("sign");
    }

    public static <T> String getSign(T t, String secretKey, String secretValue) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (Field field : t.getClass().getDeclaredFields()) {
            try {
                if (!Modifier.isStatic(field.getModifiers())) {
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), t.getClass());
                    Method method = pd.getReadMethod();
                    if (!Modifier.isStatic(method.getModifiers())) {
                            map.put(field.getName(), method.invoke(t, new Object[] {}));
                    }
                }
            } catch (Exception e) {

            }
        }
        return getSign(map, secretKey, secretValue);
    }

    /**
     * 获取签名值
     */
    public static String getSign(Map<String, Object> map, String secretKey, String secretValue) {
        String returnValue = "";
        returnValue = getPreSignString(map, secretKey, secretValue);
        returnValue = MD5Util.getMD5String(returnValue);
        
        System.out.println("getSign=" + returnValue);
        return returnValue;
    }

    /**
     * 校验签名
     * @param <T>
     * 
     * @param map
     *            待签名数据
     * @param secretKey
     * @param secretValue
     * @param sign
     * @return
     */
    public static <T> boolean checkSign(T t, String secretKey, String secretValue, String sign) {
        return getSign(t, secretKey, secretValue).equalsIgnoreCase(sign) ? true : false;
    }
    
    /**
     * 校验签名
     * 
     * @param map
     *            待签名数据
     * @param secretKey
     * @param secretValue
     * @param sign
     * @return
     */
    public static boolean checkSign(Map<String, Object> map, String secretKey, String secretValue, String sign) {
        return getSign(map, secretKey, secretValue).equalsIgnoreCase(sign) ? true : false;
    }

    /**
     * 将map 转换成能进行加密计算的字符串 将map首先按照key的首字母排序,组成形如xx=xx&yy=yy的字符串
     * 
     * @param secretkey
     *            密钥key
     * @param secretValue
     *            密钥value
     * @param map
     * @return
     */
    public static String getPreSignString(Map<String, Object> map, String secretKey, String secretValue) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        StringBuffer sbBuffer = new StringBuffer();
        TreeMap<String, Object> treeMap = new TreeMap<String, Object>(map);

        Set<String> keySet = treeMap.keySet();
        for (String entry : keySet) {
            if (StringUtils.isNotBlank(entry)
                    && StringUtils.isNotBlank(treeMap.get(entry) == null ? "" : treeMap.get(entry).toString())) {
                if(!exceptKeySets.contains(entry)){
                    sbBuffer.append(entry).append("=").append(treeMap.get(entry).toString()).append("&");
                }
            }
        }
        sbBuffer.append(secretKey);
        sbBuffer.append("=");
        sbBuffer.append(secretValue);
        
        System.out.println("getPreSignString=" + sbBuffer.toString());
        return sbBuffer.toString();
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", "pid");
        map.put("value", "42391368");
        // System.out.println(SignUtil.getSign(map,Secret.getSecretKey(),Secret.getSecretValue()));
    }
}
