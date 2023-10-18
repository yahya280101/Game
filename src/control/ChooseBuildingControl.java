package control;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import buildings.ArcheryRange;
import buildings.Barracks;
import buildings.EconomicBuilding;
import buildings.Farm;
import buildings.Market;
import buildings.MilitaryBuilding;
import buildings.Stable;
import engine.City;
import engine.Game;
import exceptions.NotEnoughGoldException;
import view.InfoPanel;

public class ChooseBuildingControl implements ActionListener {
	private JButton archeryRangeButton; 
	private JButton barracksButton; 
	private JButton stableButton;
	private JButton farmButton;
	private JButton marketButton;
	private JFrame choose;
	private Game g;


	public JButton getMarketButton() {
		return marketButton;
	}
	public void setMarketButton(JButton marketButton) {
		this.marketButton = marketButton;
	}

	private CityControl cityControl;
	 
	public JButton getArcheryRangeButton() {
		return archeryRangeButton;
	}
	public void setArcheryRangeButton(JButton archeryRangeButton) {
		this.archeryRangeButton = archeryRangeButton;
	}
	public JButton getBarracksButton() {
		return barracksButton;
	}
	public void setBarracksButton(JButton barracksButton) {
		this.barracksButton = barracksButton;
	}
	public JButton getStableButton() {
		return stableButton;
	}
	public void setStableButton(JButton stableButton) {
		this.stableButton = stableButton;
	}

