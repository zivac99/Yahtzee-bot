package com.comtrade.yamb;

/**
 * Tipovi redova u tabeli za Yamb
 */
public enum FieldType {
   /**
    * Polje u koje se upisuje broj jedinica (najviše 5) u kolekciji kockica.<br>
    * Sadržaj ovog polja se koristi i pri popunjavanju polja SUM.
    */
   FIELD_1("1", true),
   
   /**
    * Polje u koje se upisuje broj dvojki (najviše 5) u kolekciji kockica pomnožen brojem 2.<br>
    */
   FIELD_2("2", true),
   
   /**
    * Polje u koje se upisuje broj trojki (najviše 5) u kolekciji kockica pomnožen brojem 2.<br>
    */
   FIELD_3("3", true),
   
   /**
    * Polje u koje se upisuje broj četvorki (najviše 5) u kolekciji kockica pomnožen brojem 2.<br>
    */
   FIELD_4("4", true),
   
   /**
    * Polje u koje se upisuje broj petica (najviše 5) u kolekciji kockica pomnožen brojem 2.<br>
    */
   FIELD_5("5", true),
   
   /**
    * Polje u koje se upisuje broj šestica (najviše 5) u kolekciji kockica pomnožen brojem 2.<br>
    */
   FIELD_6("6", true),
   
   /**
    * Polje u koje se upisuje zbir vrednosti polja FIELD_1, FIELD_2, FIELD_3, FIELD_4, FIELD_5 i FIELD_6.<br>
    * Ako je taj zbir veći ili jednak broju 60, onda se dobija bonus od 30 poena.
    */
   SUM("=", false),
   
   /**
    * Zbir 5 najvećih vrednosti na kockicama.<br>
    */
   MAXIMUM("+", true),
   
   /**
    * Zbir 5 najmanjih vrednosti na kockicama.<br>
    */
   MINIMUM("-", true),
   
   /**
    * Vrednost koja se dobija kao vrednost izraza FIELD_1 * (MAXIMUM-MINIMUM).<br>
    * Ako je vrednost MINIMUM-a veća od vrednosti MAXIMUM-a, onda se u ovo polje upisuje 0.
    */
   PRODUCT("M", false),
   
   /**
    * Kenta je ostvarena ako se na kockicama nalazi 5 uzastopnih brojeva (od 1 do 5, ili od 2 do 6).<br>
    * Vrednost ovog polja zavisi od rednog broja bacanja u kome je kenta ostvarena.<br>
    * Kenta posle prvog bacanja vredi 66 poena.<br>
    * Kenta posle drugog bacanja vredi 56 poena.<br>
    * Kenta posle trećeg bacanja vredi 46 poena.<br>
    */
   KENTA("Q", true),
   
   /**
    * Triling je ostvaren ako se na kockicama nalaze (barem) tri iste vrednosti.<br>
    * Vrednost trilinga je 20 poena plus trostruka vrednost tog broja koji se pojavljuje (barem) tri puta.<br>
    * Primer : na kockicama je palo 6 5 5 5 5 2, triling vredi 35 poena (20 + 3*5).<br>
    * Napomena : yamb i poker JESU triling. "Višak" odgovarajućih kockica ne umanjuje činjenicu da "postoje 3 kockice sa istom vrednošću".
    */
   TRILING("T", true),
   
   /**
    * Ful je ostvaren ako se na kockicama nalaze (barem) tri iste kockice sa jednom (istom) vrednošću i još (barem) 2 kockice sa (istom, ali potencijalno drugom) vrednošću.<br>
    * Vrednost fula je 30 poena plus zbir vrednosti na prethodno opisanih pet kockica.<br>
    * Primer : na kockicama je palo 6 5 5 5 6 2, ful vredi 57 poena (30 + 3*5 + 2*6).<br>
    * Napomena : yamb JESTE ful. Dakle : ako je na kockicama palo 4 4 4 4 4 2, onda se može upisati ful od 50 poena (30 + 3*4 + 2*4).
    */
   FULL("F", true),
   
   /**
    * Poker (ponegde ga nazivaju "kare") je ostvaren ako se na kockicama nalaze (barem) 4 iste vrednosti.<br>
    * Vrednost pokera je 40 poena plus četvorostruka vrednost tog broja koji se pojavljuje (barem) 4 puta.<br>
    * Primer : na kockicama je palo 6 5 5 5 5 2, poker vredi 60 poena (40 + 4*5).<br>
    * Napomena : yamb JESTE poker. "Višak" odgovarajućih kockica ne umanjuje činjenicu da "postoje 4 kockice sa istom vrednošću".
    */
   POKER("P", true),
   
   /**
    * Yamb je ostvaren ako se na kockicama nalazi (barem) 5 istih vrednosti.<br>
    * Vrednost yamb-a je 50 poena plus petostruka vrednost tog broja koji se pojavljuje (barem) 5 puta.<br>
    * Primer : na kockicama je palo 6 5 5 5 5 5, yamb vredi 75 poena (50 + 5*5).<br>
    */
   YAMB("Y", true),
   
   /**
    * Polje u koje se upisuje zbir vrednosti polja KENTA, TRILING, FUL, POKER i YAMB.<br>
    */
   SUBTOTAL("==", false),
   
   /**
    * Zbir kolone. Polje u koje se upisuje zbir vrednosti polja SUM, PRODUCT i SUBTOTAL.<br>
    */
   TOTAL("Σ", false);
   

   /**
    * Simbol (slovo/znak) koji označava polje u tabeli
    */
   private String symbol;
   
   /**
    * Indikator koji govori da li polje popunjava igrač.<br>
    * <code>true</code> govori da polje popunjava igrač.
    * <code>false</code> govori da se polje popunjava automatski na osnovu nekih pravila igre.
    */
   private boolean playable;

   private FieldType(String symbol, boolean playable) {
      this.symbol = symbol;
      this.playable = playable;
   }

   /**
    * Indikator koji govori da li polje popunjava igrač.<br>
    * <code>true</code> govori da polje popunjava igrač.
    * <code>false</code> govori da se polje popunjava automatski na osnovu nekih pravila igre.
    */
   public boolean isPlayable() {
      return playable;
   }
   
   /**
    * Simbol (slovo/znak) koji označava polje u tabeli
    */
   public String getSymbol() {
      return symbol;
   }
}
