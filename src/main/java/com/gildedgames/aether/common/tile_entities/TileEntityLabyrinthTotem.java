package com.gildedgames.aether.common.tile_entities;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.tile_entities.multiblock.TileEntityMultiblockController;
import com.gildedgames.aether.common.world.dungeon.DungeonInstance;
import com.gildedgames.aether.common.world.dungeon.DungeonInstanceHandler;
import com.gildedgames.util.modules.instances.InstanceModule;
import com.gildedgames.util.modules.instances.PlayerInstances;
import com.gildedgames.util.modules.world.common.BlockPosDimension;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityLabyrinthTotem extends TileEntityMultiblockController implements ITickable
{
	@SideOnly(Side.CLIENT)
	public int renderTicks, prevRenderTicks;

	@Override
	public void update()
	{
		if (this.worldObj.isRemote)
		{
			this.prevRenderTicks = this.renderTicks;

			this.renderTicks++;
		}
	}

	@Override
	public void onInteract(EntityPlayer interactingPlayer)
	{
		AetherCore.LOGGER.info("Hi!");

		if (!this.worldObj.isRemote)
		{
			EntityPlayerMP player = (EntityPlayerMP) interactingPlayer;

			DungeonInstanceHandler handler = AetherCore.locate().getDungeonInstanceHandler();
			DungeonInstance inst = handler.get(new BlockPosDimension(pos, this.worldObj.provider.getDimensionId()));

			PlayerInstances hook = InstanceModule.INSTANCE.getPlayer(player);

			if (hook.getInstance() != null)
			{
				handler.teleportBack(player);
			}
			else
			{
				handler.teleportToInst(player, inst);
			}
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox()
	{
		return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY() + 1.5D, pos.getZ());
	}
}
