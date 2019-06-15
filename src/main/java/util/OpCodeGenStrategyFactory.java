package util;

public interface OpCodeGenStrategyFactory {
    public OpCodeGenStrategy GetOperationStrategy(String operator);
}
