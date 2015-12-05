package com.victor.commandserver;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import io.vertx.core.parsetools.RecordParser;

/**
 * Creado por Victor Gil<baskeboler@gmail.com>, 12/5/15.
 */
public class Client {
    public static void main(String args[]) {
        Vertx.vertx().createNetClient().connect(1234, "localhost", event -> {
            if (event.succeeded()) {
                String cmd = "echo anda a cagar";
                Buffer data = Buffer.buffer();
                data.appendInt(cmd.getBytes().length).appendBytes(cmd.getBytes());
                NetSocket socket = event.result();
                RecordParser p = RecordParser.newFixed(4, null);
                p.setOutput(new Handler<Buffer>() {
                    Token next = Token.SIZE;

                    @Override
                    public void handle(Buffer event) {
                        if (next == Token.SIZE) {
                            int size = event.getInt(0);
                            p.fixedSizeMode(size);
                            next = Token.PAYLOAD;
                        } else {
                            byte[] bytes = event.getBytes();
                            String res = new String(bytes);
                            System.out.println("Got response: " + res);
                            next = Token.SIZE;
                            p.fixedSizeMode(4);
                        }
                    }
                });
                socket.handler(p);
                for (int i = 0; i < 200000; i++)
                    socket.write(data);
            }
        });
    }

    enum Token {
        SIZE, PAYLOAD
    }
}
