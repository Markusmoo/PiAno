import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by 300145948 on 24/04/2017.
 */
public class MainFrame extends JFrame implements MouseListener{

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

    public void playWhiteNote(int n){

    }

    public void playBlackNote(int n){

    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        for(Rectangle key : blackKeys){
            if(key.contains(e.getX(), e.getY())){
                //TODO I am here
                return;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
