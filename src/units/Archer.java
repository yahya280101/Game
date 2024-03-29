package units;

import exceptions.FriendlyFireException;

public class Archer extends Unit{

	

	public Archer(int level, int maxSoldierConunt, double idleUpkeep, double marchingUpkeep, double siegeUpkeep) {
		super(level, maxSoldierConunt, idleUpkeep, marchingUpkeep, siegeUpkeep);
	}

	@Override
	public void attack(Unit target) throws FriendlyFireException {
		super.attack(target);
		double factor=0;
		if(target instanceof Archer)
		{
			if(getLevel()==1)
				factor=0.3;
			else if(getLevel()==2)
				factor=0.4;
			else 
				factor=0.5;
		}
		else if(target instanceof Infantry)
		{
			if(getLevel()==1)
				factor=0.2;
			else if(getLevel()==2)
				factor=0.3;
			else
				factor=0.4;
		}
		else if(target instanceof Cavalry)
		{
			if(getLevel()==1 || getLevel()==2)
				factor=0.1;
			
			else
				factor=0.2;
		}
		target.setPrevSoldierCount(target.getCurrentSoldierCount());
		target.setCurrentSoldierCount(target.getCurrentSoldierCount()-(int)(factor*getCurrentSoldierCount()));
		
			target.getParentArmy().handleAttackedUnit(target);
	}
	public void undoAttack(Unit target) throws FriendlyFireException {
		super.undoAttack(target);
		double factor=0;
		if(target instanceof Archer)
		{
			if(getLevel()==1)
				factor=0.3;
			else if(getLevel()==2)
				factor=0.4;
			else 
				factor=0.5;
		}
		else if(target instanceof Infantry)
		{
			if(getLevel()==1)
				factor=0.2;
			else if(getLevel()==2)
				factor=0.3;
			else
				factor=0.4;
		}
		else if(target instanceof Cavalry)
		{
			if(getLevel()==1 || getLevel()==2)
				factor=0.1;
			
			else
				factor=0.2;
		}
		target.setCurrentSoldierCount(target.getCurrentSoldierCount()+(int)(factor*target.getPrevSoldierCount()));
		
			target.getParentArmy().handleAttackedUnit(target);
	}

	public String toString() {
		String result = "";
		result +="<html>" + "Type: Archer" + "<br>" + super.toString();
		return result;
	}

}
