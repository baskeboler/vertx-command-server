package com.victor.commandserver.parser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

@BuildParseTree
public class CommandParser extends AbstractParser<Command>{

  ExpressionParser expressionParser;

  public CommandParser(ExpressionParser expParse) {
    expressionParser = expParse;

  }

  @Override
  public Rule Root() {
    // TODO Auto-generated method stub
    return Sequence(Comando(), EOI);
  }

  Rule Comando() {
    return FirstOf(ComandoEcho(), ComandoSalir(), ComandoEval());
  }

  public Rule ComandoEcho() {
    // TODO Auto-generated method stub
    return Sequence("echo", Espacio(), Texto(), push(new EchoCommand(match())));
  }

  public Rule ComandoSalir() {
    return Sequence("salir", push(new ExitCommand()));
  }

  public Rule ComandoEval() {
    return Sequence(expressionParser.Root(), push(new EvalCommand(pop())));
  }

  public Rule Espacio() {
    return ZeroOrMore(AnyOf(" \t\f"));
  }

  public Rule Texto() {
    return Sequence(Palabra(), ZeroOrMore(Espacio(), Palabra()));
  }

  public Rule Palabra() {
    return OneOrMore(AlphaNumericChar());
  }

  public Rule AlphaNumericChar() {
    return FirstOf(CharRange('a', 'z'), CharRange('A', 'Z'), Digito());
  }

  public Rule Digito() {
    return CharRange('0', '9');
  }
}
