package com.paulo.minhas_series.ui.resources;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

public class SnakeAlert {

    public static void alert(Context context, String mensagem) {
        alert(context,mensagem,"Ok",null);
    }

    public static void alert(View v, String mensagem) {
        alert(v,mensagem,"Ok",null);
    }

    public static void alert(Context context, int mensagemResourceId, int buttonResourceId, View.OnClickListener listener) {
        alert(context,context.getString(mensagemResourceId),context.getString(buttonResourceId),listener);
    }

    public static void alert(Context context, String mensagem, String button, View.OnClickListener listener) {
        Activity activity = (Activity) context;
        Snackbar.make(activity.findViewById(android.R.id.content), mensagem, Snackbar.LENGTH_LONG)
                .setAction(button, listener)
                .show();
    }

    public static void alert(View v, String mensagem, String button, View.OnClickListener listener) {
        Snackbar.make(v, mensagem, Snackbar.LENGTH_LONG)
                .setAction(button, listener)
                .show();
    }
}
