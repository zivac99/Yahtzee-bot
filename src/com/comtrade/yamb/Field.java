package com.comtrade.yamb;

/**
 * Polje u tabeli za Yamb
 */
public interface Field {
   /**
    * @return Da li je polje u koloni tipa NAJAVA
    */
   boolean isUKoloniNajava();
   
   /**
    * @return Da li je polje u koloni tipa RUCNA
    */
   boolean isUKoloniRucna();

   /**
    * @return Indeks kolone (u tabeli) kojoj polje pripada
    */
   int getColumnIndex();

   /**
    * @return Indeks reda (u tabeli) kome polje pripada
    */
   int getFieldIndex();

   /**
    * @return Da li je polje odigrano
    */
   boolean isPlayed();

   /**
    * @return Da li je u polje (po pravilima igre) može da se upiše vrednost
    */
   boolean isPlayable();

   /**
    * @return Vrednost u polju
    */
   int getValue();

   /**
    * @return Tip kolone kojoj polje pripada
    */
   ColumnType getColumnType();

   /**
    * @return Tip reda kome polje pripada
    */
   FieldType getFieldType();
}
