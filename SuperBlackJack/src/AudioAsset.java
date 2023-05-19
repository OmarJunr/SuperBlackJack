import java.net.URL;

public class AudioAsset {
   URL cardSound01 = getClass().getClassLoader().getResource("cardSound01.wav");
   URL youwon      = getClass().getClassLoader().getResource("ganhou.wav");
   URL youlost     = getClass().getClassLoader().getResource("perdeu.wav");
   URL draw        = getClass().getClassLoader().getResource("empate.wav");
   URL bgm         = getClass().getClassLoader().getResource("theelevatorbossanova.wav");
}