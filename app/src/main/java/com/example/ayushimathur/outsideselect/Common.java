package com.example.ayushimathur.outsideselect;

/**
 * Created by shwethambari on 8/3/16.
 */
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Common {

    ArrayList<HashMap<String, Object>> inboxList;
    private AppAdapter mAdapter;

    public static final String TAG_NAME = "name";
    public static final String TAG_LOCATION = "location";
    public static final String TAG_STIME = "stime";
    public static final String TAG_IMG = "image";
    public static final String TAG_DURATION = "duration";
    public Context cntxt;

    public void AddToMap(Context context, JSONArray artistList) {
        inboxList = new ArrayList<HashMap<String, Object>>();
        int id2 = 0;
        String from = "", location = "", stime = "", img = "", duration = "", start_time = "";

        for (int i = 0; i < artistList.length(); i++) {
            try {
                JSONObject a = artistList.getJSONObject(i);
                // Storing each json item in variable
                from = a.getString("artist");
                location = a.getString("zone");
                start_time = a.getString("start_time");
                img = a.getString("big_image_url");
                img = "a" + img;
                id2 = context.getResources().getIdentifier(img, "raw", context.getPackageName());
                duration = a.getString("duration_minutes");
                String[] times = start_time.split(" ");
                stime = times[1];
                duration = duration + " mins";
            } catch (JSONException e) {

            }

            // creating new HashMap
            HashMap<String, Object> map = new HashMap<String, Object>();

            // adding each child node to HashMap key => value
            map.put(TAG_NAME, from);
            map.put(TAG_LOCATION, location);
            map.put(TAG_STIME, stime);
            map.put(TAG_IMG, id2);
            map.put(TAG_DURATION, duration);

            // adding HashList to ArrayList
            this.inboxList.add(map);
        }
    }

    class AppAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return inboxList.size();
        }

        @Override
        public HashMap<String, Object> getItem(int position) {
            return inboxList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }



        @Override
        public int getViewTypeCount() {
            // menu type count
            return 3;
        }

        @Override
        public int getItemViewType(int position) {
            // current menu type
            return position % 3;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(cntxt,
                        R.layout.tab1_xml, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            Log.d("position", String.valueOf(position));
            HashMap item = getItem(position);
            Log.d("tag image name",String.valueOf(item));
            holder.icon.setImageResource((Integer)item.get("image"));
            holder.from.setText(item.get("name").toString());
            holder.location.setText(item.get("location").toString());
            holder.stime.setText(item.get("stime").toString());
            return convertView;
        }

        class ViewHolder {
            ImageView icon;
            TextView from,location,stime;

            public ViewHolder(View view) {
                icon = (ImageView) view.findViewById(R.id.icon);
                from = (TextView) view.findViewById(R.id.from);
                location = (TextView) view.findViewById(R.id.location);
                stime = (TextView) view.findViewById(R.id.stime);
                view.setTag(this);
            }
        }
    }



}

