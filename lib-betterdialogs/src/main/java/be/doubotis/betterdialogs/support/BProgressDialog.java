package be.doubotis.betterdialogs.support;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import be.doubotis.betterdialogs.BDialog;
import be.doubotis.betterdialogs.BDialogListener;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class BProgressDialog extends DialogFragment implements BProgressDialogHandler, BDialog {

    // Constants
    public static final String BUNDLE_INDETERMINATE = "dialog.indeterminate";
    private static final String BUNDLE_BUTTONS_TYPE = "dialog.buttons.type";

    public static BProgressDialog newInstance() {
        BProgressDialog frag = new BProgressDialog();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public int getDialogTag() {
        return getArguments().getInt(BDialog.BUNDLE_TAG);
    }

    @Override
    public Bundle getBundle() {
        return getArguments();
    }

    @Override
    public void show(Activity rootActivity)
    {
        this.show(rootActivity.getFragmentManager(), "dialog" + getDialogTag());
    }

    @Override
    public boolean verify(Bundle bundle) throws IllegalStateException {
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        //getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag("dialog_inside_fragment")).commit();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        //getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag("dialog_inside_fragment")).commit();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int style = getArguments().getInt(BDialog.BUNDLE_STYLE, -1);
        int tag = getArguments().getInt(BDialog.BUNDLE_TAG);
        Bundle bundle = getArguments();
        String title = getArguments().getString(BDialog.BUNDLE_TITLE, null);
        String caption = getArguments().getString(BDialog.BUNDLE_CAPTION, null);
        boolean cancelable = getArguments().getBoolean(BDialog.BUNDLE_CANCELABLE, true);
        boolean indeterminate = getArguments().getBoolean(BProgressDialog.BUNDLE_INDETERMINATE,
                false);

        String button = null;
        if (getArguments().getInt(BProgressDialog.BUNDLE_BUTTONS_TYPE, -1) == 0)
            button = getArguments().getString(BAlertDialog.BUNDLE_BUTTONS);
        else if (getArguments().getInt(BProgressDialog.BUNDLE_BUTTONS_TYPE, -1) == 1) {
            int intButton = getArguments().getInt(BAlertDialog.BUNDLE_BUTTONS);
            button = getActivity().getString(intButton);
        }

        android.app.ProgressDialog d = null;

        if (style == -1)
            d = new android.app.ProgressDialog(getActivity());
        else
            d = new android.app.ProgressDialog(getActivity(), style);

        if (title != null)
            d.setTitle(title);

        if (caption != null)
            d.setMessage(caption);

        if (button != null) {
            d.setButton(button,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dismiss();
                        }
                    }
            );
        }

        if (indeterminate)
            d.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        else
            d.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        d.setCancelable(cancelable);
        d.setCanceledOnTouchOutside(cancelable);
        setCancelable(cancelable);
        d.setMax(100);

        return d;
    }

    @Override
    public void onProgress(int progress)
    {
        if (getDialog() == null)
            return;

        android.app.ProgressDialog pd = (android.app.ProgressDialog)getDialog();
        pd.setProgress(progress);
    }

    @Override
    public void onFinished()
    {
        dismiss();
    }

    public BProgressDialogHandler getHandler() { return this; }

    // ===========================================================================================

    /** Helper to build a {@link BProgressDialog}. Methods returns the instance itself, so you can
     * chain your calls.
     */
    public static class Builder
    {
        private Bundle mBundle = new Bundle();

        public Builder(int dialogTag) {
            mBundle.putInt(BDialog.BUNDLE_TAG, dialogTag);
        }

        /**
         * Setup the dialog builder with the content of the specified bundle.
         * You can put values with key constants in {@link BDialog}.
         *
         * @param bundle The bundle to setup the dialog builder.
         * @return The same instance of Builder, allowing chaining calls.
         */
        public Builder setupWithBundle(Bundle bundle) {
            mBundle.clear();
            mBundle.putAll(bundle);
            return this;
        }

        /**
         * Set the style of the dialog.
         * @param style The style to set.
         * @return The same instance of Builder, allowing chaining calls.
         */
        public Builder setStyle(int style) {
            mBundle.putInt(BDialog.BUNDLE_STYLE, style);
            return this;
        }

        /**
         * Set the title of the dialog.
         * Used for {@link BAlertDialog} and {@link BProgressDialog}.
         *
         * @param title The title to set.
         * @return The same instance of Builder, allowing chaining calls.
         */
        public Builder setTitle(String title) {
            mBundle.putString(BDialog.BUNDLE_TITLE, title);
            return this;
        }

        /**
         * Set the caption of the dialog.
         * Used for {@link BAlertDialog} and {@link BProgressDialog}.
         *
         * @param caption The caption to set.
         * @return The same instance of Builder, allowing chaining calls.
         */
        public Builder setCaption(String caption) {
            mBundle.putString(BDialog.BUNDLE_CAPTION, caption);
            return this;
        }

        /**
         * Set the indeterminate state of the progress dialog.
         * Used for {@link BProgressDialog}.
         *
         * @param indeterminate The indeterminate state to set.
         * @return The same instance of Builder, allowing chaining calls.
         */
        public Builder setIndeterminate(boolean indeterminate) {
            mBundle.putBoolean(BProgressDialog.BUNDLE_INDETERMINATE, indeterminate);
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            mBundle.putBoolean(BProgressDialog.BUNDLE_CANCELABLE, cancelable);
            return this;
        }

        /**
         * Set the cancel button text of the progress dialog.
         * Used for {@link BAlertDialog}.
         *
         * @param button The cancel  button text to set.
         * @return The same instance of Builder, allowing chaining calls.
         */
        public Builder setButton(String button) {
            mBundle.putString(BAlertDialog.BUNDLE_BUTTONS, button);
            mBundle.putInt(BProgressDialog.BUNDLE_BUTTONS_TYPE, 0);
            return this;
        }

        /**
         * Set the cancel button text of the progress dialog.
         * Used for {@link BAlertDialog}.
         *
         * @param button The cancel  button text to set.
         * @return The same instance of Builder, allowing chaining calls.
         */
        public Builder setButton(int button) {
            mBundle.putInt(BAlertDialog.BUNDLE_BUTTONS, button);
            mBundle.putInt(BProgressDialog.BUNDLE_BUTTONS_TYPE, 1);
            return this;
        }

        /**
         * Build and prepares the {@link BDialog} listener. You can show the dialog when you want
         * by calling show() on the instance.
         *
         * @return The BDialog built from the builder information.
         * @throws IllegalStateException If some computation goes wrong.
         */
        public BDialog build() {
            BDialog dialog = BProgressDialog.newInstance();
            dialog.verify(mBundle);
            ((DialogFragment) dialog).setArguments(mBundle);
            return dialog;
        }

        /**
         * Build and prepares the {@link BDialog} instance, and showing it for the specified
         * activity.
         *
         * @param activity The activity you want the dialog be presented. The activity may
         *                 implement {@link BDialogListener}
         * @return The BDialog built from the builder information.
         */
        public BDialog buildAndShow(Activity activity) {
            BDialog dialog = build();
            dialog.show(activity);
            return dialog;
        }
    }


}
