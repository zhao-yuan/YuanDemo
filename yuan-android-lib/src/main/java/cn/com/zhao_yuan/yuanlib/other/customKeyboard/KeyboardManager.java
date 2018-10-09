package cn.com.zhao_yuan.yuanlib.other.customKeyboard;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import cn.com.zhao_yuan.yuanlib.other.customKeyboard.view.KeyboardView;
import cn.com.zhao_yuan.yuanlib.utils.key.KeyUtil;

/**
 * 功能描述:自定义键盘管理器
 *
 * @author zhao-yuan
 * @date 2018-08-10 15:18:52
 */
public class KeyboardManager {

    // 单例对象
    private static volatile KeyboardManager instance;
    // 绑定的activity
    private Activity mActivity;
    // 键盘的线性布局
    private LinearLayout keyLinearLayout;


    // 私有化实例
    private KeyboardManager() {

    }

    /**
     * 单例获得对象
     *
     * @return
     */
    public static KeyboardManager getInstance() {
        if (instance == null) {
            synchronized (KeyboardManager.class) {
                if (instance == null) {
                    instance = new KeyboardManager();
                }
            }
        }
        return instance;
    }


    public void addEditText(final Activity activity, final EditText editText, final int keyType, final KeyEventListener keyEventListener) {
        KeyUtil.setEditTextShowSoftInputOnFocus(editText, activity);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    KeyUtil.hideKey(activity);
                    mActivity = activity;
                    if (mActivity != null && keyLinearLayout != null) {
                        ViewGroup rootContent = mActivity.getWindow().getDecorView().findViewById(android.R.id.content);
                        rootContent.removeView(keyLinearLayout);
                    }
                    ViewGroup rootContent = activity.getWindow().getDecorView().findViewById(android.R.id.content);
                    FrameLayout.LayoutParams keyFl = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
                    LinearLayout linearLayout = new LinearLayout(activity);
                    KeyboardView nkv = new KeyboardView(activity, editText, keyType, keyEventListener, linearLayout);
                    linearLayout.addView(nkv);
                    rootContent.addView(linearLayout, keyFl);
                    keyLinearLayout = linearLayout;

                } else {
                    if (mActivity != null && keyLinearLayout != null) {
                        ViewGroup rootContent = mActivity.getWindow().getDecorView().findViewById(android.R.id.content);
                        rootContent.removeView(keyLinearLayout);
                    }
                }
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (keyLinearLayout != null) {
                    keyLinearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public interface KeyEventListener {
        void hideKey();

        void confirm();
    }


}
