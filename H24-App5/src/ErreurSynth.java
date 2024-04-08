public class ErreurSynth extends RuntimeException
{
    public ErreurSynth(Terminal terminal){
        super(String.format("Un caractère est invalide ou manquant, voir \"%s\" à la position %d", terminal.getChaine(), terminal.getPosition()));
    }
    public ErreurSynth(String errorTxt){
        super(errorTxt);
    }
}
