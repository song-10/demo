package com.example.sm4forandroid.Cipher;

import android.os.Build;
import android.support.annotation.RequiresApi;

import static com.example.sm4forandroid.Cipher.AES.aesDecrypt;
import static com.example.sm4forandroid.Cipher.AES.aesDecryptByBytes;
import static com.example.sm4forandroid.Cipher.AES.aesEncrypt;
import static com.example.sm4forandroid.Cipher.AES.aesEncryptToBytes;
import static com.example.sm4forandroid.Cipher.DES.desDecrypt;
import static com.example.sm4forandroid.Cipher.DES.desDecryptByBytes;
import static com.example.sm4forandroid.Cipher.DES.desEncrypt;
import static com.example.sm4forandroid.Cipher.DES.desEncryptToBytes;
import static com.example.sm4forandroid.Cipher.SM4.decryptData_CBC;
import static com.example.sm4forandroid.Cipher.SM4.encryptData_CBC;

public class Caller {

    public static final String ALGORITHM_NAME_AES = "AES";
    public static final String ALGORITHM_NAME_DES = "DES";
    public static final String ALGORITHM_NAME_SM4 = "SM4";

    public static byte[] Encode(byte plaintext[], byte key[], String way) throws Exception {
        byte[] result = null;
        switch (way){
            case ALGORITHM_NAME_SM4:
                result = encryptData_CBC(plaintext,key);
                break;
            case ALGORITHM_NAME_AES:
                result = aesEncryptToBytes(plaintext,key);
                break;
            case ALGORITHM_NAME_DES:
                result = desEncryptToBytes(plaintext,key);
                break;
        }
        return result;
    }


    public static byte[] Decode(byte[] ciphertext, byte[] key, String way) throws Exception {
        byte[] result = null;
        switch (way){
            case ALGORITHM_NAME_SM4:
                result = decryptData_CBC(ciphertext,key);
                break;
            case ALGORITHM_NAME_AES:
                result = aesDecryptByBytes(ciphertext,key);
                break;
            case ALGORITHM_NAME_DES:
                result = desDecryptByBytes(ciphertext,key);
                break;
        }
        return result;
  }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String Encode(String plaintext, String key, String way) throws Exception {
        String result = null;
        switch (way){
            case ALGORITHM_NAME_SM4:
                result = encryptData_CBC(plaintext,key);
                break;
            case ALGORITHM_NAME_AES:
                result = aesEncrypt(plaintext,key);
                break;
            case ALGORITHM_NAME_DES:
                result = desEncrypt(plaintext,key);
                break;
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String Decode(String ciphertext, String key, String way) throws Exception {
        String result = null;
        switch (way){
            case ALGORITHM_NAME_SM4:
                result = decryptData_CBC(ciphertext,key);
                break;
            case ALGORITHM_NAME_AES:
                result = aesDecrypt(ciphertext,key);
                break;
            case ALGORITHM_NAME_DES:
                result = desDecrypt(ciphertext,key);
                break;
        }
        return result;
    }
}
