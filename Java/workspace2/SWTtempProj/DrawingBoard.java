import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.Random;

public class DrawingBoard extends JFrame{
        Container c;

        public DrawingBoard(){
                super("Internal");
                c = getContentPane();
                c.setBackground(Color.WHITE);
                c.add(new MyPanel(new Point(-10, -10)));
                setSize(500,500);
                setVisible(true);
        }
        
        private class MyPanel extends JPanel implements MouseListener, MouseMotionListener{
                Point point;
                Random r;
                public MyPanel(Point p){
                        setOpaque(false);
                        r = new Random();
                        point = p;
                        setBackground(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
                        setLayout(new BorderLayout());
                        addMouseListener(this);
                        addMouseMotionListener(this);
                }
                public void mousePressed(MouseEvent me){
                        JPanel p = new MyPanel(me.getPoint());
                        this.add(new MyPanel(new Point(me.getX(), me.getY())));
                        DrawingBoard.this.setVisible(true);
                }
                public void mouseExited(MouseEvent me){
                }
                public void mouseEntered(MouseEvent me){
                }
                public void mouseReleased(MouseEvent me){
                }
                public void mouseClicked(MouseEvent me){
                }
                public void mouseDragged(MouseEvent me){
                        JPanel p = new MyPanel(me.getPoint());
                        this.add(new MyPanel(new Point(me.getX(), me.getY())));
                        DrawingBoard.this.setVisible(true);
                }
                public void mouseMoved(MouseEvent me){
                }
                        
                public void paintComponent(Graphics g){
                        super.paintComponent(g);
                        Graphics2D g2d = (Graphics2D)g;
                        g2d.setPaint(Color.RED);
                        g2d.fill(new Ellipse2D.Double(point.getX()-5,point.getY()-5,10f,10f));
                }
        }
        
        public static void main(String args[]){
                 JFrame frame = new DrawingBoard();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
}