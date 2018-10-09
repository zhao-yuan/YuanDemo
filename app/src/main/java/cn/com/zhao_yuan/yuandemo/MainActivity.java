package cn.com.zhao_yuan.yuandemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.com.zhao_yuan.yuanlib.utils.log.LogUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.init(true,0,false);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_key).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_key:
                startActivity(new Intent(this, KeyActivity.class));
                break;
        }

    }
}
