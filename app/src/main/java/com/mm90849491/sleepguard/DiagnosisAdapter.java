package com.mm90849491.sleepguard;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by M.Meng on 2015/04/09.
 */
public class DiagnosisAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Schedule> schedules;
    private Context ctx;
    private int resourceID;

    public DiagnosisAdapter(Context context, int resourceId, ArrayList<Schedule> objects) {
        this.ctx =context;
        this.resourceID = resourceId;
        this.schedules = objects;
    }

    @Override
    public int getCount() {
        if(this.schedules == null) {
            return 0;
        } else {
            return this.schedules.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return this.schedules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.schedule_item, null);
        }
        Schedule s = this.schedules.get(position);
        if(s != null) {
            TextView scheduleDate = (TextView) v.findViewById(R.id.txtSchedulingDate);
            ImageView scheduleStatus = (ImageView) v.findViewById(R.id.imgStatus);
            if(scheduleDate != null) {
                scheduleDate.setText(s.getID());
            }
            if(scheduleStatus != null) {
                scheduleStatus.setColorFilter(R.color.primaryLight);
            }
        }
        return v;
    }
}
