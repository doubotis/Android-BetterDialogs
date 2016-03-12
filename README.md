# Android-BetterDialogs
Android Dialogs with fragment support, using same Builder APIs

## Limitations
Android 4.0+ only

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

The following example will make a BProgressDialog with spinner and a "Please wait" caption.
```

```

### Get a Dialog to control it further, using BDialogs

You can use the unique static class method `BDialogs.get(Activity activity, int tag)` to get a `BDialog` instance.
