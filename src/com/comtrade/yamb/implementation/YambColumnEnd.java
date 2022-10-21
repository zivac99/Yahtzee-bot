package com.comtrade.yamb.implementation;

import com.comtrade.yamb.ColumnType;
import com.comtrade.yamb.FieldType;

class YambColumnEnd extends YambColumn {
   protected YambColumnEnd() {
      super(ColumnType.FROM_ENDPOINT);
      
      for ( int fieldIndex=0 ; fieldIndex<getFields().size() ; fieldIndex++ ) {
         ((YambField) (getFields().get(fieldIndex))).setPlayable(false);
      }
      ((YambField) (getFields().get(0))).setPlayable(true);
      ((YambField) (getFields().get(getFields().size()-1))).setPlayable(true);
   }
   
   @Override
   public void normalize() {
      super.normalize();
      for ( int fieldIndex=FieldType.FIELD_1.ordinal() ; fieldIndex<=FieldType.MAXIMUM.ordinal() ; fieldIndex++ ) {
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
      
      for ( int fieldIndex=FieldType.YAMB.ordinal() ; fieldIndex>=FieldType.MINIMUM.ordinal() ; fieldIndex-- ) {
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
