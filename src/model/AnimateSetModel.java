package model;

public class AnimateSetModel 
{
	private String _fileName;
	private String _itemPath;
	public AnimateSetModel(String fileName,String itemPath)
	{
		_fileName = fileName;
		_itemPath = itemPath;
	}
	public void setFileName(String value)
	{
		_fileName = value;
	}
	public void setItemPath(String value)
	{
		_itemPath = value;
	}
	public String getFileName()
	{
		return _fileName;
	}
	public String getItemPath()
	{
		return _itemPath;
	}
}
