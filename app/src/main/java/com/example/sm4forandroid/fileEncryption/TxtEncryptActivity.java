package com.example.sm4forandroid.fileEncryption;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.youth.xframe.widget.XToast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import com.app.R;

import org.w3c.dom.Text;

public class TxtEncryptActivity extends AppCompatActivity implements View.OnClickListener {

    String contents = "";
    String key = "";

    private Uri txtUri = null;
    private File txtAddr = null;

    private TextView str_content;
    private EditText keys;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.txt_encryption);
        Button encode = (Button) findViewById(R.id.encode);
        Button decode = (Button) findViewById(R.id.decode);
        Button find = (Button) findViewById(R.id.choose_from_files);
        Button commit = (Button) findViewById(R.id.commit_pwd);
        str_content = (TextView) findViewById(R.id.txt_content);
        keys = (EditText) findViewById(R.id.sm4_key);

        encode.setOnClickListener(this);
        decode.setOnClickListener(this);
        find.setOnClickListener(this);
        commit.setOnClickListener(this);
        if (ContextCompat.checkSelfPermission(TxtEncryptActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TxtEncryptActivity.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.encode:
//                writeData(SM4_encode(contents,key));
//                break;
            case R.id.decode:
//                writeData(SM4_decode(contents,key));
//                break;
            case R.id.commit_pwd:
                key = keys.getText().toString();
                break;
            case R.id.choose_from_files:
                getTxtpath();
                readData();
                str_content.setText(contents);
                break;
            default:
                break;
        }
    }

    private void writeData(String contents){
        try{
            if(txtAddr.isFile() && txtAddr.exists()){
                RandomAccessFile raf = new RandomAccessFile(txtAddr, "rwd");
                raf.seek(txtAddr.length());
                raf.write(contents.getBytes());
                raf.close();
            }
        }catch (Exception e){
            XToast.error(e.toString());
        }
    }

    private void readData(){
        if(!txtAddr.exists()){
            try {
                String encoding="utf-8";
                if(txtAddr.isFile() && txtAddr.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                            new FileInputStream(txtAddr),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
//                        System.out.println(lineTxt);
                        contents += lineTxt;
                    }
                    read.close();
                }else{
                    XToast.error("找不到指定的文件");
                }
            } catch (Exception e) {
                XToast.error("读取文件内容出错");
                e.printStackTrace();
            }
        }
    }

    private static final int FILE_SELECT_CODE = 0;
    private void getTxtpath(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("text/plain");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, FILE_SELECT_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleTxtOnKitKat(data);
                    } else {
                        handleTxtBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }
    @TargetApi(19)
    private void handleTxtOnKitKat(Intent data) {
        String Path = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleVideoOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Video.Media._ID + "=" + id;
                Path = getPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, selection);
                txtAddr = new File(Path);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                Path = getPath(contentUri, null);
                txtAddr = new File(Path);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            Path = getPath(uri, null);
            txtAddr = new File(Path);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取路径即可
            Path = uri.getPath();
            txtAddr = new File(Path);

        }
    }

    private void handleTxtBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String Path = getPath(uri, null);
        txtAddr = new File(Path);
    }

    private String getPath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
