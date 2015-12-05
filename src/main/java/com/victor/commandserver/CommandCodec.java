package com.victor.commandserver;

import com.victor.commandserver.parser.command.Command;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

import java.io.*;

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
            byte[] bytes = byteArrayOutputStream.toByteArray();
            buffer.appendInt(bytes.length);
            buffer.appendBytes(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Command decodeFromWire(int i, Buffer buffer) {

        int size = buffer.getInt(i);
        i += 4;
        byte[] bytes = buffer.getBytes(i, i + size);
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        Command o = null;
        try {
            ObjectInputStream objIn = new ObjectInputStream(in);
            o = (Command) objIn.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public Command transform(Command command) {
        return command;
    }

    @Override
    public String name() {
        return getClass().getName();
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
