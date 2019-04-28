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
		ArrayList<SemanticError> result = new ArrayList<SemanticError>();
		
		//check for the variable
		if(!e.containsVariable(id))
			result.add(new SemanticError(Strings.ERROR_VARIABLE_DOESNT_EXIST + id));
		
		//if the variable does exist then delete it from the environment
		else
			e.deleteVariable(id);
		
		return result;
	}

}
