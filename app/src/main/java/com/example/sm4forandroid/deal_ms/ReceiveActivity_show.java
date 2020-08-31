package com.example.sm4forandroid.deal_ms;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.TextView;
import com.app.R;

import static com.example.sm4forandroid.Cipher.Caller.Decode;

public class ReceiveActivity_show extends Activity {
    private TextView Address_show;
    private TextView Time_show;
    private TextView Early_body_show;
    private TextView Late_body_show;
    private String keyBytes = "0123456789abcdef";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_show);
        InitView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void InitView() {

        Address_show = (TextView) findViewById(R.id.address_show);
        Time_show = (TextView) findViewById(R.id.time_show);
        Early_body_show = (TextView) findViewById(R.id.early_body_show);
        Late_body_show = (TextView) findViewById(R.id.late_body_show);

        //接收内容和id
        Bundle bundle = this.getIntent().getExtras();
        String body = bundle.getString("body");
        String time = bundle.getString("time");
        String address = bundle.getString("address");
        String receive_result ="";
        String way = bundle.getString("way");

        Address_show.setText(address);
        Early_body_show.setText(body);
        Time_show.setText(time);


        //对短信消息进行解密后显示在textview中
        try {
            receive_result = Decode(body,keyBytes,way);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Late_body_show.setText(receive_result);
    }
}
