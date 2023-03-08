package com.b3lon9.app.brightcontrol;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.appsearch.observer.ObserverCallback;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import java.util.TimerTask;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


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
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this)) {
                // Enable write Permission
                Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            } else {
                Log.d((TAG), "권한 설정");
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
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

    /*lifeCycle ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒*/

    @SuppressLint("CheckResult")
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

        try {
            seekBar.setProgress(Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS));
        } catch (Settings.SettingNotFoundException e) {
            throw new RuntimeException(e);
        }

        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);

        // Button
        findViewById(R.id.btn_dec).setOnClickListener((view) -> {
            changeBrightNess(1);
            seekBar.setProgress(1);
        });

        findViewById(R.id.btn_auto).setOnClickListener((view) -> {

            Observable.fromCallable(() -> Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(value -> {
                        int c = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
                        int count = 0;
                        while (true) {
                            count += 50;
                            Thread.sleep(count);
                            Log.d(TAG, String.format("count : %d", count));
                            Log.d(TAG, "bright level : " + Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS));

                            if (c != Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS)) {
                                break;
                            }
                        }
                    });
        });

        findViewById(R.id.btn_inc).setOnClickListener((view) -> {
            changeBrightNess(255);
            seekBar.setProgress(255);
        });
    }



    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if (i > 0 && i <= 255) {
                Log.d(TAG, "change Brightness value : " + i);
                changeBrightNess(i);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void changeBrightNess(int level) {
        Settings.System.putInt(
                getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS,
                level
        );
    }
}