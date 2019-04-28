package models;

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

	public ArrayList<SemanticError> checkSemanticsWithNoOpenScope(Environment e) {

		//initialize result variable
		ArrayList<SemanticError> result = new ArrayList<SemanticError>();

		//check children semantics
		if(children!=null)
			for(Stmt el:children)
				result.addAll(el.checkSemantics(e));

		return result;
	}

}
