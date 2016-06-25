package com.gildedgames.aether.common.tile_entities;

import com.gildedgames.aether.client.sound.objects.LabyrinthTotemSound;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.tile_entities.multiblock.TileEntityMultiblockController;
import com.gildedgames.aether.common.world.dungeon.DungeonInstance;
import com.gildedgames.aether.common.world.dungeon.DungeonInstanceHandler;
import com.gildedgames.util.modules.instances.InstanceModule;
import com.gildedgames.util.modules.instances.PlayerInstances;
import com.gildedgames.util.modules.world.common.BlockPosDimension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityLabyrinthTotem extends TileEntityMultiblockController implements ITickable
{
	@SideOnly(Side.CLIENT)
	public int renderTicks, prevRenderTicks;

	@SideOnly(Side.CLIENT)
	private ISound ambiance;

	public TileEntityLabyrinthTotem()
	{
		super(BlocksAether.labyrinth_totem);
	}

	@Override
	public void update()
	{
		if (this.worldObj.isRemote)
		{
			this.prevRenderTicks = this.renderTicks;

			this.renderTicks++;

			SoundHandler soundHandler = Minecraft.getMinecraft().getSoundHandler();

			if (this.ambiance == null || !soundHandler.isSoundPlaying(this.ambiance))
			{
				soundHandler.playSound(this.ambiance = new LabyrinthTotemSound(this.worldObj, this.pos));
			}
		}
	}

	@Override
	public void onInteract(EntityPlayer interactingPlayer)
	{
		EntityPlayerMP player = (EntityPlayerMP) interactingPlayer;

		DungeonInstanceHandler handler = AetherCore.INSTANCE.getDungeonInstanceHandler();

		PlayerInstances hook = InstanceModule.INSTANCE.getPlayer(player);

		if (hook.getInstance() != null)
		{
			handler.teleportBack(player);
		}
		else
		{
			DungeonInstance inst = handler.get(new BlockPosDimension(pos, this.worldObj.provider.getDimension()));
			handler.teleportToInst(player, inst);
		}
	}
}
