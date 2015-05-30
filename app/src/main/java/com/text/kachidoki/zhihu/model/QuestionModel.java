package com.text.kachidoki.zhihu.model;

import android.widget.Toast;

import com.google.gson.Gson;
import com.text.kachidoki.zhihu.app.App;
import com.text.kachidoki.zhihu.config.API;
import com.text.kachidoki.zhihu.model.bean.QuestionInfo;
import com.text.kachidoki.zhihu.model.bean.Result;
import com.android.http.RequestManager;
import com.android.http.RequestMap;
/**
 * Created by Frank on 15/5/1.
 */
public class QuestionModel {
    private class QuestionResult extends Result{
        private QuestionInfo data;

        public QuestionInfo getData(){
            return data;
        }

        public void setData(QuestionInfo data){
            this.data = data;
        }
    }

    public void getQuestionsFromNet(int page,final OnDataCallback<QuestionInfo.Question> callback){
        RequestManager.getInstance().post(API.GetQuestions, new RequestMap("page", page + ""), new RequestManager.RequestListener() {
            @Override
            public void onRequest() {

            }

            @Override
            public void onSuccess(String response) {
                QuestionResult result = new Gson().fromJson(response,QuestionResult.class);
                callback.callback(result.getData().getQuestions());
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(App.getInstance(), "网络错误", Toast.LENGTH_SHORT);
            }
        });
    }
}
