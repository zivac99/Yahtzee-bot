package com.comtrade.yamb.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.comtrade.yamb.Column;
import com.comtrade.yamb.ColumnType;
import com.comtrade.yamb.Field;
import com.comtrade.yamb.FieldType;

abstract class YambColumn implements Column {
   private ColumnType type;
   private List<Field> fields;
   private Map<FieldType, YambField> fieldsMap;
   private int score;

   protected YambColumn(ColumnType type) {
      this.type = type;

      fields = new ArrayList<>();
      fieldsMap = new HashMap<>();
      for (FieldType fieldType : FieldType.values()) {
         YambField field = new YambField(type, fieldType);
         fields.add(field);
         fieldsMap.put(fieldType, field);
      }
   }

   @Override
   public List<Field> getFields() {
      return fields;
   }

   @Override
   public ColumnType getType() {
      return type;
   }

   public void setValue(int index, int value) {
      normalize();
   }

   private int calculateProduct() {
      // sve dok minimum i maksimum nisu odigrani - ne treba racunati Product
      if (fieldsMap.get(FieldType.MAXIMUM).getValue() == 0 || fieldsMap.get(FieldType.MINIMUM).getValue() == 0) {
         return 0;
      }
      
      int difference = fieldsMap.get(FieldType.MAXIMUM).getValue() - fieldsMap.get(FieldType.MINIMUM).getValue();
      if (difference < 0) {
         difference = 0;
      }
      return fieldsMap.get(FieldType.FIELD_1).getValue() * difference;
   }

   public void normalize() {
      int sum = 0;
      sum += fieldsMap.get(FieldType.FIELD_1).getValue();
      sum += fieldsMap.get(FieldType.FIELD_2).getValue();
      sum += fieldsMap.get(FieldType.FIELD_3).getValue();
      sum += fieldsMap.get(FieldType.FIELD_4).getValue();
      sum += fieldsMap.get(FieldType.FIELD_5).getValue();
      sum += fieldsMap.get(FieldType.FIELD_6).getValue();
      if ( sum>=60 ) {
         sum += 30;
      }
      fieldsMap.get(FieldType.SUM).setValue(sum);

      int product = calculateProduct();
      fieldsMap.get(FieldType.PRODUCT).setValue(product);

      int subscore = 0;
      subscore += fieldsMap.get(FieldType.KENTA).getValue();
      subscore += fieldsMap.get(FieldType.TRILING).getValue();
      subscore += fieldsMap.get(FieldType.FULL).getValue();
      subscore += fieldsMap.get(FieldType.POKER).getValue();
      subscore += fieldsMap.get(FieldType.YAMB).getValue();
      fieldsMap.get(FieldType.SUBTOTAL).setValue(subscore);

      score = sum + product + subscore;
      fieldsMap.get(FieldType.TOTAL).setValue(score);
   }

   @Override
   public int getScore() {
      return score;
   }
   
   @Override
   public String toString() {
      return getFields().stream().map(field -> (field.isPlayed() ? String.valueOf(field.getValue()) : ".")).collect(Collectors.joining(" "));
   }
}
