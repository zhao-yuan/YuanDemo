package cn.com.zhao_yuan.yuandemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import cn.com.zhao_yuan.yuanlib.other.customKeyboard.KeyboardManager;

public class KeyActivity extends Activity {

    EditText edt_1;
    EditText edt_2;
    EditText edt_3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key);
        edt_1 = findViewById(R.id.edt_1);
        edt_2 = findViewById(R.id.edt_2);
        edt_3 = findViewById(R.id.edt_3);
        KeyboardManager.getInstance().addEditText(this, edt_1, 1, null);
        KeyboardManager.getInstance().addEditText(this, edt_2, 2, null);
        KeyboardManager.getInstance().addEditText(this, edt_3, 3, null);
    }
}
