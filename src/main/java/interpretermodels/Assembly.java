package interpretermodels;

import parser.CVMParser;

import java.util.List;

public class Assembly extends ElementBase{

    private final List<ElementBase> children;

    public Assembly(List<ElementBase> children) {
        this.children=children;
    }

    @Override
    public void loadCode(InterpreterEnv env) {

        for (ElementBase child: this.children) {
            child.loadCode(env);
        }

        for (Integer refAdd: env.getLabelRef().keySet()) {
            //String test = env.getLabelRef().get(refAdd);
            //Integer test1 = env.getLabelAdd().get(test);
            env.code[refAdd] = env.getLabelAdd().get(env.getLabelRef().get(refAdd));
        }

        env.code[env.i++] = CVMParser.HALT;
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName() + "\n";
    }
}
