package com.mm90849491.sleepguard.UI;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mm90849491.sleepguard.Objects.Profile;
import com.mm90849491.sleepguard.R;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 */
public class ProfileList extends Fragment implements ListView.OnItemClickListener {
    /**
     * The fragment's ListView/GridView.
     */
    private ListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;
    private String[] strListView;
    private ArrayList<Profile> profiles;
    private boolean showLastName;
    private boolean showFirstName;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProfileList()  {
        this.profiles = null;
        this.showFirstName = true;
        this.showLastName = true;
    }

    public void setProfiles(ArrayList<Profile> that, boolean showFirstName, boolean showLastName) {
        this.profiles = that;
        this.showFirstName = showFirstName;
        this.showLastName = showLastName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        // Set the adapter
        this.mListView = (ListView) view.findViewById(R.id.lstProfile);

        // Set OnItemClickListener so we can be notified on item clicks
        this.mListView.setOnItemClickListener(this);

        if(this.profiles != null) {
            this.strListView = new String[this.profiles.size()];
            for (int i = this.strListView.length - 1; i >= 0; i--) {
                if(this.showFirstName && this.showLastName) {
                    this.strListView[i] = this.profiles.get(i).user.firstName() + " " + this.profiles.get(i).user.lastName();
                } else if(this.showFirstName) {
                    this.strListView[i] = this.profiles.get(i).user.firstName();
                } else if(this.showLastName) {
                    this.strListView[i] = this.profiles.get(i).user.lastName();
                } else  {
                    this.strListView[i] = String.format("Profile-%0" + Profile.digits + "d", i);
                }
            }
            this.mAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, strListView);
            this.mListView.setAdapter(this.mAdapter);
        }

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        view.setBackgroundResource(R.color.listBackgroundOnclick);
        (new Handler()).postDelayed(new ClickEffect(view, R.color.listBackground), 100);
        Intent that = new Intent(parent.getContext(), DiagnosisManager.class);
        Bundle extras = new Bundle();
        extras.putString(DiagnosisManager.DISPLAY_NAME, ((TextView) view).getText().toString().trim() );
        extras.putString(EditProfile.NEW_PROFILE, Profile.saveName(position));
        that.putExtras(extras);
        startActivity(that);
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

}
