package com.txwk.addemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class SkipActivity extends AppCompatActivity {

    private SkipPageHandler skipHandler;
    private TextView tv, count_tv;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skip);
        tv = findViewById(R.id.tv);
        count_tv = findViewById(R.id.count_tv);
        seekBar = findViewById(R.id.seekBar);

        count_tv.setText("倒计时为" + seekBar.getProgress() + "s");

        skipHandler = new SkipPageHandler(this, new CountdownInterface() {
            @Override
            public void getCountdown(final int count) {
                SkipActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText("倒计时：" + count + "s");
                    }
                });
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                count_tv.setText("倒计时为" + progress + "s");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                skipHandler.setSkipTime(seekBar.getProgress());
            }
        });
    }

    public void pause(View view) {
        if (skipHandler != null) {
            skipHandler.suspended();
        }
    }

    public void start(View view) {
        skipHandler.startTime();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (skipHandler != null) {
            skipHandler.setSkipTime(10);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (skipHandler != null) {
            skipHandler.suspended();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (skipHandler != null) {
            skipHandler.startTime();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (skipHandler != null) {
            skipHandler.exit();
        }
    }
}
