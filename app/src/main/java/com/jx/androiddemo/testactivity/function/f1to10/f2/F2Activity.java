package com.jx.androiddemo.testactivity.function.f1to10.f2;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jx.androiddemo.BaseApplication;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.aidl.Book;
import com.jx.androiddemo.aidl.BookManagerService;
import com.jx.androiddemo.aidl.IBookManager;
import com.jx.androiddemo.aidl.IOnNewBookArrivedListener;
import com.jx.androiddemo.aidl.MessengerService;
import com.jx.arch.util.QMLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import butterknife.BindView;

public class F2Activity extends BaseMvpActivity<F2Presenter> implements F2Contract.View {
    private final String TAG = "F2Activity";

    @BindView(R.id.textview_content)
    TextView contentTextView;

    private String serializableFilePath = BaseApplication.getFile() + File.separator + "user.txt";

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f2;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
    }

    @SuppressLint("CheckResult")
    private void initListener() {
    }

    public void saveUser(View view) {
        User user = new User();
        user.setName("张三");
        user.setAge("25");
        user.setSex("male");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(serializableFilePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(user);
            objectOutputStream.close();
        } catch (Exception e) {
            Log.d(TAG, e + "");
        }
    }

    public void getUser(View view) {
        User user = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(serializableFilePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            user = (User) objectInputStream.readObject();
            objectInputStream.close();
            if (null != user) {
                contentTextView.setText(user.toString());
            }
        } catch (Exception e) {
            Log.d(TAG, e + "");
        }
    }

    Parcel parcel = null;
    Intent intent = null;

    public void saveStudent(View view) {
        User user = new User();
        user.setName("张三");
        user.setAge("25");
        user.setSex("male");
        Student student = new Student(1, "李四", "female", user);
        //parcel = Parcel.obtain();
        //student.writeToParcel(parcel, 1);
        intent = new Intent();
        intent.putExtra("stu", student);
    }

    public void getStudent(View view) {
        //Student student = Student.CREATOR.createFromParcel(parcel);
        try {
            Student student = intent.getParcelableExtra("stu");
            if (null != student) {
                contentTextView.setText(student.toString());
            }
        } catch (Exception e) {
        }
    }

    private Messenger mService;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService = new Messenger(iBinder);
            Message msg = new Message();
            msg.what = 1001;
            Bundle bundle = new Bundle();
            bundle.putString("msg", "this is client");
            msg.setData(bundle);
            msg.replyTo = mMessenger;
            try {
                mService.send(msg);
            } catch (Exception e) {
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            QMLog.d(TAG, "unbind");
        }
    };

    private Messenger mMessenger = new Messenger(new MessengerHandler());

    private class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1002:
                    QMLog.d(TAG, msg.getData().getString("reply"));
                    break;
            }
        }
    }

    private boolean bindService1 = false;

    public void bindService1(View view) {
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        bindService1 = true;
    }

    public void unbindService1(View view) {
        if (bindService1) {
            unbindService(mConnection);
            bindService1 = false;
        }
    }

    private IBookManager mRemoteBookManager;
    private ServiceConnection serviceConnection2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IBookManager bookManager = IBookManager.Stub.asInterface(iBinder);
            try {
                mRemoteBookManager = bookManager;
                List<Book> list = bookManager.getBookList();
                printBookList(list);
                bookManager.addBook(new Book(3, "no name"));
                list = bookManager.getBookList();
                printBookList(list);
                bookManager.registerListner(mOnNewBookArrivedListener);
            } catch (Exception e) {
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mRemoteBookManager = null;
            QMLog.d(TAG, "binder died");
        }
    };

    private void printBookList(List<Book> list) {
        if (null != list) {
            QMLog.d(TAG, "query book list type=" + list.getClass().getCanonicalName());
            for (Book book : list) {
                if (null != book) {
                    QMLog.d(TAG, book.toString());
                }
            }
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1003:
                    QMLog.d(TAG, "received a new book" + msg.obj);
                    break;
            }
        }
    };
    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {

        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(1003, newBook).sendToTarget();
        }

    };
    private boolean bindService2 = false;

    public void bindService2(View view) {
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, serviceConnection2, Context.BIND_AUTO_CREATE);
        bindService2 = true;
    }

    public void unbindService2(View view) {
        if (bindService2) {
            if (null != mRemoteBookManager && mRemoteBookManager.asBinder().isBinderAlive()) {
                try {
                    mRemoteBookManager.unregisterListner(mOnNewBookArrivedListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            unbindService(serviceConnection2);
            bindService2 = false;
        }
    }
}