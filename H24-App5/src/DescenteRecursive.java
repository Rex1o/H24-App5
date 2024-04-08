/**
 * @author Ahmed Khoumsi
 */

/**
 * Cette classe effectue l'analyse syntaxique
 */
public class DescenteRecursive {
    private final AnalLex al;
    private Terminal previousTerminal;
    private Terminal currentTerminal;
    private Terminal.Type currentType;

    private void terminal(Terminal.Type attendu) {
        previousTerminal = currentTerminal;

        if (currentTerminal.getType() == attendu) {
            if (al.resteTerminal())
                currentTerminal = al.prochainTerminal();
            currentType = currentTerminal.getType();
        } else
            throw new ErreurSynth(currentTerminal);
    }


    /**
     * Constructeur de DescenteRecursive :
     * - recoit en argument le nom du fichier contenant l'expression a analyser
     * - pour l'initalisation d'attribut(s)
     */
    public DescenteRecursive(String data, boolean readFromFile) {
        if (readFromFile) {
            Reader r = new Reader(data);
            data = r.toString();
        }
        this.al = new AnalLex(data);
    }

    /**
     * AnalSynt() effectue l'analyse syntaxique et construit l'AST.
     * Elle retourne une reference sur la racine de l'AST construit
     */
    public ElemAST AnalSynt() {
        ElemAST racine = null;
        if (al.resteTerminal()) {
            currentTerminal = al.prochainTerminal(); // Lire le premier terminal
            currentType = currentTerminal.getType();
            racine = E();
        } else
            throw new ErreurSynth("Aucun terminal a analyser");
        return racine;
    }


// Methode pour chaque symbole non-terminal de la grammaire retenue
// ...
// ...

    public ElemAST E() {
        ElemAST gauche = T();
        return switch (currentType) {
            case Terminal.Type.Addition -> {
                terminal(Terminal.Type.Addition);
                yield new NoeudAST(previousTerminal, gauche, E());
            }
            case Terminal.Type.Soustraction -> {
                terminal(Terminal.Type.Soustraction);
                yield new NoeudAST(previousTerminal, gauche, E());
            }
            default -> gauche; // Dans le cas ou l'operation de contient aucune partie de droite
        };
    }

    public ElemAST T() {
        ElemAST gauche = F();
        return switch (currentType) {
            case Terminal.Type.Multiplication -> {
                terminal(Terminal.Type.Multiplication);
                yield new NoeudAST(previousTerminal, gauche, T());
            }
            case Terminal.Type.Division -> {
                terminal(Terminal.Type.Division);
                yield new NoeudAST(previousTerminal, gauche, T());
            }
            default -> gauche;
        };
    }

    public ElemAST F() {
        switch (currentType) {
            case Terminal.Type.Identificateur:
                terminal(Terminal.Type.Identificateur);
                return new FeuilleAST(previousTerminal);
            case Terminal.Type.Nombre:
                terminal(Terminal.Type.Nombre);
                return new FeuilleAST(previousTerminal);
            case Terminal.Type.ParentheseOuvrante:
                terminal(Terminal.Type.ParentheseOuvrante);
                ElemAST elem = E();
                terminal(Terminal.Type.ParentheseFermante);
                return elem;
            default:
                throw new ErreurSynth(previousTerminal);
        }
    }


    //Methode principale a lancer pour tester l'analyseur syntaxique
    public static void main(String[] args) {
        String toWriteLect = "";
        String toWriteEval = "";

        System.out.println("Debut d'analyse syntaxique");
        DescenteRecursive dr;
        if (args.length == 0) {
            args = new String[2];
            args[0] = "ExpArith.txt";
            args[1] = "ResultatSyntaxique.txt";

        }
        dr = new DescenteRecursive(args[0], true);
        try {
            ElemAST RacineAST = dr.AnalSynt();
            toWriteLect += "Lecture de l'AST trouve : " + RacineAST.LectAST() + "\n";
            System.out.println(toWriteLect);
            toWriteEval += "Evaluation de l'AST trouve : " + RacineAST.EvalAST() + "\n";
            System.out.println(toWriteEval);
            Writer w = new Writer(args[1], toWriteLect + toWriteEval); // Ecriture de toWrite
            // dans fichier args[1]
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(51);
        }
        System.out.println("Analyse syntaxique terminee");
    }
}
