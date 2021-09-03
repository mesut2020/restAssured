

public class Task {

    /*
    tek bir değişken almak için
    path veya jsonPath kullanılabilir

    tüm veriye ihtiyacın varsa .as(Genel.class)
    kullanılacak

    verinin içinden bir bölümü bir clasa atmak
    istersen jsonPath kullanılacak
    */
/*
    https://www.google.com/search?  -> olan şey ne ?  google ın search metodu çalışıyor
    parametreleri ise aşağıdaki gibi
    q=java
&sxsrf=AOaemvJDGBbXxUwEtoeBJL_dge5ay3kyqQ%3A1630517150695
&source=hp
&ei=nrcvYdS3J_aTjLsPgoKOCA
&iflsig=ALs-wAMAAAAAYS_FrmGn9WQCUKiCxsHtOzCS0he2-6fP&oq=
            &gs_lcp=Cgdnd3Mtd2l6EAMyBAgjECcyBA


    public list<WebSayfa> search(String q, String sxsrf, String source)
    {

    }


    main()
    {
        search("java","ssdsdsdsd","sdsdsdsds");
    }

    bu metodu webden naıl çağırırım 1.yöntem
    search?q=java&sxsrf=wwewewe&source=dfdfdfd

2.yöntem
    search/java/wwewewe/dfdfdfd

    RestAssured diyor ki
    API kullancağın zaman parametreleri metoda gönderirken
1.Metod için  param ifadesini kullan
2.Metod için pathParam ı kullan

    ben de linki ona göre düzenliyip öyle çağırayım
*/

}
