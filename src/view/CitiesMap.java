package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CitiesMap extends JPanel {
	
	public JButton getCairo() {
		return Cairo;
	}

	public void setCairo(JButton cairo) {
		Cairo = cairo;
	}

	public JButton getSparta() {
		return Sparta;
	}

	public void setSparta(JButton sparta) {
		Sparta = sparta;
	}

	public JButton getRome() {
		return Rome;
	}

	public void setRome(JButton rome) {
		Rome = rome;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	private JPanel panel;
	private JButton Cairo;
	private JButton Sparta;
	private JButton Rome;

	public JPanel getPanel() {
		return panel;
	}

	public CitiesMap() {
         panel = new JPanel();
         panel.setLayout(new GridLayout());
         Cairo=new JButton();
         Sparta=new JButton();
         Rome=new JButton();
         Cairo.setText("Cairo");
         Sparta.setText("Sparta");
         Rome.setText("Rome");
         ImageIcon romeLogo = new ImageIcon("images/Rome-Logo.png");
 		 ImageIcon cairoLogo = new ImageIcon("images/Cairo-Logo.png");
 		 ImageIcon spartaLogo = new ImageIcon("images/Sparta-Logo.png");
 		 Rome.setIcon(romeLogo);
 		 Cairo.setIcon(cairoLogo);
 		 Sparta.setIcon(spartaLogo);
 		 Cairo.setHorizontalTextPosition(JButton.CENTER);
		 Cairo.setVerticalTextPosition(JButton.BOTTOM);
		 Cairo.setIconTextGap(20);
		 Rome.setHorizontalTextPosition(JButton.CENTER);
		 Rome.setVerticalTextPosition(JButton.BOTTOM);
		 Rome.setIconTextGap(20);
		 Sparta.setHorizontalTextPosition(JButton.CENTER);
		 Sparta.setVerticalTextPosition(JButton.BOTTOM);
		 Sparta.setIconTextGap(20);
         Cairo.setSize(400, 100);
         Sparta.setSize(400, 100);
         Rome.setSize(400, 100);
         panel.add(Cairo);
         panel.add(Rome);
         panel.add(Sparta);
	}

}