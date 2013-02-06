import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;


public class Scribble {
	
	public static void main(String[] args) {
    
	JFrame frame = new JFrame();
    final DrawingPad drawPad = new DrawingPad();
    frame.add(drawPad, BorderLayout.CENTER);
    
    JPanel a = new JPanel(new GridLayout(1,2,0,0));
    TitledBorder border = new TitledBorder("Colors and Sizes");
    a.setBorder(border);
    JPanel scroll = new JPanel(new GridLayout(3,2,1,1));
    JPanel button = new JPanel(new BorderLayout());
    //JPanel colorView = new JPanel();
    
    JButton clearButton = new JButton("Clear");
    
    JSlider size = new JSlider(JSlider.HORIZONTAL,0,50,2);
    JSlider r = new JSlider(JSlider.HORIZONTAL, 0, 255, 5);
	JSlider g = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
	JSlider b = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
	
	size.setMajorTickSpacing(10);
	size.setMinorTickSpacing(1);
	size.setPaintTicks(true);
    size.setPaintLabels(true);
     
	final JLabel rLabel = new JLabel("R = 0");
    final JLabel gLabel = new JLabel("G = 0");
    final JLabel bLabel = new JLabel("B = 0");
   
    
	r.addChangeListener(new ChangeListener() {
	      public void stateChanged(ChangeEvent e) {
	        JSlider source = (JSlider) e.getSource();
	        if (!source.getValueIsAdjusting()) {
	           drawPad.rVal = (int) source.getValue();
	           rLabel.setText("R = "+drawPad.rVal);
	        }
	      }
	    });
	g.addChangeListener(new ChangeListener() {
	      public void stateChanged(ChangeEvent e) {
	        JSlider source = (JSlider) e.getSource();
	        if (!source.getValueIsAdjusting()) {
	           drawPad.gVal = (int) source.getValue();
	           gLabel.setText("G = "+drawPad.gVal);
	        }
	      }
	    });
	b.addChangeListener(new ChangeListener() {
	      public void stateChanged(ChangeEvent e) {
	        JSlider source = (JSlider) e.getSource();
	        if (!source.getValueIsAdjusting()) {
	           drawPad.bVal = (int) source.getValue();
	           bLabel.setText("B = "+drawPad.bVal);
	        }
	      }
	    });
	
	size.addChangeListener(new ChangeListener() {
	      public void stateChanged(ChangeEvent e) {
	        JSlider source = (JSlider) e.getSource();
	        if (!source.getValueIsAdjusting()) {
	           drawPad.sizeBrush = (int) source.getValue();
	          
	        }
	      }
	    });
	clearButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        drawPad.clear();
      }
    });
    
	
	
	//button.add(sizeLabel,BorderLayout.EAST);
	
	//button.add(colorView);
	button.add(clearButton);
	button.add(size,BorderLayout.SOUTH);
    scroll.add(r);scroll.add(rLabel);
    scroll.add(g);scroll.add(gLabel);
    scroll.add(b);scroll.add(bLabel);
	
	a.add(scroll);
	a.add(button);
	
	frame.add(a, BorderLayout.SOUTH);
    //frame.add(button,BorderLayout.EAST);
	frame.setSize(600, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //colorView.setBackground(new Color(drawPad.rVal,drawPad.gVal,drawPad.bVal));
   // drawPad.repaint();
    frame.setVisible(true);
    
  }



  }
	
  class DrawingPad extends JComponent {
    Image image;
    Graphics2D graphics2D;
    int currentX, currentY, oldX, oldY;
    int rVal,gVal,bVal,sizeBrush;
    public DrawingPad() {
      setDoubleBuffered(false);
      
      addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
          oldX = e.getX();
          oldY = e.getY();
          
        }
      });
      addMouseMotionListener(new MouseMotionAdapter() {
        public void mouseDragged(MouseEvent e) {
          currentX = e.getX();
          currentY = e.getY();
         graphics2D.setColor(new Color(rVal,gVal,bVal));
          if (graphics2D != null)
        	  graphics2D.setStroke(new BasicStroke(sizeBrush));
        	  graphics2D.drawLine(oldX, oldY, currentX, currentY);
          repaint();
          oldX = currentX;
          oldY = currentY;
        }
      });
    }
   
	public void paintComponent(Graphics g) {
        if (image == null) {
          image = createImage(getSize().width, getSize().height);
          //g.fillOval(20, 40, 50, 50);
          graphics2D = (Graphics2D) image.getGraphics();
          graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
              RenderingHints.VALUE_ANTIALIAS_ON);
          clear();
        }
        g.drawImage(image, 0, 0, null);
      }

      public void clear() {
        graphics2D.setPaint(Color.white);
        graphics2D.fillRect(0, 0, getSize().width, getSize().height);
        graphics2D.setPaint(Color.black);
        repaint();
      }
    }