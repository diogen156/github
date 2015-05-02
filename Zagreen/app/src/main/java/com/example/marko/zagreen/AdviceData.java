package com.example.marko.zagreen;

/**
 * Klasa koja sadrži podatke za klasu FragmentAdvices, prikazani su u expandable listi.
 *
 * @author Collude
 * @version 2015.0502
 * @since 1.0
 */
public class AdviceData {

    //naslovi expandable liste
    private String[] groupsData = {"Jeste li znali?",
            "Zašto trebam reciklirati papir?",
            "Zašto trebam reciklirati plastiku?",
            "Zašto trebam reciklirati staklo?",
            "Zašto trebam reciklirati metal?",
            "Korisni savjeti"};
    
    // sadržaj expandable liste
    private String[][] childrenData = {
            {"•\t60% otpada koji završi u kanti može se reciklirati\n" +
                    "•\t25% otpada čine papir i karton\n" +
                    "•\t8% kućnog otpada čini plastika\n" +
                    "•\t8% kućnog otpada čini staklo\n" +
                    "•\t2% kućnog otpada čini metal\n" +
                    "•\tIskoristivost stakla kod recikliranja je 100%\n" +
                    "•\tSvake godine baci se količina plastike dovoljna da 4 puta okruži Zemlju\n" +
                    "•\tU Pacifiku postoji otok smeća sedamdeset puta veći od Hrvatske\n" +
                    "•\tProsječnja godišnja potrošnja plastičnih vrečica u Hrvatskoj je 150 vrećica po osobi\n" +
                    "•\tSlavina iz koje kaplje može potrošiti i do 5000 litara vode godišnje\n" +
                    "•\tMotorno ulje može se reciklirati,rerafinirati i ponovo iskoristit\n"},

            {"•\tRecikliranjem papira smanjuje se potreba za sječom šuma\n" +
                    "•\tPotrebna su 24 stabla za proizvodnju jedne tone novina\n"},

            {"•\t1 plastična boca uštedi količinu energije dovoljne da žarulja od 60W gori 3 sata\n" +
                    "•\tSamo oko 27% svih plastičnih boca završi na reciklaži\n" +
                    "•\tPlastične vrećice proizvode se od polietilena kojemu treba gotovo 1000 godina da se razgradi\n" +
                    "•\tZa tonu reciklirane plastike uštedimo energije koliko potroše dvije osobe u godinu dana\n"},

            {"•\tRecikliranjem 1 tone stakla emitira se 315 kg CO2 manje u atmosferu\n" +
                    "•\t1 reciklirana staklena boca uštedi količinu energije dovoljne da 25 minuta napaja računalo\n" +
                    "•\tModernoj staklenoj boci treba nekoliko tisuću godina da se razgradi\n"},

            {"•\tNova limenka je na polici za 60-tak dana napravljena od recikliranog materijala\n" +
                    "•\t1 reciklirana limenka uštedi količinu energije dovoljne za 3 sata gledanja televizije\n"},

            {"•\tPrije odlaganja tekstilnog otpada provjerite da li je tekstil nekome još uvijek upotrebljiv\n" +
                    "•\tPrije odlaganja staklene i plastične ambalaže poželjno je oprati ambalažu i skinuti poklopce\n"}
    };

    /**
     * Dohvaća podatke za sadržaj expandable liste
     * @return childrenData
     */
    public String[][] getChildrenData() {
        return childrenData;
    }


    /**
     * Dohvaća podatke za naslove expandable liste
     * @return groupsData
     */
    public String[] getGroupsData() {
        return groupsData;
    }
}
