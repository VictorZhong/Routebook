package me.routebook.routebook.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by Victor on 2017/6/27.
 */

public abstract class BaseActivity extends SimpleActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initViews();
    }

    protected abstract int getContentView();

    protected abstract void initViews();

    protected <T extends View> T findView(int id){
        return (T) findViewById(id);
    }


}
