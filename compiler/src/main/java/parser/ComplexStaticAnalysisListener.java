// Generated from /home/adamf42/Projects/Esercizio1/src/main/java/parser/ComplexStaticAnalysis.g4 by ANTLR 4.7.2
package parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ComplexStaticAnalysisParser}.
 */
public interface ComplexStaticAnalysisListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ComplexStaticAnalysisParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(ComplexStaticAnalysisParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ComplexStaticAnalysisParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(ComplexStaticAnalysisParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ComplexStaticAnalysisParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(ComplexStaticAnalysisParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ComplexStaticAnalysisParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(ComplexStaticAnalysisParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ComplexStaticAnalysisParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(ComplexStaticAnalysisParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ComplexStaticAnalysisParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(ComplexStaticAnalysisParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ComplexStaticAnalysisParser#deletion}.
	 * @param ctx the parse tree
	 */
	void enterDeletion(ComplexStaticAnalysisParser.DeletionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ComplexStaticAnalysisParser#deletion}.
	 * @param ctx the parse tree
	 */
	void exitDeletion(ComplexStaticAnalysisParser.DeletionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ComplexStaticAnalysisParser#print}.
	 * @param ctx the parse tree
	 */
	void enterPrint(ComplexStaticAnalysisParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by {@link ComplexStaticAnalysisParser#print}.
	 * @param ctx the parse tree
	 */
	void exitPrint(ComplexStaticAnalysisParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by {@link ComplexStaticAnalysisParser#functioncall}.
	 * @param ctx the parse tree
	 */
	void enterFunctioncall(ComplexStaticAnalysisParser.FunctioncallContext ctx);
	/**
	 * Exit a parse tree produced by {@link ComplexStaticAnalysisParser#functioncall}.
	 * @param ctx the parse tree
	 */
	void exitFunctioncall(ComplexStaticAnalysisParser.FunctioncallContext ctx);
	/**
	 * Enter a parse tree produced by {@link ComplexStaticAnalysisParser#ifthenelse}.
	 * @param ctx the parse tree
	 */
	void enterIfthenelse(ComplexStaticAnalysisParser.IfthenelseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ComplexStaticAnalysisParser#ifthenelse}.
	 * @param ctx the parse tree
	 */
	void exitIfthenelse(ComplexStaticAnalysisParser.IfthenelseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varDec}
	 * labeled alternative in {@link ComplexStaticAnalysisParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterVarDec(ComplexStaticAnalysisParser.VarDecContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varDec}
	 * labeled alternative in {@link ComplexStaticAnalysisParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitVarDec(ComplexStaticAnalysisParser.VarDecContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funDec}
	 * labeled alternative in {@link ComplexStaticAnalysisParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterFunDec(ComplexStaticAnalysisParser.FunDecContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funDec}
	 * labeled alternative in {@link ComplexStaticAnalysisParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitFunDec(ComplexStaticAnalysisParser.FunDecContext ctx);
	/**
	 * Enter a parse tree produced by {@link ComplexStaticAnalysisParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(ComplexStaticAnalysisParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ComplexStaticAnalysisParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(ComplexStaticAnalysisParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ComplexStaticAnalysisParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(ComplexStaticAnalysisParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link ComplexStaticAnalysisParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(ComplexStaticAnalysisParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link ComplexStaticAnalysisParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExp(ComplexStaticAnalysisParser.ExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link ComplexStaticAnalysisParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExp(ComplexStaticAnalysisParser.ExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link ComplexStaticAnalysisParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(ComplexStaticAnalysisParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link ComplexStaticAnalysisParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(ComplexStaticAnalysisParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link ComplexStaticAnalysisParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(ComplexStaticAnalysisParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ComplexStaticAnalysisParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(ComplexStaticAnalysisParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intValue}
	 * labeled alternative in {@link ComplexStaticAnalysisParser#value}.
	 * @param ctx the parse tree
	 */
	void enterIntValue(ComplexStaticAnalysisParser.IntValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intValue}
	 * labeled alternative in {@link ComplexStaticAnalysisParser#value}.
	 * @param ctx the parse tree
	 */
	void exitIntValue(ComplexStaticAnalysisParser.IntValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolValue}
	 * labeled alternative in {@link ComplexStaticAnalysisParser#value}.
	 * @param ctx the parse tree
	 */
	void enterBoolValue(ComplexStaticAnalysisParser.BoolValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolValue}
	 * labeled alternative in {@link ComplexStaticAnalysisParser#value}.
	 * @param ctx the parse tree
	 */
	void exitBoolValue(ComplexStaticAnalysisParser.BoolValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expValue}
	 * labeled alternative in {@link ComplexStaticAnalysisParser#value}.
	 * @param ctx the parse tree
	 */
	void enterExpValue(ComplexStaticAnalysisParser.ExpValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expValue}
	 * labeled alternative in {@link ComplexStaticAnalysisParser#value}.
	 * @param ctx the parse tree
	 */
	void exitExpValue(ComplexStaticAnalysisParser.ExpValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idValue}
	 * labeled alternative in {@link ComplexStaticAnalysisParser#value}.
	 * @param ctx the parse tree
	 */
	void enterIdValue(ComplexStaticAnalysisParser.IdValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idValue}
	 * labeled alternative in {@link ComplexStaticAnalysisParser#value}.
	 * @param ctx the parse tree
	 */
	void exitIdValue(ComplexStaticAnalysisParser.IdValueContext ctx);
}