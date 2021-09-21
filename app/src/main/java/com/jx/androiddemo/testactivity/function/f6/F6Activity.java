package com.jx.androiddemo.testactivity.function.f6;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F6Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    private final String TAG = "F6Activity";

    private ServerSocket serverSocket;
    private int mLocalPort;
    private NsdManager.RegistrationListener registrationListener;
    private String mServiceName;
    private NsdManager nsdManager;
    private NsdManager.DiscoveryListener discoveryListener;
    private NsdManager.ResolveListener mResolveListener;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f6;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
        initializeRegistrationListener();
        initializeResolveListener();
        initializeDiscoveryListener();
    }

    private void initView() {
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

    private void registerService(int port) {
        NsdServiceInfo nsdServiceInfo = new NsdServiceInfo();
        nsdServiceInfo.setServiceName("NsdTest");
        nsdServiceInfo.setServiceType("_http._tcp");
        nsdServiceInfo.setPort(port);

        nsdManager = (NsdManager) getApplicationContext().getSystemService(Context.NSD_SERVICE);
        nsdManager.registerService(nsdServiceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener);
    }

    private int initializeServerSocket() {
        try {
            serverSocket = new ServerSocket(0);
            mLocalPort = serverSocket.getLocalPort();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mLocalPort;
    }

    private void initializeRegistrationListener() {
        registrationListener = new NsdManager.RegistrationListener() {
            @Override
            public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {

            }

            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {

            }

            @Override
            public void onServiceRegistered(NsdServiceInfo serviceInfo) {
                mServiceName = serviceInfo.getServiceName();
            }

            @Override
            public void onServiceUnregistered(NsdServiceInfo serviceInfo) {

            }
        };
    }

    private void initializeDiscoveryListener() {
        discoveryListener = new NsdManager.DiscoveryListener() {
            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {

            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {

            }

            @Override
            public void onDiscoveryStarted(String serviceType) {

            }

            @Override
            public void onDiscoveryStopped(String serviceType) {

            }

            @Override
            public void onServiceFound(NsdServiceInfo serviceInfo) {
                if (serviceInfo.getServiceName().contains("NsdTest")) {
                    try {
                        nsdManager.resolveService(serviceInfo, mResolveListener);
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo serviceInfo) {

            }
        };
    }

    public void initializeResolveListener() {
        mResolveListener = new NsdManager.ResolveListener() {

            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Called when the resolve fails.  Use the error code to debug.
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Log.e(TAG, "Resolve Succeeded. " + serviceInfo);

                if (serviceInfo.getServiceName().equals(mServiceName)) {
                    Log.d(TAG, "Same IP.");
                    return;
                }
                int port = serviceInfo.getPort();
                InetAddress host = serviceInfo.getHost();
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nsdManager != null) {
            tearDown();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerService(initializeServerSocket());
        nsdManager.discoverServices("_http._tcp", NsdManager.PROTOCOL_DNS_SD, discoveryListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void tearDown() {
        nsdManager.unregisterService(registrationListener);
        nsdManager.stopServiceDiscovery(discoveryListener);
    }
}