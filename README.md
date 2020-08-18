# PinVew

PinView is a UI component to provide pin input off the shelf. You can customize the number of fields, colors of different input states, and sizes of input items.


#### Usage:
 1. Add PinView to your layout either using xml or code:
 2. Listen for the completion and react accordingly. Refer to the [sample](https://github.com/heapsapp/PinViewAndroid/blob/master/app/src/main/java/com/heaps/android/pinview/MainActivity.kt) for an example, if you don't know how to do it.
 3. You are done! 
```xml
    <com.heaps.android.pinview.PinView
        android:id="@+id/pin_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:pendingInputColor="@color/colorAccent"
        app:idleInputColor="@color/white"
        app:completedInputColor="@color/colorPrimary"
        app:pendingInputSize="60dp"
        app:completedInputSize="48dp"
        app:numberOfFields="3"/>
```
```kotlin
      val pinView = findViewById<PinView>(R.id.pin_view)
        pinView.listener = { pinCode ->
            Toast.makeText(this, pinCode, Toast.LENGTH_SHORT).show()
            pinView.resetFields()
        }
```
