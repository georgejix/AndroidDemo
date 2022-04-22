package com.jx.androiddemo.testactivity.function.f11to20.f13;

import android.annotation.SuppressLint;
import android.os.SystemClock;
import android.util.Log;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.empty.EmptyContract;
import com.jx.androiddemo.testactivity.empty.EmptyPresenter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F13Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    private final String TAG = "ThreadActivity";

    private MyThread thread1, thread2;

    private MyRunnable myRunnable1;

    private Thread thread3;

    private CountDownLatch countDownLatch;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f13;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
        //test1();
        //test2();
        //test3();
        //test4();
        //test5();
        test6();
    }

    @SuppressLint("CheckResult")
    private void initListener() {
        //延时方法
        Observable.timer(50, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {

                });

        //点击
        /*RxView.clicks(null)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                });*/
    }

    private void test6() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 5,
                10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new JobThreadFactory());
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                SystemClock.sleep(1000);
                executor.execute(() -> Log.d(TAG, "" + System.currentTimeMillis()));
            }
        }).start();

    }

    private static class JobThreadFactory implements ThreadFactory {
        private int counter = 0;

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "android_" + counter++);
        }
    }

    private void test5() {
        final Thread2 thread1 = new Thread2();
        thread1.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                thread1.interrupt();
            }
        }).start();
    }

    class Thread2 extends Thread {
        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                Log.d(TAG, "Thread2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    private void test4() {
        //wait notify
        final Object lock = new Object();
        final Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    Log.d(TAG, "thread1 in");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "thread1 out");
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    Log.d(TAG, "thread2 in , thread1.state=" + thread1.getState().toString());
                    lock.notifyAll();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "thread2 out");

                }
            }
        });
        thread1.start();
        thread2.start();
    }

    private void test3() {
        //test blocked
        final Object lock = new Object();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    while (true) {

                    }
                }
            }
        }).start();
        final Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    Log.d(TAG, "1111111111");
                }
            }
        });
        thread1.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, thread1.getState().toString());
            }
        }).start();
    }

    private void test2() {
        countDownLatch = new CountDownLatch(1);
        thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatch.await();
                    Log.d(TAG, "wait for countdownlatch");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Log.d(TAG, thread3.getState().toString());
        thread3.start();
        Log.d(TAG, thread3.getState().toString());
        countDownLatch.countDown();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, thread3.getState().toString());
            }
        }).start();
    }

    private void test1() {
        Log.d(TAG, "MainThread:" + Thread.currentThread().hashCode());
        thread1 = new MyThread();
        thread1.start();

        thread2 = new MyThread();
        thread2.run();

        myRunnable1 = new MyRunnable();
        myRunnable1.run();
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            Log.d(TAG, "MyThread:" + Thread.currentThread().hashCode());
            new Thread__().run();
        }
    }

    class Thread__ extends Thread {
        @Override
        public void run() {
            Log.d(TAG, "Thread__:" + Thread.currentThread().hashCode());
        }
    }

    class MyRunnable implements Runnable {

        @Override
        public void run() {
            Log.d(TAG, "MyRunnable:" + Thread.currentThread().hashCode());
        }
    }
}