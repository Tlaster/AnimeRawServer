package model;

public class AnimateListModel
{

	private String _name;
	private String _dirPath;
	private int _id;
	public AnimateListModel(String name, String dirPath, int id)
	{
		_name = name;
		_dirPath = dirPath;
		_id = id;
	}
	public void setName(String value)
	{
		_name = value;
	}
	public void setDirPath(String value)
	{
		_dirPath = value;
	}
	public void setID(int value)
	{
		_id = value;
	}
	public String getName()
	{
		return _name;
	}
	public String getDirPath()
	{
		return _dirPath;
	}
	public int getID()
	{
		return _id;
	}

}
