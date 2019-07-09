// Generated from /home/adamf42/Projects/Esercizio1/src/main/java/parser/CVM.g4 by ANTLR 4.7.2
package parser;

import java.util.HashMap;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CVMParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CVMVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CVMParser#assembly}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssembly(CVMParser.AssemblyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code push}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPush(CVMParser.PushContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pop}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPop(CVMParser.PopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code add}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdd(CVMParser.AddContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addi}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddi(CVMParser.AddiContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sub}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSub(CVMParser.SubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subi}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubi(CVMParser.SubiContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mult}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMult(CVMParser.MultContext ctx);
	/**
	 * Visit a parse tree produced by the {@code div}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDiv(CVMParser.DivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code move}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMove(CVMParser.MoveContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sw}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSw(CVMParser.SwContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lw}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLw(CVMParser.LwContext ctx);
	/**
	 * Visit a parse tree produced by the {@code li}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLi(CVMParser.LiContext ctx);
	/**
	 * Visit a parse tree produced by the {@code label}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabel(CVMParser.LabelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code b}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitB(CVMParser.BContext ctx);
	/**
	 * Visit a parse tree produced by the {@code beq}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBeq(CVMParser.BeqContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blr}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlr(CVMParser.BlrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blre}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlre(CVMParser.BlreContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bgr}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBgr(CVMParser.BgrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bgre}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBgre(CVMParser.BgreContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jal}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJal(CVMParser.JalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jr}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJr(CVMParser.JrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code print}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(CVMParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code top}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTop(CVMParser.TopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code halt}
	 * labeled alternative in {@link CVMParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHalt(CVMParser.HaltContext ctx);
}