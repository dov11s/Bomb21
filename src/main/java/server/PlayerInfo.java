package server;

import shared.PacketUpdatePlayerPos;
import shared.Vector2f;

import java.util.Random;

public class PlayerInfo 
{
	int id;
	Vector2f coordinate;
	boolean isHoldingLeft;
	boolean isHoldingRight;
	boolean isHoldingUp;
	boolean isHoldingDown;
	boolean isHoldingUse;
	boolean isHoldingSkill;
	boolean isHoldingPause;
	boolean isHoldingUnPause;
	boolean placedBomb = true;
	int health;
	int baseHealth;
	float speed;
	float baseSpeed;
	int size;
	int bombCount;
	int bombTimer;
	
	int deathCounter;

	SkillAlgorithm skillAlgorithm;
	
	boolean canDie = true;
	
	public PlayerInfo() 
	{

		deathCounter = 0;

		Random r = new Random();
		int low = 100;
		int high = 800;

		setBombTimer(1);



		int x = r.nextInt(high-low) + low;
		int y = r.nextInt(high-low) + low;

		this.baseHealth = 2;
		this.coordinate = new Vector2f();
		this.coordinate.x = x;
		this.coordinate.y = y;
		this.isHoldingLeft = false;
		this.isHoldingRight = false;
		this.isHoldingUp = false;
		this.isHoldingDown = false;
		this.isHoldingPause = false;
		this.isHoldingUnPause = false;
		this.skillAlgorithm = new GoingBackInTimeSkill();
		this.size = 40;
		this.speed = 5f;
		this.baseSpeed = 5f;
		this.health = 2;
		this.bombCount = 2;
		//this.playerStats = new ConcretePlayer();
	}

	public void setBombTimer(int time){
		bombTimer = 30*time;
	}


	public void reduceTimer(){
		bombTimer--;
	}


	public  void ChangePlayerLocation(int x, int y){

		this.coordinate.x = x;
		this.coordinate.y = y;

	}
	
	public PlayerInfo(int id, Vector2f coordinate)
	{
		this.id = id;
		this.coordinate = coordinate;
		this.isHoldingLeft = false;
		this.isHoldingRight = false;
		this.isHoldingUp = false;
		this.isHoldingDown = false;
		this.isHoldingUse = false;
		this.isHoldingPause = false;
		this.isHoldingUnPause = false;
		this.size = 40;
		this.speed = 2.5f;
		this.baseSpeed = 2.5f;
		this.health = 3;
		this.bombCount = 2;
		//Test
	}
	
	public SkillAlgorithm getSkillAlgorithm() 
	{
		return skillAlgorithm;
	}

	public void setSkillAlgorithm(SkillAlgorithm skillAlgorithm) 
	{
		this.skillAlgorithm = skillAlgorithm;
	}

	
	public void onTick()
	{
		this.skillAlgorithm.onTick(this);
	}
	
	public void tryUsingSpell()
	{
		this.skillAlgorithm.useSkill(this);
	}
	
	public int getCooldown()
	{
		return this.skillAlgorithm.getCooldown();
	}
	
	public String getName()
	{
		return this.skillAlgorithm.getName();
	}
	
	public PacketUpdatePlayerPos getPacketUpdatePlayerPos(int id)
	{
		PacketUpdatePlayerPos newPack = new PacketUpdatePlayerPos();
		newPack.id = id;
		newPack.coordinate = this.coordinate;
		newPack.isHoldingLeft = this.isHoldingLeft;
		newPack.isHoldingRight = this.isHoldingRight;
		newPack.isHoldingUp = this.isHoldingUp;
		newPack.isHoldingDown = this.isHoldingDown;
		newPack.isHoldingUse = this.isHoldingUse;
		newPack.size = this.size;
		newPack.isHoldingSkill = this.isHoldingSkill;
		newPack.isHoldingPause = this.isHoldingPause;
		newPack.isHoldingUnPause = this.isHoldingUnPause;
		newPack.skillName = this.getName();
		newPack.skillCooldown = this.getCooldown();
		newPack.health = this.health;
		return newPack;
	}
	
	public void restoreState(Memento memento){
		
		if(memento.getState(this)){
			System.out.println("Successfully restored state");
		}else{
			System.out.println("Unable to restore for Caretaker " + this.id);
		}
		
	}
	
	public Memento saveState(){
		return new Memento(id, coordinate, placedBomb, health, baseHealth, speed, baseSpeed, size, bombCount, bombTimer, deathCounter);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Vector2f getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Vector2f coordinate) {
		this.coordinate = new Vector2f(coordinate.x, coordinate.y);
	}

	public boolean isPlacedBomb() {
		return placedBomb;
	}

	public void setPlacedBomb(boolean placedBomb) {
		this.placedBomb = placedBomb;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getBaseHealth() {
		return baseHealth;
	}

	public void setBaseHealth(int baseHealth) {
		this.baseHealth = baseHealth;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getBaseSpeed() {
		return baseSpeed;
	}

	public void setBaseSpeed(float baseSpeed) {
		this.baseSpeed = baseSpeed;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getBombCount() {
		return bombCount;
	}

	public void setBombCount(int bombCount) {
		this.bombCount = bombCount;
	}

	public int getDeathCounter() {
		return deathCounter;
	}

	public void setDeathCounter(int deathCounter) {
		this.deathCounter = deathCounter;
	}

	public int getBombTimer() {
		return bombTimer;
	}

}
