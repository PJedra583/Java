import java.util.*;

public class BookingTest {

    // cena pokoi (o podanym typie, nie licząc śniadania) z koszyka
    static int cena(Koszyk k, String typ) {
        int kwota = 0;
        for (Pokoj p: k.koszyk) {
            if (p.getTyp().equals(typ)) {
                kwota += Cennik.getCena(p.getClass().getName(), p.getTyp()) * p.getCzas();
            }
        }
        return kwota;
    }

    public static void main(String[] args) {

        // cennik
        Cennik cennik = Cennik.pobierzCennik();

        // dodawanie nowych cen do cennika
        cennik.dodaj("jedynka", "standard", 100, 15);	// pokój jednoosobowy typu standardowego kosztuje 100 zł/noc, śniadanie (opcja) kosztuje 15 zł

        cennik.dodaj("dwojka", "budzet", 140, 20);	// pokój dwuosobowy typu budżetowego kosztuje 140 zł/noc, śniadanie (opcja) kosztuje 20 zł

        cennik.dodaj("trojka", "standard", 300, 25);	// pokój trzyosobowy typu standardowego kosztuje 300 zł/noc, śniadanie (opcja) kosztuje 25 zł

        cennik.dodaj("rodzina", "premium", 500, 0);	// pokój rodzinny typu premium kosztuje 500 zł/noc, śniadanie (opcja) kosztuje 0 zł

        // Klient ocean deklaruje kwotę 2300 zł na rezerwacje
        Klient ocean = new Klient("ocean", 2300);

        // Klient ocean dodaje do listy życzeń różne rodzaje pokoi:
        // 4 noce w pokoju jednoosobowym typu standardowego ze śniadaniem, 5 nocy w pokoju trzyosobowym typu standardowego bez śniadania,
        // 3 noce w pokoju dwuosobowym typu premium bez śniadania, 3 noce w pokoju dwuosobowym budżetowym bez śniadania
        ocean.dodaj(new Jedynka("standard", 4, true));
        ocean.dodaj(new Trojka("standard", 5, false));
        ocean.dodaj(new Dwojka("premium", 3, false));
        ocean.dodaj(new Dwojka("budzet", 2, false));

        // Lista życzeń klienta ocean
        ListaZyczen listaOceanu = ocean.pobierzListeZyczen();

        System.out.println("Lista życzeń klienta " + listaOceanu);

        // Przed płaceniem, klient przepakuje pokoje z listy życzeń do koszyka.
        // Możliwe, że na liście życzeń są pokoje niemające ceny w cenniku,
        // w takim przypadku zostałyby usunięte z koszyka (ale nie z listy życzeń)
        Koszyk koszykOceanu = new Koszyk(ocean);
        ocean.przepakuj(koszykOceanu);

        // Co jest na liście życzeń klienta ocean
        System.out.println("Po przepakowaniu, lista życzeń klienta " + ocean.pobierzListeZyczen());

        // Co jest w koszyku klienta ocean
        System.out.println("Po przepakowaniu, koszyk klienta " + koszykOceanu);

        // Ile wynosi cena wszystkich pokoi typu standardowego w koszyku klienta ocean
        System.out.println("Pokoje standardowe w koszyku klienta ocean kosztowały:  "
                + cena(koszykOceanu, "standard"));

        // Klient zapłaci...
        ocean.zaplac("karta");	// płaci kartą płatniczą, bez prowizji

        // Ile klientowi ocean zostało pieniędzy?
        System.out.println("Po zapłaceniu, klientowi ocean zostało: " + ocean.pobierzPortfel() + " zł");

        // Mogło klientowi zabraknąć srodków, wtedy pokoje są odkładane,
        // w innym przypadku koszyk jest pusty po zapłaceniu
        System.out.println("Po zapłaceniu, koszyk klienta " + ocean.pobierzKoszyk());
        System.out.println("Po zapłaceniu, koszyk klienta " + koszykOceanu);

        // Teraz przychodzi klient morze,
        // deklaruje 780 zł na rezerwacje
        Klient morze = new Klient("morze", 780);

        // Zarezerwował za dużo jak na tę kwotę
        morze.dodaj(new Jedynka("standard", 3, false));
        morze.dodaj(new Dwojka("budzet", 4, false));

        // Co klient morze ma na swojej liście życzeń
        System.out.println("Lista życzeń klienta " + morze.pobierzListeZyczen());

        // Przepakowanie z listy życzeń do koszyka,
        // może się okazać, że na liście życzeń są pokoje niemające ceny w cenniku,
        // w takim razie zostałyby usunięte z koszyka (ale nie z listy życzeń)
        Koszyk koszykMorza = new Koszyk(morze);
        morze.przepakuj(koszykMorza);

        // Co jest na liście życzeń morze
        System.out.println("Po przepakowaniu, lista życzeń klienta " + morze.pobierzListeZyczen());

        // A co jest w koszyku klienta morze
        System.out.println("Po przepakowaniu, koszyk klienta " + morze.pobierzKoszyk());

        // klient morze płaci
        morze.zaplac("przelew");	// płaci przelewem, prowizja 5 PLN

        // Ile klientowi morze zostało pieniędzy?
        System.out.println("Po zapłaceniu, klientowi morze zostało: " + morze.pobierzPortfel() + " zł");

        // Co zostało w koszyku klienta morze (za mało pieniędzy miał)
        System.out.println("Po zapłaceniu, koszyk klienta " + koszykMorza);

        //      W   Ł   A   S   N   A           I   M   P   L   E   M   E   N   A   C    J   A      \\
//====================================================================================================================\\
        Klient rzeka = new Klient("rzeka",60);
        cennik.dodaj("jedynka", "biedny", 20, 10);
        rzeka.dodaj(new Jedynka("biedny",3,true));
        ListaZyczen listaZyczenrzeki = rzeka.pobierzListeZyczen();
        System.out.println("Lista życzeń klienta " + listaZyczenrzeki);
        Koszyk koszykrzeki = new Koszyk(rzeka);
        rzeka.przepakuj(koszykrzeki);
        System.out.println("Po przepakowaniu lista życzeń klienta " + listaZyczenrzeki);
        System.out.println("Po przepakowaniu koszyk klienta " + koszykrzeki);
        rzeka.zaplac("przelew");
        System.out.println("Po zaplaceniu klientowi rzeka zostało: " + rzeka.pobierzPortfel());
        System.out.println("Po zaplaceniu lista koszyk klienta " + koszykrzeki);

    }
}
//      C   E   N   N   I   K       \\
//====================================================================================================================\\

