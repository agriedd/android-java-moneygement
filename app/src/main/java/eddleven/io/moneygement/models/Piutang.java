package eddleven.io.moneygement.models;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(indexes = {
        @Index(value = "id_kategori, tanggal DESC")
})
public class Piutang {
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

    @ToMany( referencedJoinProperty = "id_piutang" )
    @OrderBy("tanggal DESC")
    private List<BayarPiutang> bayarPiutangs;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 810164557)
private transient PiutangDao myDao;

@Generated(hash = 186766001)
public Piutang(Long id, @NotNull Date tanggal, Integer nominal, Integer status,
        String keterangan, Double bunga, Date tanggal_lunas, Long id_kategori) {
    this.id = id;
    this.tanggal = tanggal;
    this.nominal = nominal;
    this.status = status;
    this.keterangan = keterangan;
    this.bunga = bunga;
    this.tanggal_lunas = tanggal_lunas;
    this.id_kategori = id_kategori;
}

@Generated(hash = 617462171)
public Piutang() {
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

/**
 * To-many relationship, resolved on first access (and after reset).
 * Changes to to-many relations are not persisted, make changes to the target entity.
 */
@Generated(hash = 793439383)
public List<BayarPiutang> getBayarPiutangs() {
    if (bayarPiutangs == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        BayarPiutangDao targetDao = daoSession.getBayarPiutangDao();
        List<BayarPiutang> bayarPiutangsNew = targetDao
                ._queryPiutang_BayarPiutangs(id);
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
@Generated(hash = 76925531)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getPiutangDao() : null;
}

}