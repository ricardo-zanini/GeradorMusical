import javax.swing.JFrame;

public class PlayScreen extends Screen{
    public PlayScreen(String fileContent){
        super("Reprodução");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        
         // !!!!!!!!!!!!!! TRABALAR NESSA PARTE !!!!!!!!!!!!!!

        SystemPlayer playNotes = new SystemPlayer(fileContent);
    }
}
