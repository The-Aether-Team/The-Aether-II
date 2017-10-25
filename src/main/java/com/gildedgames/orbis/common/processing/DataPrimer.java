package com.gildedgames.orbis.common.processing;

import com.gildedgames.aether.api.orbis.region.Region;
import com.gildedgames.aether.api.orbis.util.OrbisRotation;
import com.gildedgames.aether.api.orbis.util.OrbisTuple;
import com.gildedgames.aether.api.orbis.util.RotationHelp;
import com.gildedgames.orbis.common.block.BlockData;
import com.gildedgames.orbis.common.block.BlockDataContainer;
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
		if (!blockData.isVoid())
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

	public void create(final BlockDataContainer container, final BlockPos pos, final OrbisRotation rotation, final ICreationData options)
	{
		final BlockPos min = pos;
		final BlockPos max = new BlockPos(min.getX() + container.getWidth() - 1, min.getY() + container.getHeight() - 1,
				min.getZ() + container.getLength() - 1);

		final int rotAmount = Math.abs(rotation.getRotationAmount(OrbisRotation.NORTH));

		if (rotAmount != 0)
		{
			for (final OrbisTuple<BlockPos, BlockPos> tuple : RotationHelp.getAllInBoxRotated(min, max, rotation))
			{
				final BlockPos beforeRot = tuple.getFirst();
				final BlockPos rotated = tuple.getSecond();

				final BlockData toCreate = container.get(beforeRot.getX() - min.getX(), beforeRot.getY() - min.getY(), beforeRot.getZ() - min.getZ());

				this.create(toCreate, rotated, options);
			}
		}
		else
		{
			final Region region = new Region(min, max);

			for (final BlockPos iterPos : region.getMutableBlockPosInRegion())
			{
				final BlockData block = container.get(iterPos.getX() - min.getX(), iterPos.getY() - min.getY(), iterPos.getZ() - min.getZ());

				if (block != null)
				{
					this.create(block, iterPos, options);
				}
			}
		}
	}

}
