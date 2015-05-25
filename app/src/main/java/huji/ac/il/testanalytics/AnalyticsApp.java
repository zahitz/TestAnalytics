package huji.ac.il.testanalytics;

import android.app.Application;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.parse.Parse;
import com.parse.ParseCrashReporting;

/**
 * Created by Zahi on 25/05/2015.
 */
public class AnalyticsApp extends Application {
    public static GoogleAnalytics analytics;
    public static Tracker tracker;
    @Override
    public void onCreate() {
        super.onCreate();

        // Parse init
        ParseCrashReporting.enable(this);
        Parse.initialize(this, "YIy8C7YWsebzIMzTleNrifWh6zqBnk5cViWncy3D", "T5EkAeOpOT1IlWgwpTY18PwCQgVmdNUW1ellNjuh");

        // Google init
        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);
        tracker = analytics.newTracker("UA-63201165-1");
        tracker.enableExceptionReporting(true);
        tracker.enableAutoActivityTracking(true);

        // Flurry
        FlurryAgent.init(this, "STQDWCHMHQP8ZJ2NDSJQ");
    }
}
