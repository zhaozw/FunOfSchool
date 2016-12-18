package com.funOfSchool.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.funOfSchool.R;
import com.funOfSchool.util.AppUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivityTemp extends AppCompatActivity {
    private Button login;
    private EditText loginName;
    private EditText passWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_temp);
        /**
         * 临时调试聊天功能，测试专用！！！
         * 临时调试聊天功能，测试专用！！！
         * 临时调试聊天功能，测试专用！！！
         * 临时调试聊天功能，测试专用！！！
         * 临时调试聊天功能，测试专用！！！
         */
        login = (Button) findViewById(R.id.login);
        loginName = (EditText) findViewById(R.id.loginName);
        passWord = (EditText) findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在登录");
        progressDialog.show();


        // 根据学校名称，获得所选学校的经纬度和ID
        AsyncHttpClient client = new AsyncHttpClient();
        String url = AppUtils.HOST + "api/account/login";
        // 请求参数：学校名称
        RequestParams param = new RequestParams();
        param.put("loginName",loginName.getText().toString().trim());
        param.put("password",passWord.getText().toString().trim());
        // 发送网络请求
        client.post(url, param, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt("code") == 1) {
                        AppUtils.token = response.getString("token");
                        //聊天登录
                        EMClient.getInstance().login(response.getJSONObject("info").getString("userId"), passWord.getText().toString().trim(), new EMCallBack() {

                            @Override
                            public void onSuccess() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (progressDialog != null && progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                        }
                                    }
                                });

                                startActivity(new Intent(LoginActivityTemp.this, ConversationListActivity.class));
                            }

                            @Override
                            public void onError(int i, String s) {
                                Log.i("tag", "登录失败" + s);
                                progressDialog.dismiss();
                                AppUtils.showShort(getApplicationContext(),"登录失败");
                            }

                            @Override
                            public void onProgress(int i, String s) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (LoginActivityTemp.this.isFinishing() || LoginActivityTemp.this.isDestroyed()) {
                                            return;
                                        }
                                        progressDialog.setMessage("正在登录");
                                    }
                                });
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}