package eddleven.io.moneygement.repo;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;

import java.util.List;

import androidx.fragment.app.FragmentActivity;
import eddleven.io.moneygement.App;
import eddleven.io.moneygement.models.BayarHutang;
import eddleven.io.moneygement.models.BayarHutangDao;
import eddleven.io.moneygement.models.DaoSession;
import eddleven.io.moneygement.models.Hutang;
import eddleven.io.moneygement.models.HutangDao;

public class BayarHutangRepo {
    public static DaoSession getDB(Application application){
        return ((App) application).getDaoSession();
    }
    public static DaoSession getDB(Context context){
        FragmentActivity activity = (FragmentActivity) context;
        App app = (App) activity.getApplication();
        return app.getDaoSession();
    }
    public static List<BayarHutang> getAll(Application application){
        DaoSession daoSession = getDB(application);
        return daoSession.getBayarHutangDao()
                .queryBuilder()
                .orderDesc(BayarHutangDao.Properties.Tanggal)
                .list();
    }
    public static List<BayarHutang> getAllByHutang(Application application, long id){
        DaoSession daoSession = getDB(application);
        return daoSession.getBayarHutangDao()
                .queryBuilder()
                .where(BayarHutangDao.Properties.Id_hutang.eq(id))
                .orderDesc(BayarHutangDao.Properties.Tanggal)
                .list();
    }
    public static BayarHutang find(Application application, long id){
        DaoSession daoSession = getDB(application);
        return daoSession.getBayarHutangDao().load(id);
    }
    public static Boolean delete(Application application, long id){
        DaoSession daoSession = getDB(application);
        BayarHutangDao hutangDao = daoSession.getBayarHutangDao();
        hutangDao.deleteByKey(id);
        return true;
    }
    public static long getTotalNominal(Application application){
        DaoSession daoSession = getDB(application);
        BayarHutangDao hutangDao = daoSession.getBayarHutangDao();
        Cursor cursor = hutangDao.getDatabase().rawQuery("SELECT SUM(`nominal`) as total FROM " + BayarHutangDao.TABLENAME, new String[]{});
        cursor.moveToFirst();
        return cursor.getLong(0);
    }
    public static long getTotalNominalMonth(Application application){
        DaoSession daoSession = getDB(application);
        BayarHutangDao hutangDao = daoSession.getBayarHutangDao();
        Cursor cursor = hutangDao.getDatabase().rawQuery("SELECT SUM(`nominal`) as total FROM " + hutangDao.TABLENAME + " WHERE strftime('%Y', datetime(tanggal/1000, 'unixepoch')) = strftime('%Y',date('now')) AND strftime('%m', datetime(tanggal/1000, 'unixepoch')) = strftime('%m',date('now'))", new String[]{});
        cursor.moveToFirst();

        return cursor.getLong(0);
    }

    public static long getTotalNominalByHutang(Application application, Long id) {
        DaoSession daoSession = getDB(application);
        BayarHutangDao hutangDao = daoSession.getBayarHutangDao();
        Cursor cursor = hutangDao.getDatabase().rawQuery("SELECT SUM(`nominal`) as total FROM " + BayarHutangDao.TABLENAME + " WHERE " + BayarHutangDao.Properties.Id_hutang.columnName + " = " + id, new String[]{});
        cursor.moveToFirst();
        return cursor.getLong(0);
    }
}
