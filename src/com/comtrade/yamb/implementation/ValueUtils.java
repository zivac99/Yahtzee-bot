package com.comtrade.yamb.implementation;

import java.util.stream.IntStream;

import com.comtrade.yamb.Die;

class ValueUtils {
   private static final int KENTA_BASE = 46;
   private static final int TRILING_BASE = 20;
   private static final int FULL_BASE = 30;
   private static final int POKER_BASE = 40;
   private static final int YAMB_BASE = 50;

   static int countFor(Die[] dice, int value) {
      int numberOfOccurencies = (int) IntStream.range(0, dice.length).map(dieNo -> dice[dieNo].getValue()).filter(dieValue -> dieValue == value).count();
      return numberOfOccurencies > 5 ? 5 : numberOfOccurencies;
   }

   static int getSimpleValue(Die[] dice, int value) {
      return countFor(dice, value) * value;
   }

   static int[] getFrequencies(Die[] dice) {
      int[] frequencies = new int[Die.MAX_VALUE + 1];
      IntStream.range(0, frequencies.length).forEach(i -> frequencies[i] = 0);
      IntStream.range(0, dice.length).forEach(dieNo -> frequencies[dice[dieNo].getValue()]++);
      return frequencies;
   }

   public static int getValueMax(Die[] dice) {
      int[] frequencies = getFrequencies(dice);
      int sum = 0;

      for (int dieNo = 0; dieNo < 5; dieNo++) {
         for (int value = Die.MAX_VALUE; value >= Die.MIN_VALUE; value--) {
            if (frequencies[value] > 0) {
               sum += value;
               frequencies[value]--;
               break;
            }
         }
      }
      return sum;
   }

   public static int getValueMin(Die[] dice) {
      int[] frequencies = getFrequencies(dice);
      int sum = 0;

      for (int dieNo = 0; dieNo < 5; dieNo++) {
         for (int value = Die.MIN_VALUE; value <= Die.MAX_VALUE; value++) {
            if (frequencies[value] > 0) {
               sum += value;
               frequencies[value]--;
               break;
            }
         }
      }
      return sum;
   }

   private static int getFrequencyBonus(int frequency) {
      switch (frequency) {
         case 3:
            return TRILING_BASE;
         case 4:
            return POKER_BASE;
         case 5:
            return YAMB_BASE;
         default:
            return 0;
      }
   }

   public static int getValueFrequency(Die[] dice, int frequency) {
      int[] frequencies = getFrequencies(dice);
      for (int value = Die.MIN_VALUE; value <= Die.MAX_VALUE; value++) {
         if (frequencies[value] >= frequency) { return getFrequencyBonus(frequency) + frequency * value; }
      }
      return 0;
   }

   public static int getValueFullHouse(Die[] dice) {
      int[] frequencies = getFrequencies(dice);
      int value3 = 0;
      int value2 = 0;
      for (int value = Die.MIN_VALUE; value <= Die.MAX_VALUE; value++) {
         if (frequencies[value] >= 3) {
            frequencies[value] -= 3;
            value3 = 3 * value;
            break;
         }
      }
      for (int value = Die.MIN_VALUE; value <= Die.MAX_VALUE; value++) {
         if (frequencies[value] >= 2) {
            frequencies[value] -= 2;
            value2 = 2 * value;
            break;
         }
      }
      return (value3 > 0 && value2 > 0) ? FULL_BASE + value3 + value2 : 0;
   }

   private static boolean checkRange(int[] frequencies, int from, int to) {
      for (int value = from; value <= to; value++) {
         if (frequencies[value] < 1) { return false; }
      }
      return true;
   }

   private static boolean hasStraight(Die[] dice) {
      int[] frequencies = getFrequencies(dice);
      return checkRange(frequencies, 1, Die.MAX_VALUE - 1) || checkRange(frequencies, 2, Die.MAX_VALUE);
   }

   public static int getValueStraight(Die[] dice, int throwNumber) {
      return hasStraight(dice) ? KENTA_BASE + 10 * (3 - throwNumber) : 0;
   }
}
