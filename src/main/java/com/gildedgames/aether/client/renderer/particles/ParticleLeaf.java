package com.gildedgames.aether.client.renderer.particles;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleLeaf extends Particle
{
	private final ResourceLocation sprite = AetherCore.getResource("textures/particles/skyroot_leaf_particle.png");

	private float mulRotX;
	private float mulRotY;

	private float prevRotX;
	private float prevRotY;

	private float rotX;
	private float rotY;

	public ParticleLeaf(World worldIn, double posXIn, double posYIn, double posZIn, double motionX, double motionY, double motionZ, Block block)
	{
		super(worldIn, posXIn, posYIn, posZIn);

		if (block == BlocksAether.green_skyroot_leaves)
		{
			this.particleRed = 0.627f;
			this.particleGreen = 0.753f;
			this.particleBlue = 0.522f;
		}
		else if (block == BlocksAether.golden_oak_leaves)
		{
			this.particleRed = 0.976f;
			this.particleGreen = 0.745f;
			this.particleBlue = 0.0f;
		}
		else if (block == BlocksAether.blue_dark_skyroot_leaves)
		{
			this.particleRed = 0.282f;
			this.particleGreen = 0.401f;
			this.particleBlue = 0.428f;
		}
		else if (block == BlocksAether.blue_light_skyroot_leaves)
		{
			this.particleRed = 0.592f;
			this.particleGreen = 0.720f;
			this.particleBlue = 0.733f;
		}
		else if (block == BlocksAether.blue_skyroot_leaves)
		{
			this.particleRed = 0.458f;
			this.particleGreen = 0.606f;
			this.particleBlue = 0.657f;
		}
		else if (block == BlocksAether.dark_blue_dark_skyroot_leaves)
		{
			this.particleRed = 0.294f;
			this.particleGreen = 0.355f;
			this.particleBlue = 0.588f;
		}
		else if (block == BlocksAether.dark_blue_light_skyroot_leaves)
		{
			this.particleRed = 0.468f;
			this.particleGreen = 0.501f;
			this.particleBlue = 0.734f;
		}
		else if (block == BlocksAether.dark_blue_skyroot_leaves)
		{
			this.particleRed = 0.355f;
			this.particleGreen = 0.410f;
			this.particleBlue = 0.725f;
		}
		else if (block == BlocksAether.green_dark_skyroot_leaves)
		{
			this.particleRed = 0.326f;
			this.particleGreen = 0.428f;
			this.particleBlue = 0.228f;
		}
		else if (block == BlocksAether.green_light_skyroot_leaves)
		{
			this.particleRed = 0.697f;
			this.particleGreen = 0.811f;
			this.particleBlue = 0.597f;
		}
		else if (block == BlocksAether.therawood_leaves)
		{
			this.particleRed = 0.253f;
			this.particleGreen = 0.512f;
			this.particleBlue = 0.301f;
		}


		this.motionY = motionY;
		this.motionX = motionX;
		this.motionZ = motionZ;

		this.mulRotY = (this.rand.nextFloat() * 100) * (float) (this.motionY);
		this.mulRotX = (this.rand.nextFloat() * 100) * (float) (this.motionY);

		this.particleMaxAge = 100;
		this.canCollide = false;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		this.prevRotX = this.rotX;
		this.prevRotY = this.rotY;

		this.rotX += this.mulRotX;
		this.rotY += this.mulRotY;
	}

	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(this.sprite);

		GlStateManager.disableLighting();

		double x = this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX;
		double y = this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY;
		double z = this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ;

		int brightness = this.getBrightnessForRender(partialTicks);

		int j = brightness >> 16 & 65535;
		int k = brightness & 65535;

		float scale = 0.1F * this.particleScale;

		float f2 = this.prevRotX + ((this.rotX - this.prevRotX) * partialTicks);
		float f3 = this.prevRotY + ((this.rotY - this.prevRotY) * partialTicks);

		int i = 0;

		rotationX = MathHelper.cos(f3 * 0.017453292F) * (float)(1 - i * 2);
		rotationZ = MathHelper.sin(f3 * 0.017453292F) * (float)(1 - i * 2);

		rotationYZ = -rotationZ * MathHelper.sin(f2 * 0.017453292F) * (float)(1 - i * 2);
		rotationXY = rotationX * MathHelper.sin(f2 * 0.017453292F) * (float)(1 - i * 2);
		rotationXZ = MathHelper.cos(f2 * 0.017453292F);

		Vec3d[] avec3d = new Vec3d[] {
				new Vec3d(
						(double)(-rotationX * scale - rotationXY * scale),
						(double)(-rotationZ * scale),
						(double)(-rotationYZ * scale - rotationXZ * scale)
				),
				new Vec3d(
						(double)(-rotationX * scale + rotationXY * scale),
						(double)(rotationZ * scale),
						(double)(-rotationYZ * scale + rotationXZ * scale)
				),
				new Vec3d(
						(double)(rotationX * scale + rotationXY * scale),
						(double)(rotationZ * scale),
						(double)(rotationYZ * scale + rotationXZ * scale)
				),
				new Vec3d(
						(double)(rotationX * scale - rotationXY * scale),
						(double)(-rotationZ * scale),
						(double)(rotationYZ * scale - rotationXZ * scale)
				)
		};

		float a = 1.0f - (Math.max(0.0f, this.particleAge - 70.0f) * 0.03f);

		buffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		buffer.pos(x + avec3d[0].x, y + avec3d[0].y, z + avec3d[0].z).tex(0.0D, 1.0D).color(this.particleRed, this.particleGreen, this.particleBlue, a).lightmap(j, k).endVertex();
		buffer.pos(x + avec3d[1].x, y + avec3d[1].y, z + avec3d[1].z).tex(1.0D, 1.0D).color(this.particleRed, this.particleGreen, this.particleBlue, a).lightmap(j, k).endVertex();
		buffer.pos(x + avec3d[2].x, y + avec3d[2].y, z + avec3d[2].z).tex(1.0D, 0.0D).color(this.particleRed, this.particleGreen, this.particleBlue, a).lightmap(j, k).endVertex();
		buffer.pos(x + avec3d[3].x, y + avec3d[3].y, z + avec3d[3].z).tex(0.0D, 0.0D).color(this.particleRed, this.particleGreen, this.particleBlue, a).lightmap(j, k).endVertex();

		Tessellator.getInstance().draw();
	}

	public int getFXLayer()
	{
		return 3;
	}

}
