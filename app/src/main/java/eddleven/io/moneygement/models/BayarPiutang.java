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
}
