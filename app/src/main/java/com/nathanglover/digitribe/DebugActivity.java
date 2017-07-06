package com.nathanglover.digitribe;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.github.jinatonic.confetti.CommonConfetti;

import java.util.ArrayList;

public class DebugActivity extends MainActivity {

    private ArrayList<DebugDataModel> dataModels;
    private ListView listView;
    private DebugLogListAdapter adapter;

    private MediaPlayer mediaPlayer;
    private ImageView imageView;
    private Animation rotateAnimation;

    @Override
    int getContentViewId() {
        return R.layout.activity_debug;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_debug;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Log list logic
        listView=(ListView)findViewById(R.id.log_list);
        dataModels= new ArrayList<>();

        // Populate Log list here
        dataModels.add(new DebugDataModel("MSH_NODE_01", "00:14:22:01:23:45", -31.9538, 115.8532, "January 1, 1970, 00:00:00 GMT"));
        dataModels.add(new DebugDataModel("MSH_NODE_02", "00:14:22:01:23:45", -32.9538, 116.8532, "January 2, 1970, 00:00:00 GMT"));
        dataModels.add(new DebugDataModel("MSH_NODE_03", "00:14:22:01:23:45", -33.9538, 117.8532, "January 3, 1970, 00:00:00 GMT"));
        dataModels.add(new DebugDataModel("MSH_NODE_04", "00:14:22:01:23:45", -34.9538, 118.8532, "January 4, 1970, 00:00:00 GMT"));
        dataModels.add(new DebugDataModel("MSH_NODE_05", "00:14:22:01:23:45", -35.9538, 119.8532, "January 5, 1970, 00:00:00 GMT"));
        dataModels.add(new DebugDataModel("MSH_NODE_06", "00:14:22:01:23:45", -36.9538, 120.8532, "January 6, 1970, 00:00:00 GMT"));
        dataModels.add(new DebugDataModel("MSH_NODE_07", "00:14:22:01:23:45", -37.9538, 121.8532, "January 7, 1970, 00:00:00 GMT"));
        dataModels.add(new DebugDataModel("MSH_NODE_08", "00:14:22:01:23:45", -38.9538, 122.8532, "January 8, 1970, 00:00:00 GMT"));
        dataModels.add(new DebugDataModel("MSH_NODE_09", "00:14:22:01:23:45", -39.9538, 123.8532, "January 9, 1970, 00:00:00 GMT"));
        dataModels.add(new DebugDataModel("MSH_NODE_10", "00:14:22:01:23:45", -40.9538, 124.8532, "January 10, 1970, 00:00:00 GMT"));
        dataModels.add(new DebugDataModel("MSH_NODE_11", "00:14:22:01:23:45", -41.9538, 125.8532, "January 11, 1970, 00:00:00 GMT"));
        dataModels.add(new DebugDataModel("MSH_NODE_12", "00:14:22:01:23:45", -42.9538, 126.8532, "January 12, 1970, 00:00:00 GMT"));
        dataModels.add(new DebugDataModel("MSH_NODE_13", "00:14:22:01:23:45", -43.9538, 127.8532, "January 13, 1970, 00:00:00 GMT"));
        dataModels.add(new DebugDataModel("MSH_NODE_14", "00:14:22:01:23:45", -44.9538, 128.8532, "January 14, 1970, 00:00:00 GMT"));
        dataModels.add(new DebugDataModel("MSH_NODE_15", "00:14:22:01:23:45", -45.9538, 129.8532, "January 15, 1970, 00:00:00 GMT"));
        dataModels.add(new DebugDataModel("MSH_NODE_16", "00:14:22:01:23:45", -46.9538, 130.8532, "January 16, 1970, 00:00:00 GMT"));

        adapter= new DebugLogListAdapter(dataModels,getApplicationContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DebugDataModel dataModel= dataModels.get(position);

                Snackbar.make(view, dataModel.getSensor_id()+ " MAC: " +dataModel.getSensor_mac()+"\n"+dataModel.getLocation(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });

        // Image related logic
        mediaPlayer = MediaPlayer.create(this, R.raw.danger);
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotator);
        imageView = (ImageView)findViewById(R.id.kenny_loggins_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                rotateAnimation.setDuration(900);
                imageView.startAnimation(rotateAnimation);
                CommonConfetti.rainingConfetti(container, colors)
                        .infinite();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }
}
