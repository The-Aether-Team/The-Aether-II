package com.gildedgames.aether.client.ui.util.input;

public class BooleanInput extends DataInputBase<Boolean>
{

	private boolean data;

	public BooleanInput()
	{

	}

	public BooleanInput(boolean data)
	{
		this.data = data;
	}

	@Override
	public Boolean getData()
	{
		return this.data;
	}

	@Override
	public void set(Boolean data)
	{
		this.data = data;
	}

	@Override
	public boolean validString(String string)
	{
		return false;
	}

	@Override
	public Boolean parse(String string)
	{
		return false;
	}

}
