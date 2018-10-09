package cn.com.zhao_yuan.yuanlib.utils.key;

import android.app.Activity;
import android.content.Context;
import android.text.InputType;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 功能描述: 键盘工具类
 *
 * @author zhao-yuan
 * @date 2018-10-08 11:46:24
 */
public class KeyUtil {

    /**
     * 强制隐藏系统键盘
     *
     * @param activity
     */
    public static void hideKey(Activity activity) {
        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }

    /**
     * 通过反射来使edittext获取焦点时，不弹出键盘
     *
     * @param mEt
     * @param activity
     * @see https://blog.csdn.net/u010623149/article/details/55209785
     */
    public static void setEditTextShowSoftInputOnFocus(EditText mEt, Activity activity) {
        if (mEt == null || activity == null) {
            return;
        }
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16) {
            // 4.2
            methodName = "setShowSoftInputOnFocus";
        } else if (currentVersion >= 14) {
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }
        if (methodName == null) {
            mEt.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(mEt, false);
            } catch (NoSuchMethodException e) {
                mEt.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }


}
