package com.mm90849491.sleepguard;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;


public class EditProfile extends ActionBarActivity {
    private Profile newProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        this.newProfile = new Profile();
        this.newProfile.user = new Client();

        final EditText etxtFirstName = (EditText)findViewById(R.id.etxtFirstName);
        etxtFirstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    etxtLeave(v);
                }
            }
        });

        final EditText etxtLastName = (EditText)findViewById(R.id.etxtLastName);
        etxtLastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    etxtLeave(v);
                }
            }
        });

        final EditText etxtEmail = (EditText)findViewById(R.id.etxtEmail);
        etxtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    etxtLeave(v);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
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

    public void etxtLeave(View v) {
        try {
            switch (v.getId()) {
                case R.id.etxtFirstName :
                    if( ! this.newProfile.user.firstName().equals( ((EditText) v).getText().toString().trim() ) )
                        this.newProfile.user.firstName( ((EditText) v).getText().toString() );
                    break;
                case R.id.etxtLastName :
                    if( ! this.newProfile.user.lastName().equals( ((EditText) v).getText().toString().trim() ) )
                        this.newProfile.user.lastName( ((EditText) v).getText().toString() );
                    break;
                case R.id.etxtEmail :
                    if( ! this.newProfile.user.emailAddress().equals( ((EditText) v).getText().toString().trim() ) )
                        this.newProfile.user.emailAddress( ((EditText) v).getText().toString() );
                    break;

            }
            this.newProfile.save(getApplicationContext());
            Toast.makeText(EditProfile.this, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            //ignore
            Toast.makeText(EditProfile.this, "Failed to save this profile", Toast.LENGTH_SHORT).show();
        }

    }
}
