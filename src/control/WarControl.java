package control;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Status;
import units.Unit;
import view.InfoPanel;
import view.WarView;
import buildings.ArcheryRange;
import buildings.Barracks;
import buildings.Stable;
//import com.sun.javafx.print.Units;

import engine.City;
import engine.Game;
import exceptions.FriendlyFireException;
public class WarControl implements ActionListener{
	private WarView warView;
	private JButton attack;
	private Game g;
	private Army army;
	private City DefCity;
	private ArrayList<JButton> attackUnits;
	private ArrayList<JButton> defUnits;
	private JButton last;
	private JButton b4last;
	private Unit friendUnit;
	private Unit foeUnit;
	private int count;
	private WorldMapControl worldMapControl;
	public WarControl(Game g, Army army, City DefCity,WorldMapControl worldMapControl){
		this.worldMapControl=worldMapControl;
		last=null;
		b4last=null;
		warView=new WarView();
		JOptionPane.showMessageDialog(this.warView, "Hover over Unit to display it's attributes", "Note", JOptionPane.DEFAULT_OPTION);
		attackUnits=new ArrayList<JButton>();
		defUnits=new ArrayList<JButton>();
		this.g=g;
		this.army=army;
		this.DefCity=DefCity;
		attack=new JButton("attack");
		attack.setActionCommand("attack");
		attack.setEnabled(false);
		attack.addActionListener(this);
		warView.getHit().add(attack);
		warView.getFriend().setLayout(new GridLayout(2, 5));
		for(int i=0; i<army.getUnits().size();i++){
			JButton but=new JButton();
			but.setText("FriendlyUnit "+(i+1));
			Unit u=army.getUnits().get(i);
			String text="";
			if(u instanceof Archer)
				text="Archer";
				if(u instanceof Infantry)
					text="Infantry";
					if(u instanceof Cavalry)
						text="Cavalry";
			but.setToolTipText("<html>" + "Type: "+text+"<br>"+"Level: "+u.getLevel()+"<br>"+"SoldierCount: "+u.getCurrentSoldierCount()+ "</html>");	
			but.setActionCommand("Unit"+(i+1));
			but.addActionListener(this);
			warView.getFriend().add(but);
			attackUnits.add(but);
			
		}
		warView.getFoe().setLayout(new GridLayout(2, 8));
		for(int i=0; i<DefCity.getDefendingArmy().getUnits().size();i++){
			JButton but=new JButton();
			but.setText("EnemyUnit "+(i+1));
			Unit u=DefCity.getDefendingArmy().getUnits().get(i);
			String text="";
			if(u instanceof Archer)
				text="Archer";
				if(u instanceof Infantry)
					text="Infantry";
					if(u instanceof Cavalry)
						text="Cavalry";
					DefCity.setUnderSiege(false);
					DefCity.setTurnsUnderSiege(0);
					army.setCurrentStatus(Status.IDLE);

			but.setToolTipText("<html>" + "Type: "+text+"<br>"+"Level: "+u.getLevel()+"<br>"+"SoldierCount: "+u.getCurrentSoldierCount()+ "</html>");		
			but.setActionCommand("Unit"+(i+1));
			but.setEnabled(false);
			but.addActionListener(this);
			warView.getFoe().add(but);
			defUnits.add(but);
			
		}
		warView.validate();
		warView.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(count>12) {
			warView.getT().setText("Log \n -------------------\n");
			count=0;
		}
		JButton clicked=(JButton) e.getSource();
		if(attackUnits.contains(clicked)){
			if(last==attack){
    			JOptionPane.showMessageDialog(this.warView, "Cannot attack a friendly unit", "Error", JOptionPane.ERROR_MESSAGE);
			}else
			if(b4last==clicked){
				attack.setEnabled(false);
				b4last=null;
				friendUnit=null;}
			else{
			int i;
			for(i=0;i<attackUnits.size();i++)
				if(attackUnits.get(i)==clicked)
					break;
			friendUnit=army.getUnits().get(i);
			b4last=clicked ;
			attack.setEnabled(true);
			}}else
			if((clicked==attack)){
				if(last==attack){
					last=null;
					for(int i=0 ;i<defUnits.size();i++){
						if(defUnits.get(i)!=null)
							defUnits.get(i).setEnabled(false);
					}
				}else {
					last=clicked;
					for(int i=0 ;i<defUnits.size();i++){
						if(defUnits.get(i)!=null)
							defUnits.get(i).setEnabled(true);
					}
					
				}
			}else 
				if(defUnits.contains(clicked)){
					int i;
					for(i=0;i<defUnits.size();i++)
						if(defUnits.get(i)==clicked)
							break;
					foeUnit=DefCity.getDefendingArmy().getUnits().get(i);
					int log1=foeUnit.getCurrentSoldierCount();
					try {
						friendUnit.attack(foeUnit);
						count++;
					} catch (FriendlyFireException e1) {
						JOptionPane.showMessageDialog(this.warView, "You are attacking a friendly city", "Error", JOptionPane.ERROR_MESSAGE);
					}
					String htext="";
					if(foeUnit instanceof Archer)
						htext="Archer";
						if(foeUnit instanceof Infantry)
							htext="Infantry";
							if(foeUnit instanceof Cavalry)
								htext="Cavalry";
					
					clicked.setToolTipText("<html>" + "Type: "+htext+"<br>"+"Level: "+foeUnit.getLevel()+"<br>"+"SoldierCount: "+foeUnit.getCurrentSoldierCount()+ "</html>");		
					int log2 =log1-foeUnit.getCurrentSoldierCount();
					warView.getT().setText(warView.getT().getText()+clicked.getText()+" Lost "+log2+" Soldiers"+"\n");
					if(foeUnit.getCurrentSoldierCount()==0){
						warView.getT().setText(warView.getT().getText()+clicked.getText()+" has been eliminated"+"\n");
						defUnits.get(i).setEnabled(false);
						defUnits.remove(i);					
					}
					if(DefCity.getDefendingArmy().getUnits().size()==0){
		    			
						JOptionPane.showMessageDialog(warView, "You won the battle and occupied this city","Congrats", JOptionPane.DEFAULT_OPTION);
		    			g.occupy(army, DefCity.getName());
		    			worldMapControl.getView().getContentPane().removeAll();
			    		worldMapControl.getView().getContentPane().add(new InfoPanel(this.worldMapControl),BorderLayout.NORTH);
			    		WorldMapControl trans= new WorldMapControl(worldMapControl.getStartViewControl());
			    		worldMapControl.getView().getContentPane().add(trans.getWorldMap().getF());
			    		worldMapControl.getView().revalidate();
			    		worldMapControl.getView().repaint();
						warView.setVisible(false);
						if(g.isGameOver()) {
							if(g.getPlayer().getControlledCities().size() == g.getAvailableCities().size())
						JOptionPane.showMessageDialog(warView, "You beat the game and became the supreme conquerer","Congrats", JOptionPane.DEFAULT_OPTION);

							
					}
					}else
					{
						int j=(int)(Math.random()*DefCity.getDefendingArmy().getUnits().size());
						foeUnit=DefCity.getDefendingArmy().getUnits().get(j);
						j=(int)(Math.random()*army.getUnits().size());
						friendUnit=army.getUnits().get(j);
						log1=friendUnit.getCurrentSoldierCount();
						try {
							foeUnit.attack(friendUnit);
							count ++;
						} catch (FriendlyFireException e1) {
							JOptionPane.showMessageDialog(this.warView, "You are attacking a friendly city", "Error", JOptionPane.ERROR_MESSAGE);
						}
						 htext="";
							if(friendUnit instanceof Archer)
								htext="Archer";
								if(friendUnit instanceof Infantry)
									htext="Infantry";
									if(friendUnit instanceof Cavalry)
										htext="Cavalry";
									attackUnits.get(j).setToolTipText("<html>" + "Type: "+htext+"<br>"+"Level: "+friendUnit.getLevel()+"<br>"+"SoldierCount: "+friendUnit.getCurrentSoldierCount()+ "</html>");		
						log2 =log1-friendUnit.getCurrentSoldierCount();
						String friendname=attackUnits.get(j).getText();
						warView.getT().setText(warView.getT().getText()+friendname+" Lost "+log2+" Soldiers"+"\n");
						if(friendUnit.getCurrentSoldierCount()==0){
							warView.getT().setText(warView.getT().getText()+friendname+" has been eliminated"+"\n");
							attackUnits.get(j).setEnabled(false);
							attackUnits.remove(j);					
						}
						if(army.getUnits().size()==0){
			    			g.getPlayer().getControlledArmies().remove(army);
			    			JOptionPane.showMessageDialog(warView, "You lost the battle ","HardLuck", JOptionPane.DEFAULT_OPTION);
			    			worldMapControl.getView().getContentPane().removeAll();
				    		worldMapControl.getView().getContentPane().add(new InfoPanel(this.worldMapControl),BorderLayout.NORTH);
				    		WorldMapControl trans= new WorldMapControl(worldMapControl.getStartViewControl());
				    		worldMapControl.getView().getContentPane().add(trans.getWorldMap().getF());
				    		worldMapControl.getView().revalidate();
				    		worldMapControl.getView().repaint();
			    			warView.setVisible(false);
							
						
					}else{
						last=null;
						b4last=null;
						friendUnit=null;
						foeUnit=null;
						attack.setEnabled(false);
						for(int z=0;z<defUnits.size();z++){
							defUnits.get(z).setEnabled(false);
						}
					}
				}		
	}

}

	}
