package com.victor.commandserver;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;

public abstract class BaseVerticle extends AbstractVerticle {
  protected final Logger logger = Logger.getLogger(getName());
  
  @Override
  public void init(Vertx vertx, Context ctx) {
    // TODO Auto-generated method stub
    logger.info("Initializing " + ctx.getName());
    
  }
  
  public void info(String t) {
    logger.info(t);
  }
  
  public void warn(String t) {
    logger.warning(t);
  }
  
  
  protected abstract String getName();

}
