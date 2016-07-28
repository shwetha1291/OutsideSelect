package com.example.ayushimathur.outsideselect;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ayushimathur on 7/24/16.
 */
public class CustomListAdaptor extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<Integer> artid;
    private final ArrayList<String> artname;
    private final ArrayList<String> sttime;
    private final ArrayList<String> endtime;
    private final ArrayList<String> location;


//    private final Integer[] imgid;

    public CustomListAdaptor(Activity context,ArrayList<Integer> artid, ArrayList<String> artname,ArrayList<String> sttime,ArrayList<String> endtime, ArrayList<String> location) {
        super(context, R.layout.mylist, artname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.artid = artid;
        this.artname=artname;
        this.sttime=sttime;
        this.endtime=endtime;
        this.location=location;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView artnAME = (TextView) rowView.findViewById(R.id.Aname);
        TextView SttIME = (TextView) rowView.findViewById(R.id.StartTime);
        TextView endttime = (TextView) rowView.findViewById(R.id.EndTime);
        TextView locationn = (TextView) rowView.findViewById(R.id.Location);
//        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);


        artnAME.setText(artname.get(position));
        SttIME.setText(sttime.get(position));
        endttime.setText(endtime.get(position));
        locationn.setText(location.get(position));
        return rowView;

    };
}
