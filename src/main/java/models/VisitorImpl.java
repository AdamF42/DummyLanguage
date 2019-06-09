package models;


import parser.ComplexStaticAnalysisBaseVisitor;
import parser.ComplexStaticAnalysisParser;

import java.util.ArrayList;
import java.util.List;


public class VisitorImpl extends ComplexStaticAnalysisBaseVisitor<ElementBase> {

    @Override
    public StmtBlock visitBlock(ComplexStaticAnalysisParser.BlockContext ctx) {
        //list for saving children statements
        List<Stmt> children = new ArrayList<Stmt>();

        //visit each children
        if (ctx != null) {
            for (ComplexStaticAnalysisParser.StatementContext stmtCtx : ctx.statement())
                children.add((Stmt) visit(stmtCtx));
        }

        //return the constructed block statement expression
        return new StmtBlock(children);
    }

    @Override
    public ElementBase visitStatement(ComplexStaticAnalysisParser.StatementContext ctx) {
        //visit the first child, this works for every case
        return visit(ctx.getChild(0));
    }

    @Override
    public StmtAssignment visitAssignment(ComplexStaticAnalysisParser.AssignmentContext ctx) {

        //get id of variable
        String id = ctx.ID().getText();

        //get expression
        Exp exp = (Exp) visit(ctx.exp());

        //construct assignment expression
        return new StmtAssignment(exp, id);
    }

    @Override
    public StmtDelete visitDeletion(ComplexStaticAnalysisParser.DeletionContext ctx) {
        //construct delete expression with variable id
        return new StmtDelete(ctx.ID().getText());
    }


    @Override
    public StmtVarDeclaration visitVarDec(ComplexStaticAnalysisParser.VarDecContext ctx) {
        // get the type
        Type type = visitType(ctx.type());

        // get the ID
        String id = ctx.ID().getText();

        // visit the expression
        Exp exp = visitExp(ctx.exp());

        //construct assignment expression
        return new StmtVarDeclaration(type, id, exp);

    }

    @Override
    public TypeReferenceable visitType(ComplexStaticAnalysisParser.TypeContext ctx) {
        String type = ctx.getText();
        TypeReferenceable typeNode;
        if (type.equals("int")) {
            typeNode = new TypeInt();
        } else if (type.equals("bool")) {
            typeNode = new TypeBool();
        } else throw new IllegalArgumentException("Unsupported type: " + type);
        return typeNode;
    }

    @Override
    public Parameter visitParameter(ComplexStaticAnalysisParser.ParameterContext ctx) {

        TypeReferenceable paramType = visitType(ctx.type());
        // TODO: do not use contains var because it do not allow to call a variable with varExample
        if ( ctx.children.toString().contains("var")) {
            paramType.setReference(true);
        }

        return new Parameter(paramType, ctx.ID().getText());
    }

    @Override
    public Exp visitExp(ComplexStaticAnalysisParser.ExpContext ctx) {

        Term leftTerm = (Term) visit(ctx.left);

        Exp righExp = ctx.right != null ? (Exp) visit(ctx.right) : null;

        String op = ctx.getText().contains("+") ? "+" : (ctx.getText().contains("-")?"-":null);

        return new Exp(leftTerm, righExp, op);
    }


    @Override
    public Term visitTerm(ComplexStaticAnalysisParser.TermContext ctx) {

        Factor leftTerm = visitFactor(ctx.left);

        Term righExp = ctx.right != null ? visitTerm(ctx.right) : null;

        String op = ctx.getText().contains("*") ? "*" : (ctx.getText().contains("/")?"/":null);

        return new Term(leftTerm, righExp, op);
    }
    @Override
    public Factor visitFactor(ComplexStaticAnalysisParser.FactorContext ctx) {

        Exp leftTerm =  (Exp) visit(ctx.left);

        Exp righExp = ctx.right != null ? (Exp) visit(ctx.right) : null;

        String op = ctx.op != null ? ctx.op.getText() : null;

        return new Factor(leftTerm, righExp, op);
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
        String line = String.valueOf(ctx.ID().getSymbol().getLine());
        String charPos = String.valueOf(ctx.ID().getSymbol().getCharPositionInLine());
        return new ValueId(ctx.ID().getText(), line, charPos);
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
        return visitExp(ctx.exp());
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
