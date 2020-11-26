package eddleven.io.moneygement.ui.pemasukan;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import eddleven.io.moneygement.App;
import eddleven.io.moneygement.R;
import eddleven.io.moneygement.adapters.recycler.PemasukanAdapter;
import eddleven.io.moneygement.models.DaoSession;
import eddleven.io.moneygement.models.Pemasukan;
import eddleven.io.moneygement.models.PemasukanDao;

public class PemasukanFragment extends Fragment {

    private PemasukanViewModel pemasukanViewModel;
    private RecyclerView listPemasukanView;
    private PemasukanAdapter pemasukanAdapter;
    private List<Pemasukan> listPemasukan = new ArrayList<Pemasukan>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pemasukanViewModel =
                ViewModelProviders.of(this).get(PemasukanViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pemasukan, container, false);
//        final TextView textView = root.findViewById(R.id.text_pemasukan);

        this.initDaftarPemasukan(root);

        pemasukanViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        return root;
    }

    private void initDaftarPemasukan(View root) {
        TextView totalPemasukan = root.findViewById(R.id.total_pemasukan);

        this.listPemasukanView = root.findViewById(R.id.daftar_pemasukan);
        Pemasukan pemasukan = new Pemasukan();
        pemasukan.setKeterangan("Hello");
        pemasukan.setTanggal(Calendar.getInstance().getTime());
        pemasukan.setNominal(100000);
        DaoSession session = ((App) getActivity().getApplication()).getDaoSession();
        PemasukanDao pemasukanDao = session.getPemasukanDao();
        pemasukanDao.insert(pemasukan);

        this.listPemasukan = pemasukanDao.loadAll();
        pemasukanAdapter = new PemasukanAdapter(getContext(), listPemasukan);
        listPemasukanView.setHasFixedSize(false);
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        listPemasukanView.addItemDecoration(itemDecor);
        listPemasukanView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        listPemasukanView.setAdapter(pemasukanAdapter);

        Cursor cursor = pemasukanDao.getDatabase().rawQuery("SELECT SUM(`nominal`) as total FROM " + pemasukanDao.TABLENAME, new String[]{});
        cursor.moveToFirst();
        totalPemasukan.setText("Rp." + String.valueOf(cursor.getLong(0)) + ",-");
    }
}