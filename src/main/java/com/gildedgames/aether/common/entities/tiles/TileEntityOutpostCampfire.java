package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.world.IWorldObjectHoverable;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.multiblock.BlockMultiController;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerCampfiresModule;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTeleportingModule;
import com.gildedgames.aether.common.entities.tiles.multiblock.TileEntityMultiblockController;
import com.gildedgames.aether.common.init.DimensionsAether;
import com.gildedgames.orbis.lib.util.TeleporterGeneric;
import com.gildedgames.orbis.lib.util.mc.BlockPosDimension;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class TileEntityOutpostCampfire extends TileEntityMultiblockController implements ITickableTileEntity, IWorldObjectHoverable
{

	private static final int PLAYER_SEARCHING_RADIUS = 2;

	private BlockPosDimension posDim;

	public TileEntityOutpostCampfire()
	{
		super((BlockMultiController) BlocksAether.outpost_campfire, BlocksAether.multiblock_dummy_half);
	}

	@Override
	public boolean onInteract(final PlayerEntity player)
	{
		if (player instanceof ServerPlayerEntity && player.world.provider.getDimensionType() == DimensionsAether.AETHER)
		{
			final ServerPlayerEntity playerMP = (ServerPlayerEntity) player;

			final PlayerAether playerAether = PlayerAether.getPlayer(player);

			final PlayerTeleportingModule teleportingModule = playerAether.getModule(PlayerTeleportingModule.class);
			teleportingModule.setAetherPos(new BlockPosDimension((int) player.posX, (int) player.posY, (int) player.posZ, player.dimension));

			BlockPosDimension pos = teleportingModule.getNonAetherPos();

			if (pos == null)
			{
				pos = new BlockPosDimension(DimensionManager.getWorld(0).getSpawnPoint(), 0);
			}

			final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

			final Teleporter teleporter = new TeleporterGeneric(server.getWorld(player.dimension));
			PlayerList playerList = server.getPlayerList();
			playerList.transferPlayerToDimension(playerMP, pos.getDim(), teleporter);
			player.timeUntilPortal = player.getPortalCooldown();

			playerMP.connection.setPlayerLocation(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);

			return true;
		}

		return false;
	}

	@Override
	public ItemStack getPickedStack(final World world, final BlockPos pos, final BlockState state)
	{
		return new ItemStack(BlocksAether.outpost_campfire);
	}

	@OnlyIn(Dist.CLIENT)
	public void clientUpdate()
	{
		PlayerAether playerAether = PlayerAether.getPlayer(Minecraft.getInstance().player);

		PlayerCampfiresModule campfiresModule = playerAether.getModule(PlayerCampfiresModule.class);

		if (campfiresModule.hasCampfire(this.posDim))
		{
			AetherCore.PROXY.spawnCampfireParticles(this.world, this.pos.getX() + 1.0D, this.pos.getY(), this.pos.getZ() + 1.0D);
		}
	}

	@Override
	public void update()
	{
		if (this.posDim == null)
		{
			this.posDim = new BlockPosDimension(this.pos, this.world.provider.getDimension());
		}

		AxisAlignedBB searchingBB = new AxisAlignedBB(
				new BlockPos(this.pos.getX() - PLAYER_SEARCHING_RADIUS, this.pos.getY() - PLAYER_SEARCHING_RADIUS,
						this.pos.getZ() - PLAYER_SEARCHING_RADIUS),
				new BlockPos(this.pos.getX() + PLAYER_SEARCHING_RADIUS + 2.0F, this.pos.getY() + PLAYER_SEARCHING_RADIUS,
						this.pos.getZ() + PLAYER_SEARCHING_RADIUS + 2.0F));

		List<PlayerEntity> players = this.world.getEntitiesWithinAABB(PlayerEntity.class, searchingBB);

		if (this.world.isRemote)
		{
			this.clientUpdate();
		}

		for (PlayerEntity player : players)
		{
			PlayerAether playerAether = PlayerAether.getPlayer(player);

			PlayerCampfiresModule campfiresModule = playerAether.getModule(PlayerCampfiresModule.class);

			if (!campfiresModule.hasCampfire(this.posDim))
			{
				campfiresModule.addActivatedCampfire(this.posDim);

				AetherCore.PROXY.spawnCampfireStartParticles(this.world, this.pos.getX() + 1.0D, this.pos.getY(), this.pos.getZ() + 1.0D);
			}
		}
	}

	@Override
	public ITextComponent getHoverText(World world, RayTraceResult result)
	{
		return new TranslationTextComponent("gui.aether.hover.campfire");
	}
}
