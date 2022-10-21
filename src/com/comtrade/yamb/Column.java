package com.comtrade.yamb;

import java.util.List;

/**
 * Kolona na tabeli za Yamb
 */
public interface Column {
   /**
    * @return Niz polja na tabeli
    */
   List<Field> getFields();

   /**
    * @return Zbir kolone
    */
   int getScore();

   /**
    * @return Tip kolone
    */
   ColumnType getType();
}
