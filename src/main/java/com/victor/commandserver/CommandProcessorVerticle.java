package com.victor.commandserver;

import com.victor.commandserver.parser.command.Command;
import com.victor.commandserver.parser.command.EchoCommand;
import com.victor.commandserver.parser.command.ExitCommand;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandProcessorVerticle extends AbstractVerticle {
  public static final String COMMAND_PROCESSOR = "com.victor.command.processor";
  private static final Logger logger = LoggerFactory.getLogger(CommandProcessorVerticle.class);

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    // TODO Auto-generated method stub
    logger.info("Starting CommandProcessorVerticle");
    EventBus eventBus = vertx.eventBus();
    // vertx.
    eventBus.<Buffer>consumer(COMMAND_PROCESSOR).handler(this::handleCommand);

    startFuture.complete();
  }

  public void handleCommand(Message<Buffer> msg) {
    logger.info("Handling command");
    Buffer buffer = msg.body();
    vertx.eventBus().<Command>send(CommandParserVerticle.COMMAND_PARSER, buffer, reply -> {
      logger.info("Got response from parser");
      Message<Command> result = reply.result();
      Buffer resp = doCommand(result.body());
      msg.reply(resp);
    });

  }

  private Buffer doCommand(Command cmd) {
    Buffer resp = Buffer.buffer();
    if (cmd instanceof EchoCommand) {
      EchoCommand echo = (EchoCommand) cmd;
      logger.info("Executing echo for {}", echo.getText());
      resp.appendString(echo.getText());
    } else if (cmd instanceof ExitCommand) {
      ExitCommand exitCommand = (ExitCommand) cmd;
      logger.info("Executing exit");
      resp.appendString("Exit");
    }
    return resp;
  }

}
