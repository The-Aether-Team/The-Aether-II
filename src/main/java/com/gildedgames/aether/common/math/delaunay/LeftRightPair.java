package com.gildedgames.aether.common.math.delaunay;

public class LeftRightPair<T>
{
	private T left, right;

	public void put(LeftRight leftRight, T val)
	{
		if (leftRight == LeftRight.LEFT)
		{
			this.setLeft(val);
		}
		else if (leftRight == LeftRight.RIGHT)
		{
			this.setRight(val);
		}
	}

	public T get(LeftRight leftRight)
	{
		if (leftRight == LeftRight.LEFT)
		{
			return this.getLeft();
		}
		else if (leftRight == LeftRight.RIGHT)
		{
			return this.getRight();
		}

		return null;
	}

	public T getLeft()
	{
		return this.left;
	}

	public T getRight()
	{
		return this.right;
	}

	public void setLeft(T val)
	{
		this.left = val;
	}

	public void setRight(T val)
	{
		this.right = val;
	}
}
