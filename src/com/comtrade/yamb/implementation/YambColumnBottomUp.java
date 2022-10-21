package com.comtrade.yamb.implementation;

import com.comtrade.yamb.ColumnType;
import com.comtrade.yamb.FieldType;

class YambColumnBottomUp extends YambColumn {
   protected YambColumnBottomUp() {
      super(ColumnType.BOTTOM_UP);
      
      for ( int fieldIndex=0 ; fieldIndex<getFields().size() ; fieldIndex++ ) {
         ((YambField) (getFields().get(fieldIndex))).setPlayable(false);
      }
      ((YambField) (getFields().get(FieldType.YAMB.ordinal()))).setPlayable(true);
   }
   
   @Override
   public void normalize() {
      super.normalize();
      for ( int fieldIndex=FieldType.YAMB.ordinal() ; fieldIndex>=FieldType.FIELD_1.ordinal() ; fieldIndex-- ) {
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
