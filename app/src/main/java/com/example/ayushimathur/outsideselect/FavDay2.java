package com.example.ayushimathur.outsideselect;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;


public class FavDay2 extends Activity {
    // Progress Dialog
    private ProgressDialog pDialog;

    ArrayList<HashMap<String, Object>> inboxList;

    private AppAdapter mAdapter;
    SwipeMenuListView listView;

    // ALL JSON node names

    public static final String TAG_NAME = "name";
    public static final String TAG_LOCATION = "location";
    public static final String TAG_STIME = "stime";
    public static final String TAG_IMG = "image";
    public static final String TAG_DURATION = "duration";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_day1);

        listView = (SwipeMenuListView) findViewById(R.id.list);

        // Hashmap for ListView
        inboxList = new ArrayList<HashMap<String, Object>>();

        // Loading INBOX in Background Thread
        new LoadInbox().execute();


        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // Create different menus depending on the view type
                switch (menu.getViewType()) {
                    case 0:
                        createMenu1(menu);
                        break;
//                    case 1:
//                        createMenu2(menu);
//                        break;
//                    case 2:
//                        createMenu3(menu);
//                        break;
                }
            }

            private void createMenu1(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(
                        getApplicationContext());
                item1.setBackground(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
                item1.setWidth(dp2px(150));
                item1.setTitle("Remove From List");
                item1.setTitleColor(R.color.colorPrimary);
                item1.setIcon(R.drawable.crossblack);
                menu.addMenuItem(item1);

            }


        };
        // set creator
        listView.setMenuCreator(creator);

        // step 2. listener item click event
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                HashMap item = inboxList.get(position);
                switch (index) {
                    case 0:
                        inboxList.remove(position);
                        SelectArtist.lovedArtists2.remove(position);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });


    }

    /**
     * Background Async Task to Load all INBOX messages by making HTTP Request
     */
    class LoadInbox extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FavDay2.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting Inbox JSON
         */
        protected String doInBackground(String... args) {
            int id2=0;
            String from ="", location="", stime="", img="", duration="", zone_color="";

            for (int i = 0; i < SelectArtist.lovedArtists2.length() ; i++) {

                try {
                    JSONObject a = SelectArtist.lovedArtists2.getJSONObject(i);
                    // Storing each json item in variable
                    from = a.getString("artist");
                    location = a.getString("zone");
                    stime = a.getString("start_time");
                    img = a.getString("big_image_url");
                    img = "a" + img;
                    id2 = getResources().getIdentifier(img, "raw", getPackageName());
                    duration = a.getString("duration_minutes");
                    zone_color = a.getString("zone_color");
                } catch (JSONException e){

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
                inboxList.add(map);
            }


            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
//                    ListAdapter adapter = new SimpleAdapter(
//                            FavDay1.this, inboxList,
//                            R.layout.tab1_xml, new String[] { TAG_NAME, TAG_LOCATION, TAG_STIME, TAG_IMG},
//                            new int[] { R.id.from, R.id.location, R.id.stime , R.id.icon});
//                    // updating listview
//                    setListAdapter(adapter);
                }
            });
            mAdapter = new AppAdapter();
            listView.setAdapter(mAdapter);

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
                convertView = View.inflate(getApplicationContext(),
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
            String[] time=item.get("stime").toString().split(" ");
            holder.stime.setText(time[1]);
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

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}