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
    private Integer jenis;

    @ToOne( joinProperty = "id_piutang")
    private Piutang piutang;

    @ToMany( referencedJoinProperty = "id_pengeluaran" )
    @OrderBy( "tanggal DESC" )
    private List<BayarHutang> bayarHutangs;

@Generated(hash = 126411035)
public Pengeluaran(Long id, @NotNull Date tanggal, Integer nominal,
        String keterangan, Long id_kategori) {
    this.id = id;
    this.tanggal = tanggal;
    this.nominal = nominal;
    this.keterangan = keterangan;
    this.id_kategori = id_kategori;
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
}
