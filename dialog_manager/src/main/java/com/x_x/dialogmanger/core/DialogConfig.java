package com.x_x.dialogmanger.core;

public class DialogConfig {

    /**
     * 优先级，99最大
     */
    private int priority = 0;

    private boolean immediately;

    private OnDismissListener dismissListener = null;

    private OnShowListener onShowListener = null;

    public DialogConfig setPriority(int priority) {
        if (priority >= 100) {
            this.priority = 99;
        } else this.priority = Math.max(priority, 0);
        return this;
    }

    public DialogConfig setImmediately(boolean immediately) {
        this.immediately = immediately;
        return this;
    }

    public DialogConfig setDismissListener(OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
        return this;
    }

    public DialogConfig setOnShowListener(OnShowListener onShowListener) {
        this.onShowListener = onShowListener;
        return this;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isImmediately() {
        return immediately;
    }

    public OnDismissListener getDialogDismissListener() {
        return dismissListener;
    }

    public OnShowListener getOnShowListener() {
        return onShowListener;
    }
}
