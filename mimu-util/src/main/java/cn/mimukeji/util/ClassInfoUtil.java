package cn.mimukeji.util;

public class ClassInfoUtil {

    /**
     * 获取所在包名
     * 
     * @param clazz
     * @return
     */
    public static String getPackageName(Class clazz){
        return clazz.getPackage().getName();
    }
    
    /**
     * 获取类名
     * @param clazz
     * @return
     */
    public static String getClassName(Class clazz){
        return clazz.getName();
    }
}
