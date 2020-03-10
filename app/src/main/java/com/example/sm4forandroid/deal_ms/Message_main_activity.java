package com.example.sm4forandroid.deal_ms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.app.R;
/*短信加密主页面*/
public class Message_main_activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_main);

        InitView();
    }
    /*对view控件进行初始化*/
    private void InitView() {
        Button send=(Button)findViewById(R.id.bt_send);
        Button receive=(Button)findViewById(R.id.bt_receive);

//        由短信主活动切换到两个子活动（发送以及解密本地短信

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Message_main_activity.this,SendActivity.class);
                startActivity(intent);
            }
        });

        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Message_main_activity.this,ReceiveActivity.class);
                startActivity(intent);
            }
        });
    }
}
