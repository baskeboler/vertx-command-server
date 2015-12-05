package com.victor.commandserver.parser.expression;

import com.victor.commandserver.parser.command.Command;

/**
 * Creado por Victor Gil<baskeboler@gmail.com>, 12/5/15.
 */
public class AssignmentExpression implements Command {
    private String identifier;

    private Expression expression;

    public AssignmentExpression(String identifier, Expression expression) {
        this.expression = expression;
        this.identifier = identifier;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public int getValue() {
        return 0;
    }
}
