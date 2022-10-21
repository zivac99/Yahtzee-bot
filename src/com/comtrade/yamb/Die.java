package com.comtrade.yamb;

/**
 * Kocka
 */
public interface Die {
   /**
    * Minimalna vrednost kocke
    */
   static final int MIN_VALUE = 1;
   
   /**
    * Maksimalna vrednost kocke
    */
   static final int MAX_VALUE = 6;

   /**
    * @return Vrednost kockice
    */
   int getValue();

   /**
    * @return Indikator da li je vrednost kockice fiksirana
    */
   boolean isFixed();

   /**
    * Fiksira (ili defiksira) vrednost kocke
    * @param fixed <br><code>true</code>, ako vrednost treba da se fiksira<br><code>false</code>, ako vrednost može da se menja
    */
   void setFixed(boolean fixed);
}
