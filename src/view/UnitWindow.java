package view;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class UnitWindow extends JPanel{
	
public UnitWindow() {
	this.setLayout(new GridLayout(3,1));
	this.validate();
	this.repaint();
}

}

