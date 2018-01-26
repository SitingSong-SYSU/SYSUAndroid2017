package com.example.stellasong.lab5;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;
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
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.m_widget); //获取RemoteViews使得可以远程修改widget

            // 设置图片
            switch (name) {
                case "Enchated Forest":
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.enchatedforest);
                    imageId = R.mipmap.enchatedforest;
                    views.setImageViewResource(R.id.WidgetImage, R.mipmap.enchatedforest);
                    break;
                case "Arla Milk":
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.arla);
                    imageId = R.mipmap.arla;
                    views.setImageViewResource(R.id.WidgetImage, R.mipmap.arla);
                    break;
                case "Devondale Milk":
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.devondale);
                    imageId = R.mipmap.devondale;
                    views.setImageViewResource(R.id.WidgetImage, R.mipmap.devondale);
                    break;
                case "Kindle Oasis":
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.kindle);
                    imageId = R.mipmap.kindle;
                    views.setImageViewResource(R.id.WidgetImage, R.mipmap.kindle);
                    break;
                case "waitrose 早餐麦片":
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.waitrose);
                    imageId = R.mipmap.waitrose;
                    views.setImageViewResource(R.id.WidgetImage, R.mipmap.waitrose);
                    break;
                case "Mcvitie's 饼干":
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.mcvitie);
                    imageId = R.mipmap.mcvitie;
                    views.setImageViewResource(R.id.WidgetImage, R.mipmap.mcvitie);
                    break;
                case "Ferrero Rocher":
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ferrero);
                    imageId = R.mipmap.ferrero;
                    views.setImageViewResource(R.id.WidgetImage, R.mipmap.ferrero);
                    break;
                case "Maltesers":
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.maltesers);
                    imageId = R.mipmap.maltesers;
                    views.setImageViewResource(R.id.WidgetImage, R.mipmap.maltesers);
                    break;
                case "Lindt":
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.lindt);
                    imageId = R.mipmap.lindt;
                    views.setImageViewResource(R.id.WidgetImage, R.mipmap.lindt);
                    break;
                case "Borggreve":
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.borggreve);
                    imageId = R.mipmap.borggreve;
                    views.setImageViewResource(R.id.WidgetImage, R.mipmap.borggreve);
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

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_ONE_SHOT);
            builder.setContentIntent(pendingIntent);
            Notification notify = builder.build();
            notificationManager.notify(0, notify);

            //设置widget的显示,打包发给widget
            views.setTextViewText(R.id.WidgetName, name + "已添加到购物车");
            PendingIntent Pintent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.m_widget, Pintent);
            AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, mWidget.class), views);
        }
    }
}
