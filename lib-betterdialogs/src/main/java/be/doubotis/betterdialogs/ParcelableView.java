package be.doubotis.betterdialogs;

import android.content.Context;
import android.os.Parcelable;
import android.view.View;

import be.doubotis.betterdialogs.support.BAlertDialog;

/** An interface that allows to create views inside {@link BAlertDialog} instances.
 * Created by Christophe on 10-03-16.
 */
public interface ParcelableView extends Parcelable {

    /** Get the main view of the element. */
    public View getView(Context context, BAlertDialog.BAlertDialogListener eventProvider);

    public Padding getPadding(Context context);

    // ============================================================================================

    public static class Padding
    {
        public int top;
        public int bottom;
        public int left;
        public int right;

        public Padding(int top, int left, int bottom, int right) {
            this.top = top;
            this.left = left;
            this.bottom = bottom;
            this.right = right;
        }
    }
}
