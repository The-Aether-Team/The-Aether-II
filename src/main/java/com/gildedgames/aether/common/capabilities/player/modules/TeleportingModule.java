package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.CommonEvents;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import net.minecraft.client.Minecraft;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;

public class TeleportingModule extends PlayerAetherModule
{

	private float prevTimeInPortal, timeInPortal, timeCharged;

	private boolean teleported, teleporting;

	public TeleportingModule(PlayerAether playerAether)
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
	public void onUpdate()
	{
		this.prevTimeInPortal = this.timeInPortal;

		if (this.teleporting)
		{
			if (this.getEntity().worldObj.isRemote && this.timeCharged == 0 && !this.teleported)
			{
				if (Minecraft.getMinecraft().thePlayer.getEntityId() == this.getEntity().getEntityId())
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

			if (!this.teleported && (this.getEntity().capabilities.isCreativeMode || this.timeInPortal == 1.0F))
			{
				this.teleportToAether();
			}
		}
		else if (this.getEntity().isPotionActive(MobEffects.NAUSEA) && this.getEntity().getActivePotionEffect(MobEffects.NAUSEA).getDuration() > 60)
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

		this.teleporting = false;
	}

	public void processTeleporting()
	{
		this.teleporting = true;
	}

	private void teleportToAether()
	{
		this.getEntity().timeUntilPortal = this.getEntity().getPortalCooldown();
		this.teleported = true;

		if (this.getEntity().worldObj.isRemote && Minecraft.getMinecraft().thePlayer.getEntityId() == this.getEntity().getEntityId())
		{
			Minecraft.getMinecraft().thePlayer.playSound(SoundsAether.glowstone_portal_travel, 1.0F, 1.0F);
		}

		if (this.getEntity().worldObj instanceof WorldServer)
		{
			WorldServer worldServer = (WorldServer)this.getEntity().worldObj;

			final int transferToID = this.getEntity().worldObj.provider.getDimensionType() == DimensionsAether.AETHER ? 0 : AetherCore.CONFIG.getAetherDimID();

			CommonEvents.teleportEntity(this.getEntity(), worldServer, AetherCore.TELEPORTER, transferToID);
		}

		this.timeInPortal = 0.0F;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		NBTTagCompound root = new NBTTagCompound();
		output.setTag("Teleport", root);

		root.setFloat("timeCharged", this.timeCharged);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		NBTTagCompound root = input.getCompoundTag("Teleport");

		this.timeCharged = root.getFloat("timeCharged");
	}
}
