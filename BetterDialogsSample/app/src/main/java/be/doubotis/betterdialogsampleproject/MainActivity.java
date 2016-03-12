package be.doubotis.betterdialogsampleproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import be.doubotis.betterdialogs.BDialog;
import be.doubotis.betterdialogs.BDialogs;
import be.doubotis.betterdialogs.support.BAlertDialog;
import be.doubotis.betterdialogs.support.BProgressDialog;

public class MainActivity extends AppCompatActivity implements BAlertDialog.BAlertDialogListener, DataManager.SingletonListener {

    // Constants for Dialogs, allowing to know what dialog is for ?
    public static final int DIALOG_TAG_SIMPLE_ALERT_DIALOG = 0;
    public static final int DIALOG_TAG_SIMPLE_ALERT_DIALOG_WITH_BUTTONS = 1;
    public static final int DIALOG_TAG_ALERT_DIALOG_WITH_VIEW = 2;
    public static final int DIALOG_TAG_ALERT_DIALOG_THAT_DISAPPEAR = 3;
    public static final int DIALOG_TAG_ALERT_DIALOG_CUSTOM_STYLE = 4;
    private static final int DIALOG_TAG_PROGRESS_DIALOG = 5;
    private static final int DIALOG_TAG_PROGRESS_DIALOG_UPDATE_PROGRESS = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((Button) findViewById(R.id.btn1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSimpleAlertDialog();
            }
        });

        ((Button) findViewById(R.id.btn2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSimpleAlertDialogWithButtons();
            }
        });

        ((Button) findViewById(R.id.btn3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlertDialogWithView();
            }
        });

        ((Button) findViewById(R.id.btn4)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlertDialogDismissedAutomatically();
            }
        });

        ((Button) findViewById(R.id.btn5)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlertDialogWithCustomStyle();
            }
        });

        ((Button) findViewById(R.id.btn6)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProgressDialog();
            }
        });

        ((Button) findViewById(R.id.btn7)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProgressDialogFileDownload();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        DataManager.getInstance().addListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        DataManager.getInstance().removeListener(this);
    }

    private void createSimpleAlertDialog() {
        new BAlertDialog.Builder(DIALOG_TAG_SIMPLE_ALERT_DIALOG)
                .setTitle("Simple Dialog")
                .setCaption("Awesome content!")
                .setButtons(new int[]{ android.R.string.ok })
                .buildAndShow(MainActivity.this);
    }

    private void createSimpleAlertDialogWithButtons() {
        new BAlertDialog.Builder(DIALOG_TAG_SIMPLE_ALERT_DIALOG_WITH_BUTTONS)
                .setTitle("Dialog w/ Buttons")
                .setCaption("Here is 3 beautiful buttons")
                .setButtons(new String[]{"Button A", "Button B", "Button C"})
                .buildAndShow(MainActivity.this);
    }

    private void createAlertDialogWithView() {
        SampleParcelableView spv = new SampleParcelableView();

        new BAlertDialog.Builder(DIALOG_TAG_ALERT_DIALOG_WITH_VIEW)
                .setTitle("Custom View")
                .setView(spv)
                .setButtons(new String[]{"Button A"})
                .buildAndShow(MainActivity.this);
    }

    private void createAlertDialogDismissedAutomatically()
    {
        new BAlertDialog.Builder(DIALOG_TAG_ALERT_DIALOG_THAT_DISAPPEAR)
                .setTitle("Dismissed automatically")
                .setCaption("I will disappear after 3 seconds!")
                .setCancelable(false)
                .buildAndShow(MainActivity.this);

        DataManager.getInstance().asyncTimer();
    }

    private void createAlertDialogWithCustomStyle()
    {
        new BAlertDialog.Builder(DIALOG_TAG_ALERT_DIALOG_CUSTOM_STYLE)
                .setTitle("Custom Style")
                .setCaption("A beautiful dialog with a custom style")
                .setStyle(R.style.AppTheme)
                .setButtons(new String[] {"Dismiss"})
                .buildAndShow(MainActivity.this);
    }

    private void createProgressDialog()
    {
        new BProgressDialog.Builder(DIALOG_TAG_PROGRESS_DIALOG)
                .setCaption("Please wait")
                .setIndeterminate(true)
                .setButton(android.R.string.cancel)
                .buildAndShow(MainActivity.this);
    }

    private void createProgressDialogFileDownload()
    {
        new BProgressDialog.Builder(DIALOG_TAG_PROGRESS_DIALOG_UPDATE_PROGRESS)
                .setTitle("Progress update...")
                .setCancelable(false)
                .buildAndShow(MainActivity.this);

        DataManager.getInstance().asyncProgress();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onDialogButtonClick(BDialog dialog, int buttonClicked) {

        if (dialog.getDialogTag() == DIALOG_TAG_SIMPLE_ALERT_DIALOG_WITH_BUTTONS) {
            if (buttonClicked == BAlertDialog.BAlertDialogListener.BUTTON_NEUTRAL) {
                Toast.makeText(this, "Clicked Button C", Toast.LENGTH_SHORT).show();
            }
            else if (buttonClicked == BAlertDialog.BAlertDialogListener.BUTTON_POSITIVE) {
                Toast.makeText(this, "Clicked Button A", Toast.LENGTH_SHORT).show();
            }
            else if (buttonClicked == BAlertDialog.BAlertDialogListener.BUTTON_NEGATIVE) {
                Toast.makeText(this, "Clicked Button B", Toast.LENGTH_SHORT).show();
            }
        }

        if (dialog.getDialogTag() == DIALOG_TAG_ALERT_DIALOG_WITH_VIEW) {
            if (buttonClicked == BAlertDialog.BAlertDialogListener.BUTTON_POSITIVE) {

                SampleParcelableView pv = (SampleParcelableView)
                        ((BAlertDialog) dialog).getParcelableView();

                Toast.makeText(this, pv.getText() + " was taped.",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onAsyncTimerResponse() {
        BDialogs.get(MainActivity.this, DIALOG_TAG_ALERT_DIALOG_THAT_DISAPPEAR)
                .dismiss();
    }

    @Override
    public void onAsyncProgress(int progress) {
        BProgressDialog dialog = (BProgressDialog)
                BDialogs.get(MainActivity.this, DIALOG_TAG_PROGRESS_DIALOG_UPDATE_PROGRESS);

        if (dialog == null)
            return;

        if (progress == 100)
            dialog.getHandler().onFinished();
        else
            dialog.getHandler().onProgress(progress);

    }
}
