package com.victor.commandserver;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) throws IOException {
    logger.info("Starting Application");
    VertxOptions vertxOptions = new VertxOptions().setClustered(false).setWorkerPoolSize(64);
    DeploymentOptions options = new DeploymentOptions().setWorker(false);
      Vertx vertx = Vertx.vertx(vertxOptions);
    vertx.deployVerticle(CommandServerVerticle.class.getName(), options, res -> {
        if (res.succeeded()) {
            logger.info("Deployment successfull: " + res.result());
        } else {
            logger.error("We fucked up");
            res.cause().printStackTrace();
        }
    });

      System.in.read();
  }

}
