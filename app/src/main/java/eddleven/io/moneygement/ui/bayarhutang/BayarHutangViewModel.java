package eddleven.io.moneygement.ui.bayarhutang;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import eddleven.io.moneygement.models.BayarHutang;
import eddleven.io.moneygement.models.Hutang;
import eddleven.io.moneygement.repo.BayarHutangRepo;
import eddleven.io.moneygement.repo.HutangRepo;

public class BayarHutangViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<BayarHutang>> mHutang;
    private Application application;
    private Hutang hutang;

    public BayarHutangViewModel(@NonNull Application application) {
        super(application);

        this.application = application;
        mHutang = new MutableLiveData<List<BayarHutang>>();
    }

    public LiveData<List<BayarHutang>> getHutang(Hutang hutang){
        this.hutang = hutang;
        return this.mHutang;
    }

    public void populateHutang(){
        this.mHutang.setValue(
                BayarHutangRepo.getAllByHutang(this.application, hutang.getId())
        );
    }
    public void populateHutang(Application application){
        this.mHutang.setValue(
                BayarHutangRepo.getAllByHutang(application, this.hutang.getId())
        );
    }

    public void setHutan(Hutang hutang) {
        this.hutang = hutang;
    }
}