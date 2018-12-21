package com.gildedgames.aether.common.entities.util.resistance;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityHandler
{
	public static final ResourceLocation RESITANCES = new ResourceLocation(AetherCore.MOD_ID, "resistances");

	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent event)
	{
		event.addCapability(RESITANCES, new EntityResistanceCapabilities());
	}
}
