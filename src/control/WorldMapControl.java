package control;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import engine.City;
import engine.Game;
import units.Army;
import view.InfoPanel;
import view.StartView;
import view.WorldMap;

public class WorldMapControl implements ActionListener {
	private StartViewControl startViewControl;
	private WorldMap worldMap;
	private JButton b;
	private Game g;
	private StartView view;
	private String cityName="";
	private InfoPanel infoPanel;
	private boolean isHere;
	private Army chosenArmy;

	public WorldMap getWorldMap() {
		return worldMap;
	}

	public void setWorldMap(WorldMap worldMap) {
		this.worldMap = worldMap;
	}

	public JButton getB() {
		return b;
	}

	public void setB(JButton b) {
		this.b = b;
	}

	public Game getG() {
		return g;
	}

	public void setG(Game g) {
		this.g = g;
	}

	public StartView getView() {
		return view;
	}

	public void setView(StartView view) {
		this.view = view;
	}
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public WorldMapControl(StartViewControl startViewControl) {
		this.setHere(true);
		this.startViewControl=startViewControl;
		this.view =startViewControl.getView() ;
		this.g = startViewControl.getG();
		this.worldMap = new WorldMap(g);
		this.infoPanel=new InfoPanel(this);
		view.getContentPane().removeAll();
		view.getContentPane().add(infoPanel,BorderLayout.NORTH);
		view.getContentPane().add(worldMap.getF());
		view.revalidate();
		view.repaint();
		b = null;
		worldMap.getM().getCairo().addActionListener(this);
		worldMap.getM().getSparta().addActionListener(this);
		worldMap.getM().getRome().addActionListener(this);
		for(int i = 0; i < worldMap.getP().getIdleButtons().size(); i++) {
			worldMap.getP().getIdleButtons().get(i).addActionListener(this);
		}
		for(int i = 0; i < worldMap.getP().getNonIdleButtons().size(); i++) {
			worldMap.getP().getNonIdleButtons().get(i).addActionListener(this);
		}
	}

	public InfoPanel getInfoPanel() {
		return infoPanel;
	}

	public void setInfoPanel(InfoPanel infoPanel) {
		this.infoPanel = infoPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		 if(g.isGameOver()) {
             if(g.getPlayer().getControlledCities().size() == g.getAvailableCities().size()) {
                       JOptionPane.showMessageDialog(this.getStartViewControl().getView(), "You beat the game and became the supreme conquerer","Congrats", JOptionPane.DEFAULT_OPTION);
                       this.getStartViewControl().getView().setVisible(false);
                       System.exit(0);
             }
             
			 else {
                       JOptionPane.showMessageDialog(this.getStartViewControl().getView(), "You were not able to beat the game","HardLuck!!", JOptionPane.DEFAULT_OPTION);
                       this.getStartViewControl().getView().setVisible(false);
                       System.exit(0);
			 }
             }
		
		if(e.getSource() == worldMap.getM().getCairo()) {
			boolean found = false;
			for(int i = 0; i < worldMap.getG().getPlayer().getControlledCities().size(); i++) {
				if(worldMap.getG().getPlayer().getControlledCities().get(i).getName().equalsIgnoreCase("cairo")) {
					found = true;
					this.cityName="cairo";
					setHere(false);
					CityControl cairoView = new CityControl(this);
				}
			}
			if(!found) {
				JOptionPane.showMessageDialog(this.worldMap, "This city is not available", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if(e.getSource() == worldMap.getM().getSparta()) {
			boolean found = false;
			for(int i = 0; i < worldMap.getG().getPlayer().getControlledCities().size(); i++) {
				if(worldMap.getG().getPlayer().getControlledCities().get(i).getName().equalsIgnoreCase("sparta")) {
					found = true;
					this.cityName="sparta";
					setHere(false);
					CityControl spartaView = new CityControl(this);
				}
			}
			if(!found) {
				JOptionPane.showMessageDialog(this.worldMap, "This city is not available", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if(e.getSource() == worldMap.getM().getRome()) {
			boolean found = false;
			for(int i = 0; i < worldMap.getG().getPlayer().getControlledCities().size(); i++) {
				if(worldMap.getG().getPlayer().getControlledCities().get(i).getName().equalsIgnoreCase("rome")) {
					found = true;
					this.cityName="rome";
					setHere(false);
					CityControl romeView = new CityControl(this);
				}
			}
			if(!found) {
				JOptionPane.showMessageDialog(this.worldMap, "This city is not available", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(worldMap.getP().getAllBottuns().contains(e.getSource())) {

			String a=((JButton)e.getSource()).getText();
			int s = Integer.parseInt(a.charAt(4)+"");
			Army chosenArmy=g.getPlayer().getControlledArmies().get(s-1);
			setHere(false);
			new ArmyControl(chosenArmy,this);
		}
	}

	public StartViewControl getStartViewControl() {
		return startViewControl;
	}

	public void setStartViewControl(StartViewControl startViewControl) {
		this.startViewControl = startViewControl;
	}
	
   public void refreshWorldMapControl() {
	   new WorldMapControl(this.getStartViewControl());
	   this.getStartViewControl().getView().revalidate();
	   this.getStartViewControl().getView().repaint();

   }

public boolean isHere() {
	return isHere;
}

public void setHere(boolean isHere) {
	this.isHere = isHere;
}

public Army getChosenArmy() {
	return chosenArmy;
}

public void setChosenArmy(Army chosenArmy) {
	this.chosenArmy = chosenArmy;
}
}