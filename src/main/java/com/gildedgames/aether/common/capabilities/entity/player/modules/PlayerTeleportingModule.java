package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.client.ClientEventHandler;
import com.gildedgames.aether.client.gui.misc.GuiIntro;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import com.gildedgames.aether.common.util.helpers.IslandHelper;
import com.gildedgames.orbis.lib.util.TeleporterGeneric;
import com.gildedgames.orbis.lib.util.io.NBTFunnel;
import com.gildedgames.orbis.lib.util.mc.BlockPosDimension;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PlayerTeleportingModule extends PlayerAetherModule
{

	private float prevTimeInPortal, timeInPortal, timeCharged;

	private boolean teleported, teleporting;

	private BlockPosDimension nonAetherPos, aetherPos;

	private boolean playedIntro;

	private float lastPercent;

	private double timeStartedFade = -1;

	public PlayerTeleportingModule(final PlayerAether playerAether)
	{
		super(playerAether);
	}

	public float getPrevTimeInPortal()
	{
		return this.prevTimeInPortal;
	}

	public float getTimeInPortal()
	{
		return this.timeInPortal;
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

	private double getSecondsSinceStart()
	{
		return (System.currentTimeMillis() - this.timeStartedFade) / 1000.0D;
	}

	@OnlyIn(Dist.CLIENT)
	private void onUpdateClient()
	{
		if (this.getWorld().provider.getDimensionType() == DimensionsAether.NECROMANCER_TOWER)
		{
			if (!this.playedIntro && Minecraft.getMinecraft().currentScreen == null)
			{
				ClientEventHandler.setDrawBlackScreen(false);

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
		this.teleporting = false;
	}

	@Override
	public void tickEnd(TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public void onUpdate()
	{
		if (this.getWorld().isRemote)
		{
			this.onUpdateClient();
		}

		this.prevTimeInPortal = this.timeInPortal;

		if (this.teleporting)
		{
			if (this.getEntity().world.isRemote && this.timeCharged == 0 && !this.teleported)
			{
				if (Minecraft.getMinecraft().player.getEntityId() == this.getEntity().getEntityId())
				{
					Minecraft.getMinecraft().player.playSound(SoundsAether.glowstone_portal_trigger, 1.0F, 1.0F);
				}

				this.timeCharged = 70.0F;
			}

			this.timeInPortal += 0.0125F;

			if (this.timeInPortal >= 1.0F)
			{
				this.timeInPortal = 1.0F;
			}

			if (!this.teleported && (this.timeInPortal >= 0.5F))
			{
				if (this.timeStartedFade == -1)
				{
					this.timeStartedFade = System.currentTimeMillis();
				}

				if (AetherCore.isClient())
				{
					if (!ClientEventHandler.isFadingIn())
					{
						ClientEventHandler.drawBlackFadeIn(2.0D, () ->
						{
							ClientEventHandler.setChangeFromBlackToLoad(true);
							ClientEventHandler.setDrawBlackScreen(true);
						});
					}
				}

				this.getEntity().setPositionAndUpdate(this.getEntity().prevPosX, this.getEntity().prevPosY, this.getEntity().prevPosZ);

				this.getEntity().motionX = this.getEntity().motionY = this.getEntity().motionZ = 0;

				if (this.getSecondsSinceStart() >= 2.0D)
				{
					this.timeStartedFade = -1;
					this.teleportToAether();
				}
			}
		}
		else if (this.getEntity().isPotionActive(MobEffects.NAUSEA)
				&& this.getEntity().getActivePotionEffect(MobEffects.NAUSEA).getDuration() > 60)
		{
			this.timeInPortal += 0.006666667F;

			if (this.timeInPortal > 1.0F)
			{
				this.timeInPortal = 1.0F;
			}
		}
		else
		{
			if (this.timeInPortal > 0.0F)
			{
				this.timeInPortal -= 0.05F;
			}

			if (this.timeInPortal < 0.0F)
			{
				this.timeInPortal = 0.0F;
			}

			this.teleported = false;
		}

		if (this.timeCharged > 0)
		{
			--this.timeCharged;
		}

		if (this.getEntity().timeUntilPortal > 0)
		{
			--this.getEntity().timeUntilPortal;
		}
	}

	public void processTeleporting()
	{
		this.teleporting = true;
	}

	public void teleportToAether()
	{
		this.getEntity().timeUntilPortal = this.getEntity().getPortalCooldown();
		this.teleported = true;

		if (this.getEntity().world.isRemote && Minecraft.getMinecraft().player.getEntityId() == this.getEntity().getEntityId())
		{
			Minecraft.getMinecraft().player.playSound(SoundsAether.glowstone_portal_travel, 1.0F, 1.0F);
		}

		if (this.getEntity().world instanceof WorldServer)
		{
			final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
			final WorldServer worldServer = server.getWorld(this.getEntity().dimension);

			final EntityPlayer player = this.getEntity();

			final int transferToID = AetherCore.CONFIG.getAetherDimID();

			AetherCore.PROXY.teleportEntity(this.getEntity(), worldServer, new TeleporterGeneric(worldServer), transferToID, () -> {
				final PlayerAether playerAether = PlayerAether.getPlayer(player);

				if (playerAether.getTeleportingModule().getAetherPos() == null)
				{
					final BlockPos pos = new BlockPos(100, 0, 100);

					final BlockPos respawn = IslandHelper.getOutpostPos(player.world, pos);

					playerAether.getTeleportingModule()
							.setAetherPos(new BlockPosDimension(respawn.getX(), respawn.getY(), respawn.getZ(),
									AetherCore.CONFIG.getAetherDimID()));
				}

				return playerAether.getTeleportingModule().getAetherPos();
			});
		}

		this.timeInPortal = 0.0F;
	}

	@Override
	public void write(final NBTTagCompound output)
	{
		final NBTFunnel funnel = new NBTFunnel(output);

		final NBTTagCompound root = new NBTTagCompound();

		output.setTag("Teleport", root);
		root.setFloat("timeCharged", this.timeCharged);

		funnel.set("nonAetherPos", this.nonAetherPos);
		funnel.set("aetherPos", this.aetherPos);

		output.setBoolean("playedIntro", this.playedIntro);
	}

	@Override
	public void read(final NBTTagCompound input)
	{
		final NBTFunnel funnel = new NBTFunnel(input);

		final NBTTagCompound root = input.getCompoundTag("Teleport");
		this.timeCharged = root.getFloat("timeCharged");

		this.nonAetherPos = funnel.get("nonAetherPos");
		this.aetherPos = funnel.get("aetherPos");

		this.playedIntro = input.getBoolean("playedIntro");
	}
}
