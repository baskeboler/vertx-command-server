package com.victor.commandserver.parser;

public class EchoCommand extends Command{
  /**
   * 
   */
  private static final long serialVersionUID = -8259054700427195897L;
  private String text;
  
  public EchoCommand(String t) {
    text = t;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}