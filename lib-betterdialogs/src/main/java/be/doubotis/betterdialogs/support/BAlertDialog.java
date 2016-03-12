package be.doubotis.betterdialogs.support;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import be.doubotis.betterdialogs.BDialog;
import be.doubotis.betterdialogs.BDialogListener;
import be.doubotis.betterdialogs.ParcelableView;

/** This class wraps the default {@link android.app.AlertDialog} inside a mechanism of fragments.
 * Please use the {@link Builder} subclass to build it.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class BAlertDialog extends DialogFragment implements BDialog  {

    // Constants
    public static final String BUNDLE_BUTTONS = "dialog.buttons";
    private static final String BUNDLE_BUTTONS_TYPE = "dialog.buttons.type";
    private static final String BUNDLE_CUSTOM_VIEW = "dialog.customView";

    // Variables
    private ParcelableView mParcelableView;

    private static BAlertDialog newInstance() {
        BAlertDialog frag = new BAlertDialog();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public int getDialogTag() { return getArguments().getInt(BDialog.BUNDLE_TAG); }

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

        if (!bundle.containsKey(BDialog.BUNDLE_TAG))
            throw new IllegalStateException("Dialog Tag not defined");

        String[] buttons = getArguments().getStringArray(BAlertDialog.BUNDLE_BUTTONS);
        if (buttons == null) buttons = new String[] {};

        if (buttons.length > 3) {
            throw new
                    IllegalStateException("Too many buttons. Maximum of 3 buttons in BAlertDialog");
        }

        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int style = getArguments().getInt(BDialog.BUNDLE_STYLE, -1);
        int tag = getArguments().getInt(BDialog.BUNDLE_TAG);
        Bundle bundle = getArguments();
        String title = getArguments().getString(BDialog.BUNDLE_TITLE, null);
        String caption = getArguments().getString(BDialog.BUNDLE_CAPTION, null);
        boolean cancelable = getArguments().getBoolean(BDialog.BUNDLE_CANCELABLE, true);

        String[] buttons = null;
        if (getArguments().getInt(BAlertDialog.BUNDLE_BUTTONS_TYPE, -1) == 0)
            buttons = getArguments().getStringArray(BAlertDialog.BUNDLE_BUTTONS);
        else if (getArguments().getInt(BAlertDialog.BUNDLE_BUTTONS_TYPE, -1) == 1) {
            int[] intButtons = getArguments().getIntArray(BAlertDialog.BUNDLE_BUTTONS);
            buttons = convertResToString(intButtons);
        } else {
            if (buttons == null) buttons = new String[] {};
        }

        mParcelableView = getArguments().getParcelable(BAlertDialog.BUNDLE_CUSTOM_VIEW);

        android.support.v7.app.AlertDialog.Builder b = null;

        if (style == -1)
            b = new android.support.v7.app.AlertDialog.Builder(getActivity());
        else
            b = new android.support.v7.app.AlertDialog.Builder(getActivity(), style);

        if (title != null)
            b.setTitle(title);

        if (caption != null)
            b.setMessage(caption);

        if (mParcelableView != null) {
            View v = mParcelableView.getView(getActivity(), (BAlertDialogListener) getActivity());
            ParcelableView.Padding pad = mParcelableView.getPadding(getActivity());
            if (v != null) {
                b.setView(v, pad.left, pad.top, pad.right, pad.bottom);
            }
        }

        if (buttons.length >= 1)
        {
            b.setPositiveButton(buttons[0],
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ((BAlertDialogListener) getActivity()).onDialogButtonClick(
                                    BAlertDialog.this, BAlertDialogListener.BUTTON_POSITIVE);
                            dismiss();
                        }
                    }
            );
        }

        if (buttons.length >= 2) {
            b.setNegativeButton(buttons[1],
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ((BAlertDialogListener) getActivity()).onDialogButtonClick(
                                    BAlertDialog.this, BAlertDialogListener.BUTTON_NEGATIVE);
                            dismiss();
                        }
                    }
            );
        }

        if (buttons.length >= 3) {
            b.setNeutralButton(buttons[2],
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ((BAlertDialogListener) getActivity()).onDialogButtonClick(
                                    BAlertDialog.this, BAlertDialogListener.BUTTON_NEUTRAL);
                            dismiss();
                        }
                    }
            );
        }

        AlertDialog ad = b.create();
        ad.setCancelable(cancelable);
        ad.setCanceledOnTouchOutside(cancelable);
        setCancelable(cancelable);

        return ad;
    }

    private String[] convertResToString(int[] intButtons)
    {
        String[] res = new String[intButtons.length];

        for (int i=0; i < intButtons.length; i++) {
            res[i] = getActivity().getString(intButtons[i]);
        }

        return res;
    }

    public ParcelableView getParcelableView() {
        return mParcelableView;
    }

    // ===========================================================================================

    public interface BAlertDialogListener {

        public static final int BUTTON_POSITIVE = 200;
        public static final int BUTTON_NEGATIVE = 201;
        public static final int BUTTON_NEUTRAL = 202;

        public void onDialogButtonClick(BDialog dialog, int buttonClicked);
    }

    // ===========================================================================================

    /** Helper to build a {@link BAlertDialog}. Methods returns the instance itself, so you can
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
         * @param title The title to set.
         * @return The same instance of Builder, allowing chaining calls.
         */
        public Builder setTitle(String title) {
            mBundle.putString(BDialog.BUNDLE_TITLE, title);
            return this;
        }

        /**
         * Set the caption of the dialog.
         * @param caption The caption to set.
         * @return The same instance of Builder, allowing chaining calls.
         */
        public Builder setCaption(String caption) {
            mBundle.putString(BDialog.BUNDLE_CAPTION, caption);
            return this;
        }

        /** Set the custom view for the dialog. Will appear in the place of caption.
         * @param parcelableView A parcelable object (due to limitations on the DialogFragments),
         *                       that implements the {@link ParcelableView} interface.
         * @return The same instance of Builder, allowing chaining calls.
         */
        public Builder setView(ParcelableView parcelableView) {
            mBundle.putParcelable(BAlertDialog.BUNDLE_CUSTOM_VIEW, parcelableView);
            return this;
        }

        /**
         * Set the cancelable status of the dialog.
         * @param cancelable True to allow the dialog to be cancelled by taping outside.
         * @return The same instance of Builder, allowing chaining calls.
         */
        public Builder setCancelable(boolean cancelable) {
            mBundle.putBoolean(BProgressDialog.BUNDLE_CANCELABLE, cancelable);
            return this;
        }

        /**
         * Set the buttons of the alert dialog.
         * Used for {@link BAlertDialog}.
         *
         * @param buttons The buttons to set. Could be an array of strings with 0, 1, 2 or
         *                3 elements.
         * @return The same instance of Builder, allowing chaining calls.
         */
        public Builder setButtons(String[] buttons) {
            mBundle.putStringArray(BAlertDialog.BUNDLE_BUTTONS, buttons);
            mBundle.putInt(BAlertDialog.BUNDLE_BUTTONS_TYPE, 0);
            return this;
        }

        /**
         * Set the buttons of the alert dialog.
         * Used for {@link BAlertDialog}.
         *
         * @param buttons The buttons to set. Could be an array of strings with 0, 1, 2 or
         *                3 elements.
         * @return The same instance of Builder, allowing chaining calls.
         */
        public Builder setButtons(int[] buttons) {
            mBundle.putIntArray(BAlertDialog.BUNDLE_BUTTONS, buttons);
            mBundle.putInt(BAlertDialog.BUNDLE_BUTTONS_TYPE, 1);
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
            BDialog dialog = BAlertDialog.newInstance();
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
