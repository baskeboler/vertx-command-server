package com.victor.commandserver.parser;

import org.parboiled.Rule;

public class ExpressionParser extends AbstractParser<Expression> {

  @Override
  public Rule Root() {
    // TODO Auto-generated method stub
    return FirstOf(Assignment(), Evaluation());
  }

  private Rule Assignment() {
    // TODO Auto-generated method stub
    return null;
  }

  public Rule Evaluation() {
    // TODO Auto-generated method stub
    return FirstOf(Sum(), Term());
  }

  private Rule Term() {
    // TODO Auto-generated method stub
    return FirstOf(Variable(), Number());
  }

  public Rule Sum() {

    // TODO Auto-generated method stub
    return Sequence(Term(), '+', Term(), push(new AdditionExpression(pop(), pop())));
  }

  private Rule Number() {
    // TODO Auto-generated method stub
    return Sequence(Digits(), push(new ValueExpression(Integer.valueOf(match()))));
  }

  private Rule Digits() {
    // TODO Auto-generated method stub
    return OneOrMore(Digit());
  }

  private Rule Digit() {
    // TODO Auto-generated method stub
    return CharRange('0', '9');
  }

  public Rule Variable() {
    // TODO Auto-generated method stub
    return null;
  }

}
