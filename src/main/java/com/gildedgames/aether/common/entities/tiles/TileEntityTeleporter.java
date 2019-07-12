package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.world.IWorldObjectHoverable;
import com.gildedgames.aether.client.events.listeners.gui.GuiLoadingListener;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.containers.BlockAltar;
import com.gildedgames.aether.common.blocks.multiblock.BlockMultiController;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerConfigModule;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTeleportingModule;
import com.gildedgames.aether.common.entities.tiles.multiblock.TileEntityMultiblockController;
import com.gildedgames.aether.common.events.PostAetherTravelEvent;
import com.gildedgames.aether.common.init.DimensionsAether;
import com.gildedgames.aether.common.init.InstancesAether;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketSetPlayedIntro;
import com.gildedgames.aether.common.util.helpers.AetherHelper;
import com.gildedgames.aether.common.world.instances.necromancer_tower.NecromancerTowerInstanceHelper;
import com.gildedgames.orbis.lib.OrbisLib;
import com.gildedgames.orbis.lib.util.TeleporterGeneric;
import com.gildedgames.orbis.lib.util.mc.BlockPosDimension;
import com.gildedgames.orbis.lib.world.instances.IInstance;
import com.gildedgames.orbis.lib.world.instances.IPlayerInstances;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.Direction;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TileEntityTeleporter extends TileEntityMultiblockController implements ITickableTileEntity, IWorldObjectHoverable
{
	@OnlyIn(Dist.CLIENT)
	public int animationTicks, prevAnimationTicks;

	private int buildTime;

	public TileEntityTeleporter()
	{
		super((BlockMultiController) BlocksAether.aether_teleporter, BlocksAether.multiblock_dummy);
	}

	public Direction getFacing()
	{
		BlockState state = this.world.getBlockState(this.pos);

		if (state.getBlock() == BlocksAether.aether_teleporter)
		{
			return state.getValue(BlockAltar.PROPERTY_FACING);
		}

		return Direction.NORTH;
	}

	@Override
	public void update()
	{
		if (this.world.isRemote)
		{
			this.prevAnimationTicks = this.animationTicks;

			this.animationTicks++;
		}

		if (this.buildTime < 200)
		{
			this.buildTime++;
		}
		else
		{
			this.buildTime = 0;
		}
	}

	@Override
	public void readFromNBT(CompoundNBT compound)
	{
		super.readFromNBT(compound);

		this.buildTime = compound.getInt("BuildTime");
	}

	@Override
	public CompoundNBT writeToNBT(CompoundNBT compound)
	{
		super.writeToNBT(compound);

		compound.putInt("BuildTime", (short) this.buildTime);

		return compound;
	}

	public int getBuildTime()
	{
		return this.buildTime;
	}

	@Override
	public boolean onInteract(PlayerEntity player)
	{
		if (player.world.provider.getDimensionType() == DimensionsAether.AETHER)
		{
			if (player instanceof ServerPlayerEntity)
			{
				final ServerPlayerEntity playerMP = (ServerPlayerEntity) player;

				final PlayerAether playerAether = PlayerAether.getPlayer(player);
				final PlayerTeleportingModule teleportingModule = playerAether.getModule(PlayerTeleportingModule.class);

				teleportingModule
						.setAetherPos(new BlockPosDimension((int) player.posX, (int) player.posY, (int) player.posZ, player.dimension));

				final BlockPosDimension nonAetherPos = teleportingModule.getNonAetherPos();

				final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

				final Teleporter teleporter = new TeleporterGeneric(server.getWorld(player.dimension));
				final PlayerList playerList = server.getPlayerList();
				playerList.transferPlayerToDimension(playerMP, nonAetherPos.getDim(), teleporter);
				player.timeUntilPortal = player.getPortalCooldown();

				playerMP.connection.setPlayerLocation(nonAetherPos.getX(), nonAetherPos.getY(), nonAetherPos.getZ(), 0, 0);
			}

			return false;
		}

		if (this.world.isRemote)
		{
			final PlayerAether playerAether = PlayerAether.getPlayer(player);
			final PlayerTeleportingModule teleportingModule = playerAether.getModule(PlayerTeleportingModule.class);

			if (AetherCore.CONFIG.skipIntro())
			{
				teleportingModule.setPlayedIntro(true);
				NetworkingAether.sendPacketToServer(new PacketSetPlayedIntro(true));
			}

			if (!teleportingModule.hasPlayedIntro())
			{
				GuiLoadingListener.setDrawBlackScreen(true);
			}
			else
			{
				GuiLoadingListener.setDrawLoading(true);
			}

			return true;
		}
		else
		{
			final NecromancerTowerInstanceHelper handler = InstancesAether.NECROMANCER_TOWER_HANDLER;

			final PlayerAether playerAether = PlayerAether.getPlayer(player);
			final PlayerTeleportingModule teleportingModule = playerAether.getModule(PlayerTeleportingModule.class);

			final IPlayerInstances hook = OrbisLib.instances().getPlayer(player);

			if (teleportingModule.getAetherPos() != null)
			{
				final ServerPlayerEntity playerMP = (ServerPlayerEntity) player;
				final BlockPosDimension p = teleportingModule.getAetherPos();

				final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

				if (!net.minecraftforge.common.ForgeHooks.onTravelToDimension(playerMP, p.getDim()))
				{
					return false;
				}

				final Teleporter teleporter = new TeleporterGeneric(server.getWorld(player.dimension));
				final PlayerList playerList = server.getPlayerList();
				playerList.transferPlayerToDimension(playerMP, p.getDim(), teleporter);
				player.timeUntilPortal = player.getPortalCooldown();

				playerMP.connection.setPlayerLocation(p.getX(), p.getY(), p.getZ(), 225, 0);

				PostAetherTravelEvent event = new PostAetherTravelEvent(playerMP);
				MinecraftForge.EVENT_BUS.post(event);
			}
			else if (hook.getInstance() != null)
			{
				final IInstance instance = hook.getInstance();

				if (player.dimension == instance.getDimensionId())
				{
					handler.teleportBack((ServerPlayerEntity) player);
				}
				else
				{
					hook.setInstance(null);

					handler.teleportToInst((ServerPlayerEntity) player);
				}
			}
			else
			{
				if (playerAether.getModule(PlayerConfigModule.class).skipIntro())
				{
					teleportingModule.teleportToAether();
				}
				else
				{
					handler.teleportToInst((ServerPlayerEntity) player);
				}
			}

			return true;
		}
	}

	@Override
	public ItemStack getPickedStack(World world, BlockPos pos, BlockState state)
	{
		return new ItemStack(BlocksAether.aether_teleporter);
	}

	@Override
	public ITextComponent getHoverText(World world, RayTraceResult result)
	{
		return new TranslationTextComponent(AetherHelper.isAether(world) ? "gui.aether.hover.campfire" : "gui.aether.hover.teleporter");
	}
}
