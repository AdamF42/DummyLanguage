package models;


import models.expressions.*;
import models.types.*;
import models.statements.*;
import models.types.Bool;
import models.values.*;
import parser.ComplexStaticAnalysisBaseVisitor;
import parser.ComplexStaticAnalysisParser;
import java.util.ArrayList;
import java.util.List;


public class VisitorImpl extends ComplexStaticAnalysisBaseVisitor<ElementBase> {

    @Override
    public StmtBlock visitBlock(ComplexStaticAnalysisParser.BlockContext ctx) {

        if (ctx==null)
            return null;

        List<Stmt> children = new ArrayList<>();
        for (ComplexStaticAnalysisParser.StatementContext stmtCtx : ctx.statement())
            children.add((Stmt) visit(stmtCtx));

        return new StmtBlock(children);
    }

    @Override
    public ElementBase visitStatement(ComplexStaticAnalysisParser.StatementContext ctx) {

        if (ctx==null || ctx.children==null)
            return null;

        return visit(ctx.getChild(0));
    }

    @Override
    public StmtAssignment visitAssignment(ComplexStaticAnalysisParser.AssignmentContext ctx) {

        String id = ctx.ID().getText();
        Exp exp = (Exp) visit(ctx.exp());

        return new StmtAssignment(exp, id);
    }

    @Override
    public StmtDelete visitDeletion(ComplexStaticAnalysisParser.DeletionContext ctx) {
        return new StmtDelete(ctx.ID().getText());
    }

    @Override
    public StmtVarDeclaration visitVarDec(ComplexStaticAnalysisParser.VarDecContext ctx) {

        if (ctx == null || ctx.ID() == null)
            return null;

        Type type = visitType(ctx.type());
        String id = ctx.ID().getText();
        Exp exp = visitExp(ctx.exp());

        return new StmtVarDeclaration(type, id, exp);

    }

    @Override
    public TypeReferenceable visitType(ComplexStaticAnalysisParser.TypeContext ctx) {

        String type = ctx.getText();
        TypeReferenceable typeNode;
        if (type.equals("int")) {
            typeNode = new Int();
        } else if (type.equals("bool")) {
            typeNode = new Bool();
        } else throw new IllegalArgumentException("Unsupported type: " + type);

        return typeNode;
    }

    @Override
    public Parameter visitParameter(ComplexStaticAnalysisParser.ParameterContext ctx) {

        TypeReferenceable paramType = visitType(ctx.type());
        if ( ctx.getText().startsWith("var")) {
            paramType.setReference(true);
        }

        return new Parameter(paramType, ctx.ID().getText());
    }

    @Override
    public Exp visitExp(ComplexStaticAnalysisParser.ExpContext ctx) {

        if (ctx==null || ctx.left==null)
            return null;

        Term leftTerm = (Term) visit(ctx.left);
        Exp righExp = ctx.right != null ? (Exp) visit(ctx.right) : null;

        if (ctx.getChild(0).getText().equals("-")) {
            ValueInt zero = new ValueInt("0");
            String op = ctx.getChild(2)!=null ? ctx.getChild(2).getText() :null;
            if(op==null || righExp==null)
                return new ExpSub(zero, visitTerm(ctx.term()));
            switch (op) {
                case "+":
                    return new ExpAdd(new ExpSub(zero, visitTerm(ctx.term())), visitExp(ctx.right));
                case "-":
                    return new ExpSub(new ExpSub(zero, visitTerm(ctx.term())), visitExp(ctx.right));
                default:
                    throw new IllegalArgumentException("Invalid operator :" + op);
            }
        }

        String op = ctx.getChild(1)!=null ? ctx.getChild(1).getText() :null;
        if(op==null || righExp==null)
            return visitTerm(ctx.left);
        switch (op) {
            case "+":
                return new ExpAdd(leftTerm,righExp);
            case "-":
                return new ExpSub(leftTerm,righExp);
            default:
                throw new IllegalArgumentException("Invalid operator :" + op);
        }
    }

