package com.comtrade.yamb.implementation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class ReportFile implements AutoCloseable {
   private FileWriter fileWriter;
   private BufferedWriter writer;

   public ReportFile(String filename) throws IOException {
      fileWriter = new FileWriter(new File(filename));
      writer = new BufferedWriter(fileWriter);
   }

   public void write(String text) {
      try {
         writer.write(text);
      } catch (Exception e) {
      }
   }
   
   public void writeln(String text) {
      write(text + "\n");
   }
   
   public void write(ReportRow reportRow, String text) {
      writeln(reportRow.getId() + ": " + text);
   }

   @Override
   public void close() throws IOException {
      writer.flush();
      writer.close();
      fileWriter.close();
   }
}
