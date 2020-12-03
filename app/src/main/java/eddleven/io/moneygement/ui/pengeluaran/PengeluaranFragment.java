package eddleven.io.moneygement.ui.pengeluaran;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import eddleven.io.moneygement.App;
import eddleven.io.moneygement.R;
import eddleven.io.moneygement.adapters.recycler.PengeluaranAdapter;
import eddleven.io.moneygement.configs.Preference;
import eddleven.io.moneygement.dialogs.TambahPengeluaranDialog;
import eddleven.io.moneygement.models.DaoSession;
import eddleven.io.moneygement.models.Pengeluaran;
import eddleven.io.moneygement.models.PengeluaranDao;

public class PengeluaranFragment extends Fragment implements View.OnClickListener {

    private PengeluaranViewModel pengeluaranViewModel;

    private RecyclerView listPengeluaranView;
    private PengeluaranAdapter pengeluaranAdapter;
    private List<Pengeluaran> listPengeluaran = new ArrayList<Pengeluaran>();
    FloatingActionButton tambah;
    View root;
    TextView totalPengeluaran, pesanPengeluaran;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pengeluaranViewModel =
                ViewModelProviders.of(this).get(PengeluaranViewModel.class);
        root = inflater.inflate(R.layout.fragment_pengeluaran, container, false);
//        final TextView textView = root.findViewById(R.id.text_pengeluaran);

        this.initDaftarPengeluaran(root);
        pengeluaranViewModel.getPengeluaran().observe(getViewLifecycleOwner(), new Observer<List<Pengeluaran>>() {
            @Override
            public void onChanged(@Nullable List<Pengeluaran> pengeluarans) {
//                textView.setText(s);
                listPengeluaran = pengeluarans;
                pengeluaranAdapter.updateList(listPengeluaran);
            }
        });
        return root;
    }

    public void showDialogTambahPengeluaran(){
        DialogFragment newFragment = new TambahPengeluaranDialog(new TambahPengeluaranDialog.TambahPengeluaranInterface() {
            @Override
            public void setIntent(Intent intent) {
                if(intent.getBooleanExtra("status", false)){
                    getDaftarPengeluaran();
                    Snackbar.make(root.findViewById(R.id.container), "Berhasil Menambahkan Pengeluaran", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(root.findViewById(R.id.container), "Gagal Menambahkan Pengeluaran", Snackbar.LENGTH_LONG).show();
//                    Snackbar.make(root.findViewById(R.id.container), "Terjadi sebuah Kesalahan", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        newFragment.setCancelable(false);
        newFragment.show(getActivity().getSupportFragmentManager(), "tambahpengeluaran");
    }


    private void getDaftarPengeluaran(){

        DaoSession session = ((App) getActivity().getApplication()).getDaoSession();
        PengeluaranDao pengeluaranDao = session.getPengeluaranDao();
        this.listPengeluaran = pengeluaranDao.queryBuilder().orderDesc(PengeluaranDao.Properties.Tanggal).list();
        pengeluaranAdapter.setList(this.listPengeluaran);
        pengeluaranAdapter.notifyDataSetChanged();

        Cursor cursor = pengeluaranDao.getDatabase().rawQuery("SELECT SUM(`nominal`) as total FROM " + pengeluaranDao.TABLENAME, new String[]{});
        cursor.moveToFirst();
        totalPengeluaran.setText("Rp." + String.valueOf(cursor.getLong(0)) + ",-");
        this.pengeluaranViewModel.populatePengeluaran();
    }

    private void updateDataPengeluaran(){
        this.pengeluaranViewModel.populatePengeluaran();
    }

    private void initDaftarPengeluaran(View root) {
        totalPengeluaran = root.findViewById(R.id.total_pengeluaran);
        pesanPengeluaran = root.findViewById(R.id.pesan_pengeluaran);
        tambah = root.findViewById(R.id.tambah_pengeluaran);
        tambah.setOnClickListener(this);

        this.listPengeluaranView = root.findViewById(R.id.daftar_pengeluaran);

        DaoSession session = ((App) getActivity().getApplication()).getDaoSession();
        PengeluaranDao pengeluaranDao = session.getPengeluaranDao();

        pengeluaranAdapter = new PengeluaranAdapter(getContext(), listPengeluaran, new PengeluaranAdapter.UpdateEvent() {
            @Override
            public void update() {
                pengeluaranViewModel.populatePengeluaran();
            }
        });
        listPengeluaranView.setHasFixedSize(false);
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        listPengeluaranView.addItemDecoration(itemDecor);
        listPengeluaranView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        listPengeluaranView.setAdapter(pengeluaranAdapter);

        Cursor cursor = pengeluaranDao.getDatabase().rawQuery("SELECT SUM(`nominal`) as total FROM " + pengeluaranDao.TABLENAME, new String[]{});
        cursor.moveToFirst();
        totalPengeluaran.setText("Rp." + String.valueOf(cursor.getLong(0)) + ",-");

        Cursor cursor2 = pengeluaranDao.getDatabase().rawQuery("SELECT SUM(`nominal`) as total FROM " + pengeluaranDao.TABLENAME + " WHERE strftime('%Y', datetime(tanggal/1000, 'unixepoch')) = strftime('%Y',date('now')) AND strftime('%m', datetime(tanggal/1000, 'unixepoch')) = strftime('%m',date('now'))", new String[]{});
        cursor2.moveToFirst();
        String pengeluaranBulanan = String.valueOf(cursor2.getLong(0));
        String nama = Preference.getName(getContext());
        pesanPengeluaran.setText(nama + ", pengeluaran Anda bulan ini: Rp." +  pengeluaranBulanan + ",-");
    }

    @Override
    public void onClick(View v) {
        if(v == tambah){
            this.showDialogTambahPengeluaran();
        }
    }
}