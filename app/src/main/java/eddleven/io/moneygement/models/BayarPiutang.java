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
public class BayarPiutang {
    @Id
    private Long id;

    @NotNull
    private Date tanggal;
    private Integer nominal;

    private String keterangan;
    private Long id_piutang;
    private Long id_pemasukan;

    @ToOne( joinProperty = "id_piutang")
    private Piutang piutangs;

    @ToOne( joinProperty = "id_pemasukan")
    private Pemasukan pemasukan;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 93587376)
private transient BayarPiutangDao myDao;

@Generated(hash = 124072794)
public BayarPiutang(Long id, @NotNull Date tanggal, Integer nominal,
        String keterangan, Long id_piutang, Long id_pemasukan) {
    this.id = id;
    this.tanggal = tanggal;
    this.nominal = nominal;
    this.keterangan = keterangan;
    this.id_piutang = id_piutang;
    this.id_pemasukan = id_pemasukan;
}

@Generated(hash = 1491436389)
public BayarPiutang() {
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

public Long getId_piutang() {
    return this.id_piutang;
}

public void setId_piutang(Long id_piutang) {
    this.id_piutang = id_piutang;
}

public Long getId_pemasukan() {
    return this.id_pemasukan;
}

public void setId_pemasukan(Long id_pemasukan) {
    this.id_pemasukan = id_pemasukan;
}

@Generated(hash = 451312817)
private transient Long piutangs__resolvedKey;

/** To-one relationship, resolved on first access. */
@Generated(hash = 1351778817)
public Piutang getPiutangs() {
    Long __key = this.id_piutang;
    if (piutangs__resolvedKey == null || !piutangs__resolvedKey.equals(__key)) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        PiutangDao targetDao = daoSession.getPiutangDao();
        Piutang piutangsNew = targetDao.load(__key);
        synchronized (this) {
            piutangs = piutangsNew;
            piutangs__resolvedKey = __key;
        }
    }
    return piutangs;
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 695478498)
public void setPiutangs(Piutang piutangs) {
    synchronized (this) {
        this.piutangs = piutangs;
        id_piutang = piutangs == null ? null : piutangs.getId();
        piutangs__resolvedKey = id_piutang;
    }
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
@Generated(hash = 204830973)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getBayarPiutangDao() : null;
}
}
