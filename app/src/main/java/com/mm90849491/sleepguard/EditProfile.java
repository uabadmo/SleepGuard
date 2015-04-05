package com.mm90849491.sleepguard;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;


public class EditProfile extends Activity {
    public final static String NEW_PROFILE = "com.mm90849491.sleepguard.EDIT_PROFILE";
    private String fileName;
    private Profile newProfile;
    private boolean changed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                this.fileName = null;
            } else {
                this.fileName = extras.getString(NEW_PROFILE);
            }
        } else {
            this.fileName = (String) savedInstanceState.getSerializable(NEW_PROFILE);
        }

    }

    @Override
    protected void  onStart() {
        super.onStart();
        Context ctx = this.getApplicationContext();
        if(this.fileName == null) {
            this.newProfile = new Profile(ctx);
            this.newProfile.user = new Client();
            this.newProfile.doctor = new Clinician("");

        } else {
            File temp = new File(ctx.getFilesDir(), this.fileName);
            this.newProfile = new Profile(ctx, temp);
        }

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

    public void onClickSave(View v) {
        v.setBackgroundResource(R.color.primaryLight);
        if(this.changed) {
            try {
                this.newProfile.save();
                Toast.makeText(EditProfile.this, "Saved", Toast.LENGTH_SHORT).show();
                this.changed = false;

            } catch (IOException e) {
                //ignore
                Toast.makeText(EditProfile.this, "Failed to save this profile", Toast.LENGTH_SHORT).show();
            }
        }
        (new Handler()).postDelayed(new ClickEffect(v, R.color.primary), 100);

    }

    public void onClickCancel(View v) {
        v.setBackgroundColor(getResources().getColor(R.color.primaryLight));
        this.changed = false;
        (new Handler()).postDelayed(new ClickEffect(v, R.color.primary), 100);
        super.onBackPressed();
    }

    public void etxtLeave(View v) {
        switch (v.getId()) {
            case R.id.etxtFirstName:
                if ( !((EditText) v).getText().toString().trim().isEmpty()) {
                    if (!((EditText) v).getText().toString().trim().equals(getString(R.string.text_first_name))) {
                        if (!this.newProfile.user.firstName().equals(((EditText) v).getText().toString().trim())) {
                            this.newProfile.user.firstName(((EditText) v).getText().toString());
                            this.changed = true;
                        }
                    }
                } else if (this.newProfile.user.firstName() != null) {
                    this.newProfile.user.firstName(null);
                    this.changed = true;
                }
                break;
            case R.id.etxtLastName:
                if ( !((EditText) v).getText().toString().trim().isEmpty()) {
                    if(!((EditText) v).getText().toString().trim().equals(getString(R.string.text_last_name)) ) {
                        if (!this.newProfile.user.lastName().equals(((EditText) v).getText().toString().trim())) {
                            this.newProfile.user.lastName(((EditText) v).getText().toString());
                            this.changed = true;
                        }
                    }
                } else if(this.newProfile.user.lastName() != null) {
                    this.newProfile.user.lastName(null);
                    this.changed = true;
                }
                break;
            case R.id.etxtEmail:
                if ( !((EditText) v).getText().toString().trim().isEmpty()) {
                    if(!((EditText) v).getText().toString().trim().equals(getString(R.string.text_e_mail)) ) {
                        if (this.newProfile.user.emailAddress() == null ||
                                !this.newProfile.user.emailAddress().equals(((EditText) v).getText().toString().trim())) {
                            this.newProfile.user.emailAddress(((EditText) v).getText().toString());
                            this.changed = true;
                        }
                    }
                } else if(this.newProfile.user.emailAddress() != null) {
                    this.newProfile.user.emailAddress(null);
                    this.changed = true;
                }
                break;


        }
    }
}
