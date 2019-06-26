package models.statements;

import models.Environment;
import util.SemanticError;
import models.types.Type;
import util.TypeCheckError;

import java.util.ArrayList;
import java.util.List;

import static util.Strings.*;

public class StmtBlock extends Stmt {

	private final List<Stmt> children;

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
		ArrayList<SemanticError> result = checkSemanticsWithNoOpenScope(e);
		e.closeScope();

		return result;
	}

	@Override
	public String codeGeneration() {

		StringBuilder result = new StringBuilder();
		result.append(push(FP));
		result.append(AllocateVariables());
		result.append(move(FP,SP));
		for(Stmt child:children) {
			result.append(child.codeGeneration());
		}
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
		result.append(loadI(TMP,"0"));
		for (Stmt var: children) {
			if (var instanceof StmtVarDeclaration)
			result.append(push(TMP));
		}
		return result.toString();
	}

	private String DeallocateVariables(){

		StringBuilder result = new StringBuilder();
		for (Stmt var: children) {
			if (var instanceof StmtVarDeclaration)
				result.append(pop());
		}
		return result.toString();
	}
}
