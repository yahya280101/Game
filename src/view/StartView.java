package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class StartView extends JFrame {
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private JButton b1; 
	private JButton b2;
	private JButton b3;
	private JButton b4;
	private JTextField namePlayer;

	public JPanel getP1() {
		return p1;
	}

	public void setP1(JPanel p1) {
		this.p1 = p1;
	}

	public JPanel getP2() {
		return p2;
	}

	public void setP2(JPanel p2) {
		this.p2 = p2;
	}

	public JPanel getP3() {
		return p3;
	}

	public void setP3(JPanel p3) {
		this.p3 = p3;
	}

	public JButton getB1() {
		return b1;
	}

	public void setB1(JButton b1) {
		this.b1 = b1;
	}

	public JButton getB2() {
		return b2;
	}

	public void setB2(JButton b2) {
		this.b2 = b2;
	}

	public JButton getB3() {
		return b3;
	}

	public void setB3(JButton b3) {
		this.b3 = b3;
	}

	public JButton getB4() {
		return b4;
	}

	public void setB4(JButton b4) {
		this.b4 = b4;
	}
	public JTextField getNamePlayer() {
		return namePlayer;
	}

	public void setNamePlayer(JTextField namePlayer) {
		this.namePlayer = namePlayer;
	}

	public StartView() {
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Empire");
		ImageIcon logo = new ImageIcon("images/Logo.png");
		ImageIcon romeLogo = new ImageIcon("images/Rome-Logo.png");
		ImageIcon cairoLogo = new ImageIcon("images/Cairo-Logo.png");
		ImageIcon spartaLogo = new ImageIcon("images/Sparta-Logo.png");
		ImageIcon startButton = new ImageIcon("images/Start-Button.png");
		this.setIconImage(logo.getImage());
		b1 = new JButton();
		b1.setText("Cairo");
		b1.setIcon(cairoLogo);
		b1.setHorizontalTextPosition(JButton.CENTER);
		b1.setVerticalTextPosition(JButton.BOTTOM);
		b1.setIconTextGap(20);
		b1.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		b1.setBackground(new Color(226, 227, 202));
	    b2 = new JButton("Sparta");
	    b2.setIcon(spartaLogo);
	    b2.setHorizontalTextPosition(JButton.CENTER);
		b2.setVerticalTextPosition(JButton.BOTTOM);
		b2.setIconTextGap(20);
		b2.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		b2.setBackground(new Color(226, 227, 202));
	    b3 = new JButton("Rome");
	    b3.setIcon(romeLogo);
	    b3.setHorizontalTextPosition(JButton.CENTER);
		b3.setVerticalTextPosition(JButton.BOTTOM);
		b3.setIconTextGap(20);
		b3.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		b3.setBackground(new Color(226, 227, 202));
	    b4 = new JButton("Start");
	    b4.setIcon(startButton);
	    b4.setHorizontalTextPosition(JButton.CENTER);
		b4.setVerticalTextPosition(JButton.BOTTOM);
		b4.setIconTextGap(-15);
		b4.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		b4.setBackground(new Color(255, 255, 255));
		b4.setBorder(BorderFactory.createEtchedBorder());
	    JLabel l = new JLabel("Player Name: ");
	    l.setFont(new Font("Book Antiqua", Font.BOLD, 30));
		l.setBounds(10, 20, 80, 25);
		namePlayer = new JTextField(20);
		namePlayer.setBounds(100, 20, 165, 25);
		namePlayer.setFont(new Font("Book Antiqua", Font.BOLD, 30));
		JLabel label = new JLabel();
		label.setIcon(logo);
		p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		p1.add(l);
		p1.add(namePlayer);
		JPanel temp = new JPanel();
		temp.setPreferredSize(new Dimension(WIDTH, 325));
		temp.add(label);
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(temp, BorderLayout.NORTH);
		this.getContentPane().add(p1, BorderLayout.CENTER);
		p2 = new JPanel();
		p3 = new JPanel();
		p2.setLayout(new GridLayout(1, 3, 5, 5));
		p2.add(b1);
		p2.add(b2);
		p2.add(b3);
		p3.setLayout(new GridLayout(1, 1));
		p3.add(b4);
		JPanel p4 = new JPanel();
		p4.setLayout(new GridLayout(1, 2, 10, 10));
		p4.add(p2);
		p4.add(p3);
		p4.setPreferredSize(new Dimension(WIDTH, 250));
		this.getContentPane().add(p4, BorderLayout.SOUTH);
		this.setBackground(new Color(255, 255, 255));
		this.validate();
		this.repaint();
	}	

}