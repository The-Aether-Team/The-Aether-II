package com.gildedgames.aether.common.entities.dungeon.labyrinth.boss;

import com.gildedgames.aether.api.capabilites.entity.boss.BossStageAction;
import com.gildedgames.aether.common.blocks.BlocksAether;
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
		return this.timer.getSecondsPassed() >= 11;
	}

	@Override public void update(EntitySlider entity)
	{
		if (entity.getStartLocation() == null)
		{
			return;
		}

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
						entity.worldObj.setBlockToAir(pos);
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
					entity.worldObj.setBlockToAir(pos);
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
