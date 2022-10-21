package com.comtrade.yamb.implementation;

import com.comtrade.yamb.Board;
import com.comtrade.yamb.Die;
import com.comtrade.yamb.FieldData;
import com.comtrade.yamb.Request;
import com.comtrade.yamb.RequestType;

class YambRequest implements Request {
   private RequestType type;
   private Board board;
   private int gameNumber;
   private int moveNumber;
   private int throwNumber;
   private FieldData najavljenoPolje;
   private Die[] dice;

   public YambRequest(RequestType type, int gameNumber, int moveNumber, int throwNumber, Board board, Die[] dice, FieldData najavljenoPolje) {
      this.type = type;
      this.gameNumber = gameNumber;
      this.moveNumber = moveNumber;
      this.throwNumber = throwNumber;
      this.board = board;
      this.dice = dice;
      this.najavljenoPolje = najavljenoPolje;
   }

   public YambRequest(RequestType type, int gameNumber, int moveNumber, int throwNumber, Board board) {
      this(type, gameNumber, moveNumber, throwNumber, board, null, null);
   }
   
   public YambRequest(RequestType type, int gameNumber, int moveNumber, int throwNumber) {
      this(type, gameNumber, moveNumber, throwNumber, null, null, null);
   }
   
   public YambRequest(RequestType type, int gameNumber, int moveNumber) {
      this(type, gameNumber, moveNumber, 0);
   }
   
   public YambRequest(RequestType type, int gameNumber) {
      this(type, gameNumber, 0);
   }
   
   public YambRequest(RequestType type) {
      this(type, 0);
   }
   
   @Override
   public Board getBoard() {
      return board;
   }

   @Override
   public Die[] getDice() {
      return dice;
   }

   @Override
   public int getThrowNumber() {
      return throwNumber;
   }

   @Override
   public FieldData getNajavljenoPolje() {
      return najavljenoPolje;
   }

   @Override
   public RequestType getType() {
      return type;
   }

   @Override
   public int getGameNumber() {
      return gameNumber;
   }

   @Override
   public int getMoveNumber() {
      return moveNumber;
   }
}
