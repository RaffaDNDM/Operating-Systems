/**
@author Raffaele Di Nardo Di Maio
*/

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Search extends JFrame
{
    JButton b = new JButton("Press");
    JPanel center = new JPanel();
    Listen ascolt = new Listen(this);

    public Search()
    {
        super("Search");
        setVisible(true);
        setSize(350,350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage("Cristina.jpg"));

	setResizable(true);
        setLayout(new BorderLayout());
        center.add(b);
        getContentPane().add(center, BorderLayout.NORTH);
        b.addActionListener(ascolt);
	
        //Background back = new Background();
        //getContentPane().add(back);
    }

    class Listen implements ActionListener
    {
        Search f;

        public Listen(Search frame_par)
        {
            f = frame_par;
        }

        public void actionPerformed(ActionEvent e)
        {
	     f.setVisible(true);
	     f.setSize(350,350);
	     f.setDefaultCloseOperation(EXIT_ON_CLOSE);
	     f.setIconImage(Toolkit.getDefaultToolkit().getImage("Cristina.jpg"));
             remove(center);

		f.setLayout(new BorderLayout());

		String lista[]=new String[10];
		for(int i=0;i<lista.length;i++)
			lista[i]="Elemento numero "+i;

		JComboBox<String> cBox=new JComboBox<>(lista);
		f.getContentPane().add(cBox, BorderLayout.CENTER);

	     //PhotoList pl = new PhotoList(f);
	}
    }

    class Background extends JPanel
    {
        private Image img;

        public Background()
        {
            img = Toolkit.getDefaultToolkit().getImage("Cristina.jpg");
        }

        protected void paintComponent(Graphics g)
        {
            setOpaque(false);
            g.drawImage(img,0,0, getWidth(), getHeight(), null);
            super.paintComponent(g);
        }
    }

    public static void main(String[] args)
    {
        Search s = new Search();
    }
}
