package com.victor.commandserver;

import com.victor.commandserver.parser.command.Command;
import com.victor.commandserver.parser.command.CommandParser;
import com.victor.commandserver.parser.expression.ExpressionParser;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;
import org.parboiled.Parboiled;
import org.parboiled.errors.ParseError;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creado por Victor Gil<baskeboler@gmail.com>, 12/5/15.
 */
public class CommandParserVerticle extends AbstractVerticle {
    public static final String COMMAND_PARSER = "com.victor.command.parser";
    private static final Logger logger = LoggerFactory.getLogger(CommandParserVerticle.class);
    CommandParser commandParser;
    ReportingParseRunner<Command> parseRunner;

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);
        logger.info("Initializing CommandParserVerticle");
        ExpressionParser expParser = Parboiled.createParser(ExpressionParser.class);
        commandParser = Parboiled.createParser(CommandParser.class, expParser);
        parseRunner = new ReportingParseRunner<>(commandParser.Root());
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        logger.info("Starting CommandParserVerticle");
        vertx.eventBus().consumer(COMMAND_PARSER, this::handleMessage);
        startFuture.complete();
    }

    private void handleMessage(Message<Buffer> objectMessage) {
        byte[] bytes = objectMessage.body().getBytes();
        String cmdStr = new String(bytes);
        logger.info("Handling parse for: {}", cmdStr);
        ParsingResult<Command> result = parseRunner.run(cmdStr);
        if (result.matched) {
            Command resultValue = result.resultValue;
            objectMessage.<Command>reply(resultValue);
        } else {
            String errorString = result.parseErrors.stream()
                    .map(ParseError::getErrorMessage)
                    .reduce("", (s1, s2) -> s1 + "\n" + s2);
            objectMessage.fail(ErrorCodes.PARSE_FAILURE, errorString);
        }
    }
}
