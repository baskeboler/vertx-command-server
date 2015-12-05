import com.victor.commandserver.parser.expression.*;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Creado por Victor Gil<baskeboler@gmail.com>, 12/5/15.
 */
public class ExpressionParserTest {
    ExpressionParser parser;

    ReportingParseRunner<Expression> parseRunner;

    @BeforeTest
    public void createParsers() {

        parser = Parboiled.createParser(ExpressionParser.class);
        assertNotNull(parser);
        parseRunner = new ReportingParseRunner<>(parser.Root());
        assertNotNull(parseRunner);
    }

    @Test
    public void createParserTest() {
        ExpressionParser parser = Parboiled.createParser(ExpressionParser.class);
        assertNotNull(parser);
        ReportingParseRunner<Expression> parseRunner = new ReportingParseRunner<>(parser.Root());
        assertNotNull(parseRunner);
    }

    @Test
    public void testParseVar() {
        String t = "varname";
        ParsingResult<Expression> result = parseRunner.run(t);
        assertFalse(result.hasErrors());
        assertTrue(result.matched);

        Expression resultValue = result.resultValue;
        boolean isId = resultValue instanceof IdentifierExpression;
        assertTrue(isId);

        IdentifierExpression idExp = (IdentifierExpression) resultValue;
        assertEquals(t, idExp.getIdentifier());
    }

    @Test
    public void testParseNumber() {
        String t = "2344";
        ParsingResult<Expression> result = parseRunner.run(t);
        assertFalse(result.hasErrors());
        assertTrue(result.matched);

        Expression resultValue = result.resultValue;
        boolean isId = resultValue instanceof ValueExpression;
        assertTrue(isId);
    }

    @Test
    public void testParseSum() {
        String t = "2344+3";
        ParsingResult<Expression> result = parseRunner.run(t);
        assertFalse(result.hasErrors());
        assertTrue(result.matched);

        Expression resultValue = result.resultValue;
        boolean isId = resultValue instanceof AdditionExpression;
        assertTrue(isId);
    }

    @Test
    public void testParseSum2() {
        String t = "2344+miVariable";
        ParsingResult<Expression> result = parseRunner.run(t);
        assertFalse(result.hasErrors());
        assertTrue(result.matched);

        Expression resultValue = result.resultValue;
        boolean isId = resultValue instanceof AdditionExpression;
        assertTrue(isId);
    }

    @Test
    public void testParseSum3() {
        String t = "2344+3+111";
        ParsingResult<Expression> result = parseRunner.run(t);
        assertFalse(result.hasErrors());
        assertTrue(result.matched);

        Expression resultValue = result.resultValue;
        boolean isId = resultValue instanceof AdditionExpression;
        assertTrue(isId);
    }

    @Test
    public void testParseAssignment() {
        String t = "a=123+23+3+3++";
        ParsingResult<Expression> result = parseRunner.run(t);
        assertFalse(result.hasErrors());
        assertTrue(result.matched);

        Expression resultValue = result.resultValue;
        boolean isId = resultValue instanceof AssignmentExpression;
        assertTrue(isId);
    }
}
