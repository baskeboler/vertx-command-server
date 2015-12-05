package com.victor.commandserver.parser;
import org.parboiled.BaseParser;
import org.parboiled.Rule;

public abstract class AbstractParser<V> extends BaseParser<V> {

  public abstract Rule Root();
}
