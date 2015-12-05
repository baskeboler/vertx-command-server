package com.victor.commandserver;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import com.victor.commandserver.parser.Command;
import com.victor.commandserver.parser.CommandParser;

import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;

public class CommandProcessorVerticle extends BaseVerticle {
  public static final String COMMAND_PROCESSOR = "com.victor.command.processor";
  private CommandParser parser;
  private ParseRunner<Command> parseRunner;

  @Override
  public void init(Vertx vertx, Context context) {
    // TODO Auto-generated method stub
    super.init(vertx, context);
    parser = Parboiled.createParser(CommandParser.class);
    parseRunner = new ReportingParseRunner<Command>(parser.Root());
  }

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    // TODO Auto-generated method stub
    EventBus eventBus = vertx.eventBus();
    // vertx.
    eventBus.<Buffer>consumer(COMMAND_PROCESSOR).handler(this::handleCommand);

    startFuture.complete();
  }

  public void handleCommand(Message<Buffer> msg) {
    info("Handling command");
    byte[] bytes = msg.body().getBytes();
    String commandStr = new String(bytes);
    ParsingResult<Command> result = parseRunner.run(commandStr);
    if (result.hasErrors()) {
      String errors = result.parseErrors.stream().map(e -> e.getErrorMessage()).reduce("", (s1, s2) -> s1 + "\n" + s2);
      
      msg.fail(1, errors);
    }
  }

  @Override
  protected String getName() {
    // TODO Auto-generated method stub
    return this.getClass().getName();
  }


}
