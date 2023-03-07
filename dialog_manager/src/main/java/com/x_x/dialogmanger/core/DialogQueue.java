package com.x_x.dialogmanger.core;

import android.app.Dialog;
import android.os.Handler;
import android.os.Looper;

import java.util.Iterator;
import java.util.Objects;
import java.util.PriorityQueue;

class DialogQueue {
    private final static String Tag = "DialogQueue";

    private final PriorityQueue<DialogWrapper> dialogQueue;

    private boolean isStop = false;

    public boolean dialogShowing = false;

    private DialogWrapper currentDialogWrapper = null;

    private final Handler mainHandler;

    DialogQueue() {
        dialogQueue = new PriorityQueue<>();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    DialogQueue(Dialog dialog, DialogConfig config) {
        this();
        add(dialog, config);
    }

    /**
     * @param dialog
     * @return
     */
    public boolean add(Dialog dialog, DialogConfig config) {
        //判断是否已经存在
        boolean contains = contains(dialog);
        if (contains) {
            return false;
        }

        DialogWrapper newDialogWrapper = new DialogWrapper(dialog, this, config);

        int oldSize = dialogQueue.size();
        dialogQueue.add(newDialogWrapper);

        checkImmediatelyShow(newDialogWrapper);

        if (oldSize == 0) {
            promoteAndShow();
        }
        return true;
    }

    /**
     * 是否需要立即展示这个dialog
     *
     * @param newDialogWrapper
     */
    private void checkImmediatelyShow(DialogWrapper newDialogWrapper) {
        if (currentDialogWrapper == null) return;
        if (!newDialogWrapper.config.isImmediately()) return;

        if (newDialogWrapper.config.getPriority() <= currentDialogWrapper.config.getPriority()) {
            return;
        }
        currentDialogWrapper.interrupt();//dialog被中断显示，展示优先级高的
    }

    private boolean contains(Dialog dialog) {
        Objects.requireNonNull(dialog);
        final Iterator<DialogWrapper> each = dialogQueue.iterator();
        while (each.hasNext()) {
            Dialog containedDialog = each.next().getDialog();
            if (containedDialog.hashCode() == dialog.hashCode()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 推动展示队列里的dialog
     */
    public void promoteAndShow() {
        if (isStop) return;
        if (dialogShowing) return;
        synchronized (this) {
            //先不从队列删除，当dialog dismiss再删除
            //如果在这里移除，重复添加dialog时候有可能会再次添加一次
            DialogWrapper dialogWrapper = dialogQueue.peek();
            if (dialogWrapper != null) {
                currentDialogWrapper = dialogWrapper;
                mainHandler.post(() -> {
                    dialogWrapper.getDialog().show();
                });
            }
        }
    }

    public void removeDialogWrapper(DialogWrapper removeDialogWrapper) {
        synchronized (this) {
            for (Iterator<DialogWrapper> i = dialogQueue.iterator(); i.hasNext(); ) {
                DialogWrapper dialogWrapper = i.next();
                if (removeDialogWrapper == dialogWrapper) {
                    i.remove();
                }
            }
        }
    }

    public void release() {
        dialogQueue.clear();
        isStop = true;
        currentDialogWrapper.getDialog().dismiss();
        currentDialogWrapper = null;
        mainHandler.removeCallbacksAndMessages(null);
    }
}