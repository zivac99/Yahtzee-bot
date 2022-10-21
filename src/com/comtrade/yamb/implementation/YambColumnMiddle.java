package com.comtrade.yamb.implementation;

import com.comtrade.yamb.ColumnType;
import com.comtrade.yamb.FieldType;

class YambColumnMiddle extends YambColumn {
   protected YambColumnMiddle() {
      super(ColumnType.FROM_MIDDLEPOINT);
      
      for ( int fieldIndex=0 ; fieldIndex<getFields().size() ; fieldIndex++ ) {
         ((YambField) (getFields().get(fieldIndex))).setPlayable(false);
      }
      ((YambField) (getFields().get(FieldType.MAXIMUM.ordinal()))).setPlayable(true);
      ((YambField) (getFields().get(FieldType.MINIMUM.ordinal()))).setPlayable(true);
   }
   
   @Override
   public void normalize() {
      super.normalize();
      for ( int fieldIndex=FieldType.MINIMUM.ordinal() ; fieldIndex<=FieldType.YAMB.ordinal() ; fieldIndex++ ) {
         YambField field = ((YambField) (getFields().get(fieldIndex)));
         if ( field.isPlayed() ) {
            field.setPlayable(false);
            continue;
         }
         if ( field.getFieldType().isPlayable() ) {
            ((YambField) (getFields().get(fieldIndex))).setPlayable(true);
            break;
         }
      }
      
      for ( int fieldIndex=FieldType.MAXIMUM.ordinal() ; fieldIndex>=FieldType.FIELD_1.ordinal() ; fieldIndex-- ) {
         YambField field = ((YambField) (getFields().get(fieldIndex)));
         if ( field.isPlayed() ) {
            field.setPlayable(false);
            continue;
         }
         if ( field.getFieldType().isPlayable() ) {
            ((YambField) (getFields().get(fieldIndex))).setPlayable(true);
            break;
         }
      }
   }
}
