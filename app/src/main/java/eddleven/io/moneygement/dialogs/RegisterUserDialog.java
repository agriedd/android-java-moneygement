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

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import eddleven.io.moneygement.ActivityUtama;
import eddleven.io.moneygement.R;
import eddleven.io.moneygement.configs.Preference;

public class RegisterUserDialog extends DialogFragment implements View.OnClickListener {

    private Button tutup, simpan;
    private TextInputEditText registerName;
    private TextInputLayout registerNameLayout;

    public RegisterUserDialog(){

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

        View view = requireActivity().getLayoutInflater().inflate(R.layout.x_dialog_register_user, null);

        this.tutup = view.findViewById(R.id.tutup);
        this.simpan = view.findViewById(R.id.simpan);
        this.registerName = view.findViewById(R.id.register_nama);
        this.registerNameLayout = view.findViewById(R.id.register_nama_layout);

        this.tutup.setOnClickListener(this);
        this.simpan.setOnClickListener(this);

        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        if(v == this.tutup){
            this.dismiss();
        } else if(v == this.simpan){
            this.registerNameLayout.setErrorEnabled(false);

            if(Objects.requireNonNull(this.registerName.getText()).toString().trim().isEmpty()){
                this.registerNameLayout.setErrorEnabled(true);
                this.registerNameLayout.setError("Nama tidak boleh kosong");
            } else {
                Preference.SET_NAME(getActivity(), this.registerName.getText().toString().trim());
                requireActivity().startActivity(new Intent(
                        getActivity(),
                        ActivityUtama.class
                ));
            }
        }
    }
}
