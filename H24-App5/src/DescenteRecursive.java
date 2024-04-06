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

        if(currentTerminal.isOperator){
            currentTerminal = al.prochainTerminal();
            ElemAST n2 = E();
            n1 = new NoeudAST(currentTerminal, n1, n2);
        }

        return n1;
    }

    public ElemAST T(){
        ElemAST n1 = F();

        if(currentTerminal.isOperator){
            currentTerminal = al.prochainTerminal();
            ElemAST n2 = T();
            n1 = new NoeudAST(currentTerminal, n1, n2);
        }

        return n1;
    }


    public ElemAST F(){
        ElemAST n;
        if(!currentTerminal.isOperator){
            n = new FeuilleAST(currentTerminal);
        }
        else if(currentTerminal.isOperator && currentTerminal.chaine == "("){


        }
        else{
            throw new ErreurSynth("Erreur de syntaxe : Absence de ( ou d'un identificateur au traitement de : " + currentTerminal.chaine);
        }

        return new FeuilleAST(new Terminal("")); // TODO changer ca
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
