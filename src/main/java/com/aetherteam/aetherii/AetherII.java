package com.aetherteam.aetherii;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.AetherIIClient;
import com.aetherteam.aetherii.data.AetherData;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(AetherII.MODID)
public class AetherII {
    public static final String MODID = "aether_ii";
    private static final Logger LOGGER = LogUtils.getLogger();

    public AetherII(IEventBus bus, Dist dist) {
        bus.addListener(AetherII::commonSetup);
        bus.addListener(AetherData::dataSetup);

        DeferredRegister<?>[] registers = {
                AetherIIBlocks.BLOCKS,
                AetherIIItems.ITEMS,
                AetherIIEntityTypes.ENTITY_TYPES
        };

        for (DeferredRegister<?> register : registers) {
            register.register(bus);
        }

        AetherII.eventSetup(bus);

        if (dist == Dist.CLIENT) {
            AetherIIClient.clientInit(bus);
        }
    }

    public static void commonSetup(FMLCommonSetupEvent event) {

    }

    public static void eventSetup(IEventBus neoBus) {
        IEventBus bus = NeoForge.EVENT_BUS;

        neoBus.addListener(AetherIIEntityTypes::registerSpawnPlacements);
        neoBus.addListener(AetherIIEntityTypes::registerEntityAttributes);
    }
}
