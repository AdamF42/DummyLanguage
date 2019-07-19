// Generated from /home/adamf42/Projects/Esercizio1/src/main/java/parser/CVM.g4 by ANTLR 4.7.2
package parser;

import java.util.HashMap;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CVMParser}.
 */
public interface CVMListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CVMParser#assembly}.
	 * @param ctx the parse tree
	 */
	void enterAssembly(CVMParser.AssemblyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CVMParser#assembly}.
	 * @param ctx the parse tree
	 */
	void exitAssembly(CVMParser.AssemblyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code push}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterPush(CVMParser.PushContext ctx);
	/**
	 * Exit a parse tree produced by the {@code push}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitPush(CVMParser.PushContext ctx);
	/**
	 * Enter a parse tree produced by the {@code pop}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterPop(CVMParser.PopContext ctx);
	/**
	 * Exit a parse tree produced by the {@code pop}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitPop(CVMParser.PopContext ctx);
	/**
	 * Enter a parse tree produced by the {@code add}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterAdd(CVMParser.AddContext ctx);
	/**
	 * Exit a parse tree produced by the {@code add}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitAdd(CVMParser.AddContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addi}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterAddi(CVMParser.AddiContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addi}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitAddi(CVMParser.AddiContext ctx);
	/**
	 * Enter a parse tree produced by the {@code sub}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterSub(CVMParser.SubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code sub}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitSub(CVMParser.SubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subi}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterSubi(CVMParser.SubiContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subi}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitSubi(CVMParser.SubiContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mult}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterMult(CVMParser.MultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mult}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitMult(CVMParser.MultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code div}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterDiv(CVMParser.DivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code div}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitDiv(CVMParser.DivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code move}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterMove(CVMParser.MoveContext ctx);
	/**
	 * Exit a parse tree produced by the {@code move}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitMove(CVMParser.MoveContext ctx);
	/**
	 * Enter a parse tree produced by the {@code sw}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterSw(CVMParser.SwContext ctx);
	/**
	 * Exit a parse tree produced by the {@code sw}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitSw(CVMParser.SwContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lw}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterLw(CVMParser.LwContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lw}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitLw(CVMParser.LwContext ctx);
	/**
	 * Enter a parse tree produced by the {@code li}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterLi(CVMParser.LiContext ctx);
	/**
	 * Exit a parse tree produced by the {@code li}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitLi(CVMParser.LiContext ctx);
	/**
	 * Enter a parse tree produced by the {@code label}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterLabel(CVMParser.LabelContext ctx);
	/**
	 * Exit a parse tree produced by the {@code label}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitLabel(CVMParser.LabelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code b}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterB(CVMParser.BContext ctx);
	/**
	 * Exit a parse tree produced by the {@code b}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitB(CVMParser.BContext ctx);
	/**
	 * Enter a parse tree produced by the {@code beq}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterBeq(CVMParser.BeqContext ctx);
	/**
	 * Exit a parse tree produced by the {@code beq}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitBeq(CVMParser.BeqContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blr}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterBlr(CVMParser.BlrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blr}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitBlr(CVMParser.BlrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blre}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterBlre(CVMParser.BlreContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blre}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitBlre(CVMParser.BlreContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bgr}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterBgr(CVMParser.BgrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bgr}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitBgr(CVMParser.BgrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bgre}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterBgre(CVMParser.BgreContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bgre}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitBgre(CVMParser.BgreContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jal}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterJal(CVMParser.JalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jal}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitJal(CVMParser.JalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jr}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterJr(CVMParser.JrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jr}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitJr(CVMParser.JrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code print}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterPrint(CVMParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code print}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitPrint(CVMParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code top}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterTop(CVMParser.TopContext ctx);
	/**
	 * Exit a parse tree produced by the {@code top}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitTop(CVMParser.TopContext ctx);
	/**
	 * Enter a parse tree produced by the {@code halt}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterHalt(CVMParser.HaltContext ctx);
	/**
	 * Exit a parse tree produced by the {@code halt}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitHalt(CVMParser.HaltContext ctx);
}