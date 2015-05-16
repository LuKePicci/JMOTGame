package it.polimi.ingsw.cg_30;


public class Player
{
	private String name;

	private int index;

	private PlayerCard identity;

	private int killsCount;
	
	private SpareDeck itemsDeck;
	
	


	public int getIndex()
	{
		return index;
	}

	public String getName()
	{
		return name;
	}

	public PlayerCard getIdentity()
	{
		return identity;
	}

	public SpareDeck getItemsDeck()
	{
		return itemsDeck;
	}

	public int getKillsCount()
	{
		return killsCount;
	}
	
	
	//COSTRUTTORE
	public Player(String name, int index, PlayerCard identity){
		this.name=name;
		this.index=index;
		this.identity=identity;
		this.killsCount=0;
		this.itemsDeck= new SpareDeck();
	}
	
	public Player(){
		this.name="unknown player";
		this.index=0;
		this.identity=null;
		this.killsCount=0;
		this.itemsDeck= new SpareDeck();
	}

	public void incrementKillsCount(){
		killsCount++;
	}
}

