package util.operationCodeGenStrategy;


public class OpCodeGenStrategyFactoryImpl implements OpCodeGenStrategyFactory {

    @Override
    public OpCodeGenStrategy GetOperationStrategy(String op) {

        switch (op) {
            case "+":
                return new AddCodeGenStrategy();
            case "-":
                return new SubCodeGenStrategy();
            case "*":
                return new MultCodeGenStrategy();
            case "/":
                return new DivCodeGenStrategy();
            case("&&"):
                return new AndCodeGenStrategy();
            case("||"):
                return new OrCodeGenStrategy();
            default:
                throw new IllegalArgumentException("Invalid operator :" + op);
        }

    }

}
