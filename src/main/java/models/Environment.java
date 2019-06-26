package models;

import models.stentry.FunSTentry;
import models.stentry.STentry;
import models.stentry.VarSTentry;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class Environment {

	private LinkedList<HashMap<String, VarSTentry>> vtable = new LinkedList<>();
	private LinkedList<HashMap<String, FunSTentry>> ftable = new LinkedList<>();
	private boolean insideFunction = false;
	private int nestingLevel = -1;
	private int offset = 0;

	public boolean isInsideFunctionDeclaration() {
		return insideFunction;
	}

	public void setInsideFunctionDeclaration(boolean insideFunction) {
		this.insideFunction = insideFunction;
	}

	public void addVariable(String id, VarSTentry val) {
		Objects.requireNonNull(vtable.peek()).put(id, val);
		offset +=4;
	}

	public void addFunction(String id, FunSTentry val) {
		Objects.requireNonNull(ftable.peek()).put(id, val);
	}

	public void openScope() {
		nestingLevel++;
		vtable.push(new HashMap<>());
		ftable.push(new HashMap<>());
	}

	public void closeScope(){
		nestingLevel--;
		vtable.pop();
		ftable.pop();
		offset = 0;
	}

	public boolean containsVariable(String id){

		if(id==null) return false;

		for(HashMap<String, VarSTentry> scope: vtable){
			if(scope.containsKey(id))
				return true;
		}
		return false;
	}

	public boolean containsFunction(String id){

		for(HashMap<String, FunSTentry> scope: ftable){
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

	public VarSTentry getVariableValue(String id){
		for(HashMap<String, VarSTentry> scope: vtable){
			if(scope.containsKey(id)){
				return scope.get(id);				
			}
		}
		return null;
	}

	public FunSTentry getFunctionValue(String id){
		for(HashMap<String, FunSTentry> scope: ftable){
			if(scope.containsKey(id)){
				return scope.get(id);
			}
		}
		return null;
	}

	public STentry getVariableValueLocal(String id){
		return Objects.requireNonNull(vtable.peek()).get(id);
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
