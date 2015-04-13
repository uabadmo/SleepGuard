package com.mm90849491.sleepguard.UI;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mm90849491.sleepguard.R;


public class HelpDesk extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_desk);

        TextView manual = (TextView) findViewById(R.id.txtManual);
        manual.setText(Html.fromHtml(
                        "<h1>Sleep Guard</h1>\n" +
                        "<p><strong>Set Up</strong><br />\n" +
                        "  1. Create a new profile by pressing the button +.<br />\n" +
                        "  2. Fill in your information. You may leave it blank, the user name will be John Smith.<br />\n" +
                        "  3. You may change the display settings in the menu -&gt; setting page.<br />\n" +
                        "  4. Click the new profile you made to open the diagnosis manager.<br />\n" +
                        "  5. Create a new diagnosis by pressing the button +.</p>\n" +
                        "<p><strong>Generate Result</strong><br />\n" +
                        "  1. Choose your diagnosis which you want to generate the result, or create a new one.<br />\n" +
                        "  2. Fill in the Audio File tap first.<br />\n" +
                        "  3. You may enter a file name and schedule a record later, or record immediately.<br />\n" +
                        "  4. When the Audio File is ready, press the generate button. It may take few minutes.<br />\n" +
                        "  5. View the result if available, or try another file.</p>\n" +
                        "<p><strong>Edit</strong><br />\n" +
                        "  1. You may change the diagnosis setting by click the one in diagnosis manage.<br />\n" +
                        "  2. You need to select the profile and change it in menu -&gt; edit to edit the profile.<br />\n" +
                        "  3. A diagnosis cannot be edited after the result successfully generated.<br />\n" +
                        "  4. A schedule cannot be edited before it is executed.</p>\n" +
                        "<p><strong>Help</strong><br />\n" +
                        "  1. This page will pop-up only the first time this application is open.<br />\n" +
                        "  2. You can view this page by pressing the button ! in profile manager (top page).</p>"
        ));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_help_desk, menu);
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

    public void btnClose(View v) {
        super.onBackPressed();
    }
}
