package com.paulo.minhas_series.ui.resources;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Html;

import com.paulo.minhas_series.R;

public class DialogAlert {

    public static void alert(Context context, String message) {
        alert(context, "", message, null);
    }

    public static void alert(Context context, int messageResource) {
        alert(context, "", context.getString(messageResource), null);
    }

    public static void alert(Context context, int titleResource, int messageResource) {
        alert(context, context.getString(titleResource),context.getString(messageResource), null);
    }

    public static void alert(Context context, String title, String message) {
        alert(context, title, message, null);
    }

    public static void alert(Context context, String title, String message, DialogInterface.OnClickListener confirmListener) {
        alert(context, title, message, "Ok", confirmListener, null, null, null, null);
    }

    public static void alert(Context context, int titleResource, int messageResource, int positiveButtonResource, DialogInterface.OnClickListener positiveListener, int negativeButtonResource, DialogInterface.OnClickListener negativeListener ) {
        alert(context,context.getString(titleResource),context.getString(messageResource), context.getString(positiveButtonResource), positiveListener, context.getString(negativeButtonResource), negativeListener, null, null);
    }

    public static void alert(Context context, String title, String message, String positiveButton, DialogInterface.OnClickListener positiveListener, String negativeButton, DialogInterface.OnClickListener negativeListener ) {
        alert(context,title,message,positiveButton, positiveListener,negativeButton, negativeListener, null, null);
    }

    public static void alert(Context context, String title, String message, String positiveButton, DialogInterface.OnClickListener positiveListener, String negativeButton, DialogInterface.OnClickListener negativeListener, String neutralButton, DialogInterface.OnClickListener neutralListener ) {
        AlertDialog.Builder bld = new AlertDialog.Builder(context, R.style.AppTheme_AlertDialogStyle);
        bld
                .setIcon(R.drawable.ic_icon)
                .setTitle(title)
                .setMessage(Html.fromHtml(message))
                .setPositiveButton(positiveButton, positiveListener);

        if( negativeButton != null ){
            bld.setNegativeButton(negativeButton, negativeListener);
        }

        if( neutralButton != null ){
            bld.setNeutralButton(neutralButton, neutralListener);
        }

        bld.create().show();
    }
}
