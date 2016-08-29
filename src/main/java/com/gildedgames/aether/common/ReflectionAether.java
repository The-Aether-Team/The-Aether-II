package com.gildedgames.aether.common;

public class ReflectionAether
{

	public static class ReflectionEntry
	{
		private String[] mappings;

		private ReflectionEntry(String... mappings)
		{
			this.mappings = mappings;
		}

		public String[] getMappings()
		{
			return this.mappings;
		}
	}

	public static final ReflectionEntry EQUIPPED_PROGRESS_MAIN_HAND = new ReflectionEntry("field_187469_f", "equippedProgressMainHand");

	public static final ReflectionEntry NET_CLIENT_HANDLER = new ReflectionEntry("field_78774_b", "netClientHandler");

	public static final ReflectionEntry INVULNERABLE_DIMENSION_CHANGE = new ReflectionEntry("field_184851_cj", "invulnerableDimensionChange");

	public static final ReflectionEntry BLOCK_HARDNESS = new ReflectionEntry("field_149782_v", "blockHardness");

	public static final ReflectionEntry IS_JUMPING = new ReflectionEntry("field_70703_bu", "isJumping");

	public static final ReflectionEntry FOOD_LEVEL = new ReflectionEntry("field_75127_a", "foodLevel");

	public static final ReflectionEntry FOOD_SATURATION_LEVEL = new ReflectionEntry("field_75125_b", "foodSaturationLevel");

	public static final ReflectionEntry FOOD_EXHAUSTION_LEVEL = new ReflectionEntry("field_75126_c", "foodExhaustionLevel");

}
