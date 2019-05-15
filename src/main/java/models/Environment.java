package models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Environment {

	//contains the stack of scopes. the last one is always the current active scope
	//this linked list is used as a stack with LIFO behavior
	private LinkedList<HashMap<String, STentry>> scopes = new LinkedList<>();
	private boolean insideFunction = false;
	private int functionNestingLevel = -1;
	private int nestingLevel = -1;

	public boolean isInsideFunctionDeclaration() {
		return insideFunction;
	}

	public void setInsideFunctionDeclaration(boolean insideFunction) {
		this.insideFunction = insideFunction;
	}


	public void setFunctionNestingLevel(int functionNestingLevel) {
		this.functionNestingLevel = functionNestingLevel;
	}

	/**
	 * Adds variable with the given id to existence	
	 * @param id
	 */
	public void addVariable(String id, STentry val) {
		scopes.peek().put(id, val);
	}

	/** 
	 * Inserts a new scope into the environment.
	 * When a scope is inserted old scope is clone so previous defined
	 * variables still exist
	 */
	public void openScope() {
		nestingLevel++;
		scopes.push(new HashMap<>());
	}

	/**
	 * Drops the current scope and returns to the outer scope
	 * removing all changes and additions done within this scope 
	 */
	public void closeScope(){
		nestingLevel--;
		scopes.pop();
	}
	
	/**
	 * Given an id determines if the variable belongs to the environment
	 * this is to check the scopes from inner to outer looking for the variable
	 * @param id
	 */
	public boolean containsVariable(String id){
		
		for(HashMap<String, STentry> scope:scopes){
			if(scope.containsKey(id))
				return true;
		}
		return false;
	}

	public boolean containsVariableLocal(String id){
		assert scopes.peek() != null;
		STentry scope = scopes.peek().get(id);
		return scope != null;
	}


	/**
	 * Check for variable
	 * @param id of the variable
	 * @return variable value, null if the variable doesnt exist
	 */
	public STentry getVariableValue(String id){
		for(HashMap<String, STentry> scope:scopes){
			if(scope.containsKey(id)){
				return scope.get(id);				
			}
		}
		return null;
	}
	
	
	/**
	 * Check local scope for variable
	 * @param id of the variable
	 * @return variable value in current scope, null otherwise
	 */
	public STentry getVariableValueLocal(String id){
		return scopes.peek().get(id);
	}

	public int getNestingLevel() {
		return this.nestingLevel;
	}

	@Override
	public String toString() {
		return "Environment{" +
				"scopes=" + scopes +
				", nestingLevel=" + nestingLevel +
				'}';
	}


	public void setToBeDeletedOnFunCall(STentry entry) {
		entry.setToBeDeleted(true);
	}

	public boolean isInCurrentScope(int identifierNL){
		return identifierNL == nestingLevel;
	}

}
