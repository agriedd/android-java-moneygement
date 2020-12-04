package eddleven.io.moneygement.ui.hutang;

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
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import eddleven.io.moneygement.App;
import eddleven.io.moneygement.HutangActivity;
import eddleven.io.moneygement.R;
import eddleven.io.moneygement.adapters.recycler.HutangAdapter;
import eddleven.io.moneygement.adapters.recycler.PengeluaranAdapter;
import eddleven.io.moneygement.configs.Preference;
import eddleven.io.moneygement.dialogs.TambahHutangDialog;
import eddleven.io.moneygement.dialogs.TambahPengeluaranDialog;
import eddleven.io.moneygement.models.DaoSession;
import eddleven.io.moneygement.models.Hutang;
import eddleven.io.moneygement.models.HutangDao;
import eddleven.io.moneygement.models.Pengeluaran;
import eddleven.io.moneygement.models.PengeluaranDao;
import eddleven.io.moneygement.ui.pengeluaran.PengeluaranViewModel;

public class HutangFragment extends Fragment implements View.OnClickListener {

    private HutangViewModel hutangViewModel;

    private RecyclerView listHutangView;
    private HutangAdapter hutangAdapter;
    private List<Hutang> hutangList = new ArrayList<Hutang>();
    FloatingActionButton tambah;
    View root;
    TextView totalHutang, pesanHutang;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        hutangViewModel =
                ViewModelProviders.of(this).get(HutangViewModel.class);
        root = inflater.inflate(R.layout.fragment_hutang, container, false);
//        final TextView textView = root.findViewById(R.id.text_pengeluaran);

        this.initDaftarHutang(root);
        hutangViewModel.getHutang().observe(getViewLifecycleOwner(), new Observer<List<Hutang>>() {
            @Override
            public void onChanged(@Nullable List<Hutang> hutangs) {
//                textView.setText(s);
                hutangList = hutangs;
                hutangAdapter.updateList(hutangList);
            }
        });
        return root;
    }

    public void showDialogTambahHutang(){
        DialogFragment newFragment = new TambahHutangDialog(new TambahHutangDialog.TambahHutangInterface() {
            @Override
            public void setIntent(Intent intent) {
                if(intent.getBooleanExtra("status", false)){
                    getDaftarHutang();
                    Snackbar.make(root.findViewById(R.id.container), "Berhasil Menambahkan Hutang", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(root.findViewById(R.id.container), "Gagal Menambahkan Hutang", Snackbar.LENGTH_LONG).show();
//                    Snackbar.make(root.findViewById(R.id.container), "Terjadi sebuah Kesalahan", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        newFragment.setCancelable(false);
        newFragment.show(getActivity().getSupportFragmentManager(), "tambahhutang");
    }


    private void getDaftarHutang(){

        DaoSession session = ((App) getActivity().getApplication()).getDaoSession();
        HutangDao hutangDao = session.getHutangDao();
        this.hutangList = hutangDao.queryBuilder().orderDesc(HutangDao.Properties.Tanggal).list();
        hutangAdapter.setList(this.hutangList);
        hutangAdapter.notifyDataSetChanged();

        Cursor cursor = hutangDao.getDatabase().rawQuery("SELECT SUM(`nominal`) as total FROM " + HutangDao.TABLENAME, new String[]{});
        cursor.moveToFirst();
        totalHutang.setText("Rp." + String.valueOf(cursor.getLong(0)) + ",-");
        this.hutangViewModel.populateHutang();
    }

    private void updateDataHutang(){
        this.hutangViewModel.populateHutang();
    }

    private void initDaftarHutang(View root) {
        totalHutang = root.findViewById(R.id.total_hutang);
        pesanHutang = root.findViewById(R.id.pesan_hutang);
        tambah = root.findViewById(R.id.tambah_hutang);
        tambah.setOnClickListener(this);

        this.listHutangView = root.findViewById(R.id.daftar_hutang);

        DaoSession session = ((App) getActivity().getApplication()).getDaoSession();
        HutangDao hutangDao = session.getHutangDao();

        FragmentActivity activity = getActivity();

        hutangAdapter = new HutangAdapter(getContext(), hutangList, new HutangAdapter.UpdateEvent() {
            @Override
            public void update() {
                hutangViewModel.populateHutang();
            }

            @Override
            public void openActivity(long id) {
                Intent intent = new Intent(activity, HutangActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        listHutangView.setHasFixedSize(false);
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        listHutangView.addItemDecoration(itemDecor);
        listHutangView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        listHutangView.setAdapter(hutangAdapter);

        Cursor cursor = hutangDao.getDatabase().rawQuery("SELECT SUM(`nominal`) as total FROM " + hutangDao.TABLENAME, new String[]{});
        cursor.moveToFirst();
        totalHutang.setText("Rp." + String.valueOf(cursor.getLong(0)) + ",-");

        Cursor cursor2 = hutangDao.getDatabase().rawQuery("SELECT SUM(`nominal`) as total FROM " + hutangDao.TABLENAME + " WHERE strftime('%Y', datetime(tanggal/1000, 'unixepoch')) = strftime('%Y',date('now')) AND strftime('%m', datetime(tanggal/1000, 'unixepoch')) = strftime('%m',date('now'))", new String[]{});
        cursor2.moveToFirst();
        String pengeluaranBulanan = String.valueOf(cursor2.getLong(0));
        String nama = Preference.getName(getContext());
        pesanHutang.setText(nama + ", Pinjaman Anda bulan ini: Rp." +  pengeluaranBulanan + ",-");
    }

    @Override
    public void onClick(View v) {
        if(v == tambah){
            this.showDialogTambahHutang();
        }
    }
}