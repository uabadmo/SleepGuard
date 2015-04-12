package com.mm90849491.sleepguard.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mm90849491.sleepguard.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by M.Meng on 2015/04/10.
 */
public class SchedulerAdapter extends BaseExpandableListAdapter {
    protected static final int[] IconMap = { android.R.drawable.ic_menu_report_image,
                                                android.R.drawable.ic_menu_edit,
            android.R.drawable.ic_menu_view,
            android.R.drawable.ic_menu_compass,
            android.R.drawable.ic_menu_search,
            android.R.drawable.ic_menu_my_calendar,
            android.R.drawable.ic_btn_speak_now

    };
    private Context ctx;
    private String expandHeader;
    private List<String> expandItems;
    private int[] itemIcons;
    private TextView[] txtChildren;
    private ImageButton[] btnChildren;

    public SchedulerAdapter(Context context, String listDataHeader, List<String> listChildData, int[] icons) {
        this.ctx = context;
        this.expandHeader = listDataHeader;
        this.expandItems = listChildData;
        this.itemIcons = icons;
        this.txtChildren = new TextView[icons.length];
        this.btnChildren = new ImageButton[icons.length];
    }

    public void setText(int childPosition, String text) {
        this.expandItems.set(childPosition, text);
        this.txtChildren[childPosition].setText(text);
    }

    public String getText(int childPosition) {
        return this.expandItems.get(childPosition);
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.expandItems.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.expandHeader;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.expandItems.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expand_header, null);
        }

        TextView txtItems = (TextView) convertView.findViewById(R.id.txtHeader);

        txtItems.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expand_items, null);
        }

        TextView txt = (TextView) convertView.findViewById(R.id.txtItem0);
        ImageButton btn = (ImageButton) convertView.findViewById(R.id.btnItem0);

        txt.setText(this.expandItems.get(childPosition));
        if(this.itemIcons[childPosition] == 0) {
            btn.setImageResource(android.R.color.transparent);
        } else {
            btn.setImageResource(IconMap[this.itemIcons[childPosition]]);
        }
        this.txtChildren[childPosition] = txt;
        this.btnChildren[childPosition] = btn;
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
