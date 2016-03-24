package com.gildedgames.aether.common.entities;

public enum ElementalState
{

	FIRE
			{
				@Override
				public float getModifierAgainst(ElementalState element)
				{
					if (element == ElementalState.WATER || element == ElementalState.FROST)
					{
						return 2.0F;
					}

					if (element == ElementalState.EARTH || element == ElementalState.AIR)
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
					if (element == ElementalState.FIRE || element == ElementalState.FORGED)
					{
						return 2.0F;
					}

					if (element == ElementalState.EARTH || element == ElementalState.BIOLOGICAL)
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

					if (element == ElementalState.AIR)
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
					if (element == ElementalState.BLIGHT)
					{
						return 2.0F;
					}

					if (element == ElementalState.EARTH)
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
					if (element == ElementalState.EARTH)
					{
						return 2.0F;
					}

					if (element == ElementalState.AIR)
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

					if (element == ElementalState.FORGED || element == ElementalState.FIRE || element == ElementalState.FROST || element == ElementalState.EARTH || element == ElementalState.AIR)
					{
						return 0.5F;
					}

					if (element == ElementalState.BLIGHT)
					{
						return 0.0F;
					}

					return 1.0F;
				}
			},
	BIOLOGICAL
			{
				@Override
				public float getModifierAgainst(ElementalState element)
				{
					return 1.0F;
				}
			},
	FORGED
			{
				@Override
				public float getModifierAgainst(ElementalState element)
				{
					return 1.0F;
				}
			};

	ElementalState()
	{

	}

	public abstract float getModifierAgainst(ElementalState element);

}
