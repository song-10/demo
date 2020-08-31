package com.example.sm4forandroid.deal_ms;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.app.R;
import com.youth.xframe.widget.XToast;

import static com.example.sm4forandroid.Cipher.Caller.ALGORITHM_NAME_AES;
import static com.example.sm4forandroid.Cipher.Caller.ALGORITHM_NAME_DES;
import static com.example.sm4forandroid.Cipher.Caller.ALGORITHM_NAME_SM4;
import static com.example.sm4forandroid.Cipher.Caller.Encode;

public class SendActivity extends Activity {
    private AlertDialog alertDialog;
    private String way = "";
    private IntentFilter sendFilter;
    private SendStatusReceiver sendStatusReceiver;
    private String keyBytes = "0123456789abcdef";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        String[] permissions = {Manifest.permission.SEND_SMS};
        if(PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            requestPermissions(permissions,200);
        }
        Choice();
        InitView();
    }
    private void InitView() {

        Button cancel = (Button) findViewById(R.id.cancel_edit);
        Button send = (Button) findViewById(R.id.send_edit);
        final EditText phone = (EditText) findViewById(R.id.phone_edit_text);
        final EditText msgInput = (EditText) findViewById(R.id.content_edit_text);


        //为发送短信设置要监听的广播
        sendFilter = new IntentFilter();
        sendFilter.addAction("SENT_SMS_ACTION");
        sendStatusReceiver = new SendStatusReceiver();
        registerReceiver(sendStatusReceiver, sendFilter);
//发送加密短信
        send.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                //接收edittext中的内容,并且进行加密
                String address = phone.getText().toString();
                String content = msgInput.getText().toString();
                String sendresult ="";
                try {
                    sendresult =Encode(content,keyBytes,way);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(SendActivity.this, "加密发送中，请稍后...", Toast.LENGTH_SHORT).show();
                //发送短信
                //并使用sendTextMessage的第四个参数对短信的发送状态进行监控
                SmsManager sms = SmsManager.getDefault();
                Intent sentIntent = new Intent("SENT_SMS_ACTION");
                PendingIntent pi = PendingIntent.getBroadcast(
                        SendActivity.this, 0, sentIntent, 0);
                sms.sendTextMessage(address, null, sendresult, pi, null);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Choice(){
        final String[] items = {"DES","AES","SM4"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SendActivity.this);
        alertBuilder.setTitle("选择加密算法");
        alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if(which == 0){
                    way = ALGORITHM_NAME_DES;
                    alertDialog.dismiss();
                }else if (which == 1) {
                    way = ALGORITHM_NAME_AES;
                    alertDialog.dismiss();
                } else{
                    way = ALGORITHM_NAME_SM4;
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    class SendStatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (getResultCode() == RESULT_OK) {
                //发送成功
                XToast.success("发送成功");
                finish();
            } else {
                //发送失败
                XToast.error("发送失败");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在Activity摧毁的时候停止监听
        unregisterReceiver(sendStatusReceiver);
    }
}
