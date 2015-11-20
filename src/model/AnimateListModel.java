package model;

public class AnimateListModel
{

	private String _name;
	private int _id;
	public AnimateListModel(String name, int id)
	{
		_name = name;
		_id = id;
	}
	public void setName(String value)
	{
		_name = value;
	}
	public void setID(int value)
	{
		_id = value;
	}
	public String getName()
	{
		return _name;
	}
	public int getID()
	{
		return _id;
	}

}
