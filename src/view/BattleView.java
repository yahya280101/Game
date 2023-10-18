package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BattleView extends JFrame{
	private JPanel lext;
	private JPanel Buttons;
	public BattleView(){
		this.setBounds(350, 200, 400, 400);
		this.setVisible(true);
		lext=new JPanel();
		lext.setLayout(new FlowLayout());
		lext.setPreferredSize(new Dimension(this.WIDTH,150));
		Buttons=new JPanel();
		Buttons.setLayout(new GridLayout(1,0));
		this.add(lext, BorderLayout.NORTH);
		this.add(Buttons,BorderLayout.CENTER);
		this.validate();
		this.repaint();
		
	}
	public JPanel getLext() {
		return lext;
	}
	public JPanel getButtons() {
		return Buttons;
	}

}
