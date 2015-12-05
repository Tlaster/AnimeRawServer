package model;

public class AnimateSetModel {
	private String _name;
	private int _clickCount;
	public AnimateSetModel(String name,int clickCount)
	{
		_name = name;
		_clickCount = clickCount;
	}
	public void setName(String value)
	{
		_name = value;
	}
	public void setClickCount(int value)
	{
		_clickCount = value;
	}
	public String getName()
	{
		return _name;
	}
	public int getClickCount()
	{
		return _clickCount;
	}
}
