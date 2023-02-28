package com.x_x.dialogmanger;

import android.app.Dialog;

import androidx.activity.ComponentActivity;

import com.x_x.dialogmanger.core.DialogConfig;
import com.x_x.dialogmanger.core.DialogManagerCore;

/**
 * 当页面生命周期在可现实dialog时，从保存的队列中拿出dialog调用show方法，当前dialog dismiss时候查找下一个dialog
 */
public final class DialogManager {


    public static void put(ComponentActivity lifecycleOwner,
                           Dialog dialog) {
        put(lifecycleOwner, dialog, new DialogConfig());
    }

    public static void put(ComponentActivity lifecycleOwner,
                           Dialog dialog,
                           DialogConfig config
    ) {
        DialogManagerCore.get().put(lifecycleOwner, dialog, config);
    }

}
