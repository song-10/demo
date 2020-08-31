package com.example.sm4forandroid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.R;

/**
 * Description: 可输入的line item
 */
public class EditLineItem extends LinearLayout {

    private String title;
    private String placeholder;
    private TextView mTitle;
    private EditText mContent;
    private int inputType;
    private Drawable rightimage;


    public EditLineItem(Context context) {
        this(context, null);
    }

    public EditLineItem(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public EditLineItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.EditLineItemBB);

        title = a.getString(R.styleable.EditLineItemBB_bc_title);
        placeholder = a.getString(R.styleable.EditLineItemBB_bc_placeholder);
        rightimage =  a.getDrawable(R.styleable.EditLineItemBB_bc_rightimage);
        inputType = a.getInt(R.styleable.EditLineItemBB_bc_input_type, InputType.TYPE_CLASS_TEXT);
        initLayout(context);
    }

    private void initLayout(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.edit_line_item, this);
        mTitle = (TextView) view.findViewById(R.id.title);
        mContent = (EditText) view.findViewById(R.id.content);

        mTitle.setText(title);
        mContent.setHint(placeholder);
        mContent.setInputType(inputType);
        //改变默认的单行模式
        mContent.setSingleLine(false);
        //水平滚动设置为False
        mContent.setHorizontallyScrolling(false);

        if (rightimage!=null){
            rightimage.setBounds(0, 0, 32, 32);
            mTitle.setCompoundDrawables(rightimage,null,null,null);
        }
    }


    public void setTitle(String s) {
        mTitle.setText(s);
    }

    public String getContent() {
        return mContent.getText().toString();
    }

    /**
     * 更新编辑的内容
     * @param content
     */
    public void setContent(String content) {
        this.mContent.setText(content);
    }

    public TextView getTextView() {
        return mContent;
    }

}
