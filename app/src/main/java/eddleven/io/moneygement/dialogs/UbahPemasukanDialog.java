package eddleven.io.moneygement.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import eddleven.io.moneygement.App;
import eddleven.io.moneygement.R;
import eddleven.io.moneygement.models.DaoSession;
import eddleven.io.moneygement.models.Pemasukan;
import eddleven.io.moneygement.models.PemasukanDao;
import eddleven.io.moneygement.repo.PemasukanRepo;

public class UbahPemasukanDialog extends DialogFragment implements View.OnClickListener {

    public interface UbahPemasukanInterface{
        public void setIntent(Intent intent);
    }

    private Button tutup, simpan;
    private TextInputEditText nominal, keterangan;
    private TextInputLayout nominalLayout, keteranganLayout;
    private UbahPemasukanInterface event;
    private Pemasukan pemasukan;
    private long id;

    public UbahPemasukanDialog(long id, UbahPemasukanInterface event) {
        this.event = event;
        this.id = id;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = requireActivity().getLayoutInflater().inflate(R.layout.x_dialog_tambah_pemasukan, null);

        this.pemasukan = PemasukanRepo.find(getActivity().getApplication(), id);

        this.tutup = view.findViewById(R.id.tutup);
        this.simpan = view.findViewById(R.id.simpan);
        this.nominal = view.findViewById(R.id.pemasukan_nominal);
        this.keterangan = view.findViewById(R.id.pemasukan_keterangan);

        nominal.setText(pemasukan.getNominal().toString());
        keterangan.setText(pemasukan.getKeterangan().toString());

        this.keteranganLayout = view.findViewById(R.id.tambah_pemasukan_keterangan);
        this.nominalLayout = view.findViewById(R.id.tambah_pemasukan_nominal);

        this.tutup.setOnClickListener(this);
        this.simpan.setOnClickListener(this);

        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
    @Override
    public void onClick(View v) {
        if(v == tutup){
            this.dismiss();
        }
        if(v == simpan){
            DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
            PemasukanDao pemasukanDao = daoSession.getPemasukanDao();
            Pemasukan pemasukan = pemasukanDao.load(id);
            pemasukan.setNominal(Integer.parseInt(String.valueOf(nominal.getText())));
            pemasukan.setKeterangan(String.valueOf(keterangan.getText()));
            pemasukanDao.update(pemasukan);

            if(pemasukan.getId() != null){
                Intent intent = new Intent();
                intent.putExtra("status", true);
                intent.putExtra("message", "Berhasil");
                intent.putExtra("id", pemasukan.getId());

                this.event.setIntent(intent);

                this.dismiss();
            }
        }
    }
}
