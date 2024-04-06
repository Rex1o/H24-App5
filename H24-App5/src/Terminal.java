/** @author Ahmed Khoumsi */

/** Cette classe identifie les terminaux reconnus et retournes par
 *  l'analyseur lexical
 */
public class Terminal {
    public enum Type {
        Identificateur,
        Nombre,
        Addition,
        Division,
        Multiplication,
        Soustraction,
        ParentheseOuvrante,
        ParentheseFermante
    }


    private int position;
    public int getPosition(){
        return position;
    }

    private String chaine;
    public String getChaine(){
        return chaine;
    }

    private Type type;
    public Type getType(){
        return type;
    }

    /** Un ou deux constructeurs (ou plus, si vous voulez)
     *   pour l'initalisation d'attributs
     */
    public Terminal(String chaine, int cursorPos, Type type) {
        this.chaine = chaine;
        this.type = type;
        this.position = cursorPos;
    }

    public Terminal(String chaine, int cursorPos , char operator) {
        this.chaine = chaine;
        this.type = getOperators(operator);
        this.position = cursorPos;
    }

    private static Type getOperators (char c){
        return switch (c) {
            case '+' -> Type.Addition;
            case '-' -> Type.Soustraction;
            case '/' -> Type.Division;
            case '*' -> Type.Multiplication;
            case '(' -> Type.ParentheseOuvrante;
            case ')' -> Type.ParentheseFermante;
            default -> throw new RuntimeException(String.format("Caractère '%c' non supporté comme opérateur", c));
        };
    }

    public String toString(){
        return STR."\{type.name()} \{chaine}";
    }
}