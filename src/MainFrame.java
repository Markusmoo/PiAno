import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import org.jfugue.realtime.RealtimePlayer;
import org.jfugue.theory.Note;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by 300145948 on 24/04/2017.
 */
public class MainFrame extends JFrame implements MouseListener{

    boolean isPi = false;
    public GpioController gpio;

    public RealtimePlayer player;


    Rectangle[] whiteKeys = new Rectangle[7];
    Rectangle[] blackKeys = new Rectangle[5];

    public static void main(String[] args){
        new MainFrame();
    }

    public MainFrame(){
        this.setTitle("PiAno");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setForeground(Color.WHITE);

        if(System.getProperty("os.name").toLowerCase().contains("linux")){
            isPi = true;
            gpio = GpioFactory.getInstance();
        }else{
            try{
                player = new RealtimePlayer();
            }catch(MidiUnavailableException e){
                e.printStackTrace();
            }
        }

        this.setupKeys();

        this.setVisible(true);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.addMouseListener(this);

        //new Timer(2000, e -> repaint()).start();  //TODO For debug refresh
    }

    public void setupKeys(){
        int whiteKeyWidth = this.getWidth() / whiteKeys.length;
        int blackKeyWidth = this.getWidth() / (whiteKeys.length*2);
        int i2 = 0;
        for(int i = 0; i < whiteKeys.length; i++){
            whiteKeys[i] = new Rectangle(whiteKeyWidth*i, 0, whiteKeyWidth, this.getHeight());
            if(i != 2 && i != 6){
                blackKeys[i2] = new Rectangle(whiteKeyWidth*i+(blackKeyWidth+blackKeyWidth/2), 0 , blackKeyWidth, this.getHeight()/3 + this.getHeight()/3);
                i2++;
            }
        }
    }

    public void paint(Graphics g){
        super.paint(g);
        Color old = g.getColor();
        setupKeys();
        for(Rectangle key : whiteKeys){
            g.setColor(Color.WHITE);
            g.fillRect(key.x, key.y, key.width, key.height);
            g.setColor(Color.black);
            g.drawLine(key.x, key.y, key.x, key.height);
        }
        for(Rectangle key : blackKeys){
            g.fillRect(key.x, key.y, key.width, key.height);
        }
        g.setColor(old);
    }

    public void playNote(String note){
        if(isPi){
            //TODO
        }else{
            player.startNote(new Note(note));
        }
    }

    public void playWhiteNote(int n){
        switch (n){
            case(0): playNote("c"); break;
            case(1): playNote("d"); break;
            case(2): playNote("e"); break;
            case(3): playNote("f"); break;
            case(4): playNote("g"); break;
            case(5): playNote("e"); break;
            default: playNote("c"); break;
        }
    }

    public void playBlackNote(int n){
        switch (n){
            case(0): playNote("c#"); break;
            case(1): playNote("d#"); break;
            case(2): playNote("f#"); break;
            case(3): playNote("g#"); break;
            case(4): playNote("a#"); break;
            default: playNote("c"); break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        int idx = 0;
        for(Rectangle key : blackKeys){
            if(key.contains(e.getX(), e.getY())){
                playBlackNote(idx);
                return;
            }
            idx++;
        }
        idx = 0;
        for(Rectangle key : whiteKeys){
            if(key.contains(e.getX(), e.getY())){
                playWhiteNote(idx);
                return;
            }
            idx++;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
