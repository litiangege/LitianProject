package com.administrator.litian20170807;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TopBar.TopBarClickListener {
    private TopBar mTopBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.widget_topbar);

       mTopBar = ((TopBar) findViewById(R.id.top_bar));
        mTopBar.setTopBarClickListener(this);

    }

    @Override
    public void leftClick() {
        Toast.makeText(getApplicationContext(),"点击返回",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void rightClick() {
        Toast.makeText(getApplicationContext(),"点击了解更多",Toast.LENGTH_SHORT).show();
    }




}
