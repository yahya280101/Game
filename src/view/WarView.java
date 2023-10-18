package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
//import javafx.geometry.Dimension2D;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class WarView extends JFrame{
	private JPanel log;
	private JPanel all;
	private JPanel friend;
	private JPanel foe;
	private JPanel hit;
	private JTextArea t;
	
public WarView(){
	this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	this.setVisible(true);
	this.setTitle("Battle");
	log=new JPanel();
	friend=new JPanel();
	foe=new JPanel();
	hit=new JPanel();
	all=new JPanel();
	all.setLayout(new BorderLayout());
	t= new JTextArea();
	t.setText("Log \n -------------------\n");
	t.setPreferredSize(new Dimension(200, this.getHeight()));
	t.setEditable(false);
	log.setPreferredSize(new Dimension(200,this.HEIGHT));
	friend.setPreferredSize(new Dimension(this.WIDTH,(int) (0.4 * this.getHeight())));
	foe.setPreferredSize(new Dimension(this.WIDTH,(int) (0.4 * this.getHeight())));
	log.setLayout(new FlowLayout());
	hit.setLayout(new GridLayout());
	log.add(t);
	this.add(log, BorderLayout.EAST);
	all.add(foe, BorderLayout.NORTH);
	all.add(friend, BorderLayout.SOUTH);
	all.add(hit, BorderLayout.CENTER);
	this.add(all);
	this.validate();
	this.repaint();
}
public JPanel getAll() {
	return all;
}
public JTextArea getT() {
	return t;
}

public JPanel getLog() {
	return log;
}
public JPanel getFriend() {
	return friend;
}
public JPanel getFoe() {
	return foe;
}
public JPanel getHit() {
	return hit;
}
}
