package be.doubotis.betterdialogs;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;

import be.doubotis.betterdialogs.support.BAlertDialog;
import be.doubotis.betterdialogs.support.BProgressDialog;

/**
 * Created by Christophe on 20-10-15.
 */
public interface BDialog {

    public static final String BUNDLE_TITLE = "dialog.title";
    public static final String BUNDLE_CAPTION = "dialog.caption";
    public static final String BUNDLE_VIEW = "dialog.view";
    public static final String BUNDLE_DATA = "dialog.data";
    public static final String BUNDLE_CONTROL = "dialog.control";
    public static final String BUNDLE_TAG = "dialog.tag";
    public static final String BUNDLE_STYLE = "dialog.style";
    public static final String BUNDLE_CANCELABLE = "dialog.cancelable";

    /** Gets the dialog tag. */
    public int getDialogTag();
    /** Gets the internal bundle used to set the dialog layout. */
    public Bundle getBundle();
    /** Shows the dialog in the specified activity. */
    public void show(Activity parent);
    /** Check the bundle to verify if something is not well defined inside.
     * So you can return <code>FALSE</code> or throw an {@link IllegalStateException}. */
    public boolean verify(Bundle bundle) throws IllegalStateException;
    public void dismiss();
}
