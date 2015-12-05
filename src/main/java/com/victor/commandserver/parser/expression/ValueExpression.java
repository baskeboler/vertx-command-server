package com.victor.commandserver.parser.expression;

import com.victor.commandserver.parser.command.Command;

public class ValueExpression implements Command {
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
