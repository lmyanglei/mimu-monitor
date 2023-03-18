package cn.mimukeji.util;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.alibaba.fastjson.JSONObject;


// 将一个字符串按照zip方式压缩和解压缩
public class ZipUtil {

    // 压缩
    public static String compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        return out.toString("ISO-8859-1");
    }

    // 解压缩
    public static String uncompress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        // toString()使用平台默认编码，也可以显式的指定如toString("GBK")
        return out.toString();
    }

    /**
     * 解压zip文件
     *
     * @param sourceFile 解压文件存放路径
     *            ,待解压的zip文件; toFolder,解压后的存放路径
     * @throws Exception
     **/

    public static void unzipFile(String sourceFile, String toFolder)throws Exception {
        String toDisk = toFolder;// 接收解压后的存放路径
        ZipFile zfile = new ZipFile(sourceFile);// 连接待解压文件  创建待解压文件对象
        Enumeration zList = zfile.entries();// 得到zip包里的所有元素
        ZipEntry ze = null;
        byte[] buf = new byte[1024];
        while (zList.hasMoreElements()) {
            ze = (ZipEntry) zList.nextElement(); //遍历压缩包中的所有元素
            if (ze.isDirectory()) { //如果当前元素是文件夹，跳出本次循环。
//              log.info("打开zip文件里的文件夹:" + ze.getName() + "skipped...");
                continue;
            }
            OutputStream outputStream = null;  //创建输出流对象
            InputStream inputStream = null;  //创建输入流对像
            try {
                // 以ZipEntry为参数得到一个InputStream，并写到OutputStream中
                outputStream = new BufferedOutputStream(new FileOutputStream(
                        toDisk + File.separator + ze.getName())); //创建写入字符流对象（参数：写入的文件夹 toDisk，写入的文件名称 ze.getName()）
                inputStream = new BufferedInputStream(zfile.getInputStream(ze)); //当前元素转为输入流对象（待输入）
                int readLen = 0;
                while ((readLen = inputStream.read(buf, 0, 1024)) != -1) {  //循环读取输入流（每次1024字节）
                    outputStream.write(buf, 0, readLen);  //写入文件
                }
                inputStream.close();  //完毕后关闭输入，输出流。
                outputStream.close();
            } catch (Exception e) {
//              log.info("解压失败：" + e.toString());
                throw new IOException("解压失败：" + e.toString());
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException ex) {

                    }
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                inputStream = null;
                outputStream = null;
            }

        }
        zfile.close();
    }

    // 测试方法
    public static void main(String[] args) throws IOException {
        try {
            unzipFile("D:\\20220101.export.CSV.zip","D:\\Downloads\\20220101");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
