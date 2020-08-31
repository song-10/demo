package com.example.sm4forandroid.fileEncryption;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.app.R;
import com.youth.xframe.widget.XToast;

import static com.example.sm4forandroid.Cipher.Caller.ALGORITHM_NAME_AES;
import static com.example.sm4forandroid.Cipher.Caller.ALGORITHM_NAME_SM4;
import static com.example.sm4forandroid.Cipher.Caller.Decode;
import static com.example.sm4forandroid.Cipher.Caller.Encode;

public class ImageEncryptActivity extends AppCompatActivity {
    private AlertDialog alertDialog;
    private static final String TAG = "ImageEncryptActivity";

    public static final int CHOOSE_PHOTO = 2;

    private ImageView picture;

    private Uri imageUri;

    private File imageAddr = null;
    private FileInputStream fis = null;
    private FileOutputStream fos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_encryption);
        Button chooseFromAlbum = (Button) findViewById(R.id.choose_from_album);
        Button ImgEncode = (Button)findViewById(R.id.btn_encrypt);
        Button ImgDecode = (Button)findViewById(R.id.btn_decrypt);
        picture = (ImageView) findViewById(R.id.picture);
        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ImageEncryptActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ImageEncryptActivity.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                } else {
                    openAlbum();
                }
            }
        });
        ImgEncode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = {"AES","SM4"};
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ImageEncryptActivity.this);
                alertBuilder.setTitle("选择加密算法");
                alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (which == 0) {
                            do_Encrypt(ALGORITHM_NAME_AES);
                            alertDialog.dismiss();
                        } else if (which == 1) {
                            do_Encrypt(ALGORITHM_NAME_SM4);
                            alertDialog.dismiss();
                        }
                    }
                });
                alertDialog = alertBuilder.create();
                alertDialog.show();
            }
        });

        ImgDecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = {"AES","SM4"};
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ImageEncryptActivity.this);
                alertBuilder.setTitle("选择加密算法");
                alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (which == 0) {
                            do_Decrypt(ALGORITHM_NAME_AES);
                            alertDialog.dismiss();
                        } else if (which == 1) {
                            do_Decrypt(ALGORITHM_NAME_SM4);
                            alertDialog.dismiss();
                        }
                    }
                });
                alertDialog = alertBuilder.create();
                alertDialog.show();
            }
        });

    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                imageAddr = new File(imagePath);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
                imageAddr = new File(imagePath);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
            imageAddr = new File(imagePath);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
            imageAddr = new File(imagePath);

        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        imageAddr = new File(imagePath);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    private void do_Encrypt(String way){
            boolean isSuccess;
            isSuccess = true;
            try{
                byte[] key={(byte)0x19, (byte)0x16, (byte)0x4, (byte)0x14, (byte)0x5, (byte)0x7, (byte)0x7, (byte)0x21, (byte)0xf, (byte)0x13, (byte)0x1e, (byte)0x1a, (byte)0x24, (byte)0x8, (byte)0x28, (byte)0x17};
                fis = new FileInputStream(imageAddr);
                byte[] oldByte = new byte[(int) imageAddr.length()];
                fis.read(oldByte);
                byte[] newByte = Encode(oldByte, key, way);
                fos = new FileOutputStream(imageAddr);
                fos.write(newByte);
            } catch (FileNotFoundException e){
                isSuccess = false;
                e.printStackTrace();
            } catch (IOException e){
                isSuccess = false;
                e.printStackTrace();
            }catch (Exception e){
                isSuccess = false;
                e.printStackTrace();
            }finally {
                try {
                    fis.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isSuccess)
                XToast.success("加密成功");
            else
                XToast.error("加密失败");
            Log.i(TAG, "保存成功");
        }

    private void do_Decrypt(String way){
        boolean isSuccess;
        isSuccess = true;
        byte[] oldByte = new byte[(int) imageAddr.length()];
        try {
            byte[] key={(byte)0x19, (byte)0x16, (byte)0x4, (byte)0x14, (byte)0x5, (byte)0x7, (byte)0x7, (byte)0x21, (byte)0xf, (byte)0x13, (byte)0x1e, (byte)0x1a, (byte)0x24, (byte)0x8, (byte)0x28, (byte)0x17};
            fis = new FileInputStream(imageAddr);
            fis.read(oldByte);
            byte[] newByte = Decode(oldByte, key, way);
            fos = new FileOutputStream(imageAddr);
            fos.write(newByte);

        } catch (FileNotFoundException e) {
            isSuccess = false;
            e.printStackTrace();
        } catch (IOException e) {
            isSuccess = false;
            e.printStackTrace();
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        try {
            fis.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isSuccess)
            XToast.success("解密成功");
        else
            XToast.error("解密失败");
    }
}
