package com.victor.commandserver.parser.expression;

import com.victor.commandserver.parser.command.Command;

public class AdditionExpression implements Command {
  private Expression left;
  private Expression right;
  public AdditionExpression(Expression left, Expression right) {
    this.left = left;
    this.right = right;
    
  }
  public Expression getRight() {
    return right;
  }
  public void setRight(Expression right) {
    this.right = right;
  }
  public Expression getLeft() {
    return left;
  }
  public void setLeft(Expression left) {
    this.left = left;
  }
  @Override
  public int getValue() {
    // TODO Auto-generated method stub
    return left.getValue() + right.getValue();
  }
  
}
