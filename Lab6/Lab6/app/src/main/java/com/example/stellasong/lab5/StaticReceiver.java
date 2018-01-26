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

public class StaticReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.example.stellasong.lab5.staticreceiver")) {
            Bundle bundle = intent.getExtras();
            Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),bundle.getInt("ItemImage"));
            int imageId = (int)bundle.get("ItemImage");

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("新商品热卖")
                    .setContentText(bundle.getString("Name") + "仅售" + bundle.getString("Price") + "！")
                    .setLargeIcon(bitmap)
                    .setSmallIcon(imageId)
                    .setTicker("您有一条新消息")
                    .setAutoCancel(true);
            Intent intent1 = new Intent(context, ShowDetails.class);
            intent1.putExtra("Name", bundle.getString("Name"));
            intent1.putExtra("Price", bundle.getString("Price"));
            intent1.putExtra("Info", bundle.getString("Info"));

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_ONE_SHOT);
            builder.setContentIntent(pendingIntent);
            Notification notify = builder.build();
            notificationManager.notify(0, notify);
        }
    }
}
