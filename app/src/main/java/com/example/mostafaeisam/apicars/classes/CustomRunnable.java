package com.example.mostafaeisam.apicars.classes;

import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.example.mostafaeisam.apicars.classes.Items;
import com.example.mostafaeisam.apicars.responses.GetAuctionInfo;

import java.util.TimeZone;

public class CustomRunnable implements Runnable {

    public long millisUntilFinished = 40000;
    public TextView holder;
    public TextView tvTimeLeft ;
    Handler handler;

    public CustomRunnable(long millisUntilFinished, TextView holder, Handler handler) {
        this.millisUntilFinished = millisUntilFinished;
        this.holder = holder;
        this.handler = handler;
    }

    @Override
    public void run() {

        long seconds = (millisUntilFinished / 1000);
        long minutes = seconds / 60;
        long hours = minutes / 60;
        //long days = hours / 24;
        /*
        long hoursofdays = days * 24;
        long totalHours = hours + hoursofdays;
        */
        String time = hours + ":" + minutes % 60 + ":" + seconds % 60;

        holder.setText(time);

        millisUntilFinished -= 1000;

        Log.d("DEV123",time);
        /* and here comes the "trick" */
        handler.postDelayed(this, 1000);

        if ((minutes/60) <= 5 && hours ==0) {
            holder.setTextColor(Color.RED);
        } else {
            holder.setTextColor(Color.parseColor("#595959"));
        }

    }
}
