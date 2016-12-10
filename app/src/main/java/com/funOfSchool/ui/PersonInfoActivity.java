package com.funOfSchool.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.funOfSchool.R;

/**
 * Created by lenovo on 2016/12/10.
 */
public class PersonInfoActivity extends Activity {
    private ImageButton btnBack;
    private TextView tvName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personinfo);
        //  获得各控件
        findView();
        //  为各按钮设置监听器
        setListener();
        // 为各资料项设值
        setInfoValue();
    }
    private void findView(){
        btnBack = (ImageButton)findViewById(R.id.back);
        tvName = (TextView)findViewById(R.id.value_name);
    }
    private void setListener(){
        PersoninfoListener personinfoListener = new PersoninfoListener();
        btnBack.setOnClickListener(personinfoListener);
    }
    private void setInfoValue(){
        tvName.setText("音袁");
    }
    private class PersoninfoListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.back:
                    finish();
                    break;
            }
        }
    }
}
