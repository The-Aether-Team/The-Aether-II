package com.gildedgames.aether.client.ui.data;

public class Pos2D
{

	private final float x, y;

	private Pos2D(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public float x()
	{
		return this.x;
	}

	public float y()
	{
		return this.y;
	}

	@Override
	public String toString()
	{
		return "Position() X: '" + this.x + "', Y: '" + this.y + "'";
	}

	public Pos2DBuilder clone()
	{
		return new Pos2DBuilder(this);
	}

	public static Pos2D flush()
	{
		return new Pos2DBuilder().flush();
	}

	public static Pos2D flush(float x, float y)
	{
		return new Pos2D(x, y);
	}

	public static Pos2DBuilder build()
	{
		return new Pos2DBuilder();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Pos2D))
		{
			return false;
		}

		Pos2D pos = (Pos2D) obj;

		return pos.x() == this.x() && pos.y() == this.y();
	}

	public static class Pos2DBuilder
	{

		private float x, y;

		private Pos2DBuilder()
		{

		}

		private Pos2DBuilder(Pos2D pos)
		{
			this.x = pos.x;
			this.y = pos.y;
		}

		private Pos2DBuilder(Pos2DBuilder builder)
		{
			this.x = builder.x;
			this.y = builder.y;
		}

		public Pos2DBuilder x(float x)
		{
			this.x = x;

			return this;
		}

		public Pos2DBuilder y(float y)
		{
			this.y = y;

			return this;
		}

		public Pos2DBuilder addX(float x)
		{
			return this.add(x, 0);
		}

		public Pos2DBuilder addY(float y)
		{
			return this.add(0, y);
		}

		public Pos2DBuilder subtractX(float x)
		{
			return this.subtract(x, 0);
		}

		public Pos2DBuilder subtractY(float y)
		{
			return this.subtract(0, y);
		}

		public Pos2DBuilder subtract(float x, float y)
		{
			return this.add(-x, -y);
		}

		public Pos2DBuilder subtract(Pos2D pos)
		{
			if (pos == null)
			{
				return this;
			}

			return this.add(-pos.x, -pos.y);
		}

		public Pos2DBuilder add(float x, float y)
		{
			this.x += x;
			this.y += y;

			return this;
		}

		public Pos2DBuilder add(Pos2D pos)
		{
			if (pos == null)
			{
				return this;
			}

			return this.add(pos.x, pos.y);
		}

		public Pos2D flush()
		{
			return new Pos2D(this.x, this.y);
		}

	}

}
