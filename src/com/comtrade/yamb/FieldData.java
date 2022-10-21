package com.comtrade.yamb;

/**
 * Koordinate polja (u tabeli).<br>
 * Ovaj podatak se koristi kad god Organizator igre i Igrač treba da razmene informacije (naredbe) u vezi sa nekim poljem.
 * Primeri :<br> 
 * Koordinate polja u koje Igrač želi da se upiše vrednost.
 * Koordinate polja koje Igrač želi da najavi.
 * Koordinate polja koje je Igrač najavio u prvom bacanju (pa ga Organizator igre podseća na to).
 */
public class FieldData {
   /**
    * Indeks kolone u kojoj se polje nalazi
    */
   private int columnIndex;
   
   /**
    * Indeks reda u kome se polje nalazi
    */
   private int fieldIndex;
   
   public FieldData(int columnIndex, int fieldIndex) {
      super();
      this.columnIndex = columnIndex;
      this.fieldIndex = fieldIndex;
   }

   /**
    * Indeks kolone u kojoj se polje nalazi
    */
   public int getColumnIndex() {
      return columnIndex;
   }

   /**
    * Indeks reda u kome se polje nalazi
    */
   public int getFieldIndex() {
      return fieldIndex;
   }
   
   @Override
   public String toString() {
      return "[" + columnIndex + ", " + fieldIndex + "]";
   }
}
