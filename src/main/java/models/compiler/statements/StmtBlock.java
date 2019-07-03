package models.compiler.statements;

import models.compiler.Environment;
import util.SemanticError;
import models.compiler.types.Type;
import util.TypeCheckError;
import java.util.ArrayList;
import java.util.List;
import static util.Strings.*;

public class StmtBlock extends Stmt {

	private final List<Stmt> children;
	private int nl;

	public StmtBlock(List<Stmt> children) {
		this.children = children;
	}

	@Override
	public Type typeCheck() throws TypeCheckError {
		for (Stmt el : children)
			el.typeCheck();
		return null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment e) {

		e.openScope();
		this.nl = e.getNestingLevel();
		ArrayList<SemanticError> result = checkSemanticsWithNoOpenScope(e);
		e.closeScope();

		return result;
	}

	@Override
	public String codeGeneration() {

		StringBuilder result = new StringBuilder();
		result.append(push(FP)); // OLD FP
		result.append(AllocateVariables()); // LOAD VARIABLES
		result.append(push(FP)); // OlD FP
		result.append(move(FP,SP));
		if (nl==0)
			result.append(storeW(FP,"0",FP));
		result.append(codeGenerationForFunDec());
		result.append(pop());
		result.append(DeallocateVariables());
		result.append(assignTop(FP));
		result.append(pop());

		return result.toString();
	}


	String codeGenerationForFunDec() {

		StringBuilder result = new StringBuilder();
		for(Stmt child:children) {
			result.append(child.codeGeneration());
		}

		return result.toString();
	}

	ArrayList<SemanticError> checkSemanticsWithNoOpenScope(Environment e) {

		ArrayList<SemanticError> result = new ArrayList<>();
		for(Stmt child:children) {
			result.addAll(child.checkSemantics(e));
			this.addAllDeletions(child.getDeletions());
			this.addAllrwAccesses(child.getRwAccesses());
		}

		return result;
	}

	private String AllocateVariables(){

		StringBuilder result = new StringBuilder();
		int varCounter = 0;
		for (Stmt var: children) {
			if (var instanceof StmtVarDeclaration)
				varCounter++;
		}
		if (varCounter>0)
			result.append(addi(SP,SP,String.valueOf(-varCounter*4)));
		return result.toString();
	}

	//TODO: rifattorizza con AllocateVariables
	private String DeallocateVariables(){

		StringBuilder result = new StringBuilder();
		int varCounter = 0;
		for (Stmt var: children) {
			if (var instanceof StmtVarDeclaration)
				varCounter++;
		}
		if (varCounter>0)
			result.append(addi(SP,SP,String.valueOf(varCounter*4)));
		return result.toString();
	}
}
