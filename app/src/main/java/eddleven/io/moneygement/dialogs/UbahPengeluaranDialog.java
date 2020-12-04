package eddleven.io.moneygement.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import eddleven.io.moneygement.App;
import eddleven.io.moneygement.R;
import eddleven.io.moneygement.models.DaoSession;
import eddleven.io.moneygement.models.Pengeluaran;
import eddleven.io.moneygement.models.PengeluaranDao;
import eddleven.io.moneygement.repo.PengeluaranRepo;

public class UbahPengeluaranDialog extends DialogFragment implements View.OnClickListener {

    public interface UbahPengeluaranInterface{
        public void setIntent(Intent intent);
    }

    private Button tutup, simpan;
    private TextInputEditText nominal, keterangan;
    private TextInputLayout nominalLayout, keteranganLayout;
    private UbahPengeluaranInterface event;
    private Pengeluaran pengeluaran;
    private long id;

    public UbahPengeluaranDialog(long id, UbahPengeluaranInterface event) {
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

        View view = requireActivity().getLayoutInflater().inflate(R.layout.x_dialog_tambah_pengeluaran, null);

        this.pengeluaran = PengeluaranRepo.find(getActivity().getApplication(), id);

        this.tutup = view.findViewById(R.id.tutup);
        this.simpan = view.findViewById(R.id.simpan);
        this.nominal = view.findViewById(R.id.pengeluaran_nominal);
        this.keterangan = view.findViewById(R.id.pengeluaran_keterangan);

        nominal.setText(pengeluaran.getNominal().toString());
        keterangan.setText(pengeluaran.getKeterangan().toString());

        this.keteranganLayout = view.findViewById(R.id.tambah_pengeluaran_keterangan);
        this.nominalLayout = view.findViewById(R.id.tambah_pengeluaran_nominal);

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
            PengeluaranDao pengeluaranDao = daoSession.getPengeluaranDao();
            Pengeluaran pengeluaran = pengeluaranDao.load(id);
            pengeluaran.setNominal(Integer.parseInt(String.valueOf(nominal.getText())));
            pengeluaran.setKeterangan(String.valueOf(keterangan.getText()));
            pengeluaranDao.update(pengeluaran);

            if(pengeluaran.getId() != null){
                Intent intent = new Intent();
                intent.putExtra("status", true);
                intent.putExtra("message", "Berhasil");
                intent.putExtra("id", pengeluaran.getId());

                this.event.setIntent(intent);

                this.dismiss();
            }
        }
    }
}
