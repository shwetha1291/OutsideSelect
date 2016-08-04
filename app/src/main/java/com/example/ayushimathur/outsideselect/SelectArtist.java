    package com.example.ayushimathur.outsideselect;

    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.media.AudioManager;
    import android.media.Image;
    import android.media.MediaPlayer;
    import android.net.Uri;
    import android.os.AsyncTask;
    import android.os.Environment;
    import android.support.annotation.IntegerRes;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
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

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;
    import java.io.IOException;
    import java.io.InputStream;



    public class SelectArtist extends AppCompatActivity {
        TextView artname;
        JSONObject m_jArry;
        JSONArray obj;
        private boolean intialStage = true;

        public static JSONArray lovedArtists1 = new JSONArray();
        public static JSONArray lovedArtists2 = new JSONArray();
        public static JSONArray lovedArtists3 = new JSONArray();
        public static JSONArray rejectedArtists1 = new JSONArray();
        public static JSONArray rejectedArtists2 = new JSONArray();
        public static JSONArray rejectedArtists3 = new JSONArray();
        MediaPlayer player = new MediaPlayer();
        int songplaying = 0;
        /**
         * ATTENTION: This was auto-generated to implement the App Indexing API.
         * See https://g.co/AppIndexing/AndroidStudio for more information.
         */
        private GoogleApiClient client;
        ImageView btnskipRight, btnheart,
                btnFavList, btnheartAfter,
                btnRejectList, btnCross,
                btnskipLeft, btnCrossAfter,
                btnpause, btnplay;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_select_artist);
            artname = (TextView) findViewById(R.id.artistName);

            btnplay = (ImageView) findViewById(R.id.btnplay);
            btnskipRight = (ImageView) findViewById(R.id.btnskipRight);
            btnFavList = (ImageView) findViewById(R.id.favList);
            btnRejectList = (ImageView)findViewById(R.id.rejectList);
            btnskipLeft = (ImageView) findViewById(R.id.btnskipLeft);
            btnpause = (ImageView) findViewById(R.id.btnpause);
            btnpause.setVisibility(View.GONE);
            btnheart = (ImageView) findViewById(R.id.btnheart);
            btnheartAfter = (ImageView) findViewById(R.id.btnheartAfter);
            btnCross = (ImageView) findViewById(R.id.btncross);
            btnCrossAfter = (ImageView) findViewById(R.id.btncrossAfter);
            btnCrossAfter.setVisibility(View.GONE);
            btnheartAfter.setVisibility(View.GONE);

            btnFavList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), AndroidTabAndListView.class);
                    player.stop();
                    player.release();
                    startActivity(intent);
                    finish();
                }
            });

            btnRejectList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), RejectedArtistsLists.class);
                    player.stop();
                    player.release();
                    startActivity(intent);
                    finish();
                }
            });


            //   Collection
            btnheart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONObject jo_inside;
                    String artist;
                    String date = "";
                    try {
                        jo_inside = obj.getJSONObject(songplaying);
                        date = jo_inside.getString("start_time");
                        artist = jo_inside.getString("artist");
                        if(date.contains("2016-08-05")) {
                            if (!userexists(lovedArtists1, artist)) {
                                lovedArtists1.put(jo_inside);
                            }
                            Log.d("lenfavlistabb ", Integer.toString(lovedArtists1.length()));
                            if (userexists(rejectedArtists1, artist)) {
                                rejectedArtists1.remove(songplaying);
                            }
                        }

                        if(date.contains("2016-08-06")) {
                            if (!userexists(lovedArtists2, artist)) {
                                lovedArtists2.put(jo_inside);
                            }
                            if (userexists(rejectedArtists2, artist)) {
                                rejectedArtists2.remove(songplaying);
                            }
                        }

                        if(date.contains("2016-08-07")) {
                            if (!userexists(lovedArtists3, artist)) {
                                lovedArtists3.put(jo_inside);
                            }
                            if (userexists(rejectedArtists3, artist)) {
                                rejectedArtists3.remove(songplaying);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    btnheartAfter.setVisibility(View.VISIBLE);
                    btnheart.setVisibility(View.GONE);

                    btnCrossAfter.setVisibility(View.GONE);
                    btnCross.setVisibility(View.VISIBLE);
                    btnskipRight.callOnClick();
                }
            });

            btnheartAfter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONObject jo_inside;
                    String artist;
                    String date = "";
                    try {
                        jo_inside = obj.getJSONObject(songplaying);
                        date = jo_inside.getString("start_time");
                        artist = jo_inside.getString("artist");
                        if(date.contains("2016-08-07")) {
                            if (userexists(lovedArtists3, artist)) {
                                lovedArtists3.remove(songplaying);
                            }
                        }
                        if(date.contains("2016-08-06")) {
                            if (userexists(lovedArtists2, artist)) {
                                lovedArtists2.remove(songplaying);
                            }
                        }
                        if(date.contains("2016-08-05")) {
                            if (userexists(lovedArtists1, artist)) {
                                lovedArtists1.remove(songplaying);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    btnheartAfter.setVisibility(View.GONE);
                    btnheart.setVisibility(View.VISIBLE);
                }
            });


            btnCross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONObject jo_inside;
                    String artist;
                    String date = "";
                    try {
                        jo_inside = obj.getJSONObject(songplaying);
                        date = jo_inside.getString("start_time");
                        artist = jo_inside.getString("artist");
                        if(date.contains("2016-08-07")) {
                            if (!userexists(rejectedArtists3, artist)) {
                                rejectedArtists3.put(jo_inside);
                            }
                            if (userexists(lovedArtists3, artist)) {
                                lovedArtists3.remove(songplaying);
                            }
                        }

                        if(date.contains("2016-08-06")) {
                            if (!userexists(rejectedArtists2, artist)) {
                                rejectedArtists2.put(jo_inside);
                            }
                            if (userexists(lovedArtists2, artist)) {
                                lovedArtists2.remove(songplaying);
                            }
                        }

                        if(date.contains("2016-08-05")) {
                            if (!userexists(rejectedArtists1, artist)) {
                                rejectedArtists1.put(jo_inside);
                            }
                            if (userexists(lovedArtists1, artist)) {
                                lovedArtists1.remove(songplaying);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    btnCrossAfter.setVisibility(View.VISIBLE);
                    btnCross.setVisibility(View.GONE);

                    btnheart.setVisibility(View.VISIBLE);
                    btnheartAfter.setVisibility(View.GONE);
                    btnskipRight.callOnClick();
                }
            });

            btnCrossAfter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONObject jo_inside;
                    String artist;
                    String date = "";
                    try {
                        jo_inside = obj.getJSONObject(songplaying);
                        date = jo_inside.getString("start_time");
                        artist = jo_inside.getString("artist");
                        if(date.contains("2016-08-07")) {
                            if (userexists(rejectedArtists3, artist)) {
                                rejectedArtists3.remove(songplaying);
                            }
                        }
                        if(date.contains("2016-08-06")) {
                            if (userexists(rejectedArtists2, artist)) {
                                rejectedArtists2.remove(songplaying);
                            }
                        }
                        if(date.contains("2016-08-05")) {
                            if (userexists(rejectedArtists1, artist)) {
                                rejectedArtists1.remove(songplaying);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    btnCrossAfter.setVisibility(View.GONE);
                    btnCross.setVisibility(View.VISIBLE);
                }
            });


            btnskipRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (songplaying < obj.length()-1) {
                        songplaying++;
                        player.reset();
                        setUpImageAndSong();
                    }   else {
                        Intent intent = new Intent(view.getContext(), AndroidTabAndListView.class);
                        player.stop();
                        player.release();
                        startActivity(intent);
                        finish();
                    }
                }
            });

            btnskipLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (songplaying > 0) {
                        songplaying--;
                        player.reset();
                        setUpImageAndSong();
                        btnheart.setVisibility(View.VISIBLE);
                        btnheartAfter.setVisibility(View.GONE);
                    }else {
                        Intent intent = new Intent(view.getContext(), Splash.class);
                        player.stop();
                        player.release();
                        startActivity(intent);
                        finish();
                    }

                }
            });

            btnplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (player.isPlaying()){
                        player.pause();
                        btnpause.setVisibility(View.GONE);
                        btnplay.setVisibility(View.VISIBLE);
                    } else {
                        player.start();
                        btnpause.setVisibility(View.VISIBLE);
                        btnplay.setVisibility(View.GONE);
                    }
                }
            });

            btnpause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (player.isPlaying()) {
                        player.pause();
                        btnpause.setVisibility(View.GONE);
                        btnplay.setVisibility(View.VISIBLE);
                    } else {
                        player.start();
                        btnpause.setVisibility(View.VISIBLE);
                        btnplay.setVisibility(View.GONE);
                    }
                }
            });

            try {
                obj = new JSONArray(loadJSONFromAsset());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            player=new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            setUpImageAndSong();

            // ATTENTION: This was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        }

        public String loadJSONFromAsset() {
            String json = null;
            try {
                InputStream is = getResources().openRawResource(R.raw.schedule);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }

        private boolean userexists(JSONArray jsonArray, String usernameToFind){
            Log.d("user exists big string ", jsonArray.toString());
            Log.d("search artist ", usernameToFind);
            boolean ifexists =  jsonArray.toString().contains(usernameToFind);
            Log.d("if exists ", Boolean.toString(ifexists));
            return ifexists;
        }

        private void setUpImageAndSong() {
            try {
                Log.d("songplaying index", Integer.toString(songplaying));
                JSONObject jo_inside = obj.getJSONObject(songplaying);
                String artistName = jo_inside.getString("artist");
                String imgName = jo_inside.getString("big_image_url");
                String song_url = jo_inside.getString("song_url");
                artname.setText(artistName);

                Log.d("sonag name",song_url);

                new Player().execute(song_url);
                ImageView imgv = (ImageView) findViewById(R.id.artistphoto);
                imgName = "a" + imgName ;
                Log.d("image name", imgName);
                int id2 = getResources().getIdentifier(imgName, "raw", getPackageName());
                imgv.setImageResource(id2);
            }
            catch (JSONException e){

            }

            btnpause.setVisibility(View.VISIBLE);
            btnplay.setVisibility(View.GONE);

            btnheart.setVisibility(View.VISIBLE);
            btnheartAfter.setVisibility(View.GONE);

            btnCross.setVisibility(View.VISIBLE);
            btnCrossAfter.setVisibility(View.GONE);
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

        /**
         * preparing mediaplayer will take sometime to buffer the content so prepare it inside the background thread and starting it on UI thread.
         * @author piyush
         *
         */

        class Player extends AsyncTask<String, Void, Boolean> {
            private ProgressDialog progress;

            @Override
            protected Boolean doInBackground(String... params) {
                // TODO Auto-generated method stub
                Boolean prepared;
                try {

                    player.setDataSource(params[0]);
                    player.setLooping(true);
                    player.prepare();
                    prepared = true;
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    Log.d("IllegarArgument", e.getMessage());
                    prepared = false;
                    e.printStackTrace();
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    prepared = false;
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    prepared = false;
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    prepared = false;
                    e.printStackTrace();
                }
                return prepared;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);
                if (progress.isShowing()) {
                    progress.cancel();
                }
                Log.d("Prepared", "//" + result);
                player.start();

                intialStage = false;
            }

            public Player() {
                progress = new ProgressDialog(SelectArtist.this);
            }

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                this.progress.setMessage("Buffering...");
                this.progress.show();

            }
        }

        @Override
        protected void onPause() {
            // TODO Auto-generated method stub
            super.onPause();
            if (player != null) {
//                player.stop();
                player.release();
                player = null;
            }
        }



    }
