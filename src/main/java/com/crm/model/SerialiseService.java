//package com.crm.model;
//
//import com.crm.generic.*;
//import com.fasterxml.jackson.core.JsonGenerationException;
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import java.io.IOException;
//import java.util.List;
//
//public interface SerialiseService<T extends Object> {
//public CustomSerializer(Class t) {
//      super(t);
//   }
//   public CustomSerializer(O) {
//      this(Employee.class);
//   }
//   @Override
//   public void serialize(Employee emp, JsonGenerator jgen, SerializerProvider sp) throws IOException, JsonGenerationException {
//      StringBuilder sb = new StringBuilder();
//      jgen.writeStartObject();
//      jgen.writeNumberField("id", emp.getId());
//      jgen.writeStringField("name", emp.getName());
//      for(String s: emp.getLanguages()) {
//         sb.append(s).append(";");
//      }
//      jgen.writeStringField("languages", sb.toString());
//      jgen.writeEndObject();
//   }
//}
