package eddleven.io.moneygement.ui.hutang;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import eddleven.io.moneygement.models.Hutang;
import eddleven.io.moneygement.repo.HutangRepo;

public class HutangViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<Hutang>> mHutang;
    private Application application;

    public HutangViewModel(@NonNull Application application) {
        super(application);

        this.application = application;
        mText = new MutableLiveData<>();
        mText.setValue("This is Hutang fragment");
        mHutang = new MutableLiveData<List<Hutang>>();
        this.populateHutang();
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<List<Hutang>> getHutang(){
        return this.mHutang;
    }

    public void populateHutang(){
        this.mHutang.setValue(
                HutangRepo.getAll(this.application)
        );
    }
    public void populateHutang(Application application){
        this.mHutang.setValue(
                HutangRepo.getAll(application)
        );
    }
}