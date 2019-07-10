package com.gildedgames.aether.client.renderer.entities.companions;

import com.gildedgames.aether.client.models.entities.companions.ModelNexSpirit;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.companions.EntityNexSpirit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class RenderNexSpirit extends RenderCompanion<EntityNexSpirit>
{
	private static final ResourceLocation TEXTURE = AetherCore.getResource("textures/entities/companions/nex_spirit.png");

	private static final ResourceLocation TEXTURE_BROKEN = AetherCore.getResource("textures/entities/companions/nex_spirit_broken.png");

	public RenderNexSpirit(RenderManager renderManager)
	{
		super(renderManager, new ModelNexSpirit(), 0.2f, 1.2D);
	}

	@Override
	protected void preRenderCallback(EntityNexSpirit entity, float partialTicks)
	{
		GL11.glScalef(0.65f, 0.65f, 0.65f);
		GL11.glTranslatef(0.0f, -0.7f, 0.0f);

		GL11.glTranslatef(0.0f, MathHelper.cos((entity.ticksExisted + partialTicks) / 10f) / 10f, -0.4f);
	}

	@Override
	protected void renderExtra(EntityNexSpirit entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scaleFactor, float opacity)
	{
		float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();

		float wave = MathHelper.cos((entity.ticksExisted + partialTicks) / 4);
		float wave1 = MathHelper.cos((entity.ticksExisted + partialTicks + 4) / 4);
		float wave2 = MathHelper.cos((entity.ticksExisted + partialTicks + 8) / 4) * 1.7f;

		GL11.glDisable(GL11.GL_TEXTURE_2D);

		int color = entity.isBroken() ? 0xc06967 : 0x7db3bf;

		float red = ((color >> 16) & 0xff) / 255F;
		float green = ((color >> 8) & 0xff) / 255F;
		float blue = (color & 0xff) / 255F;

		GL11.glPushMatrix();
		GL11.glColor4f(red, green, blue, opacity);

		GL11.glScalef(0.1f, 0.07f, 0.1f);
		GL11.glTranslatef(0, 0, 2.2f);

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glNormal3f(-1, 0, 0);
		GL11.glVertex3f(-3.5f, 0, 0);
		GL11.glVertex3f(-3.0f + wave, 5, 0);
		GL11.glVertex3f(-3.0f + wave, 5, 4);
		GL11.glVertex3f(-3.5f, 0, 4);

		GL11.glNormal3f(1, 0, 0);
		GL11.glVertex3f(3.5f, 0, 0);
		GL11.glVertex3f(3.0f + wave, 5, 0);
		GL11.glVertex3f(3.0f + wave, 5, 4);
		GL11.glVertex3f(3.5f, 0, 4);

		GL11.glNormal3f(0, 0, -1);
		GL11.glVertex3f(3.5f, 0, 0);
		GL11.glVertex3f(-3.5f, 0, 0);
		GL11.glVertex3f(-3.0f + wave, 5, 0);
		GL11.glVertex3f(3.0f + wave, 5, 0);

		GL11.glNormal3f(0, 0, 1);
		GL11.glVertex3f(3.5f, 0, 4);
		GL11.glVertex3f(-3.5f, 0, 4);
		GL11.glVertex3f(-3.0f + wave, 5, 4);
		GL11.glVertex3f(3.0f + wave, 5, 4);
		GL11.glEnd();
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glScalef(0.1f, 0.07f, 0.1f);
		GL11.glTranslatef(0, 5, 2.2f);

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glNormal3f(-1, 0, 0);
		GL11.glVertex3f(-2.5f + wave1, 5, 1);
		GL11.glVertex3f(-3.0f + wave, 0, 0);
		GL11.glVertex3f(-3.0f + wave, 0, 4);
		GL11.glVertex3f(-2.5f + wave1, 5, 3);

		GL11.glNormal3f(1, 0, 0);
		GL11.glVertex3f(2.5f + wave1, 5, 1);
		GL11.glVertex3f(3.0f + wave, 0, 0);
		GL11.glVertex3f(3.0f + wave, 0, 4);
		GL11.glVertex3f(2.5f + wave1, 5, 3);

		GL11.glNormal3f(0, 0, -1);
		GL11.glVertex3f(2.5f + wave1, 5, 1);
		GL11.glVertex3f(-2.5f + wave1, 5, 1);
		GL11.glVertex3f(-3.0f + wave, 0, 0);
		GL11.glVertex3f(3.0f + wave, 0, 0);

		GL11.glNormal3f(0, 0, 1);
		GL11.glVertex3f(2.5f + wave1, 5, 3);
		GL11.glVertex3f(-2.5f + wave1, 5, 3);
		GL11.glVertex3f(-3.0f + wave, 0, 4);
		GL11.glVertex3f(3.0f + wave, 0, 4);
		GL11.glEnd();
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glScalef(0.1f, 0.07f, 0.1f);
		GL11.glTranslatef(0, 10, 2.2f);

		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glNormal3f(-1, 0, 0);
		GL11.glVertex3f(-2.5f + wave1, 0, 1);
		GL11.glVertex3f(0 + wave2, 8, 2);
		GL11.glVertex3f(-2.5f + wave1, 0, 3);

		GL11.glNormal3f(-1, 0, 0);
		GL11.glVertex3f(2.5f + wave1, 0, 1);
		GL11.glVertex3f(0 + wave2, 8, 2);
		GL11.glVertex3f(2.5f + wave1, 0, 3);

		GL11.glNormal3f(0, 0, -1);
		GL11.glVertex3f(2.5f + wave1, 0, 1);
		GL11.glVertex3f(0 + wave2, 8, 2);
		GL11.glVertex3f(-2.5f + wave1, 0, 1);

		GL11.glNormal3f(0, 0, 1);
		GL11.glVertex3f(2.5f + wave1, 0, 3);
		GL11.glVertex3f(0 + wave2, 8, 2);
		GL11.glVertex3f(-2.5f + wave1, 0, 3);
		GL11.glEnd();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();

		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityNexSpirit entity)
	{
		return entity.isBroken() ? TEXTURE_BROKEN : TEXTURE;
	}
}
