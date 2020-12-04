package eddleven.io.moneygement.repo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;

import java.util.List;

import androidx.fragment.app.FragmentActivity;
import eddleven.io.moneygement.App;
import eddleven.io.moneygement.models.DaoSession;
import eddleven.io.moneygement.models.Pemasukan;
import eddleven.io.moneygement.models.PemasukanDao;
import eddleven.io.moneygement.models.PengeluaranDao;

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

    public static long getTotalNominal(Application application){
        DaoSession daoSession = getDB(application);
        PemasukanDao pemasukanDao = daoSession.getPemasukanDao();
        Cursor cursor = pemasukanDao.getDatabase().rawQuery("SELECT SUM(`nominal`) as total FROM " + pemasukanDao.TABLENAME, new String[]{});
        cursor.moveToFirst();
        return cursor.getLong(0);
    }
    public static long getTotalNominalMonth(Application application){
        DaoSession daoSession = getDB(application);
        PemasukanDao pemasukanDao = daoSession.getPemasukanDao();
        Cursor cursor = pemasukanDao.getDatabase().rawQuery("SELECT SUM(`nominal`) as total FROM " + pemasukanDao.TABLENAME + " WHERE strftime('%Y', datetime(tanggal/1000, 'unixepoch')) = strftime('%Y',date('now')) AND strftime('%m', datetime(tanggal/1000, 'unixepoch')) = strftime('%m',date('now'))", new String[]{});
        cursor.moveToFirst();
        return cursor.getLong(0);
    }
}
