package eddleven.io.moneygement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import eddleven.io.moneygement.configs.Preference;
import eddleven.io.moneygement.dialogs.RegisterUserDialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Boolean mStatusBackPressed = false;
    private final Handler mHandlerBackPressed = new Handler();
    private MaterialButton quit, register, registerPrimary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.checkUserSession();
        this.quit = findViewById(R.id.quit);
        this.register = findViewById(R.id.register);
        this.registerPrimary = findViewById(R.id.register_primary);
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
        if(this.registerUser()){
            this.skipActivity();
        }
    }

    public boolean registerUser(){
        return Preference.isNameSet(this);
    }

    public void skipActivity(){
        this.startMainActivity();
    }
    public void startMainActivity(){
        startActivity(new Intent(getApplicationContext(), ActivityUtama.class));
    }

    @Override
    public void onClick(View v) {
        if(v == this.quit){
            Toast.makeText(this, "See you...", Toast.LENGTH_SHORT).show();
            this.finish();
        } else if(v == this.register || v == this.registerPrimary){
            this.showDialogRegisterUser();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(this.registerUser()){
            finish();
        }
    }
}