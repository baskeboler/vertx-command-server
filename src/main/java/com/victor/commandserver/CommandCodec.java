package com.victor.commandserver;

import com.victor.commandserver.parser.command.Command;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

import java.io.*;

/**
 * Creado por Victor Gil<baskeboler@gmail.com>, 12/5/15.
 */
public class CommandCodec<C extends Command> implements MessageCodec<C, C> {

    Class<C> type;

    public CommandCodec(Class<C> type) {
        this.type = type;
    }

    @Override
    public void encodeToWire(Buffer buffer, C command) {
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
    public C decodeFromWire(int i, Buffer buffer) {

        int size = buffer.getInt(i);
        i += 4;
        byte[] bytes = buffer.getBytes(i, i + size);
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        C o = null;
        try {
            ObjectInputStream objIn = new ObjectInputStream(in);
            o = (C) objIn.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public C transform(C command) {
        return command;
    }

    @Override
    public String name() {
        return String.format("%s<%s>", getClass().getName(), type.getName());
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
