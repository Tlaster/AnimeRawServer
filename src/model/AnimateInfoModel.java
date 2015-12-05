package model;

import java.util.ArrayList;

public class AnimateInfoModel
{
	private ArrayList<AnimateSetModel> _setList;
	private String _name;
	private int _id;
	public AnimateInfoModel()
	{
		this(0,null,null);
	}
	public AnimateInfoModel(int id,String name,ArrayList<AnimateSetModel> list)
	{
		_id = id;
		_name = name;
		_setList = list;
	}
	public void setName(String value)
	{
		_name = value;
	}
	public void setID(int value)
	{
		_id = value;
	}
	public void setSetList(ArrayList<AnimateSetModel> value)
	{
		_setList = value;
	}
	public String getName()
	{
		return _name;
	}
	public int getID()
	{
		return _id;
	}
	public ArrayList<AnimateSetModel> getSetList()
	{
		return _setList;
	}
}
