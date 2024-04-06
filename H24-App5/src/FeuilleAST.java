/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class FeuilleAST extends ElemAST {

    // Attribut(s)
    private Terminal terminal;

    /**Constructeur pour l'initialisation d'attribut(s)
     */
    public FeuilleAST(Terminal terminal) {  // avec arguments
        this.terminal = terminal;
    }


    /** Evaluation de feuille d'AST
     */
    public int EvalAST( ) {

        String regex = "[0-9]+";

        if(!this.terminal.chaine.matches(regex)){
            ErreurEvalAST("Erreur : La feuille contient une variable/autre chose qu'un nombre");
        }

        return Integer.valueOf(this.terminal.chaine);
    }


    /** Lecture de chaine de caracteres correspondant a la feuille d'AST
     */
    public String LectAST( ) {
        return this.terminal.chaine;
    }

}