package control;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Popup;
import javax.swing.border.Border;

import buildings.ArcheryRange;
import buildings.Barracks;
import buildings.Building;
import buildings.EconomicBuilding;
import buildings.Farm;
import buildings.Market;
import buildings.MilitaryBuilding;
import buildings.Stable;
import engine.City;
import engine.Game;
import engine.Player;
import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import exceptions.NotEnoughGoldException;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Unit;
import view.CityView;
import view.InfoPanel;
import view.StartView;

public class CityControl implements ActionListener{
	private WorldMapControl worldMapControl;
	private CityView cityView;
	private String cityName;
	private JButton b;
	private ArrayList<Building> MilB;
	private ArrayList<Army> armies;
	private UnitControl Ucontrol;
	private InfoPanel infoPanel;
	private ChooseBuildingControl chooseBuildingControl;
	private City city;
	private JButton endTurnButton;
	private Game g;

	public ChooseBuildingControl getChooseBuildingControl() {
		return chooseBuildingControl;
	}
	public void setChooseBuildingControl(ChooseBuildingControl chooseBuildingControl) {
		this.chooseBuildingControl = chooseBuildingControl;
	}
	public CityControl (WorldMapControl worldMapControl) {
		for(int i=0;i<worldMapControl.getG().getPlayer().getControlledCities().size();i++) {
			if(worldMapControl.getG().getPlayer().getControlledCities().get(i).getName().equalsIgnoreCase(worldMapControl.getCityName())) {
				this.setCity(worldMapControl.getG().getPlayer().getControlledCities().get(i));

			}
			
		}
		Border militaryLabel = BorderFactory.createTitledBorder("Military Buildings");
		Border economicLabel = BorderFactory.createTitledBorder("Economic Buildings");
		this.worldMapControl = worldMapControl; 
		StartView view = worldMapControl.getView();
		cityView = new CityView();
		view.getContentPane().removeAll();
		view.getContentPane().add(cityView);
		view.revalidate();
		view.repaint();
		cityView.getBot().setPreferredSize(new Dimension(view.getWidth(), 50));
		cityView.getTop().setPreferredSize(new Dimension(view.getWidth(), 100));
		cityView.getRight().setPreferredSize(new Dimension((int) (0.5 * view.getWidth()), view.getHeight() - 150));
		cityView.getLeft().setPreferredSize(new Dimension((int) (0.5 * view.getWidth()), view.getHeight() - 150));
		cityView.getMilitary().setLayout(new GridLayout(0, 3, 5, 5));
		cityView.getEconomic().setLayout(new GridLayout(0, 2, 5, 5));
		cityView.getMilitary().setPreferredSize(new Dimension((int) (0.5 * view.getWidth()), (int) (0.5 * (view.getHeight() - 150))));
		cityView.getEconomic().setPreferredSize(new Dimension((int) (0.5 * view.getWidth()), (int) (0.5 * (view.getHeight() - 150))));
		cityView.getCombo().setLayout(new FlowLayout());
		cityView.getCombo().setPreferredSize(new Dimension((int) (0.5 * view.getWidth()), (int) (0.2 * (view.getHeight() - 150))));
		cityView.getCommands().setLayout(new GridLayout(0, 3, 5, 5));
		cityView.getCommands().setPreferredSize(new Dimension((int) (0.5 * view.getWidth()), (int) (0.8 * (view.getHeight() - 150))));
		b = null;
	    this.g=worldMapControl.getG();
		cityName = city.getName();
		Player p = g.getPlayer();
		MilB=new ArrayList<Building>();
		infoPanel = new InfoPanel(this.getWorldMapControl());
		cityView.getTop().add(infoPanel);
		cityView.validate();
		cityView.repaint();
		for(int i=0;i<city.getMilitaryBuildings().size();i++) {
			JButton Building=new JButton();
			MilitaryBuilding MB =city.getMilitaryBuildings().get(i);
			MilB.add(MB);
			Building.setToolTipText(MB.toString());
			String text="";
			if (MB instanceof ArcheryRange) {
				text="ArcheryRange";
}
			else 
				if(MB instanceof Barracks)
					text="Barracks";
				else 
					if(MB instanceof Stable)
						text="Stable";
			Building.setText(text);
			Building.setActionCommand(text);
			cityView.getMilitary().add(Building);
			Building.addActionListener(this);
		}
		for(int i=0;i<city.getEconomicalBuildings().size();i++) {
			JButton Building=new JButton();
			EconomicBuilding MB =city.getEconomicalBuildings().get(i);
			MilB.add(MB);
			Building.setToolTipText(MB.toString());
			String text="";
			if (MB instanceof Farm)
				text="Farm";
			else 
				if(MB instanceof Market)
					text="Market";	
			Building.setText(text);
			cityView.getEconomic().add(Building);
			Building.setActionCommand(text);
			Building.addActionListener(this);
		}
		JPanel mPanel = new JPanel();
		mPanel.add(cityView.getMilitary());
		mPanel.setBorder(militaryLabel);
		cityView.getLeft().add(mPanel);
		JPanel ePanel = new JPanel();
		ePanel.add(cityView.getEconomic());
		ePanel.setBorder(economicLabel);
		cityView.getLeft().add(ePanel);
		JButton UpgradeButton=new JButton("Upgrade");
		UpgradeButton.setActionCommand("Upgrade");
		cityView.getCommands().add(UpgradeButton);
		JButton RecruitButton=new JButton("Recruit");
		RecruitButton.setActionCommand("Recruit");
		cityView.getCommands().add(RecruitButton);
		JButton BuildButton=new JButton("Build");
		BuildButton.setActionCommand("Build");
		cityView.getCommands().add(BuildButton);
		JButton BackButton=new JButton("Back");
		BackButton.setActionCommand("Back");
		cityView.getBot().add(BackButton);
		UpgradeButton.addActionListener(this);
		BuildButton.addActionListener(this);
		RecruitButton.addActionListener(this);
		BackButton.addActionListener(this);
	
		String x="defendingArmy";
		armies= new ArrayList<Army>();
		armies.add(city.getDefendingArmy());
		
		for(int i=0;i< g.getPlayer().getControlledArmies().size();i++)
			if(g.getPlayer().getControlledArmies().get(i).getCurrentLocation().equals(city.getName())){
				armies.add(g.getPlayer().getControlledArmies().get(i));
				x+=" Army"+(i+1);
			}
		String[] xs=x.split(" ");
		JComboBox<String> combo= new JComboBox<String>(xs);
		combo.setPreferredSize(new Dimension(200,50));
		combo.addActionListener(this);
		cityView.getCombo().add(combo);
		cityView.getRight().add(cityView.getCombo());
		cityView.getRight().add(cityView.getCommands());
		view.validate();
		view.repaint();

	}
public CityView getCityView() {
		return cityView;
	}
	public void setCityView(CityView cityView) {
		this.cityView = cityView;
	}
	public JButton getB() {
		return b;
	}
	public void setB(JButton b) {
		this.b = b;
	}
	public ArrayList<Building> getMilB() {
		return MilB;
	}
	public void setMilB(ArrayList<Building> milB) {
		MilB = milB;
	}
	public ArrayList<Army> getArmies() {
		return armies;
	}
	public void setArmies(ArrayList<Army> armies) {
		this.armies = armies;
	}
	public UnitControl getUcontrol() {
		return Ucontrol;
	}
	public void setUcontrol(UnitControl ucontrol) {
		Ucontrol = ucontrol;
	}
	public WorldMapControl getWorldMapControl() {
		return worldMapControl;
	}
	public void setWorldMapControl(WorldMapControl worldMapControl) {
		this.worldMapControl = worldMapControl;
	}

