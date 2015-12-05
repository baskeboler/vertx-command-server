package com.victor.commandserver;
import io.vertx.core.AsyncResult;
import io.vertx.core.Context;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.net.NetSocket;
import io.vertx.core.parsetools.RecordParser;

public class CommandServerVerticle extends BaseVerticle {

  enum ParseToken {
    SIZE, PAYLOAD
  }
  
  @Override
  public void init(Vertx vertx, Context context) {
    // TODO Auto-generated method stub
    info("Running init() in CommandServerVerticle");
    super.init(vertx, context);
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
      }
    }).listen(port, res -> {
      if (res.succeeded()) {
        info("Listening on port" + port);
        startFuture.complete();
      } else {
        startFuture.fail(res.cause());
      }
    });
  }

//  Future<Void> deployProcessors() {
//    Future<Void> future = Future.<Void>future();
//    Handler<AsyncResult<String>> handler = res -> {
//      if (res.succeeded()) {
//        future.complete();
//      } else {
//        future.fail(res.cause());
//      }
//    };
//    DeploymentOptions options = new DeploymentOptions().setWorker(isWorker).setInstances(nInstances);
//    vertx.deployVerticle(getName(), options, handler);
//    return future;
//  }

  @Override
  protected String getName() {
    // TODO Auto-generated method stub
    return this.getClass().getName();
  }

//  @Override protected boolean is

}
