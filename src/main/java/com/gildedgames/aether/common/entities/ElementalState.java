package com.gildedgames.aether.common.entities;

public enum ElementalState
{

	BIOLOGICAL
	{
		@Override
		public float getModifierAgainst(ElementalState element)
		{
			return 1.0F;
		}
	},
	FIRE
	{
		@Override
		public float getModifierAgainst(ElementalState element)
		{
			if (element == ElementalState.BLIGHT)
			{
				return 2.0F;
			}

			if (element == this)
			{
				return 0.5F;
			}

			return 1.0F;
		}
	},
	WATER
	{
		@Override
		public float getModifierAgainst(ElementalState element)
		{
			if (element == ElementalState.FIRE)
			{
				return 2.0F;
			}

			if (element == this)
			{
				return 0.5F;
			}

			return 1.0F;
		}
	},
	EARTH
	{
		@Override
		public float getModifierAgainst(ElementalState element)
		{
			if (element == ElementalState.FROST)
			{
				return 2.0F;
			}

			if (element == this)
			{
				return 0.5F;
			}

			return 1.0F;
		}
	},
	AIR
	{
		@Override
		public float getModifierAgainst(ElementalState element)
		{
			if (element == ElementalState.EARTH)
			{
				return 2.0F;
			}

			if (element == this)
			{
				return 0.5F;
			}

			return 1.0F;
		}
	},
	FROST
	{
		@Override
		public float getModifierAgainst(ElementalState element)
		{
			if (element == ElementalState.WATER)
			{
				return 2.0F;
			}

			if (element == this)
			{
				return 0.5F;
			}

			return 1.0F;
		}
	},
	BLIGHT
	{
		@Override
		public float getModifierAgainst(ElementalState element)
		{
			if (element == ElementalState.BIOLOGICAL)
			{
				return 2.0F;
			}

			if (element == this)
			{
				return 0.5F;
			}

			return 1.0F;
		}
	};

	ElementalState()
	{

	}

	public abstract float getModifierAgainst(ElementalState element);

}
