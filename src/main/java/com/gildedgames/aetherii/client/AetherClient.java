package com.gildedgames.aetherii.client;

import com.gildedgames.aetherii.AetherII;
import com.gildedgames.aetherii.client.render.TestRenderer;
import com.gildedgames.aetherii.register.ContentRegistry;
import com.gildedgames.aetherii.register.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = AetherII.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AetherClient {
	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModEntities.TEST.get(), TestRenderer::new);
	}

	public static void setup(FMLCommonSetupEvent event) {
		ContentRegistry.getDialogManager().attachReloadListener();
	}
}