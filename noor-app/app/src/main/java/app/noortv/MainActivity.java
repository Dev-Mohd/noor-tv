package app.noortv;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/** Opens this shortcut's video in the YouTube TV app, then gets out of the way. */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        if (BuildConfig.REMINDERS) {
            Reminder.schedule(this);
            Adhan.schedule(this);
            MessageServer.start(this);
        }
        // Morning adhkar from 03:00 until 15:00 (before Asr), evening adhkar the rest of the day.
        int hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY);
        boolean evening = !BuildConfig.VIDEO_EVENING.isEmpty() && (hour >= 15 || hour < 3);
        Uri video = Uri.parse("https://www.youtube.com/watch?v=" + (evening ? BuildConfig.VIDEO_EVENING : BuildConfig.VIDEO));
        Intent yt = new Intent(Intent.ACTION_VIEW, video).setPackage("com.google.android.youtube.tv");
        try {
            startActivity(yt);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, video));
        }
        finish();
    }
}
