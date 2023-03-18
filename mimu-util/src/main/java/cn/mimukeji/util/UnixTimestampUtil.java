package cn.mimukeji.util;

public class UnixTimestampUtil {

    public static void main(String[] args) {
        System.out.println(UnixTimestampUtil.current());
    }
    
    public static Long current(){
        return System.currentTimeMillis() / 1000;
    };

    public static Long currentTimeMillis(){
        return System.currentTimeMillis();
    };

}