	public Game getG() {
		return g;
	}
	public void setG(Game g) {
		this.g = g;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setInfoPanel(InfoPanel infoPanel) {
		this.infoPanel = infoPanel;
	}
	

@Override
public void actionPerformed(ActionEvent e) {
	if(b == null) {
		if(e.getActionCommand().equals("Upgrade") || e.getActionCommand().equals("Recruit")) {
			JOptionPane.showMessageDialog(this.getCityView(), "Please select a building", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	if (e.getActionCommand().equals("Upgrade")&& b!=null && !e.getActionCommand().equals("Recruit")) {
			String x=b.getActionCommand();
			switch(x) {
			case"ArcheryRange": for(int i=0;i<MilB.size();i++) {
				if(MilB.get(i) instanceof ArcheryRange) {
					try {
						worldMapControl.getG().getPlayer().upgradeBuilding(MilB.get(i));
					} catch (NotEnoughGoldException e1) {
						JOptionPane.showMessageDialog(this.getCityView(), "You don't have enough gold", "Error", JOptionPane.ERROR_MESSAGE);
					} catch(BuildingInCoolDownException e2) {
						JOptionPane.showMessageDialog(this.getCityView(), "The Building is in cool down! wait for the next turn", "Error", JOptionPane.ERROR_MESSAGE);
					} catch(MaxLevelException e3) {
						JOptionPane.showMessageDialog(this.getCityView(), "The building reached its maximum level", "Error", JOptionPane.ERROR_MESSAGE);
					}
					cityView.getTop().remove(infoPanel);
					infoPanel = new InfoPanel(this.getWorldMapControl());
					this.setInfoPanel(infoPanel);
					cityView.getTop().add(infoPanel);
					b.setToolTipText(MilB.get(i).toString());
					b.setBackground(null);
			b=null;
			cityView.revalidate();
			cityView.repaint();
				}
			
				
			}
			break;
			case"Barracks": for(int i=0;i<MilB.size();i++) {
				if(MilB.get(i) instanceof Barracks) {
					try {
						worldMapControl.getG().getPlayer().upgradeBuilding(MilB.get(i));
					} catch (NotEnoughGoldException e1) {
						JOptionPane.showMessageDialog(this.getCityView(), "You don't have enough gold", "Error", JOptionPane.ERROR_MESSAGE);
					} catch(BuildingInCoolDownException e2) {
						JOptionPane.showMessageDialog(this.getCityView(), "The Building is in cool down! wait for the next turn", "Error", JOptionPane.ERROR_MESSAGE);
					} catch(MaxLevelException e3) {
						JOptionPane.showMessageDialog(this.getCityView(), "The building reached its maximum level", "Error", JOptionPane.ERROR_MESSAGE);
					}
					cityView.getTop().remove(infoPanel);
					infoPanel = new InfoPanel(this.getWorldMapControl());
					cityView.getTop().add(infoPanel);					
					b.setToolTipText(MilB.get(i).toString());
					b.setBackground(null);
					b=null;
					cityView.revalidate();
    				cityView.repaint();}
			
				
			}
			break;
			case"Stable": for(int i=0;i<MilB.size();i++) {
				if(MilB.get(i) instanceof Stable) {
					try {
						worldMapControl.getG().getPlayer().upgradeBuilding(MilB.get(i));
					} catch (NotEnoughGoldException e1) {
						JOptionPane.showMessageDialog(this.getCityView(), "You don't have enough gold", "Error", JOptionPane.ERROR_MESSAGE);
					} catch(BuildingInCoolDownException e2) {
						JOptionPane.showMessageDialog(this.getCityView(), "The Building is in cool down! wait for the next turn", "Error", JOptionPane.ERROR_MESSAGE);
					} catch(MaxLevelException e3) {
						JOptionPane.showMessageDialog(this.getCityView(), "The building reached its maximum level", "Error", JOptionPane.ERROR_MESSAGE);
					}
					cityView.getTop().remove(infoPanel);
					infoPanel = new InfoPanel(this.getWorldMapControl());
					cityView.getTop().add(infoPanel);					
					b.setToolTipText(MilB.get(i).toString());
					b.setBackground(null);
					b=null;
					cityView.revalidate();
    				cityView.repaint();}
			
				
			}
			break;
			case"Market": for(int i=0;i<MilB.size();i++) {
				if(MilB.get(i) instanceof Market) {
					try {
						worldMapControl.getG().getPlayer().upgradeBuilding(MilB.get(i));
					} catch (NotEnoughGoldException e1) {
						JOptionPane.showMessageDialog(this.getCityView(), "You don't have enough gold", "Error", JOptionPane.ERROR_MESSAGE);
					} catch(BuildingInCoolDownException e2) {
						JOptionPane.showMessageDialog(this.getCityView(), "The Building is in cool down! wait for the next turn", "Error", JOptionPane.ERROR_MESSAGE);
					} catch(MaxLevelException e3) {
						JOptionPane.showMessageDialog(this.getCityView(), "The building reached its maximum level", "Error", JOptionPane.ERROR_MESSAGE);
					}
					cityView.getTop().remove(infoPanel);
					infoPanel = new InfoPanel(this.getWorldMapControl());
					cityView.getTop().add(infoPanel);
					b.setToolTipText(MilB.get(i).toString());
					b.setBackground(null);
					b=null;
					cityView.revalidate();
    				cityView.repaint();}
			
				
			}
			break;
			case"Farm": for(int i=0;i<MilB.size();i++) {
				if(MilB.get(i) instanceof Farm) {
					try {
						worldMapControl.getG().getPlayer().upgradeBuilding(MilB.get(i));
					} catch (NotEnoughGoldException e1) {
						JOptionPane.showMessageDialog(this.getCityView(), "You don't have enough gold", "Error", JOptionPane.ERROR_MESSAGE);
					} catch(BuildingInCoolDownException e2) {
						JOptionPane.showMessageDialog(this.getCityView(), "The Building is in cool down! wait for the next turn", "Error", JOptionPane.ERROR_MESSAGE);
					} catch(MaxLevelException e3) {
						JOptionPane.showMessageDialog(this.getCityView(), "The building reached its maximum level", "Error", JOptionPane.ERROR_MESSAGE);
					}
					cityView.getTop().remove(infoPanel);
					infoPanel = new InfoPanel(this.getWorldMapControl());
					cityView.getTop().add(infoPanel);						
					b.setToolTipText(MilB.get(i).toString());
					b.setBackground(null);
					b=null;
					cityView.revalidate();
    				cityView.repaint();}
			
				
			}
			break;
			}
			
	}
			else if(e.getActionCommand().equals("Recruit")&& b!=null&& !e.getActionCommand().equals("Upgrade")) {
				String x=b.getActionCommand();
				switch(x) {
				case"ArcheryRange": for(int i=0;i<MilB.size();i++) {
					if(MilB.get(i) instanceof ArcheryRange) {
						try {
							worldMapControl.getG().getPlayer().recruitUnit("Archer", cityName);
						} catch (BuildingInCoolDownException e1) {
							JOptionPane.showMessageDialog(this.getCityView(), "The Building is in cool down! wait for the next turn", "Error", JOptionPane.ERROR_MESSAGE);
						} catch(MaxRecruitedException e2) {
							JOptionPane.showMessageDialog(this.getCityView(), "You have recruited the max amound of units per turn! wait for the next turn", "Error", JOptionPane.ERROR_MESSAGE);
						} catch(NotEnoughGoldException e3) {
							JOptionPane.showMessageDialog(this.getCityView(), "You don't have enough gold", "Error", JOptionPane.ERROR_MESSAGE);
						}
						cityView.getTop().remove(infoPanel);
						infoPanel = new InfoPanel(this.getWorldMapControl());
						cityView.getTop().add(infoPanel);	
						b.setBackground(null);	
				b=null;
				cityView.revalidate();
				cityView.repaint();}
				
					
				}
				break;
				case"Barracks": for(int i=0;i<MilB.size();i++) {
					if(MilB.get(i) instanceof Barracks) {
						try {
							worldMapControl.getG().getPlayer().recruitUnit("Infantry", cityName);
						} catch (BuildingInCoolDownException e1) {
							JOptionPane.showMessageDialog(this.getCityView(), "The Building is in cool down! wait for the next turn", "Error", JOptionPane.ERROR_MESSAGE);
						} catch(MaxRecruitedException e2) {
							JOptionPane.showMessageDialog(this.getCityView(), "You have recruited the max amound of units per turn! wait for the next turn", "Error", JOptionPane.ERROR_MESSAGE);
						} catch(NotEnoughGoldException e3) {
							JOptionPane.showMessageDialog(this.getCityView(), "You don't have enough gold", "Error", JOptionPane.ERROR_MESSAGE);
						}
						cityView.getTop().remove(infoPanel);
						infoPanel = new InfoPanel(this.getWorldMapControl());
						cityView.getTop().add(infoPanel);	
						b.setBackground(null);
						b=null;
						cityView.revalidate();
	    				cityView.repaint();}
				
					
				}
				break;
				case"Stable": for(int i=0;i<MilB.size();i++) {
					if(MilB.get(i) instanceof Stable) {
						try {
							worldMapControl.getG().getPlayer().recruitUnit("Cavalry", cityName);
						} catch (BuildingInCoolDownException e1) {
							JOptionPane.showMessageDialog(this.getCityView(), "The Building is in cool down! wait for the next turn", "Error", JOptionPane.ERROR_MESSAGE);
						} catch(MaxRecruitedException e2) {
							JOptionPane.showMessageDialog(this.getCityView(), "You have recruited the max amound of units per turn! wait for the next turn", "Error", JOptionPane.ERROR_MESSAGE);
						} catch(NotEnoughGoldException e3) {
							JOptionPane.showMessageDialog(this.getCityView(), "You don't have enough gold", "Error", JOptionPane.ERROR_MESSAGE);
						}
						cityView.getTop().remove(infoPanel);
						infoPanel = new InfoPanel(this.getWorldMapControl());
						cityView.getTop().add(infoPanel);	
						b.setBackground(null);
						b=null;
						cityView.revalidate();
	    				cityView.repaint();}
				
					
				}
				break;
				default: JOptionPane.showMessageDialog(this.getCityView(), "You can't recruit from an economical building", "Error", JOptionPane.ERROR_MESSAGE);
				}	
}else if(e.getActionCommand().equals("Build") && !e.getActionCommand().equals("Upgrade") && !e.getActionCommand().equals("Recruit")) {
	chooseBuildingControl=new ChooseBuildingControl(this);
}
		else if(! (e.getSource() instanceof JComboBox)&& !e.getActionCommand().equals("Upgrade")&& !e.getActionCommand().equals("Recruit")&&!(e.getActionCommand().equals("End Turn"))){
			  if(e.getSource()==b) {
				  b.setBackground(null);
				  b=null;}
			  else {
				  if (b!=null) 
					b.setBackground(null);
				b=(JButton)e.getSource();
				b.setBackground(Color.GREEN);
			  }
		}
		else if(e.getSource() instanceof JComboBox){
			  JComboBox lel = (JComboBox) e.getSource();
			  String a= (String) lel.getSelectedItem();
			  if(a.equals("defendingArmy")) {
				  Ucontrol= new UnitControl(armies.get(0),worldMapControl.getG(),city,this);
				  cityView.revalidate();
  				cityView.repaint();
				 
			  }
			  else {
				  int s = Integer.parseInt(a.charAt(4)+"");
				  Ucontrol=new UnitControl(worldMapControl.getG().getPlayer().getControlledArmies().get(s-1),worldMapControl.getG(),city,this);
				  cityView.revalidate();
  				cityView.repaint();
			  }
	
	}
     if(e.getActionCommand().equals("Back")) {
    	 worldMapControl.getView().getContentPane().removeAll();
    	 worldMapControl.getView().getContentPane().add(new InfoPanel(this.worldMapControl),BorderLayout.NORTH);
    	 WorldMapControl trans= new WorldMapControl(worldMapControl.getStartViewControl());
    	 worldMapControl.getView().getContentPane().add(trans.getWorldMap().getF());
    	 worldMapControl.setHere(true);
    	 worldMapControl.getView().revalidate();
    	 worldMapControl.getView().repaint();
		}
   
}
public City getCity() {
	return city;
}
public void setCity(City city) {
	this.city = city;
}
}
