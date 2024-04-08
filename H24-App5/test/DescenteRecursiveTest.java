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
        String toWritePostFix = "";

        toWriteLect += "Lecture de l'AST trouve : " + root.LectAST() + "\n";
        System.out.println(toWriteLect);

        toWritePostFix += "Postfix de l'AST trouve : " + root.PostFix() + "\n";
        System.out.println(toWritePostFix);

        ErreurEval thrown = assertThrows(
                ErreurEval.class,
                () -> root.EvalAST(),
                "Supposé recevoir un erreur d'eval"
        );
        System.out.println(thrown.toString());
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

        ErreurSynth thrown = assertThrows(
                ErreurSynth.class,
                () -> dr.AnalSynt(),
                "Supposé recevoir un erreur de synthèse"
        );
        System.out.println(thrown.toString());
    }
    @Test
    public void DeuxParenthese() {
        String test = "(( 4 – 3)) * 6 / 2";
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
    public void ParanthesePasFermante() {
        String test = "(( 4 – 3) * 6 / 2";
        DescenteRecursive dr;
        dr = new DescenteRecursive(test, false);
        ErreurSynth thrown = assertThrows(
                ErreurSynth.class,
                () -> dr.AnalSynt(),
                "Supposé recevoir un erreur de synthèse"
        );
        System.out.println(thrown.toString());
    }

    @Test
    public void TestGenerale() {
        String test = "5+6   + 9 -96 / (5 +3) * ((45+63) +98)";
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

}