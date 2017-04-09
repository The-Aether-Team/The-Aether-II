package com.gildedgames.aether.common.tiles;

import com.gildedgames.aether.client.sound.objects.LabyrinthTotemSound;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.tiles.multiblock.TileEntityMultiblockController;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
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
		super(BlocksAether.labyrinth_totem, BlocksAether.multiblock_dummy);
	}

	@Override
	public void update()
	{
		if (this.world.isRemote)
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
			soundHandler.playSound(this.ambiance = new LabyrinthTotemSound(this.world, this.pos));
		}
	}

	@Override
	public void onInteract(EntityPlayer interactingPlayer)
	{
		EntityPlayerMP player = (EntityPlayerMP) interactingPlayer;

		player.sendMessage(new TextComponentString("Dungeons are not currently implemented. Sorry about that!"));
	}

	@Override
	public ItemStack getPickedStack(World world, BlockPos pos, IBlockState state)
	{
		return new ItemStack(BlocksAether.labyrinth_totem);
	}

}
