# BootCamp-Week1-Project
Bu repoda "https://api.minerstat.com/v2/coins" servisinden kripto para bilgilerinin çekilmesi ile ilgili çalışmalar yer almaktadır.

## Gerekli Bağımlılıklar
* Ekran geçişleri için (Navigation)
```
    def nav_version = "2.4.2"
    implementation "androidx.navigation:navigation-fragment:$nav_version"
    implementation "androidx.navigation:navigation-ui:$nav_version"
```


* Login için Firebase üzerinden Email, Google ve Facebook seçenekleri
```
  implementation platform('com.google.firebase:firebase-bom:30.0.1')
  implementation 'com.google.firebase:firebase-auth'
  implementation 'com.google.android.gms:play-services-auth:20.2.0'
  implementation 'com.google.firebase:firebase-auth:21.0.5'
  implementation 'com.facebook.android:facebook-android-sdk:latest.release'
```


* Servisten verilerin çekilebilmesi için (Retrofit)
```
    implementation 'com.squareup.retrofit2:retrofit:2.3.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.3.0'
    implementation 'com.squareup.okhttp:okhttp:2.7.2'
```

## Navigation Görünümü
![nav_gorsel](https://user-images.githubusercontent.com/30652453/173502322-a0a747f5-9f98-43d6-8e2a-664e6923ae64.png)



## Çalışma anında HomeFragment
![homefragmentlive](https://user-images.githubusercontent.com/30652453/173502343-36183fe6-c93c-4b33-8e16-ed40a251d1e1.gif)
