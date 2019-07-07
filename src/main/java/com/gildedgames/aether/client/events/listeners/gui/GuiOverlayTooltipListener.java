package com.gildedgames.aether.client.events.listeners.gui;

import com.gildedgames.aether.api.world.IWorldObjectHoverable;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class GuiOverlayTooltipListener
{
	@SubscribeEvent
	public static void onRenderIngameOverlay(final RenderGameOverlayEvent.Pre event)
	{
		if (event.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS)
		{
			Minecraft minecraft = Minecraft.getMinecraft();

			if (minecraft.currentScreen == null && minecraft.objectMouseOver != null)
			{
				IWorldObjectHoverable hoverable = null;

				if (minecraft.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK)
				{
					BlockPos pos = minecraft.objectMouseOver.getBlockPos();

					Block block = minecraft.world.getBlockState(pos).getBlock();

					if (!(block instanceof IWorldObjectHoverable))
					{
						return;
					}

					hoverable = (IWorldObjectHoverable) block;
				}
				else if (minecraft.objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY)
				{
					Entity entity = minecraft.objectMouseOver.entityHit;

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

					String button = minecraft.gameSettings.keyBindUseItem.getDisplayName();

					String label = String.format("%s[%s]%s %s", TextFormatting.YELLOW, button, TextFormatting.WHITE, body.getFormattedText());

					ScaledResolution resolution = new ScaledResolution(minecraft);

					int width = minecraft.fontRenderer.getStringWidth(label);
					int x = (resolution.getScaledWidth() / 2) - (width / 2);
					int y = resolution.getScaledHeight() - 70;

					Gui.drawRect(x - 3, y - 3, x + width + 3, y + 10, Integer.MIN_VALUE);

					minecraft.fontRenderer.drawString(label, x, y, 0xFFFFFF);
				}
			}
		}
	}

}
