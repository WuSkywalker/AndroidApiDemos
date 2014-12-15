package com.ytf.api.demos.accessibility;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

/**
 * Package: com.ytf.api.demos.accessibility
 * Created with AndroidApiDemos
 * User: WuHua
 * Date: 14-12-15
 * Time: 23:55
 * Description: Starts up the task list that will interact with AccessibilityService sample.
 */
public class TaskListActivity extends ListActivity{
    private static final String TAG = "TaskListActivity";

    /** An intent for launching the system settings. */
    private static final Intent sSettingsIntent =
            new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);

    /**
     * Called when the activity is first created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
