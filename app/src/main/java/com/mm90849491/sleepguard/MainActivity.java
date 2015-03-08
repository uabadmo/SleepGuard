package com.mm90849491.sleepguard;


import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    ImageButton runCommand;//FAB button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runCommand = (ImageButton)findViewById(R.id.runCommand);
        runCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "New Profile", Toast.LENGTH_SHORT).show();
                startActivity( new Intent(getApplicationContext(), EditProfile.class ));
            }
        });

        FragmentManager fm = getFragmentManager();

        if (fm.findFragmentById(android.R.id.content) == null) {
            SimpleListFragment list = new SimpleListFragment();
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }

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
        }else if(id == R.id.action_profile){
            Toast.makeText(MainActivity.this, "Selected Profiles", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void  btnOnClick(View v) {
        finish();
        System.exit(0);
    }


    public static class SimpleListFragment extends ListFragment
    {

        String[] names = new String[] { "Meng", "Matt", "Alex" };

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    inflater.getContext(), android.R.layout.simple_list_item_1,
                    names);
            setListAdapter(adapter);
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }


}

