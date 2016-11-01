package com.gildedgames.aether.client.ui.util.input;

import org.apache.commons.lang3.math.NumberUtils;

public class FloatInput extends DataInputBase<Float>
{

	private float data;

	public FloatInput()
	{

	}

	public FloatInput(float data)
	{
		this.data = data;
	}

	@Override
	public Float getData()
	{
		return this.data;
	}

	@Override
	public void set(Float data)
	{
		this.data = data;
	}

	@Override
	public boolean validString(String string)
	{
		return NumberUtils.isNumber(string);
	}

	@Override
	public Float parse(String string)
	{
		return Float.parseFloat(string);
	}

}
