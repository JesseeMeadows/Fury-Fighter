public class ScoreTable
{
	private static final int KILLED_REGULAR = 100;
	private static final int PICKUP_REGULAR = 300;
	private static final int WEAPON_PICKUP_MULTIPLIER_UPPER_DIVISION = 2000;
	private static final int LEVEL_BONUS_UPPER_DIVISION = 60000;

	private static final int KILLED_FLYER = KILLED_REGULAR;
	private static final int KILLED_TURRENT = KILLED_REGULAR;
	private static final int KILLED_WORM = KILLED_REGULAR;
	private static final int KILLED_BOUNCER = KILLED_REGULAR;
	private static final int KILLED_TORPEDO = KILLED_REGULAR;
	private static final int KILLED_BOMB = KILLED_REGULAR;
	private static final int KILLED_SCORPION = KILLED_REGULAR;
	private static final int KILLED_EYE = 1000;

	private static final int PICKUP_WEAPON_POD = PICKUP_REGULAR;
	private static final int PICKUP_SPEED_POD = PICKUP_REGULAR;
	private static final int PICKUP_DEFENSE_POD = PICKUP_REGULAR;
	private static final int PICKUP_1UP = PICKUP_REGULAR;
	private static final int PICKUP_BOMB_FRAGMENT = 30;

	private static final int WEAPON_PICKUP_MULTIPLIER_EAGLE = 500;
	private static final int WEAPON_PICKUP_MULTIPLIER_ALBATROSS = 1000;
	private static final int WEAPON_PICKUP_MULTIPLIER_ACE
		= WEAPON_PICKUP_MULTIPLIER_UPPER_DIVISION;
	private static final int WEAPON_PICKUP_MULTIPLIER_ULTIMATE
		= WEAPON_PICKUP_MULTIPLIER_UPPER_DIVISION;

	private static final int LEVEL_BONUS_EAGLE = 15000;
	private static final int LEVEL_BONUS_ALBATROSS = 30000;
	private static final int LEVEL_BONUS_ACE = LEVEL_BONUS_UPPER_DIVISION;
	private static final int LEVEL_BONUS_ULTIMATE = LEVEL_BONUS_UPPER_DIVISION;

	private static final int LEVEL_END_WEAPON_THRESHOLD_EACH = 10;
	private static final int LEVEL_END_WEAPON_THRESHOLD_REWARD_LIVES = 3;

	public static int scoreForKilled(EnemyModel enemy)
	{
		return KILLED_REGULAR;
	}

	public static int scoreForPickup(Pickup pickup)
	{
		if(pickup.getType().equals("fragment"))
		{
			return PICKUP_BOMB_FRAGMENT;
		}
		return PICKUP_REGULAR;
	}

	/* TODO: Figure out parameters for scoreForLevelBonus */
	public static int scoreForLevelBonus()
	{
		//Difficulty select hasn't been implemented yet, so we opted to give "Normal Mode" points. 
		return LEVEL_BONUS_ALBATROSS;
	}
}
