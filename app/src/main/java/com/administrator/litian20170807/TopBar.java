package com.administrator.litian20170807;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/8/7.
 */

public class TopBar extends RelativeLayout {

    private String mLeftText;
    private String mRightText;
    private String mTitle;


    private int mLeftTextColor;
    private int mRightTextColor;
    private int mTitleColor;
    private Drawable mLeftDrawable;
    private Drawable mRightDrawable;
    private float mTitleSize;

    private Button mLeftButton;
    private Button mRightButton;
    private TextView mTitleView;
    private LayoutParams mLeftParams;
    private LayoutParams mRightParams;
    private LayoutParams mTitleParams;

    private TopBarClickListener mTopBarClickListener;

    private static final int BUTTON_LEFT = 0;
    public static final int BUTTON_RIGHT = 1;

    public TopBar(Context context) {
        super(context, null);
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (attrs != null) {

            //通过这个方法，将你在attrs.xml中定义的declare-styleable的所有属性存储到TypedArray中。
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopBar);

            /**
             * 左侧按钮的属性
             */
            mLeftText =  ta.getString(R.styleable.TopBar_leftText);
            mLeftTextColor = ta.getColor(R.styleable.TopBar_leftTextColor, Color.BLACK);
            mLeftDrawable = ta.getDrawable(R.styleable.TopBar_leftBackground);


            /**
             * 右侧按钮的属性
             */
            mRightText = ta.getString(R.styleable.TopBar_rightText);
            mRightTextColor = ta.getColor(R.styleable.TopBar_rightTextColor, Color.BLACK);
            mRightDrawable = ta.getDrawable(R.styleable.TopBar_rightBackground);


            /**
             * 标题的属性
             */
            mTitle = ta.getString(R.styleable.TopBar_title1);
            mTitleColor = ta.getColor(R.styleable.TopBar_titleTextColor1,Color.BLACK);
            mTitleSize = ta.getDimension(R.styleable.TopBar_titleTextSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));


            ta.recycle();
        }


        initView(context);

    }


    public void setTopBarClickListener(TopBarClickListener clickListener) {
        mTopBarClickListener = clickListener;

    }

    /**
     * 初始化视图
     */
    private void initView(Context context) {
        mLeftButton = new Button(context);
        mRightButton = new Button(context);
        mTitleView = new TextView(context);

        mLeftButton.setTextColor(mLeftTextColor);
        mLeftButton.setText(mLeftText);
        mLeftButton.setBackground(mLeftDrawable);

        mRightButton.setText(mRightText);
        mRightButton.setTextColor(mRightTextColor);
        mRightButton.setBackground(mRightDrawable);

        mTitleView.setText(mTitle);
        mTitleView.setTextColor(mTitleColor);
        mTitleView.setTextSize(mTitleSize);
        mTitleView.setGravity(Gravity.CENTER);

        //为组件元素设置相应的布局元素

        mLeftParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mLeftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(mLeftButton, mLeftParams);


        mRightParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mRightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(mRightButton, mRightParams);

        mTitleParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mTitleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(mTitleView, mTitleParams);

        mLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTopBarClickListener != null) {
                    mTopBarClickListener.leftClick();
                }
            }
        });

        mRightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTopBarClickListener != null) {
                    mTopBarClickListener.rightClick();
                }
            }
        });

    }


    /**
     * 设置按钮是否显示
     * @param id
     * @param flag
     */
    private void setButtonVisable(int id,boolean flag){
        if(flag){
            if(id==BUTTON_LEFT){
                mLeftButton.setVisibility(View.VISIBLE);
            }else{
                mRightButton.setVisibility(View.VISIBLE);
            }
        }else{
            if(id==BUTTON_LEFT){
                mLeftButton.setVisibility(View.GONE);
            }else{
                mRightButton.setVisibility(View.GONE);
            }
        }
    }


    public interface TopBarClickListener {

        //左按钮点击回掉
        void leftClick();

        //右按钮点击回调
        void rightClick();

    }
}
