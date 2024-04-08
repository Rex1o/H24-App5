import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DescenteRecursiveTest {
    @Test
    public void Validation1() {
        String test = "( U_x - V_y ) * W_z / 35";
        DescenteRecursive dr;
        dr = new DescenteRecursive(test, false);
        ElemAST root = dr.AnalSynt();
        String toWriteLect = "";
        String toWriteEval = "";
        String toWritePostFix = "";

        toWriteLect += "Lecture de l'AST trouve : " + root.LectAST() + "\n";
        System.out.println(toWriteLect);

        toWritePostFix += "Postfix de l'AST trouve : " + root.PostFix() + "\n";
        System.out.println(toWritePostFix);

        toWriteEval += "Evaluation de l'AST trouve : " + root.EvalAST() + "\n";
        System.out.println(toWriteEval);
    }

    @Test
    public void Validation2() {
        String test = " (55 – 47 ) * 14 / 2";
        DescenteRecursive dr;
        dr = new DescenteRecursive(test, false);
        ElemAST root = dr.AnalSynt();
        String toWriteLect = "";
        String toWriteEval = "";
        String toWritePostFix = "";

        toWriteLect += "Lecture de l'AST trouve : " + root.LectAST() + "\n";
        System.out.println(toWriteLect);

        toWritePostFix += "Postfix de l'AST trouve : " + root.PostFix() + "\n";
        System.out.println(toWritePostFix);

        toWriteEval += "Evaluation de l'AST trouve : " + root.EvalAST() + "\n";
        System.out.println(toWriteEval);
    }

    @Test
    public void Validation3() {
        String test = "( U_x – ) * W_z / 35";
        DescenteRecursive dr;
        dr = new DescenteRecursive(test, false);
        ElemAST root = dr.AnalSynt();
        String toWriteLect = "";
        String toWriteEval = "";

        toWriteLect += "Lecture de l'AST trouve : " + root.LectAST() + "\n";
        System.out.println(toWriteLect);
        toWriteEval += "Evaluation de l'AST trouve : " + root.EvalAST() + "\n";
        System.out.println(toWriteEval);
    }
}