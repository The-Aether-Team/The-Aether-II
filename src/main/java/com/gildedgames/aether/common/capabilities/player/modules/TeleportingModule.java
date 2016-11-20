package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.CommonEvents;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import net.minecraft.client.Minecraft;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingEvent;

public class TeleportingModule extends PlayerAetherModule
{

	private float prevTimeInPortal, timeInPortal, timeCharged;

	private boolean teleported, teleporting;

	public TeleportingModule(PlayerAetherImpl playerAether)
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

	@Override
	public void onUpdate(LivingEvent.LivingUpdateEvent event)
	{
		this.prevTimeInPortal = this.timeInPortal;

		if (this.teleporting)
		{
			if (this.getPlayer().worldObj.isRemote && this.timeCharged == 0 && !this.teleported)
			{
				if (Minecraft.getMinecraft().thePlayer.getEntityId() == this.getPlayer().getEntityId())
				{
					Minecraft.getMinecraft().thePlayer.playSound(SoundsAether.glowstone_portal_trigger, 1.0F, 1.0F);
				}

				this.timeCharged = 70.0F;
			}

			this.timeInPortal += 0.0125F;

			if (this.timeInPortal >= 1.0F)
			{
				this.timeInPortal = 1.0F;
			}

			if (!this.teleported && (this.getPlayer().capabilities.isCreativeMode || this.timeInPortal == 1.0F))
			{
				this.teleportToAether();
			}
		}
		else if (this.getPlayer().isPotionActive(MobEffects.NAUSEA) && this.getPlayer().getActivePotionEffect(MobEffects.NAUSEA).getDuration() > 60)
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

		if (this.getPlayer().timeUntilPortal > 0)
		{
			--this.getPlayer().timeUntilPortal;
		}

		this.teleporting = false;
	}

	public void processTeleporting()
	{
		this.teleporting = true;
	}

	private void teleportToAether()
	{
		this.getPlayer().timeUntilPortal = this.getPlayer().getPortalCooldown();
		this.teleported = true;

		if (this.getPlayer().worldObj.isRemote && Minecraft.getMinecraft().thePlayer.getEntityId() == this.getPlayer().getEntityId())
		{
			Minecraft.getMinecraft().thePlayer.playSound(SoundsAether.glowstone_portal_travel, 1.0F, 1.0F);
		}

		if (this.getPlayer().worldObj instanceof WorldServer)
		{
			WorldServer worldServer = (WorldServer)this.getPlayer().worldObj;

			final int transferToID = this.getPlayer().worldObj.provider.getDimensionType() == DimensionsAether.AETHER ? 0 : AetherCore.CONFIG.getAetherDimID();

			CommonEvents.teleportEntity(this.getPlayer(), worldServer, AetherCore.TELEPORTER, transferToID);
		}

		this.timeInPortal = 0.0F;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		output.setFloat("timeCharged", this.timeCharged);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		this.timeCharged = input.getFloat("timeCharged");
	}
}
