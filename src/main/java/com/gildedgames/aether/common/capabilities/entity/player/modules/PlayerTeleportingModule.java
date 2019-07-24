package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.api.player.IPlayerAetherModule;
import com.gildedgames.aether.client.gui.misc.GuiIntro;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.init.DimensionsAether;
import com.gildedgames.aether.common.util.helpers.IslandHelper;
import com.gildedgames.orbis.lib.util.TeleporterGeneric;
import com.gildedgames.orbis.lib.util.io.NBTFunnel;
import com.gildedgames.orbis.lib.util.mc.BlockPosDimension;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;
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

		if (this.getEntity().getEntityWorld().provider.getDimensionType() == DimensionsAether.AETHER)
		{
			return;
		}

		if (this.isTeleportCharging)
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
						Minecraft.getMinecraft().player.playSound(new SoundEvent(AetherCore.getResource("portal.glowstone.trigger")), 1.0F, 1.0F);
					}
				}
				else if (!this.getWorld().isRemote && (this.getTicksInTeleporter() >= TELEPORT_DELAY || this.getEntity().isCreative()))
				{
					this.ticksInTeleporter = 0;

					this.teleportToAether();
				}
			}
		}

		this.ticksInTeleporter = MathHelper.clamp(this.ticksInTeleporter, 0, TELEPORT_DELAY);
	}

	public void processTeleporting()
	{
		this.isTeleportCharging = true;
	}

	public void teleportToAether()
	{
		this.getEntity().timeUntilPortal = this.getEntity().getPortalCooldown();

		if (this.getEntity().world.isRemote && Minecraft.getMinecraft().player.getEntityId() == this.getEntity().getEntityId())
		{
			Minecraft.getMinecraft().player.playSound(new SoundEvent(AetherCore.getResource("portal.glowstone.travel")), 1.0F, 1.0F);
		}

		if (this.getEntity().world instanceof WorldServer)
		{
			final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
			final WorldServer worldServer = server.getWorld(this.getEntity().dimension);

			final EntityPlayer player = this.getEntity();

			final int transferToID = AetherCore.CONFIG.getAetherDimID();

			AetherCore.PROXY.teleportEntity(this.getEntity(), worldServer, new TeleporterGeneric(worldServer), transferToID, () -> {

				final PlayerAether playerAether = PlayerAether.getPlayer(player);
				final PlayerTeleportingModule teleportingModule = playerAether.getModule(PlayerTeleportingModule.class);

				if (teleportingModule.getAetherPos() == null)
				{
					final BlockPos pos = new BlockPos(100, 0, 100);

					final BlockPos respawn = IslandHelper.getOutpostPos(player.world, pos);

					teleportingModule
							.setAetherPos(new BlockPosDimension(respawn.getX(), respawn.getY(), respawn.getZ(),
									AetherCore.CONFIG.getAetherDimID()));
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
