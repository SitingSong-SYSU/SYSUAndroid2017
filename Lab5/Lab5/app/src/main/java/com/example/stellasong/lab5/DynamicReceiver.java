package com.example.stellasong.lab5;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by StellaSong on 2017/10/25.
 */

public class DynamicReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.example.stellasong.lab5.dynamicreceiver")) {
            Bundle bundle = intent.getExtras();
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.enchatedforest);
            int imageId = R.mipmap.enchatedforest;
            String name = bundle.getString("Name");

            // 设置图片
            switch (name) {
                case "Enchated Forest":
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.enchatedforest);
                    imageId = R.mipmap.enchatedforest;
                    break;
                case "Arla Milk":
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.arla);
                    imageId = R.mipmap.arla;
                    break;
                case "Devondale Milk":
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.devondale);
                    imageId = R.mipmap.devondale;
                    break;
                case "Kindle Oasis":
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.kindle);
                    imageId = R.mipmap.kindle;
                    break;
                case "waitrose 早餐麦片":
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.waitrose);
                    imageId = R.mipmap.waitrose;
                    break;
                case "Mcvitie's 饼干":
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.mcvitie);
                    imageId = R.mipmap.mcvitie;
                    break;
                case "Ferrero Rocher":
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ferrero);
                    imageId = R.mipmap.ferrero;
                    break;
                case "Maltesers":
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.maltesers);
                    imageId = R.mipmap.maltesers;
                    break;
                case "Lindt":
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.lindt);
                    imageId = R.mipmap.lindt;
                    break;
                case "Borggreve":
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.borggreve);
                    imageId = R.mipmap.borggreve;
                    break;
            }

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("马上下单")
                    .setContentText(name + "已添加到购物车")
                    .setLargeIcon(bitmap)
                    .setSmallIcon(imageId)
                    .setTicker("您有一条新消息")
                    .setAutoCancel(true);
            Intent mIntent = new Intent(context, MainActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            Notification notify = builder.build();
            notificationManager.notify(0, notify);
        }
    }
}
