package com.ytf.api.demos.accessibility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import com.ytf.api.demos.R;

/**
 * Package: com.ytf.api.demos.accessibility
 * Created with AndroidApiDemos
 * User: WuHua
 * Date: 14-12-15
 * Time: 23:21
 * Description: This is the entry activity for sample that demonstrates how to implement an
 * {@link android.accessibilityservice.AccessibilityService}.
 */
public class ClockBackActivity extends Activity {
    private static final String TAG = "ClockBackActivity";

    /**
     * An intent for launching the system settings.
     */
    private static final Intent sSettingsIntent =
            new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);

    /**
     * {@inheritDoc}
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accessibility_service);

        // Add a shortcut to the accessibility settings.
        ImageButton button = (ImageButton) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(sSettingsIntent);
            }
        });
    }
}
