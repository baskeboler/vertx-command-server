package com.victor.commandserver.parser;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.support.Var;

@BuildParseTree
public class CommandParser extends AbstractParser<Command>{

  Rule Comando() {
    return FirstOf(ComandoEcho(), ComandoEval(), ComandoSalir());
  }

  public Rule ComandoEcho() {
    // TODO Auto-generated method stub
    return null;
  }

  public Rule ComandoEval() {
    // TODO Auto-generated method stub
    return null;
  }

  public Rule ComandoSalir() {
    // TODO Auto-generated method stub
    return null;
  }
  
  public Rule Espacio() {
    return ZeroOrMore(AnyOf(" "));
  }
  
  public Rule Digito() {
    return CharRange('0', '9');
  }
  
  public Rule Digitos() {
    return OneOrMore(Digito());
  }
  
  public Rule Numero() {
    return Sequence(Digitos(), pop(new Var()));
  }

  @Override
  public Rule Root() {
    // TODO Auto-generated method stub
    return null;
  }
}
