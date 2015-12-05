package model;

public class AnimateListModel
{

	private String _name;
	private int _id;
	private String _lastUpdate;
	public AnimateListModel(int id,String name,String lastUpdate)
	{
		_name = name;
		_id = id;
		_lastUpdate = lastUpdate;
	}
	public void setName(String value)
	{
		_name = value;
	}
	public void setID(int value)
	{
		_id = value;
	}
	public void setLastUpdate(String value)
	{
		_lastUpdate = value;
	}
	public String getName()
	{
		return _name;
	}
	public int getID()
	{
		return _id;
	}
	public String getLastUpdate()
	{
		return _lastUpdate;
	}

}
