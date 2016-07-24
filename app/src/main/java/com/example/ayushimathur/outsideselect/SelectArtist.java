package com.example.ayushimathur.outsideselect;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.PorterDuffXfermode;
import android.graphics.PorterDuff.Mode;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SelectArtist extends AppCompatActivity {

    ArrayList<String> songlist;
    TextView artname;
    public static ArrayList<Artist> lovedArtists = new ArrayList<Artist>();
    //public static ArrayList<Artist> dislikedArtist = new ArrayList<Artist>();
    MediaPlayer player;
    int numTaps = 0;
    int songplaying = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_artist);
        artname = (TextView) findViewById(R.id.artistName);


        final ImageView btnplay = (ImageView) findViewById(R.id.btnplay);
        final LinearLayout heart = (LinearLayout) findViewById(R.id.artistphotolayout);
        final LinearLayout photoIcon = (LinearLayout) findViewById(R.id.artistphotolayout);
        heart.setOnDragListener();


        ImageView btnskip = (ImageView) findViewById(R.id.btnskip);

        final ImageView btnheart = (ImageView) findViewById(R.id.btnheart);

        InputStream inputStream = getResources().openRawResource(R.raw.artistdata);
        CSVFile csvFile = new CSVFile(inputStream);
        final ArrayList<Artist> artistList = new ArrayList<Artist>(csvFile.read());

        songlist = new ArrayList<>();

        //iterate through list of files in raw
        String path = Environment.getExternalStorageDirectory().toString() + "/raw";
        String filepath;
        Log.d("Files", "Path: " + path);
        File f = new File(path);
        File file[] = f.listFiles();
        Log.d("Files", "Size: " + file.length);
        for (int i = 0; i < file.length; i++) {
            if (file[i].getName().contains("song")) {
                Log.d("Files", "FileName:" + file[i].getName());
                filepath = path + "/" + file[i].getName();
                songlist.add(filepath);
            }
        }


        //   Collection
        btnheart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!lovedArtists.contains(artistList.get(songplaying))) {
                    lovedArtists.add(artistList.get(songplaying));
                }
            }
        });

        btnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (songplaying < artistList.size() - 1) {
                    numTaps = 0;
                    player.stop();
                    btnplay.callOnClick();
                    songplaying++;
                } else {
                    Intent intent = new Intent(view.getContext(), clashCheck.class);
                    player.release();
                    startActivity(intent);
                    finish();
                }

            }
        });

        btnplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String imgName;
                numTaps++;
                if (numTaps < 2) {
                    player = new MediaPlayer();
                    try {
                        player.setDataSource(songlist.get(songplaying));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Artist a = artistList.get(songplaying);
                    artname.setText(a.getName());
                    ImageView imgv = (ImageView) findViewById(R.id.artistphoto);


                    imgName = "img" + songplaying;
                    int id = getResources().getIdentifier(imgName, "minmap", getPackageName());
                    imgv.setImageResource(id);


                    imgv.setOnTouchListener(new MyTouchListener());


                    player.setLooping(true);
                    player.start();
                    //conver to pause
                }
                if (numTaps % 2 == 0) {
                    player.pause();
                    //cont to start
                }
                if (numTaps % 2 != 0) {
                    player.start();
                    //convert to start
                }
            }
        });


        ImageView img1 = (ImageView) findViewById(R.id.artistphoto);
        Bitmap bm = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_launcher);
        Bitmap conv_bm = getRoundedBitmap(bm);
        img1.setImageBitmap(conv_bm);
        artname.setText("");

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public static Bitmap getRoundedBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SelectArtist Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.ayushimathur.outsideselect/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SelectArtist Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.ayushimathur.outsideselect/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}




