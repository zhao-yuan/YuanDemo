package cn.com.zhao_yuan.yuanlib.other.customKeyboard.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.util.List;

import cn.com.zhao_yuan.yuanlib.R;
import cn.com.zhao_yuan.yuanlib.other.customKeyboard.KeyboardManager;

public class KeyboardView extends android.inputmethodservice.KeyboardView {

    // 键盘类型-纯数字键盘
    public static final int KEY_TYPE_NUMBER = 1;
    // 带两位小数点的金额键盘
    public static final int KEY_TYPE_NUMBER_MONKEY = 2;
    // 带有X的身份证键盘
    public static final int KEY_TYPE_ID = 3;

    // EditText
    private EditText editText;
    // 上层事件
    private KeyboardManager.KeyEventListener keyEventListener;
    // 键盘所在的LinearLayout
    private LinearLayout linearLayout;
    // 上下文
    private Context context;
    // 键盘类型
    private int keyType;

    public KeyboardView(Context context, EditText editText, int keyType, KeyboardManager.KeyEventListener keyEventListener, LinearLayout linearLayout) {
        super(context, null);
        this.editText = editText;
        this.keyEventListener = keyEventListener;
        this.linearLayout = linearLayout;
        this.context = context;
        this.keyType = keyType;
        setKeyboard(new Keyboard(context, R.xml.zy_number_keyborad));
        setOnKeyboardActionListener(new NumberOnKeyboardActionListener());
        setPreviewEnabled(false);
    }

    /**
     * 重新画一些按键
     */
    @Override
    public void onDraw(Canvas canvas) {

        Keyboard keyboard = getKeyboard();
        List<Keyboard.Key> keys = null;
        if (keyboard != null) {
            keys = keyboard.getKeys();
        }
        if (keys == null) {
            return;
        }
        for (Keyboard.Key key : keys) {
            if (key.codes[0] == 46) {
                if (keyType == KEY_TYPE_NUMBER) {
                    key.label = "";
                    key.codes[0] = 0;
                } else if (keyType == KEY_TYPE_NUMBER_MONKEY) {
                    key.label = ".";
                } else if (keyType == KEY_TYPE_ID) {
                    key.label = "X";
                    key.codes[0] = 88;
                }

            }
            String label = key.label + "";
            Drawable bgDb2;
            if (key.codes[0] == keyboard.KEYCODE_DONE) {
                bgDb2 = context.getResources().getDrawable(R.drawable.bg_jianpan_queding);
            } else {
                bgDb2 = context.getResources().getDrawable(R.drawable.bg_keyboardview);
            }
            int[] drawableState1 = key.getCurrentDrawableState();
            bgDb2.setState(drawableState1);
            bgDb2.setBounds(key.x, key.y, key.x + key.width, key.y
                    + key.height);
            bgDb2.draw(canvas);

            // 绘制图标
            if (key.icon != null) {
                key.icon.setBounds(key.x + (key.width - key.icon.getIntrinsicWidth()) / 2, key.y + (key.height - key.icon.getIntrinsicHeight()) / 2,
                        key.x + (key.width - key.icon.getIntrinsicWidth()) / 2 + key.icon.getIntrinsicWidth(), key.y + (key.height - key.icon.getIntrinsicHeight()) / 2 + key.icon.getIntrinsicHeight());
                key.icon.draw(canvas);
            }

            if (!TextUtils.isEmpty(key.label)) {
                int labelTextSize = 0;
                Field field;
                try {
                    field = android.inputmethodservice.KeyboardView.class.getDeclaredField("mLabelTextSize");
                    field.setAccessible(true);
                    labelTextSize = (int) field.get(this);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                Rect bounds = new Rect();
                Paint paint = new Paint();
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setAntiAlias(true);
                paint.setColor(Color.BLACK);
                paint.setTextSize(labelTextSize);
                paint.setTypeface(Typeface.DEFAULT);
                paint.getTextBounds(label, 0, label.length(), bounds);
                canvas.drawText(label, key.x + (key.width / 2), (key.y + key.height / 2) + bounds.height() / 2, paint);
            }


        }
    }

    public class NumberOnKeyboardActionListener implements KeyboardView.OnKeyboardActionListener {


        @Override
        public void onPress(int i) {

        }

        @Override
        public void onRelease(int i) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = editText.getText();
            int start = editText.getSelectionStart();
            if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            } else if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 隐藏键盘
                linearLayout.setVisibility(GONE);
                if (keyEventListener != null) {
                    keyEventListener.hideKey();
                }
            } else if (primaryCode == Keyboard.KEYCODE_DONE) {// 隐藏键盘
                linearLayout.setVisibility(GONE);
                if (keyEventListener != null) {
                    keyEventListener.confirm();
                }
            } else {
                String str = editText.getText() + "";
                int index = str.indexOf(".");

                if (index != -1 && primaryCode == 46) {


                } else {
                    if (primaryCode == 46 && start == 0) {
                        editable.insert(start, "0");
                        editable.insert(start + 1, Character.toString((char) primaryCode));
                    } else if (index >= 0 && str.length() - index >= 3 && start > index) {
                    } else if (start == 1 && "0".equals(str.substring(0, 1)) && primaryCode != 46) {
                        editable.delete(0, 1);
                        editable.insert(0, Character.toString((char) primaryCode));
                    } else {
                        editable.insert(start, Character.toString((char) primaryCode));
                    }

                }
            }
        }

        @Override
        public void onText(CharSequence charSequence) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    }
}