	public JButton getFarmButton() {
		return farmButton;
	}
	public void setFarmButton(JButton farmButton) {
		this.farmButton = farmButton;
	}
	public Game getG() {
		return g;
	}
	public void setG(Game g) {
		this.g = g;
	}
	public ChooseBuildingControl(CityControl cityControl) {
		this.cityControl=cityControl;
		this.g=cityControl.getG();
		archeryRangeButton = new JButton("Archery Range");
	    barracksButton = new JButton("Barracks");
		stableButton= new JButton("Stable");
		stableButton= new JButton("Stable");
		farmButton= new JButton("Farm");
		marketButton= new JButton("Market");
		choose = new JFrame();
		choose.setVisible(true);
		choose.setLayout(new GridLayout());
		choose.setPreferredSize(new Dimension(800, 200));
		choose.setLocation(248, 247);
		choose.pack();
		choose.setResizable(false);
		choose.add(archeryRangeButton);
		choose.add(barracksButton);
		choose.add(stableButton);
		choose.add(farmButton);
		choose.add(marketButton);
		choose.revalidate();
		choose.repaint();
		archeryRangeButton.addActionListener(this);
		barracksButton.addActionListener(this);
		stableButton.addActionListener(this);
		farmButton.addActionListener(this);
		marketButton.addActionListener(this);
			}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==archeryRangeButton) {
			boolean found = false;
				try {
					for(City c : cityControl.getG().getPlayer().getControlledCities()) {
						if(c.getName().equalsIgnoreCase(cityControl.getCityName())) {
							for(MilitaryBuilding b : c.getMilitaryBuildings()) {
								if(b instanceof ArcheryRange) {
									found = true;
									JOptionPane.showMessageDialog(this.cityControl.getCityView(), "This building already exists", "Error", JOptionPane.ERROR_MESSAGE);
								}
							}
						}
					}
					if(!found) {
						cityControl.getG().getPlayer().build("ArcheryRange",cityControl.getCityName());
						CityControl trans= new CityControl(cityControl.getWorldMapControl());
						choose.setVisible(false);
						trans.getWorldMapControl().getStartViewControl().getView().revalidate();
						trans.getWorldMapControl().getStartViewControl().getView().repaint();
					}
				} catch (NotEnoughGoldException e1) {
					JOptionPane.showMessageDialog(cityControl.getCityView(), "You don't have enough gold", "Error", JOptionPane.ERROR_MESSAGE);
				}
	}
		else if(e.getSource()==barracksButton) {
			boolean found = false;
			try {
				for(City c : cityControl.getG().getPlayer().getControlledCities()) {
					if(c.getName().equalsIgnoreCase(cityControl.getCityName())) {
						for(MilitaryBuilding b : c.getMilitaryBuildings()) {
							if(b instanceof Barracks) {
								found = true;
								JOptionPane.showMessageDialog(this.cityControl.getCityView(), "This building already exists", "Error", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}
				if(!found) {
					cityControl.getG().getPlayer().build("Barracks",cityControl.getCityName());
					CityControl trans= new CityControl(cityControl.getWorldMapControl());
					choose.setVisible(false);
					trans.getWorldMapControl().getStartViewControl().getView().revalidate();
					trans.getWorldMapControl().getStartViewControl().getView().repaint();
				}
			} catch (NotEnoughGoldException e1) {
				JOptionPane.showMessageDialog(cityControl.getCityView(), "You don't have enough gold", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
			else if(e.getSource()==stableButton) {
				boolean found = false;
				try {
					for(City c : cityControl.getG().getPlayer().getControlledCities()) {
						if(c.getName().equalsIgnoreCase(cityControl.getCityName())) {
							for(MilitaryBuilding b : c.getMilitaryBuildings()) {
								if(b instanceof Stable) {
									found = true;
									JOptionPane.showMessageDialog(this.cityControl.getCityView(), "This building already exists", "Error", JOptionPane.ERROR_MESSAGE);
								}
							}
						}
					}
					if(!found) {
						cityControl.getG().getPlayer().build("Stable",cityControl.getCityName());
						CityControl trans= new CityControl(cityControl.getWorldMapControl());
						choose.setVisible(false);
						trans.getWorldMapControl().getStartViewControl().getView().revalidate();
						trans.getWorldMapControl().getStartViewControl().getView().repaint();
					}
				} catch (NotEnoughGoldException e1) {
					JOptionPane.showMessageDialog(cityControl.getCityView(), "You don't have enough gold", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(e.getSource()== farmButton) {
				boolean found = false;
				try {
					for(City c : cityControl.getG().getPlayer().getControlledCities()) {
						if(c.getName().equalsIgnoreCase(cityControl.getCityName())) {
							for(EconomicBuilding b : c.getEconomicalBuildings()) {
								if(b instanceof Farm) {
									found = true;
									JOptionPane.showMessageDialog(this.cityControl.getCityView(), "This building already exists", "Error", JOptionPane.ERROR_MESSAGE);
								}
							}
						}
					}
					if(!found) {
						cityControl.getG().getPlayer().build("Farm",cityControl.getCityName());
						CityControl trans= new CityControl(cityControl.getWorldMapControl());
						choose.setVisible(false);
						trans.getWorldMapControl().getStartViewControl().getView().revalidate();
						trans.getWorldMapControl().getStartViewControl().getView().repaint();
					}
				} catch (NotEnoughGoldException e1) {
					JOptionPane.showMessageDialog(cityControl.getCityView(), "You don't have enough gold", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}else if(e.getSource()== marketButton) {
				boolean found = false;
				try {
					for(City c : cityControl.getG().getPlayer().getControlledCities()) {
						if(c.getName().equalsIgnoreCase(cityControl.getCityName())) {
							for(EconomicBuilding b : c.getEconomicalBuildings()) {
								if(b instanceof Market) {
									found = true;
									JOptionPane.showMessageDialog(this.cityControl.getCityView(), "This building already exists", "Error", JOptionPane.ERROR_MESSAGE);
								}
							}
						}
					}
					if(!found) {
						cityControl.getG().getPlayer().build("Market",cityControl.getCityName());
						CityControl trans= new CityControl(cityControl.getWorldMapControl());
						choose.setVisible(false);
						trans.getWorldMapControl().getStartViewControl().getView().revalidate();
						trans.getWorldMapControl().getStartViewControl().getView().repaint();
					}
				} catch (NotEnoughGoldException e1) {
					JOptionPane.showMessageDialog(cityControl.getCityView(), "You don't have enough gold", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
	}
}