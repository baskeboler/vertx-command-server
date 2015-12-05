package com.victor.commandserver;

import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import io.vertx.core.parsetools.RecordParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CommandServerVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(CommandServerVerticle.class);

    @Override
    public void init(Vertx vertx, Context context) {
        // TODO Auto-generated method stub
        info("Running init() in CommandServerVerticle");
        super.init(vertx, context);
    }

    private void info(String s) {
        logger.info(s);
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        info("Starting Command Server Verticle");
        //eventBus = vertx.eventBus();
        //Future<Void> deployProcessors = deployProcessors();
        //deployProcessors.setHandler(handler);
        int port = 1234;
        vertx.createNetServer().connectHandler(new Handler<NetSocket>() {
            ParseToken nextToken;

            @Override
            public void handle(NetSocket socket) {
                logger.info("Handling new connection from {}", socket.remoteAddress().host());
                nextToken = ParseToken.SIZE;
                RecordParser parser = RecordParser.newFixed(4, null);
                Handler<Buffer> h = buffer -> {
                    if (nextToken == ParseToken.SIZE) {
                        int size = buffer.getInt(0);
                        parser.fixedSizeMode(size);
                        nextToken = ParseToken.PAYLOAD;
                    } else {
                        vertx.eventBus().<Buffer>send("com.victor.command.processor", buffer, reply -> {
                            Buffer body = reply.result().body();
                            Buffer result = Buffer.buffer().appendInt(body.length()).appendBuffer(buffer);
                            socket.write(result);
                        });
                        nextToken = ParseToken.SIZE;
                    }
                };
                parser.setOutput(h);
                socket.handler(parser);
                socket.closeHandler(aVoid -> {
                    logger.info("Socket connection closed from {}", socket.remoteAddress().host());
                });
            }
        }).listen(port, res -> {
            if (res.succeeded()) {
                info("Listening on port " + port);
                startFuture.complete();
            } else {
                startFuture.fail(res.cause());
            }
        });
    }

    enum ParseToken {
        SIZE, PAYLOAD
    }

}
