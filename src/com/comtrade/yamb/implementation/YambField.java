package com.comtrade.yamb.implementation;

import com.comtrade.yamb.ColumnType;
import com.comtrade.yamb.Die;
import com.comtrade.yamb.Field;
import com.comtrade.yamb.FieldType;

class YambField implements Field {
   private ColumnType columnType;
   private FieldType fieldType;
   private boolean played = false;
   private boolean playable = true;
   private int value = 0;

   public YambField(ColumnType columnType, FieldType fieldtype) {
      super();
      this.columnType = columnType;
      this.fieldType = fieldtype;
   }

   @Override
   public int getColumnIndex() {
      return this.columnType.ordinal();
   }
   
   @Override
   public int getFieldIndex() {
      return this.fieldType.ordinal();
   }

   @Override
   public boolean isPlayed() {
      return played;
   }

   public void setPlayable(boolean playable) {
      this.playable = playable;
   }
   
   @Override
   public boolean isPlayable() {
      return fieldType.isPlayable() && playable && !played;
   }

   @Override
   public int getValue() {
      return value;
   }

   @Override
   public ColumnType getColumnType() {
      return columnType;
   }

   @Override
   public FieldType getFieldType() {
      return fieldType;
   }

   public void setValue(int value) {
      this.value = value;
      played = true;
   }

   void setValue(Die[] dice, int throwNumber) {
      int value = 0;
      switch (fieldType) {
         case FIELD_1:
            value = ValueUtils.getSimpleValue(dice, 1);
            break;
         case FIELD_2:
            value = ValueUtils.getSimpleValue(dice, 2);
            break;
         case FIELD_3:
            value = ValueUtils.getSimpleValue(dice, 3);
            break;
         case FIELD_4:
            value = ValueUtils.getSimpleValue(dice, 4);
            break;
         case FIELD_5:
            value = ValueUtils.getSimpleValue(dice, 5);
            break;
         case FIELD_6:
            value = ValueUtils.getSimpleValue(dice, 6);
            break;
         case MAXIMUM:
            value = ValueUtils.getValueMax(dice);
            break;
         case MINIMUM:
            value = ValueUtils.getValueMin(dice);
            break;
         case KENTA:
            value = ValueUtils.getValueStraight(dice, throwNumber);
            break;
         case TRILING:
            value = ValueUtils.getValueFrequency(dice, 3);
            break;
         case FULL:
            value = ValueUtils.getValueFullHouse(dice);
            break;
         case POKER:
            value = ValueUtils.getValueFrequency(dice, 4);
            break;
         case YAMB:
            value = ValueUtils.getValueFrequency(dice, 5);
            break;
         default:
            break;
      }
      setValue(value);
   }
   
   @Override
   public boolean isUKoloniNajava() {
      return columnType.equals(ColumnType.NAJAVA);
   }
   
   @Override
   public boolean isUKoloniRucna() {
      return columnType.equals(ColumnType.RUCNA);
   }
   
   @Override
   public String toString() {
      return "[" + getColumnIndex() + ", " + getFieldIndex() + "]";
   }
}
