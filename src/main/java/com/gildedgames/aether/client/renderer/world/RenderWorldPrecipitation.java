package com.gildedgames.aether.client.renderer.world;

import com.gildedgames.aether.api.world.islands.IIslandDataPartial;
import com.gildedgames.aether.api.world.islands.precipitation.PrecipitationStrength;
import com.gildedgames.aether.api.world.islands.precipitation.PrecipitationType;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.util.helpers.IslandHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.IRenderHandler;

import java.util.HashMap;
import java.util.Random;

public class RenderWorldPrecipitation extends IRenderHandler
{
	private HashMap<String, ResourceLocation> textureCache = new HashMap<>();

	private int renderTicks;

	private final float[] rainXCoords = new float[1024];

	private final float[] rainYCoords = new float[1024];

	private final Random random = new Random();

	public RenderWorldPrecipitation()
	{
		for (int i = 0; i < 32; ++i)
		{
			for (int j = 0; j < 32; ++j)
			{
				float f = (float) (j - 16);
				float f1 = (float) (i - 16);
				float f2 = MathHelper.sqrt(f * f + f1 * f1);
				this.rainXCoords[i << 5 | j] = -f1 / f2;
				this.rainYCoords[i << 5 | j] = f / f2;
			}
		}
	}

	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc)
	{
		this.updateLightmap(mc, partialTicks);

		this.renderTicks++;

		mc.entityRenderer.enableLightmap();

		Entity entity = mc.getRenderViewEntity();

		int entityX = MathHelper.floor(entity.posX);
		int entityY = MathHelper.floor(entity.posY);
		int entityZ = MathHelper.floor(entity.posZ);

		Tessellator tessellator = Tessellator.getInstance();

		BufferBuilder buffer = tessellator.getBuffer();

		GlStateManager.disableCull();

		GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);

		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
				GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.alphaFunc(516, 0.1F);

		double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
		double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
		double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;

		int l = MathHelper.floor(y);

		int radius = 5;

		if (mc.gameSettings.fancyGraphics)
		{
			radius = 10;
		}

		int continuous = -1;

		float ticks = (float) this.renderTicks + partialTicks;

		buffer.setTranslation(-x, -y, -z);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

		for (int z2 = entityZ - radius; z2 <= entityZ + radius; ++z2)
		{
			for (int x2 = entityX - radius; x2 <= entityX + radius; ++x2)
			{
				int index = (z2 - entityZ + 16) * 32 + x2 - entityX + 16;

				double rainX = (double) this.rainXCoords[index] * 0.5D;
				double rainY = (double) this.rainYCoords[index] * 0.5D;

				pos.setPos(x2, 0, z2);

				IIslandDataPartial island = IslandHelper.getPartial(world, pos.getX() >> 4, pos.getZ() >> 4);

				if (island == null)
				{
					continue;
				}

				double intensity = island.getPrecipitation().getStrength() == PrecipitationStrength.STORM ? 128.0f : (island.getPrecipitation().getStrength() == PrecipitationStrength.HEAVY ? 180.0f : 256.0f);

				float opacity = Math.min(1.0f, island.getPrecipitation().getStrength(partialTicks));

				if (opacity > 0 && island.getPrecipitation().getType() != PrecipitationType.NONE)
				{
					int height = world.getPrecipitationHeight(pos).getY();

					int yMin = entityY - radius;
					int yMax = entityY + radius;

					if (yMin < height)
					{
						yMin = height;
					}

					if (yMax < height)
					{
						yMax = height;
					}

					int y2 = height;

					if (height < l)
					{
						y2 = l;
					}

					if (yMin != yMax)
					{
						this.random.setSeed((long) (x2 * x2 * 3121 + x2 * 45238971 ^ z2 * z2 * 418711 + z2 * 13761));

						pos.setPos(x2, yMin, z2);

						if (island.getPrecipitation().getType() == PrecipitationType.RAIN)
						{
							if (continuous != 0)
							{
								if (continuous >= 0)
								{
									tessellator.draw();
								}

								continuous = 0;

								String textureKey = AetherCore.getResourcePath("textures/environment/weather/" + island.getPrecipitation().getType()
										+ "_" + island.getPrecipitation().getStrength().getResourceId() + ".png");

								ResourceLocation texture = this.textureCache.computeIfAbsent(textureKey, (key) -> new ResourceLocation(textureKey));
								mc.getTextureManager().bindTexture(texture);

								buffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
							}

							double textureVOffset = -((double) (this.renderTicks + x2 * x2 * 3121 + x2 * 45238971 + z2 * z2 * 418711 + z2 * 13761 & 31)
									+ (double) partialTicks) / intensity * (3.0D + this.random.nextDouble());

							double d6 = (double) ((float) x2 + 0.5F) - entity.posX;
							double d7 = (double) ((float) z2 + 0.5F) - entity.posZ;

							float f3 = MathHelper.sqrt(d6 * d6 + d7 * d7) / (float) radius;
							float f4 = ((1.0F - f3 * f3) * 0.5F + 0.5F) * opacity;

							pos.setPos(x2, y2, z2);

							int light = world.getCombinedLight(pos, 0);
							int lightU = light >> 16 & 65535;
							int lightV = light & 65535;

							int color = island.getBiome().getWaterColor();

							float red = ((color & 0xFF0000) >> 16) / 255.0f;
							float blue = ((color & 0xFF00) >> 8) / 255.0f;
							float green = (color & 0xFF) / 255.0f;

							buffer.pos((double) x2 - rainX + 0.5D, (double) yMax, (double) z2 - rainY + 0.5D).tex(0.0D, (double) yMin * 0.25D + textureVOffset)
									.color(red, blue, green, f4).lightmap(lightU, lightV).endVertex();
							buffer.pos((double) x2 + rainX + 0.5D, (double) yMax, (double) z2 + rainY + 0.5D).tex(1.0D, (double) yMin * 0.25D + textureVOffset)
									.color(red, blue, green, f4).lightmap(lightU, lightV).endVertex();
							buffer.pos((double) x2 + rainX + 0.5D, (double) yMin, (double) z2 + rainY + 0.5D).tex(1.0D, (double) yMax * 0.25D + textureVOffset)
									.color(red, blue, green, f4).lightmap(lightU, lightV).endVertex();
							buffer.pos((double) x2 - rainX + 0.5D, (double) yMin, (double) z2 - rainY + 0.5D).tex(0.0D, (double) yMax * 0.25D + textureVOffset)
									.color(red, blue, green, f4).lightmap(lightU, lightV).endVertex();
						}
						else if (island.getPrecipitation().getType() == PrecipitationType.SNOW)
						{
							if (continuous != 1)
							{
								if (continuous >= 0)
								{
									tessellator.draw();
								}

								continuous = 1;

								String textureKey = AetherCore.getResourcePath("textures/environment/weather/" + island.getPrecipitation().getType()
										+ "_" + island.getPrecipitation().getStrength().getResourceId() + ".png");

								ResourceLocation texture = this.textureCache.computeIfAbsent(textureKey, (key) -> new ResourceLocation(textureKey));
								mc.getTextureManager().bindTexture(texture);

								buffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
							}

							double d8 = (double) (-((float) (this.renderTicks & 511) + partialTicks) / 512.0F);

							double uOffset = this.random.nextDouble() + (ticks * this.random.nextGaussian() * 0.01D);
							double vOffset = this.random.nextDouble() + (ticks * this.random.nextGaussian() * 0.001D);

							double d11 = (double) ((float) x2 + 0.5F) - entity.posX;
							double d12 = (double) ((float) z2 + 0.5F) - entity.posZ;

							float f6 = MathHelper.sqrt(d11 * d11 + d12 * d12) / (float) radius;
							float alpha = ((1.0F - f6 * f6) * 0.3F + 0.5F) * opacity;

							pos.setPos(x2, y2, z2);

							int light = (world.getCombinedLight(pos, 0) * 3 + 15728880) / 4;
							int lightU = light >> 16 & 65535;
							int lightV = light & 65535;

							buffer.pos((double) x2 - rainX + 0.5D, (double) yMax, (double) z2 - rainY + 0.5D).tex(0.0D + uOffset, (double) yMin * 0.25D + d8 + vOffset)
									.color(1.0F, 1.0F, 1.0F, alpha).lightmap(lightU, lightV).endVertex();
							buffer.pos((double) x2 + rainX + 0.5D, (double) yMax, (double) z2 + rainY + 0.5D).tex(1.0D + uOffset, (double) yMin * 0.25D + d8 + vOffset)
									.color(1.0F, 1.0F, 1.0F, alpha).lightmap(lightU, lightV).endVertex();
							buffer.pos((double) x2 + rainX + 0.5D, (double) yMin, (double) z2 + rainY + 0.5D).tex(1.0D + uOffset, (double) yMax * 0.25D + d8 + vOffset)
									.color(1.0F, 1.0F, 1.0F, alpha).lightmap(lightU, lightV).endVertex();
							buffer.pos((double) x2 - rainX + 0.5D, (double) yMin, (double) z2 - rainY + 0.5D).tex(0.0D + uOffset, (double) yMax * 0.25D + d8 + vOffset)
									.color(1.0F, 1.0F, 1.0F, alpha).lightmap(lightU, lightV).endVertex();
						}
					}
				}
			}
		}

		if (continuous >= 0)
		{
			tessellator.draw();
		}

		buffer.setTranslation(0.0D, 0.0D, 0.0D);
		GlStateManager.enableCull();
		GlStateManager.disableBlend();
		GlStateManager.alphaFunc(516, 0.1F);
		mc.entityRenderer.disableLightmap();
	}

	private void updateLightmap(Minecraft mc, float partialTicks)
	{
		BlockPos pos = mc.player.getPosition();

		IIslandDataPartial island = IslandHelper.getPartial(mc.world, pos.getX() >> 4, pos.getZ() >> 4);

		if (island == null)
		{
			return;
		}

		float strength = 1.0f - (island.getPrecipitation().getSkyDarkness() * island.getPrecipitation().getStrength(partialTicks));

		float f = mc.world.getSunBrightness(partialTicks);
		f = f * strength;

		EntityRendererHelper.updateLightmap(mc, partialTicks, f);
	}
}
