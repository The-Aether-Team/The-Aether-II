package com.gildedgames.aether.common.tiles;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.client.sound.objects.LabyrinthTotemSound;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.tiles.multiblock.TileEntityMultiblockController;
import com.gildedgames.aether.common.world.dungeon.instance.DungeonInstance;
import com.gildedgames.aether.common.world.dungeon.instance.DungeonInstanceHandler;
import com.gildedgames.aether.api.util.BlockPosDimension;
import com.gildedgames.aether.api.capabilites.instances.IPlayerInstances;
import com.gildedgames.aether.api.capabilites.instances.Instance;
import com.gildedgames.aether.common.network.packets.PacketRegisterDimension;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
			this.onClientUpdate();
		}
	}

	@SideOnly(Side.CLIENT)
	public void onClientUpdate()
	{
		this.prevRenderTicks = this.renderTicks;

		this.renderTicks++;

		SoundHandler soundHandler = Minecraft.getMinecraft().getSoundHandler();

		if (this.ambiance == null || !soundHandler.isSoundPlaying(this.ambiance))
		{
			soundHandler.playSound(this.ambiance = new LabyrinthTotemSound(this.worldObj, this.pos));
		}
	}

	@Override
	public void onInteract(EntityPlayer interactingPlayer)
	{
		EntityPlayerMP player = (EntityPlayerMP) interactingPlayer;

		DungeonInstanceHandler handler = AetherCore.PROXY.getDungeonInstanceHandler();

		IPlayerInstances hook = AetherAPI.instances().getPlayer(player);

		if (hook.getInstance() instanceof Instance)
		{
			Instance instance = hook.getInstance();

			if (interactingPlayer.dimension == instance.getDimIdInside())
			{
				handler.teleportBack(player);

				hook.setInstance(null);
			}
			else
			{
				DungeonInstance inst = handler.get(new BlockPosDimension(this.pos, this.worldObj.provider.getDimension()));

				if (interactingPlayer instanceof EntityPlayerMP)
				{
					NetworkingAether.sendPacketToPlayer(new PacketRegisterDimension(DimensionsAether.SLIDER_LABYRINTH, inst.getDimIdInside()), (EntityPlayerMP)interactingPlayer);
				}

				handler.teleportToInst(player, inst);
			}
		}
		else
		{
			DungeonInstance inst = handler.get(new BlockPosDimension(this.pos, this.worldObj.provider.getDimension()));

			if (interactingPlayer instanceof EntityPlayerMP)
			{
				NetworkingAether.sendPacketToPlayer(new PacketRegisterDimension(DimensionsAether.SLIDER_LABYRINTH, inst.getDimIdInside()), (EntityPlayerMP)interactingPlayer);
			}

			handler.teleportToInst(player, inst);
		}
	}

	@Override
	public ItemStack getPickedStack(World world, BlockPos pos, IBlockState state)
	{
		return new ItemStack(BlocksAether.labyrinth_totem);
	}

}
