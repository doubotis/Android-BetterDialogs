# Android-BetterDialogs
Android Dialogs with fragment support, using same Builder APIs

## Limitations
* Android 4.0+ only
* Needs android.support.v7

## Install

Include this in your build.gradle file :
```
dependencies {
  compile 'be.doubotis.betterdialogs:libbetterdialogs:1.0.0'
}
```

## Usage

### Display an AlertDialog, using BAlertDialog
Same as AlertDialog base API. You can call `BAlertDialog.Builder` builder class.

The following example will make a BAlertDialog with "Your title" title and "Your caption" message and simply display it on the main activity.
```
new BAlertDialog.Builder(YOUR_INTEGER_DIALOG_TAG)
                .setTitle("Your title")
                .setCaption("Your caption")
                .buildAndShow(MainActivity.this);
```

### Button Handling
By the limitations of `DialogFragment`, it's not possible to set directly a listener into the builder, but you can specify the buttons and the amount you want.

```
new BAlertDialog.Builder(YOUR_INTEGER_DIALOG_TAG)
                .setCaption("Hello World!")
                .setButtons(new String[] {"Positive Button", "Negative Button", "Neutral Button"})
                .buildAndShow(MainActivity.this);
```

Additionnaly, your activity **may** implement the `BAlertDialog.BAlertDialogListener` to be warned about button clicks.
See an exemple of implementing this interface below:
```
@Override
public void onDialogButtonClick(BDialog dialog, int buttonClicked) {

    if (dialog.getDialogTag() == YOUR_INTEGER_DIALOG_TAG) {
        if (buttonClicked == BAlertDialog.BAlertDialogListener.BUTTON_NEUTRAL) {
            Toast.makeText(this, "Neutral Button", Toast.LENGTH_SHORT).show();
        }
        else if (buttonClicked == BAlertDialog.BAlertDialogListener.BUTTON_POSITIVE) {
            Toast.makeText(this, "Positive Button", Toast.LENGTH_SHORT).show();
        }
        else if (buttonClicked == BAlertDialog.BAlertDialogListener.BUTTON_NEGATIVE) {
            Toast.makeText(this, "Negative Button", Toast.LENGTH_SHORT).show();
        }
    }
```

### Display a ProgressDialog, using BProgressDialog
Same as ProgressDialog base API. you can call `BProgressDialog.Builder` builder class.

The following example will make a BProgressDialog with spinner and a "Please wait" caption and simply display it on the main activity.
```
new BProgressDialog.Builder(YOUR_INTEGER_DIALOG_TAG)
                .setCaption("Please wait")
                .setIndeterminate(true)
                .buildAndShow(MainActivity.this);
```

### Get a Dialog to control it further, using BDialogs

You can use the unique static class method `BDialogs.get(Activity activity, int tag)` to get a `BDialog` instance.
The method will return to you the dialog you want if it is present on the specified activity. If not, this returns null.

A `BDialog` instance is only an interface, that is implemented by `BAlertDialog` and `BProgressDialog` classes. These classes extends `DialogFragment`, so with a fast casting you can use any method of `DialogFragment`.

## More information
See the Sample project to get all the way to control dialogs with the Better Dialogs library.
