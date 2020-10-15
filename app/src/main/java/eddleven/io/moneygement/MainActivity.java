package eddleven.io.moneygement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Boolean mStatusBackPressed = false;
    private final Handler mHandlerBackPressed = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}