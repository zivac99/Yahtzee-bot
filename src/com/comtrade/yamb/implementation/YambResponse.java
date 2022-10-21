package com.comtrade.yamb.implementation;

import com.comtrade.yamb.FieldData;
import com.comtrade.yamb.Response;

class YambResponse implements Response {
   private boolean moveOver = false;
   private boolean[] dice;
   private FieldData fieldData;

   public YambResponse() {}
   
   public YambResponse(Response response) {
      this();
      if ( response!=null ) {
         this.moveOver = response.isMoveOver();
         this.dice = response.getDice();
         this.fieldData = response.getFieldData();
      }
   }
   
   @Override
   public boolean isMoveOver() {
      return moveOver;
   }

   public void setMoveOver(boolean moveOver) {
      this.moveOver = moveOver;
   }

   @Override
   public boolean[] getDice() {
      return dice;
   }

   public void setDice(boolean[] dice) {
      this.dice = dice;
   }

   @Override
   public FieldData getFieldData() {
      return fieldData;
   }

   public void setFieldData(FieldData fieldData) {
      this.fieldData = fieldData;
   }
}
