package main.com.display;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Muazzam on 1/2/2016.
 */
/*
 * MainActivity class that loads MainFragment
 */
public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
