package eddleven.io.moneygement.models;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;

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
}
