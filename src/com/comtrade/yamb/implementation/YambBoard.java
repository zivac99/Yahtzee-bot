package com.comtrade.yamb.implementation;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.comtrade.yamb.Board;
import com.comtrade.yamb.Column;
import com.comtrade.yamb.ColumnType;

class YambBoard implements Board {
   private YambColumn[] columns = new YambColumn[ColumnType.values().length];

   YambBoard() {
      columns[0] = new YambColumnTopDown();
      columns[1] = new YambColumnFree();
      columns[2] = new YambColumnBottomUp();
      columns[3] = new YambColumnMiddle();
      columns[4] = new YambColumnEnd();
      columns[5] = new YambColumnNajava();
      columns[6] = new YambColumnRucna();
   }

   @Override
   public Column[] getColumns() {
      return columns;
   }

   public void normalize() {
      for (YambColumn column : columns) {
         column.normalize();
      }
   }

   @Override
   public int getScore() {
      int score = 0;
      for (YambColumn column : columns) {
         score += column.getScore();
      }
      return score;
   }

   @Override
   public String toString() {
      return Stream.of(columns).map(column -> column.toString()).collect(Collectors.joining(" / "));
   }
}
