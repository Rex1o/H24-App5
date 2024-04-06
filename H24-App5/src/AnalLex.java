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
    private State state;
    private final StringBuilder sb; // Plus rapide que concat des strings

    /**
     * Constructeur pour l'initialisation d'attribut(s)
     */
    public AnalLex(String data) {  // arguments possibles
        this.data = data;
        this.cursor = 0;
        this.state = State.A;
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
        while (cursor < data.length()) {
            char c = data.charAt(cursor);

            switch (state) {
                case State.A:
                    // 90 = 'Z', 64 = 'A' en ASCII
                    if (c <= 90 && c >= 64) {
                        sb.append(c);
                        cursor++;
                        state = State.B;

                    } else if (c <= 57 && c >= 48) { // 57 = '9', 48 = '0'
                        sb.append(c);
                        cursor++;
                        state = State.D;
                    } else if (c == '+' || c == '-' || c == '/' || c == '*' || c == '(' || c == ')') {
                        sb.append(c);
                        cursor++;
                        state = State.E;
                    } else {
                        throw new  ErreurLex(String.format("Caractère non '%c' non supporté à la col: %d",c ,cursor));
                    }
                    break;
                case State.B:
                    // 90 = Z, 64 = A et 97 = a, z = 122
                    if (c <= 90 && c >= 64 || c <= 122 && c >= 97) {
                        sb.append(c);
                        cursor++;
                    } else if (c == '_') {
                        sb.append(c);
                        cursor++;
                        state = State.C;
                    } else
                        return new Terminal(sb.toString());
                    break;
                case State.C:
                    if (c <= 90 && c >= 64 || c <= 122 && c >= 97) {
                        sb.append(c);
                        cursor++;
                        state = State.B;
                    } else
                        throw new ErreurLex(String.format("L'identificateur ne peut pas fini avec le caractère : %c. Voir col : %d", c, cursor));
                    break;
                case State.D:
                    if (c <= 57 && c >= 48) // 57 = '9', 48 = '0'
                        cursor++;
                    else
                        return new Terminal(sb.toString());
                    break;
                case State.E:
                    return new Terminal(sb.toString());
                default:
                    break;
            }
        }
        return new Terminal(sb.toString());
    }

    /**
     * ErreurLex() envoie un message d'erreur lexicale
     */
    public void ErreurLex(String s) {
        throw new ErreurLex(s);
    }
    
    //Methode principale a lancer pour tester l'analyseur lexical
    public static void main(String[] args) {
        String toWrite = "";
        System.out.println("Debut d'analyse lexicale");
        if (args.length == 0) {
            args = new String[2];
            args[0] = "ExpArith.txt";
            args[1] = "ResultatLexical.txt";
        }
        Reader r = new Reader(args[0]);

        AnalLex lexical = new AnalLex(r.toString()); // Creation de l'analyseur lexical

        // Execution de l'analyseur lexical
        Terminal t = null;
        while (lexical.resteTerminal()) {
            t = lexical.prochainTerminal();
            toWrite += t.chaine + "\n";  // toWrite contient le resultat
        }                   //    d'analyse lexicale
        System.out.println(toWrite);    // Ecriture de toWrite sur la console
        Writer w = new Writer(args[1], toWrite); // Ecriture de toWrite dans fichier args[1]
        System.out.println("Fin d'analyse lexicale");
    }
}