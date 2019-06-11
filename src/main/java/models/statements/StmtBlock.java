package models.statements;

import models.Environment;
import util.SemanticError;
import models.types.Type;
import util.Strings;
import util.TypeCheckError;

import java.util.ArrayList;
import java.util.List;

public class StmtBlock extends Stmt {
	private List<Stmt> children;

	/**
	 * Creates a new block
	 * @param children: the list of direct children elements of the block
	 */
	public StmtBlock(List<Stmt> children) {
		this.children = children;

	}

	@Override
	public Type typeCheck() throws TypeCheckError {

		if(children!=null) {
			for (Stmt el : children)
				el.typeCheck();
		}
		return null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment e) {
		//create scope for inner elements
		e.openScope();

		ArrayList<SemanticError> result = checkSemanticsWithNoOpenScope(e);

		//close scope for this block
		e.closeScope();

		return result;
	}

	@Override
	public String codeGeneration() {
		String result = Strings.EMPTY;
		if(children!=null){
			for(Stmt child:children) {
				result += child.codeGeneration();
			}
		}
		return result;
	}

	public ArrayList<SemanticError> checkSemanticsWithNoOpenScope(Environment e) {

		//initialize result variable
		ArrayList<SemanticError> result = new ArrayList<SemanticError>();

		//check children semantics
		if(children!=null)
			for(Stmt child:children) {
				result.addAll(child.checkSemantics(e));
				this.addAllDeletions(child.getDeletions());
				this.addAllrwAccesses(child.getRwAccesses());
			}

		return result;
	}


}
