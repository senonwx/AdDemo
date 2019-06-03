package com.txwk.addemo;

import android.content.Context;
import android.content.Intent;

/**
 * 10s不操作 自动跳转
 */
public class SkipPageHandler {

    private Context context;
    private CountdownInterface countdownInterface;
    private Thread thread;
    private boolean isStart = true;//是否开始
    private int settingTime = 10;
    private Class clazz = SkipPageActivity.class;//默认跳转页面
    private int timing = settingTime;//默认10s


    public SkipPageHandler(Context context, CountdownInterface countdownInterface) {
        this.context = context;
        this.countdownInterface = countdownInterface;
    }

    //计时器开始
    public void startTime() {
        if (thread == null) {
            isStart = true;
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (isStart) {
                            if (countdownInterface != null) {
                                countdownInterface.getCountdown(timing);
                            }
                            thread.sleep(1000);
                            timing--;
                            if (timing <= 0) {
                                timing = settingTime;
                                isStart = false;
                                thread = null;
                                jump();
                            }
                            LogUtils.e("timing= " + timing);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }

    //开始跳转
    public void jump() {
        Intent intent = new Intent(context, clazz);
        //创建新栈并加入
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //设置跳转时间
    public void setSkipTime(int time) {
        this.settingTime = time;
        this.timing = settingTime;
        LogUtils.e("settingTime= " + time);
    }

    //设置跳转时间
    public void setSkipActivity(Class clazz) {
        this.clazz = clazz;
        LogUtils.e("setSkipActivity= " + clazz);
    }

    //结束释放activity避免类存泄露
    public void exit() {
        thread = null;
        context = null;
        LogUtils.e("exit  ");
    }

    //暂停
    public void suspended() {
        timing = settingTime;
        isStart = false;
        thread = null;
        LogUtils.e("suspended ");
    }


}
