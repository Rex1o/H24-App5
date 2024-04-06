/**
 * @author Ahmed Khoumsi
 */

import javax.xml.datatype.DatatypeConfigurationException;

/**
 * Cette classe effectue l'analyse lexicale
 */
public class AnalLex {
    enum State {
        A,
        B,
        C,
        D,
        E
    }

    public String data;
    private int cursor;
    private State currentState, nextState;
    private final StringBuilder sb; // Plus rapide que concat des strings

    /**
     * Constructeur pour l'initialisation d'attribut(s)
     */
    public AnalLex(String data) {  // arguments possibles
        this.data = data;
        this.cursor = 0;
        this.currentState = State.A;
        this.nextState = State.A;
        this.sb = new StringBuilder();
    }

    /**
     * resteTerminal() retourne :
     * false  si tous les terminaux de l'expression arithmetique ont ete retournes
     * true s'il reste encore au moins un terminal qui n'a pas ete retourne
     */
    public boolean resteTerminal() {
        return cursor != data.length();
    }


    /**
     * prochainTerminal() retourne le prochain terminal
     * Cette methode est une implementation d'un AEF
     */
    public Terminal prochainTerminal() {
        sb.setLength(0);
        int startingCursor = cursor;
        boolean endOfTerminal = false;
        char c = 0;

        while (cursor < data.length() && !endOfTerminal) {
            c = data.charAt(cursor);
            currentState = nextState;

            switch (currentState) {
                case State.A:
                    // Si c faite partie de [A-Z]
                    if (c <= 90 && c >= 64)
                        changeStateAndAppend(c, State.B);
                    else if (c <= 57 && c >= 48) // Si c fait partie de [0-9]
                        changeStateAndAppend(c, State.D);
                    else if (c == '+' || c == '-' || c == '/' || c == '*' || c == '(' || c == ')') // // Si c est une de ces operandes
                        changeStateAndAppend(c, State.E);
                    else if( c == ' ') // Si on obtient un espace, on revient a A, mais on l'ajoute pas au terminal
                        cursor++;
                    else
                        throw new ErreurLex(String.format("Caratère '%c' invalide, voir \"%s\" à la position %d", c, sb.toString() ,cursor));
                    break;
                case State.B:
                    // Si c faite partie de [A-Z] ou [a-z]
                    if (c <= 90 && c >= 64 || c <= 122 && c >= 97)
                        changeStateAndAppend(c, State.B);
                    else if (c == '_')
                        changeStateAndAppend(c, State.C);
//                    else if (c <= 57 && c >= 48)  // Si c fait partie de [0-9] //FIXME VOIR LE CAS DE TEST ExceptionChiffreApresLettreIdentificateur
//                        throw new ErreurLex(String.format("Caratère '%c' invalide, voir \"%s\" à la position %d. Un identificateur ne peut pas contenir de chiffres.",  c, sb.toString() ,cursor));
                     else{
                        endOfTerminal = true;
                        nextState = State.A;
                    }
                    break;
                case State.C:
                    // Si c faite partie de [A-Z] ou [a-z]
                    if (c <= 90 && c >= 64 || c <= 122 && c >= 97)
                        changeStateAndAppend(c ,State.B);
//                    else if (c <= 57 && c >= 48)  // Si c fait partie de [0-9] //FIXME VOIR LE CAS DE TEST ExceptionChiffreApresUndeScoreIdentificateur
//                        throw new ErreurLex(String.format("Caratère '%c' invalide, voir \"%s\" à la position %d. Un identificateur ne peut pas contenir de chiffres.",  c, sb.toString() ,cursor));
                     else
                        throw new ErreurLex(String.format("Caratère '%c' invalide, voir \"%s\" à la position %d", c, sb.toString() ,cursor));
                    break;
                case State.D:
                    if (c <= 57 && c >= 48)// Si c fait partie de [0-9]
                        changeStateAndAppend(c, State.D);
//                    else if (c <= 90 && c >= 64 || c <= 122 && c >= 97) //FIXME VOIR LE CAS DE TEST  ChiffreSuiviLettre
//                        throw new ErreurLex(String.format("Caratère '%c' invalide, voir \"%s\" à la position %d. Nombre contient un caractère.",  c, sb.toString() ,cursor));
                    else{
                        endOfTerminal = true;
                        nextState = State.A;
                    }
                    break;
                case State.E:
                    endOfTerminal = true;
                    nextState = State.A;
                default:
                    break;
            }
        }
        // Cas limite ou le dernier Terminal de la sequence est un identificateur finissant par '_'
        if (cursor >= data.length() && nextState == State.C){
            throw new ErreurLex(String.format("Caratère '%c' invalide, voir \"%s\" à la position %d", c, sb.toString() ,cursor));
        }

        return switch (currentState){
            case State.A -> new Terminal(sb.toString(), startingCursor, sb.charAt(0));// Ce cas arrive quand le dernier terminal est un operateur
            case State.B -> new Terminal(sb.toString(), startingCursor, Terminal.Type.Identificateur);
            case State.C -> throw new ErreurLex(String.format("Un identificateur ne peut pas finir par '_', voir \"%s\" à la position %d", sb.toString() ,cursor));
            case State.D -> new Terminal(sb.toString(), startingCursor, Terminal.Type.Nombre);
            case State.E -> new Terminal(sb.toString(), startingCursor, sb.charAt(0));
        };
    }

    private void changeStateAndAppend(char c, State nextState){
        cursor++;
        this.nextState = nextState;
        sb.append(c);
    }
}