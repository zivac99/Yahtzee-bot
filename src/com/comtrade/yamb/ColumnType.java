package com.comtrade.yamb;

/**
 * Tipovi kolona u tabeli za Yamb
 */
public enum ColumnType {
   /**
    * Kolona u kojoj se polja popunjavaju odozgo nadole
    */
   TOP_DOWN("↓"),
   
   /**
    * Kolona u kojoj se polja popunjavaju slobodno, bez ikakvog pravila
    */
   FREE("⇅"),
   
   /**
    * Kolona u kojoj se polja popunjavaju odozdo nagore
    */
   BOTTOM_UP("↑"),
   
   /**
    * Kolona u kojoj se polja popunjavaju od sredine ka "krajevima" : od maksimuma nagore, a od minimuma nadole
    */
   FROM_MIDDLEPOINT("↕"),
   
   /**
    * Kolona u kojoj se polja popunjavaju od "krajeva" ka sredini : od dna nagore (zaključno sa minimumom), od vrha nadole (zaključno sa maksimumom)
    */
   FROM_ENDPOINT("↥↧"),
   
   /**
    * Kolona u kojoj se polja popunjavaju slobodno, ali popunjavanje mora biti "najavljeno" posle prvog bacanja
    */
   NAJAVA("N"),
   
   /**
    * Kolona u kojoj se polja popunjavaju slobodno, ali se upis vrši (isključivo) posle prvog bacanja
    */
   RUCNA("R");
   
   /**
    * Simbol (slovo/znak) koji označava kolonu u tabeli
    */
   private String symbol;
   
   private ColumnType(String symbol) {
      this.symbol = symbol;
   }
   
   public String getSymbol() {
      return symbol;
   }
}
