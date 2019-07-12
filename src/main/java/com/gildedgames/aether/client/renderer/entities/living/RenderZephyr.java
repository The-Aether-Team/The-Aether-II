package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelZephyr;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.monsters.EntityZephyr;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;

public class RenderZephyr extends LivingRenderer<EntityZephyr, ModelZephyr>
{

	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/zephyr/zephyr.png");

	public RenderZephyr(final EntityRendererManager manager)
	{
		super(manager, new ModelZephyr(), 1.0F);
	}

	private float partialTicks;

	@Override
	protected void preRenderCallback(final EntityZephyr entity, final float partialTicks)
	{
		this.partialTicks = partialTicks;
	}

	@Override
	protected void renderModel(EntityZephyr entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
	{
		GlStateManager.pushMatrix();

		GlStateManager.translatef(0, 1.1f, 0);

		/*if (zephyr.getFlightPath() != null)
		{
			Vec3d pos = entity.getPositionVector();

			Point3d cur = zephyr.getPoint(this.partialTicks);

			GlStateManager.pushMatrix();

			GlStateManager.translatef(-pos.x, pos.y, pos.z);
			Point3d last = null, last2 = null;

			if (Minecraft.getInstance().gameSettings.showDebugInfo)
			{
				float scale = 20f / Minecraft.getInstance().player.getDistance(entity);

				for (float t = 0; t <= 1.2f; t += 0.03f)
				{
					Point3d cur2 = MathUtil.getPoints(zephyr.getFlightPath(), t);
					FloatList timeMap = zephyr.getTimeMap();
					FloatList speedMap = zephyr.getSpeedMap();
					int r = 255, g = 0;

					if (speedMap != null && timeMap != null && t < 1)
					{
						for (int i = 0; i < timeMap.size(); i++)
						{
							if ((t >= timeMap.get(i) && i + 1 < timeMap.size() && t <= timeMap.get(i + 1)) || (i + 1 == timeMap.size() && t >= timeMap.get(i)))
							{
								float v = speedMap.get(i) * (1f / 3f);

								Color c = Color.getHSBColor(v, 1, 1);

								r = c.getRed();
								g = c.getGreen();

								break;
							}
						}
					}

					if (last != null)
					{
//						GlUtil.drawLine(last.x, -last.y, -last.z, cur2.x, -cur2.y, -cur2.z, r, g, 0, 255, scale);
					}

					last = cur2;

					if (zephyr.getFutureFlightPath() != null)
					{
						Point3d cur3 = MathUtil.getPoints(zephyr.getFutureFlightPath(), t);

						if (last2 != null)
						{
//							GlUtil.drawLine(last2.x, -last2.y, -last2.z, cur3.x, -cur3.y, -cur3.z, 255 - (int) (t * 255), (int) (255 * t), (int) (t * 122), 255, scale);
						}

						last2 = cur3;
					}
				}
			}

			GlStateManager.popMatrix();

			Point3d v2 = MathUtil.getPoints(zephyr.getFlightPath(), zephyr.getTime() - 0.001f);

			GlStateManager.rotatef(QuaternionUtil.lookAt(new Vec3d(cur.x, -cur.y, -cur.z), new Vec3d(v2.x, -v2.y, -v2.z)));
		}*/

		super.renderModel(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(final EntityZephyr entity)
	{
		return TEXTURE;
	}

}
