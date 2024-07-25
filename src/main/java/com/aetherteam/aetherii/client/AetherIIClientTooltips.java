package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.client.renderer.item.tooltip.ClientCharmTooltip;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;

public class AetherIIClientTooltips {
    public static void registerClientTooltipComponents(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(ClientCharmTooltip.CharmTooltip.class, (charmTooltip) -> new ClientCharmTooltip(charmTooltip.base(), charmTooltip.items()));
    }
}
