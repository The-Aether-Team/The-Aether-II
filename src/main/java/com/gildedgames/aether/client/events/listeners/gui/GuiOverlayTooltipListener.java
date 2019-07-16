package com.gildedgames.aether.client.events.listeners.gui;

import com.gildedgames.aether.api.world.IWorldObjectHoverable;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class GuiOverlayTooltipListener
{
	@SubscribeEvent
	public static void onRenderIngameOverlay(final RenderGameOverlayEvent.Pre event)
	{
		if (event.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS)
		{
			Minecraft minecraft = Minecraft.getInstance();

			if (minecraft.currentScreen == null && minecraft.objectMouseOver != null)
			{
				IWorldObjectHoverable hoverable = null;

				if (minecraft.objectMouseOver.getType() == RayTraceResult.Type.BLOCK)
				{
					BlockPos pos = ((BlockRayTraceResult) minecraft.objectMouseOver).getPos();

					Block block = minecraft.world.getBlockState(pos).getBlock();

					if (!(block instanceof IWorldObjectHoverable))
					{
						return;
					}

					hoverable = (IWorldObjectHoverable) block;
				}
				else if (minecraft.objectMouseOver.getType() == RayTraceResult.Type.ENTITY)
				{
					Entity entity = ((EntityRayTraceResult) minecraft.objectMouseOver).getEntity();

					if (!(entity instanceof IWorldObjectHoverable))
					{
						return;
					}

					hoverable = (IWorldObjectHoverable) entity;
				}

				if (hoverable != null)
				{
					ITextComponent body = hoverable.getHoverText(minecraft.world, minecraft.objectMouseOver);

					if (body == null)
					{
						return;
					}

					String button = minecraft.gameSettings.keyBindUseItem.getLocalizedName();

					String label = String.format("%s[%s]%s %s", TextFormatting.YELLOW, button, TextFormatting.WHITE, body.getFormattedText());

					int width = minecraft.fontRenderer.getStringWidth(label);
					int x = (minecraft.mainWindow.getScaledWidth() / 2) - (width / 2);
					int y = minecraft.mainWindow.getScaledHeight() - 70;

					AbstractGui.fill(x - 3, y - 3, x + width + 3, y + 10, Integer.MIN_VALUE);

					minecraft.fontRenderer.drawString(label, x, y, 0xFFFFFF);
				}
			}
		}
	}

}
