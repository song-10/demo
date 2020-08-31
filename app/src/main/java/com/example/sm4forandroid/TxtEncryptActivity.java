package com.example.sm4forandroid;

import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.app.R;


import com.youth.xframe.widget.XToast;

import static com.example.sm4forandroid.Cipher.Caller.ALGORITHM_NAME_AES;
import static com.example.sm4forandroid.Cipher.Caller.ALGORITHM_NAME_DES;
import static com.example.sm4forandroid.Cipher.Caller.ALGORITHM_NAME_SM4;
import static com.example.sm4forandroid.Cipher.Caller.Decode;
import static com.example.sm4forandroid.Cipher.Caller.Encode;


public class TxtEncryptActivity extends AppCompatActivity {
    private AlertDialog alertDialog;

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
        setContentView(R.layout.txt_encryption);

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
                if(key.length() != 0){
                    XToast.success("获取密钥成功");
                }else {
                    XToast.error("请输入密钥！");
                }
                CipherText = dText.getText().toString();
                PlainText = eText.getText().toString();
            }
        });

        ENcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] items = {"DES","AES","SM4"};
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(TxtEncryptActivity.this);
                alertBuilder.setTitle("选择加密算法");
                alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if(which == 0){
                            do_Encrypt(ALGORITHM_NAME_DES);
                            alertDialog.dismiss();
                        }else if (which == 1) {
                            do_Encrypt(ALGORITHM_NAME_AES);
                            alertDialog.dismiss();
                        } else{
                            do_Encrypt(ALGORITHM_NAME_SM4);
                            alertDialog.dismiss();
                        }
                    }
                });
                alertDialog = alertBuilder.create();
                alertDialog.show();
            }
        });

        DEcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] items = {"DES","AES","SM4"};
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(TxtEncryptActivity.this);
                alertBuilder.setTitle("选择加密算法");
                alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if(which == 0){
                            do_Decrypt(ALGORITHM_NAME_DES);
                            alertDialog.dismiss();
                        }else if (which == 1) {
                            do_Decrypt(ALGORITHM_NAME_AES);
                            alertDialog.dismiss();
                        } else{
                            do_Decrypt(ALGORITHM_NAME_SM4);
                            alertDialog.dismiss();
                        }
                    }
                });
                alertDialog = alertBuilder.create();
                alertDialog.show();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void do_Encrypt(String way){
        try{
            CipherText = Encode(PlainText,key,way);
            dText.setText(CipherText);
            XToast.success("加密成功");
        }catch (Exception e){
            e.printStackTrace();
            if(key == null){
                XToast.error("加密失败,请输入密钥！");
            }else {
                XToast.error("加密失败");
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void do_Decrypt(String way){
        try{
            PlainText = Decode(CipherText,key,way);
            eText.setText(PlainText);
            XToast.success("解密成功");
        }catch (Exception e){
            e.printStackTrace();
            if(key == null){
                XToast.error("解密失败,请输入密钥！");
            }else {
                XToast.error("解密失败");
            }
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static void main(String[] args) throws Exception {
//        String key = "123";
//            String plaintext = null;
//            String cipherText = null;
//            String test = "test123456";
//
//            cipherText = Encode(test,key,ALGORITHM_NAME_DES);
//            System.out.println("SM4 CBC Padding encrypt result:\n" + cipherText);
//            plaintext = Decode(cipherText, key,ALGORITHM_NAME_DES);
//            System.out.println("SM4 CBC Padding decrypt result:\n" + plaintext);
//    }

}
