package com.comtrade.yamb.implementation;

import java.io.IOException;

import com.comtrade.yamb.Column;
import com.comtrade.yamb.ColumnType;
import com.comtrade.yamb.Die;
import com.comtrade.yamb.Field;
import com.comtrade.yamb.FieldData;
import com.comtrade.yamb.FieldType;
import com.comtrade.yamb.Request;
import com.comtrade.yamb.RequestType;
import com.comtrade.yamb.player.Player;

class YambGame {
   private static final int MAX_THROWS = 3;
   private static final int DICE_COUNT = 6;

   private int gameNumber;
   private int moveNumber;
   private int throwNumber;
   private Player player;
   private YambBoard board;
   private ReportFile reportFile;
   private YambDie[] dice;
   private YambResponse response;
   private FieldData najavljenoPolje;

   public YambGame(Player player, int gameNumber) throws IOException {
      this.player = player;
      this.gameNumber = gameNumber;
      board = new YambBoard();
      dice = new YambDie[DICE_COUNT];
      for (int i = 0; i < DICE_COUNT; i++) {
         dice[i] = new YambDie();
      }
   }

   private long getScore() {
      return board.getScore();
   }

   private int getPlayableFieldsCount() {
      int playableFieldsCount = 0;
      for (FieldType fieldType : FieldType.values()) {
         if (fieldType.isPlayable()) {
            playableFieldsCount++;
         }
      }
      return board.getColumns().length * playableFieldsCount;
   }

   public long play() throws Exception {
      try {
         player.play(new YambRequest(RequestType.GAME_START, gameNumber));
         reportFile = new ReportFile(String.format("%05d.ymb", gameNumber));
         int maxMoves = getPlayableFieldsCount();
         for (moveNumber = 1; moveNumber <= maxMoves; moveNumber++) {
            RandomNumbersGenerator.setRandomNumbersData(gameNumber, moveNumber);
            player.play(new YambRequest(RequestType.MOVE_START, gameNumber, moveNumber));
            playMove();
            player.play(new YambRequest(RequestType.MOVE_END, gameNumber, moveNumber, 0, board));
         }
         player.play(new YambRequest(RequestType.GAME_END, gameNumber, maxMoves, MAX_THROWS, board));
      } finally {
         reportFile.close();
      }
      return getScore();
   }

   private YambField findNormalField() {
      for (Column column : board.getColumns()) {
         for (Field field : column.getFields()) {
            if (!field.isUKoloniNajava() && !field.isUKoloniRucna() && field.isPlayable()) { return (YambField) field; }
         }
      }
      return null;
   }

   private YambField findNajavaField() {
      for (Column column : board.getColumns()) {
         for (Field field : column.getFields()) {
            if (field.isUKoloniNajava() && field.isPlayable()) { return (YambField) field; }
         }
      }
      return null;
   }
   
   private YambField findRucnoField() {
      for (Column column : board.getColumns()) {
         for (Field field : column.getFields()) {
            if (field.isUKoloniRucna() && field.isPlayable()) { return (YambField) field; }
         }
      }
      return null;
   }
   
   private void scoreMove() {
      FieldData fieldData = response.getFieldData();
      YambField field = toYambField(fieldData);
      field.setValue(dice, throwNumber);
      reportFile.write(ReportRow.DECISION, fieldData.toString());
      board.normalize();
   }

   private void playMove() throws Exception {
      resetDice();
      boolean moveOver = false;
      najavljenoPolje = null;
      for (throwNumber = 1; !moveOver && throwNumber <= MAX_THROWS; throwNumber++) {
         moveOver = playThrow();
      }
      throwNumber--;
      
      scoreMove();
      reportBoard();
   }

   private void resetDice() {
      for (YambDie die : dice) {
         die.setFixed(false);
      }
   }

   private void throwDice() {
      for (YambDie die : dice) {
         if (!die.isFixed()) {
            die.setValue(RandomNumbersGenerator.getNext());
         }
      }
   }

   private void reportDice() throws IOException {
      if ( response.getDice()!=null ) {
         for ( int i=0 ; i<dice.length && i<response.getDice().length ; i++ ) {
            dice[i].setFixed(response.getDice()[i]);
         }
      }
      
      StringBuilder text = new StringBuilder();
      for (Die die : dice) {
         text.append(die.getValue() + (die.isFixed() ? "x" : ".") + " ");
      }
      reportFile.write(ReportRow.THROW, text.toString());
   }

   private void reportBoard() throws IOException {
      reportFile.write(ReportRow.BOARD, board.toString());
      reportFile.write(ReportRow.SCORE, String.valueOf(board.getScore()));
   }

   private boolean playThrow() throws Exception {
      throwDice();
      Request request = new YambRequest(RequestType.THROW, gameNumber, moveNumber, throwNumber, board, dice, najavljenoPolje);
      response = new YambResponse(player.play(request));
      reportDice();
      scoreThrow();
      return response.isMoveOver();
   }

