package com.example.sm4forandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;
import com.app.R;
import com.example.sm4forandroid.deal_ms.Message_main_activity;
import com.example.sm4forandroid.fileEncryption.ImageEncryptionActivity;
import com.example.sm4forandroid.fileEncryption.VideoEncryptActivity;
import com.example.sm4forandroid.fileEncryption.VoiceEncryptActivity;

import butterknife.ButterKnife;


/**列表，点击可以选择文件，进入主页面*/
//主界面
public class Main2Activity extends BaseActivity{
    private AlertDialog alertDialog;

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
        ImageButton file;
        file = (ImageButton) findViewById(R.id.bt_file);
        ImageButton message;
        message = (ImageButton) findViewById(R.id.bt_message);

//        选择各个加密的活动切换
        //文本加密
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Main2Activity.this, MEActivity.class);
                startActivity(intent);
            }
        });
        //文件
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showList();
            }
        });
//        //短信加密
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, Message_main_activity.class);
                startActivity(intent);
            }
        });
    }

    public void showList(){

        final String[] items = {"txt加解密","图片加解密","视频加解密","音频加解密"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("加密内容");
        alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if(which == 0) {
//                            Intent intent = new Intent(Main2Activity.this,TxtEncryptActivity.class);
//                            startActivity(intent);
//                            alertDialog.dismiss();
                }
                else if(which == 1){
                            Intent intent = new Intent(Main2Activity.this, ImageEncryptionActivity.class);
                            startActivity(intent);
                            alertDialog.dismiss();
                }
                else if(which == 2){
                            Intent intent = new Intent(Main2Activity.this, VideoEncryptActivity.class);
                            startActivity(intent);
                            alertDialog.dismiss();
                }
                else{
                            Intent intent = new Intent(Main2Activity.this,VoiceEncryptActivity.class);
                            startActivity(intent);
                            alertDialog.dismiss();
                }
            }
        });
        alertDialog = alertBuilder.create();
        alertDialog.show();
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
