package be.doubotis.betterdialogs;

import android.app.Activity;
import android.app.Fragment;

/** Util class to get dialogs instances in an activity.
 * Created by Christophe on 20-02-16.
 */
public class BDialogs
{
    /** Get a {@link BDialog} instance corresponding to the specified tag for an activity.
     * @param activity The activity to lookup dialogs.
     * @param dialogTag The tag passed to the
     * @return Returns the BDialog instance if something is found, null otherwise.
     */
    public static BDialog get(Activity activity, int dialogTag)
    {
        Fragment frag = activity.getFragmentManager().findFragmentByTag("dialog" + dialogTag);
        return (BDialog)frag;
    }
}
