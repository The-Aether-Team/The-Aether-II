package com.gildedgames.aether.api.capabilites.entity.properties;

import net.minecraft.util.text.TextFormatting;

public enum ElementalState
{

	BIOLOGICAL
	{
		@Override
		public float getModifierAgainst(ElementalState element)
		{
			return 1.0F;
		}

		@Override
		public String getUnlocalizedName()
		{
			return "elementalState.biological";
		}

		@Override
		public TextFormatting getNameFormatting()
		{
			return TextFormatting.BLUE;
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

		@Override
		public String getUnlocalizedName()
		{
			return "elementalState.fire";
		}

		@Override
		public TextFormatting getNameFormatting()
		{
			return TextFormatting.DARK_RED;
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

		@Override
		public String getUnlocalizedName()
		{
			return "elementalState.water";
		}

		@Override
		public TextFormatting getNameFormatting()
		{
			return TextFormatting.BLUE;
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

		@Override
		public String getUnlocalizedName()
		{
			return "elementalState.earth";
		}

		@Override
		public TextFormatting getNameFormatting()
		{
			return TextFormatting.GOLD;
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

		@Override
		public String getUnlocalizedName()
		{
			return "elementalState.air";
		}

		@Override
		public TextFormatting getNameFormatting()
		{
			return TextFormatting.WHITE;
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

		@Override
		public String getUnlocalizedName()
		{
			return "elementalState.frost";
		}

		@Override
		public TextFormatting getNameFormatting()
		{
			return TextFormatting.DARK_AQUA;
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

		@Override
		public String getUnlocalizedName()
		{
			return "elementalState.blight";
		}

		@Override
		public TextFormatting getNameFormatting()
		{
			return TextFormatting.LIGHT_PURPLE;
		}
	};

	ElementalState()
	{

	}

	public abstract float getModifierAgainst(ElementalState element);

	public abstract String getUnlocalizedName();

	public abstract TextFormatting getNameFormatting();

	public static ElementalState fromOrdinal(int ordinal)
	{
		ElementalState[] type = values();

		return type[ordinal > type.length || ordinal < 0 ? 0 : ordinal];
	}

}
