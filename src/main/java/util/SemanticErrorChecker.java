package util;

import models.stentry.FunStEntry;
import models.stentry.StEntry;
import models.stentry.VarStEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static util.Strings.*;

public class SemanticErrorChecker {

    public static final Function<StEntry, Boolean> IS_NULL = Objects::isNull;
    public static final Function<StEntry, Boolean> FUN_IS_DELETED = (entry) -> entry instanceof FunStEntry && entry.isDeleted();
    public static final Function<StEntry, Boolean> ALREADY_DECLARED = (entry) -> entry!=null && !entry.isDeleted();
    public static final Function<StEntry, Boolean> VAR_IS_DELETED = (entry) -> entry instanceof VarStEntry && entry.isDeleted();


    private static final Function<String, Function<String, SemanticError>> ERROR_TEMPLATE = base_error -> id -> new SemanticError(base_error + id);

    public static Map<Function<StEntry, Boolean>, Function<String, SemanticError>> VALIDATION_ERRORS;

    static {
        VALIDATION_ERRORS = new HashMap<>();
        VALIDATION_ERRORS.put(IS_NULL, ERROR_TEMPLATE.apply(ERROR_IDENTIFIER_DOESNT_EXIST));
        VALIDATION_ERRORS.put(FUN_IS_DELETED, ERROR_TEMPLATE.apply(ERROR_FUNCTION_HAS_BEEN_DELETED));
        VALIDATION_ERRORS.put(VAR_IS_DELETED, ERROR_TEMPLATE.apply(ERROR_VARIABLE_HAS_BEEN_DELETED));
        VALIDATION_ERRORS.put(ALREADY_DECLARED, ERROR_TEMPLATE.apply(ERROR_ALREADY_DECLARED_IDENTIFIER));
    }
}
