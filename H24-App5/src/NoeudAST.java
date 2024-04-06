/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class NoeudAST extends ElemAST {

    // Attributs

    private Terminal terminal;
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
        int result = 0;
        switch(terminal.chaine) {
            case "+":
                result = gauche.EvalAST() + droit.EvalAST();
                break;
        }
        return result;
    }


    /** Lecture de noeud d'AST
     */
    public String LectAST( ) {
        return "(" + gauche.LectAST() + terminal.chaine + droit.LectAST() + ")";
    }

}