class Cennik {
    private static Cennik instance = null;
    private Set<Pozycja> ceny = new HashSet<>();
    private Cennik(){}
    public static Cennik pobierzCennik(){
        if (instance == null) {
            instance = new Cennik();
        }
        return instance;
    }

    public void dodaj (String nazwa,String typ, int cena, int sniadanie) {
        ceny.add(new Pozycja(nazwa,typ,cena,sniadanie));
    }

    // Porownujemy Pozycje z Pokojem aby znalezc cene w secie
    public static int getCena(String nazwa,String typ) {
        for (Pozycja p: instance.ceny) {
            if (nazwa.toLowerCase().equals(p.nazwa)) {
                if (typ.toLowerCase().equals(p.typ)) {
                    return p.cena;
                }
            }
        }
        // Jesli pokoju nie ma na liscie zwracamy -1
        return -1;
    }
    public static int getCenaSniadania(String nazwa,String typ,Boolean sniadanie) {

        // Porownujemy Pozycje z Pokojem aby znalezc cene sniadania w secie
        if (sniadanie) {
            for (Pozycja p : instance.ceny) {
                if (nazwa.toLowerCase().equals(p.nazwa)) {
                    if (typ.toLowerCase().equals(p.typ)) {
                        return p.sniadanie;
                    }
                }
            }
        }
        return 0;
    }

    //Pozycja w cenniku
    public class Pozycja {
        String nazwa;
        String typ;
        int cena;
        int sniadanie;
        public Pozycja(String nazwa, String typ, int cena, int sniadanie) {
            this.nazwa = nazwa;
            this.typ = typ;
            this.cena = cena;
            this.sniadanie = sniadanie;
        }
        @Override
        public String toString() {
            return "Pokój " + this.typ +" "+ this.nazwa + " w cenie: " + this.cena + "zl, ze sniadaniem:" + this.sniadanie;
        }
        //W ten sposob porownujemy czy Pozycja jest juz w cenniku
        @Override
        public boolean equals(Object o) {
            if (o instanceof Pozycja) {
                // Zakladamy ze nie mozna dodac takich samych pokoji o roznych cenach
                if ( this.nazwa.equals(((Pozycja) o).nazwa) && this.typ.equals(((Pozycja) o).typ) ) {
                    return true;
                }
            }
            return false;
        }
    }
}


//      L   I   S   T   A       Ż   Y   C   Z   E   Ń      \\
//====================================================================================================================\\

class ListaZyczen {
    List<Pokoj> lista;
    public ListaZyczen() {
        this.lista = new ArrayList<Pokoj>();
    }

    @Override
    public String toString() {
        if (lista.isEmpty()) {return "-- Pusto --";}
        return "\n" + lista.toString() + "\n";
    }
}

//      K   O   S   Z   Y   K       \\
//====================================================================================================================\\

class Koszyk {
    Klient klient;
    List<Pokoj> koszyk;

    public Koszyk (Klient klient) {
        this.klient = klient;
        this.koszyk = new ArrayList<>();
        this.klient.setKoszyk_klienta(this);
    }
    @Override
    public String toString() {
        if (koszyk.isEmpty()) {return "-- Pusto --";}
        return "\n" + koszyk.toString() + "\n";
    }
}

