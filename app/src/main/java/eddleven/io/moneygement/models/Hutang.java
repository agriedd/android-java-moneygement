package eddleven.io.moneygement.models;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.DaoException;

@Entity(indexes = {
        @Index(value = "id_kategori, tanggal DESC")
})
public class Hutang {
    @Id
    private Long id;

    @NotNull
    private Date tanggal;
    private Integer nominal;
    private Integer status;

    private String keterangan;
    private Double bunga;
    private Date tanggal_lunas;
    private Long id_kategori;
    private Long id_pemasukan;

    @ToOne( joinProperty = "id_pemasukan")
    private Pemasukan pemasukan;

    @ToMany( referencedJoinProperty = "id_hutang" )
    @OrderBy("tanggal DESC")
    private List<BayarHutang> bayarHutangs;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 1448832256)
private transient HutangDao myDao;

@Generated(hash = 1766889145)
public Hutang(Long id, @NotNull Date tanggal, Integer nominal, Integer status,
        String keterangan, Double bunga, Date tanggal_lunas, Long id_kategori,
        Long id_pemasukan) {
    this.id = id;
    this.tanggal = tanggal;
    this.nominal = nominal;
    this.status = status;
    this.keterangan = keterangan;
    this.bunga = bunga;
    this.tanggal_lunas = tanggal_lunas;
    this.id_kategori = id_kategori;
    this.id_pemasukan = id_pemasukan;
}

@Generated(hash = 88626436)
public Hutang() {
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

public Integer getStatus() {
    return this.status;
}

public void setStatus(Integer status) {
    this.status = status;
}

public String getKeterangan() {
    return this.keterangan;
}

public void setKeterangan(String keterangan) {
    this.keterangan = keterangan;
}

public Double getBunga() {
    return this.bunga;
}

public void setBunga(Double bunga) {
    this.bunga = bunga;
}

public Date getTanggal_lunas() {
    return this.tanggal_lunas;
}

public void setTanggal_lunas(Date tanggal_lunas) {
    this.tanggal_lunas = tanggal_lunas;
}

public Long getId_kategori() {
    return this.id_kategori;
}

public void setId_kategori(Long id_kategori) {
    this.id_kategori = id_kategori;
}

public Long getId_pemasukan() {
    return this.id_pemasukan;
}

public void setId_pemasukan(Long id_pemasukan) {
    this.id_pemasukan = id_pemasukan;
}

@Generated(hash = 1973977777)
private transient Long pemasukan__resolvedKey;

/** To-one relationship, resolved on first access. */
@Generated(hash = 1364192104)
public Pemasukan getPemasukan() {
    Long __key = this.id_pemasukan;
    if (pemasukan__resolvedKey == null
            || !pemasukan__resolvedKey.equals(__key)) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        PemasukanDao targetDao = daoSession.getPemasukanDao();
        Pemasukan pemasukanNew = targetDao.load(__key);
        synchronized (this) {
            pemasukan = pemasukanNew;
            pemasukan__resolvedKey = __key;
        }
    }
    return pemasukan;
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1194306397)
public void setPemasukan(Pemasukan pemasukan) {
    synchronized (this) {
        this.pemasukan = pemasukan;
        id_pemasukan = pemasukan == null ? null : pemasukan.getId();
        pemasukan__resolvedKey = id_pemasukan;
    }
}

/**
 * To-many relationship, resolved on first access (and after reset).
 * Changes to to-many relations are not persisted, make changes to the target entity.
 */
@Generated(hash = 207670125)
public List<BayarHutang> getBayarHutangs() {
    if (bayarHutangs == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        BayarHutangDao targetDao = daoSession.getBayarHutangDao();
        List<BayarHutang> bayarHutangsNew = targetDao
                ._queryHutang_BayarHutangs(id);
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

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1402715843)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getHutangDao() : null;
}
}
