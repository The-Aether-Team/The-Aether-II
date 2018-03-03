package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.multiblock.BlockMultiController;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.entities.tiles.multiblock.TileEntityMultiblockController;
import com.gildedgames.orbis.api.util.TeleporterGeneric;
import com.gildedgames.orbis.api.util.mc.BlockPosDimension;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TileEntityOutpostCampfire extends TileEntityMultiblockController implements ITickable
{

	public TileEntityOutpostCampfire()
	{
		super((BlockMultiController) BlocksAether.outpost_campfire, BlocksAether.multiblock_dummy_half);
	}

	@Override
	public void onInteract(final EntityPlayer player)
	{
		if (player instanceof EntityPlayerMP)
		{
			final EntityPlayerMP playerMP = (EntityPlayerMP) player;

			final PlayerAether playerAether = PlayerAether.getPlayer(player);

			playerAether.getTeleportingModule().setAetherPos(new BlockPosDimension((int) player.posX, (int) player.posY, (int) player.posZ, player.dimension));

			final BlockPosDimension pos = playerAether.getTeleportingModule().getNonAetherPos();

			final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

			final Teleporter teleporter = new TeleporterGeneric(server.getWorld(player.dimension));
			final PlayerList playerList = server.getPlayerList();
			playerList.transferPlayerToDimension(playerMP, pos.getDim(), teleporter);
			player.timeUntilPortal = player.getPortalCooldown();

			playerMP.connection.setPlayerLocation(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
		}

	}

	@Override
	public ItemStack getPickedStack(final World world, final BlockPos pos, final IBlockState state)
	{
		return new ItemStack(BlocksAether.outpost_campfire);
	}

	@Override
	public void update()
	{

	}

}
