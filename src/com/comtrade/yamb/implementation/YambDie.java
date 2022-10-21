package com.comtrade.yamb.implementation;

import com.comtrade.yamb.Die;

class YambDie implements Die {
   private int value;
   private boolean fixed;

   @Override
   public int getValue() {
      return value;
   }

   public void setValue(int value) {
      this.value = value;
   }

   @Override
   public boolean isFixed() {
      return fixed;
   }

   @Override
   public void setFixed(boolean fixed) {
      this.fixed = fixed;
   }
}
