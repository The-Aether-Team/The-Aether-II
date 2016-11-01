package com.gildedgames.aether.client.ui.util.input;

import org.apache.commons.lang3.math.NumberUtils;

public class IntegerInput extends DataInputBase<Integer>
{

	private int data;

	public IntegerInput()
	{

	}

	public IntegerInput(int data)
	{
		this.data = data;
	}

	@Override
	public Integer getData()
	{
		return this.data;
	}

	@Override
	public void set(Integer data)
	{
		this.data = data;
	}

	@Override
	public boolean validString(String string)
	{
		return NumberUtils.isDigits(string);
	}

	@Override
	public Integer parse(String string)
	{
		return Integer.parseInt(string);
	}

}
