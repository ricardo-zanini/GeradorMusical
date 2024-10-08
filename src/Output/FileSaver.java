package Output;
import java.awt.*;

import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.midi.MidiParserListener;

import javax.sound.midi.Sequence;

import org.jfugue.player.Player;
import org.staccato.StaccatoParser;

import Alert.UserAlert;
import Input.FileSelector;

import org.jfugue.pattern.Pattern;

import java.io.*;
import java.util.Optional;
import java.awt.event.ActionEvent;
import java.util.logging.Filter;

public class FileSaver extends FileSelector{

    private JButton buttonSaveMusic;
    private Sequence fileContentSequence;

    public FileSaver(int textFieldWidth, Sequence content){
        super(textFieldWidth);

        setFileContentSequence(content);

        createComponents();
        configComponents(new FileNameExtensionFilter(".midi", ".midi"));
        addComponents();
    }

    public FileSaver(int textFieldWidth, Sequence content, FileNameExtensionFilter filter){
        super(textFieldWidth, filter);

        setFileContentSequence(content);

        createComponents();
        configComponents(filter);
        addComponents();

    }

    private void createComponents(){
        setButtonSaveMusic(new JButton("Salvar"));
    }

    private void configComponents(FileNameExtensionFilter filter){
        buttonSaveMusic.setBackground(new Color(216, 225, 240));
        buttonSaveMusic.setBorder(new LineBorder(new Color(0, 0, 0),0));
        buttonSaveMusic.setFont(new Font(null, Font.BOLD, 12));
        buttonSaveMusic.setMaximumSize(new Dimension(80, 40));
        buttonSaveMusic.addActionListener(event -> eventFileSave(event, filter));
        buttonSaveMusic.setFocusable(false);
    }

    private void addComponents(){
        add(buttonSaveMusic, BorderLayout.EAST);
    }

    private void eventFileSave(ActionEvent e, FileNameExtensionFilter filter){
        String filePath = getFieldFile().getText();

        if(filePath.length() == 0){
            UserAlert userAlert = new UserAlert("Você não selecionou um destino!");
        }else{
            try {
                File fileSaveCreate = new File(filePath);
                if(getExtension(filePath).equals(Optional.of("midi")) && filter.getExtensions()[0].equals("midi")){
                    if (fileSaveCreate.createNewFile()) {
                        try {

                            Player player = new Player();
                            MidiFileManager.save(getFileContentSequence(), fileSaveCreate);

                            UserAlert userAlert = new UserAlert("Arquivo Salvo com sucesso"); 
                          } catch (IOException ex) {
                            UserAlert userAlert = new UserAlert("ERRO - Erro ao criar arquivo"); 
                          }
                    } else {
                        UserAlert userAlert = new UserAlert("O arquivo selecionado já existe!"); 
                    }
                }else{

                    // Caso deseje-se adicionar outros tipos de arquivo para salvamento, alterar aqui
                    UserAlert userAlert = new UserAlert("O arquivo deve ser do tipo MIDI"); 
                }
              } catch (IOException ex) {
                UserAlert userAlert = new UserAlert("ERRO - Erro ao criar arquivo"); 
              }
        }
    }

    public Sequence getFileContentSequence() {
        return this.fileContentSequence;
    }

    public void setFileContentSequence(Sequence content) {
        this.fileContentSequence = content;
    }

    private Optional<String> getExtension(String filename) {
        return Optional.ofNullable(filename)
          .filter(f -> f.contains("."))
          .map(f -> f.substring(filename.lastIndexOf(".") + 1));
        }

    public JButton getButtonSaveMusic() {
        return buttonSaveMusic;
    }
    public void setButtonSaveMusic(JButton buttonSaveMusic) {
        this.buttonSaveMusic = buttonSaveMusic;
    }
}
