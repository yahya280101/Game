package control;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import engine.City;
import engine.Game;
import exceptions.MaxCapacityException;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Unit;
import view.InfoPanel;
import view.UnitWindow;

public class UnitControl implements ActionListener{
	private UnitWindow view;
	private Game g;
	private JButton relocate;
	private JComboBox<String> armies;
	private ArrayList<JButton> units;
	private JButton n;
	private boolean clicked1;
	private City c;
	private Unit selectedUnit;
	private Army army;
	private JButton init;
	private CityControl cityControl;
	private JButton back;
	public UnitControl(Army army,Game g,City c,CityControl cityControl) {
		this.cityControl=cityControl;
		view= new UnitWindow();
		this.g=g;
		this.c=c;
		this.army=army;
		selectedUnit=null;
		n=null;
		init=null;
		units=new ArrayList<JButton>();
		JPanel Hn= new JPanel();
		Hn.setLayout(new FlowLayout());
		if(c.getDefendingArmy()==army) {
			init=new JButton();
			init.setText("Initiate Army");
			init.setEnabled(false);
			init.setActionCommand("Initiate Army");
			init.addActionListener(this);
			Hn.add(init);}
		relocate=new JButton();
		relocate.setText("RelocateUnit To");
		relocate.setEnabled(false);
		relocate.setActionCommand("RelocateUnit To");
		relocate.addActionListener(this);
		this.back=new JButton("Back");
        back.addActionListener(this);
		Hn.add(relocate);
		Hn.add(back);
		String x="defendingArmy";
		for(int i=0;i< g.getPlayer().getControlledArmies().size();i++) {
				x+=" Army"+(i+1);
			}
		String[] xs=x.split(" ");
		JComboBox<String> combo= new JComboBox<String>(xs);
		combo.setPreferredSize(new Dimension(200,20));
		combo.addActionListener(this);
		Hn.add(combo);
		JPanel Un= new JPanel();
		Un.setLayout(new GridLayout(0, 5));
		JPanel In= new JPanel();
		In.setLayout(new FlowLayout());
		for (int i=0;i<army.getUnits().size();i++) {
			JButton a= new JButton();
			a.setPreferredSize(new Dimension(150, 250));
			a.setText("Unit "+(i+1));
			a.addActionListener(this);
			a.setToolTipText(army.getUnits().get(i).toString());
			units.add(a);
			Un.add(a);
		}
		JTextArea b= new JTextArea();
		b.setPreferredSize(new Dimension(300, 280));
		b.setFont(new Font("Serif", Font.ITALIC, 22));
		b.setText(army.toString());
		b.setEditable(false);
		In.add(b);
		view.add(In);
		view.add(Un);
		view.add(Hn);
		view.validate();
		view.repaint();
		cityControl.getWorldMapControl().getStartViewControl().getView().getContentPane().removeAll();
		cityControl.getWorldMapControl().getStartViewControl().getView().getContentPane().add(view);
		cityControl.getWorldMapControl().getStartViewControl().getView().getContentPane().revalidate();
		cityControl.getWorldMapControl().getStartViewControl().getView().getContentPane().repaint();
	}

@Override
public void actionPerformed(ActionEvent e) {
	if(units.contains(e.getSource())) {
		if(clicked1==true)
			JOptionPane.showMessageDialog(view, "Select an Army to relocate ur unit to", "Error", JOptionPane.ERROR_MESSAGE);
		else	if(n==e.getSource()) {
				n=null;
				if(c.getDefendingArmy()==army)
				init.setEnabled(true);
				relocate.setEnabled(false);
				selectedUnit=null;
		}
		else {
			
			n=(JButton) e.getSource();
			for(int j=0;j<units.size();j++) {
				if(units.get(j)==n)
					selectedUnit= army.getUnits().get(j);
			}
			if(c.getDefendingArmy()==army) 
			init.setEnabled(true);
			relocate.setEnabled(true);
		
		}		
		
	}else if(e.getActionCommand().equals("RelocateUnit To")) {
		if (clicked1==true) {
			clicked1=false;
			if(c.getDefendingArmy()==army)
			init.setEnabled(true);

		}else {
			clicked1=true;
			if(c.getDefendingArmy()==army)
			init.setEnabled(false);

		}
	}else if((e.getSource() instanceof JComboBox) && clicked1==true){
		  JComboBox lel = (JComboBox) e.getSource();
		  String a= (String) lel.getSelectedItem();
		  if(a.equals("defendingArmy")) {
			  try {
				c.getDefendingArmy().relocateUnit(selectedUnit);
				 n.setEnabled(false);
				  units.remove(n);
			} catch (MaxCapacityException e1) {
				JOptionPane.showMessageDialog(view, "The army you selected is at max capacity", "Error", JOptionPane.ERROR_MESSAGE);
			}
			  relocate.setEnabled(false);
			 
			  n=null;
			  clicked1=false;
			  view.validate();
			  view.repaint();
			 
		  }else {
			  int s = Integer.parseInt(a.charAt(4)+"");
			  try {
				g.getPlayer().getControlledArmies().get(s-1).relocateUnit(selectedUnit);
				  n.setEnabled(false);
				  units.remove(n);
			} catch (MaxCapacityException e1) {
				JOptionPane.showMessageDialog(view, "The army you selected is at max capacity", "Error", JOptionPane.ERROR_MESSAGE);
			}
			  relocate.setEnabled(false);
			
			  n=null;
			  clicked1=false;
			  view.revalidate();
			  view.repaint();
		  }
	
	
}    else if(e.getActionCommand().equals("Initiate Army")){
		g.getPlayer().initiateArmy(c, selectedUnit);
		 relocate.setEnabled(false);
		  n.setEnabled(false);
		  units.remove(n);
		  n=null;
		  init.setEnabled(false);
		  clicked1=false;
		  view.revalidate();
		  view.repaint();
		WorldMapControl worldMapControl=cityControl.getWorldMapControl();
	    worldMapControl.getView().getContentPane().removeAll();
  		UnitControl trans= new UnitControl(this.army,this.g,this.c,this.cityControl);
  		worldMapControl.getView().getContentPane().add(trans.getView());
  		worldMapControl.getView().revalidate();
  		worldMapControl.getView().repaint();
  	
		  selectedUnit=null;
}
	if(e.getSource()==back) {
		 WorldMapControl worldMapControl=cityControl.getWorldMapControl();
		 worldMapControl.getView().getContentPane().removeAll();
	   	 CityControl trans= new CityControl(worldMapControl);
	   	 worldMapControl.getView().getContentPane().add(trans.getCityView());
	   	 worldMapControl.getView().revalidate();
	   	 worldMapControl.getView().repaint();
	}
}

public UnitWindow getView() {
	return view;
}


public void setView(UnitWindow view) {
	this.view = view;
}



public CityControl getCityControl() {
	return cityControl;
}


public void setCityControl(CityControl cityControl) {
	this.cityControl = cityControl;
}


public JButton getBack() {
	return back;
}


public void setBack(JButton back) {
	this.back = back;
}}