package com.victor.commandserver.parser;

public class ValueExpression implements Expression{
  int value;
  public  ValueExpression(int v) {
    value = v;
  }
  @Override
  public int getValue() {
    // TODO Auto-generated method stub
    return value;
  }

}
