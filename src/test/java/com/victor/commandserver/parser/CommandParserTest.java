package com.victor.commandserver.parser;

import junit.framework.Assert;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Creado por Victor Gil<baskeboler@gmail.com>, 12/5/15.
 */
public class CommandParserTest {
    CommandParser parser;
    ExpressionParser eParse;
    ReportingParseRunner<Command> runner;

    @BeforeTest
    public void before() {
        eParse = Parboiled.createParser(ExpressionParser.class);
        parser = Parboiled.createParser(CommandParser.class, eParse);
        runner = new ReportingParseRunner<>(parser.Root());
    }

    @Test
    public void testEcho() {
        String t = "echo anaa a la concha tu madre";
        ParsingResult<Command> result = runner.run(t);
        Assert.assertFalse(result.hasErrors());
    }


    @Test
    public void testSalir() {
        String t = "salir";
        ParsingResult<Command> result = runner.run(t);
        Assert.assertFalse(result.hasErrors());
    }


    @Test
    public void testEval() {
        String t = "echo=1+2+3";
        ParsingResult<Command> result = runner.run(t);
        Assert.assertFalse(result.hasErrors());
    }
}