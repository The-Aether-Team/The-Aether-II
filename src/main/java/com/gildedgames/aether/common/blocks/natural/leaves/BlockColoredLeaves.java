package com.gildedgames.aether.common.blocks.natural.leaves;

import com.gildedgames.aether.common.entities.living.passive.EntitySkyrootLizard;

public abstract class BlockColoredLeaves extends BlockNaturalLeaves
{
	private final Color color;

	public BlockColoredLeaves(Color color)
	{
		this.color = color;
	}

	@Override
	protected void applyLizardProperties(EntitySkyrootLizard lizard)
	{
		lizard.setLizardType(EntitySkyrootLizard.Type.SKYROOT);
		lizard.setLizardColor(this.getLeafColor());
	}

	protected Color getLeafColor()
	{
		return this.color;
	}

	public enum Color
	{
		GREEN,
		BLUE,
		DARK_BLUE;

		public static final Color[] VALUES = Color.values();
	}

}
