package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import engine.City;
import engine.Game;
import units.Army;
import units.Status;

public class ControlledArmiesPanel extends JPanel {

	public ArrayList<JButton> getIdleButtons() {
		return idleButtons;
	}

	public void setIdleButtons(ArrayList<JButton> idleJButtons) {
		this.idleButtons = idleJButtons;
	}

	public ArrayList<JButton> getNonIdleButtons() {
		return nonIdleButtons;
	}

	public void setNonIdleButtons(ArrayList<JButton> nonIdleButtons) {
		this.nonIdleButtons = nonIdleButtons;
	}

	public void setPanelArmies(JPanel panelArmies) {
		this.panelArmies = panelArmies;
	}

	public JPanel getPanelArmies() {
		return panelArmies;
	}

	private JPanel panelArmies;
	private ArrayList<JButton> idleButtons;
	private ArrayList<JButton> nonIdleButtons;
	private ArrayList<JButton> allBottuns;

	public ControlledArmiesPanel(Game g) {
		idleButtons = new ArrayList<JButton>();
		nonIdleButtons = new ArrayList<JButton>();
		allBottuns= new ArrayList<JButton>();
		ArrayList<Army> controlledArmies = g.getPlayer().getControlledArmies();
		ArrayList<Army> idle = new ArrayList<Army>();
		ArrayList<Army> nonIdle = new ArrayList<Army>();
	    Border blackline = BorderFactory.createTitledBorder("Idle Armies");
		JPanel panelIdle = new JPanel();
        panelIdle.setLayout(new GridLayout());
        JPanel panel1 = new JPanel();
	    String spaces = "                   ";
	    panel1.add(panelIdle);
	    panel1.setBorder(blackline);
	    int c=1;
	    Border whiteline = BorderFactory.createTitledBorder("NonIdle Armies");
        JPanel panelNonIdle = new JPanel();
        panelNonIdle.setLayout(new GridLayout());
        JPanel panel2 = new JPanel();
	    panel2.add(panelNonIdle);
	    panel2.setBorder(whiteline);
	      int i=1;
			for(Army army : controlledArmies) {
				JButton b = new JButton();
				b.setPreferredSize(new Dimension(150, 80));
				b.setText("Army"+i);
                if(army.getUnits().size()==0) {
                	b.setEnabled(false);
                }
                else {
                	b.setEnabled(true);
                }
				if((army.getCurrentStatus() == Status.BESIEGING)) {
					City sieged1=null;
					for(City sieged : g.getAvailableCities()) {
					if((sieged.isUnderSiege())&&army.getCurrentLocation().equalsIgnoreCase(sieged.getName())) {
						sieged1=sieged;
					}
					}
					b.setToolTipText("<html>"+"Currently besieging "+army.getCurrentLocation()+"<br>"+"Turns Under Siege: "+sieged1.getTurnsUnderSiege()+"</html>");
				}
				if((army.getCurrentStatus() == Status.MARCHING)) {
					
					b.setToolTipText("<html>"+"Currently Targeting "+army.getTarget()+"<br>"+"Turns left to reach city: "+army.getDistancetoTarget()+"</html>");
				}
				i++;
				allBottuns.add(b);
				if(army.getCurrentStatus() == Status.IDLE) {
					idle.add(army);
					panelIdle.add(b);
					idleButtons.add(b);
				} else {
					nonIdle.add(army);
					panelNonIdle.add(b);
					nonIdleButtons.add(b);
				}
			}
		panelArmies = new JPanel();
		panelArmies.setLayout(new GridLayout());
		panelArmies.add(panel1);
		panelArmies.add(panel2);
	}

	public ArrayList<JButton> getAllBottuns() {
		return allBottuns;
	}

	public void setAllBottuns(ArrayList<JButton> allBottuns) {
		this.allBottuns = allBottuns;
	}

}