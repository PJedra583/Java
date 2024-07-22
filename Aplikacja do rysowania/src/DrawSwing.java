import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class DrawSwing {

    public static void main(String[] args)
    {
        new DrawSwing();
    }

    public DrawSwing()
    {
        SwingUtilities.invokeLater(this::createGUI);    // () -> createGUI()
    }

    protected void createGUI()
    {
        // utworzenie okna
        JFrame jf = new JFrame();

        // określenie tytułu okna
        jf.setTitle("Simple drawing");

        // obsługa zamknięcia okna JFrame
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // określenie położenia okna
        jf.setLocation(50,50);

        // uniemożliwienie zmiany rozmiarów okna
        jf.setResizable(false);

        // obszaru rysowania - pulpitu
        MyPanel p = new MyPanel();

        // odrysowanie komponentu (jeśli będzie taka konieczność)
//        p.repaint();

        ///////////////////////////////Menu//////////////////////////////////////
        JMenuBar mb=new JMenuBar();
        JLabel mode = new JLabel("Circle");
        JLabel state = new JLabel("New");
        JMenu file = new JMenu("File");
        JMenuItem open,save,saveAs,quit;
        open=new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        save=new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (p.fileToSave == null) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Save as");

                    int choosen = fileChooser.showSaveDialog(jf);

                    if (choosen == JFileChooser.APPROVE_OPTION) {
                        p.fileToSave = fileChooser.getSelectedFile();
                        try {
                            p.saveToFile();
                        } catch (IOException ex) {
                            throw new RuntimeException();
                        }
                        jf.setTitle("Simple Draw: " + p.fileToSave.getName());
                        state.setText("Saved");
                    }
                } else {
                    try {
                        p.saveToFile();
                    } catch (IOException ex) {
                        throw new RuntimeException();
                    }
                }
            }
        });
        saveAs=new JMenuItem("Save as");
        saveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save as");

                int choosen = fileChooser.showSaveDialog(jf);

                if (choosen == JFileChooser.APPROVE_OPTION) {
                    p.fileToSave = fileChooser.getSelectedFile();
                    try {
                        p.saveToFile();
                    } catch (IOException ex) {
                        throw new RuntimeException();
                    }
                    jf.setTitle("Simple Draw: " + p.fileToSave.getName());
                    state.setText("Saved");
                }
            }
        });
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open file");
                int choosen = fileChooser.showOpenDialog(jf);
                if (choosen == JFileChooser.APPROVE_OPTION) {
                    p.clear();
                    try {
                        p.backgroundimage = ImageIO.read(fileChooser.getSelectedFile());
                    } catch (IOException ex) {
                        throw new RuntimeException();
                    }
                    p.repaint();
                }
                state.setText("Modified");
            }
        });
        saveAs.setAccelerator(KeyStroke.getKeyStroke("ctrl shift S"));
        quit=new JMenuItem("Quit");
        quit.setMnemonic(KeyEvent.VK_S);
        quit.setMnemonic(KeyEvent.VK_O);
        quit.setMnemonic(KeyEvent.VK_Q);
        quit.setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        file.add(open); file.add(save); file.add(saveAs); file.add(quit);
        JMenu draw= new JMenu("Draw");
        JMenuItem color,clear;
        color=new JMenuItem("Color");
        color.setAccelerator(KeyStroke.getKeyStroke("alt shift C"));
        color.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(jf,"Moj pierwszy JColorChooser",Color.BLUE);
                p.setColor2(color);
            }
        });
        clear=new JMenuItem("Clear");
        clear.setAccelerator(KeyStroke.getKeyStroke("ctrl shift N"));
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.clear();
            }
        });
        JMenu figure = new JMenu("Figure");
        JRadioButtonMenuItem circle,square,pen;
        circle=new JRadioButtonMenuItem("Circle");
        circle.setMnemonic(KeyEvent.VK_C);
        circle.setAccelerator(KeyStroke.getKeyStroke("control C"));
        square=new JRadioButtonMenuItem("Square");
        square.setMnemonic(KeyEvent.VK_R);
        square.setAccelerator(KeyStroke.getKeyStroke("control R"));
        pen=new JRadioButtonMenuItem("Pen");
        pen.setMnemonic(KeyEvent.VK_E);
        pen.setAccelerator(KeyStroke.getKeyStroke("control E"));
        circle.addActionListener(e -> {
            p.tryb = Tryb.CIRCLE;
            mode.setText("Circle");
        });
        square.addActionListener(e -> {
            p.tryb = Tryb.SQUARE;
            mode.setText("Square");
        });
        pen.addActionListener(e -> {
            p.tryb = Tryb.PEN;
            mode.setText("Pen");
        });
        ButtonGroup jButtonGroup = new ButtonGroup();
        jButtonGroup.add(circle);
        jButtonGroup.add(square);
        jButtonGroup.add(pen);
        circle.setSelected(true);
        figure.add(circle); figure.add(square); figure.add(pen);
        draw.add(figure); draw.add(color); draw.add(clear);
        mb.add(file);
        mb.add(draw);
        jf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    p.d = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_F1) {
                    p.addElement();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    p.d = false;
                }
            }
        });
        // wymiana domyślnego pulpitu na własny
        jf.setContentPane(p);

        JToolBar t = new JToolBar();
        t.setLayout(new BorderLayout());
        t.setPreferredSize(new Dimension(400,20));
        t.add(mode,BorderLayout.WEST);
        t.add(state,BorderLayout.EAST);
        mb.setPreferredSize(new Dimension(400,30));
        jf.setLayout(new BorderLayout());
        jf.add(mb,BorderLayout.NORTH);
        jf.add(t,BorderLayout.SOUTH);
        // upakowanie okna
        jf.pack();
        // wyświetlenie okna
        jf.setVisible(true);
    }
}

