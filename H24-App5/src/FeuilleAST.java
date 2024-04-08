/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class FeuilleAST extends ElemAST {

    /**Constructeur pour l'initialisation d'attribut(s)
     */
    public FeuilleAST(Terminal terminal) {  // avec arguments
        this.terminal = terminal;
    }


    /** Evaluation de feuille d'AST
     */
    public int EvalAST( ) {
        if(this.terminal.getType() == Terminal.Type.Identificateur){
            throw new ErreurEval("Erreur : L'équation contient un Identificateur, elle ne peut donc pas être faite");
        }

        return Integer.parseInt(this.terminal.getChaine());
    }


    /** Lecture de chaine de caracteres correspondant a la feuille d'AST
     */
    public String LectAST( ) {
        return this.terminal.getChaine();
    }

    @Override
    public String PostFix() {
        return terminal.getChaine();
    }

}