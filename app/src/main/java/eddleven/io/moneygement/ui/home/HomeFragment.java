package eddleven.io.moneygement.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import eddleven.io.moneygement.R;
import eddleven.io.moneygement.configs.Preference;
import eddleven.io.moneygement.repo.PemasukanRepo;
import eddleven.io.moneygement.repo.PengeluaranRepo;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    View root;
    TextView totalSaldo, pesanSaldo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
        this.init(root);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        return root;
    }

    private void init(View root) {
        this.totalSaldo = root.findViewById(R.id.total_saldo);
        this.pesanSaldo = root.findViewById(R.id.pesan_saldo);
        this.getTotalPengeluaran();
    }

    private void getTotalPengeluaran(){
        long totalPemasukan = PemasukanRepo.getTotalNominal(getActivity().getApplication());
        long totalPengeluaran = PengeluaranRepo.getTotalNominal(getActivity().getApplication());
        long stotalSaldo = totalPemasukan - totalPengeluaran;

        totalSaldo.setText("Rp." + String.valueOf(stotalSaldo) + ",-");
        this.getTotalPengeluaranBulanan();
    }
    private void getTotalPengeluaranBulanan(){
        long totalPemasukan = PemasukanRepo.getTotalNominalMonth(getActivity().getApplication());
        long totalPengeluaran = PengeluaranRepo.getTotalNominalMonth(getActivity().getApplication());
        long stotalSaldo = totalPemasukan - totalPengeluaran;

        String nama = Preference.getName(getContext());
        pesanSaldo.setText(nama + ", total saldo Anda bulan ini: Rp." +  String.valueOf(stotalSaldo) + ",-");
    }
}