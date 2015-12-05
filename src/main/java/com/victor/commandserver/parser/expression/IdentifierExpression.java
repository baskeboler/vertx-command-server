package com.victor.commandserver.parser.expression;

import com.victor.commandserver.parser.command.Command;
import com.victor.commandserver.parser.Variables;

public class IdentifierExpression implements Command {
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

  public String getIdentifier() {
    return identifier;
  }
}
