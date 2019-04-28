package models;


import parser.ComplexStaticAnalysisBaseVisitor;
import parser.ComplexStaticAnalysisParser;

import java.util.ArrayList;
import java.util.List;


public class VisitorImpl extends ComplexStaticAnalysisBaseVisitor<ElementBase> {

    @Override
    public ElementBase visitBlock(ComplexStaticAnalysisParser.BlockContext ctx) {
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
    public ElementBase visitAssignment(ComplexStaticAnalysisParser.AssignmentContext ctx) {

        //get id of variable
        String id = ctx.ID().getText();

        //get expression
        Exp exp = (Exp) visit(ctx.exp());

        //construct assignment expression
        StmtAssignment assign = new StmtAssignment(exp, id);
        return assign;
    }

    @Override
    public ElementBase visitDeletion(ComplexStaticAnalysisParser.DeletionContext ctx) {
        //construct delete expression with variable id
        StmtDelete delete = new StmtDelete(ctx.ID().getText());
        return delete;
    }

//    @Override
//    public T visitPrint(ComplexStaticAnalysisParser.PrintContext ctx) {
//        return visitChildren(ctx);
//    }

//    @Override
//    public T visitFunctioncall(ComplexStaticAnalysisParser.FunctioncallContext ctx) {
//        return visitChildren(ctx);
//    }
//
//    @Override
//    public T visitIfthenelse(ComplexStaticAnalysisParser.IfthenelseContext ctx) {
//        return visitChildren(ctx);
//    }


    @Override
    public ElementBase visitVarDec(ComplexStaticAnalysisParser.VarDecContext ctx) {
        // get the type
        Type type = visitType(ctx.type());

        // get the ID
        String id = ctx.ID().getText();

        // visit the expression
        ElementBase exp = visit(ctx.exp());

        //construct assignment expression
        StmtVarDeclaration vardec = new StmtVarDeclaration(type, id, exp);
        return vardec;

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

        if ( ctx.children.toString().contains("var")) {
            paramType.setReference(true);
        }

        return new Parameter(paramType, ctx.ID().getText());
    }

    @Override
    public ElementBase visitExp(ComplexStaticAnalysisParser.ExpContext ctx) {

        Term leftTerm = (Term) visit(ctx.left);

        Exp righExp = ctx.right != null ? (Exp) visit(ctx.right) : null;

        Exp result = new Exp(leftTerm, righExp);

        return result;
    }

    @Override
    public ElementBase visitTerm(ComplexStaticAnalysisParser.TermContext ctx) {

        Factor leftTerm = (Factor) visit(ctx.left);

        Term righExp = ctx.right != null ? (Term) visit(ctx.right) : null;

        Term result = new Term(leftTerm, righExp);

        return result;
    }
    @Override
    public ElementBase visitFactor(ComplexStaticAnalysisParser.FactorContext ctx) {

        ElementBase leftTerm = visit(ctx.left);

        ElementBase righExp = ctx.right != null ? visit(ctx.right) : null;

        Factor result = new Factor(leftTerm, righExp);

        return result;
    }

    @Override
    public ElementBase visitFunDec(ComplexStaticAnalysisParser.FunDecContext ctx) {

        String funId = ctx.ID().getText();

        List<Parameter> params = new ArrayList<>();
        for (ComplexStaticAnalysisParser.ParameterContext pc: ctx.parameter()){
            params.add(visitParameter(pc));
        }

        ElementBase body = visit(ctx.block());

        return new StmtFunDeclaration(funId,params,body);

    }

    @Override
    public ElementBase visitIntValue(ComplexStaticAnalysisParser.IntValueContext ctx) {
        return new ValueInt(Integer.parseInt(ctx.INTEGER().getText()));
    }

    @Override
    public ElementBase visitIdValue(ComplexStaticAnalysisParser.IdValueContext ctx) {
        return new ValueId(ctx.ID().getText());
    }

    @Override
    public ElementBase visitBoolValue(ComplexStaticAnalysisParser.BoolValueContext ctx) {
        return new ValueBool(ctx.getChild(0).getText());
    }

    @Override
    public ElementBase visitFunctioncall(ComplexStaticAnalysisParser.FunctioncallContext ctx) {

        String funId = ctx.ID().getText();

        List<ElementBase> params = new ArrayList<ElementBase>();
        if(ctx.exp() != null){
            for (ComplexStaticAnalysisParser.ExpContext param: ctx.exp()){
                params.add(visit(param));
            }
        }



        return new StmtFunctionCall(funId, params);
    }

    @Override
    public ElementBase visitExpValue(ComplexStaticAnalysisParser.ExpValueContext ctx) {
        return visitExp(ctx.exp());
    }

    @Override
    public ElementBase visitIfthenelse(ComplexStaticAnalysisParser.IfthenelseContext ctx) {

        ElementBase condition = visit(ctx.exp());

        ElementBase ifBranch = visit(ctx.block(0));

        ElementBase thenBranch = visit(ctx.block(1));

        return new StmtIfThenElse(condition,ifBranch,thenBranch);
    }
}
