package com.text.kachidoki.zhihu.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.text.kachidoki.zhihu.model.bean.Result;

/**
 * Created by Frank on 15/5/2.
 */
public class SendQuestionActivity extends BaseActivity {
    TextView tv_title;
    TextView tv_content;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendquestion);
        setToolbar(true);
        tv_title=$(R.id.send_title);
        tv_content=$(R.id.send_content);


    }

    public void send(View v){
        RequestMap params = new RequestMap();
        params.put("title",tv_title.getText().toString());
        params.put("content",tv_content.getText().toString());
        params.put("token", App.getInstance().getToken());
        RequestManager.getInstance().post(API.SendQuestions,params,new RequestManager.RequestListener() {
            @Override
            public void onRequest() {

            }

            @Override
            public void onSuccess(String response) {
                Result result = new Gson().fromJson(response,Result.class);

                if (result.getStatus() == APIStatus.Success){
                    Toast.makeText(SendQuestionActivity.this, "发布成功", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(SendQuestionActivity.this,result.getInfo(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(SendQuestionActivity.this,"发布失败",Toast.LENGTH_SHORT).show();
            }
        });

        finish();
    }
}
