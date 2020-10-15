# Init ORM
## Apa itu ORM?
<blockquote>
  ORM atau kepanjangannya Object Relation Maping merupakan sebuah teknik yang digunakan dalam pemrograman untuk menggunakan basisdata 
  relasional sebagai penyimpanan data dengan bentuk objek. Teknik ini biasa digunakan dalam bahasa pemrograman berorientasi objek saat 
  harus menggunakan basisdata relasional dalam penyimpanannya.
</blockquote>

## Kenapa Menggunakan ORM?

dengan menggunakan orm dapat mempersingkat waktu pembuatan aplikasi.
alasan lain...? (Coming soon...)

## Menggunakan Library

aplikasi ini menggunakan sebuah library dependensi yang bernama __Greendao__

### GreenDao

#### Implementasi

cara mengimplementasikan dependensi ini dengan:

menambahkan baris dependency pada file __build.gradle__ root:
```gradle
  buildscript {
    // ...
    dependencies {
      classpath 'org.greenrobot:greendao-gradle-plugin:3.3.0'
    }
    //...
 }
```

dan pada file __build.gradle__ app/*:
```gradle
//...
apply plugin: 'org.greenrobot.greendao'
//...


greendao {
    schemaVersion 2
}

dependencies {
  //...
  implementation 'org.greenrobot:greendao:3.3.0'
}
```

buat Class __App__ untuk mengatur sesi sambungan db Sqlite

```java
package eddleven.io.moneygement;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import eddleven.io.moneygement.models.DaoMaster;
import eddleven.io.moneygement.models.DaoSession;

public class App extends Application {
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        // regular SQLite database
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "moneygement-db");
        Database db = helper.getWritableDb();

        // encrypted SQLCipher database
        // note: you need to add SQLCipher to your dependencies, check the build.gradle file
        // DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db-encrypted");
        // Database db = helper.getEncryptedWritableDb("encryption-key");

        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}

```

tambahkan attribute name dengan value .App pada element application __AndroidManifest.xml__:

```xml

  <application
        android:name=".App"
        >
        <!-- .... -->
  </application>
```


Referensi:
1. [Pemetaan objek-relasional | Wikipedia](https://id.wikipedia.org/wiki/Pemetaan_objek-relasional)
2. [https://greenrobot.org/greendao/documentation/](https://greenrobot.org/greendao/documentation/)
