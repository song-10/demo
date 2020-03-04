package com.example.sm4forandroid;

import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.youth.xframe.XFrame;
import org.litepal.LitePalApplication;
import com.app.R;


/**Application类贯穿整个程序的生命周期，随着进程的加载对象就创建了，
 * 当进程销毁的时候Application类才销毁。在实际开发过程中，可以继承Application类，
 * 创建自己的MyApplication类，在类里面可以提供一些变量，数据等等，
 * 这些变量与数据在整个生命周期过程中都可以被调用，比如经常要用到的Context，Handler对象可以被当前的Module里的任何类库引用到，
 * 而不必在需要用的时候去new对象或者通过构造传入。
 */

public class MyApplication extends LitePalApplication {
    /* onCreate()此方法可以监听APP一些配置信息的改变事件（如屏幕旋转等），当配置信息改变的时候会调用这个方法*/
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate() {
        super.onCreate();


        //android 7.0 调用系统相机崩溃
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        XFrame.init(this);
        //初始化日志
        XFrame.initXLog();
        //初始化多状态界面View
        XFrame.initXLoadingView()
                .setErrorViewResId(R.layout._loading_layout_error);

        /**
         * 初始化全局图片加载框架
         * GlideImageLoader为你的图片加载框架实现类
         */
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5a6d4941");

    }

}