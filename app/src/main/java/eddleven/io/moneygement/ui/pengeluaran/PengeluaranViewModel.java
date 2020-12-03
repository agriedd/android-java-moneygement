package eddleven.io.moneygement.ui.pengeluaran;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import eddleven.io.moneygement.models.Pengeluaran;
import eddleven.io.moneygement.repo.PengeluaranRepo;

public class PengeluaranViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<Pengeluaran>> mPengeluaran;
    private Application application;

    public PengeluaranViewModel(@NonNull Application application) {
        super(application);

        this.application = application;
        mText = new MutableLiveData<>();
        mText.setValue("This is Pemasukan fragment");
        mPengeluaran = new MutableLiveData<List<Pengeluaran>>();
        this.populatePengeluaran();
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<List<Pengeluaran>> getPengeluaran(){
        return this.mPengeluaran;
    }

    public void populatePengeluaran(){
        this.mPengeluaran.setValue(
                PengeluaranRepo.getAll(this.application)
        );
    }
    public void populatePengeluaran(Application application){
        this.mPengeluaran.setValue(
                PengeluaranRepo.getAll(application)
        );
    }
}