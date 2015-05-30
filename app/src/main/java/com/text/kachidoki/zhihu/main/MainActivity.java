package com.text.kachidoki.zhihu.main;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.view.jpagerview.JPagerAdapter;
import com.jude.view.jpagerview.JPagerView;
import com.jude.view.jpagerview.JStatePagerAdapter;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.text.kachidoki.zhihu.R;
import com.text.kachidoki.zhihu.app.App;
import com.text.kachidoki.zhihu.app.BaseActivity;
import com.text.kachidoki.zhihu.launch.LoginActivity;
import com.text.kachidoki.zhihu.model.OnDataCallback;
import com.text.kachidoki.zhihu.model.QuestionModel;
import com.text.kachidoki.zhihu.model.bean.QuestionInfo;
import com.text.kachidoki.zhihu.widget.BaseViewHolder;
import com.text.kachidoki.zhihu.widget.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends BaseActivity {

    private SuperRecyclerView recycleView;
//    private QuestionAdapter mAdapter;
    private QuestionAdapter2 mAdapter2;
    private QuestionModel mQuestionModel = new QuestionModel();
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private int page=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar(true);
        recycleView = (SuperRecyclerView)findViewById(R.id.recyclerview);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
//        mAdapter = new QuestionAdapter();
        mAdapter2 = new QuestionAdapter2(this);
//        recycleView.setAdapter(mAdapter);
        recycleView.setAdapter(mAdapter2);


        recycleView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mQuestionModel.getQuestionsFromNet(0, new OnDataCallback<QuestionInfo.Question>() {
                    @Override
                    public void callback(QuestionInfo.Question... list) {
                        page = 0;
                        recycleView.showRecycler();
                        mAdapter2.clear();
                        mAdapter2.addAll(list);
//                        mAdapter.question.clear();
//                        mAdapter.add(list);
                    }
                });

            }

        });

        recycleView.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int i, int i1, int i2) {
                mQuestionModel.getQuestionsFromNet(page + 1, new OnDataCallback<QuestionInfo.Question>() {
                    @Override
                    public void callback(QuestionInfo.Question... list) {
                        page++;
                        recycleView.showRecycler();
                        recycleView.hideMoreProgress();
                        if (list != null) {
//                            mAdapter.add(list);
                            mAdapter2.addAll(list);
                        }
                    }

                });
            }
        }, 10);

        mAdapter2.addHeader(new QuestionHeader());

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


        mQuestionModel.getQuestionsFromNet(0, new OnDataCallback<QuestionInfo.Question>() {
            @Override
            public void callback(QuestionInfo.Question... list) {

//                mAdapter.add(list);
                mAdapter2.addAll(list);
            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(App.getInstance().getToken()==""){
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }else {
            getMenuInflater().inflate(R.menu.menu_login, menu);
        }



        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        if (mDrawerToggle.onOptionsItemSelected(item)){
//            return true;
//        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivityForResult(new Intent(this, LoginActivity.class),10086);
            return true;
        }
        if(id == R.id.action_send){
            startActivityForResult(new Intent(this, SendQuestionActivity.class),10088);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //优化的Adapter
    class QuestionAdapter2 extends RecyclerArrayAdapter<QuestionInfo.Question>{

        public QuestionAdapter2(Context context){
            super(context);
        }
        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new QuestionVH2(parent);
        }

        @Override
        public void OnBindViewHolder(BaseViewHolder holder, int position) {
            holder.setData(getItem(position));
        }
    }
    //优化的ViewHolder
    class QuestionVH2 extends BaseViewHolder<QuestionInfo.Question>{
        TextView tv_title;
        TextView tv_content;
        TextView tv_data;
        TextView tv_userName;
        public QuestionVH2(ViewGroup parent) {
            super(parent, R.layout.item_question);
            tv_title=(TextView) itemView.findViewById(R.id.title);
            tv_content=(TextView) itemView.findViewById(R.id.content);
            tv_data = (TextView) itemView.findViewById(R.id.data);
            tv_userName = (TextView) itemView.findViewById(R.id.user_name);
        }

        @Override
        public void setData(final QuestionInfo.Question question) {
            tv_title.setText(question.getTitle());
            tv_content.setText(question.getContent());
            tv_data.setText(question.getDate());
            tv_userName.setText(question.getAuthorName());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AnswerActivity.class);
                    intent.putExtra("title", question.getTitle());
                    intent.putExtra("content", question.getContent());
                    intent.putExtra("id", question.getId());
                    startActivity(intent);
                }
            });
        }
    }


//    //原始的RecyclerViewAdapter用法
//    private class QuestionAdapter extends RecyclerView.Adapter<QuestionVH>{
//
//        private ArrayList<QuestionInfo.Question> question = new ArrayList<>();
//        public void add(QuestionInfo.Question[] data){
//            question.addAll(Arrays.asList(data));
//            notifyDataSetChanged();
//        }
//
//
//
//        @Override
//        public QuestionVH onCreateViewHolder(ViewGroup viewGroup, int i) {
//            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_question,viewGroup,false);
//            return new QuestionVH(view);
//        }
//
//
//
//        @Override
//        public void onBindViewHolder(QuestionVH questionVH, int i) {
//            questionVH.setData(question.get(i));
//        }
//
//        @Override
//        public int getItemCount() {
//            return question.size();
//        }
//    }
//
//
//    //原始的RecyclerView的ViewHolder用法
//    class QuestionVH extends RecyclerView.ViewHolder{
//        TextView tv_title;
//        TextView tv_content;
//        TextView tv_data;
//        public QuestionVH(View itemView) {
//            super(itemView);
//            tv_title=(TextView) itemView.findViewById(R.id.title);
//            tv_content=(TextView) itemView.findViewById(R.id.content);
//            tv_data = (TextView) itemView.findViewById(R.id.data);
//
//        }
//        public void setData(final QuestionInfo.Question question){
//            tv_title.setText(question.getTitle());
//            tv_content.setText(question.getContent());
//            tv_data.setText(question.getDate());
//
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(MainActivity.this,AnswerActivity.class);
//                    intent.putExtra("title", question.getTitle());
//                    intent.putExtra("content", question.getContent());
//                    intent.putExtra("id",question.getId());
//                    startActivity(intent);
//                }
//            });
//        }
//    }

    class QuestionHeader implements RecyclerArrayAdapter.HeaderView{
        private JPagerView jPagerView;

        @Override
        public View onCreateView(ViewGroup parent) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.header_question,parent,false);
            jPagerView = (JPagerView)view.findViewById(R.id.jpagerview);
            jPagerView.setAdapter(new MyBinearAdapter());
            return view;
        }

        @Override
        public void onBindView(View headerView) {

        }
    }

    class MyBinearAdapter extends JStatePagerAdapter {
        int[] res ={
                R.drawable.test1,
                R.drawable.test2,
                R.drawable.test3,
                R.drawable.test4,
                R.drawable.test5
        };

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView img = new ImageView(MainActivity.this);
            //图片拉伸
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //设置图片长宽
            img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
            img.setImageResource(res[position]);
            return img;
        }

        @Override
        public int getCount() {
            return res.length;
        }
    }


    public void exitLogin(View v){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);

    }
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState)
//    {
//        super.onPostCreate(savedInstanceState);
//        // Sync the toggle state after onRestoreInstanceState has occurred.
//        mDrawerToggle.syncState();
//
//
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig)
//    {
//        super.onConfigurationChanged(newConfig);
//        mDrawerToggle.onConfigurationChanged(newConfig);
//
//    }

}
