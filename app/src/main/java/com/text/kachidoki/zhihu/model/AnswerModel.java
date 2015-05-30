package com.text.kachidoki.zhihu.model;

import android.util.Log;
import android.widget.Toast;

import com.android.http.RequestManager;
import com.android.http.RequestMap;
import com.google.gson.Gson;
import com.text.kachidoki.zhihu.app.App;
import com.text.kachidoki.zhihu.config.API;
import com.text.kachidoki.zhihu.model.bean.AnswerInfo;
import com.text.kachidoki.zhihu.model.bean.QuestionInfo;
import com.text.kachidoki.zhihu.model.bean.Result;

/**
 * Created by Frank on 15/5/2.
 */
public class AnswerModel {
    private class AnswerResult extends Result {
        private AnswerInfo data;


        public AnswerInfo getData() {
            return data;
        }

        public void setData(AnswerInfo data) {
            this.data = data;
        }
    }

    public void getAnswersFromNet(int page,String id,final OnDataCallback<AnswerInfo.Answer> callback){
        RequestMap params = new RequestMap();
        params.put("page",page+"");
        params.put("questionId",id);
        params.put("count","");
        RequestManager.getInstance().post(API.GetAnswers, params, new RequestManager.RequestListener() {
            @Override
            public void onRequest() {

            }

            @Override
            public void onSuccess(String response) {
                AnswerResult result = new Gson().fromJson(response, AnswerResult.class);
//                Log.i("ZhiHuNetAnswer", String.valueOf(result.getData().getAnswers().length));
                callback.callback(result.getData().getAnswers());
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(App.getInstance(), "网络错误", Toast.LENGTH_SHORT);
            }
        });
    }
}
