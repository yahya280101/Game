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
import exceptions.FriendlyCityException;
import exceptions.MaxCapacityException;
import exceptions.TargetNotReachedException;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Status;
import units.Unit;
import view.ArmyView;
import view.InfoPanel;
import view.UnitWindow;

public class ArmyControl implements ActionListener{
	public ArmyView getView() {
		return view;
	}


	public void setView(ArmyView view) {
		this.view = view;
	}


	public Game getG() {
		return g;
	}


	public void setG(Game g) {
		this.g = g;
	}


	public JButton getRelocate() {
		return relocate;
	}


	public void setRelocate(JButton relocate) {
		this.relocate = relocate;
	}


	public ArrayList<JButton> getUnits() {
		return units;
	}


	public void setUnits(ArrayList<JButton> units) {
		this.units = units;
	}


	public JButton getN() {
		return n;
	}


	public void setN(JButton n) {
		this.n = n;
	}


	public boolean isClicked1() {
		return clicked1;
	}


	public void setClicked1(boolean clicked1) {
		this.clicked1 = clicked1;
	}


	public City getC() {
		return c;
	}


	public void setC(City c) {
		this.c = c;
	}


	public Unit getSelectedUnit() {
		return selectedUnit;
	}


	public void setSelectedUnit(Unit selectedUnit) {
		this.selectedUnit = selectedUnit;
	}


	public Army getArmy() {
		return army;
	}


	public void setArmy(Army army) {
		this.army = army;
	}


	public JButton getInit() {
		return init;
	}


	public void setInit(JButton init) {
		this.init = init;
	}


	public JButton getCairo() {
		return cairo;
	}


	public void setCairo(JButton cairo) {
		this.cairo = cairo;
	}


	public JButton getRome() {
		return rome;
	}


	public void setRome(JButton rome) {
		this.rome = rome;
	}


	public JButton getSparta() {
		return sparta;
	}


	public void setSparta(JButton sparta) {
		this.sparta = sparta;
	}


	public JPanel getDownPanel() {
		return downPanel;
	}


	public void setDownPanel(JPanel downPanel) {
		this.downPanel = downPanel;
	}


	public JPanel getCities() {
		return cities;
	}


	public void setCities(JPanel cities) {
		this.cities = cities;
	}


	public JPanel getCommands() {
		return commands;
	}


	public void setCommands(JPanel commands) {
		this.commands = commands;
	}


	public JButton getAttack() {
		return attack;
	}


	public void setAttack(JButton attack) {
		this.attack = attack;
	}

	public JButton getTarget() {
		return target;
	}


	public void setTarget(JButton target) {
		this.target = target;
	}


	public boolean isClicked2() {
		return clicked2;
	}


	public void setClicked2(boolean clicked2) {
		this.clicked2 = clicked2;
	}


	public JButton getSaveCity() {
		return saveCity;
	}


	public void setSaveCity(JButton saveCity) {
		this.saveCity = saveCity;
	}


	private ArmyView view;
	private Game g;
	private JButton relocate;
	private ArrayList<JButton> units;
	private JButton n;
	private boolean clicked1;
	private City c;
	private Unit selectedUnit;
	private Army army;
	private JButton init;
	private JButton cairo;
	private JButton rome;
	private JButton sparta;
	private JPanel downPanel;
	private JPanel cities;
	private JPanel commands;
	private JButton attack;
	private JButton target;
	private boolean clicked2;
	private JButton saveCity;
	private JButton back;
	private WorldMapControl worldMapControl;
	
