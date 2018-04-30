package com.gildedgames.aether.common.blocks.containers;

import com.gildedgames.aether.client.ClientEventHandler;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.entities.tiles.TileEntityTeleporter;
import com.gildedgames.aether.common.events.PostAetherTravelEvent;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.registry.content.InstancesAether;
import com.gildedgames.aether.common.world.necromancer_tower.NecromancerTowerInstance;
import com.gildedgames.aether.common.world.necromancer_tower.NecromancerTowerInstanceHandler;
import com.gildedgames.orbis_api.OrbisAPI;
import com.gildedgames.orbis_api.util.TeleporterGeneric;
import com.gildedgames.orbis_api.util.mc.BlockPosDimension;
import com.gildedgames.orbis_api.world.instances.IInstance;
import com.gildedgames.orbis_api.world.instances.IPlayerInstances;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;

public class BlockAetherTeleporter extends Block implements ITileEntityProvider
{

	public static final PropertyDirection PROPERTY_FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockAetherTeleporter()
	{
		super(Material.ROCK);

		this.setHardness(2.5f);
		this.setSoundType(SoundType.STONE);
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_FACING, EnumFacing.NORTH));
	}

	@Override
	public IBlockState getStateForPlacement(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY,
			final float hitZ, final int meta,
			final EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(PROPERTY_FACING, placer.getHorizontalFacing());
	}

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_FACING, EnumFacing.getHorizontal(meta));
	}

	@Override
	public int getMetaFromState(final IBlockState state)
	{
		return state.getValue(PROPERTY_FACING).getIndex();
	}

	@Override
	public boolean isFullCube(final IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(final IBlockState p_isOpaqueCube_1_)
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(
			final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing facing,
			final float hitX, final float hitY, final float hitZ)
	{
		if (player.world.provider.getDimensionType() == DimensionsAether.AETHER)
		{
			if (player instanceof EntityPlayerMP)
			{
				final EntityPlayerMP playerMP = (EntityPlayerMP) player;

				final PlayerAether playerAether = PlayerAether.getPlayer(player);

				playerAether.getTeleportingModule()
						.setAetherPos(new BlockPosDimension((int) player.posX, (int) player.posY, (int) player.posZ, player.dimension));

				final BlockPosDimension nonAetherPos = playerAether.getTeleportingModule().getNonAetherPos();

				final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

				final Teleporter teleporter = new TeleporterGeneric(server.getWorld(player.dimension));
				final PlayerList playerList = server.getPlayerList();
				playerList.transferPlayerToDimension(playerMP, nonAetherPos.getDim(), teleporter);
				player.timeUntilPortal = player.getPortalCooldown();

				playerMP.connection.setPlayerLocation(nonAetherPos.getX(), nonAetherPos.getY(), nonAetherPos.getZ(), 0, 0);
			}

			return false;
		}

		if (world.isRemote)
		{
			final PlayerAether playerAether = PlayerAether.getPlayer(player);

			if (!playerAether.getTeleportingModule().hasPlayedIntro())
			{
				ClientEventHandler.setDrawBlackScreen(true);
			}
			else
			{
				ClientEventHandler.setDrawLoading(true);
			}

			return true;
		}
		else
		{
			final NecromancerTowerInstanceHandler handler = InstancesAether.NECROMANCER_TOWER_HANDLER;

			final PlayerAether playerAether = PlayerAether.getPlayer(player);
			final IPlayerInstances hook = OrbisAPI.instances().getPlayer(player);

			if (playerAether.getTeleportingModule().getAetherPos() != null)
			{
				if (hook.getInstance() != null)
				{
					final IInstance instance = hook.getInstance();

					instance.onLeave(player);
				}

				final EntityPlayerMP playerMP = (EntityPlayerMP) player;
				final BlockPosDimension p = playerAether.getTeleportingModule().getAetherPos();

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
					handler.teleportBack((EntityPlayerMP) player);
				}
				else
				{
					hook.setInstance(null);

					final NecromancerTowerInstance inst = handler.get(playerAether);

					playerAether.getTeleportingModule()
							.setNonAetherPos(new BlockPosDimension((int) player.posX, (int) player.posY, (int) player.posZ, player.dimension));

					handler.teleportToInst((EntityPlayerMP) player, inst);
				}
			}
			else
			{
				final NecromancerTowerInstance inst = handler.get(playerAether);

				playerAether.getTeleportingModule()
						.setNonAetherPos(new BlockPosDimension((int) player.posX, (int) player.posY, (int) player.posZ, player.dimension));

				handler.teleportToInst((EntityPlayerMP) player, inst);
			}

			return true;
		}
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(final World world, final int i)
	{
		return new TileEntityTeleporter();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_FACING);
	}
}
