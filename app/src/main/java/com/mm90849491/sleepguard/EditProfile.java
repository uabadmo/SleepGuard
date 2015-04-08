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
    private EditText etxtFirstName;
    private EditText etxtLastName;
    private EditText etxtEmail;
    private String fileName;
    private Profile newProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                this.fileName = Profile.saveName(99);
            } else {
                this.fileName = extras.getString(NEW_PROFILE);
            }
        } else {
            this.fileName = (String) savedInstanceState.getSerializable(NEW_PROFILE);
        }

        this.etxtFirstName = (EditText)findViewById(R.id.etxtFirstName);
        this.etxtLastName = (EditText)findViewById(R.id.etxtLastName);
        this.etxtEmail = (EditText)findViewById(R.id.etxtEmail);

    }

    @Override
    protected void  onStart() {
        super.onStart();
        Context ctx = this.getApplicationContext();
        File temp = new File(ctx.getFilesDir(), this.fileName);
        if(temp.exists()) {
            this.newProfile = new Profile(ctx, temp);
            if (this.newProfile.user.firstName() != null) {
                this.etxtFirstName.setText(this.newProfile.user.firstName());
            }

            if (this.newProfile.user.lastName() != null) {
                this.etxtLastName.setText(this.newProfile.user.lastName());
            }
            if (this.newProfile.user.emailAddress() != null) {
                this.etxtEmail.setText(this.newProfile.user.emailAddress());
            }
        } else {
            this.newProfile = new Profile(ctx);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
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

    public void onClickSave(View that) {
        boolean changed = false;
        EditText v;
        String dummy;
        String oldString;
        String newString;
        that.setBackgroundResource(R.color.primaryLight);

        /* null sensitive  */
        v = this.etxtFirstName;
        dummy = getResources().getString(R.string.text_first_name);
        oldString = this.newProfile.user.firstName();
        if(v.getText().toString() == null || v.getText().toString().trim().isEmpty()
                || v.getText().toString().trim().equals(dummy) ) {
            if(oldString != null) {
                changed = true;
                this.newProfile.user.firstName(null);
            }
        } else {
            newString = v.getText().toString().trim();
            if(oldString == null || !oldString.equals(newString)) {
                changed = true;
                this.newProfile.user.firstName(newString);
            }
        }

        v = this.etxtLastName;
        dummy = getResources().getString(R.string.text_last_name);
        oldString = this.newProfile.user.lastName();
        if(v.getText().toString() == null || v.getText().toString().trim().isEmpty()
                || v.getText().toString().trim().equals(dummy) ) {
            if(oldString != null) {
                changed = true;
                this.newProfile.user.lastName(null);
            }
        } else {
            newString = v.getText().toString().trim();
            if(oldString == null || !oldString.equals(newString)) {
                changed = true;
                this.newProfile.user.lastName(newString);
            }
        }

        /* null non-sensitive  */
        v = this.etxtEmail;
        dummy = getResources().getString(R.string.text_e_mail);
        oldString = this.newProfile.user.emailAddress();
        if(v.getText().toString() == null || v.getText().toString().trim().isEmpty()
                || v.getText().toString().trim().equals(dummy) ) {
            if(oldString != null) {
                changed = true;
                this.newProfile.user.emailAddress(null);
            }
        } else {
            newString = v.getText().toString().trim();
            if(oldString == null || !oldString.equals(newString)) {
                changed = true;
                this.newProfile.user.emailAddress(newString);
            }
        }

        if(changed) {
            try {
                this.newProfile.save();
                Toast.makeText(EditProfile.this, "Saved", Toast.LENGTH_SHORT).show();


            } catch (IOException e) {
                //ignore
                Toast.makeText(EditProfile.this, "Failed to save this profile", Toast.LENGTH_SHORT).show();
            }
        }
        (new Handler()).postDelayed(new ClickEffect(that, R.color.primary), 100);

    }

    public void onClickCancel(View v) {
        v.setBackgroundColor(getResources().getColor(R.color.primaryLight));
        super.onBackPressed();
    }
}
