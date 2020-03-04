package com.example.sm4forandroid.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.R;

//弹窗，对话框(文件加解密)

public class SingleChooseDialog {


    public static  void show(Context context, String[] s, final OnDialogItemClickListener mListener){

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_choose_dialog, null);
        final Dialog dialog = new Dialog(context, R.style.Theme_Transparent);

        final ArrayAdapter<String> mAdapter=new ArrayAdapter<String>(context,R.layout.my_simple_list_item,s);

        ListView listView= (ListView) view.findViewById(R.id.list);
        listView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onItemClick(mAdapter.getItem(position),position);
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);

        dialog.show();

    }

    public interface OnDialogItemClickListener {
        void onItemClick(String value, int position);
    }
}
