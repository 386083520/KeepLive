package com.example.administrator.keeplive;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/5/19.
 */

public class KeepLiveService extends Service{
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1,new Notification());
        bindService(new Intent(this,MyService.class),mServiceConnection, Context.BIND_IMPORTANT);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnection.Stub(){

        };
    }

    private ServiceConnection mServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(KeepLiveService.this,"与MyService服务建立连接",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startService(new Intent(KeepLiveService.this,MyService.class));
            bindService(new Intent(KeepLiveService.this,MyService.class),mServiceConnection, Context.BIND_IMPORTANT);

        }
    };
}
