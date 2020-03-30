package com.example.sm4forandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.app.R;


import com.example.sm4forandroid.sm4.SM4Util;
import com.youth.xframe.widget.XToast;

import junit.framework.Assert;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class MEActivity extends AppCompatActivity {

    private EditText ePwd;
    private EditText eText;
    private EditText dText;
    private String key = null;
    private String CipherText=null;
    private String PlainText =null;


    private Button ENcode;
    private Button DEcode;
    private Button CommitKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        eText = (EditText) findViewById(R.id.edit_plaintext);
        dText = (EditText) findViewById(R.id.edit_cipher);
        ePwd = (EditText) findViewById(R.id.sm4_key);
        InitView();
    }

    private void InitView() {
        ENcode = (Button) findViewById(R.id.encode);
        DEcode = (Button) findViewById(R.id.decode);
        CommitKey = (Button) findViewById(R.id.commit_pwd);

        CommitKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = ePwd.getText().toString();
                CipherText = dText.getText().toString();
                PlainText = eText.getText().toString();
            }
        });

//        ENcode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try{
//                    SM4Encode();
//                    XToast.success("加密成功");
//                }catch (Exception e){
//                    e.printStackTrace();
//                    XToast.error("加密失败");
//                }
//            }
//        });
//
//        DEcode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try{
//                    SM4Decode();
//                    XToast.success("解密成功");
//                }catch (Exception e){
//                    e.printStackTrace();
//                    XToast.error("解密失败");
//                }
//            }
//        });
//
//
//    private void SM4Decode() throws Exception {
//        PlainText = new BCECUtil(key).encrypt(CipherText);
//        eText.setText(PlainText);
//        }
//    private void SM4Encode() throws Exception {
//        CipherText = new BCECUtil(key).encrypt(PlainText);
//        dText.setText(CipherText);
//    }
    }

    public static void main(String[] args) throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidKeyException {
        try {
            byte[] key = {0x10,0x10,0x10,0x10,0x10,0x10,0x10,0x10,0x10,0x10,0x10,0x10,0x10,0x10,0x10,0x10};
            byte[] iv =  {0x11,0x11,0x11,0x11,0x11,0x11,0x11,0x11,0x11,0x11,0x11,0x11,0x11,0x11,0x11,0x11};
            byte[] cipherText = null;
            byte[] decryptedData = null;
            byte[] plaintext = {0x61,0x62,0x63};

            cipherText = SM4Util.encrypt_Cbc_Padding(key, iv, plaintext);
            System.out.println("SM4 CBC Padding encrypt result:\n" + Arrays.toString(cipherText));
            decryptedData = SM4Util.decrypt_Cbc_Padding(key, iv, cipherText);
            System.out.println("SM4 CBC Padding decrypt result:\n" + Arrays.toString(decryptedData));
            if (!Arrays.equals(decryptedData, plaintext)) {
                Assert.fail();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }
}
