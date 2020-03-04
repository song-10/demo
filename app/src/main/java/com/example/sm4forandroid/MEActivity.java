package com.example.sm4forandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.app.R;


import com.youth.xframe.widget.XToast;


public class MEActivity extends AppCompatActivity {

    private EditText ePwd;
    private EditText eText;
    private EditText dText;
    private String key = "12345678";
    private String CipherText;
    private String PlainText = "test";


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
//        PlainText = new SM4(key).encrypt(CipherText);
//        eText.setText(PlainText);
//        }
//    private void SM4Encode() throws Exception {
//        CipherText = new SM4(key).encrypt(PlainText);
//        dText.setText(CipherText);
//    }
//
//
//    public static void main(String[] args) throws Exception {
//        String str="test";
//        String key="12345678";
//        String s=SM4.encode(str,key);
//        System.out.println(s);
//        System.out.println(SM4.decode(s,key));
//        MEActivity t=new MEActivity();
//        t.SM4Encode();
//
//    }
    }
}
