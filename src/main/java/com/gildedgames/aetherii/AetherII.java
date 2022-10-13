package com.gildedgames.aetherii;

import com.gildedgames.aetherii.client.AetherClient;
import com.gildedgames.aetherii.message.OpenDialogMessage;
import com.gildedgames.aetherii.register.ContentRegistry;
import com.gildedgames.aetherii.register.ModEntities;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;

@Mod(AetherII.MODID)
public class AetherII {
    public static final String MODID = "aether_ii";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String NETWORK_PROTOCOL = "2";

    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MODID, "net"))
            .networkProtocolVersion(() -> NETWORK_PROTOCOL)
            .clientAcceptedVersions(NETWORK_PROTOCOL::equals)
            .serverAcceptedVersions(NETWORK_PROTOCOL::equals)
            .simpleChannel();

    public AetherII() {
        this.setupMessages();
        ModEntities.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(AetherClient::setup));
    }

    private void setupMessages() {
        CHANNEL.messageBuilder(OpenDialogMessage.class, 0)
                .encoder(OpenDialogMessage::serialize).decoder(OpenDialogMessage::deserialize)
                .consumerMainThread(OpenDialogMessage::handle);
    }

    private void processIMC(final InterModProcessEvent event) {
        ContentRegistry.preInit();
    }
}
