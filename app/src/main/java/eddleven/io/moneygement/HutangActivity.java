package eddleven.io.moneygement;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import eddleven.io.moneygement.adapters.recycler.BayarUtangAdapter;
import eddleven.io.moneygement.adapters.recycler.HutangAdapter;
import eddleven.io.moneygement.adapters.recycler.PengeluaranAdapter;
import eddleven.io.moneygement.dialogs.TambahBayarHutangDialog;
import eddleven.io.moneygement.dialogs.TambahHutangDialog;
import eddleven.io.moneygement.models.BayarHutang;
import eddleven.io.moneygement.models.BayarHutangDao;
import eddleven.io.moneygement.models.DaoSession;
import eddleven.io.moneygement.models.Hutang;
import eddleven.io.moneygement.models.HutangDao;
import eddleven.io.moneygement.models.Pengeluaran;
import eddleven.io.moneygement.repo.HutangRepo;
import eddleven.io.moneygement.ui.bayarhutang.BayarHutangViewModel;
import eddleven.io.moneygement.ui.hutang.HutangViewModel;
import eddleven.io.moneygement.ui.pengeluaran.PengeluaranViewModel;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HutangActivity extends AppCompatActivity {

    private BayarHutangViewModel pengeluaranViewModel;

    private RecyclerView listHutangView;
    private BayarUtangAdapter pengeluaranAdapter;
    private List<BayarHutang> listPengeluaran = new ArrayList<BayarHutang>();
    FloatingActionButton tambah;
    View root;
    TextView totalPengeluaran, pesanPengeluaran;
    long id;
    Hutang hutang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hutang);

        id = getIntent().getLongExtra("id", 0);
        hutang = HutangRepo.find(getApplication(), id);

        pengeluaranViewModel =
                ViewModelProviders.of(this).get(BayarHutangViewModel .class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(hutang.getKeterangan().toString());

        listHutangView = findViewById(R.id.daftar_hutang);
        pengeluaranAdapter = new BayarUtangAdapter(this, listPengeluaran, new BayarUtangAdapter.UpdateEvent() {
            @Override
            public void update() {
                pengeluaranViewModel.populateHutang();
            }
        });

        listHutangView.setHasFixedSize(false);
        DividerItemDecoration itemDecor = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        listHutangView.addItemDecoration(itemDecor);
        listHutangView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        listHutangView.setAdapter(pengeluaranAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TambahBayarHutangDialog(new TambahBayarHutangDialog.TambahBayarHutangInterface() {
                    @Override
                    public void setIntent(Intent intent) {
                        if(intent.getBooleanExtra("status", false)){
                            pengeluaranViewModel.populateHutang();
                            Snackbar.make(findViewById(R.id.container), "Berhasil Menambahkan Data Pembayaran", Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(findViewById(R.id.container), "Gagal Menambahkan Data Pembayaran", Snackbar.LENGTH_LONG).show();
//                    Snackbar.make(root.findViewById(R.id.container), "Terjadi sebuah Kesalahan", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }, hutang);
                newFragment.setCancelable(false);
                newFragment.show(getSupportFragmentManager(), "tambahhutang");
            }
        });
        pengeluaranViewModel.getHutang(hutang).observe(this, new Observer<List<BayarHutang>>() {
            @Override
            public void onChanged(@Nullable List<BayarHutang> hutangs) {
//                textView.setText(s);
                listPengeluaran = hutangs;
                pengeluaranAdapter.updateList(listPengeluaran);
            }
        });
        pengeluaranViewModel.populateHutang();
    }

    public void getData(){

        DaoSession session = ((App) getApplication()).getDaoSession();
        BayarHutangDao bayarHutangDao = session.getBayarHutangDao();
        listPengeluaran = bayarHutangDao.queryBuilder().orderDesc(BayarHutangDao.Properties.Tanggal).list();
        pengeluaranAdapter.setList(this.listPengeluaran);
        pengeluaranAdapter.notifyDataSetChanged();

//        Cursor cursor = hutangDao.getDatabase().rawQuery("SELECT SUM(`nominal`) as total FROM " + HutangDao.TABLENAME, new String[]{});
//        cursor.moveToFirst();
//        totalHutang.setText("Rp." + String.valueOf(cursor.getLong(0)) + ",-");
//        this.hutangViewModel.populateHutang();
    }
}