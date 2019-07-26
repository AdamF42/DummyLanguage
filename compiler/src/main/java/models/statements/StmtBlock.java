package models.statements;

import exeptions.TypeCheckException;
import models.Environment;
import models.types.Type;
import util.SemanticError;

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
	public Type typeCheck() throws TypeCheckException {
		for (Stmt el : children)
			el.typeCheck();
		return null;
	}

	@Override
	public List<SemanticError> checkSemantics(Environment e) {

		e.openScope();
		this.nl = e.getNestingLevel();
		ArrayList<SemanticError> result = checkSemanticsWithNoOpenScope(e);
		e.closeScope();

		return result;
	}

	@Override
	public String codeGeneration() {

		StringBuilder result = new StringBuilder();
		result.append(push(FP));
		result.append(handleVariablesAllocation(true));
		result.append(push(FP));
		result.append(move(FP,SP));
		if (nl==0)
			result.append(storeW(FP,"0",FP));
		result.append(codeGenerationForFunDec());
		result.append(pop());
		result.append(handleVariablesAllocation(false));
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

	String handleVariablesAllocation(boolean isAllocating){

		long varCounter = children.stream().filter(child -> child instanceof StmtVarDeclaration).count();

		if (varCounter < 1)
			return EMPTY;

		if (isAllocating)
			return addi(SP, SP, String.valueOf(-varCounter * 4));

		return addi(SP, SP, String.valueOf(varCounter * 4));
	}

}
