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
public class Pengeluaran {
    @Id
    private Long id;

    @NotNull
    private Date tanggal;
    private Integer nominal;

    private String keterangan;
    private Long id_kategori;
    private Long id_piutang;
    private Integer jenis = 0;
    private Long id_hutang;

    @ToOne( joinProperty = "id_piutang")
    private Piutang piutang;

    @ToMany( referencedJoinProperty = "id_pengeluaran" )
    @OrderBy( "tanggal DESC" )
    private List<BayarHutang> bayarHutangs;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 28770752)
private transient PengeluaranDao myDao;

@Generated(hash = 1232600601)
private transient Long piutang__resolvedKey;

@Generated(hash = 236801833)
public Pengeluaran(Long id, @NotNull Date tanggal, Integer nominal, String keterangan,
        Long id_kategori, Long id_piutang, Integer jenis, Long id_hutang) {
    this.id = id;
    this.tanggal = tanggal;
    this.nominal = nominal;
    this.keterangan = keterangan;
    this.id_kategori = id_kategori;
    this.id_piutang = id_piutang;
    this.jenis = jenis;
    this.id_hutang = id_hutang;
}
@Generated(hash = 1034899702)
public Pengeluaran() {
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
public Integer getNominal() {
    return this.nominal;
}
public void setNominal(Integer nominal) {
    this.nominal = nominal;
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
public Long getId_piutang() {
    return this.id_piutang;
}
public void setId_piutang(Long id_piutang) {
    this.id_piutang = id_piutang;
}
public Integer getJenis() {
    return this.jenis;
}
public void setJenis(Integer jenis) {
    this.jenis = jenis;
}
/** To-one relationship, resolved on first access. */
@Generated(hash = 1689819294)
public Piutang getPiutang() {
    Long __key = this.id_piutang;
    if (piutang__resolvedKey == null || !piutang__resolvedKey.equals(__key)) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        PiutangDao targetDao = daoSession.getPiutangDao();
        Piutang piutangNew = targetDao.load(__key);
        synchronized (this) {
            piutang = piutangNew;
            piutang__resolvedKey = __key;
        }
    }
    return piutang;
}
/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 532818417)
public void setPiutang(Piutang piutang) {
    synchronized (this) {
        this.piutang = piutang;
        id_piutang = piutang == null ? null : piutang.getId();
        piutang__resolvedKey = id_piutang;
    }
}
/**
 * To-many relationship, resolved on first access (and after reset).
 * Changes to to-many relations are not persisted, make changes to the target entity.
 */
@Generated(hash = 28945454)
public List<BayarHutang> getBayarHutangs() {
    if (bayarHutangs == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        BayarHutangDao targetDao = daoSession.getBayarHutangDao();
        List<BayarHutang> bayarHutangsNew = targetDao
                ._queryPengeluaran_BayarHutangs(id);
        synchronized (this) {
            if (bayarHutangs == null) {
                bayarHutangs = bayarHutangsNew;
            }
        }
    }
    return bayarHutangs;
}
/** Resets a to-many relationship, making the next get call to query for a fresh result. */
@Generated(hash = 64719377)
public synchronized void resetBayarHutangs() {
    bayarHutangs = null;
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
public Long getId_hutang() {
    return this.id_hutang;
}
public void setId_hutang(Long id_hutang) {
    this.id_hutang = id_hutang;
}
/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 803416777)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getPengeluaranDao() : null;
}
}
