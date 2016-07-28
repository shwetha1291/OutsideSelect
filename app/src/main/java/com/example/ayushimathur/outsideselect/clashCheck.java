package com.example.ayushimathur.outsideselect;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class clashCheck extends Activity {

    ListView list;
    DateFormat f = new SimpleDateFormat("hh:mm");
    ArrayList<Integer> artistid = new ArrayList<Integer>();
    ArrayList<String> artistnames = new ArrayList<String>();
    ArrayList<String> artisttimingst = new ArrayList<String>();
    ArrayList<String> artisttimingend = new ArrayList<String>();
    ArrayList<String> loctn = new ArrayList<String>();
    public static ArrayList<Integer> finalList = new ArrayList<Integer>();
    boolean addedItem = true;
    String Slecteditem;
    int Position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int res;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clash_check);
        list = (ListView) findViewById(R.id.list);
        Button goahed = (Button) findViewById(R.id.goahead);

        //sort on basis of start time
        Collections.sort(SelectArtist.lovedArtists);


        HashMap<Integer, ArrayList<Integer>> clashes = new HashMap<Integer, ArrayList<Integer>>();
        for (int i = 0; i < SelectArtist.lovedArtists.size(); i++) {
            Artist a = SelectArtist.lovedArtists.get(i);
            String etimeA = a.getEndtime();
            int j = i + 1;
            for (; j < SelectArtist.lovedArtists.size(); j++) {
                Artist b = SelectArtist.lovedArtists.get(j);
                String stimeB = b.getStarttime();
                try {
                    res = f.parse(etimeA).compareTo(f.parse(stimeB));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
                if (res > 0) {
                    if (!clashes.containsKey(i)) {
                        ArrayList<Integer> val = new ArrayList<Integer>();
                        clashes.put(i, val);
                    }
                    clashes.get(i).add(j);
                }
            }
        }

        if (clashes.size() == 0) {
            Log.d("welcome no clashes", "welcome no clashes");
            Intent intent = new Intent(this, ListingArtists.class);
            startActivity(intent);
            finish();
        } else {
            String imgName;
            Log.d("welcome  clashes", "welcome  clashes");
            Iterator<HashMap.Entry<Integer, ArrayList<Integer>>> entries = clashes.entrySet().iterator();
            while (entries.hasNext() && addedItem) {
                Log.d("hi", "hi");
                HashMap.Entry<Integer, ArrayList<Integer>> e = entries.next();
                addedItem = false;

                //CLEAR LIST VIEW
                list.setAdapter(null);


                //POPULATE LISTVIEW
                int artistId = e.getKey();
                Log.d("artistid", Integer.toString(artistId));
                Artist a = SelectArtist.lovedArtists.get(artistId);
                Log.d("artyname", a.getName());
                if (!artistnames.contains(a.getName())) {
                    artistid.add(a.getId());
                    artistnames.add(a.getName());
                    artisttimingst.add(a.getStarttime());
                    artisttimingend.add(a.getEndtime());
                    loctn.add(a.getLocation());
                }
                //display a on the left side
                for (int clashedId : e.getValue()) {
                    //display b on the right side
                    Artist clashedArtist = SelectArtist.lovedArtists.get(clashedId);
                    if (!artistnames.contains(clashedArtist.getName())) {
                        artistid.add(clashedArtist.getId());
                        artistnames.add(clashedArtist.getName());
                        artisttimingst.add(clashedArtist.getStarttime());
                        artisttimingend.add(clashedArtist.getEndtime());
                        loctn.add(clashedArtist.getLocation());
                    }

                }

                //list related loading
                Log.d("lala", "lala");

                CustomListAdaptor adapter = new CustomListAdaptor(this, artistid, artistnames, artisttimingst, artisttimingend, loctn);

                list.setAdapter(adapter);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    int idx;

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        view.setSelected(true);
                        Slecteditem = "You have selected " + artistnames.get(position);
                        Position = position;
                        int idx = artistid.get(Position);
                        if(! clashCheck.finalList.contains(idx)) {
                            clashCheck.finalList.add(idx);
                        }
                        Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
                    }
                });

                goahed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), ListingArtists.class);
                        startActivity(intent);
                        finish();
                    }
                });

            }

        }
    }
}
