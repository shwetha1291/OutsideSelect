package com.example.ayushimathur.outsideselect;

import android.app.TabActivity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class AndroidTabAndListView extends TabActivity {
    // TabSpec Names
    private static final String INBOX_SPEC = "FRIDAY"+"\n"+"Aug 5th";
    private static final String OUTBOX_SPEC = "SATURDAY"+"\n"+"Aug 6th";
    private static final String PROFILE_SPEC = "SUNDAY"+"\n"+"Aug 7th";


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public void onNewIntent(Intent intent){
        super.onNewIntent(intent);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_tab_and_list_view);
        TabHost tabHost = getTabHost();
        ImageView home = (ImageView) findViewById(R.id.homebtn);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SelectArtist.class);
                startActivity(intent);
                finish();
            }
        });
        // Inbox Tab
        TabHost.TabSpec inboxSpec = tabHost.newTabSpec(INBOX_SPEC);
        // Tab Icon
        inboxSpec.setIndicator(INBOX_SPEC, getResources().getDrawable(R.drawable.icon_inbox));
        Intent inboxIntent = new Intent(this, FavDay1.class);
        // Tab Content
        inboxSpec.setContent(inboxIntent);

        // Outbox Tab
        TabHost.TabSpec outboxSpec = tabHost.newTabSpec(OUTBOX_SPEC);
        outboxSpec.setIndicator(OUTBOX_SPEC, getResources().getDrawable(R.drawable.bitmap));
        Intent outboxIntent = new Intent(this, FavDay2.class);
        outboxSpec.setContent(outboxIntent);

        // Profile Tab
        TabHost.TabSpec profileSpec = tabHost.newTabSpec(PROFILE_SPEC);
        profileSpec.setIndicator(PROFILE_SPEC, getResources().getDrawable(R.drawable.icon_profile));
        Intent profileIntent = new Intent(this, FavDay3.class);
        profileSpec.setContent(profileIntent);

        // Adding all TabSpec to TabHost
        tabHost.addTab(inboxSpec); // Adding Inbox tab
        tabHost.addTab(outboxSpec); // Adding Outbox tab
        tabHost.addTab(profileSpec); // Adding Profile tab
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AndroidTabAndListView Page", // TODO: Define a title for the content shown.
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
                "AndroidTabAndListView Page", // TODO: Define a title for the content shown.
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
