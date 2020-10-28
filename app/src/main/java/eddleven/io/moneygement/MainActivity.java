package eddleven.io.moneygement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import eddleven.io.moneygement.dialogs.RegisterUserDialog;
import eddleven.io.moneygement.models.DaoSession;
import eddleven.io.moneygement.models.PemasukanDao;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Boolean mStatusBackPressed = false;
    private final Handler mHandlerBackPressed = new Handler();
    private PemasukanDao pemasukanDao;
    private DaoSession daoSession;
    private LinearLayout containerMainActivity;
    private MaterialButton quit, register, registerPrimary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.containerMainActivity = (LinearLayout) findViewById(R.id.container_main_activity);
        this.quit = (MaterialButton) findViewById(R.id.quit);
        this.register = (MaterialButton) findViewById(R.id.register);
        this.registerPrimary = (MaterialButton) findViewById(R.id.register_primary);
        this.quit.setOnClickListener(this);
        this.register.setOnClickListener(this);
        this.registerPrimary.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if(!this.mStatusBackPressed){
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

    /**
     * menampilkan dialog tambah user
     *
     */
    public void showDialogRegisterUser(){
        DialogFragment newFragment = new RegisterUserDialog();
        newFragment.show(getSupportFragmentManager(), "missiles");
    }


    /**
     * mengecek sesi user pada user preference
     * masih nama user
     *
     */
    public void checkUserSession(){

    }

    public boolean registerUser(){
        return false;
    }

    public boolean skipActivity(){
        this.startMainActivity();
        return true;
    }
    public void startMainActivity(){
        startActivity(new Intent(this, ActivityUtama.class));
    }

    @Override
    public void onClick(View v) {
        if(v == (View) this.quit){
            Toast.makeText(this, "See you...", Toast.LENGTH_SHORT).show();
            this.finish();
        } else if(v == (View) this.register || v == (View) this.registerPrimary){
            this.showDialogRegisterUser();
        }
    }
}