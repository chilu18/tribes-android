package com.nathanglover.digitribe;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.github.jinatonic.confetti.CommonConfetti;

public class DebugActivity extends MainActivity {

    private MediaPlayer mediaPlayer;
    private ImageView imageView;
    private Animation rotateAnimation;

    String[] logginsArray = {
            "My trust in a higher power that wants me to survive and have love in my life, is what keeps me moving forward.",
            "Real freedom is creative, proactive, and will take me into new territories. I am not free if my freedom is predicated on reacting to my past.",
            "My quest these days is to find my long lost inner child, but I'm afraid if I do, I'll end up with food in my hair and way too in love with the cats.",
            "Courage is always rewarded.",
            "I am learning to forgive my inner geek, and even value him as a free man.",
            "You must have love as the core; it takes courage to be willing to constantly tell the truth to each other and risk letting the relationship go.",
            "It's hard enough doing something bold without jumping into your bad reviews.",
            "Running away will never make you free."
    };

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

        mediaPlayer = MediaPlayer.create(this, R.raw.danger);
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotator);
        ArrayAdapter loggins_list_adapter = new ArrayAdapter<String>(this, R.layout.loggins_list_item, logginsArray);
        ListView loggins_list = (ListView) findViewById(R.id.log_list);
        loggins_list.setAdapter(loggins_list_adapter);

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
