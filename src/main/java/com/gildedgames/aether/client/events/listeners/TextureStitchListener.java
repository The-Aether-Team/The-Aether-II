package com.gildedgames.aether.client.events.listeners;

import com.gildedgames.aether.common.containers.slots.*;
import com.gildedgames.aether.common.containers.slots.icestone_cooler.SlotCoolingItem;
import com.gildedgames.aether.common.containers.slots.incubator.SlotMoaEgg;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class TextureStitchListener
{
	@SubscribeEvent
	public static void onTextureStitchPre(final TextureStitchEvent.Pre event)
	{
		SlotEquipment.registerIcons(event);
		SlotAmbrosium.registerIcons(event);
		SlotMoaEgg.registerIcons(event);
		SlotFlintAndSteel.registerIcons(event);
		SlotCoolingItem.registerIcons(event);
	}

}
