package eddleven.io.moneygement.ui.pemasukan;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PemasukanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PemasukanViewModel() {
        mText = new MutableLiveData<>();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mText.setValue("This is Pemasukan fragment");
            }
        }, 5000);
    }

    public LiveData<String> getText() {
        return mText;
    }
}