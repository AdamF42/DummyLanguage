package models.statements;

import models.Environment;
import models.stentry.STentry;
import util.SemanticError;
import models.types.Type;
import util.Strings;

import java.util.ArrayList;

public class StmtDelete extends Stmt {

	private String id;


	public StmtDelete(String id) {
		this.id = id;
	}

	@Override
	public Type typeCheck() {
		return null;
	}

	// TODO: refactor this horrible code...
	@Override
	public ArrayList<SemanticError> checkSemantics(Environment e) {

		ArrayList<SemanticError> result = new ArrayList<>();
		STentry variable = e.getVariableValue(id);
		STentry idEntry = variable == null ? e.getFunctionValue(id) : variable;

		if(idEntry == null) {
			result.add(new SemanticError(Strings.ERROR_VARIABLE_DOESNT_EXIST + id));
		}
		else if (idEntry.isDeleted()){
			result.add(new SemanticError(Strings.ERROR_VARIABLE_HAS_BEEN_DELETED + id));
		}
		else {
			if (e.isInCurrentScope(idEntry.getNestinglevel())){
				idEntry.setDeleted(true);
			}
			else {
				// check if I'm inside a scope but I'm trying to delete something out
				// Need to distinguish if i'm in a function or not
				if(e.isInsideFunctionDeclaration()){
					e.setToBeDeletedOnFunCall(idEntry);
				} else {
					idEntry.setDeleted(true);
				}
			}
			this.addDeletion(idEntry);
		}
		return result;
	}

	@Override
	public String codeGeneration() {
		return Strings.EMPTY;
	}

}
