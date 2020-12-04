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
}
