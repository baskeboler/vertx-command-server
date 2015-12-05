package com.victor.commandserver;

import com.victor.commandserver.parser.Command;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Creado por Victor Gil<baskeboler@gmail.com>, 12/5/15.
 */
public class CommandCodec implements MessageCodec<Command, Command> {
    @Override
    public void encodeToWire(Buffer buffer, Command command) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(command);
            buffer.appendBytes(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Command decodeFromWire(int i, Buffer buffer) {
        return null;
    }

    @Override
    public Command transform(Command command) {
        return null;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public byte systemCodecID() {
        return 0;
    }
}
