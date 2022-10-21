package com.comtrade.yamb.implementation;

enum ReportRow {
   THROW("T"),
   DECISION("D"),
   NAJAVA("N"),
   BOARD("B"),
   SCORE("S"),
   ERROR("E");
   
   private String id;
   
   private ReportRow(String id) {
      this.id = id;
   }
   
   public String getId() {
      return id;
   }
}
