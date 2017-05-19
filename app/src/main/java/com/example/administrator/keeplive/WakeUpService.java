package com.example.administrator.keeplive;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */

public class WakeUpService extends JobService{
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        JobInfo.Builder jobBuilder=new JobInfo.Builder(1,new ComponentName(this,WakeUpService.class));
        jobBuilder.setPeriodic(2000);
        JobScheduler jobScheduler= (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobBuilder.build());
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        String serviceName = MyService.class.getName();
        boolean isLive = serviceLive(serviceName);
        if(!isLive){
            startService(new Intent(this,MyService.class));
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    public boolean serviceLive(String serviceName){
        boolean isWork=false;
        ActivityManager activityManager= (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(100);
        if(services.size()<=0){
                return false;
        }
        for (int i=0;i<services.size();i++){
            String name = services.get(i).service.getClassName().toString();
            if(name.equals(serviceName)){
              isWork=true;
                break;
            }


        }
        return isWork;

    }


}
