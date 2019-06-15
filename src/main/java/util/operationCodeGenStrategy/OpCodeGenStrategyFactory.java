package util.operationCodeGenStrategy;

public interface OpCodeGenStrategyFactory {
    public OpCodeGenStrategy GetOperationStrategy(String operator);
}
