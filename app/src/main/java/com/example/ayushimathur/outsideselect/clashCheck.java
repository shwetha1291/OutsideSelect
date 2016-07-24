package com.example.ayushimathur.outsideselect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class clashCheck extends AppCompatActivity {

    DateFormat f = new SimpleDateFormat("hh:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int res;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clash_check);

        //sort on basis of start time
        Collections.sort(SelectArtist.lovedArtists);

        HashMap<Integer, ArrayList<Integer>> clashes = new HashMap<Integer, ArrayList<Integer>>();
        for(int i = 0 ; i < SelectArtist.lovedArtists.size(); i ++){

            Artist a =  SelectArtist.lovedArtists.get(i);
            String etimeA = a.getEndtime();
            int j = i+1;
            for(; j< SelectArtist.lovedArtists.size(); j ++){
                Artist b =  SelectArtist.lovedArtists.get(j);
                String stimeB = b.getStarttime();
                try {
                    res = f.parse(etimeA).compareTo(f.parse(stimeB));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
                if(res > 0 ){
                    if (!clashes.containsKey(i)) {
                        ArrayList<Integer> val = new ArrayList<Integer>();
                        clashes.put(i,val);
                    }
                    clashes.get(i).add(j);
                }
            }
        }
        if(clashes.size()==0){
            Intent intent=new Intent(this,ListingArtists.class);
            startActivity(intent);
            finish();
        }
        else{
            String imgName;
            LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
            for(Map.Entry<Integer, ArrayList<Integer>> e : clashes.entrySet()){
                int artistId = e.getKey();
                Artist a = SelectArtist.lovedArtists.get(artistId);
                ImageView image = new ImageView(this);
                imgName = "img"+ artistId;
                int id = getResources().getIdentifier(imgName, "minmap", getPackageName());
                image.setBackgroundResource(id);
                linearLayout1.addView(image);
                //display a on the left side
                for(int clashedId : e.getValue()){
                    //display b on the right side
                    Artist clashedArtist = SelectArtist.lovedArtists.get(clashedId);
                    imgName = "img"+ artistId;
                    id = getResources().getIdentifier(imgName, "minmap", getPackageName());
                    image.setBackgroundResource(id);
                    linearLayout1.addView(image);

                }
            }

        }


    }
}
