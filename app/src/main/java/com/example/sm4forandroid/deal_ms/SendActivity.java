package com.example.sm4forandroid.deal_ms;

//import android.app.Activity;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.app.R;

public class SendActivity extends Activity {
    private IntentFilter sendFilter;
    private SendStatusReceiver sendStatusReceiver;
    private String keyBytes = "abcdefgabcdefg12";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
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
            @Override
            public void onClick(View v) {
                Toast.makeText(SendActivity.this, "加密发送中，请稍后...", Toast.LENGTH_SHORT).show();
                //接收edittext中的内容,并且进行加密
                String address = phone.getText().toString();
                String content = msgInput.getText().toString();
                String sendresult ="";
                try {
//                    sendresult =SM4Encode(content,keyBytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //发送短信
                //并使用sendTextMessage的第四个参数对短信的发送状态进行监控
                SmsManager smsManager = SmsManager.getDefault();
                Intent sentIntent = new Intent("SENT_SMS_ACTION");
                PendingIntent pi = PendingIntent.getBroadcast(
                        SendActivity.this, 0, sentIntent, 0);
                smsManager.sendTextMessage(address, null, sendresult, pi, null);


                Log.d("短信加密 app","address value:adress debug"+address);//D ebug  调试
                Log.d("短信加密 app","content value:发送的明文："+content);
                Log.d("短信加密 app","sendresult value:接收的密文："+sendresult);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    class SendStatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (getResultCode() == RESULT_OK) {
                //发送成功
                Toast.makeText(context, "发送成功", Toast.LENGTH_LONG)
                        .show();

                Intent intent1 = new Intent(SendActivity.this, ReceiveActivity.class);
                startActivity(intent1);
                finish();
            } else {
                //发送失败
                Toast.makeText(context, "发送失败", Toast.LENGTH_LONG)
                        .show();
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
