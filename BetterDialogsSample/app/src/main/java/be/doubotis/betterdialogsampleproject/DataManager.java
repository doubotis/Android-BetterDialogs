package be.doubotis.betterdialogsampleproject;

import android.os.Handler;

import java.util.ArrayList;

/**
 * Created by Christophe on 12-03-16.
 */
public class DataManager {

    // DataManager
    private static DataManager __INSTANCE;

    // Variables
    private ArrayList<SingletonListener> mListeners = new ArrayList<SingletonListener>();

    public static DataManager getInstance() {
        if (__INSTANCE == null)
            __INSTANCE = new DataManager();
        return __INSTANCE;
    }

    public void addListener(SingletonListener listener) {
        mListeners.add(listener);
    }

    public void removeListener(SingletonListener listener) {
        mListeners.remove(listener);
    }

    public void asyncTimer()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < mListeners.size(); i++)
                    mListeners.get(i).onAsyncTimerResponse();
            }
        }, 3000);
    }

    public void asyncProgress()
    {
        final Handler h = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i=0; i <= 100; i++) {

                    try { Thread.sleep(100); } catch (Exception e){};

                    final int finalI = i;
                    h.post(new Runnable() {
                        @Override
                        public void run() {

                            for (int j = 0; j < mListeners.size(); j++)
                                mListeners.get(j).onAsyncProgress(finalI);
                        }
                    });
                }

            }
        }).start();
    }

    // ============================================================================================

    public interface SingletonListener {
        public void onAsyncTimerResponse();
        public void onAsyncProgress(int progress);
    }
}
