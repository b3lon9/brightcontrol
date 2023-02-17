package com.b3lon9.app.brightcontrol;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;


public class MainActivity extends Activity {
    private String TAG = "neander";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = Gravity.CENTER;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.activity_main);

        initialize();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
        super.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() : packagename" + getPackageName());

        /*ActivityManager am = (ActivityManager)getSystemService(Activity.ACTIVITY_SERVICE);
        am.killBackgroundProcesses(getPackageName());
        android.os.Process.killProcess(android.os.Process.myPid());*/
    }



    private void initialize() {
        int widthPixels = getResources().getDisplayMetrics().widthPixels;  // 1080
        int heightPixels = getResources().getDisplayMetrics().heightPixels; // 2401
        Log.d(TAG, "display widthPixels : " + widthPixels);
        Log.d(TAG, "display heightPixels : " + heightPixels);
        Log.d(TAG, "display density : " + getResources().getDisplayMetrics().density);

        // brightness controller display width 80% on display
        SeekBar seekBar = this.findViewById(R.id.seekbar);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams1.width = (int)(widthPixels * 0.8);
        seekBar.setLayoutParams(layoutParams1);

        findViewById(R.id.testbtn).setOnClickListener(view -> {
            WindowManager.LayoutParams layout = getWindow().getAttributes();
            layout.screenBrightness = 1F;
            getWindow().setAttributes(layout);
        });
    }

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}