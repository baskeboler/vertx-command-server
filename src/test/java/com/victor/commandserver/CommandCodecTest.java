package com.victor.commandserver;

import com.victor.commandserver.parser.command.Command;
import com.victor.commandserver.parser.command.ExitCommand;
import io.vertx.core.buffer.Buffer;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

/**
 * Creado por Victor Gil<baskeboler@gmail.com>, 12/5/15.
 */
public class CommandCodecTest {
    private CommandCodec codec;
    Buffer buffer;

    @BeforeTest
    public void init() {
        codec = new CommandCodec();
        buffer = Buffer.buffer();

    }
    @Test
    public void testEncodeToWire() throws Exception {
        codec.encodeToWire(buffer, new ExitCommand());
        assertNotEquals(buffer.length(), 0);
    }

    @Test
    public void testDecodeFromWire() throws Exception {
        codec.encodeToWire(buffer, new ExitCommand());
        assertNotEquals(buffer.length(), 0);
        Command command = codec.decodeFromWire(0, buffer);
        assertTrue(command instanceof Command);
        assertTrue(command instanceof ExitCommand);
    }
}