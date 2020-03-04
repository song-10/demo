package com.example.sm4forandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import com.app.R;

//import com.example.messageencrypt.Message_MainActivity;
//import com.example.voiceencryption.VoiceEncryptionActivity;
//import com.itzs.testcipher.pictureMainActivity;
import butterknife.ButterKnife;


/**列表，点击可以选择文件，进入主页面*/
//主界面
public class Main2Activity extends BaseActivity{


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1);
        InitView();

    }

    /*Activity只有在Resumed、Paused、Stopped这三种状态下存在较长时间（即可以运行较长时间），
      Activity在其他状态下都是一闪而过。其中，在onResume中，Activity变为用户可见并且可以交互*/

    //对各个view控件进行初始化
    private void InitView() {
        ImageButton text;
        text=(ImageButton)findViewById(R.id.bt_text);
        ImageButton music;
        music = (ImageButton) findViewById(R.id.bt_music);
        ImageButton message;
        message = (ImageButton) findViewById(R.id.bt_message);
        ImageButton picture;
        picture=(ImageButton)findViewById(R.id.bt_picture);

//        选择各个加密的活动切换
        //文本加密
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Main2Activity.this, MEActivity.class);
                startActivity(intent);
            }
        });
//        //音频加密
//        music.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Main2Activity.this, VoiceEncryptionActivity.class);
//                startActivity(intent);
//            }
//        });
//        //短信加密
//        message.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Main2Activity.this, Message_MainActivity.class);
//                startActivity(intent);
//            }
//        });
//        //图片加密
//        picture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(Main2Activity.this, pictureMainActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main_1;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {

    }
}
