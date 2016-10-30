package com.gildedgames.aether.common.entities.living.boss.slider;

import com.gildedgames.aether.api.capabilites.entity.boss.BossStageAction;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.util.sliding.SlidingHorizontalMoveHelper;
import com.gildedgames.aether.common.util.TickTimer;
import com.gildedgames.util.core.nbt.NBTHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class BreakFloorActionSlider implements BossStageAction<EntitySlider>
{

	private TickTimer timer = new TickTimer();

	public BreakFloorActionSlider()
	{

	}

	@Override public boolean shouldRemove()
	{
		return this.timer.getSecondsPassed() >= 13;
	}

	@Override public void update(EntitySlider entity)
	{
		if (entity.getStartLocation() == null)
		{
			return;
		}

		if (entity.getDistance(entity.getStartLocation().getX(), entity.getStartLocation().getY(), entity.getStartLocation().getZ()) > 2.0D)
		{
			entity.getMoveHelper().setMoveTo(entity.getStartLocation().getX(), entity.getStartLocation().getY(), entity.getStartLocation().getZ(), 3.0D);

			return;
		}

		((SlidingHorizontalMoveHelper)entity.getMoveHelper()).stop();

		this.timer.tick();

		if (this.timer.getSecondsPassed() < 10)
		{
			if (this.timer.isMultipleOfSeconds())
			{
				BlockPos min = entity.getStartLocation().add(-50, -1, -50);
				BlockPos max = entity.getStartLocation().add(50, -1, 50);

				for (BlockPos pos : BlockPos.getAllInBox(min, max))
				{
					IBlockState state = entity.worldObj.getBlockState(pos);

					if (entity.getRNG().nextInt(5) == 0 && state != null && state.getBlock() == BlocksAether.unstable_labyrinth_capstone)
					{
						entity.worldObj.destroyBlock(pos, false);
					}
				}
			}
		}
		else
		{
			BlockPos min = entity.getStartLocation().add(-50, -1, -50);
			BlockPos max = entity.getStartLocation().add(50, -1, 50);

			for (BlockPos pos : BlockPos.getAllInBox(min, max))
			{
				IBlockState state = entity.worldObj.getBlockState(pos);

				if (state != null && state.getBlock() == BlocksAether.unstable_labyrinth_capstone)
				{
					entity.worldObj.destroyBlock(pos, false);
				}
			}
		}
	}

	@Override public void write(NBTTagCompound output)
	{
		NBTHelper.fullySerialize("timer", this.timer, output);
	}

	@Override public void read(NBTTagCompound input)
	{
		this.timer = NBTHelper.fullyDeserialize("timer", input);
	}

}
