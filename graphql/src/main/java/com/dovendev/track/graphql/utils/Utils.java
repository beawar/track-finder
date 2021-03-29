package com.dovendev.track.graphql.utils;

import graphql.GraphqlErrorException;
import java.util.HashMap;
import java.util.Map;

public class Utils {

  public static String typeName(Object obj) {
    return obj != null ? obj.getClass().getSimpleName() : "null";
  }

  public static Long parseId(Object obj) throws GraphqlErrorException {
    try {
      if (obj == null) {
        return null;
      }
      return Long.valueOf(String.valueOf(obj));
    } catch (NumberFormatException ne) {
      throw buildError("INVALID_ID", "Invalid id " + obj.toString(), ne);
    }
  }

  public static GraphqlErrorException buildError(String code, String description, Exception e) {
    GraphqlErrorException.Builder errorBuilder = GraphqlErrorException.newErrorException();
    errorBuilder.message(e != null ? e.getMessage() : description);
    Map<String, Object> errorData = new HashMap<>();
    errorData.put("error_code", code);
    errorData.put("error_message", description);
    errorBuilder.extensions(errorData);
    return errorBuilder.build();
  }
}
