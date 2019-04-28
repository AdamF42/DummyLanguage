package models;

import util.Strings;

import java.util.ArrayList;

public class StmtDelete extends Stmt {

	private String id;

	/**
	 * Creates a delete statement
	 * @param id the variable we want to delete
	 */
	public StmtDelete(String id) {
		this.id = id;
	}

	@Override
    public Type typeCheck() {
		return null;
	}

	/*
	 * Checks if the variable in use exists. if it doesn't then add an error, 
	 * if it does then remove it from the current scope
	 * @see models.SimpleElementBase#CheckSemantics(models.Environment)
	 */
	@Override
	public ArrayList<SemanticError> checkSemantics(Environment e) {
		ArrayList<SemanticError> result = new ArrayList<>();
		
		// check for the variable in all scopes
		if(!e.containsVariable(id)) {
			result.add(new SemanticError(Strings.ERROR_VARIABLE_DOESNT_EXIST + id));
		} else if (e.getVariableValue(id).getType().isDeleted()){
			result.add(new SemanticError(Strings.ERROR_VARIABLE_HAS_BEEN_DELETED + id));
		} else {
			STentry identifierEntry = e.getVariableValue(id);
			if (e.isInCurrentScope(identifierEntry.getNestinglevel())){
					identifierEntry.getType().setDeleted(true);
			} else {
				// check if I'm inside a scope but I'm trying to delete something out
				// Need to distinguish if i'm in a function or not
				if(e.isInsideFunctionDeclaration()){
					e.setToBeDeletedOnFunCall(identifierEntry);
				} else {
					identifierEntry.getType().setDeleted(true);
				}
			}
			this.addDeletion(identifierEntry);
		}
		return result;
	}

}
