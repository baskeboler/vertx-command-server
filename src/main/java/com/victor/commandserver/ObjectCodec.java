package com.victor.commandserver;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

/**
 * Creado por Victor Gil<baskeboler@gmail.com>, 12/5/15.
 */
public class ObjectCodec<T> implements MessageCodec<T, T> {
    @Override
    public void encodeToWire(Buffer buffer, T t) {

    }

    @Override
    public T decodeFromWire(int pos, Buffer buffer) {
        return null;
    }

    @Override
    public T transform(T t) {
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
