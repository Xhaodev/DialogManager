# DialogManager
AndroidDialog Manager
## Usage
```kotlin
val dialog1 = AlertDialog.Builder(this)
    .setTitle("Dialog1")
    .setMessage("Message1")
    .setOnDismissListener {
        /**
         * Will not be executed!
         * Use "DialogConfig().setDismissListener()" instead
         */
        Log.e("----", "dialog 1 dismiss")
    }
    .setOnDismissListener {
        /**
         * Will not be executed!
         * Use "DialogConfig().setOnShowListener()" instead
         */
        Log.e("----", "dialog 1 show")
    }
    .create()

val dialog2 = AlertDialog.Builder(this)
    .setTitle("Dialog2")
    .setMessage("Message2")
    .create()

DialogManager.put(this, dialog1)

//Dialog2 will be shown first
DialogManager.put(this, dialog2,
    DialogConfig()
        .setPriority(2)
        .setImmediately(true)
        .setDismissListener {
            Log.e("----", "dialog 2 dismiss")
        }
        .setOnShowListener {
            Log.e("----", "dialog 2 show")
        }
)
```
