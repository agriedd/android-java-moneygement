package eddleven.io.moneygement.repo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.List;

import androidx.fragment.app.FragmentActivity;
import eddleven.io.moneygement.App;
import eddleven.io.moneygement.models.DaoSession;
import eddleven.io.moneygement.models.Pemasukan;
import eddleven.io.moneygement.models.PemasukanDao;

public class PemasukanRepo {
    public static DaoSession getDB(Application application){
        return ((App) application).getDaoSession();
    }
    public static DaoSession getDB(Context context){
        FragmentActivity activity = (FragmentActivity) context;
        App app = (App) activity.getApplication();
        return app.getDaoSession();
    }
    public static List<Pemasukan> getAll(Application application){
        DaoSession daoSession = getDB(application);
        return daoSession.getPemasukanDao()
                .queryBuilder()
                .orderDesc(PemasukanDao.Properties.Tanggal)
                .list();
    }
    public static Pemasukan find(Application application, long id){
        DaoSession daoSession = getDB(application);
        return daoSession.getPemasukanDao().load(id);
    }
}
