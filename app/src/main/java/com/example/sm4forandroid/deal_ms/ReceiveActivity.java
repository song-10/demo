package com.example.sm4forandroid.deal_ms;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.app.R;

import static com.example.sm4forandroid.Cipher.Caller.ALGORITHM_NAME_AES;
import static com.example.sm4forandroid.Cipher.Caller.ALGORITHM_NAME_DES;
import static com.example.sm4forandroid.Cipher.Caller.ALGORITHM_NAME_SM4;

public class ReceiveActivity extends Activity implements AdapterView.OnItemClickListener{
    String way = "";
    private AlertDialog alertDialog;
    private TextView Tv_address;
    private TextView Tv_body;
    private TextView Tv_time;
    private ListView listview;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter simple_adapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        String[] permissions = {Manifest.permission.READ_SMS};
        if(PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            requestPermissions(permissions,200);
        }
        Choice();
        InitView();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onStart() {
        super.onStart();
        RefreshList();
    }


    private void InitView() {
        Tv_address = (TextView) findViewById(R.id.tv_address);
        Tv_body = (TextView) findViewById(R.id.tv_body);
        Tv_time = (TextView) findViewById(R.id.tv_time);
        listview = (ListView) findViewById(R.id.list_receive);
        dataList = new ArrayList<Map<String, Object>>();

        listview.setOnItemClickListener(this);
    }


    private void RefreshList() {
        //从短信数据库读取信息
        Uri uri = Uri.parse("content://sms/inbox");
        String[] projection = new String[]{"address", "body", "date"};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, "date desc");

        //20条最近短信
        for (int i = 0; i < 20; i++) {
            //从手机短信数据库获取信息
            if(cursor.moveToNext()) {
                String address = cursor.getString(cursor.getColumnIndex("address"));
                String body = cursor.getString(cursor.getColumnIndex("body"));
                long longDate = cursor.getLong(cursor.getColumnIndex("date"));
                //将获取到的时间转换为我们想要的方式
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                Date d = new Date(longDate);
                String time = dateFormat.format(d);


                Map<String, Object> map = new HashMap<String, Object>();
                map.put("address", address);
                map.put("body", body+"body");
                map.put("time", time+" time");
                dataList.add(map);
            }
        }

        simple_adapter = new SimpleAdapter(this, dataList, R.layout.activity_receive_list_item,
                new String[]{"address", "body", "time"}, new int[]{
                R.id.tv_address, R.id.tv_body, R.id.tv_time});
        listview.setAdapter(simple_adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //获取listview中此个item中的内容
        //content的内容格式如下
        String content = listview.getItemAtPosition(i) + "";
        String body = content.substring(content.indexOf("body=")+5,content.lastIndexOf("body"));
        String address = content.substring(content.indexOf("address=")+8,content.indexOf("address=")+19);
        String time = content.substring(content.indexOf("time=")+5,content.lastIndexOf("time"));
        //使用bundle存储数据发送给下一个Activity
        Intent intent=new Intent(ReceiveActivity.this,ReceiveActivity_show.class);
        Bundle bundle = new Bundle();
        bundle.putString("body", body);
        bundle.putString("address", address);
        bundle.putString("time", time);
        bundle.putString("way", way);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    private void Choice(){
        final String[] items = {"DES","AES","SM4"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ReceiveActivity.this);
        alertBuilder.setTitle("选择加密算法");
        alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if(which == 0){
                    way = ALGORITHM_NAME_DES;
                    alertDialog.dismiss();
                }else if (which == 1) {
                    way = ALGORITHM_NAME_AES;
                    alertDialog.dismiss();
                } else{
                    way = ALGORITHM_NAME_SM4;
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog = alertBuilder.create();
        alertDialog.show();
    }
}