   private YambField toYambField(FieldData fieldData) {
      return (YambField) board.getColumns()[fieldData.getColumnIndex()].getFields().get(fieldData.getFieldIndex());
   }
   
   private void checkForNajava() {
      // najava postoji samo ako je u pitanju prvo bacanje
      if ( throwNumber==1 ) {
         najavljenoPolje = response.getFieldData();
         if ( najavljenoPolje!=null ) {
            YambField field = toYambField(najavljenoPolje);
            reportFile.write(ReportRow.NAJAVA, najavljenoPolje.toString());
            if ( !field.isPlayable() ) {
               reportFile.write(ReportRow.ERROR, "Upis u polje " + najavljenoPolje.toString() + " nije dozvoljen.");
               najavljenoPolje = null;
            } else if ( !field.isUKoloniNajava() && !field.isUKoloniRucna() ) {
//               reportFile.write(ReportRow.ERROR, "Upis u polje sa koordinatama " + najavljenoPolje.toString() + " se ne najavljuje.");
               najavljenoPolje = null;
            } else {
               // najava je ispravna ako se radi o neodigranom polju iz kolone Najava ili Rucna
               if ( field.isUKoloniRucna() ) {
                  response.setMoveOver(true);
               }
               return;
            }
         } 
         
         YambField field = findNormalField();
         if ( field!=null ) {
            return;
         }

         field = findNajavaField();
         if ( field!=null ) {
            reportFile.write(ReportRow.ERROR, "Neophodna je bila informacija o najavi upisa. Polje će biti automatski odabrano.");
            najavljenoPolje = new FieldData(field.getColumnIndex(), field.getFieldIndex());
            reportFile.write(ReportRow.NAJAVA, najavljenoPolje.toString());
            return;
         }
            
         field = findRucnoField();
         if ( field!=null ) {
            reportFile.write(ReportRow.ERROR, "Neophodna je bila informacija o upisu u ručnu kolonu. Polje će biti automatski odabrano.");
            najavljenoPolje = new FieldData(field.getColumnIndex(), field.getFieldIndex());
            response.setFieldData(najavljenoPolje);
            reportFile.write(ReportRow.NAJAVA, najavljenoPolje.toString());
            response.setMoveOver(true);
            return;
         }
      }
   }
   
   private boolean isNenajavljenoFieldDataCorrect() {
      int columnIndex = response.getFieldData().getColumnIndex();
      if ( columnIndex == ColumnType.NAJAVA.ordinal() || columnIndex == ColumnType.RUCNA.ordinal() ) {
         if ( throwNumber>1 ) {
            return false;
         }
      }

      return true;
   }
   
   private YambField decideField() {
      return findNormalField();
   }
   
   private boolean isFieldCorrect() {
      int columnIndex = response.getFieldData().getColumnIndex();
      int fieldIndex = response.getFieldData().getFieldIndex();
      if ( columnIndex >= ColumnType.values().length ) {
         reportFile.write(ReportRow.ERROR, "Indeks kolone nije ispravan");
         return false;
      }
      if ( columnIndex < 0 ) {
         reportFile.write(ReportRow.ERROR, "Indeks kolone nije ispravan");
         return false;
      }
      if ( fieldIndex >= FieldType.values().length ) {
         reportFile.write(ReportRow.ERROR, "Indeks reda nije ispravan");
         return false;
      }
      if ( fieldIndex < 0 ) {
         reportFile.write(ReportRow.ERROR, "Indeks reda nije ispravan");
         return false;
      }

      boolean isFieldCorrect = board.getColumns()[columnIndex].getFields().get(fieldIndex).isPlayable();
      if ( !isFieldCorrect ) {
         reportFile.write(ReportRow.ERROR, "Nije dozvoljeno odigravanje odabranog polja [" + columnIndex + ", " + fieldIndex + "]");
      }
      return isFieldCorrect;
   }
   
   private void checkForFieldData() throws YambException {
      if ( throwNumber==MAX_THROWS || response.isMoveOver() ) {
         if ( najavljenoPolje!=null ) {
            response.setFieldData(najavljenoPolje);
            return;
         } 
         
         if ( response.getFieldData()!=null ) {
            if ( !isNenajavljenoFieldDataCorrect() ) {
               reportFile.write(ReportRow.ERROR, "Nedozvoljen upis u nenajavljeno polje");
            } else {
               if ( isFieldCorrect() ) {
                  return;
               }
            }
         } 
         
         YambField field = decideField();
         if ( field!=null ) {
            response.setFieldData(new FieldData(field.getColumnIndex(), field.getFieldIndex()));
            return;
         }
         
         throw new YambException("No decision made");
      }
   }
   
   private void scoreThrow() throws YambException {
      checkForNajava();
      checkForFieldData();
   }
}
