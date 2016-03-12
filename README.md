# Android-BetterDialogs
Android Dialogs with fragment support, using same Builder APIs
This library allows you to control dialogs one's displayed, even if the orientation of the screen was changed.

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

#### Button Handling
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

#### View Handling
Still due to limitations of `DialogFragment`, it's not possible to set a standard View to be added to a BAlertDialog. You need a wrapping into a Parcelable interface.

So you need to create a class that implements `ParcelableView` interface, by implementing these two methods:
```
    public View getView(Context context, BAlertDialog.BAlertDialogListener eventProvider);
    public Padding getPadding(Context context);
```
The first one is to get the View you want to add into your dialog.
The second one is to get the paddings you want your view have inside the dialog. That is used by the `setView(View, int, int, int, int)` method of AlertDialog base API to adjust your view position inside the dialog.

More of that, your wrapping class must implement and handle `Parcelable`. See this http://developer.android.com/reference/android/os/Parcelable.html to understand what is and how to handle it.
You can see an example with a custom EditText view wrapping in the Sample project.

If you want to send events inside the custom view, just use the eventProvider variable in the getView(Context, BAlertDialog.BAlertDialogListener) interface method. This will be caught by any activities that implements `BAlertDialog.BAlertDialogListener`.

Additionnaly, you can access the ParcelableView one's the dialog creation by using the `getParcelableView()` method of a `BAlertDialog`. Be carreful, this will return null until the Dialog is displayed. Prefer using this logic call :
```
((BAlertDialog)BDialogs.get(MainActivity.this, MY_DIALOG_INT_TAG)).getParcelableView();
```

```
SampleParcelableView spv = new SampleParcelableView();
new BAlertDialog.Builder(DIALOG_TAG_ALERT_DIALOG_WITH_VIEW)
        .setTitle("Custom View")
        .setView(spv)
        .setButtons(new String[]{"Button A"})
        .buildAndShow(MainActivity.this);
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

This is useful when you want to get a dialog that was displayed without storing it as an Activity's variable member. More of that, even if the screen changes his orientation, `BDialogs.get(Activity activity, int tag)` is still able to lookup the dialog, based on the dialog you specify on the Builder.

## More information
See the Sample project to get all the way to control dialogs with the Better Dialogs library.
