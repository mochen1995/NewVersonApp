package com.tuyue.okhttputils;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.zhl.cbdialog.CBDialogBuilder;

import static com.zhl.cbdialog.CBDialogBuilder.DIALOG_STYLE_NORMAL;
import static com.zhl.cbdialog.CBDialogBuilder.INDICATOR_BallRotate;

/**
 * Created by dell on 2017/1/16.
 * 自定义的对话框组件
 */

public class CBDialogUtils {

    private static CBDialogBuilder builder;
    private static Dialog dialog;
    private static Dialog alertDialog;

    public static void showLoadingCBDialog(Context context) {
        builder = new CBDialogBuilder(context, CBDialogBuilder.DIALOG_STYLE_PROGRESS_AVLOADING);
        builder.setProgressIndicator(INDICATOR_BallRotate)
                .setTouchOutSideCancelable(false)
                .setDialogAnimation(CBDialogBuilder.DIALOG_ANIM_SLID_BOTTOM)
                .setDialoglocation(CBDialogBuilder.DIALOG_LOCATION_CENTER)
                .setTitle(null)
                .setMessage("正在加载请稍后...")
                .showConfirmButton(false)
                .showCancelButton(false);
        dialog = builder.create();
        dialog.show();
    }
    public static void showConfirmCBDialog(Context context,String msg,CBDialogBuilder.onDialogbtnClickListener listener)
    {
        builder = new CBDialogBuilder(context);
        builder.setTitle(msg)
                .setTouchOutSideCancelable(true)
                .setDialogAnimation(CBDialogBuilder.DIALOG_ANIM_SLID_TOP)
                .setDialoglocation(CBDialogBuilder.DIALOG_LOCATION_CENTER)
                .showIcon(false)
                .setMessage(null)
                .showCancelButton(true)
                .showConfirmButton(true)
                .setConfirmButtonText("去登陆")
                .setCancelButtonText("取消")
                .setButtonClickListener(true, listener);
        dialog = builder.create();
        dialog.show();
    }
    public static void showConfirmCBDialog(Context context,String msg,String sure,String cancel,CBDialogBuilder.onDialogbtnClickListener listener)
    {
        builder = new CBDialogBuilder(context);
        builder.setTitle(msg)
                .setTouchOutSideCancelable(true)
                .setDialogAnimation(CBDialogBuilder.DIALOG_ANIM_SLID_TOP)
                .setDialoglocation(CBDialogBuilder.DIALOG_LOCATION_CENTER)
                .showIcon(false)
                .setMessage(null)
                .showCancelButton(true)
                .showConfirmButton(true)
                .setConfirmButtonText(sure)
                .setCancelButtonText(cancel)
                .setButtonClickListener(true, listener);
        dialog = builder.create();
        dialog.show();
    }
    public static void showLoadingCBDialog(Context context,String msg) {
        builder = new CBDialogBuilder(context, CBDialogBuilder.DIALOG_STYLE_PROGRESS_AVLOADING);
        builder.setProgressIndicator(INDICATOR_BallRotate)
                .setTouchOutSideCancelable(false)
                .setDialogAnimation(CBDialogBuilder.DIALOG_ANIM_SLID_BOTTOM)
                .setDialoglocation(CBDialogBuilder.DIALOG_LOCATION_CENTER)
                .setTitle(null)
                .setMessage(msg)
                .showConfirmButton(false)
                .showCancelButton(false);
        dialog = builder.create();
        dialog.show();
    }
    public static void setLoadingText(String msg)
    {
        if (dialog!=null&&dialog.isShowing())
        {
            builder.setMessage(msg);
        }
    }

    public static void dismissLoadingCBDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public static void showItemsCBDialog(Context context, String[] items, CBDialogBuilder.onDialogItemClickListener dialogItemClickListener) {
        builder = new CBDialogBuilder(context, DIALOG_STYLE_NORMAL, 0.95f);
        builder.setTouchOutSideCancelable(true)
                .setTitle(null)
                .setDialogAnimation(CBDialogBuilder.DIALOG_ANIM_SLID_BOTTOM)
                .setDialoglocation(CBDialogBuilder.DIALOG_LOCATION_BOTTOM)
                .showConfirmButton(false)
                .showCancelButton(false)
                .setItems(items, dialogItemClickListener);
        dialog = builder.create();
        dialog.show();
    }

    public static void showCustomCBDialog(Context context, View view) {
        builder = new CBDialogBuilder(context, DIALOG_STYLE_NORMAL);
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null)
            parent.removeAllViews();
        builder.setTitle(null)
                .showIcon(false)
                .showConfirmButton(false)
                .showCancelButton(false)
                .setView(view);
        dialog = builder.create();
        dialog.show();
    }

    public static void showSysDialog(final Context context, View view) {
        alertDialog = new Dialog(context, R.style.Dialog);
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent!=null) {
            parent.removeAllViews();
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int width = (int) (displayMetrics.widthPixels - displayMetrics.density * 32); //屏幕宽高-32dp
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        alertDialog.setContentView(view, layoutParams);
        alertDialog.show();
    }
    public static void dismissSysDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }
}
