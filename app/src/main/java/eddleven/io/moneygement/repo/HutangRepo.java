package eddleven.io.moneygement.repo;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;

import java.util.List;

import androidx.fragment.app.FragmentActivity;
import eddleven.io.moneygement.App;
import eddleven.io.moneygement.models.DaoSession;
import eddleven.io.moneygement.models.Hutang;
import eddleven.io.moneygement.models.HutangDao;
import eddleven.io.moneygement.models.Pengeluaran;
import eddleven.io.moneygement.models.PengeluaranDao;

public class HutangRepo {
    public static DaoSession getDB(Application application){
        return ((App) application).getDaoSession();
    }
    public static DaoSession getDB(Context context){
        FragmentActivity activity = (FragmentActivity) context;
        App app = (App) activity.getApplication();
        return app.getDaoSession();
    }
    public static List<Hutang> getAll(Application application){
        DaoSession daoSession = getDB(application);
        return daoSession.getHutangDao()
                .queryBuilder()
                .orderDesc(HutangDao.Properties.Tanggal)
                .list();
    }
    public static Hutang find(Application application, long id){
        DaoSession daoSession = getDB(application);
        return daoSession.getHutangDao().load(id);
    }
    public static Boolean delete(Application application, long id){
        DaoSession daoSession = getDB(application);
        HutangDao hutangDao = daoSession.getHutangDao();
        hutangDao.deleteByKey(id);
        return true;
    }
    public static long getTotalNominal(Application application){
        DaoSession daoSession = getDB(application);
        HutangDao hutangDao = daoSession.getHutangDao();
        Cursor cursor = hutangDao.getDatabase().rawQuery("SELECT SUM(`nominal`) as total FROM " + hutangDao.TABLENAME + " WHERE " + HutangDao.Properties.Status.columnName + " = " + 0, new String[]{});
        cursor.moveToFirst();
        return cursor.getLong(0);
    }
    public static long getTotalNominalMonth(Application application){
        DaoSession daoSession = getDB(application);
        HutangDao hutangDao = daoSession.getHutangDao();
        Cursor cursor = hutangDao.getDatabase().rawQuery("SELECT SUM(`nominal`) as total FROM " + hutangDao.TABLENAME + " WHERE strftime('%Y', datetime(tanggal/1000, 'unixepoch')) = strftime('%Y',date('now')) AND strftime('%m', datetime(tanggal/1000, 'unixepoch')) = strftime('%m',date('now'))" + " AND " + HutangDao.Properties.Status.columnName + " = " + 0, new String[]{});
        cursor.moveToFirst();

        return cursor.getLong(0);
    }
}
