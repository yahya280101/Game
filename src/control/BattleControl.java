package control;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import buildings.ArcheryRange;
import buildings.Barracks;
import buildings.Stable;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Status;
import units.Unit;
import view.BattleView;
import view.InfoPanel;
import engine.City;
import engine.Game;
import exceptions.FriendlyFireException;

public class BattleControl implements ActionListener {
	private BattleView view;
	private JButton Manual;
	private JButton AutoResolve;
	private Army army;
	private Game g;
	private City Defendingcity;
	private WorldMapControl worldMapControl;
	public BattleControl(Game g,Army army,City Defendingcity,WorldMapControl worldMapControl){
		this.worldMapControl=worldMapControl;
		view = new BattleView();
		JTextField a= new JTextField("Would you like to Control the fight or do u want in automated?");
		a.setEditable(false);
		view.getLext().add(a);
		this.army=army;
		this.g=g;
		this.Defendingcity=Defendingcity;
		Manual=new JButton("Manual");
		Manual.setActionCommand("Manual");
		AutoResolve=new JButton("AutoResolve");
		AutoResolve.setActionCommand("AutoResolve");
		AutoResolve.addActionListener(this);
		Manual.addActionListener(this);
		view.getButtons().add(AutoResolve);
		view.getButtons().add(Manual);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("AutoResolve")){
			AutoResolve.setEnabled(false);
			Manual.setEnabled(false);
			try {
				g.autoResolve(army, Defendingcity.getDefendingArmy());
			} catch (FriendlyFireException e1) {
				JOptionPane.showMessageDialog(view, "You are attacking a friendly city", "Error", JOptionPane.ERROR_MESSAGE);
			}
			if(army.getUnits().size()!=0) {
    			JOptionPane.showMessageDialog(this.view, "You won the battle and occupied this city","Congrats", JOptionPane.DEFAULT_OPTION);
			if(g.isGameOver()) {
				if(g.getPlayer().getControlledCities().size() == g.getAvailableCities().size())
			JOptionPane.showMessageDialog(view, "You beat the game and became the supreme conquerer","Congrats", JOptionPane.DEFAULT_OPTION);
			}}
			else {
    			JOptionPane.showMessageDialog(this.view, "You lost the battle ","HardLuck", JOptionPane.DEFAULT_OPTION);
    			g.getPlayer().getControlledArmies().remove(army);

			}
			Defendingcity.setUnderSiege(false);
			Defendingcity.setTurnsUnderSiege(0);
			army.setCurrentStatus(Status.IDLE);
			view.setVisible(false);
			worldMapControl.getView().getContentPane().removeAll();
    		worldMapControl.getView().getContentPane().add(new InfoPanel(this.worldMapControl),BorderLayout.NORTH);
    		WorldMapControl trans= new WorldMapControl(worldMapControl.getStartViewControl());
    		worldMapControl.getView().getContentPane().add(trans.getWorldMap().getF());
    		worldMapControl.getView().revalidate();
    		worldMapControl.getView().repaint();


		}else{
			if(e.getActionCommand().equals("Manual")){
				view.setVisible(false);
				new WarControl(g,army,Defendingcity,worldMapControl);
			}
		}
	}

	public WorldMapControl getWorldMapControl() {
		return worldMapControl;
	}

	public void setWorldMapControl(WorldMapControl worldMapControl) {
		this.worldMapControl = worldMapControl;
	}
}