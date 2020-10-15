package eddleven.io.moneygement;

import androidx.appcompat.app.AppCompatActivity;
import eddleven.io.moneygement.models.DaoMaster;
import eddleven.io.moneygement.models.DaoSession;
import eddleven.io.moneygement.models.Pemasukan;
import eddleven.io.moneygement.models.PemasukanDao;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Boolean mStatusBackPressed = false;
    private final Handler mHandlerBackPressed = new Handler();
    private PemasukanDao pemasukanDao;
    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        daoSession = ((App) getApplication()).getDaoSession();
//        // do this in your activities/fragments to get hold of a DAO
//        pemasukanDao = daoSession.getPemasukanDao();
//        Pemasukan pemasukan = new Pemasukan();
//        pemasukan.setText("hello dunia!");
//        pemasukan.setDate(new Date());
//        pemasukanDao.insert(pemasukan);
//        List<Pemasukan> dataPemasukan = new ArrayList<>();
//        dataPemasukan = pemasukanDao.loadAll();
//        Toast.makeText(this, dataPemasukan.get(dataPemasukan.size() - 1 ).getText(), Toast.LENGTH_LONG).show();
//        Log.d("DaoExample", "Inserted new Pemasukan, ID: " + pemasukan.getId());
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