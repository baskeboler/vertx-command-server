package com.victor.commandserver;

import java.io.IOException;
import java.util.logging.Logger;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Launcher;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class Main {
  private static final Logger logger = Logger.getGlobal();

  public static void main(String[] args) throws IOException {
    logger.info("Starting Application");
    VertxOptions vertxOptions = new VertxOptions().setClustered(false).setWorkerPoolSize(64);
    Vertx vertx = Vertx.vertx(vertxOptions);
    DeploymentOptions options = new DeploymentOptions().setWorker(false);
    vertx.deployVerticle(CommandServerVerticle.class.getName(), options, res -> {
      if (res.succeeded()) {
        logger.info("Deployment successfull: " + res.result());
      } else {
        logger.severe("We fucked up");
        res.cause().printStackTrace();
      }
    });
    System.in.read();
  }

}
