package eddleven.io.moneygement.ui.pemasukan;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import eddleven.io.moneygement.models.Pemasukan;
import eddleven.io.moneygement.repo.PemasukanRepo;

public class PemasukanViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<Pemasukan>> mPemasukan;
    private Application application;

    public PemasukanViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        mText = new MutableLiveData<>();
        mText.setValue("This is Pemasukan fragment");
        mPemasukan = new MutableLiveData<List<Pemasukan>>();
        this.populatePemasukan();
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<List<Pemasukan>> getPemasukan(){
        return this.mPemasukan;
    }

    public void populatePemasukan(){
        this.mPemasukan.setValue(
            PemasukanRepo.getAll(this.application)
        );
    }
    public void populatePemasukan(Application application){
        this.mPemasukan.setValue(
                PemasukanRepo.getAll(application)
        );
    }
}