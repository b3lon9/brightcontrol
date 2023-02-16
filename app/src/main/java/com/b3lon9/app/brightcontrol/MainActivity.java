package com.b3lon9.app.brightcontrol;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int widthPixels = getResources().getDisplayMetrics().widthPixels;  // 1080
        int heightPixels = getResources().getDisplayMetrics().heightPixels; // 2401
        Log.d("neander", "display widthPixels : " + widthPixels);
        Log.d("neander", "display heightPixels : " + heightPixels);
        Log.d("neader", "display density : " + getResources().getDisplayMetrics().density);
        // <<< naver api contents
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = Gravity.CENTER;
        // layoutParams.width = (int)(widthPixels * 0.5);
        // layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        layoutParams.dimAmount = 0.7f;
        getWindow().setAttributes(layoutParams);
        // >>>

        setContentView(R.layout.activity_main);

        TextView label = this.findViewById(R.id.label);
        SeekBar seekBar = this.findViewById(R.id.seekbar);
         LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams1.width = (int)(widthPixels * 0.8);
        seekBar.setLayoutParams(layoutParams1);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int value = i;

                if (value < 10) {
                    value = 10;
                } else if (value > 100){
                    value = 100;
                }

                //label.setText(value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("neander", "onPause()");
        super.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("neander", "onStop() : packagename" + getPackageName());

        /*ActivityManager am = (ActivityManager)getSystemService(Activity.ACTIVITY_SERVICE);
        am.killBackgroundProcesses(getPackageName());
        android.os.Process.killProcess(android.os.Process.myPid());*/
    }
}