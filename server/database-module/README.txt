AÇIK:
* Food için FoodCategory null olamaz. Validation için düşün.
* Entity loaded neden iki defa çalışıyor? Merge yaparken tekrar mı sorgu atıyor?
* Entity persiste ettiğimizde 2. cahce koymuyor. hemen bir find yaptıktan sonra koyuyor.
* querry cache. JPQL ve SQL ile yapılan querryler cahclenmiyor. Select atıyor cahce hit yapmıyor ama write yapıyor.
* listenerı çıkardım sonra ekle
* local jms producer ejbden alınıp cdi'a taşınacak
* jms mesajlarını setproperty ile crud yaptırabilirz?
* notnull db değil beanvalidation da test edilecek. Food ve Category bitti. diğerlerine bak

* servicte foodcategory validate ediliyor. aynı zamanda listede food varsa foodlar validate edilecek. intercepter
kullanılabilir.