package eddleven.io.moneygement.ui.pemasukan;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Calendar;
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
import eddleven.io.moneygement.adapters.recycler.PemasukanAdapter;
import eddleven.io.moneygement.configs.Preference;
import eddleven.io.moneygement.dialogs.RegisterUserDialog;
import eddleven.io.moneygement.dialogs.TambahPemasukanDialog;
import eddleven.io.moneygement.models.DaoSession;
import eddleven.io.moneygement.models.Pemasukan;
import eddleven.io.moneygement.models.PemasukanDao;

public class PemasukanFragment extends Fragment implements View.OnClickListener {

    public PemasukanViewModel pemasukanViewModel;
    private RecyclerView listPemasukanView;
    private PemasukanAdapter pemasukanAdapter;
    private List<Pemasukan> listPemasukan = new ArrayList<Pemasukan>();
    FloatingActionButton tambah;
    View root;
    TextView totalPemasukan, pesanPemasukan;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pemasukanViewModel =
                ViewModelProviders.of(this).get(PemasukanViewModel.class);
        this.root = inflater.inflate(R.layout.fragment_pemasukan, container, false);

//        final TextView textView = root.findViewById(R.id.text_pemasukan);

        this.initDaftarPemasukan(root);
        pemasukanViewModel.getPemasukan().observe(getViewLifecycleOwner(), new Observer<List<Pemasukan>>() {
            @Override
            public void onChanged(List<Pemasukan> pemasukans) {
                listPemasukan = pemasukans;
                pemasukanAdapter.updateList(listPemasukan);
            }
        });
        pemasukanViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        return root;
    }

    public void showDialogTambahPemasukan(){
        DialogFragment newFragment = new TambahPemasukanDialog(new TambahPemasukanDialog.TambahPemasukanInterface() {
            @Override
            public void setIntent(Intent intent) {
                if(intent.getBooleanExtra("status", false)){
                    getDaftarPemasukan();
                    Snackbar.make(root.findViewById(R.id.container), "Berhasil Menambahkan Pemasukan", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(root.findViewById(R.id.container), "Gagal Menambahkan Pemasukan", Snackbar.LENGTH_LONG).show();
//                    Snackbar.make(root.findViewById(R.id.container), "Terjadi sebuah Kesalahan", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        newFragment.setCancelable(false);
        newFragment.show(getActivity().getSupportFragmentManager(), "tambahpemasukan");
    }

    private void getDaftarPemasukan(){

        DaoSession session = ((App) getActivity().getApplication()).getDaoSession();
        PemasukanDao pemasukanDao = session.getPemasukanDao();
        this.listPemasukan = pemasukanDao.queryBuilder().orderDesc(PemasukanDao.Properties.Tanggal).list();
        pemasukanAdapter.setList(this.listPemasukan);
        pemasukanAdapter.notifyDataSetChanged();

        Cursor cursor = pemasukanDao.getDatabase().rawQuery("SELECT SUM(`nominal`) as total FROM " + pemasukanDao.TABLENAME, new String[]{});
        cursor.moveToFirst();
        totalPemasukan.setText("Rp." + String.valueOf(cursor.getLong(0)) + ",-");
        this.pemasukanViewModel.populatePemasukan();
    }

    private void updateDataPemasukan(){
        this.pemasukanViewModel.populatePemasukan();
    }

    private void initDaftarPemasukan(View root) {
        totalPemasukan = root.findViewById(R.id.total_pemasukan);
        pesanPemasukan = root.findViewById(R.id.pesan_pemasukan);
        tambah = root.findViewById(R.id.tambah_pemasukan);
        tambah.setOnClickListener(this);

        this.listPemasukanView = root.findViewById(R.id.daftar_pemasukan);

        DaoSession session = ((App) getActivity().getApplication()).getDaoSession();
        PemasukanDao pemasukanDao = session.getPemasukanDao();

        pemasukanAdapter = new PemasukanAdapter(getContext(), listPemasukan, new PemasukanAdapter.UpdateEvent() {
            @Override
            public void update() {
                pemasukanViewModel.populatePemasukan();
            }
        });
        listPemasukanView.setHasFixedSize(false);
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        listPemasukanView.addItemDecoration(itemDecor);
        listPemasukanView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        listPemasukanView.setAdapter(pemasukanAdapter);

        Cursor cursor = pemasukanDao.getDatabase().rawQuery("SELECT SUM(`nominal`) as total FROM " + pemasukanDao.TABLENAME, new String[]{});
        cursor.moveToFirst();
        totalPemasukan.setText("Rp." + String.valueOf(cursor.getLong(0)) + ",-");

        Cursor cursor2 = pemasukanDao.getDatabase().rawQuery("SELECT SUM(`nominal`) as total FROM " + pemasukanDao.TABLENAME + " WHERE strftime('%Y', datetime(tanggal/1000, 'unixepoch')) = strftime('%Y',date('now')) AND strftime('%m', datetime(tanggal/1000, 'unixepoch')) = strftime('%m',date('now'))", new String[]{});
        cursor2.moveToFirst();
        String pemasukanBulanan = String.valueOf(cursor2.getLong(0));
        String nama = Preference.getName(getContext());
        pesanPemasukan.setText(nama + ", pemasukan Anda bulan ini: Rp." +  pemasukanBulanan + ",-");
    }

    @Override
    public void onClick(View v) {
        if(v == tambah){
            this.showDialogTambahPemasukan();
        }
    }
}