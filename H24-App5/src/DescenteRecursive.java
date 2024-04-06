/** @author Ahmed Khoumsi */

/** Cette classe effectue l'analyse syntaxique
 */
public class DescenteRecursive {

    // Attributs
    private String data;
    private AnalLex al;
    private Terminal currentTerminal;

    /** Constructeur de DescenteRecursive :
     - recoit en argument le nom du fichier contenant l'expression a analyser
     - pour l'initalisation d'attribut(s)
     */
    public DescenteRecursive(String in) {
        Reader r = new Reader(in);
        this.data = r.toString();
        this.al = new AnalLex(data);
    }


    /** AnalSynt() effectue l'analyse syntaxique et construit l'AST.
     *    Elle retourne une reference sur la racine de l'AST construit
     */
    public ElemAST AnalSynt( ) {

        ElemAST arbre = null;
        if(al.resteTerminal()){
            currentTerminal = al.prochainTerminal();
            arbre = E();
        }

        return arbre;
    }


// Methode pour chaque symbole non-terminal de la grammaire retenue
// ...
// ...

    public ElemAST E(){
        ElemAST n1 = T();

        // +-
        if(currentTerminal.getType() == Terminal.Type.Addition ||
                currentTerminal.getType() == Terminal.Type.Soustraction){
            Terminal operator = currentTerminal;
            if(al.resteTerminal()) {
                currentTerminal = al.prochainTerminal();
            }
            else{
                throw new ErreurSynth("Operande manquante dans l'expression : (" + n1.LectAST() + ")" + currentTerminal.getChaine() + "[Manquant]");
            }
            ElemAST n2 = E();
            n1 = new NoeudAST(operator, n1, n2);
        }
        else{
            throw new ErreurSynth("Deux operandes de suite ne sont pas valide : " + n1.LectAST() + " " + currentTerminal.getChaine());
        }

        return n1;
    }

    public ElemAST T(){
        ElemAST n1 = F();

        // * /
        if(currentTerminal.getType() == Terminal.Type.Division ||
                currentTerminal.getType() == Terminal.Type.Multiplication){
            Terminal operator = currentTerminal;
            if(al.resteTerminal()) {
                currentTerminal = al.prochainTerminal();
            }
            else{
                throw new ErreurSynth("Operande manquante dans l'expression : (" + n1.LectAST() + ")" + currentTerminal.getChaine() + "[Manquant]");
            }
            ElemAST n2 = T();
            n1 = new NoeudAST(operator, n1, n2);
        }
        // +- )
        else if(currentTerminal.getType() == Terminal.Type.Addition ||
                currentTerminal.getType() == Terminal.Type.Soustraction ||
                currentTerminal.getType() == Terminal.Type.ParentheseFermante){
            return n1;
        }
        else{
            throw new ErreurSynth("Deux operandes de suite ne sont pas valide : " + n1.LectAST() + " " + currentTerminal.getChaine());
        }

        return n1;
    }

    public ElemAST F(){
        ElemAST n;
        if(currentTerminal.getType() == Terminal.Type.Nombre ||
                currentTerminal.getType() == Terminal.Type.Identificateur){
            n = new FeuilleAST(currentTerminal);
            if(al.resteTerminal()){
                currentTerminal = al.prochainTerminal();
            }
        }
        else if(currentTerminal.getChaine().equals("(")){
            if(al.resteTerminal()) {
                currentTerminal = al.prochainTerminal();
            }
            else{
                throw new ErreurSynth("Paranthese ouvrante sans etre ferme");
            }
            n = E();
            if(currentTerminal.getChaine().equals(")")){
                if(al.resteTerminal()) {
                    currentTerminal = al.prochainTerminal();
                }
            }
            else{
                throw new ErreurSynth(") manquante");
            }
        }
        else{
            throw new ErreurSynth("Erreur de syntaxe : Absence de ( ou d'un identificateur au traitement de : " + currentTerminal.getChaine());
        }

        return n;
    }



    /** ErreurSynt() envoie un message d'erreur syntaxique
     */
    public void ErreurSynt(String s)
    {
        throw new ErreurSynth(s);
    }



    //Methode principale a lancer pour tester l'analyseur syntaxique
    public static void main(String[] args) {
        String toWriteLect = "";
        String toWriteEval = "";

        System.out.println("Debut d'analyse syntaxique");
        if (args.length == 0){
            args = new String [2];
            args[0] = "ExpArith.txt";
            args[1] = "ResultatSyntaxique.txt";
        }
        DescenteRecursive dr = new DescenteRecursive(args[0]);
        try {
            ElemAST RacineAST = dr.AnalSynt();
            toWriteLect += "Lecture de l'AST trouve : " + RacineAST.LectAST() + "\n";
            System.out.println(toWriteLect);
            toWriteEval += "Evaluation de l'AST trouve : " + RacineAST.EvalAST() + "\n";
            System.out.println(toWriteEval);
            Writer w = new Writer(args[1],toWriteLect+toWriteEval); // Ecriture de toWrite
            // dans fichier args[1]
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(51);
        }
        System.out.println("Analyse syntaxique terminee");
    }

}
