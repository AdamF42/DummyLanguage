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
            case("=="):
                return new EqCodeGenStrategy();
            case("!="):
                return new NotEqCodeGenStrategy();
            case(">"):
                return new GrCodeGenStrategy();
            case("<"):
                return new LrCodeGenStrategy();
            case(">="):
                return new GreCodeGenStrategy();
            case("<="):
                return new LreCodeGenStrategy();
            default:
                throw new IllegalArgumentException("Invalid operator :" + op);
        }

    }

}
