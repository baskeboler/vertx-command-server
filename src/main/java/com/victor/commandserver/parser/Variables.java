package com.victor.commandserver.parser;

import java.util.HashMap;
import java.util.Map;

public class Variables {
  public static final Map<String, Integer> varMap = new HashMap<>();
  
  public static void setValue(String id, int value) {
    varMap.put(id, value);
  }
  
  public static Integer getValue(String id) {
    return varMap.get(id);
  }
}
