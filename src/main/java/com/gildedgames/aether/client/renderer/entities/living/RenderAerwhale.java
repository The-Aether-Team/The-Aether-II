package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.client.models.entities.living.ModelAerwhale;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.animals.EntityAerwhale;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderAerwhale extends RenderLiving<EntityLiving>
{
	private static final ResourceLocation texture = AetherCore.getResource("textures/entities/aerwhale/aerwhale.png");

	public RenderAerwhale(RenderManager renderManager)
	{
		super(renderManager, new ModelAerwhale(), 2.0F);
	}

	float partialTicks;

	@Override
	protected void preRenderCallback(EntityLiving entity, float f)
	{
		GlStateManager.scale(2.0D, 2.0D, 2.0D);
		GlStateManager.translate(0.0D, 1.0D, 0);

		this.partialTicks = f;
	}

	@Override
	protected void renderModel(EntityLiving entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch,
			float scaleFactor)
	{
		EntityAerwhale aerwhale = (EntityAerwhale) entitylivingbaseIn;

		/*if (aerwhale.getFlightPath() != null)
		{
			Vec3d pos = entitylivingbaseIn.getPositionVector();

			Point3d cur = aerwhale.getPoint(this.partialTicks);

			if (Minecraft.getMinecraft().gameSettings.showDebugInfo)
			{
				GlStateManager.pushMatrix();

				GlStateManager.translate(-pos.x, pos.y, pos.z);
				Point3d last = null, last2 = null;

				float scale = 20f / Minecraft.getMinecraft().player.getDistance(entitylivingbaseIn);

				for (float i = 0; i <= 1.2f; i += 0.03f)
				{
					float t = i;

					Point3d cur2 = MathUtil.getPoints(aerwhale.getFlightPath(), t);

					if (last != null)
					{
//						GlUtil.drawLine(last.x, -last.y, -last.z, cur2.x, -cur2.y, -cur2.z, (int) (255 * t), 255 - (int) (t * 255), (int) (t * 122), 255, scale);
					}

					last = cur2;

					if (aerwhale.getFutureFlightPath() != null)
					{
						Point3d cur3 = MathUtil.getPoints(aerwhale.getFutureFlightPath(), t);

						if (last2 != null)
						{
//							GlUtil.drawLine(last2.x, -last2.y, -last2.z, cur3.x, -cur3.y, -cur3.z, 255 - (int) (t * 255), (int) (255 * t), (int) (t * 122), 255, scale);
						}

						last2 = cur3;
					}
				}

				GlStateManager.popMatrix();
			}

			boolean flag = this.isVisible(entitylivingbaseIn);
			boolean flag1 = !flag && !entitylivingbaseIn.isInvisibleToPlayer(Minecraft.getMinecraft().player);

			if (flag || flag1)
			{
				if (!this.bindEntityTexture(entitylivingbaseIn))
				{
					return;
				}

				if (flag1)
				{
					GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
				}

				Vec3d last = new Vec3d(cur.x, -cur.y, -cur.z);

				for (int i = 0; i < 3; i++)
				{
					Point3d v2 = MathUtil.getPoints(aerwhale.getFlightPath(), aerwhale.getTime() - 0.001f );
					Vec3d cur2 = new Vec3d(v2.x, -v2.y, -v2.z);

					GlStateManager.rotate(QuaternionUtil.lookAt(last, cur2));

					ModelAerwhale model = (ModelAerwhale) this.mainModel;

					if (i == 0)
					{
						model.Head.render(scaleFactor);
					}
					else if (i == 1)
					{
						model.MiddleBody.render(scaleFactor);
					}
					else
					{
						GlStateManager.translate(0.0D, -0.1D, 1);
						model.BackBody.render(scaleFactor);
					}

					last = cur2;

				}

				if (flag1)
				{
					GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
				}
			}
		}
		else
		{*/
			super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
//		}
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving entity)
	{
		return texture;
	}
}
