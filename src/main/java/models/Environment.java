package models;

import java.util.HashMap;
import java.util.LinkedList;

public class Environment {

	private LinkedList<HashMap<String, STentry>> vtable = new LinkedList<>();
	private LinkedList<HashMap<String, STentry>> ftable = new LinkedList<>();
	private boolean insideFunction = false;
	private int nestingLevel = -1;
	private int offset = 0; //TODO: think about it

	public boolean isInsideFunctionDeclaration() {
		return insideFunction;
	}


	public void setInsideFunctionDeclaration(boolean insideFunction) {
		this.insideFunction = insideFunction;
	}


	/**
	 * Adds variable with the given id to existence	
	 * @param id
	 */
	public void addVariable(String id, STentry val) {
		vtable.peek().put(id, val);
		offset +=4;
	}

	public void addFunction(String id, STentry val) {
		ftable.peek().put(id, val);
	}

	/** 
	 * Inserts a new scope into the environment.
	 * When a scope is inserted old scope is clone so previous defined
	 * variables still exist
	 */
	public void openScope() {
		nestingLevel++;
		vtable.push(new HashMap<>());
		ftable.push(new HashMap<>());
	}


	/**
	 * Drops the current scope and returns to the outer scope
	 * removing all changes and additions done within this scope 
	 */
	public void closeScope(){
		nestingLevel--;
		vtable.pop();
		ftable.pop();
		offset = 0; //TODO: think about it
	}


	/**
	 * Given an id determines if the variable belongs to the environment
	 * this is to check the vtable from inner to outer looking for the variable
	 * @param id
	 */
	public boolean containsVariable(String id){
		
		for(HashMap<String, STentry> scope: vtable){
			if(scope.containsKey(id))
				return true;
		}
		return false;
	}

	public boolean containsFunction(String id){

		for(HashMap<String, STentry> scope: ftable){
			if(scope.containsKey(id))
				return true;
		}
		return false;
	}


	public boolean containsVariableLocal(String id){
		assert vtable.peek() != null;
		STentry scope = vtable.peek().get(id);
		return scope != null;
	}


	/**
	 * Check for variable
	 * @param id of the variable
	 * @return variable value, null if the variable doesnt exist
	 */
	public STentry getVariableValue(String id){
		for(HashMap<String, STentry> scope: vtable){
			if(scope.containsKey(id)){
				return scope.get(id);				
			}
		}
		return null;
	}

	public STentry getFunctionValue(String id){
		for(HashMap<String, STentry> scope: ftable){
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
		return vtable.peek().get(id);
	}


	public int getNestingLevel() {
		return this.nestingLevel;
	}


	public void setToBeDeletedOnFunCall(STentry entry) {
		entry.setToBeDeleted(true);
	}


	public boolean isInCurrentScope(int identifierNL){
		return identifierNL == nestingLevel;
	}

	public int getOffset() {
		return offset;
	}

}
