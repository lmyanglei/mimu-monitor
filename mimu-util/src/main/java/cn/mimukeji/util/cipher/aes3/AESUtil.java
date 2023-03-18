package cn.mimukeji.util.cipher.aes3;
 
public class AESUtil {
    public static void main(String[] args) {
        String key="aes'skey";
        
        String plain="这是AES。This is AES.";
        AES aes=new AES(key);
        aes.setPlaintext(plain);
        aes.Encrypt();
        aes.showCiphertext();
        
        aes.Decrypt();
        aes.showPlaintext();
    }
}