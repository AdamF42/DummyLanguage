package util.operationCodeGenStrategy;

public interface OpCodeGenStrategyFactory {
    OpCodeGenStrategy GetOperationStrategy(String operator);
}
