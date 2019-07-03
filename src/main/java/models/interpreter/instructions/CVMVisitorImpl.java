package models.interpreter.instructions;

import models.interpreter.*;
import org.antlr.v4.runtime.tree.ParseTree;
import parser.CVMBaseVisitor;
import parser.CVMParser;

import java.util.ArrayList;
import java.util.List;

public class CVMVisitorImpl extends CVMBaseVisitor {


    @Override
    public Assembly visitAssembly(CVMParser.AssemblyContext ctx) {
        visitChildren(ctx);

        List<ElementBase> children = new ArrayList<>();

        for ( ParseTree childCtx: ctx.children) {
            children.add((ElementBase) visit(childCtx));
        }

        return new Assembly(children);
    }

    @Override
    public ElementBase visitPush(CVMParser.PushContext ctx) {
        return new Push(ctx.REGISTER().getSymbol().getText());
    }

    @Override
    public ElementBase visitPop(CVMParser.PopContext ctx) {
        return new Pop();
    }

    @Override
    public ElementBase visitAdd(CVMParser.AddContext ctx) {
        return new Add(ctx.REGISTER(0).getText(), ctx.REGISTER(1).getText(), ctx.REGISTER(2).getText());
    }

    @Override
    public ElementBase visitAddi(CVMParser.AddiContext ctx) {
        return new AddI(ctx.REGISTER(0).getText(),ctx.REGISTER(1).getText(),ctx.NUMBER().getText());
    }

    @Override
    public ElementBase visitSub(CVMParser.SubContext ctx) {
        return new Sub(ctx.REGISTER(0).getText(), ctx.REGISTER(1).getText(), ctx.REGISTER(2).getText());

    }

    @Override
    public ElementBase visitSubi(CVMParser.SubiContext ctx) {
        return new SubI(ctx.REGISTER(0).getText(),ctx.REGISTER(1).getText(),ctx.NUMBER().getText());
    }

    @Override
    public ElementBase visitMult(CVMParser.MultContext ctx) {
        return new Mult(ctx.REGISTER(0).getText(), ctx.REGISTER(1).getText(), ctx.REGISTER(2).getText());
    }

    @Override
    public ElementBase visitDiv(CVMParser.DivContext ctx) {
        return new Div(ctx.REGISTER(0).getText(), ctx.REGISTER(1).getText(), ctx.REGISTER(2).getText());
    }

    @Override
    public ElementBase visitMove(CVMParser.MoveContext ctx) {
        return new Move(ctx.REGISTER(0).getText(), ctx.REGISTER(1).getSymbol().getText());
    }

    @Override
    public ElementBase visitSw(CVMParser.SwContext ctx) {
        return new StoreW(ctx.REGISTER(0).getText(), ctx.NUMBER().getText(), ctx.REGISTER(1).getText());
    }

    @Override
    public ElementBase visitLw(CVMParser.LwContext ctx) {
        return new LoadW(ctx.REGISTER(0).getText(), ctx.NUMBER().getText(), ctx.REGISTER(1).getText());
    }

    @Override
    public ElementBase visitLi(CVMParser.LiContext ctx) {
        return new LoadI(ctx.REGISTER().getText(), ctx.NUMBER().getText());
    }

    @Override
    public ElementBase visitLabel(CVMParser.LabelContext ctx) {
        return new Label(ctx.LABEL().getText());
    }

    @Override
    public ElementBase visitB(CVMParser.BContext ctx) {
        return new Branch(ctx.LABEL().getText());
    }

    @Override
    public ElementBase visitBeq(CVMParser.BeqContext ctx) {
        return new BranchEq(ctx.REGISTER(0).getText(), ctx.REGISTER(1).getText(), ctx.LABEL().getText());
    }

    @Override
    public ElementBase visitBlr(CVMParser.BlrContext ctx) {
        return new BranchLess(ctx.REGISTER(0).getText(), ctx.REGISTER(1).getText(), ctx.LABEL().getText());

    }

    @Override
    public ElementBase visitBlre(CVMParser.BlreContext ctx) {
        return new BranchLessEq(ctx.REGISTER(0).getText(), ctx.REGISTER(1).getText(), ctx.LABEL().getText());
    }

    @Override
    public ElementBase visitBgr(CVMParser.BgrContext ctx) {
        return new BranchGreater(ctx.REGISTER(0).getText(), ctx.REGISTER(1).getText(), ctx.LABEL().getText());
    }

    @Override
    public ElementBase visitBgre(CVMParser.BgreContext ctx) {
        return new BranchGreaterEq(ctx.REGISTER(0).getText(), ctx.REGISTER(1).getText(), ctx.LABEL().getText());
    }

    @Override
    public ElementBase visitJal(CVMParser.JalContext ctx) {
        return new Jal(ctx.LABEL().getText());
    }

    @Override
    public ElementBase visitJr(CVMParser.JrContext ctx) {
        return new Jr(ctx.REGISTER().getText());
    }

    @Override
    public ElementBase visitPrint(CVMParser.PrintContext ctx) {
        return new Print();
    }

    @Override
    public ElementBase visitHalt(CVMParser.HaltContext ctx) {
        return null;
    }

    @Override
    public ElementBase visitTop(CVMParser.TopContext ctx) {
        return new Top(ctx.REGISTER().getText());
    }
}
