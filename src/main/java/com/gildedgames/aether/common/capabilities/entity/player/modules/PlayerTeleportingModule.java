package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.api.player.IPlayerAetherModule;
import com.gildedgames.aether.api.registrar.SoundsAether;
import com.gildedgames.aether.client.events.listeners.gui.GuiLoadingListener;
import com.gildedgames.aether.client.gui.misc.GuiIntro;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.events.PostAetherTravelEvent;
import com.gildedgames.aether.common.init.DimensionsAether;
import com.gildedgames.aether.common.init.InstancesAether;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketSetPlayedIntro;
import com.gildedgames.aether.common.util.helpers.IslandHelper;
import com.gildedgames.aether.common.world.instances.necromancer_tower.NecromancerTowerInstanceHelper;
import com.gildedgames.orbis.lib.OrbisLib;
import com.gildedgames.orbis.lib.util.TeleporterGeneric;
import com.gildedgames.orbis.lib.util.io.NBTFunnel;
import com.gildedgames.orbis.lib.util.mc.BlockPosDimension;
import com.gildedgames.orbis.lib.world.instances.IInstance;
import com.gildedgames.orbis.lib.world.instances.IPlayerInstances;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerTeleportingModule extends PlayerAetherModule implements IPlayerAetherModule.Serializable
{
	public static final int TELEPORT_DELAY = 80;

	private boolean isTeleportCharging;

	private BlockPosDimension nonAetherPos, aetherPos;

	private boolean playedIntro;

	private float lastPercent;

	private int ticksInTeleporter, prevTicksInTeleporter;

	public PlayerTeleportingModule(final PlayerAether playerAether)
	{
		super(playerAether);
	}

	public BlockPosDimension getNonAetherPos()
	{
		return this.nonAetherPos;
	}

	public void setNonAetherPos(final BlockPosDimension pos)
	{
		this.nonAetherPos = pos;
	}

	public BlockPosDimension getAetherPos()
	{
		return this.aetherPos;
	}

	public void setAetherPos(final BlockPosDimension pos)
	{
		this.aetherPos = pos;
	}

	public boolean hasPlayedIntro()
	{
		return this.playedIntro;
	}

	public void setPlayedIntro(final boolean playedIntro)
	{
		this.playedIntro = playedIntro;
	}

	public int getTicksInTeleporter()
	{
		return this.ticksInTeleporter;
	}

	public int getPrevTicksInTeleporter()
	{
		return this.prevTicksInTeleporter;
	}

	@SideOnly(Side.CLIENT)
	private void onUpdateClient()
	{
		if (this.getWorld().provider.getDimensionType() == DimensionsAether.NECROMANCER_TOWER)
		{
			if (!this.playedIntro && Minecraft.getMinecraft().currentScreen == null)
			{
				Minecraft.getMinecraft().displayGuiScreen(new GuiIntro());
			}
		}
	}

	public float getLastPercent()
	{
		return this.lastPercent;
	}

	public void setLastPercent(float lastPercent)
	{
		this.lastPercent = lastPercent;
	}

	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{
		this.isTeleportCharging = false;
	}

	@Override
	public void tickEnd(TickEvent.PlayerTickEvent event)
	{
		if (this.getWorld().isRemote)
		{
			this.onUpdateClient();
		}

		this.prevTicksInTeleporter = this.ticksInTeleporter;

		if (this.isTeleportCharging && this.getEntity().getEntityWorld().provider.getDimensionType() != DimensionsAether.NECROMANCER_TOWER
				&& this.getEntity().getEntityWorld().provider.getDimensionType() != DimensionsAether.AETHER)
		{
			if (this.getEntity().timeUntilPortal > 0)
			{
				this.getEntity().timeUntilPortal = this.getEntity().getPortalCooldown();
			}
			else
			{
				this.ticksInTeleporter++;

				if (this.ticksInTeleporter == 1)
				{
					if (this.getWorld().isRemote && Minecraft.getMinecraft().player.getEntityId() == this.getEntity().getEntityId())
					{
						Minecraft.getMinecraft().player.playSound(SoundsAether.glowstone_portal_trigger, 1.0F, 1.0F);
					}
				}
				else if (!this.getWorld().isRemote && (this.getTicksInTeleporter() >= TELEPORT_DELAY || this.getEntity().isCreative()))
				{
					this.ticksInTeleporter = 0;

					this.teleportToTower();
				}
			}
		}
		else if (this.isTeleportCharging && this.getEntity().getEntityWorld().provider.getDimensionType() == DimensionsAether.NECROMANCER_TOWER)
		{
			if (this.getEntity().timeUntilPortal > 0)
			{
				this.getEntity().timeUntilPortal = this.getEntity().getPortalCooldown();
			}
			else
			{
				this.ticksInTeleporter++;

				if (this.ticksInTeleporter == 1)
				{
					if (this.getWorld().isRemote && Minecraft.getMinecraft().player.getEntityId() == this.getEntity().getEntityId())
					{
						Minecraft.getMinecraft().player.playSound(SoundsAether.glowstone_portal_trigger, 1.0F, 1.0F);
					}
				}
				else if (!this.getWorld().isRemote && (this.getTicksInTeleporter() >= TELEPORT_DELAY || this.getEntity().isCreative()))
				{
					this.ticksInTeleporter = 0;

					this.teleportToAether();
				}
			}
		}
		else
		{
			this.ticksInTeleporter -= 4;
		}

		this.ticksInTeleporter = MathHelper.clamp(this.ticksInTeleporter, 0, TELEPORT_DELAY);
	}

	public void processTeleporting()
	{
		this.isTeleportCharging = true;
	}

	public void teleportToTower()
	{
		final EntityPlayer player = this.getEntity();
		final PlayerAether playerAether = PlayerAether.getPlayer(player);

		final World world = this.getEntity().getEntityWorld();

		final NecromancerTowerInstanceHelper handler = InstancesAether.NECROMANCER_TOWER_HANDLER;

		final PlayerTeleportingModule teleportingModule = playerAether.getModule(PlayerTeleportingModule.class);

		final IPlayerInstances hook = OrbisLib.instances().getPlayer(player);

		if (world.isRemote)
		{
			if (AetherCore.CONFIG.skipIntro)
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
		}
		else
		{
			if (teleportingModule.getAetherPos() != null)
			{
				final EntityPlayerMP playerMP = (EntityPlayerMP) player;
				final BlockPosDimension p = teleportingModule.getAetherPos();

				final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

				if (net.minecraftforge.common.ForgeHooks.onTravelToDimension(playerMP, p.getDim()))
				{
					final Teleporter teleporter = new TeleporterGeneric(server.getWorld(player.dimension));
					final PlayerList playerList = server.getPlayerList();
					playerList.transferPlayerToDimension(playerMP, p.getDim(), teleporter);
					player.timeUntilPortal = player.getPortalCooldown();

					playerMP.connection.setPlayerLocation(p.getX(), p.getY(), p.getZ(), 225, 0);

					PostAetherTravelEvent event = new PostAetherTravelEvent(playerMP);
					MinecraftForge.EVENT_BUS.post(event);
				}
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

					handler.teleportToInst((EntityPlayerMP) player);
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
					handler.teleportToInst((EntityPlayerMP) player);
				}
			}
		}
	}

	public void teleportToAether()
	{
		this.getEntity().timeUntilPortal = this.getEntity().getPortalCooldown();

		if (this.getEntity().world.isRemote && Minecraft.getMinecraft().player.getEntityId() == this.getEntity().getEntityId())
		{
			Minecraft.getMinecraft().player.playSound(SoundsAether.glowstone_portal_travel, 1.0F, 1.0F);
		}

		if (this.getEntity().world instanceof WorldServer)
		{
			final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
			final WorldServer worldServer = server.getWorld(this.getEntity().dimension);

			final EntityPlayer player = this.getEntity();

			final int transferToID = AetherCore.CONFIG.aetherDimID;

			AetherCore.PROXY.teleportEntity(this.getEntity(), worldServer, new TeleporterGeneric(worldServer), transferToID, () -> {

				final PlayerAether playerAether = PlayerAether.getPlayer(player);
				final PlayerTeleportingModule teleportingModule = playerAether.getModule(PlayerTeleportingModule.class);

				if (teleportingModule.getAetherPos() == null)
				{
					final BlockPos pos = new BlockPos(100, 0, 100);

					final BlockPos respawn = IslandHelper.getOutpostPos(player.world, pos);

					teleportingModule
							.setAetherPos(new BlockPosDimension(respawn.getX(), respawn.getY(), respawn.getZ(),
									AetherCore.CONFIG.aetherDimID));
				}

				return teleportingModule.getAetherPos();
			});
		}
	}

	@Override
	public void write(final NBTTagCompound output)
	{
		final NBTFunnel funnel = new NBTFunnel(output);

		funnel.set("nonAetherPos", this.nonAetherPos);
		funnel.set("aetherPos", this.aetherPos);

		output.setBoolean("playedIntro", this.playedIntro);
	}

	@Override
	public void read(final NBTTagCompound input)
	{
		final NBTFunnel funnel = new NBTFunnel(input);

		this.nonAetherPos = funnel.get("nonAetherPos");

		if (this.nonAetherPos != null && this.nonAetherPos.getDim() == DimensionsAether.AETHER.getId())
		{
		    this.nonAetherPos = null;
        }

		this.aetherPos = funnel.get("aetherPos");

		this.playedIntro = input.getBoolean("playedIntro");
	}

	@Override
	public ResourceLocation getIdentifier()
	{
		return AetherCore.getResource("teleport");
	}


}
