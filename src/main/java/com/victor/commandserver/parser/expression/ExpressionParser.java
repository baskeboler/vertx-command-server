package com.victor.commandserver.parser.expression;

import com.victor.commandserver.parser.AbstractParser;
import com.victor.commandserver.parser.command.Command;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.support.StringVar;
import org.parboiled.support.Var;

@BuildParseTree
public class ExpressionParser extends AbstractParser<Command> {

  @Override
  public Rule Root() {
    return FirstOf(Assignment(), Evaluation());
  }

  public Rule Assignment() {
    StringVar id = new StringVar();
    return Sequence(Identifier(), id.set(match()), '=', Evaluation(), push(new AssignmentExpression(id.get(), pop())));
  }

  public Rule Evaluation() {
    return FirstOf(Sum(), Term());
  }

  public Rule Term() {
    // TODO Auto-generated method stub
    return FirstOf(Variable(), Number());
  }

  public Rule Sum() {
    Var<Expression> left = new Var(), right = new Var();
    // TODO Auto-generated method stub
    return Sequence(Term(), '+', Evaluation(),
            right.set(pop()), left.set(pop()),
            push(new AdditionExpression(left.get(), right.get())));
  }

  public Rule Number() {
    return Sequence(Digits(), push(new ValueExpression(Integer.valueOf(match()))));
  }

  public Rule Digits() {
    return OneOrMore(Digit());
  }

  public Rule Digit() {
    return CharRange('0', '9');
  }

  public Rule Variable() {
    return Sequence(Identifier(), push(new IdentifierExpression(match())));
  }

  public Rule Identifier() {
    return Sequence(AlphaChar(), ZeroOrMore(AlphaNumericChar()));
  }

  public Rule AlphaChar() {
    return FirstOf(CharRange('a', 'z'), CharRange('A', 'Z'));
  }

  public Rule AlphaNumericChar() {
    return FirstOf(AlphaChar(), Digit());
  }

}
