package be.doubotis.betterdialogsampleproject;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;

import be.doubotis.betterdialogs.ParcelableView;
import be.doubotis.betterdialogs.support.BAlertDialog;

/**
 * Created by Christophe on 10-03-16.
 */
public class SampleParcelableView implements Parcelable, ParcelableView {

    public static final int EVENT_FIRED = 10;

    private Parcelable mEditTextState = null;

    private EditText mEditText;

    public SampleParcelableView() {

    }

    @Override
    public View getView(Context context, BAlertDialog.BAlertDialogListener eventProvider) {

        if (mEditText != null) {
            Parcelable p = mEditText.onSaveInstanceState();
            mEditText = new EditText(context);
            mEditText.onRestoreInstanceState(p);
            return mEditText;
        } else {
            mEditText = new EditText(context);
            return mEditText;
        }
    }

    @Override
    public Padding getPadding(Context context) {
        return new Padding(0,10,10,10);
    }

    // ===========================================================================================

    protected SampleParcelableView(Parcel in) {
        mEditTextState = in.readParcelable(this.getClass().getClassLoader());
    }

    public static final Creator<SampleParcelableView> CREATOR = new Creator<SampleParcelableView>() {
        @Override
        public SampleParcelableView createFromParcel(Parcel in) {
            return new SampleParcelableView(in);
        }

        @Override
        public SampleParcelableView[] newArray(int size) {
            return new SampleParcelableView[size];
        }
    };





    @Override
    public int describeContents() {
        return 0;
    }

    public String getText() {
        return mEditText.getText().toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        mEditTextState = mEditText.onSaveInstanceState();
        parcel.writeParcelable(mEditTextState, 0);
    }
}
