package eddleven.io.moneygement.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import java.util.List;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

@Entity(indexes = {
        @Index(value = "id_kategori, tanggal DESC")
})
public class Pemasukan {
    @Id
    private Long id;

    @NotNull
    private Date tanggal;
    private Integer nominal;

    private String keterangan;
    private Long id_kategori;
    private Long id_hutang;
    private Integer jenis = 0;

    @ToOne( joinProperty = "id_hutang")
    private Hutang hutang;

    @ToMany( referencedJoinProperty = "id_pemasukan" )
    @OrderBy("tanggal DESC")
    private List<BayarPiutang> bayarPiutangs;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 1032913912)
private transient PemasukanDao myDao;

@Generated(hash = 1221306332)
private transient Long hutang__resolvedKey;

@Generated(hash = 543465497)
public Pemasukan(Long id, @NotNull Date tanggal, Integer nominal,
        String keterangan, Long id_kategori, Long id_hutang, Integer jenis) {
    this.id = id;
    this.tanggal = tanggal;
    this.nominal = nominal;
    this.keterangan = keterangan;
    this.id_kategori = id_kategori;
    this.id_hutang = id_hutang;
    this.jenis = jenis;
}
@Generated(hash = 1565316908)
public Pemasukan() {
}
public Long getId() {
    return this.id;
}
public void setId(Long id) {
    this.id = id;
}
public Date getTanggal() {
    return this.tanggal;
}
public void setTanggal(Date tanggal) {
    this.tanggal = tanggal;
}
public String getKeterangan() {
    return this.keterangan;
}
public void setKeterangan(String keterangan) {
    this.keterangan = keterangan;
}
public Long getId_kategori() {
    return this.id_kategori;
}
public void setId_kategori(Long id_kategori) {
    this.id_kategori = id_kategori;
}
public Integer getNominal() {
    return this.nominal;
}
public void setNominal(Integer nominal) {
    this.nominal = nominal;
}
public Long getId_hutang() {
    return this.id_hutang;
}
public void setId_hutang(Long id_hutang) {
    this.id_hutang = id_hutang;
}
public Integer getJenis() {
    return this.jenis;
}
public void setJenis(Integer jenis) {
    this.jenis = jenis;
}
/** To-one relationship, resolved on first access. */
@Generated(hash = 229203850)
public Hutang getHutang() {
    Long __key = this.id_hutang;
    if (hutang__resolvedKey == null || !hutang__resolvedKey.equals(__key)) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        HutangDao targetDao = daoSession.getHutangDao();
        Hutang hutangNew = targetDao.load(__key);
        synchronized (this) {
            hutang = hutangNew;
            hutang__resolvedKey = __key;
        }
    }
    return hutang;
}
/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1701229407)
public void setHutang(Hutang hutang) {
    synchronized (this) {
        this.hutang = hutang;
        id_hutang = hutang == null ? null : hutang.getId();
        hutang__resolvedKey = id_hutang;
    }
}
/**
 * To-many relationship, resolved on first access (and after reset).
 * Changes to to-many relations are not persisted, make changes to the target entity.
 */
@Generated(hash = 1718106792)
public List<BayarPiutang> getBayarPiutangs() {
    if (bayarPiutangs == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        BayarPiutangDao targetDao = daoSession.getBayarPiutangDao();
        List<BayarPiutang> bayarPiutangsNew = targetDao
                ._queryPemasukan_BayarPiutangs(id);
        synchronized (this) {
            if (bayarPiutangs == null) {
                bayarPiutangs = bayarPiutangsNew;
            }
        }
    }
    return bayarPiutangs;
}
/** Resets a to-many relationship, making the next get call to query for a fresh result. */
@Generated(hash = 769552536)
public synchronized void resetBayarPiutangs() {
    bayarPiutangs = null;
}
/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 128553479)
public void delete() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.delete(this);
}
/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 1942392019)
public void refresh() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.refresh(this);
}
/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 713229351)
public void update() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.update(this);
}
/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 2072117055)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getPemasukanDao() : null;
}
}