//      K   L   I   E   N   T      \\
//====================================================================================================================\\

class Klient {
    private String identyfikator;
    private int do_wydania;
    private ListaZyczen listaZyczen;
    private Koszyk koszyk_klienta;
    public Klient (String identyfikator, int do_wydania ) {
        this.listaZyczen = new ListaZyczen();
        this.identyfikator = identyfikator;
        this.do_wydania = do_wydania;
        //Koszyk jest ustalany w konstruktorze Koszyk
        koszyk_klienta = null;
    }

    public void setKoszyk_klienta(Koszyk koszyk_klienta) {
        this.koszyk_klienta = koszyk_klienta;
    }

    public void dodaj (Pokoj pokoj) {
        listaZyczen.lista.add(pokoj);
    }
    public ListaZyczen pobierzListeZyczen() {
        return listaZyczen;
    }
    public int pobierzPortfel() {
        return do_wydania;
    }
    public Koszyk pobierzKoszyk() {
        return koszyk_klienta;
    }
    public void przepakuj(Koszyk koszyk2) {
        for (Pokoj p:listaZyczen.lista) {
            // Jeśli Pozycja znajduje sie w cenniku
            if (Cennik.getCena(p.getClass().getName(),p.getTyp()) > 0 ) {
                koszyk2.koszyk.add(p);
            }
        }
        //Usuwanie elementow koszyka z listy
        for (Pokoj p:koszyk2.koszyk) {
            listaZyczen.lista.remove(p);
        }
    }

    public void zaplac(String karta) {
        int budzet = do_wydania;
        boolean Zrealizowano = false;
        List<Pokoj> temp = new ArrayList<>();
        if (!karta.equals("karta")) {
            budzet -= 5;
        }
        //ConcurentModificationException podczas usuwania elementow listy w petli
        for (Pokoj p : koszyk_klienta.koszyk) {
            int cena_sniadania = 0;
            if (budzet > (Cennik.getCena(p.getClass().getName(), p.getTyp()) * p.getCzas()) + Cennik.getCenaSniadania(p.getClass().getName(),p.getTyp(),p.getSniadania())) {
                budzet -= Cennik.getCena(p.getClass().getName(), p.getTyp()) * p.getCzas() + (Cennik.getCenaSniadania(p.getClass().getName(),p.getTyp(),p.getSniadania())* p.getCzas());
                temp.add(p);
                Zrealizowano = true;
            } else {
                int max_iterator = p.getCzas();
                for (int i = 0; i < max_iterator; i++) {
                    if (budzet > Cennik.getCena(p.getClass().getName(),p.getTyp())+Cennik.getCenaSniadania(p.getClass().getName(),p.getTyp(),p.getSniadania())) {
                        p.setCzas((p.getCzas()-1));
                        budzet -= Cennik.getCena(p.getClass().getName(),p.getTyp())+Cennik.getCenaSniadania(p.getClass().getName(),p.getTyp(),p.getSniadania());
                        Zrealizowano = true;
                    }
                }
            }
        }
        for (Pokoj p:temp) {
            koszyk_klienta.koszyk.remove(p);
        }
        // Zwrócenie Opłaty w przypadku niezrealizowania transakcji
        if (!Zrealizowano) {
            if (!karta.equals("karta")) {
                budzet += 5;
            }
        }
        do_wydania = budzet;
    }
}

//       P   O   K   Ó   J       \\
//====================================================================================================================\\

abstract class Pokoj {
    //Klasa abstrakcyjna
    public String getTyp() {
        return this.typ;
    }

    public int getCzas() {
        return this.czas;
    }

    public boolean getSniadania() {
        return this.sniadania;
    }

    public void setCzas(int i) {
        this.czas = i;
    }
    private String typ;
    private int czas;

    private boolean sniadania;
    protected Pokoj (String typ, int czas, boolean sniadania) {
        this.czas = czas;
        this.typ = typ;
        this.sniadania = sniadania;
    }
    @Override
    public String toString() {
        String s = "nie";
        if (this.sniadania) {
            s = "tak";
        }
        return "{{{"+getClass().getName() + ", Typu: " + this.typ + " Ile: " + this.czas + " noce, Sniadania: " + s + " Cena: " + Cennik.getCena(getClass().getName(),getTyp()) +"}}}";
    }

}
//Klasy Dziedziczace
class Jedynka extends Pokoj {
    //private String name;
    public Jedynka (String typ, int czas, boolean sniadania) {
        super(typ,czas,sniadania);
        // this.name = this.getClass().getName();
    }

}
class Dwojka extends Pokoj {
    public Dwojka (String typ, int czas, boolean sniadania) {
        super(typ,czas,sniadania);
    }
}
class Trojka extends Pokoj {
    public Trojka (String typ, int czas, boolean sniadania) {
        super(typ,czas,sniadania);
    }
}
