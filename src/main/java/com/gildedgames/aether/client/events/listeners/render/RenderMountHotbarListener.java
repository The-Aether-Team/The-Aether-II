package com.gildedgames.aether.client.events.listeners.render;

import com.gildedgames.aether.api.entity.IMount;
import com.gildedgames.aether.api.entity.IMountProcessor;
import com.gildedgames.aether.common.entities.mounts.FlyingMount;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class RenderMountHotbarListener
{
	private static final Minecraft mc = Minecraft.getInstance();

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void onRenderGui(final RenderGameOverlayEvent event)
	{
		if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR)
		{
			if (mc.player.isPassenger())
			{
				if (mc.player.getRidingEntity() instanceof IMount)
				{
					final IMount mount = (IMount) mc.player.getRidingEntity();
					final IMountProcessor processor = mount.getMountProcessor();

					if (processor instanceof FlyingMount)
					{
						final FlyingMount flyingMount = (FlyingMount) processor;

						mc.ingameGUI.drawCenteredString(mc.fontRenderer, String.valueOf((int) (flyingMount.getData().getRemainingAirborneTime())),
								mc.mainWindow.getScaledWidth() / 2, mc.mainWindow.getScaledHeight() - 30, 0xFFFFFF);
					}
				}
			}
		}
	}
}
