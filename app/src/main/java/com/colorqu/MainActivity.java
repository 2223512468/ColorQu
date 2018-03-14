package com.colorqu;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.widget.Button;


public class MainActivity extends Activity {

    private ColorPickerView colorDisk = null;
    private Button btnColor = null;
    public static boolean flagOfColorChange = false;
    private final static int COLOR_CHANGE = 1;
    Handler mColorhandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case COLOR_CHANGE:
                    btnColor.setTextColor(ColorPickerView.ColorText);
                    break;

                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnColor = (Button) findViewById(R.id.btnColor);

        //用线程监听 是否颜色已经改变
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (true) {
                    //当色盘颜色改变时，也就是松手时，把flagOfColorChange置为true
                    //然后handler 发送消息，button改变字体

                    //此变量为全局变量，破坏了封装性。但是实现了功能，有更好的方式可以留言
                    if (flagOfColorChange) {

                        System.out.println("color change!!!");
                        flagOfColorChange = false;
                        mColorhandler.sendEmptyMessage(COLOR_CHANGE);
                    }
                }

            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
