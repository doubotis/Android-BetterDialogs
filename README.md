# Android-BetterDialogs
Android Dialogs with fragment support, using same Builder APIs

## Limitations
Android 4.0+ only
Needs android.support.v7

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

### Display a ProgressDialog, using BProgressDialog
Same as ProgressDialog base API. you can call `BProgressDialog.Builder` builder class.

The following example will make a BProgressDialog with spinner and a "Please wait" caption and simply display it on the main activity.
```
new BProgressDialog.Builder(DIALOG_TAG_PROGRESS_DIALOG)
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
