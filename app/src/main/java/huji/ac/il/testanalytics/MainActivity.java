package huji.ac.il.testanalytics;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.parse.ParseAnalytics;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class MainActivity extends ActionBarActivity {

    Button count, next, crash;
    Tracker tracker;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Parse
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        // Google
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(MainActivity.this);
        tracker = analytics.newTracker("UA-63201165-1");
        tracker.setScreenName("main screen");

        count = (Button) findViewById(R.id.count);
        crash = (Button) findViewById(R.id.crash);
        next = (Button) findViewById(R.id.next);
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> dimensions = new HashMap<String, String>();
                dimensions.put("number", Integer.valueOf(i).toString());
                i++;

                // Parse
                ParseAnalytics.trackEventInBackground("button", dimensions);

                // Google
                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("UX")
                        .setAction("button")
                        .setLabel(Integer.valueOf(i).toString())
                        .build());

                // Flurry
                FlurryAgent.logEvent("button", dimensions);
            }
        });
        crash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                int r1 = r.nextInt(2);
                if (r1 == 1) {
                    throw new NullPointerException();
                } else {
                    throw new ArrayIndexOutOfBoundsException();
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent second = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(second);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
