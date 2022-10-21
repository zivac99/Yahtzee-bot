package com.comtrade.yamb.implementation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class RandomNumbersGenerator {
   private static int[] numbers;
   private static int randomNumberIndex = 0;

   static {
      try {
         FileReader fileReader = new FileReader(new File("randomNumbers.txt"));
         BufferedReader reader = new BufferedReader(fileReader);
         String content = reader.readLine();
         reader.close();
         fileReader.close();
         numbers = new int[content.length()];
         for (int i = 0; i < numbers.length; i++) {
            numbers[i] = Integer.valueOf(content.charAt(i));
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static void setRandomNumbersData(int gameNumber, int moveNumber) {
      randomNumberIndex = (gameNumber-1) * 2000 + (moveNumber-1) * 20;
   }

   public static int getNext() {
      return numbers[(randomNumberIndex++) % numbers.length];
   }
}
