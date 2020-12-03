package eddleven.io.moneygement;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import eddleven.io.moneygement.configs.Preference;

public class ActivityUtama extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private Boolean mStatusBackPressed = false;
    private final Handler mHandlerBackPressed = new Handler();
    private LinearLayout container;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.user_name);
        navUsername.setText(Preference.getName(this));

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_pemasukan, R.id.nav_pengeluaran)
//                .setDrawerLayout(drawer)
//                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        this.container = findViewById(R.id.container_activity_utama);
        drawer.setScrimColor(getResources().getColor(R.color.transparent));
        drawer.setDrawerElevation(0f);

        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                final float diffScaledOffset = slideOffset * (1 - 1f);
                final float offsetScale = 1 - diffScaledOffset;
                container.setScaleX(offsetScale);
                container.setScaleY(offsetScale);

                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = container.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                container.setTranslationX(xTranslation);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.activity_utama, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if(!this.mStatusBackPressed && navigationView.getMenu().findItem(R.id.nav_home).isChecked()){
            this.mStatusBackPressed = true;
            Toast.makeText(this, "Tekan Kembali Sekali Lagi", Toast.LENGTH_SHORT).show();
            this.mHandlerBackPressed.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mStatusBackPressed = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
        }
    }
}