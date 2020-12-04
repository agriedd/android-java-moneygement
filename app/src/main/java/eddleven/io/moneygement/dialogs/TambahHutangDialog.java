package eddleven.io.moneygement.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import eddleven.io.moneygement.App;
import eddleven.io.moneygement.R;
import eddleven.io.moneygement.models.DaoSession;
import eddleven.io.moneygement.models.Hutang;
import eddleven.io.moneygement.models.HutangDao;
import eddleven.io.moneygement.models.Pemasukan;
import eddleven.io.moneygement.models.PemasukanDao;

public class TambahHutangDialog extends DialogFragment implements View.OnClickListener {

    public interface TambahHutangInterface{
        public void setIntent(Intent intent);
    }

    private Button tutup, simpan;
    private TextInputEditText nominal, keterangan, tanggal_expired;
    private TextInputLayout nominalLayout, keteranganLayout, tanggalExpiredLayout;
    private TambahHutangInterface event;
    private Date tanggal_tenggat;

    public TambahHutangDialog(TambahHutangInterface event) {
        this.event = event;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = requireActivity().getLayoutInflater().inflate(R.layout.x_dialog_tambah_hutang, null);

        this.tutup = view.findViewById(R.id.tutup);
        this.simpan = view.findViewById(R.id.simpan);
        this.nominal = view.findViewById(R.id.hutang_nominal);
        this.keterangan = view.findViewById(R.id.hutang_keterangan);
        this.tanggal_expired = view.findViewById(R.id.hutang_tenggat);
        tanggal_expired.setOnClickListener(this);

        this.keteranganLayout = view.findViewById(R.id.tambah_hutang_keterangan);
        this.nominalLayout = view.findViewById(R.id.tambah_hutang_nominal);

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
        } else
        if(v == tanggal_expired){
            Calendar newCalendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                    tanggal_tenggat = newDate.getTime();
                    tanggal_expired.setText(dateFormatter.format(newDate.getTime()));
                }

            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();
        } else
        if(v == simpan){

            DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
            HutangDao hutangDao = daoSession.getHutangDao();
            Hutang hutang = new Hutang();
            hutang.setNominal(Integer.parseInt(String.valueOf(nominal.getText())));
            hutang.setKeterangan(String.valueOf(keterangan.getText()));
            hutang.setTanggal(new Date());
            hutang.setTanggal_lunas(tanggal_tenggat);
            hutangDao.insert(hutang);

            PemasukanDao pemasukanDao = daoSession.getPemasukanDao();
            Pemasukan pemasukan = new Pemasukan();
            pemasukan.setNominal(Integer.parseInt(String.valueOf(nominal.getText())));
            pemasukan.setKeterangan("Pinjaman: " + String.valueOf(keterangan.getText()));
            pemasukan.setJenis(1);
            pemasukan.setTanggal(new Date());
            pemasukanDao.insert(pemasukan);

            if(hutang.getId() != null){
                Intent intent = new Intent();
                intent.putExtra("status", true);
                intent.putExtra("message", "Berhasil");
                intent.putExtra("id", hutang.getId());

                this.event.setIntent(intent);

                this.dismiss();
            }
        }
    }
}
