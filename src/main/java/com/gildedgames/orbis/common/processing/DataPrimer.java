package com.gildedgames.orbis.common.processing;

import com.gildedgames.orbis.common.block.BlockData;
import com.gildedgames.orbis.common.block.BlockInstance;
import com.gildedgames.orbis.common.data.ICreationData;
import com.gildedgames.orbis.common.events.ChangeBlockEvent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;

public class DataPrimer
{

	private final IPrimer primer;

	public DataPrimer(final IPrimer primer)
	{
		this.primer = primer;
	}

	public void create(final BlockInstance instance, final ICreationData data)
	{
		this.create(instance.getBlockData(), instance.getPos(), data);
	}

	public void create(final BlockData blockData, final BlockPos pos, final ICreationData creationData)
	{
		if (!blockData.isAir())
		{
			// TODO: Implement rotation processing
			final IBlockState rotated = blockData.getBlockState();

			this.primer.setBlockState(pos, rotated);

			if (blockData.getTileEntity() != null)
			{
				this.primer.setTileEntity(pos, blockData.getTileEntity());
			}
		}

		final ChangeBlockEvent changeBlockEvent = new ChangeBlockEvent(creationData.getWorld(), pos, creationData.getCreator());
		MinecraftForge.EVENT_BUS.post(changeBlockEvent);
	}

}
