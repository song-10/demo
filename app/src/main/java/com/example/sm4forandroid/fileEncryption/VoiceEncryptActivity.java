package com.example.sm4forandroid.fileEncryption;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.app.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class VoiceEncryptActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "VoiceEncryptActivity";
    private File VoiceAddr=null;
    private FileInputStream fis = null;
    private FileOutputStream fos = null;

    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_encryption);
        Button play = (Button) findViewById(R.id.play);
        Button pause = (Button) findViewById(R.id.pause);
        Button stop = (Button) findViewById(R.id.stop);
        Button find = (Button) findViewById(R.id.choose_from_files);
        Button Encode = (Button) findViewById(R.id.encryptionButton);
        Button Decode = (Button) findViewById(R.id.decryptionButton);

        Encode.setOnClickListener(this);
        Decode.setOnClickListener(this);
        find.setOnClickListener(this);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        if (ContextCompat.checkSelfPermission(VoiceEncryptActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(VoiceEncryptActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    private void initMediaPlayer() {
        try {
            mediaPlayer.setDataSource(VoiceAddr.getPath()); // 指定音频文件的路径
            mediaPlayer.prepare(); // 让MediaPlayer进入到准备状态
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initMediaPlayer();
                } else {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start(); // 开始播放
                }
                break;
            case R.id.pause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause(); // 暂停播放
                }
                break;
            case R.id.stop:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.reset(); // 停止播放
                    initMediaPlayer();
                }
                break;
            case R.id.choose_from_files:
                getVoicepath();
                initMediaPlayer();
                break;
            case R.id.encryptionButton:
//                Encrypt();
                break;
            case R.id.decryptionButton:
//                Decrypt();
                break;
            default:
                break;
        }
    }

//    private void Decrypt() {
//        boolean isSuccess;// 解密保存
//        isSuccess = true;
//        byte[] oldByte = new byte[(int) VoiceAddr.length()];
//        try {
//            fis = new FileInputStream(VoiceAddr);
//            fis.read(oldByte);
//            byte[] newByte = SM4Decode(key, oldByte);
//            // 解密
//            fos = new FileOutputStream(VoiceAddr);
//            fos.write(newByte);
//
//        } catch (FileNotFoundException e) {
//            isSuccess = false;
//            e.printStackTrace();
//        } catch (IOException e) {
//            isSuccess = false;
//            e.printStackTrace();
//        } catch (Exception e) {
//            isSuccess = false;
//            e.printStackTrace();
//        }
//        try {
//            fis.close();
//            fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (isSuccess)
//            Toast.makeText(this, "解密成功", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(this, "解密失败", Toast.LENGTH_SHORT).show();
//    }

//    private void Encrypt() {
//        boolean isSuccess;// 加密保存
//        isSuccess = true;
//        try {
//            fis = new FileInputStream(VoiceAddr);
//            byte[] oldByte = new byte[(int) VoiceAddr.length()];
//            fis.read(oldByte); // 读取
//            byte[] newByte = SM4Encode(key, oldByte);
//            // 加密
//            fos = new FileOutputStream(VoiceAddr);
//            fos.write(newByte);
//
//        } catch (FileNotFoundException e) {
//            isSuccess = false;
//            e.printStackTrace();
//        } catch (IOException e) {
//            isSuccess = false;
//            e.printStackTrace();
//        } catch (Exception e) {
//            isSuccess = false;
//            e.printStackTrace();
//        } finally {
//            try {
//                fis.close();
//                fos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        if (isSuccess)
//            Toast.makeText(this, "加密成功", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(this, "加密失败", Toast.LENGTH_SHORT).show();
//
//        Log.i(TAG, "保存成功");
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    private static final int FILE_SELECT_CODE = 0;

    private void getVoicepath() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("audio/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, FILE_SELECT_CODE); // 打开文件，之后返回到case FILE_SELECT_CODE
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleAudioOnKitKat(data);
                    } else {
                        handleAudioBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }
    @TargetApi(19)
    private void handleAudioOnKitKat(Intent data) {
        String Path = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleVoiceOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Audio.Media._ID + "=" + id;
                Path = getPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, selection);
                VoiceAddr = new File(Path);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                Path = getPath(contentUri, null);
                VoiceAddr = new File(Path);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            Path = getPath(uri, null);
            VoiceAddr = new File(Path);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取路径即可
            Path = uri.getPath();
            VoiceAddr = new File(Path);

        }
    }

    private void handleAudioBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String Path = getPath(uri, null);
        VoiceAddr = new File(Path);
    }

    private String getPath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}