 package com.text.kachidoki.zhihu.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.text.kachidoki.zhihu.R;

/**
 * Created by Frank on 15/4/27.
 */
public class BaseActivity extends AppCompatActivity {
    private Toolbar toolbar;
    public Toolbar getToolbar() {
        return toolbar;
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    protected <T extends View> T $(int R){
        return (T)findViewById(R);
    }

    protected void setToolbar(boolean returnable){
        toolbar = $(R.id.toolbar);
        if(toolbar!=null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(returnable);
        }
    }


}