	public ArmyControl(Army army,WorldMapControl worldMapControl) {
		this.saveCity = new JButton();
		this.worldMapControl=worldMapControl;
		this.saveCity = null;
     	this.downPanel = new JPanel();
		this.cities = new JPanel();
		this.cairo = new JButton("Cairo");
		this.rome = new JButton("Rome");
		this.sparta = new JButton("Sparta");
		this.attack = new JButton("Attack");
		this.target = new JButton("Target");
		this.back=new JButton("Back");
		cairo.addActionListener(this);
		rome.addActionListener(this);
		sparta.addActionListener(this);
		attack.addActionListener(this);
		target.addActionListener(this);
		back.addActionListener(this);
		this.commands = new JPanel();
		this.commands.setLayout(new GridLayout());
		commands.add(attack);
		commands.add(target);
		commands.add(back);
		view= new ArmyView(worldMapControl);
		this.g=worldMapControl.getG();
		this.army=army;
		selectedUnit=null;
		n=null;
		init=null;
		units=new ArrayList<JButton>();
		JPanel Hn= new JPanel();
		Hn.setLayout(new FlowLayout());
		relocate=new JButton();
		relocate.setText("RelocateUnit To");
		relocate.setEnabled(false);
		relocate.setActionCommand("RelocateUnit To");
		relocate.addActionListener(this);
		Hn.add(relocate);
		cities.setLayout(new FlowLayout());
		cities.add(cairo);
		cities.add(sparta);
		cities.add(rome);
		downPanel.setLayout(new FlowLayout());
		downPanel.add(commands);
		downPanel.add(cities);
		String x="";
		for(int i=0;i< g.getPlayer().getControlledArmies().size();i++) {
			if(g.getPlayer().getControlledArmies().size()==(i-1))
				x+="Army"+(i+1);
				else	
			x+="Army"+(i+1)+" ";
					
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
			a.setPreferredSize(new Dimension(150, 150));
			a.setText("Unit "+(i+1));
			a.addActionListener(this);
			a.setToolTipText(army.getUnits().get(i).toString());
			units.add(a);
			Un.add(a);
			if(army.getCurrentStatus()== Status.MARCHING) {
				attack.setEnabled(false);
				target.setEnabled(false);
				for(JButton j : units) {
					j.setEnabled(false);
				}
			}
			if(army.getCurrentStatus() == Status.BESIEGING) {
				target.setEnabled(false);
				for(JButton j : units) {
					j.setEnabled(false);
				}
			}
			for(City city : g.getPlayer().getControlledCities()) {
				if(city.getName().equalsIgnoreCase("Cairo")) {
					cairo.setEnabled(false);
				}
				if(city.getName().equalsIgnoreCase("Sparta")) {
					sparta.setEnabled(false);
				}
				if(city.getName().equalsIgnoreCase("Rome")) {
					rome.setEnabled(false);
				}
			}
		}
		JTextArea b= new JTextArea();
		b.setPreferredSize(new Dimension(300, 300));
		b.setFont(new Font("Serif", Font.ITALIC, 22));
		b.setText(army.toString());
		b.setEditable(false);
		In.setPreferredSize(new Dimension(this.getWorldMapControl().getView().getWidth(), 300));
		In.add(b);
		view.add(In);
		view.add(Un);
		view.add(Hn);
		view.add(downPanel);
		view.validate();
		view.repaint();
		this.worldMapControl.getStartViewControl().getView().getContentPane().removeAll();
		this.worldMapControl.getStartViewControl().getView().getContentPane().add(view);
		this.worldMapControl.getStartViewControl().getView().validate();
		this.worldMapControl.getStartViewControl().getView().repaint();
	}

@Override
public void actionPerformed(ActionEvent e) {
	if(units.contains(e.getSource())) {
		if(clicked1==true)
			JOptionPane.showMessageDialog(view, "Select an Army to relocate ur unit to", "Error", JOptionPane.ERROR_MESSAGE);
		else	if(n==e.getSource()) {
				n=null;
			relocate.setEnabled(false);
		}else {
			n=(JButton) e.getSource();
			for(int j=0;j<units.size();j++) {
				if(units.get(j)==n)
					selectedUnit= army.getUnits().get(j);

		relocate.setEnabled(true);			
			}}		
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
			  int s = Integer.parseInt(a.charAt(4)+"");
			  try {
				g.getPlayer().getControlledArmies().get(s-1).relocateUnit(selectedUnit);
			} catch (MaxCapacityException e1) {
				JOptionPane.showMessageDialog(view, "The army you selected is at max capacity", "Error", JOptionPane.ERROR_MESSAGE);
			}
			  relocate.setEnabled(false);
			  n.setEnabled(false);
			  n=null;
			  clicked1=false;
			  view.revalidate();
			  view.repaint();
		  }	
	else
	if(e.getActionCommand().equals("Initiate Army")){
		g.getPlayer().initiateArmy(c, selectedUnit);
		 relocate.setEnabled(false);
		  n.setEnabled(false);
		  n=null;
		  init.setEnabled(false);
		  clicked1=false;
		  view.revalidate();
		  view.repaint();

} else {
	if(e.getSource() == cairo || e.getSource() == rome || e.getSource() == sparta) {
		if(e.getSource() == saveCity) {
			clicked2 = false;
			saveCity = null;
		} else {
			clicked2 = true;
			saveCity = (JButton) e.getSource();
		}
	}
}
	if(e.getSource() == attack && clicked2) {
		if(army.getCurrentLocation().equals(saveCity.getText())) {
			for(City city : g.getAvailableCities()) {
				if(city.getName().equalsIgnoreCase(saveCity.getText())) {
					if(city.isUnderSiege()) {
						army.setCurrentStatus(Status.IDLE);
					}
					new BattleControl(g, army, city,worldMapControl);
				}
			}
		} else {
			JOptionPane.showMessageDialog(view, "The target is not reached yet", "Error", JOptionPane.ERROR_MESSAGE);
		}
		clicked2 = false;
		saveCity = null;
		view.revalidate();
		view.repaint();
	} else if(e.getSource() == target && clicked2) {
		g.targetCity(army, saveCity.getText());
		clicked2 = false;
		saveCity = null;
		view.revalidate();
		view.repaint();
        	}
	if(e.getSource()==back) {
   	 worldMapControl.getView().getContentPane().removeAll();
   	 worldMapControl.getView().getContentPane().add(new InfoPanel(this.worldMapControl),BorderLayout.NORTH);
   	 WorldMapControl trans= new WorldMapControl(worldMapControl.getStartViewControl());
   	 worldMapControl.getView().getContentPane().add(trans.getWorldMap().getF());
	 worldMapControl.setHere(true);
   	 worldMapControl.getView().revalidate();
   	 worldMapControl.getView().repaint();
		}
}

public WorldMapControl getWorldMapControl() {
	return worldMapControl;
}


public void setWorldMapControl(WorldMapControl worldMapControl) {
	this.worldMapControl = worldMapControl;
}


public JButton getBack() {
	return back;
}


public void setBack(JButton back) {
	this.back = back;
}
}