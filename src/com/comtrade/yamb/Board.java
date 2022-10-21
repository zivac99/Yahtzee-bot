package com.comtrade.yamb;

/**
 * Tabela za Yamb
 */
public interface Board {
   /**
    * @return Niz kolona u tabeli
    */
   Column[] getColumns();

   /**
    * @return Ukupan rezultat partije
    */
   int getScore();
}