class MyPanel
        extends JPanel {
    public int MousePosX,MousePosY;
    public int MousePosX2,MousePosY2;
    public List<Circle> circles;
    public List<Square> squares;
    public List<Pen> pens;
    ArrayList<Point> points;
    public Tryb tryb;
    Color color;
    BufferedImage backgroundimage;
    File fileToSave;
    boolean d;
    MyPanel()
    {
        //Zmienne Obiektu MyPanel
        this.color = Color.black;
        this.backgroundimage = null;
        this.tryb = Tryb.CIRCLE;
        this.fileToSave = null;
        this.circles = new ArrayList<>();
        this.squares = new ArrayList<>();
        this.pens = new ArrayList<>();
        this.d = false;
        // ustalenie rozmiarów początkowych
        setPreferredSize(new Dimension(400,400));
        //MOUSE LISTENER
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // LPM zamiast przycisku F1 (Problem z jego obsługą na laptopie)
                if (e.getButton() == MouseEvent.BUTTON1 && !d) {
                    if (tryb == Tryb.CIRCLE) {
                        MousePosX = e.getX();
                        MousePosY = e.getY();
                        circles.add(new Circle(new Point(MousePosX, MousePosY), color));
                        repaint();
                    } else if (tryb == Tryb.SQUARE) {
                        if (e.getButton() == MouseEvent.BUTTON1 && !d) {
                            MousePosX = e.getX();
                            MousePosY = e.getY();
                            squares.add(new Square(new Point(MousePosX, MousePosY), color));
                            repaint();
                        }
                    }
                } else if (e.getButton() == MouseEvent.BUTTON1 && d) {
                    List<Circle> tmpCircles = new ArrayList<>();
                    List<Square> tmpSquares = new ArrayList<>();
                    MousePosX = e.getX();
                    MousePosY = e.getY();
                    for (Circle circle : circles) {
                        if (circle.isInCircle(MousePosX,MousePosY)) {
                            tmpCircles.add(circle);
                        }
                    }
                    for (Square square : squares) {
                        if (square.isInSquare(MousePosX,MousePosY)) {
                            tmpSquares.add(square);
                        }
                    }
                    for (Circle circle : tmpCircles) {
                        circles.remove(circle);
                    }
                    for (Square square : tmpSquares) {
                        squares.remove(square);
                    }
                    repaint();
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
                if (tryb == Tryb.PEN) {
                    points = new ArrayList<>();
                    points.add(e.getPoint());
                    pens.add(new Pen(points,color));
                }
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (tryb == Tryb.PEN) {
                    points.add(e.getPoint());
                    repaint();
                }
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                MousePosX2 = e.getX();
                MousePosY2 = e.getY();
            }
        });
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (this.backgroundimage != null) {
            //30 dla JMENU
            g.drawImage(this.backgroundimage, 0, 30, null);
        }
        for (Circle circle : circles) {
            g.setColor(circle.color);
            g.fillOval(circle.p.x - 20, circle.p.y - 20, 40,40 );
        }
        for (Square square : squares) {
            g.setColor(square.color);
            g.fillRect(square.p.x - 20, square.p.y - 20, 40,40 );
        }
        for (Pen pen : pens) {
            g.setColor(pen.color);
            for (int i = 0; i < pen.points.size()-1; i++) {
                g.drawLine(pen.points.get(i).x, pen.points.get(i).y, pen.points.get(i+1).x,pen.points.get(i+1).y);
            }
        }
        g.setColor(color);
    }
    public void setColor2(Color c) {
        color = c;
    }
    public void clear(){
        circles = new ArrayList<>();
        squares = new ArrayList<>();
        pens = new ArrayList<>();
        backgroundimage = null;
        repaint();
    }
    //Dla przycisku f1
    public void addElement(){
        Point p = new Point(MousePosX2,MousePosY2);
        //Zła lokalizacja:  Point p = MouseInfo.getPointerInfo().getLocation();
        int r,g,b;
        r = (int) (Math.random()*255);
        g = (int) (Math.random()*255);
        b = (int) (Math.random()*255);
        if (this.tryb == Tryb.CIRCLE) {
            circles.add(new Circle(p,new Color(r,g,b)));
        } else if (this.tryb == Tryb.SQUARE) {
            squares.add(new Square(p,new Color(r,g,b)));
        }
        repaint();
    }
    public void saveToFile() throws IOException {
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        this.printAll(g2);
        // 30 dla JMENU, 20 dla JTOOLBAR
        BufferedImage subimage = image.getSubimage(0, 30, image.getWidth(), image.getHeight()-50);
        ImageIO.write(subimage, "png", fileToSave);
    }
}
enum Tryb {
    CIRCLE,SQUARE,PEN
}
class Circle {
    Point p;
    Color color;
    public Circle (Point p,Color color){
        this.p = p;
        this.color = color;
    }
    public boolean isInCircle(int MouseX,int MouseY){
        return Math.sqrt(Math.pow(MouseX - this.p.x, 2) + Math.pow(MouseY - this.p.y, 2)) < 20;
    }
}
class Square {
    Point p;
    Color color;
    public Square (Point p,Color color){
        this.p = p;
        this.color = color;
    }
    public boolean isInSquare(int MouseX,int MouseY){
        return (MouseX < this.p.x + 20 && MouseX > this.p.x - 20 && MouseY < this.p.y + 20 && MouseY > this.p.y - 20);
    }
}
class Pen {

    ArrayList<Point> points;
    Color color;
    public Pen (ArrayList<Point> points,Color color){
        this.points = points;
        this.color = color;
    }
}