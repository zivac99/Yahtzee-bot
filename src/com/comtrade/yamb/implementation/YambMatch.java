package com.comtrade.yamb.implementation;

import java.io.IOException;

import com.comtrade.yamb.Constants;
import com.comtrade.yamb.RequestType;
import com.comtrade.yamb.player.Player;

public class YambMatch {
   private static final int REPORT_FREQUENCY = 50;

   private Player player;
   private int gameNumber;

   public YambMatch(Player player) throws IOException {
      this.player = player;
   }

   public void play() throws Exception {
      int bestGameIndex = 0;
      long bestGameScore = 0;
      
      player.play(new YambRequest(RequestType.MATCH_START));
      long start = System.currentTimeMillis();
      long points = 0;
      for (gameNumber = 1; gameNumber <= Constants.GAMES_COUNT; gameNumber++) {
         YambGame game = new YambGame(player, gameNumber);
         
         long gameScore = game.play();
         if ( bestGameScore < gameScore ) {
            bestGameScore = gameScore;
            bestGameIndex = gameNumber;
         }
         points += gameScore;
         
         if (gameNumber % REPORT_FREQUENCY == 0) {
            long currentTime = System.currentTimeMillis();
            System.out.println(String.format("Game %6d, current result (%.3f), time : %.3f second(s) ", gameNumber, 1.0 * points / gameNumber, (currentTime - start) / 1000.0));
         }
      }
      long end = System.currentTimeMillis();
      player.play(new YambRequest(RequestType.MATCH_END));
      double finalResult = 1.0 * points / Constants.GAMES_COUNT;
      double timeSpent = (end - start) / 1000.0;
      System.out.println(String.format("Final result (%.3f), time : %.3f second(s).", finalResult, timeSpent));
      
      ReportFile reportFile = new ReportFile("result.txt");
      reportFile.writeln("Number of games : " + Constants.GAMES_COUNT);
      reportFile.writeln(String.format("Result          : %.3f", finalResult));
      reportFile.writeln(String.format("Time            : %.3f", timeSpent));
      reportFile.writeln(String.format("Best game       : %d %d", bestGameIndex, bestGameScore));
      reportFile.close();
   }
}
