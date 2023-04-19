package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = AetherII.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AetherIIClient {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {

    }
}
