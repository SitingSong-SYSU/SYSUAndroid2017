package com.example.stellasong.lab5;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Created by StellaSong on 2017/11/3.
 */

public class mWidget extends AppWidgetProvider {
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        CharSequence widgetText = context.getString(R.string.no_message);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.m_widget);
        views.setTextViewText(R.id.WidgetName, widgetText);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.WidgetImage, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent){
        super.onReceive(context, intent);
        if(intent.getAction().equals("com.example.stellasong.lab5.staticreceiver")) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.m_widget);
            Bundle bundle = intent.getExtras();

            views.setTextViewText(R.id.WidgetName, bundle.getString("Name") + "仅售" + bundle.getString("Price") + "！");
            views.setImageViewResource(R.id.WidgetImage, bundle.getInt("ItemImage"));
            //要发给detail的Intent
            Intent intent1 = new Intent(context, ShowDetails.class);
            intent1.addCategory(Intent.CATEGORY_LAUNCHER);
            intent1.putExtra("Name", bundle.getString("Name"));
            intent1.putExtra("Price", bundle.getString("Price"));
            intent1.putExtra("Info", bundle.getString("Info"));

            PendingIntent Pintent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.m_widget, Pintent);
            ComponentName me = new ComponentName(context, mWidget.class);
            AppWidgetManager.getInstance(context).updateAppWidget(me, views);
        }
    }
}
