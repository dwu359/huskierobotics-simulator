
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
              public void run() {
                   Main frame = new Main();
              }
        });
    }

    public Main() {
        JFrame jFrame = new JFrame();
        DrawingComponent drawingComponent = new DrawingComponent();
        drawingComponent.setDoubleBuffered(true); // enables double buffering
        
        jFrame.addKeyListener(drawingComponent);
        jFrame.add(drawingComponent);
        jFrame.setTitle(Constants.TITLE);
        jFrame.setSize(Constants.WINDOW_SIZE[0], Constants.WINDOW_SIZE[1]);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class DrawingComponent extends JComponent implements KeyListener {
    private int x = 0;
    private int y = 0;
    public DrawingComponent() {
        Timer timer = new Timer(Constants.DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint(); // calls paintComponent method
            }
        });
        timer.start();
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.fillOval(x, y, 30, 30);
        g2d.dispose();
    }

    public void update()
    {
        // code that runs in timer loop
    }

    public void keyPressed(KeyEvent e) {
        //System.out.println(e.getKeyCode());
        switch(e.getKeyCode()) {
            case 87:
                //System.out.println("W");
                y--;
                break;
            case 65:
                //System.out.println("A");
                x--;
                break;
            case 83:
                //System.out.println("S");
                y++;
                break;
            case 68:
                //System.out.println("D");
                x++;
                break;
            case 38:
                //System.out.println("Up Arrow");
                break;
            case 37:
                //System.out.println("Left Arrow");
                break;
            case 40:
                //System.out.println("Down Arrow");
                break;
            case 39:
                //System.out.println("Right Arrow");
                break;
        }
    }

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}
