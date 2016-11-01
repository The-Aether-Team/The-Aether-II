package com.gildedgames.aether.client.ui.util.input;

public class StringInput extends DataInputBase<String>
{

	private String data;

	public StringInput()
	{

	}

	public StringInput(String data)
	{
		this.data = data;
	}

	@Override
	public String getData()
	{
		return this.data;
	}

	@Override
	public void set(String data)
	{
		this.data = data;
	}

	@Override
	public boolean validString(String string)
	{
		return true;
	}

	@Override
	public String parse(String string)
	{
		return string;
	}

}
