package com.dovendev.track.graphql.scalars;

public class Utils {

  public static String typeName(Object obj) {
    return obj != null ? obj.getClass().getSimpleName() : "null";
  }
}
