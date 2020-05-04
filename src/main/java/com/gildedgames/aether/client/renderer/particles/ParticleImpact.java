package com.gildedgames.aether.client.renderer.particles;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleImpact extends Particle
{
	private static final ResourceLocation SLASH_TEXTURE = AetherCore.getResource("textures/particles/impact.png");

	public ParticleImpact(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46285_8_, double p_i46285_10_, double p_i46285_12_, float p_i46285_14_)
	{
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);

		float f0 = Math.random() > 0.5 ? 0.5F : -0.5F;
		float f1 = Math.random() > 0.5 ? 0.5F : -0.5F;
		this.motionX *= 0.10000000149011612D;
		this.motionY *= 0.10000000149011612D;
		this.motionZ *= 0.10000000149011612D;
		this.motionX += f0 * 0.4D;
		this.motionY += p_i46285_10_ * 0.4D;
		this.motionZ += f1 * 0.4D;
		float f = 1.0F;
		this.particleRed = f;
		this.particleGreen = f;
		this.particleBlue = f;
		this.particleScale *= 0.75F;
		this.particleScale *= p_i46285_14_;
		this.particleMaxAge = (int)(6.0D / (Math.random() * 0.8D + 0.6D));
		this.particleMaxAge = (int)((float)this.particleMaxAge * p_i46285_14_);
		this.onUpdate();
	}

	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.particleAge++ >= this.particleMaxAge)
		{
			this.setExpired();
		}

		this.move(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.699999988079071D;
		this.motionY *= 0.699999988079071D;
		this.motionZ *= 0.699999988079071D;
		this.motionY -= 0.019999999552965164D;

		if (this.onGround)
		{
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
		}
	}

	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY,
							   float rotationXZ)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(SLASH_TEXTURE);

		GlStateManager.disableLighting();

		double x = this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - interpPosX;
		double y = this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - interpPosY;
		double z = this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - interpPosZ;

		int brightness = this.getBrightnessForRender(partialTicks);

		int j = brightness >> 16 & 65535;
		int k = brightness & 65535;

		float f = ((float)this.particleAge + partialTicks) / (float)this.particleMaxAge * 32.0F;

		f = MathHelper.clamp(f, 0.0F, 1.0F);

		float scale = 0.1F * this.particleScale * f;

		Vec3d[] avec3d = new Vec3d[] {
				new Vec3d(
						(double) (-rotationX * scale - rotationXY * scale),
						(double) (-rotationZ * scale),
						(double) (-rotationYZ * scale - rotationXZ * scale)
				),
				new Vec3d(
						(double) (-rotationX * scale + rotationXY * scale),
						(double) (rotationZ * scale),
						(double) (-rotationYZ * scale + rotationXZ * scale)
				),
				new Vec3d(
						(double) (rotationX * scale + rotationXY * scale),
						(double) (rotationZ * scale),
						(double) (rotationYZ * scale + rotationXZ * scale)
				),
				new Vec3d(
						(double) (rotationX * scale - rotationXY * scale),
						(double) (-rotationZ * scale),
						(double) (rotationYZ * scale - rotationXZ * scale)
				)
		};

		float a = 1.0f - (Math.max(0.0f, this.particleAge - 70.0f) * 0.03f);

		buffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		buffer.pos(x + avec3d[0].x, y + avec3d[0].y, z + avec3d[0].z).tex(0.0D, 1.0D).color(this.particleRed, this.particleGreen, this.particleBlue, a)
				.lightmap(j, k).endVertex();
		buffer.pos(x + avec3d[1].x, y + avec3d[1].y, z + avec3d[1].z).tex(1.0D, 1.0D).color(this.particleRed, this.particleGreen, this.particleBlue, a)
				.lightmap(j, k).endVertex();
		buffer.pos(x + avec3d[2].x, y + avec3d[2].y, z + avec3d[2].z).tex(1.0D, 0.0D).color(this.particleRed, this.particleGreen, this.particleBlue, a)
				.lightmap(j, k).endVertex();
		buffer.pos(x + avec3d[3].x, y + avec3d[3].y, z + avec3d[3].z).tex(0.0D, 0.0D).color(this.particleRed, this.particleGreen, this.particleBlue, a)
				.lightmap(j, k).endVertex();

		Tessellator.getInstance().draw();
	}

	@Override
	public int getFXLayer()
	{
		return 3;
	}
}