    @Override
    public Term visitTerm(ComplexStaticAnalysisParser.TermContext ctx) {

        if(ctx==null || ctx.left ==null)
            return null;

        Factor leftTerm = visitFactor(ctx.left);
        Term righExp = ctx.right != null ? visitTerm(ctx.right) : null;
        String op = ctx.getChild(1)!=null ? ctx.getChild(1).getText() :null;
        if(op==null || righExp==null)
            return visitFactor(ctx.left);
        switch (op) {
            case "*":
                return new TermMult(leftTerm, righExp);
            case "/":
                return new TermDiv(leftTerm, righExp);
            default:
                throw new IllegalArgumentException("Invalid operator :" + op);
        }

    }

    @Override
    public Factor visitFactor(ComplexStaticAnalysisParser.FactorContext ctx) {

        if(ctx==null || ctx.left ==null)
            return null;

        Exp leftTerm =  (Exp) visit(ctx.left);
        Exp righExp = ctx.right != null ? (Exp) visit(ctx.right) : null;
        String op = ctx.op != null ? ctx.op.getText() : null;
        if(op==null || righExp==null)
            return (Value) visit(ctx.left);
        switch (op) {
            case("&&"):
                return new FactorAnd(leftTerm,righExp);
            case("||"):
                return new FactorOr(leftTerm,righExp);
            case("=="):
                return new FactorEq(leftTerm,righExp);
            case("!="):
                return new FactorNotEq(leftTerm,righExp);
            case(">"):
                return new FactorGr(leftTerm,righExp);
            case("<"):
                return new FactorLr(leftTerm,righExp);
            case(">="):
                return new FactorGre(leftTerm,righExp);
            case("<="):
                return new FactorLre(leftTerm,righExp);
            default:
                throw new IllegalArgumentException("Invalid operator :" + op);
        }
    }

    @Override
    public StmtFunDeclaration visitFunDec(ComplexStaticAnalysisParser.FunDecContext ctx) {

        String funId = ctx.ID().getText();
        List<Parameter> params = new ArrayList<>();
        for (ComplexStaticAnalysisParser.ParameterContext pc: ctx.parameter()){
            params.add(visitParameter(pc));
        }
        StmtBlock body = visitBlock(ctx.block());

        return new StmtFunDeclaration(funId,params,body);
    }

    @Override
    public Value visitIntValue(ComplexStaticAnalysisParser.IntValueContext ctx) {
        return new ValueInt(ctx.INTEGER().getText());
    }

    @Override
    public Value visitIdValue(ComplexStaticAnalysisParser.IdValueContext ctx) {
        return new ValueId(ctx.ID().getText());
    }

    @Override
    public Value visitBoolValue(ComplexStaticAnalysisParser.BoolValueContext ctx) {

        return new ValueBool(ctx.getChild(0).getText());
    }

    @Override
    public StmtFunctionCall visitFunctioncall(ComplexStaticAnalysisParser.FunctioncallContext ctx) {

        String funId = ctx.ID().getText();
        List<Exp> params = new ArrayList<>();
        if(ctx.exp() != null){
            for (ComplexStaticAnalysisParser.ExpContext param: ctx.exp()){
                params.add(visitExp(param));
            }
        }

        return new StmtFunctionCall(funId, params);
    }

    @Override
    public Exp visitExpValue(ComplexStaticAnalysisParser.ExpValueContext ctx) {
        return new ValueExp(visitExp(ctx.exp()));
    }

    @Override
    public StmtIfThenElse visitIfthenelse(ComplexStaticAnalysisParser.IfthenelseContext ctx) {

        Exp condition = visitExp(ctx.exp());
        StmtBlock ifBranch = visitBlock(ctx.block(0));
        StmtBlock thenBranch = visitBlock(ctx.block(1));

        return new StmtIfThenElse(condition,ifBranch,thenBranch);
    }

    @Override
    public StmtPrint visitPrint(ComplexStaticAnalysisParser.PrintContext ctx) {
        Exp exp = visitExp(ctx.exp());
        return new StmtPrint(exp);
    }
}
