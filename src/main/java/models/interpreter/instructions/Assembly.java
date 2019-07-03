package models.interpreter.instructions;

import models.interpreter.ElementBase;
import models.interpreter.CodeMemory;
import parser.CVMParser;

import java.util.List;

public class Assembly extends ElementBase {

    private final List<ElementBase> children;

    public Assembly(List<ElementBase> children) {
        this.children=children;
    }

    @Override
    public void loadCode(CodeMemory env) {

        for (ElementBase child: this.children) {
//            int from = env.i;
            child.loadCode(env);
//            int to = env.i;
//            System.out.println(child.getClass().getSimpleName());
//            for (int i = from; i < to; i++)
//                System.out.println(i +" : "+env.code[i]);
//            System.out.println(env.i);

        }

        for (Integer refAdd: env.getLabelRef().keySet()) {
            env.code[refAdd] = env.getLabelAdd().get(env.getLabelRef().get(refAdd));
//            System.out.print(refAdd +" patched with: " + env.getLabelAdd().get(env.getLabelRef().get(refAdd)) + "\n");
        }

        env.code[env.i++] = CVMParser.HALT;


    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName() + "\n";
    }
}
