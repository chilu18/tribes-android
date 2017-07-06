package com.nathanglover.digitribe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

public abstract class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigationView;
    protected ViewGroup container;
    protected int goldDark, goldMed, gold, goldLight;
    protected int[] colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        container = (ViewGroup) findViewById(R.id.container);
        goldDark = ContextCompat.getColor(this, R.color.gold_dark);
        goldMed = ContextCompat.getColor(this, R.color.gold_med);
        gold = ContextCompat.getColor(this, R.color.gold);
        goldLight = ContextCompat.getColor(this, R.color.gold_light);
        colors = new int[] { goldDark, goldMed, gold, goldLight };

        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        navigationView.postDelayed(() -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_map) {
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            } else if (itemId == R.id.navigation_usb) {
                startActivity(new Intent(getApplicationContext(), USBActivity.class));
            } else if (itemId == R.id.navigation_bluetooth) {
                startActivity(new Intent(getApplicationContext(), BluetoothActivity.class));
            } else if (itemId == R.id.navigation_debug) {
                startActivity(new Intent(getApplicationContext(), DebugActivity.class));
            }
            finish();
        }, 300);
        return true;
    }

    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        Menu menu = navigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }
    }

    abstract int getContentViewId();

    abstract int getNavigationMenuItemId();
}
