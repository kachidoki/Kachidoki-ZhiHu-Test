package com.text.kachidoki.zhihu.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.http.RequestManager;
import com.android.http.RequestMap;
import com.google.gson.Gson;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.text.kachidoki.zhihu.R;
import com.text.kachidoki.zhihu.app.App;
import com.text.kachidoki.zhihu.app.BaseActivity;
import com.text.kachidoki.zhihu.config.API;
import com.text.kachidoki.zhihu.config.APIStatus;
import com.text.kachidoki.zhihu.model.AnswerModel;
import com.text.kachidoki.zhihu.model.OnDataCallback;
import com.text.kachidoki.zhihu.model.QuestionModel;
import com.text.kachidoki.zhihu.model.bean.AnswerInfo;
import com.text.kachidoki.zhihu.model.bean.QuestionInfo;
import com.text.kachidoki.zhihu.model.bean.Result;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Frank on 15/5/2.
 */
public class AnswerActivity extends BaseActivity {

    private TextView tv_title;
    private TextView tv_content;
    private SuperRecyclerView recycleView;
    private EditText tv_comment;
    private AnswerAdapter mAdapter;
    private String id;
    private AnswerModel mAnswerModel = new AnswerModel();
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private int page=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        setToolbar(true);

        recycleView = (SuperRecyclerView)findViewById(R.id.recyclerview);
        tv_comment = $(R.id.answer_comment);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AnswerAdapter();
        recycleView.setAdapter(mAdapter);



        recycleView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAnswerModel.getAnswersFromNet(0,id,new OnDataCallback<AnswerInfo.Answer>() {

                    @Override
                    public void callback(AnswerInfo.Answer... list) {
                        page=0;
                        recycleView.showRecycler();
                        mAdapter.answer.clear();
                        if (list != null) {
                            mAdapter.add(list);
                        }
                    }
                });
            }
        });

        recycleView.setOnMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int i, int i1, int i2) {
                mAnswerModel.getAnswersFromNet(page+1,id,new OnDataCallback<AnswerInfo.Answer>() {
                    @Override
                    public void callback(AnswerInfo.Answer... list) {
                        page++;
                        recycleView.showRecycler();
                        recycleView.hideMoreProgress();
                        if (list != null) {
                            mAdapter.add(list);
                        }
                    }
                });
            }
        });

        tv_title = $(R.id.answer_title);
        tv_content = $(R.id.answer_content);




        Intent intent = this.getIntent();
        if(intent.getStringExtra("title")!=null){
            String title = intent.getStringExtra("title");
            tv_title.setText(title);
        }
        if(intent.getStringExtra("content")!=null){
            String content = intent.getStringExtra("content");
            tv_content.setText(content);
        }
        id = intent.getStringExtra("id");


        //侧拉代码
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,getToolbar(), 0, 0);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        

        mAnswerModel.getAnswersFromNet(0,id,new OnDataCallback<AnswerInfo.Answer>() {

            @Override
            public void callback(AnswerInfo.Answer... list) {
                if (list != null) {
//                    Log.i("ZhiHuNet", String.valueOf(list.length));
                    mAdapter.add(list);
                }
            }
        });
    }

    public void submit(View v){
        final RequestMap params = new RequestMap();
        params.put("content",tv_comment.getText().toString());
        params.put("questionId",id);
        params.put("token", App.getInstance().getToken());
        RequestManager.getInstance().post(API.SendAnswers, params, new RequestManager.RequestListener() {
            @Override
            public void onRequest() {

            }

            @Override
            public void onSuccess(String response) {
                Result result = new Gson().fromJson(response, Result.class);

                if (result.getStatus() == APIStatus.Success) {
                    Toast.makeText(AnswerActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
//                    Log.i("ZhiHuNetAnswerContent",tv_comment.getText().toString());
                } else {
                    Toast.makeText(AnswerActivity.this, result.getInfo(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(AnswerActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
            }
        });

        tv_comment.setText("");

    }


    private class AnswerAdapter extends RecyclerView.Adapter<AnswerVH>{

        private ArrayList<AnswerInfo.Answer> answer = new ArrayList<>();
        public void add(AnswerInfo.Answer[] data){
            answer.addAll(Arrays.asList(data));
            notifyDataSetChanged();
        }

        @Override
        public AnswerVH onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(AnswerActivity.this).inflate(R.layout.item_answer,viewGroup,false);
            return new AnswerVH(view);
        }

        @Override
        public void onBindViewHolder(AnswerVH answerVH, int i) {
            answerVH.setData(answer.get(i));
        }


        @Override
        public int getItemCount() {
            return answer.size();
        }
    }

    class AnswerVH extends RecyclerView.ViewHolder {
        TextView tv_authorId;
        TextView tv_content;
        TextView tv_data;

        public AnswerVH(View itemView) {
            super(itemView);
            tv_authorId = (TextView)itemView.findViewById(R.id.authorId);
            tv_content = (TextView)itemView.findViewById(R.id.answer_content);
            tv_data = (TextView)itemView.findViewById(R.id.answer_data);
        }

        public void setData(AnswerInfo.Answer answer){
            Log.i("ZhiHuNet",answer.getAuthorId());
            Log.i("ZhiHuNet",answer.getContent());
//            Log.i("ZhiHuNetData",answer.getData()+"");
            tv_authorId.setText(answer.getAuthorName());
            tv_content.setText(answer.getContent());
            tv_data.setText(answer.getDate());
        }
    }


}
