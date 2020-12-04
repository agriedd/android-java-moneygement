package eddleven.io.moneygement.models;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(indexes = {
        @Index(value = "tanggal DESC")
})
public class BayarHutang {
    @Id
    private Long id;

    @NotNull
    private Date tanggal;
    private Integer nominal;

    private String keterangan;
    private Long id_hutang;
    private Long id_pengeluaran;

    @ToOne( joinProperty = "id_pengeluaran")
    private Pengeluaran pengeluaran;

    @ToOne( joinProperty = "id_hutang")
    private Hutang hutang;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 142028901)
private transient BayarHutangDao myDao;

@Generated(hash = 89812362)
public BayarHutang(Long id, @NotNull Date tanggal, Integer nominal,
        String keterangan, Long id_hutang, Long id_pengeluaran) {
    this.id = id;
    this.tanggal = tanggal;
    this.nominal = nominal;
    this.keterangan = keterangan;
    this.id_hutang = id_hutang;
    this.id_pengeluaran = id_pengeluaran;
}

@Generated(hash = 1867285358)
public BayarHutang() {
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

public Long getId_hutang() {
    return this.id_hutang;
}

public void setId_hutang(Long id_hutang) {
    this.id_hutang = id_hutang;
}

public Long getId_pengeluaran() {
    return this.id_pengeluaran;
}

public void setId_pengeluaran(Long id_pengeluaran) {
    this.id_pengeluaran = id_pengeluaran;
}

@Generated(hash = 215743920)
private transient Long pengeluaran__resolvedKey;

/** To-one relationship, resolved on first access. */
@Generated(hash = 1319735171)
public Pengeluaran getPengeluaran() {
    Long __key = this.id_pengeluaran;
    if (pengeluaran__resolvedKey == null
            || !pengeluaran__resolvedKey.equals(__key)) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        PengeluaranDao targetDao = daoSession.getPengeluaranDao();
        Pengeluaran pengeluaranNew = targetDao.load(__key);
        synchronized (this) {
            pengeluaran = pengeluaranNew;
            pengeluaran__resolvedKey = __key;
        }
    }
    return pengeluaran;
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 573905701)
public void setPengeluaran(Pengeluaran pengeluaran) {
    synchronized (this) {
        this.pengeluaran = pengeluaran;
        id_pengeluaran = pengeluaran == null ? null : pengeluaran.getId();
        pengeluaran__resolvedKey = id_pengeluaran;
    }
}

@Generated(hash = 1221306332)
private transient Long hutang__resolvedKey;

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
@Generated(hash = 886277645)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getBayarHutangDao() : null;
}
}
