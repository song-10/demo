package com.example.sm4forandroid.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.TextClock;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.sm4forandroid.BaseActivity;
import com.example.sm4forandroid.Main2Activity;
//import com.app.MainActivity;
import com.app.R;
import com.example.sm4forandroid.utils.MyUtils;
import com.youth.xframe.widget.XToast;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

import static com.app.R.id.about;
import static com.app.R.layout.activity_save_pwd;

/**图形密码设置页面
 */
public class SavePwdActivity extends BaseActivity{

    @Bind(R.id.patter_lock_view)
    PatternLockView mPatterLockView;
    private String pwd;
    private String pwdone;
    private String pwdtwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_save_pwd);
        ButterKnife.bind(this);
        pwd = MyUtils.gePwd(this);
        mPatterLockView.addPatternLockListener(mPatternLockViewListener);
        InitView();

    }

    /*解锁页面提示按钮信息*/

    private void InitView() {
//        Button Tips;
//        Tips = (Button) findViewById(R.id.button_info);
        findViewById(R.id.about).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (v.getId() == about) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(SavePwdActivity.this);
                    dialog.setTitle("提示");
                    dialog.setMessage("1、初次进入程序时请设置解锁应用图案；\n" +
                            "2、图案设置后不能更改,且忘记后无法找回；\n" +
                            "3、重新更换需重新安装此应用，但所有数据将被删除；\n" +
                            "4、文本加密解密操作时会删除原文件，操作前可先自行备份。");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.show();
                }
            }
        });
    }



    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
            Log.d(getClass().getName(), "Pattern drawing started");
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
            Log.d(getClass().getName(), "Pattern progress: " +
                    PatternLockUtils.patternToString(mPatterLockView, progressPattern));
        }
//首页图形解锁
        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            Log.d(getClass().getName(), "Pattern complete: " +
                    PatternLockUtils.patternToString(mPatterLockView, pattern));

            if(!TextUtils.isEmpty(pwd)) {
                pwdone=PatternLockUtils.patternToString(mPatterLockView, pattern);
                if (pwd.equals(pwdone)) {
                    Intent intent = new Intent(SavePwdActivity.this, Main2Activity.class);
                    intent.putExtra("title", "加密小助手");
                    startActivity(intent);
                    finish();
                } else {
                    XToast.error("密码不正确");
                }
            }else {
                if(!TextUtils.isEmpty(pwdone)){
                    pwdtwo=PatternLockUtils.patternToString(mPatterLockView, pattern);
                }else {
                    pwdone=PatternLockUtils.patternToString(mPatterLockView, pattern);
                    XToast.info("请再次设置密码");
                }
                if(TextUtils.isEmpty(pwd)&&!TextUtils.isEmpty(pwdone)&&!TextUtils.isEmpty(pwdtwo)){
                    if(pwdone.equals(pwdtwo)){
                        XToast.success("设置成功");
                        finish();
                        MyUtils.savePwd(SavePwdActivity.this,pwdone);
                        Intent intent = new Intent(SavePwdActivity.this, Main2Activity.class);
                        startActivity(intent);
                    }else {
                        pwdone="";
                        pwdtwo="";
                        XToast.error("两次设置不一样，请重新设置");
                    }
                }
            }
            mPatterLockView.clearPattern();
        }

        @Override
        public void onCleared() {
            Log.d(getClass().getName(), "Pattern has been cleared");
            mPatterLockView.clearPattern();
        }
    };

    @Override
    public int getLayoutId() {
        return activity_save_pwd;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {

    }


}
