package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.Game;

public class WorldMap extends JPanel {
	
	public Game getG() {
		return g;
	}

	public void setG(Game g) {
		this.g = g;
	}

	public CitiesMap getM() {
		return m;
	}

	public void setM(CitiesMap m) {
		this.m = m;
	}

	public ControlledArmiesPanel getP() {
		return p;
	}

	public void setP(ControlledArmiesPanel p) {
		this.p = p;
	}

	public void setF(JPanel f) {
		this.f = f;
	}

	private JPanel f;
	private CitiesMap m;
	private ControlledArmiesPanel p;
	private Game g;
	private InfoPanel infoPanel;
	

	public JPanel getF() {
		return f;
	}
	public InfoPanel getInfoPanel() {
		return infoPanel;
	}

	public void setInfoPanel(InfoPanel infoPanel) {
		this.infoPanel = infoPanel;
	}

	public WorldMap(Game g) {
		this.g = g;
	    f = new JPanel();
	    f.setLayout(new GridLayout(2,1));
	    m = new CitiesMap();
	    p = new ControlledArmiesPanel(g);
	    f.add(m.getPanel());
	    f.add(p.getPanelArmies());
	    f.setVisible(true);
		f.validate();
	    f.repaint();
	}

}