package com.gildedgames.aether.client.renderer.world;

import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.api.world.islands.precipitation.IPrecipitationManager;
import com.gildedgames.aether.api.world.islands.precipitation.PrecipitationStrength;
import com.gildedgames.aether.common.AetherCore;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.IRenderHandler;

import javax.vecmath.Vector2f;
import java.util.HashMap;
import java.util.Random;

public class RenderWorldPrecipitation extends IRenderHandler
{
	private final HashMap<String, ResourceLocation> textureCache = new HashMap<>();

	private int renderTicks;

	private int renderTicksSinceSound;

	private final float[] rainXCoords = new float[1024];

	private final float[] rainYCoords = new float[1024];

	private final Random random = new Random();

	private float skyDarkness, lastSkyDarkness;

	public RenderWorldPrecipitation()
	{
		for (int x = 0; x < 32; ++x)
		{
			for (int y = 0; y < 32; ++y)
			{
				float y2 = (float) (y - 16);
				float x2 = (float) (x - 16);

				float f2 = MathHelper.sqrt(y2 * y2 + x2 * x2);

				this.rainXCoords[x << 5 | y] = -x2 / f2;
				this.rainYCoords[x << 5 | y] = y2 / f2;
			}
		}
	}

	// Called every 20th of a second
	public void tick()
	{
		if (!Minecraft.getInstance().isGamePaused())
		{
			this.renderTicks++;
			this.renderTicksSinceSound++;
		}

	}

