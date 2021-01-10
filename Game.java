import java.util.*;
import java.lang.*; 
interface Comparable<T> {
	public int compareTo(T s);
}
abstract class Character            // Superclass of Hero and Monster
{
	protected double HP;
	protected int level;
	protected Random r;
	Character()
	{
		HP=0;
		level=0;
		r=new Random();
	}
}
abstract class Hero extends Character     // Superclass of the four heroes
{
	protected String name;
	protected double XP;
	protected int attackNumber;
	protected int powerCount;
	protected int attackPower;
	protected int defencePower;
	protected int maxHP;
	protected boolean usingSidekick;
	protected ArrayList<Sidekick> SList;
	protected Sidekick currentSidekick;
	Hero()
	{
		name=null;
		XP=0;
		attackNumber=0;
		powerCount=0;
		attackPower=0;
		defencePower=0;
		maxHP=0;
		usingSidekick=false;
		SList=new ArrayList<Sidekick>();
		currentSidekick=null;
	}
	public void levelUp()          // level UP fucntion for Hero
	{
		int prev=level;
		if((XP>=20&&XP<60)) {
			level=2;
			if(prev!=level) {
				System.out.println("Level UP: Level: "+level);
				HP=100+(level-1)*50;
				maxHP=(int)HP;
				attackPower++;
				defencePower++;
			}	
		}
		if((XP>=60&&XP<120)) {
			level=3;
			if(prev!=level) {
				System.out.println("Level UP: Level: "+level);
				HP=100+(level-1)*50;
				maxHP=(int)HP;
				attackPower++;
				defencePower++;
			}
		}
		if(XP>=120) {
			level=4;
			if(prev!=level) {
				System.out.println("Level UP: Level: "+level);
				HP=100+(level-1)*50;
				maxHP=(int)HP;
				attackPower++;
				defencePower++;
			}
		}
	}
	public Sidekick getSidekick()        // function to get sidekick of maximum HP
	{
		if(SList.size()==0)
			return null;
		if(SList.size()==1)
			return SList.get(0);
		else {
			for(int j=0;j<SList.size();j++) {
				for(int i=0;i<SList.size()-1;i++)
				{
					if(!SList.get(i).equals(SList.get(i+1)))
					{
						if(SList.get(i).compareTo(SList.get(i+1))<0)
							Collections.swap(SList,i,i+1);
					}
				}
			}
			return SList.get(0);
		}		
	}
	public abstract void attack(Monster m);
	public abstract void defence(Monster m);
	public abstract void specialPower(Monster m);
}
class Warrior extends Hero             // Subclass of Hero
{
	Warrior(String name)
	{
		this.name=name;
		this.HP=100;
		maxHP=100;
		this.level=1;
		this.XP=0;
		attackPower=10;
		defencePower=3;
	}
	@Override
	public void attack(Monster m){
		if(powerCount>0)
		{
			if(m.HP>attackPower+5)
				m.HP-=attackPower+5;
			else
				m.HP=0;
			System.out.println("You attacked and inflicted "+(attackPower+5)+" damage to the monster.");
			powerCount--;
		}
		else
		{	
			if(m.HP>attackPower)
				m.HP-=attackPower;
			else
				m.HP=0;
			System.out.println("You attacked and inflicted "+attackPower+" damage to the monster.\nYour Hp: "+HP+"/"+maxHP+" Monsters Hp: "+m.HP+"/"+m.maxHP);
		}	
		if(attackNumber<3)
			attackNumber++;
		if(usingSidekick==true) {
			if(currentSidekick.getClass().getName().compareTo("Minions")==0) {
				if(currentSidekick.specialCount==2) {
					currentSidekick.specialAttack(m);
				}		
			}
			currentSidekick.attack(m);
		}
		System.out.println("Your Hp: "+HP+"/"+maxHP+" Monsters Hp: "+m.HP+"/"+m.maxHP);
	}
	@Override
	public void defence(Monster m){
		if(usingSidekick==true)
		{
			if(currentSidekick.getClass().getName().compareTo("Knight")==0&&m.getClass().getName().compareTo("Zombies")==0)
				currentSidekick.specialAttack(m);
		}
		if(powerCount>0)
		{
			m.weakened+=defencePower+5;
			powerCount--;
		}
		else	
			m.weakened+=defencePower;
		System.out.println("You choose to Defend. Monster attack reduced by "+m.weakened+"!\nYour Hp: "+HP+"/"+maxHP+" Monsters Hp: "+m.HP+"/"+m.maxHP);
		if(attackNumber<3)
			attackNumber++;
		
	}
	@Override
	public void specialPower(Monster m){
		attackNumber=0;
		powerCount=3;
		System.out.println("Special Power activated. Attack and defence attributes increased by 5 for next 3 moves.");
	}
}
class Mage extends Hero              // Subclass of Hero
{
	Mage(String name)
	{
		this.name=name;
		this.HP=100;
		maxHP=100;
		this.level=1;
		this.XP=0;
		attackPower=5;
		defencePower=5;
	}
	@Override
	public void attack(Monster m){
		if(m.HP>attackPower)
			m.HP-=attackPower;
		else
			m.HP=0;
		if(powerCount>0)
		{
			if(m.HP>=(int)(0.05*m.HP))
				m.HP-=(int)(0.05*m.HP);
			else
				m.HP=0;
			powerCount--;
		}
		if(attackNumber<3)
			attackNumber++;
		System.out.println("You attacked and inflicted "+attackPower+" damage to the monster.");
		if(usingSidekick==true) {
			if(currentSidekick.getClass().getName().compareTo("Minions")==0) {
				if(currentSidekick.specialCount==2) {
					currentSidekick.specialAttack(m);
				}		
			}
			currentSidekick.attack(m);
		}
		System.out.println("Your Hp: "+HP+"/"+maxHP+" Monsters Hp: "+m.HP+"/"+m.maxHP);
	}
	@Override
	public void defence(Monster m){
		if(usingSidekick==true)
		{
			if(currentSidekick.getClass().getName().compareTo("Knight")==0&&m.getClass().getName().compareTo("Zombies")==0)
				currentSidekick.specialAttack(m);
		}
		if(powerCount>0)
		{
			if(m.HP>=(int)(0.05*m.HP))
				m.HP-=(int)(0.05*m.HP);
			else
				m.HP=0;
			powerCount--;
		}
		m.weakened+=defencePower;
		if(attackNumber<3)
			attackNumber++;
		System.out.println("You choose to Defend. Monster attack reduced by "+m.weakened+"!\nYour Hp: "+HP+"/"+maxHP+" Monsters Hp: "+m.HP+"/"+m.maxHP);
	}
	@Override
	public void specialPower(Monster m){
		attackNumber=0;
		powerCount=3;
		System.out.println("Special Power activated. Opponent's HP will be reduced by 5% for next 3 moves.");
	}
}
class Thief extends Hero          // Subclass of Hero
{
	Thief(String name)
	{
		this.name=name;
		this.HP=100;
		maxHP=100;
		this.level=1;
		this.XP=0;
		attackPower=6;
		defencePower=4;
	}
	@Override
	public void attack(Monster m){
		if(m.HP>attackPower)
			m.HP-=attackPower;
		else
			m.HP=0;
		if(attackNumber<3)
			attackNumber++;
		System.out.println("You attacked and inflicted "+attackPower+" damage to the monster.");
		if(usingSidekick==true) {
			if(currentSidekick.getClass().getName().compareTo("Minions")==0) {
				if(currentSidekick.specialCount==2) {
					currentSidekick.specialAttack(m);
				}		
			}
			currentSidekick.attack(m);
		}
		System.out.println("Your Hp: "+HP+"/"+maxHP+" Monsters Hp: "+m.HP+"/"+m.maxHP);
	}
	@Override
	public void defence(Monster m){
		if(usingSidekick==true)
		{
			if(currentSidekick.getClass().getName().compareTo("Knight")==0&&m.getClass().getName().compareTo("Zombies")==0)
				currentSidekick.specialAttack(m);
		}
		m.weakened+=defencePower;
		if(attackNumber<3)
			attackNumber++;
		System.out.println("You choose to Defend. Monster attack reduced by "+m.weakened+"!\nYour Hp: "+HP+"/"+maxHP+" Monsters Hp: "+m.HP+"/"+m.maxHP);
	}
	@Override
	public void specialPower(Monster m){
		attackNumber=0;
		int temp=(int)((0.3)*m.HP);
		if(HP+temp<=maxHP)
			HP+=temp;
		else
			HP=maxHP;
		m.HP-=temp;
		System.out.println("Special Power activated.\nPerforming Special Attack.\nYou have stolen "+temp+"HP from the monster.\nYour Hp: "+HP+"/"+maxHP+" Monsters Hp: "+m.HP+"/"+m.maxHP);
	}
}
class Healer extends Hero                    // Subclass of Hero
{
	Healer(String name)
	{
		this.name=name;
		this.HP=100;
		maxHP=100;
		this.level=1;
		this.XP=0;
		attackPower=4;
		defencePower=8;
	}
	@Override
	public void attack(Monster m){
		if(powerCount>0)
		{
			if(HP+(int)(0.05*HP)<=maxHP)
				HP+=(int)(0.05*HP);
			else
				HP=maxHP;
			powerCount--;
		}
		if(m.HP>attackPower)
			m.HP-=attackPower;
		else
			m.HP=0;
		if(attackNumber<3)
			attackNumber++;
		System.out.println("You attacked and inflicted "+attackPower+" damage to the monster.");
		if(usingSidekick==true) {
			if(currentSidekick.getClass().getName().compareTo("Minions")==0) {
				if(currentSidekick.specialCount==2) {
					currentSidekick.specialAttack(m);
				}		
			}
			currentSidekick.attack(m);
		}
		System.out.println("Your Hp: "+HP+"/"+maxHP+" Monsters Hp: "+m.HP+"/"+m.maxHP);
	}
	@Override
	public void defence(Monster m){
		if(usingSidekick==true)
		{
			if(currentSidekick.getClass().getName().compareTo("Knight")==0&&m.getClass().getName().compareTo("Zombies")==0)
				currentSidekick.specialAttack(m);
		}
		if(powerCount>0)
		{
			if(HP+(int)(0.05*HP)<=maxHP)
				HP+=(int)(0.05*HP);
			else
				HP=maxHP;
			powerCount--;
		}
		m.weakened+=defencePower;
		if(attackNumber<3)
			attackNumber++;
		System.out.println("You choose to defend. Monster attack reduced by "+m.weakened+"!\nYour Hp: "+HP+"/"+maxHP+" Monsters Hp: "+m.HP+"/"+m.maxHP);
	}
	@Override
	public void specialPower(Monster m){
		attackNumber=0;
		powerCount=3;
		System.out.println("Special Power activated. Your HP will be increased by 5% for next 3 moves.");
	}
}
abstract class Monster extends Character         // Superclass of the four monsters
{
	protected int weakened;
	protected int maxHP;
	Monster()
	{
		super();
		weakened=0;
	}
	public abstract void attack(Hero h);
}
class Goblins extends Monster          // Subclass of Monster
{
	Goblins()
	{
		this.level=1;
		this.HP=100;
		maxHP=100;
	}	
	@Override
	public void attack(Hero h){
		int sum=0;
		for(int i=1;i<HP/4;i++)
			sum+=(i-HP/8)*(i-HP/8);
		int dmg=(int)(r.nextGaussian()*(Math.sqrt((double)sum/((double)HP/4)))+(double)HP/8);
		int total=dmg-weakened;
		if(total<0)
			total=0;
		if(h.HP>total)
			h.HP-=total;
		else
			h.HP=0;
		weakened=0;
		System.out.println("Goblins attacked and inflicted "+(total)+" damage to you.\nYour Hp: "+h.HP+"/"+h.maxHP+" Monsters Hp: "+HP+"/100");
		if(h.usingSidekick==true) {
			if(h.currentSidekick.HP>(1.5)*total)
				h.currentSidekick.HP-=(1.5)*total;
			else
				h.currentSidekick.HP=0;
			if(h.currentSidekick.getClass().getName().compareTo("Minions")==0) {
				if(h.currentSidekick.specialCount==2) {
					if(h.currentSidekick.HP>(1.5)*total) {
						h.currentSidekick.list[0].HP-=(1.5)*total;
						h.currentSidekick.list[1].HP-=(1.5)*total;
						h.currentSidekick.list[2].HP-=(1.5)*total;
					}
					else {
						h.currentSidekick.list[0].HP=0;
						h.currentSidekick.list[1].HP=0;
						h.currentSidekick.list[2].HP=0;
					}
					System.out.println("Sidekick's HP: "+h.currentSidekick.list[0].HP+"/100");
					System.out.println("Sidekick's HP: "+h.currentSidekick.list[1].HP+"/100");
					System.out.println("Sidekick's HP: "+h.currentSidekick.list[2].HP+"/100");
				}	
			}
			System.out.println("Sidekick's HP: "+h.currentSidekick.HP+"/100");
		}
	}
}
class Zombies extends Monster           // Subclass of Monster
{
	Zombies()
	{
		this.level=2;
		this.HP=150;
		maxHP=150;
	}
	@Override
	public void attack(Hero h){
		int sum=0;
		for(int i=1;i<HP/4;i++)
			sum+=(i-HP/8)*(i-HP/8);
		int dmg=(int)(r.nextGaussian()*(Math.sqrt((double)sum/((double)HP/4)))+(double)HP/8);
		int total=dmg-weakened;
		if(total<0)
			total=0;
		if(h.HP>total)
			h.HP-=total;
		else
			h.HP=0;
		weakened=0;
		System.out.println("Zombies attacked and inflicted "+(total)+" damage to you.\nYour Hp: "+h.HP+"/"+h.maxHP+" Monsters Hp: "+HP+"/150");
		if(h.usingSidekick==true) {
			if(h.currentSidekick.HP>(1.5)*total)
				h.currentSidekick.HP-=(1.5)*total;
			else
				h.currentSidekick.HP=0;
			if(h.currentSidekick.getClass().getName().compareTo("Minions")==0) {
				if(h.currentSidekick.specialCount==2) {
					if(h.currentSidekick.HP>(1.5)*total) {
						h.currentSidekick.list[0].HP-=(1.5)*total;
						h.currentSidekick.list[1].HP-=(1.5)*total;
						h.currentSidekick.list[2].HP-=(1.5)*total;
					}
					else {
						h.currentSidekick.list[0].HP=0;
						h.currentSidekick.list[1].HP=0;
						h.currentSidekick.list[2].HP=0;
					}
					System.out.println("Sidekick's HP: "+h.currentSidekick.list[0].HP+"/100");
					System.out.println("Sidekick's HP: "+h.currentSidekick.list[1].HP+"/100");
					System.out.println("Sidekick's HP: "+h.currentSidekick.list[2].HP+"/100");
				}	
			}
			System.out.println("Sidekick's HP: "+h.currentSidekick.HP+"/100");
		}	
	}
}
class Fiends extends Monster           //Subclass of Monster
{
	Fiends()
	{
		this.level=3;
		this.HP=200;
		maxHP=200;
	}
	@Override
	public void attack(Hero h){
		int sum=0;
		for(int i=1;i<HP/4;i++)
			sum+=(i-HP/8)*(i-HP/8);
		int dmg=(int)(r.nextGaussian()*(Math.sqrt((double)sum/((double)HP/4)))+(double)HP/8);
		int total=dmg-weakened;
		if(total<0)
			total=0;
		if(h.HP>total)
			h.HP-=total;
		else
			h.HP=0;
		weakened=0;
		System.out.println("Fiends attacked and inflicted "+(total)+" damage to you.\nYour Hp: "+h.HP+"/"+h.maxHP+" Monsters Hp: "+HP+"/200");
		if(h.usingSidekick==true) {
			if(h.currentSidekick.HP>(1.5)*total)
				h.currentSidekick.HP-=(1.5)*total;
			else
				h.currentSidekick.HP=0;
			if(h.currentSidekick.getClass().getName().compareTo("Minions")==0) {
				if(h.currentSidekick.specialCount==2) {
					if(h.currentSidekick.HP>(1.5)*total) {
						h.currentSidekick.list[0].HP-=(1.5)*total;
						h.currentSidekick.list[1].HP-=(1.5)*total;
						h.currentSidekick.list[2].HP-=(1.5)*total;
					}
					else {
						h.currentSidekick.list[0].HP=0;
						h.currentSidekick.list[1].HP=0;
						h.currentSidekick.list[2].HP=0;
					}
					System.out.println("Sidekick's HP: "+h.currentSidekick.list[0].HP+"/100");
					System.out.println("Sidekick's HP: "+h.currentSidekick.list[1].HP+"/100");
					System.out.println("Sidekick's HP: "+h.currentSidekick.list[2].HP+"/100");
				}	
			}
			System.out.println("Sidekick's HP: "+h.currentSidekick.HP+"/100");
		}
	}
}
class Lionfang extends Monster          //Subclass of Monster
{
	Lionfang()
	{
		this.level=4;
		this.HP=250;
		maxHP=250;
	}
	@Override
	public void attack(Hero h){
		int temp=r.nextInt(10);
		if(temp==1)
		{
			int l=(int)h.HP/2-weakened;
			h.HP-=l;
			weakened=0;
			System.out.println("Lionfang used his Special Attack and inflicted "+l+" damage to you.\nYour Hp: "+h.HP+"/"+h.maxHP+" Monsters Hp: "+HP+"/250");
			if(h.usingSidekick==true) {
				if(h.currentSidekick.HP>(1.5)*l)
					h.currentSidekick.HP-=(1.5)*l;
				else
					h.currentSidekick.HP=0;
				if(h.currentSidekick.getClass().getName().compareTo("Minions")==0) {
					if(h.currentSidekick.specialCount==2) {
						if(h.currentSidekick.HP>(1.5)*l) {
							h.currentSidekick.list[0].HP-=(1.5)*l;
							h.currentSidekick.list[1].HP-=(1.5)*l;
							h.currentSidekick.list[2].HP-=(1.5)*l;
						}
						else {
							h.currentSidekick.list[0].HP=0;
							h.currentSidekick.list[1].HP=0;
							h.currentSidekick.list[2].HP=0;
						}
						System.out.println("Sidekick's HP: "+h.currentSidekick.list[0].HP+"/100");
						System.out.println("Sidekick's HP: "+h.currentSidekick.list[1].HP+"/100");
						System.out.println("Sidekick's HP: "+h.currentSidekick.list[2].HP+"/100");
					}	
				}
				System.out.println("Sidekick's HP: "+h.currentSidekick.HP+"/100");
			}
		}
		else
		{	
			int sum=0;
			for(int i=1;i<HP/4;i++)
				sum+=(i-HP/8)*(i-HP/8);
			int dmg=(int)(r.nextGaussian()*(Math.sqrt((double)sum/((double)HP/4)))+(double)HP/8);
			int total=dmg-weakened;
			if(total<0)
				total=0;
			if(h.HP>total)
				h.HP-=total;
			else
				h.HP=0;
			weakened=0;
			System.out.println("Lionfang attacked and inflicted "+(total)+" damage to you.\nYour Hp: "+h.HP+"/"+h.maxHP+" Monsters Hp: "+HP+"/250");
			if(h.usingSidekick==true) {
				if(h.currentSidekick.HP>(1.5)*total)
					h.currentSidekick.HP-=(1.5)*total;
				else
					h.currentSidekick.HP=0;
				if(h.currentSidekick.getClass().getName().compareTo("Minions")==0) {
					if(h.currentSidekick.specialCount==2) {
						if(h.currentSidekick.HP>(1.5)*total) {
							h.currentSidekick.list[0].HP-=(1.5)*total;
							h.currentSidekick.list[1].HP-=(1.5)*total;
							h.currentSidekick.list[2].HP-=(1.5)*total;
						}
						else {
							h.currentSidekick.list[0].HP=0;
							h.currentSidekick.list[1].HP=0;
							h.currentSidekick.list[2].HP=0;
						}
						System.out.println("Sidekick's HP: "+h.currentSidekick.list[0].HP+"/100");
						System.out.println("Sidekick's HP: "+h.currentSidekick.list[1].HP+"/100");
						System.out.println("Sidekick's HP: "+h.currentSidekick.list[2].HP+"/100");
					}	
				}
				System.out.println("Sidekick's HP: "+h.currentSidekick.HP+"/100");
			}
		}
	}
}
abstract class Sidekick implements Cloneable,Comparable<Sidekick>   // blueprint for Sidekick
{
	protected double HP;
	protected double XP;
	protected double XPStack;
	protected double attackPower;
	protected int specialCount;
	protected Sidekick[] list;
	Sidekick()
	{
		HP=100;
		XP=0;
		attackPower=0;
		specialCount=1;
		list=new Sidekick[3];
		XPStack=0;
	}
	public int compareTo(Sidekick s)
	{
		if(XP>s.XP) return 1;
		else if(XP<s.XP) return -1;
		else return 0;
	}
	@Override
	public boolean equals(Object x)
	{
		if(getClass()==x.getClass()) {
			Sidekick temp=(Sidekick) x;
			return (XP==temp.XP);
		}
		else return false;			
	}
	public Sidekick clone() {
		try {
			Sidekick copy = (Sidekick)super.clone();
			return copy;
		} 
		catch (CloneNotSupportedException e) {
			return null;    // this will never happen
		}
	} 
	public void levelUP() {           // level UP function for Sidekick
		int temp=(int)XPStack/5;
		attackPower+=temp;
		XPStack-=5*temp;
		if(temp>0)
			System.out.println("Sidekick attack increased by "+temp);
	}
	public void makeClones() {}
	abstract public void attack(Monster m);
	abstract public void specialAttack(Monster m);
}
class Minions extends Sidekick        // Subclass of Sidekick
{
	Minions(double XP)
	{
		super();
		this.XP=0;
		if(XP>5)
			attackPower=1+(XP-5)*(0.5);
		else
			attackPower=1;
	}
	@Override
	public Minions clone() {
		Minions copy=(Minions)super.clone();
		return copy;
	}
	@Override
	public void makeClones()
	{
		Minions clone1=this.clone();
		Minions clone2=this.clone();
		Minions clone3=this.clone();
		list[0]=clone1;
		list[1]=clone2;
		list[2]=clone3;
	}
	@Override
	public void attack(Monster m) {
		if(m.HP>attackPower)
			m.HP-=attackPower;
		else
			m.HP=0;
		System.out.println("Minions attacked and inflicted "+attackPower+" damage to the monster.\nMinions HP:"+HP+"/100");
	}
	@Override
	public void specialAttack(Monster m) {
		list[0].attack(m);
		list[1].attack(m);
		list[2].attack(m);
	}
}
class Knight extends Sidekick        // Subclass of Sidekick
{
	Knight(double XP)
	{
		super();
		this.XP=0;
		if(XP>8)
			attackPower=2+(XP-8)*(0.5);
		else
			attackPower=2;
	}
	@Override
	public void attack(Monster m) {
		if(m.HP>attackPower)
			m.HP-=attackPower;
		else
			m.HP=0;
		System.out.println("Knight attacked and inflicted "+attackPower+" damage to the monster.\nKnight HP:"+HP+"/100");
	}
	@Override
	public void specialAttack(Monster m) {
			System.out.println("Knight's Special Power activated");
			m.weakened+=5;	
	}
}
class Node        // Class to represent a location
{
	private Monster m;
	private int n;
	private ArrayList<Node> forwardAdj;
	private Node backwardAdj;
	Node(int n)
	{
		this.n=n;
		forwardAdj=new ArrayList<Node>();
		backwardAdj=null;
		m=null;
	}
	public Monster getM() {
		return m;
	}
	public void setM(Monster m) {
		this.m = m;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public ArrayList<Node> getForwardAdj() {
		return forwardAdj;
	}
	public Node getBackwardAdj() {
		return backwardAdj;
	}
	public void setBackwardAdj(Node backwardAdj) {
		this.backwardAdj = backwardAdj;
	}
}
class Graph             // Class to represent the whole map of the game
{
	public ArrayList<Node> list;
	Graph(int m)
	{
		list=new ArrayList<Node>();
		for(int i=0;i<m;i++)
		{
			list.add(new Node(i));
		}
	}
	public void addEdge(int m,int n){
		list.get(m).getForwardAdj().add(list.get(n));
	}
	public void display(){
		list.forEach((i)->System.out.println(i.getN()+" "+i.getForwardAdj()+" "+i.getBackwardAdj()));
	}
}
public class Game               // main driver class
{
	public static ArrayList<Hero> users=new ArrayList<Hero>();
	public static Graph graph;
	public static void map(){
		graph=new Graph(11);
		graph.addEdge(0,1);
		graph.addEdge(0,4);
		graph.addEdge(0,7);
		graph.addEdge(1,2);
		graph.addEdge(1,5);
		graph.addEdge(1,8);
		graph.addEdge(4,2);
		graph.addEdge(4,5);
		graph.addEdge(4,8);
		graph.addEdge(7,2);
		graph.addEdge(7,5);
		graph.addEdge(7,8);
		graph.addEdge(2,3);
		graph.addEdge(2,6);
		graph.addEdge(2,9);
		graph.addEdge(5,3);
		graph.addEdge(5,6);
		graph.addEdge(5,9);
		graph.addEdge(8,3);
		graph.addEdge(8,6);
		graph.addEdge(8,9);
		graph.addEdge(3,10);
		graph.addEdge(6,10);
		graph.addEdge(9,10);
		for(int i=1;i<10;i++)
		{
			Random r=new Random();
			int p=r.nextInt(3)+1;
			switch(p)
			{
				case 1:
				{
					graph.list.get(i).setM(new Goblins());
					break;
				}
				case 2:
				{
					graph.list.get(i).setM(new Zombies());
					break;
				}
				case 3:
				{
					graph.list.get(i).setM(new Fiends());
					break;
				}
			}
		}
		graph.list.get(10).setM(new Lionfang());
	}
	public static void MainMenu() {
		System.out.println("Welcome To ArchLegends\nChoose Your Option\n1) New User\n2) Existing User\n3) Exit");
	}
	public static void NMenu() {
		System.out.println("Choose A Hero\n1) Warrior\n2) Thief\n3) Mage\n4) Healer");	
	}
	public static void LMenu(Hero h,Node n) {
		if(n.getN()==0)
		{
			System.out.println("You are at the Starting Location. Choose Path:");
			int minLevel=5;
			int suggestedLocation=0;
			for(int i=0;i<n.getForwardAdj().size();i++)
			{
				System.out.println((i+1)+") Go to Location "+n.getForwardAdj().get(i).getN());
				if(minLevel>n.getForwardAdj().get(i).getM().level)
				{
					minLevel=n.getForwardAdj().get(i).getM().level;
					suggestedLocation=n.getForwardAdj().get(i).getN();
				}
			}
			System.out.println("Enter -1 to Exit\n ....Hint: For Better Chances Of Reaching LionFang, choose location "+suggestedLocation);
		}
		else if(n.getForwardAdj().get(0).getN()==10)
		{
			System.out.println("You are at Location "+n.getN()+". Choose Path:\n1) Go to Location 10 to fight Final Boss\n2) Go Back\nEnter -1 to Exit");
			if(h.level<4)
				System.out.println("....Hint: For Better Chances Of Defeating Lionfang, go back");
		}
		else
		{
			System.out.println("You are at Location "+n.getN()+". Choose Path:");
			int minLevel=5;
			int suggestedLocation=0;
			for(int i=0;i<n.getForwardAdj().size();i++)
			{
				System.out.println((i+1)+") Go to Location "+n.getForwardAdj().get(i).getN());
				if(minLevel>n.getForwardAdj().get(i).getM().level)
				{
					minLevel=n.getForwardAdj().get(i).getM().level;
					suggestedLocation=n.getForwardAdj().get(i).getN();
				}
			}
			if(h.level<minLevel)
				System.out.println("4) Go Back\nEnter -1 to Exit\n ....Hint: For Better Chances Of Reaching LionFang, go back");
			else
				System.out.println("4) Go Back\nEnter -1 to Exit\n ....Hint: For Better Chances Of Reaching LionFang, choose location "+suggestedLocation);
		}
	}
	public static void FMenu(Hero h,Node n){
		if(h.attackNumber<3)
			System.out.println("Choose Move:\n1) Attack\n2) Defence");
		else
			System.out.println("Choose Move:\n1) Attack\n2) Defence\n3) Special Attack");
	}
	public static void location(Hero x, Node currentLocation, Monster m)
	{
		FMenu(x,currentLocation);
		Scanner s=new Scanner(System.in);
		int choice=Integer.parseInt(s.next());
		if(x.attackNumber<3)
		{	
			switch(choice)
			{
			case 1:{	
				System.out.println("You choose to attack.");
				x.attack(m);
				break;
			}
			case 2:{		
				x.defence(m);
				break;
			}
			default:
				break;
			}
		}
		else
		{
			switch(choice)
			{
			case 1:{	
				System.out.println("You choose to attack.");
				x.attack(m);
				break;
			}
			case 2:{		
				x.defence(m);
				break;
			}
			case 3:{
				x.specialPower(m);
				break;
			}
			default:
				break;
			}
		}
	}
	public static void choice(Hero x)
	{
		Scanner s=new Scanner(System.in);
		System.out.println("If you would you like to buy a sidekick, type yes. Else type no to upgrade level.");
		String ch=s.next();
		if(ch.compareTo("no")==0) {
			x.levelUp();
		}
		else if(ch.compareTo("yes")==0) {
			System.out.println("Your current XP is "+x.XP+"\nIf you want to buy a minion, press 1.\nIf you want to buy a knight, press 2.");
			int ch1=Integer.parseInt(s.next());
			System.out.print("XP to spend: ");
			int ch2=Integer.parseInt(s.next());
			Sidekick S=null;
			if(ch1==1) 
				S=new Minions(ch2);
			else if(ch1==2)
				S=new Knight(ch2);
			x.SList.add(S);
			System.out.println("You bought a Sidekick: "+S.getClass().getName()+"\nXP of Sidekick is "+S.XP+"\nAttack of Sidekick is "+S.attackPower);
			x.XP-=ch2;
			x.levelUp();
		}
	}
	public static void main(String[] args) 
	{
		Scanner s=new Scanner(System.in);
		MainMenu();
		int ch;
		do
		{
			ch=Integer.parseInt(s.next());
			switch(ch)
			{
				case 1:           // query for user creation
				{
					System.out.println("Enter Username");
					String name=s.next();
					NMenu();
					int type=Integer.parseInt(s.next());
					Hero h;
					switch(type)
					{
						case 1:{
							h=new Warrior(name);
							users.add(h);
							System.out.println("User Creation done. Username: "+name+". Hero type: Warrior. Log in to play the game . Exiting");
							break;
						}
						case 2:{
							h=new Thief(name);
							users.add(h);
							System.out.println("User Creation done. Username: "+name+". Hero type: Thief. Log in to play the game . Exiting");
							break;
						}
						case 3:{
							h=new Mage(name);
							users.add(h);
							System.out.println("User Creation done. Username: "+name+". Hero type: Mage. Log in to play the game . Exiting");
							break;
						}
						case 4:{
							h=new Healer(name);
							users.add(h);
							System.out.println("User Creation done. Username: "+name+". Hero type: Healer. Log in to play the game . Exiting");
							break;
						}
						default:{
							System.out.println("Wrong Choice, User Creation Failed!");
							break;
						}
					}
					MainMenu();
					break;
					
				}
				case 2:            // query for searching the user and logging in
				{
					System.out.println("Enter Username");
					String name=s.next();
					Hero x=null;
					int count=0;
					for(int i=0;i<users.size();i++)
					{
						if(users.get(i).name.compareTo(name)==0)
						{
							System.out.println("User Found... Logging in");
							if(users.get(i).getClass().getName()=="Warrior")
								x=new Warrior(users.get(i).name);
							if(users.get(i).getClass().getName()=="Thief")
								x=new Thief(users.get(i).name);
							if(users.get(i).getClass().getName()=="Mage")
								x=new Mage(users.get(i).name);
							if(users.get(i).getClass().getName()=="Healer")
								x=new Healer(users.get(i).name);
							count++;
							break;
						}
					}
					if(count==0)
					{	
						System.out.println("No Such User Found!");
						MainMenu();
						break;
					}
					map();
					Node currentLocation=graph.list.get(0);
					System.out.println("Welcome "+name);
					while(x.HP>0&&currentLocation.getN()!=10)
					{
						if(currentLocation.getN()==0)
						{
							LMenu(x,currentLocation);
							int option=Integer.parseInt(s.next());
							if(option==-1)
							{
								MainMenu();
								break;
							}
							currentLocation.getForwardAdj().get(option-1).setBackwardAdj(currentLocation);
							currentLocation=currentLocation.getForwardAdj().get(option-1);
							System.out.println("Moving to Location "+currentLocation.getN());
							Monster m=null;
							if(currentLocation.getM().getClass().getName()=="Goblins")
								m=new Goblins();
							if(currentLocation.getM().getClass().getName()=="Zombies")
								m=new Zombies();
							if(currentLocation.getM().getClass().getName()=="Fiends")
								m=new Fiends();
							System.out.println("Fight Started. You are fighting level "+m.level+" "+m.getClass().getName());
							if(x.SList.size()!=0) {
								System.out.println("Type yes if you wish to use a sidekick, else type no.");
								String ch2=s.next();
								if(ch2.compareTo("yes")==0) {
									x.usingSidekick=true;
									x.currentSidekick=x.getSidekick();
									if(x.currentSidekick.getClass().getName().compareTo("Minions")==0) {
										System.out.println("You have a sidekick "+x.currentSidekick.getClass().getName()+" with you. Attack of sidekick is "+x.currentSidekick.attackPower);
										if(x.currentSidekick.specialCount==1) {
											System.out.println("Press c to use cloning ability. Else press f to move to the fight");	
											String ch3=s.next();
											if(ch3.compareTo("c")==0) {
												x.currentSidekick.specialCount=2;
												x.currentSidekick.makeClones();
												System.out.println("Cloning Done.");
											}	
										}
									}	
									else
										System.out.println("You have a sidekick "+x.currentSidekick.getClass().getName()+" with you. Attack of sidekick is "+x.currentSidekick.attackPower);
								}
								else
									x.usingSidekick=false;
							}
							while(x.HP>0&&m.HP>0)
							{
								location(x,currentLocation,m);
								if(m.HP==0||m.HP<0){
									System.out.println("Monster Killed!\n"+(m.level*20)+" XP awarded");
									x.HP=x.maxHP;
									x.XP+=m.level*20;
									if(x.usingSidekick==true) {
										x.currentSidekick.HP=100;
										x.currentSidekick.XP+=(m.level*20)/10;
										x.currentSidekick.XPStack+=(m.level*20)/10;
										System.out.println((m.level*20)/10+" XP awarded to sidekick");
										x.currentSidekick.levelUP();
										if(x.currentSidekick.specialCount==2)
											x.currentSidekick.specialCount=0;
									}
									System.out.println("Fight won, proceed to the next location.");
									choice(x);
									break;
								}
								System.out.println("Monster Attack!");
								m.attack(x);
								if(x.usingSidekick==true&&(x.currentSidekick.HP==0||x.currentSidekick.HP<0)) {
									System.out.println("Your Sidekick died!");
									x.SList.remove(x.currentSidekick);
									x.currentSidekick=null;
									x.usingSidekick=false;
								}
							}
							if(x.HP==0||x.HP<0){
								System.out.println("You died! Returning to the Starting Location...");
								x.HP=100;
								x.maxHP=100;
								x.XP=0;
								x.level=1;
								x.attackNumber=0;
								currentLocation=graph.list.get(0);
							}
						}
						else if(currentLocation.getForwardAdj().get(0).getN()==10)
						{	
							LMenu(x,currentLocation);
							int option=Integer.parseInt(s.next());
							if(option==-1)
							{
								MainMenu();
								break;
							}	
							if(option==1)
							{
								currentLocation.getForwardAdj().get(option-1).setBackwardAdj(currentLocation);
								currentLocation=currentLocation.getForwardAdj().get(option-1);
								System.out.println("Moving to Location "+currentLocation.getN());
								Monster m=new Lionfang();
								System.out.println("Fight Started. You are fighting level "+m.level+" "+m.getClass().getName());
								if(x.SList.size()!=0) {
									System.out.println("Type yes if you wish to use a sidekick, else type no.");
									String ch2=s.next();
									if(ch2.compareTo("yes")==0) {
										x.usingSidekick=true;
										x.currentSidekick=x.getSidekick();
										if(x.currentSidekick.getClass().getName().compareTo("Minions")==0) {
											System.out.println("You have a sidekick "+x.currentSidekick.getClass().getName()+" with you. Attack of sidekick is "+x.currentSidekick.attackPower);
											if(x.currentSidekick.specialCount==1) {
												System.out.println("Press c to use cloning ability. Else press f to move to the fight");	
												String ch3=s.next();
												if(ch3.compareTo("c")==0) {
													x.currentSidekick.specialCount=2;
													x.currentSidekick.makeClones();
													System.out.println("Cloning Done.");
												}	
											}
										}	
										else
											System.out.println("You have a sidekick "+x.currentSidekick.getClass().getName()+" with you. Attack of sidekick is "+x.currentSidekick.attackPower);
									}
									else
										x.usingSidekick=false;
								}	
								while(x.HP>0&&m.HP>0)
								{
									location(x,currentLocation,m);
									if(m.HP==0||m.HP<0){
										System.out.println("Monster Killed!\n"+(m.level*20)+" XP awarded");
										x.HP=x.maxHP;
										x.XP+=m.level*20;
										if(x.usingSidekick==true) {
											x.currentSidekick.HP=100;
											x.currentSidekick.XP+=(m.level*20)/10;
											x.currentSidekick.XPStack+=(m.level*20)/10;
											System.out.println((m.level*20)/10+" XP awarded to sidekick");
											if(x.currentSidekick.specialCount==2)
												x.currentSidekick.specialCount=0;
										}	
										x.levelUp();
										System.out.println("Fight won, You win the game!");
										MainMenu();
										break;
									}
									System.out.println("Monster Attack!");
									m.attack(x);
									if(x.usingSidekick==true&&(x.currentSidekick.HP==0||x.currentSidekick.HP<0)) {
										System.out.println("Your Sidekick died!");
										x.SList.remove(x.currentSidekick);
										x.currentSidekick=null;
										x.usingSidekick=false;
									}
								}
								if(x.HP==0||x.HP<0){
									System.out.println("You died! Returning to the Starting Location...");
									x.HP=100;
									x.maxHP=100;
									x.XP=0;
									x.level=1;
									x.attackNumber=0;
									currentLocation=graph.list.get(0);
								}
							}	
							else if(option==2)
							{
								currentLocation=currentLocation.getBackwardAdj();
								System.out.println("Moving to Location "+currentLocation.getN());
								Monster m=null;
								if(currentLocation.getM().getClass().getName()=="Goblins")
									m=new Goblins();
								if(currentLocation.getM().getClass().getName()=="Zombies")
									m=new Zombies();
								if(currentLocation.getM().getClass().getName()=="Fiends")
									m=new Fiends();
								System.out.println("Fight Started. You are fighting level "+m.level+" "+m.getClass().getName());
								if(x.SList.size()!=0) {
									System.out.println("Type yes if you wish to use a sidekick, else type no.");
									String ch2=s.next();
									if(ch2.compareTo("yes")==0) {
										x.usingSidekick=true;
										x.currentSidekick=x.getSidekick();
										if(x.currentSidekick.getClass().getName().compareTo("Minions")==0) {
											System.out.println("You have a sidekick "+x.currentSidekick.getClass().getName()+" with you. Attack of sidekick is "+x.currentSidekick.attackPower);
											if(x.currentSidekick.specialCount==1) {
												System.out.println("Press c to use cloning ability. Else press f to move to the fight");	
												String ch3=s.next();
												if(ch3.compareTo("c")==0) {
													x.currentSidekick.specialCount=2;
													x.currentSidekick.makeClones();
													System.out.println("Cloning Done.");
												}	
											}
										}	
										else
											System.out.println("You have a sidekick "+x.currentSidekick.getClass().getName()+" with you. Attack of sidekick is "+x.currentSidekick.attackPower);
									}
									else
										x.usingSidekick=false;
								}
								while(x.HP>0&&m.HP>0)
								{
									location(x,currentLocation,m);
									if(m.HP==0||m.HP<0) {
										System.out.println("Monster Killed!\n"+(m.level*20)+" XP awarded");
										x.HP=x.maxHP;
										x.XP+=m.level*20;
										if(x.usingSidekick==true) {
											x.currentSidekick.HP=100;
											x.currentSidekick.XP+=(m.level*20)/10;
											x.currentSidekick.XPStack+=(m.level*20)/10;
											System.out.println((m.level*20)/10+" XP awarded to sidekick");
											x.currentSidekick.levelUP();
											if(x.currentSidekick.specialCount==2)
												x.currentSidekick.specialCount=0;
										}
										System.out.println("Fight won, proceed to the next location.");
										choice(x);
										break;
									}
									System.out.println("Monster Attack!");
									m.attack(x);
									if(x.usingSidekick==true&&(x.currentSidekick.HP==0||x.currentSidekick.HP<0)) {
										System.out.println("Your Sidekick died!");
										x.SList.remove(x.currentSidekick);
										x.currentSidekick=null;
										x.usingSidekick=false;
									}
								}
								
								if(x.HP==0||x.HP<0) {
									System.out.println("You died! Returning to the Starting Location...");
									x.HP=100;
									x.maxHP=100;
									x.XP=0;
									x.level=1;
									x.attackNumber=0;
									currentLocation=graph.list.get(0);
								}
							}	
						}
						else
						{
							LMenu(x,currentLocation);
							int option=Integer.parseInt(s.next());
							if(option==-1)
							{
								MainMenu();
								break;
							}	
							if(option==1||option==2||option==3)
							{
								currentLocation.getForwardAdj().get(option-1).setBackwardAdj(currentLocation);
								currentLocation=currentLocation.getForwardAdj().get(option-1);
								System.out.println("Moving to Location "+currentLocation.getN());
								Monster m=null;
								if(currentLocation.getM().getClass().getName()=="Goblins")
									m=new Goblins();
								if(currentLocation.getM().getClass().getName()=="Zombies")
									m=new Zombies();
								if(currentLocation.getM().getClass().getName()=="Fiends")
									m=new Fiends();
								System.out.println("Fight Started. You are fighting level "+m.level+" "+m.getClass().getName());
								if(x.SList.size()!=0) {
									System.out.println("Type yes if you wish to use a sidekick, else type no.");
									String ch2=s.next();
									if(ch2.compareTo("yes")==0) {
										x.usingSidekick=true;
										x.currentSidekick=x.getSidekick();
										if(x.currentSidekick.getClass().getName().compareTo("Minions")==0) {
											System.out.println("You have a sidekick "+x.currentSidekick.getClass().getName()+" with you. Attack of sidekick is "+x.currentSidekick.attackPower);
											if(x.currentSidekick.specialCount==1) {
												System.out.println("Press c to use cloning ability. Else press f to move to the fight");	
												String ch3=s.next();
												if(ch3.compareTo("c")==0) {
													x.currentSidekick.specialCount=2;
													x.currentSidekick.makeClones();
													System.out.println("Cloning Done.");
												}	
											}
										}	
										else
											System.out.println("You have a sidekick "+x.currentSidekick.getClass().getName()+" with you. Attack of sidekick is "+x.currentSidekick.attackPower);
									}
									else
										x.usingSidekick=false;
								}
								while(x.HP>0&&m.HP>0)
								{
									location(x,currentLocation,m);
									if(m.HP==0||m.HP<0) {
										System.out.println("Monster Killed!\n"+(m.level*20)+" XP awarded");
										x.HP=x.maxHP;
										x.XP+=m.level*20;
										if(x.usingSidekick==true) {
											x.currentSidekick.HP=100;
											x.currentSidekick.XP+=(m.level*20)/10;
											x.currentSidekick.XPStack+=(m.level*20)/10;
											System.out.println((m.level*20)/10+" XP awarded to sidekick");
											x.currentSidekick.levelUP();
											if(x.currentSidekick.specialCount==2)
												x.currentSidekick.specialCount=0;
										}
										System.out.println("Fight won, proceed to the next location.");
										choice(x);
										break;
									}
									System.out.println("Monster Attack!");
									m.attack(x);
									if(x.usingSidekick==true&&(x.currentSidekick.HP==0||x.currentSidekick.HP<0)) {
										System.out.println("Your Sidekick died!");
										x.SList.remove(x.currentSidekick);
										x.currentSidekick=null;
										x.usingSidekick=false;
									}
								}
								
								if(x.HP==0||x.HP<0) {
									System.out.println("You died! Returning to the Starting Location...");
									x.HP=100;
									x.maxHP=100;
									x.XP=0;
									x.level=1;
									x.attackNumber=0;
									currentLocation=graph.list.get(0);
								}
							}
							else if(option==4)
							{
								currentLocation=currentLocation.getBackwardAdj();
								if(currentLocation.getM()!=null)
								{
									System.out.println("Moving to Location "+currentLocation.getN());
									Monster m=null;
									if(currentLocation.getM().getClass().getName()=="Goblins")
										m=new Goblins();
									if(currentLocation.getM().getClass().getName()=="Zombies")
										m=new Zombies();
									if(currentLocation.getM().getClass().getName()=="Fiends")
										m=new Fiends();
									System.out.println("Fight Started. You are fighting level "+m.level+" "+m.getClass().getName());
									if(x.SList.size()!=0) {
										System.out.println("Type yes if you wish to use a sidekick, else type no.");
										String ch2=s.next();
										if(ch2.compareTo("yes")==0) {
											x.usingSidekick=true;
											x.currentSidekick=x.getSidekick();
											if(x.currentSidekick.getClass().getName().compareTo("Minions")==0) {
												System.out.println("You have a sidekick "+x.currentSidekick.getClass().getName()+" with you. Attack of sidekick is "+x.currentSidekick.attackPower);
												if(x.currentSidekick.specialCount==1) {
													System.out.println("Press c to use cloning ability. Else press f to move to the fight");	
													String ch3=s.next();
													if(ch3.compareTo("c")==0) {
														x.currentSidekick.specialCount=2;
														x.currentSidekick.makeClones();
														System.out.println("Cloning Done.");
													}	
												}
											}	
											else
												System.out.println("You have a sidekick "+x.currentSidekick.getClass().getName()+" with you. Attack of sidekick is "+x.currentSidekick.attackPower);
										}
										else
											x.usingSidekick=false;
									}
									while(x.HP>0&&m.HP>0)
									{
										location(x,currentLocation,m);
										if(m.HP==0||m.HP<0) {
											System.out.println("Monster Killed!\n"+(m.level*20)+" XP awarded");
											x.HP=x.maxHP;
											x.XP+=m.level*20;
											if(x.usingSidekick==true) {
												x.currentSidekick.HP=100;
												x.currentSidekick.XP+=(m.level*20)/10;
												x.currentSidekick.XPStack+=(m.level*20)/10;
												System.out.println((m.level*20)/10+" XP awarded to sidekick");
												x.currentSidekick.levelUP();
												if(x.currentSidekick.specialCount==2)
													x.currentSidekick.specialCount=0;
											}
											System.out.println("Fight won, proceed to the next location.");
											choice(x);
											break;
										}
										System.out.println("Monster Attack!");
										m.attack(x);
										if(x.usingSidekick==true&&(x.currentSidekick.HP==0||x.currentSidekick.HP<0)) {
											System.out.println("Your Sidekick died!");
											x.SList.remove(x.currentSidekick);
											x.currentSidekick=null;
											x.usingSidekick=false;
										}
									}
									if(x.HP==0||x.HP<0) {
										System.out.println("You died! Returning to the Starting Location...");
										x.HP=100;
										x.maxHP=100;
										x.XP=0;
										x.level=1;
										x.attackNumber=0;
										currentLocation=graph.list.get(0);
									}
								}
							}
						}
					}
					break;
				}	
				case 3:        // query to exit 
					break;	
				default: {
					System.out.println("Wrong Choice, Enter Again!");
					break;
				}
			}
		}
		while(ch!=3);
	}
}
