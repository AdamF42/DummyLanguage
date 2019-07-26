package models.statements;

import com.google.common.collect.Lists;
import models.Environment;
import models.stentry.StEntry;
import models.types.Type;
import util.SemanticError;
import util.Strings;
import java.util.*;
import java.util.function.Function;
import static util.SemanticErrorChecker.*;


public class StmtDelete extends Stmt {

	private final String id;

    private static final List<Function<StEntry, Boolean>> CHECKS = Arrays.asList(IS_NULL, VAR_IS_DELETED, FUN_IS_DELETED);

	public StmtDelete(String id) {
		this.id = id;
	}

	@Override
	public Type typeCheck() {
		return null;
	}

	@Override
	public List<SemanticError> checkSemantics(Environment e) {

		StEntry idEntry = Optional.ofNullable((StEntry)e.getVariableValue(id)).orElse(e.getFunctionValue(id));
		for (Function<StEntry, Boolean> check: CHECKS) {
			if (check.apply(idEntry))
				return Lists.newArrayList(VALIDATION_ERRORS.get(check).apply(id));
		}

		if (e.isInCurrentScope(idEntry.getNestinglevel())){
			idEntry.setDeleted(true);
		}
		else {
			if(e.isInsideFunctionDeclaration()){
				e.setToBeDeletedOnFunCall(idEntry);
			} else {
				idEntry.setDeleted(true);
			}
		}
		this.addDeletion(idEntry);

		return new ArrayList<>();
	}


	@Override
	public String codeGeneration() {
		return Strings.EMPTY;
	}

}