	@Override
	public void render(float partialTicks, ClientWorld world, Minecraft mc)
	{
		this.playSounds(mc);

		mc.entityRenderer.enableLightmap();

		Entity entity = mc.getRenderViewEntity();

		int entityX = MathHelper.floor(entity.posX);
		int entityY = MathHelper.floor(entity.posY);
		int entityZ = MathHelper.floor(entity.posZ);

		Tessellator tessellator = Tessellator.getInstance();

		BufferBuilder buffer = tessellator.getBuffer();

		GlStateManager.disableCull();

		GlStateManager.normal3f(0.0F, 1.0F, 0.0F);

		GlStateManager.enableBlend();
		GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
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

		buffer.setTranslation(-x, -y, -z);

		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

		IPrecipitationManager precipitation = world.getCapability(CapabilitiesAether.PRECIPITATION_MANAGER, null);

		Vector2f velocity = precipitation.getWindVector();

		for (int z2 = entityZ - radius; z2 <= entityZ + radius; ++z2)
		{
			for (int x2 = entityX - radius; x2 <= entityX + radius; ++x2)
			{
				int index = (z2 - entityZ + 16) * 32 + x2 - entityX + 16;

				double rainX = (double) this.rainXCoords[index] * 0.5D;
				double rainY = (double) this.rainYCoords[index] * 0.5D;

				pos.setPos(x2, 0, z2);

				float opacity = world.getRainStrength(partialTicks);

				if (opacity > 0 && world.isRaining())
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

						Biome biome = world.getBiome(pos);

						if (biome.isSnowyBiome())
						{
							float intensity = precipitation.getStrength() == PrecipitationStrength.STORM ?
									2.5f :
									(precipitation.getStrength() == PrecipitationStrength.HEAVY ? 1.4f : 0.6f);

							if (continuous != 1)
							{
								if (continuous >= 0)
								{
									tessellator.draw();
								}

								continuous = 1;

								String textureKey = AetherCore.getResourcePath("textures/environment/weather/" + "snow"
										+ "_" + precipitation.getStrength().getResourceId() + ".png");

								ResourceLocation texture = this.textureCache.computeIfAbsent(textureKey, (key) -> new ResourceLocation(textureKey));
								mc.getTextureManager().bindTexture(texture);

								buffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
							}

							float ticks = (this.renderTicks + partialTicks) * intensity;

							double d8 = -(this.renderTicks + partialTicks) * 0.001D * intensity;

							double uOffset = this.random.nextDouble() + -((ticks + partialTicks) * this.random.nextGaussian() * 0.01D);
							double vOffset = this.random.nextDouble() + -((ticks + partialTicks) * this.random.nextGaussian() * 0.001D);

							double d11 = (double) ((float) x2 + 0.5F) - entity.posX;
							double d12 = (double) ((float) z2 + 0.5F) - entity.posZ;

							float f6 = MathHelper.sqrt(d11 * d11 + d12 * d12) / (float) radius;
							float alpha = ((1.0F - f6 * f6) * 0.3F + 0.5F) * opacity;

							pos.setPos(x2, y2, z2);

							int light = (world.getCombinedLight(pos, 0) * 3 + 15728880) / 4;
							int lightU = light >> 16 & 65535;
							int lightV = light & 65535;

							buffer.pos((double) x2 - rainX + 0.5D, (double) yMax, (double) z2 - rainY + 0.5D)
									.tex(0.0D + uOffset, (double) yMin * 0.25D + d8 + vOffset)
									.color(1.0F, 1.0F, 1.0F, alpha).lightmap(lightU, lightV).endVertex();
							buffer.pos((double) x2 + rainX + 0.5D, (double) yMax, (double) z2 + rainY + 0.5D)
									.tex(1.0D + uOffset, (double) yMin * 0.25D + d8 + vOffset)
									.color(1.0F, 1.0F, 1.0F, alpha).lightmap(lightU, lightV).endVertex();
							buffer.pos((double) x2 + rainX + 0.5D, (double) yMin, (double) z2 + rainY + 0.5D)
									.tex(1.0D + uOffset, (double) yMax * 0.25D + d8 + vOffset)
									.color(1.0F, 1.0F, 1.0F, alpha).lightmap(lightU, lightV).endVertex();
							buffer.pos((double) x2 - rainX + 0.5D, (double) yMin, (double) z2 - rainY + 0.5D)
									.tex(0.0D + uOffset, (double) yMax * 0.25D + d8 + vOffset)
									.color(1.0F, 1.0F, 1.0F, alpha).lightmap(lightU, lightV).endVertex();
						}
						else
						{
							float intensity = precipitation.getStrength() == PrecipitationStrength.STORM ?
									48.0f :
									(precipitation.getStrength() == PrecipitationStrength.HEAVY ? 64.0f : 128.0f);

							if (continuous != 0)
							{
								if (continuous >= 0)
								{
									tessellator.draw();
								}

								continuous = 0;

								String textureKey = AetherCore.getResourcePath("textures/environment/weather/" + "rain"
										+ "_" + precipitation.getStrength().getResourceId() + ".png");

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

							int color = biome.getWaterColor();

							float red = ((color & 0xFF0000) >> 16) / 255.0f;
							float blue = ((color & 0xFF00) >> 8) / 255.0f;
							float green = (color & 0xFF) / 255.0f;

							buffer.pos((double) x2 - rainX + 0.5D, (double) yMax, (double) z2 - rainY + 0.5D)
									.tex(0.0D, (double) yMin * 0.25D + textureVOffset)
									.color(red, blue, green, f4)
									.lightmap(lightU, lightV).endVertex();

							buffer.pos((double) x2 + rainX + 0.5D, (double) yMax, (double) z2 + rainY + 0.5D)
									.tex(1.0D, (double) yMin * 0.25D + textureVOffset)
									.color(red, blue, green, f4)
									.lightmap(lightU, lightV).endVertex();

							buffer.pos((double) x2 + rainX + 0.5D + velocity.x, (double) yMin, (double) z2 + rainY + 0.5D + velocity.y)
									.tex(1.0D, (double) yMax * 0.25D + textureVOffset)
									.color(red, blue, green, f4)
									.lightmap(lightU, lightV).endVertex();

							buffer.pos((double) x2 - rainX + 0.5D + velocity.x, (double) yMin, (double) z2 - rainY + 0.5D + velocity.y)
									.tex(0.0D, (double) yMax * 0.25D + textureVOffset)
									.color(red, blue, green, f4)
									.lightmap(lightU, lightV).endVertex();
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

	private void playSounds(Minecraft mc)
	{
		if (mc.isGamePaused())
		{
			return;
		}

		BlockPos pos = mc.player.getPosition();

		IPrecipitationManager precipitation = mc.player.world.getCapability(CapabilitiesAether.PRECIPITATION_MANAGER, null);

		if (!mc.world.isRaining() || mc.world.getRainStrength(mc.getRenderPartialTicks()) <= 0.3f)
		{
			return;
		}

		int radius = 25;
		int frequency = 15;

		BlockPos.MutableBlockPos searchPos = new BlockPos.MutableBlockPos();

		if (this.renderTicksSinceSound >= frequency)
		{
			int x = pos.getX() + mc.world.rand.nextInt(radius) - (radius / 2) + (int) (mc.player.motionX * radius / 2.0);
			int z = pos.getZ() + mc.world.rand.nextInt(radius) - (radius / 2) + (int) (mc.player.motionZ * radius / 2.0);

			int y = mc.world.getTopSolidOrLiquidBlock(new BlockPos(x, 255, z)).getY() + 1;

			if (mc.world.getLightFor(LightType.SKY, new BlockPos(x, y, z)) >= 15)
			{
				SoundEvent event = null;
				float volume = 1.0f;

				searchPos.setPos(x, y, z);

				if (mc.world.getBiome(searchPos).getTempCategory() == Biome.TempCategory.COLD)
				{
					if (precipitation.getStrength() == PrecipitationStrength.STORM)
					{
						event = new SoundEvent(AetherCore.getResource("environment.snow.wind"));
						volume = 0.6f;
					}
				}
				else
				{
					if (precipitation.getStrength() == PrecipitationStrength.LIGHT)
					{
						event = new SoundEvent(AetherCore.getResource("environment.rain.light"));
						volume = 0.6f;
					}
					else
					{
						event = new SoundEvent(AetherCore.getResource("environment.rain.heavy"));
						volume = 0.7f;
					}
				}

				this.renderTicksSinceSound = 0;

				if (event != null)
				{
					mc.world.playSound(x, y, z, event, SoundCategory.WEATHER, volume, 1.0f + (mc.world.rand.nextFloat() * 0.1f), true);
				}
			}
		}
	}

}
