package com.text.kachidoki.zhihu.launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.http.RequestManager;
import com.android.http.RequestMap;
import com.google.gson.Gson;
import com.text.kachidoki.zhihu.R;
import com.text.kachidoki.zhihu.app.App;
import com.text.kachidoki.zhihu.app.BaseActivity;
import com.text.kachidoki.zhihu.config.API;
import com.text.kachidoki.zhihu.config.APIStatus;
import com.text.kachidoki.zhihu.model.bean.LoginInfo;
import com.text.kachidoki.zhihu.model.bean.Result;

/**
 * Created by Frank on 15/5/1.
 */
public class LoginActivity extends BaseActivity {

    private TextView tvName;
    private TextView tvPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        setToolbar(true);
        tvName = $(R.id.login_name);
        tvPassword = $(R.id.login_password);

        Intent intent = this.getIntent();
        if(intent.getStringExtra("name")!=null){
            String name = intent.getStringExtra("name");
            tvName.setText(name);
        }
        if(intent.getStringExtra("password")!=null){
            String password = intent.getStringExtra("password");
            tvPassword.setText(password);
        }

    }

    public void register(View view){
        startActivityForResult(new Intent(this, RegisterActivity.class),10086);
    }


    class LoginResult extends Result{
        private LoginInfo data;

        public LoginInfo getData() {
            return data;
        }

        public void setData(LoginInfo data) {
            this.data = data;
        }
    }

    public void Login(View v){
        RequestMap params = new RequestMap();
        params.put("name",tvName.getText().toString());
        params.put("password",tvPassword.getText().toString());
        RequestManager.getInstance().post(API.Login, params, new RequestManager.RequestListener() {
            @Override
            public void onRequest() {

            }

            @Override
            public void onSuccess(String response) {
                LoginResult result = new Gson().fromJson(response, LoginResult.class);
                if (result.getStatus() == APIStatus.Success) {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    App.getInstance().save(result.getData().getToken(),result.getData().getName());
//                    Log.i("ZhiHuNet", result.getData().getToken());
//                    Log.i("ZhiHuNet",App.getInstance().getToken());
//                    Log.i("ZhiHuNet",result.getData().getName()+"     "+result.getData().getPassword());
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, result.getInfo(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == 10086 && resultCode == RESULT_OK){

            if(intent.getStringExtra("name")!=null){
                String name = intent.getStringExtra("name");
                tvName.setText(name);
            }
            if(intent.getStringExtra("password")!=null){
                String password = intent.getStringExtra("password");
                tvPassword.setText(password);
            }
        }
    }
}
