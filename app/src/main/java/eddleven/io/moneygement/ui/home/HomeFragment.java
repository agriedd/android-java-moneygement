package eddleven.io.moneygement.ui.home;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import eddleven.io.moneygement.R;
import eddleven.io.moneygement.configs.Preference;
import eddleven.io.moneygement.repo.HutangRepo;
import eddleven.io.moneygement.repo.PemasukanRepo;
import eddleven.io.moneygement.repo.PengeluaranRepo;
import eddleven.io.moneygement.ui.hutang.HutangFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    View root;
    TextView totalSaldo, pesanSaldo;
    TextView totalHutang;
    Button toHutang;

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
        this.totalHutang = root.findViewById(R.id.total_hutang);
        this.getTotalPengeluaran();

        long totalHutangs = HutangRepo.getTotalNominal(getActivity().getApplication());
        if (totalHutangs > 0){
            root.findViewById(R.id.info_hutang_container).setVisibility(View.VISIBLE);
            totalHutang.setText("Rp." + String.valueOf(totalHutangs) + ",-");
            toHutang = root.findViewById(R.id.btn_bayar_utang);
            totalHutang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment frag = new HutangFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.nav_host_fragment, frag);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });
        } else {
            root.findViewById(R.id.info_hutang_container).setVisibility(View.GONE);
        }
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

        TextView infoPengeluaran = root.findViewById(R.id.info_saldo_pengeluaran);
        TextView infoPemasukan = root.findViewById(R.id.info_saldo_pemasukan);
        ImageView infoImg = root.findViewById(R.id.info_saldo_img);
        infoPengeluaran.setText("Rp." + String.valueOf(totalPengeluaran) + ",-");
        infoPemasukan.setText("Rp." + String.valueOf(totalPemasukan) + ",-");
        if(stotalSaldo > 0){
            infoImg.setImageResource(R.drawable.ic_baseline_show_chart_24);
        } else {
            infoImg.setImageResource(R.drawable.ic_baseline_trending_down_24);
        }
    }
}