/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class NoeudAST extends ElemAST {

    // Attributs
    private ElemAST gauche;
    private ElemAST droit;

    /** Constructeur pour l'initialisation d'attributs
     */
    public NoeudAST(Terminal terminal, ElemAST gauche, ElemAST droit) { // avec arguments
        this.terminal = terminal;
        this.gauche = gauche;
        this.droit = droit;
    }


    /** Evaluation de noeud d'AST
     */
    public int EvalAST( ) {
        return switch(terminal.getType()) {
            case Identificateur -> throw new ErreurEval("Un noeud ne devrait pas être un identificateur");
            case Nombre -> throw new ErreurEval("Un noeud ne devrait pas être un nombre");
            case Addition -> gauche.EvalAST() + droit.EvalAST();
            case Division -> gauche.EvalAST() / droit.EvalAST();
            case Multiplication -> gauche.EvalAST() * droit.EvalAST();
            case Soustraction -> gauche.EvalAST() - droit.EvalAST();
            case ParentheseOuvrante -> throw new ErreurEval("Un noeud ne devrait pas être un parenthèse");
            case ParentheseFermante -> throw new ErreurEval("Un noeud ne devrait pas être un parenthèse");
        };
    }

    public String PostFix(){
        return gauche.PostFix() +" "+  droit.PostFix() + " "+ terminal.getChaine();
    }

    /** Lecture de noeud d'AST
     */
    public String LectAST( ) {
        return "(" + gauche.LectAST() + terminal.getChaine() + droit.LectAST() + ")";
    }

}

