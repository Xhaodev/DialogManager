package com.x_x.dialogmanger.core;

import android.app.Dialog;
import android.util.Log;

public class DialogWrapper implements Comparable<DialogWrapper> {

    /**
     * 被管理的dialog
     */
    private final Dialog dialog;

    private boolean interrupted = false;
    /**
     * 被存放的DialogQueue
     */
    private final DialogQueue dialogQueue;

    final DialogConfig config;

    DialogWrapper(Dialog dialog, DialogQueue dialogQueue, DialogConfig config) {
        this.dialog = dialog;
        this.dialogQueue = dialogQueue;
        this.config = config;
        observer();
    }

    private void observer() {
        dialog.setOnDismissListener(dialog -> {
            Log.e("Tag", "setOnDismissListener");
            OnDismissListener listener = config.getDialogDismissListener();
            if (listener != null) {
                listener.onDismiss(dialog);
            }
            if (interrupted) {
                interrupted = false;
            } else {
                //被打断的dialog 还保留在队列中, 不要移除
                dialogQueue.removeDialogWrapper(this);
            }
            dialogQueue.dialogShowing = false;
            dialogQueue.promoteAndShow();
        });
        dialog.setOnShowListener(dialog -> {
            Log.e("Tag", "setOnShowListener");
            OnShowListener listener = config.getOnShowListener();
            if (listener != null) {
                listener.onShow(dialog);
            }
            if (interrupted) {
                //被打断的dialog 不展示，
                dialog.dismiss();
            }
            dialogQueue.dialogShowing = true;
        });
    }

    public Dialog getDialog() {
        return dialog;
    }

    @Override
    public int compareTo(DialogWrapper wrapper) {
        return Integer.compare(wrapper.config.getPriority(), this.config.getPriority());//使用降序
    }

    public void interrupt() {
        interrupted = true;
        Log.e("interrupt", "interrupt dismiss");
        dialog.dismiss();
    }
}


