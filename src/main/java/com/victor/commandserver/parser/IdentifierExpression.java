package com.victor.commandserver.parser;

public class IdentifierExpression implements Expression{
  String identifier;
  public IdentifierExpression(String name) {
    // TODO Auto-generated constructor stub
    identifier = name;
  }
  @Override
  public int getValue() {
    // TODO Auto-generated method stub
    return Variables.getValue(identifier);
  }

}
