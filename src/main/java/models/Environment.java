package models;

import models.stentry.FunStEntry;
import models.stentry.StEntry;
import models.stentry.VarStEntry;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class Environment {

	private LinkedList<HashMap<String, VarStEntry>> vtable = new LinkedList<>();
	private LinkedList<HashMap<String, FunStEntry>> ftable = new LinkedList<>();
	private boolean insideFunction = false;
	private int nestingLevel = -1;
	private int offset = 0;

	public boolean isInsideFunctionDeclaration() {
		return insideFunction;
	}

	public void setInsideFunctionDeclaration(boolean insideFunction) {
		this.insideFunction = insideFunction;
	}

	public void addVariable(String id, VarStEntry val) {
		Objects.requireNonNull(vtable.peek()).put(id, val);
		offset +=4;
	}

	public void addFunction(String id, FunStEntry val) {
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

		for(HashMap<String, VarStEntry> scope: vtable){
			if(scope.containsKey(id))
				return true;
		}
		return false;
	}

	public boolean containsFunction(String id){

		for(HashMap<String, FunStEntry> scope: ftable){
			if(scope.containsKey(id))
				return true;
		}
		return false;
	}

	public boolean containsVariableLocal(String id){
		assert vtable.peek() != null;
		StEntry scope = vtable.peek().get(id);
		return scope != null;
	}

	public VarStEntry getVariableValue(String id){
		for(HashMap<String, VarStEntry> scope: vtable){
			if(scope.containsKey(id)){
				return scope.get(id);				
			}
		}
		return null;
	}

	public FunStEntry getFunctionValue(String id){
		for(HashMap<String, FunStEntry> scope: ftable){
			if(scope.containsKey(id)){
				return scope.get(id);
			}
		}
		return null;
	}

	public StEntry getVariableValueLocal(String id){
		return Objects.requireNonNull(vtable.peek()).get(id);
	}

	public int getNestingLevel() {
		return this.nestingLevel;
	}

	public void setToBeDeletedOnFunCall(StEntry entry) {
		entry.setToBeDeleted(true);
	}

	public boolean isInCurrentScope(int identifierNL){
		return identifierNL == nestingLevel;
	}

	public int getOffset() {
		return offset;
	}

}
