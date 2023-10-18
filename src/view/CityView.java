package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class CityView extends JPanel{
	
	private JPanel Top;
	private JPanel Bot;
	private JPanel Right;
	private JPanel Left;
	private JPanel military;
	private JPanel economic;
	private JPanel commands;
	private JPanel combo;
	
public CityView(){
	this.setLayout(new BorderLayout());
	Top= new JPanel();
	Bot= new JPanel();
	Right= new JPanel();
	Left= new JPanel();
	military = new JPanel();
	economic = new JPanel();
	commands = new JPanel();
	combo = new JPanel();
	Top.setLayout(new GridLayout(1, 2));
	Bot.setLayout(new FlowLayout());
	Right.setLayout(new GridLayout(2, 0));
	Left.setLayout(new GridLayout(2, 0));
	this.add(Top, BorderLayout.NORTH);
	this.add(Bot, BorderLayout.SOUTH);
	this.add(Right, BorderLayout.EAST);
	this.add(Left, BorderLayout.CENTER);
	this.revalidate();
	this.repaint();
}
public JPanel getTop() {
	return Top;
}
public JPanel getBot() {
	return Bot;
}
public JPanel getRight() {
	return Right;
}
public JPanel getLeft() {
	return Left;
}

public void setTop(JPanel top) {
	Top = top;
}
public void setBot(JPanel bot) {
	Bot = bot;
}
public void setRight(JPanel right) {
	Right = right;
}
public void setLeft(JPanel left) {
	Left = left;
}
public JPanel getMilitary() {
	return military;
}
public void setMilitary(JPanel military) {
	this.military = military;
}
public JPanel getEconomic() {
	return economic;
}
public void setEconomic(JPanel economic) {
	this.economic = economic;
}
public JPanel getCommands() {
	return commands;
}
public void setCommands(JPanel commands) {
	this.commands = commands;
}
public JPanel getCombo() {
	return combo;
}
public void setCombo(JPanel combo) {
	this.combo = combo;
}
}