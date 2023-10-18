package view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import control.ArmyControl;
import control.BattleControl;
import control.ChooseActionControl;
import control.WorldMapControl;
import engine.City;
import engine.Game;
import units.Army;
import units.Status;

public class InfoPanel extends JPanel implements ActionListener {
	private WorldMapControl worldMapControl;
    private JTextArea playervalues;
	private Game g;
	private String playerName;
	private int playerTurnCount;
	private double playerTreasury;
	private double playerFood;
	private JButton endTurnButton;
	private City clickedCity;
	private boolean attackClicked;
	private ArmyControl armyControl;
	
	public City getClickedCity() {
		return clickedCity;
	}

	public void setClickedCity(City clickedCity) {
		this.clickedCity = clickedCity;
	}

	public JButton getEndTurnButton() {
		return endTurnButton;
	}

	public void setEndTurnButton(JButton endTurnButton) {
		this.endTurnButton = endTurnButton;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPlayerTurnCount() {
		return playerTurnCount;
	}

	public void setPlayerTurnCount(int playerTurnCount) {
		this.playerTurnCount = playerTurnCount;
	}

	public double getPlayerTreasury() {
		return playerTreasury;
	}

	public void setPlayerTreasury(double playerTreasury) {
		this.playerTreasury = playerTreasury;
	}

	public double getPlayerFood() {
		return playerFood;
	}

	public void setPlayerFood(double playerFood) {
		this.playerFood = playerFood;
	}

	public JTextArea getPlayervalues() {
		return playervalues;
	}

	public void setPlayervalues(JTextArea playervalues) {
		this.playervalues = playervalues;
	}

	public Game getG() {
		return g;
	}

	public void setG(Game g) {
		this.g = g;
	}

	public InfoPanel(WorldMapControl worldMapControl) {
		this.setWorldMapControl(worldMapControl);
		this.g = worldMapControl.getG();
		this.playerName = g.getPlayer().getName();
		this.playerTurnCount = g.getCurrentTurnCount();
		this.playerTreasury = g.getPlayer().getTreasury();
		this.playerFood = g.getPlayer().getFood();
		this.setPreferredSize(new Dimension(this.getWidth(), (int) (0.15 * this.getWorldMapControl().getStartViewControl().getView().getHeight())));
		this.playervalues = new JTextArea("Player Name: "+this.playerName +"\n" +"Turn Count: "+ playerTurnCount +"\n"+ "Gold: " +playerTreasury+"\n"+ "Food: "+playerFood);
		this.playervalues.setPreferredSize(new Dimension((int) (0.5 * this.getWorldMapControl().getStartViewControl().getView().getWidth()),(int) (0.15 * this.getWorldMapControl().getStartViewControl().getView().getHeight())));
		this.playervalues.setFont(new Font("Serif", Font.ITALIC, 19));
		this.playervalues.setEditable(false);
		this.setLayout(new GridLayout());
		this.add(this.getPlayervalues());
		this.endTurnButton = new JButton("End Turn");
		this.endTurnButton.setActionCommand("End Turn");
		this.endTurnButton.addActionListener(this);
		this.add(endTurnButton);
		this.validate();
		this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		 if(g.isGameOver()) {
             if(g.getPlayer().getControlledCities().size() == g.getAvailableCities().size()) {
                       JOptionPane.showMessageDialog(worldMapControl.getStartViewControl().getView(), "You beat the game and became the supreme conquerer","Congrats", JOptionPane.DEFAULT_OPTION);
                       this.getWorldMapControl().getStartViewControl().getView().setVisible(false);
                       System.exit(0);
             }
             
			 else {
                       JOptionPane.showMessageDialog(worldMapControl.getStartViewControl().getView(), "You were not able to beat the game","HardLuck!!", JOptionPane.DEFAULT_OPTION);
                       this.getWorldMapControl().getStartViewControl().getView().setVisible(false);
                       System.exit(0);
			 }
             }
		if(e.getSource()==endTurnButton) {
			boolean initfight = false;
			City chosen=null;
			Army attacker=null;
			boolean choose=false;
			Army marcher=null;
			Army moved=null;
			for (City c : g.getAvailableCities()) {
				if (c.isUnderSiege()) {
					if(c.getTurnsUnderSiege() == 2){
						for(Army a : g.getPlayer().getControlledArmies()) {
							if(a.getCurrentStatus()==Status.BESIEGING){
							attacker=a;
							chosen=c;
							initfight=true;
								
						}
						
					}
						}
				}
				}
			for(Army a:g.getPlayer().getControlledArmies()) {
				if(a.getCurrentStatus()==Status.MARCHING) {
					if(a.getDistancetoTarget()==1) {
						marcher=a;
						choose=true;
						for(City c1 : g.getAvailableCities()) {
							if(c1.getName().equalsIgnoreCase(a.getTarget())) {
								clickedCity = c1;
							}
						}
				}
			}
			
				}
			g.endTurn();
			 if(g.isGameOver()) {
              if(g.getPlayer().getControlledCities().size() == g.getAvailableCities().size()) {
                        JOptionPane.showMessageDialog(worldMapControl.getStartViewControl().getView(), "You beat the game and became the supreme conquerer","Congrats", JOptionPane.DEFAULT_OPTION);
                        this.getWorldMapControl().getStartViewControl().getView().setVisible(false);
                        System.exit(0);
              }
              
			 else {
                        JOptionPane.showMessageDialog(worldMapControl.getStartViewControl().getView(), "You were not able to beat the game","HardLuck!!", JOptionPane.DEFAULT_OPTION);
                        this.getWorldMapControl().getStartViewControl().getView().setVisible(false);
                        System.exit(0);
			 }
              }
			 if(initfight==true) {
				new BattleControl(g, attacker, chosen,worldMapControl);
			 }
			 if(choose==true) {
                 JOptionPane.showMessageDialog(worldMapControl.getStartViewControl().getView(), "You have reached the city, you must choose to fight or Laysiege","Warning!", JOptionPane.DEFAULT_OPTION);
                 new ChooseActionControl(this);	 
			 }
			 this.removeAll();
			 this.playervalues = new JTextArea("Player Name: "+g.getPlayer().getName() +"\n" +"Turn Count: "+ g.getCurrentTurnCount() +"\n"+ "Gold: " +g.getPlayer().getTreasury()+"\n"+ "Food: "+ g.getPlayer().getFood());
			 this.playervalues.setPreferredSize(new Dimension((int) (0.5 * this.getWorldMapControl().getStartViewControl().getView().getWidth()),(int) (0.15 * this.getWorldMapControl().getStartViewControl().getView().getHeight())));
			 this.playervalues.setFont(new Font("Serif", Font.ITALIC, 19));
			 this.add(this.getPlayervalues());
			 this.add(endTurnButton);
             this.validate();
	    	 this.repaint();
	    	 this.getWorldMapControl().getStartViewControl().getView().validate();
	    	 this.getWorldMapControl().getStartViewControl().getView().repaint();
	    	if(worldMapControl.isHere()) {
	    		worldMapControl.getView().getContentPane().removeAll();
	    		worldMapControl.getView().getContentPane().add(new InfoPanel(this.worldMapControl),BorderLayout.NORTH);
	    		WorldMapControl trans= new WorldMapControl(worldMapControl.getStartViewControl());
	    		worldMapControl.getView().getContentPane().add(trans.getWorldMap().getF());
	    		worldMapControl.getView().revalidate();
	    		worldMapControl.getView().repaint();
	    	}
		}
	}

	public WorldMapControl getWorldMapControl() {
		return worldMapControl;
	}

	public void setWorldMapControl(WorldMapControl worldMapControl) {
		this.worldMapControl = worldMapControl;
	}

	public boolean isAttackClicked() {
		return attackClicked;
	}

	public void setAttackClicked(boolean attackClicked) {
		this.attackClicked = attackClicked;
	}

	public ArmyControl getArmyControl() {
		return armyControl;
	}

	public void setArmyControl(ArmyControl armyControl) {
		this.armyControl = armyControl;
	}

}