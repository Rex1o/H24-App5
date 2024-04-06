/** @author Ahmed Khoumsi */

/** Cette classe identifie les terminaux reconnus et retournes par
 *  l'analyseur lexical
 */
public class Terminal {

    public String chaine;

    public boolean isOperator;


    /** Un ou deux constructeurs (ou plus, si vous voulez)
     *   pour l'initalisation d'attributs
     */
    public Terminal(String chaine) {   // arguments possibles
        this.chaine = chaine;
        this.isOperator = chaine.equals("=");
    }

}