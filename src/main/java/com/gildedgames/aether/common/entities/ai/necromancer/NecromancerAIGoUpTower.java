package com.gildedgames.aether.common.entities.ai.necromancer;

import com.gildedgames.aether.common.entities.ai.EntityAIMoveToBlockYSensitive;
import com.gildedgames.aether.common.entities.characters.EntityNecromancer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NecromancerAIGoUpTower extends EntityAIMoveToBlockYSensitive
{

	private final EntityNecromancer necromancer;

	private final BlockPos[] positions;

	private int index;

	private boolean shouldExecute;

	public NecromancerAIGoUpTower(final EntityNecromancer necromancer, final BlockPos... positions)
	{
		super(necromancer, 0.5D, 55);

		this.necromancer = necromancer;
		this.positions = positions;
	}

	public void setShouldExecute(final boolean shouldExecute)
	{
		this.shouldExecute = shouldExecute;
	}

	@Override
	public boolean shouldExecute()
	{
		if (this.necromancer.getNavigator().getPath() != null && this.necromancer.getNavigator().getPath().isFinished())
		{
			if (this.index < this.positions.length - 1)
			{
				this.index++;
			}
		}

		this.runDelay = 0;

		return super.shouldExecute() && this.shouldExecute;
	}

	private BlockPos getCurrentPos()
	{
		return this.positions[this.index];
	}

	@Override
	protected boolean shouldMoveTo(final World worldIn, final BlockPos pos)
	{
		if (!this.shouldExecute)
		{
			return false;
		}

		return pos.equals(this.getCurrentPos());
	}
}
