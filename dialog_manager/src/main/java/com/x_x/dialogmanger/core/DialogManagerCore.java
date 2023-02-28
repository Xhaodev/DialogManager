package com.x_x.dialogmanger.core;

import android.app.Dialog;
import android.util.Log;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DialogManagerCore {
    private final static String Tag = "DialogManagerCore";

    /**
     * 单例模式实现
     */
    private static class SingletonHolder {
        private static final DialogManagerCore DEFAULT_MANAGER = new DialogManagerCore();
    }

    public static DialogManagerCore get() {
        return SingletonHolder.DEFAULT_MANAGER;
    }

    /**
     * 存放Dialog
     * LifecycleOwner为Activity和Fragment
     */
    private final Map<LifecycleOwner, DialogQueue> dialogQueueMap;

    DialogManagerCore() {
        dialogQueueMap = new HashMap<>();
    }

    private final LifecycleEventObserver lifecycleEventObserver = new LifecycleEventObserver() {
        @Override
        public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
            switch (event) {
                case ON_DESTROY: {//需要清空dialog ，销毁队列
                    removeQueue(source);
                    break;
                }
            }
        }
    };

    public void put(ComponentActivity lifecycleOwner,
                    Dialog dialog,
                    DialogConfig config
    ) {
        if (null == dialog) return;
        synchronized (this) {
            //判断有没有保存这个页面，没有保存过要添加observer
            if (!dialogQueueMap.containsKey(lifecycleOwner)) {
                lifecycleOwner.getLifecycle().addObserver(lifecycleEventObserver);
                //添加到map中
                dialogQueueMap.put(lifecycleOwner, new DialogQueue(dialog, config));
                return;
            }
            boolean isAddSuccess = Objects.requireNonNull(dialogQueueMap.get(lifecycleOwner))
                    .add(dialog, config);

            if (!isAddSuccess) {
                Log.e(Tag, "添加dialog失败");
            }
        }
    }

    private void removeQueue(LifecycleOwner source) {
        DialogQueue queue = dialogQueueMap.get(source);
        if (queue != null) queue.release();
        dialogQueueMap.remove(source);
    }

}
