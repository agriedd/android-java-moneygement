package eddleven.io.moneygement.repo;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;

import org.greenrobot.greendao.query.DeleteQuery;

import java.util.List;

import androidx.fragment.app.FragmentActivity;
import eddleven.io.moneygement.App;
import eddleven.io.moneygement.models.DaoSession;
import eddleven.io.moneygement.models.Pemasukan;
import eddleven.io.moneygement.models.PemasukanDao;
import eddleven.io.moneygement.models.Pengeluaran;
import eddleven.io.moneygement.models.PengeluaranDao;

public class PengeluaranRepo {
    public static DaoSession getDB(Application application){
        return ((App) application).getDaoSession();
    }
    public static DaoSession getDB(Context context){
        FragmentActivity activity = (FragmentActivity) context;
        App app = (App) activity.getApplication();
        return app.getDaoSession();
    }
    public static List<Pengeluaran> getAll(Application application){
        DaoSession daoSession = getDB(application);
        return daoSession.getPengeluaranDao()
                .queryBuilder()
                .orderDesc(PengeluaranDao.Properties.Tanggal)
                .list();
    }
    public static Pengeluaran find(Application application, long id){
        DaoSession daoSession = getDB(application);
        return daoSession.getPengeluaranDao().load(id);
    }

    public static Boolean deleteByHutang(Application application, long id){
        DaoSession daoSession = getDB(application);
        final DeleteQuery<Pengeluaran> tableDeleteQuery = daoSession.queryBuilder(Pengeluaran.class)
                .where(PengeluaranDao.Properties.Id_hutang.eq(id))
                .buildDelete();
        tableDeleteQuery.executeDeleteWithoutDetachingEntities();
        daoSession.clear();
        return true;
    }
    public static long getTotalNominal(Application application){
        DaoSession daoSession = getDB(application);
        PengeluaranDao pengeluaranDao = daoSession.getPengeluaranDao();
        Cursor cursor = pengeluaranDao.getDatabase().rawQuery("SELECT SUM(`nominal`) as total FROM " + pengeluaranDao.TABLENAME, new String[]{});
        cursor.moveToFirst();
        return cursor.getLong(0);
    }
    public static long getTotalNominalMonth(Application application){
        DaoSession daoSession = getDB(application);
        PengeluaranDao pengeluaranDao = daoSession.getPengeluaranDao();
        Cursor cursor = pengeluaranDao.getDatabase().rawQuery("SELECT SUM(`nominal`) as total FROM " + pengeluaranDao.TABLENAME + " WHERE strftime('%Y', datetime(tanggal/1000, 'unixepoch')) = strftime('%Y',date('now')) AND strftime('%m', datetime(tanggal/1000, 'unixepoch')) = strftime('%m',date('now'))", new String[]{});
        cursor.moveToFirst();

        return cursor.getLong(0);
    }
}
