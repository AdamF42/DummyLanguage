package interpretermodels;

import parser.CVMParser;

import java.util.Arrays;
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
//        System.out.print(Arrays.toString(env.code) + "\n");
        for (Integer refAdd: env.getLabelRef().keySet()) {
            env.code[refAdd] = env.getLabelAdd().get(env.getLabelRef().get(refAdd));
        }
//        System.out.print(Arrays.toString(env.code) + "\n");

        env.code[env.i++] = CVMParser.HALT;
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName() + "\n";
    }
}
