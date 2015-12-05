package com.victor.commandserver.parser.command;

import com.victor.commandserver.parser.expression.Expression;

/**
 * Creado por Victor Gil<baskeboler@gmail.com>, 12/5/15.
 */
public class EvalCommand implements Command {
    private Expression expression;

    public EvalCommand(Expression e) {
        expression = e;

    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public int getValue() {
        return 0;
    }
}
