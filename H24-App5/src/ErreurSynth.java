public class ErreurSynth extends RuntimeException
{
    public ErreurSynth(Terminal terminal){
        super(String.format("Problème avec le texte : %s à la position %d", terminal.getChaine(), terminal.getPosition()));
    }
    public ErreurSynth(String errorTxt){
        super(errorTxt);
    }
}
