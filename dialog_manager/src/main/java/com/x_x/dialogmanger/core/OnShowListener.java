package com.x_x.dialogmanger.core;

import android.content.DialogInterface;

public interface OnShowListener {
    /**
     * This method will be invoked when the dialog is shown.
     *
     * @param dialog the dialog that was shown will be passed into the
     *               method
     */
    void onShow(DialogInterface dialog);
}