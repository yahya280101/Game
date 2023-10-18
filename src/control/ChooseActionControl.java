package control;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import buildings.ArcheryRange;
import engine.City;
import engine.Game;
import exceptions.FriendlyCityException;
import exceptions.NotEnoughGoldException;
import exceptions.TargetNotReachedException;
import units.Army;
import view.InfoPanel;

public class ChooseActionControl implements ActionListener {
	public JButton getAttack() {
		return attack;
	}

	public void setAttack(JButton attack) {
		this.attack = attack;
	}

	public JButton getLaySiege() {
		return laySiege;
	}

	public void setLaySiege(JButton laySiege) {
		this.laySiege = laySiege;
	}

	public JFrame getChoose() {
		return choose;
	}

	public void setChoose(JFrame choose) {
		this.choose = choose;
	}

	public Game getG() {
		return g;
	}

	public void setG(Game g) {
		this.g = g;
	}


	public InfoPanel getInfoPanel() {
		return infoPanel;
	}

	public void setInfoPanel(InfoPanel infoPanel) {
		this.infoPanel = infoPanel;
	}

	public boolean isUnderSiege() {
		return underSiege;
	}

	public void setUnderSiege(boolean underSiege) {
		this.underSiege = underSiege;
	}
	
	private JButton attack;
	private JButton laySiege;
	private JFrame choose;
	private Game g;
	private InfoPanel infoPanel;
	private boolean underSiege;
    private WorldMapControl worldMapControl;
	public ChooseActionControl(InfoPanel infoPanel) {
		this.infoPanel = infoPanel;
		this.worldMapControl=infoPanel.getWorldMapControl();
		this.g= infoPanel.getG();
		attack = new JButton("Attack");
		laySiege = new JButton("Lay Siege");
		choose = new JFrame();
		choose.setVisible(true);
		choose.setLayout(new GridLayout());
		choose.setPreferredSize(new Dimension(800, 200));
		choose.setLocation(248, 247);
		choose.pack();
		choose.setResizable(false);
		choose.add(attack);
		choose.add(laySiege);
		choose.revalidate();
		choose.repaint();
		attack.addActionListener(this);
		laySiege.addActionListener(this);
		}

	public void actionPerformed(ActionEvent e) {
		Army attacker = null;
		City defender = null;
		ArrayList<City> notControlled = new ArrayList<City>();
		for(City c : g.getAvailableCities()) {
			if(!g.getPlayer().getControlledCities().contains(c)) {
				notControlled.add(c);
			}
		}
		for(Army a : g.getPlayer().getControlledArmies()) {
			if(a.getDistancetoTarget()==0) {
				for(City c : notControlled) {
					if(a.getCurrentLocation().equalsIgnoreCase(c.getName())) {
						attacker = a;
						defender = c;
					}
				}
			}
		}
		if(e.getSource() == attack) {
			new BattleControl(infoPanel.getG(), attacker, defender,infoPanel.getWorldMapControl());
			choose.setVisible(false);
		}
		if(e.getSource() == laySiege) {
			try {
				g.getPlayer().laySiege(attacker, defender);
			} catch (TargetNotReachedException e1) {
				JOptionPane.showMessageDialog(infoPanel.getWorldMapControl().getStartViewControl().getView(), "The target is not reached yet", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (FriendlyCityException e1) {
				JOptionPane.showMessageDialog(infoPanel.getWorldMapControl().getStartViewControl().getView(), "You Are besieging a friendly city", "Error", JOptionPane.ERROR_MESSAGE);
			}
			choose.setVisible(false);
		}
	}

	public WorldMapControl getWorldMapControl() {
		return worldMapControl;
	}

	public void setWorldMapControl(WorldMapControl worldMapControl) {
		this.worldMapControl = worldMapControl;
	}
}