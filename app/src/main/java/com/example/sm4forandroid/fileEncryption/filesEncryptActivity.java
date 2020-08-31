package com.example.sm4forandroid.fileEncryption;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.R;
import com.youth.xframe.widget.XToast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.sm4forandroid.Cipher.Caller.ALGORITHM_NAME_AES;
import static com.example.sm4forandroid.Cipher.Caller.ALGORITHM_NAME_SM4;
import static com.example.sm4forandroid.Cipher.Caller.Decode;
import static com.example.sm4forandroid.Cipher.Caller.Encode;

public class filesEncryptActivity extends AppCompatActivity {
    private AlertDialog alertDialog;
    private static final String TAG = "filesEncryptActivity";

    private EditText addr;
    private File filesAddr = null;
    private String address = null;
    private String dir = null;
    private FileInputStream fis = null;
    private FileOutputStream fos = null;


    private Button ENcode;
    private Button DEcode;
    private Button Commitaddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.files_encryption);
        addr = (EditText) findViewById(R.id.addr);
        InitView();
    }

    private void InitView() {
        ENcode = (Button) findViewById(R.id.encode);
        DEcode = (Button) findViewById(R.id.decode);
        Commitaddr = (Button) findViewById(R.id.commit_pwd);

        Commitaddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address = addr.getText().toString();
                dir = Environment.getExternalStorageDirectory().getAbsolutePath();
                if(address.length() != 0){
                    address = dir + "/" + address;
                    filesAddr = new File(address);
                    XToast.success("文件："+address);
                }else {
                    XToast.error("请输入文件路径！");
                }
            }
        });

        ENcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (address != null) {
                    final String[] items = {"AES", "SM4"};
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(filesEncryptActivity.this);
                    alertBuilder.setTitle("选择加密算法");
                    alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            if (which == 0) {
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
                }else{
                    XToast.error("请输入文件路径！");
                }
            }
        });

        DEcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (address != null) {
                    final String[] items = {"AES", "SM4"};
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(filesEncryptActivity.this);
                    alertBuilder.setTitle("选择加密算法");
                    alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            if (which == 0) {
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
                }else {
                    XToast.error("请输入文件路径！");
                }
            }
        });
    }
    private void do_Encrypt(String way){
        if(filesAddr.exists()) {
            boolean isSuccess;// 加密保存
            isSuccess = true;
            try {
                byte[] key = {(byte) 0x19, (byte) 0x16, (byte) 0x4, (byte) 0x14, (byte) 0x5, (byte) 0x7, (byte) 0x7, (byte) 0x21, (byte) 0xf, (byte) 0x13, (byte) 0x1e, (byte) 0x1a, (byte) 0x24, (byte) 0x8, (byte) 0x28, (byte) 0x17};
                fis = new FileInputStream(filesAddr);
                byte[] oldByte = new byte[(int) filesAddr.length()];
                fis.read(oldByte); // 读取
                byte[] newByte = Encode(oldByte, key, way);
                // 加密
                fos = new FileOutputStream(filesAddr);
                fos.write(newByte);

            } catch (FileNotFoundException e) {
                isSuccess = false;
                e.printStackTrace();
            } catch (IOException e) {
                isSuccess = false;
                e.printStackTrace();
            } catch (Exception e) {
                isSuccess = false;
                e.printStackTrace();
            } finally {
                try {
                    fis.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isSuccess)
                XToast.success("加密成功");
            else
                XToast.error("加密失败");
            Log.i(TAG, "保存成功");
        }else{
            XToast.error("文件不存在！");
        }
    }
    private void do_Decrypt(String way) {
        if (filesAddr.exists()) {
            boolean isSuccess;// 解密保存
            isSuccess = true;
            byte[] oldByte = new byte[(int) filesAddr.length()];
            try {
                byte[] key = {(byte) 0x19, (byte) 0x16, (byte) 0x4, (byte) 0x14, (byte) 0x5, (byte) 0x7, (byte) 0x7, (byte) 0x21, (byte) 0xf, (byte) 0x13, (byte) 0x1e, (byte) 0x1a, (byte) 0x24, (byte) 0x8, (byte) 0x28, (byte) 0x17};
                fis = new FileInputStream(filesAddr);
                fis.read(oldByte);
                byte[] newByte = Decode(oldByte, key, way);
                // 解密
                fos = new FileOutputStream(filesAddr);
                fos.write(newByte);

            } catch (FileNotFoundException e) {
                isSuccess = false;
                e.printStackTrace();
            } catch (IOException e) {
                isSuccess = false;
                e.printStackTrace();
            } catch (Exception e) {
                isSuccess = false;
                e.printStackTrace();
            }
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (isSuccess)
                XToast.success("解密成功");
            else
                XToast.error("解密失败");
        } else {
            XToast.error("文件不存在！");
        }
    }
}
