1. EntityManager managed olabilmesi için, find, getReference, persist, merge olması lazım.
2. Factoryden farklı olarak equals hashcode üzerinden değil referans üzerinden işlem yapıyor.
3. Eğer referansları farklı ise nesne aynı olsada managed olmaz. Yani direk operation yapamazsın.
4. Managed olmayan entityler silinemez, güncellenemez.








* Eclipselink entityId sıfırdan farklı olursa persist ediyor ama Hibernate etmiyor.