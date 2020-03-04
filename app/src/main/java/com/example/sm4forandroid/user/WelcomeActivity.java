package com.example.sm4forandroid.user;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import com.app.R;
import com.example.sm4forandroid.activity.SavePwdActivity;
import butterknife.ButterKnife;
/*欢迎界面*/
public class WelcomeActivity extends Activity {

    private  ImageView mWel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome );
        //mWel = (ImageView) findViewById(R.id.wel);
        ButterKnife.bind(this);
        handler.sendEmptyMessageDelayed(0, 1000);

    }



    //进行一个消息的处理
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                gotoMainActivity();
                finish();
            }
        }
    };

    private void gotoMainActivity() {
        Intent intent = new Intent(this, SavePwdActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
