package com.text.kachidoki.zhihu.launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.http.RequestManager;
import com.android.http.RequestMap;
import com.google.gson.Gson;
import com.text.kachidoki.zhihu.R;
import com.text.kachidoki.zhihu.app.BaseActivity;
import com.text.kachidoki.zhihu.config.API;
import com.text.kachidoki.zhihu.config.APIStatus;
import com.text.kachidoki.zhihu.model.bean.Result;

/**
 * Created by Frank on 15/4/27.
 */
public class RegisterActivity extends BaseActivity{
    private TextView tvName;
    private TextView tvPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setToolbar(true);
        tvName = $(R.id.name);
        tvPassword = $(R.id.password);
    }

    public void Register(View v){
        RequestMap params = new RequestMap();
        params.put("name",tvName.getText().toString());
        params.put("password",tvPassword.getText().toString());
        RequestManager.getInstance().post(API.Register,params,new RequestManager.RequestListener() {
            @Override
            public void onRequest() {

            }

            @Override
            public void onSuccess(String response) {
                Result result = new Gson().fromJson(response,Result.class);

                if (result.getStatus() == APIStatus.Success){
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(RegisterActivity.this,result.getInfo(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = new Intent();
        intent.putExtra("name", tvName.getText().toString());
        intent.putExtra("password", tvPassword.getText().toString());
        setResult(RESULT_OK,intent);
        finish();


    }
}
