package com.gildedgames.orbis.client;

import com.gildedgames.aether.api.orbis.IWorldRenderer;
import com.gildedgames.aether.api.orbis.region.IRegion;
import com.gildedgames.aether.api.orbis.shapes.IShape;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class RenderShape implements IWorldRenderer
{

	private final static VertexBuffer buffer = Tessellator.getInstance().getBuffer();//new WorldRendererColorReplacer(2097152);

	private final static List<IWorldRenderer> subRenderers = new ArrayList<IWorldRenderer>();

	private final Minecraft mc = Minecraft.getMinecraft();

	/**
	 * renderGrid: Set to false to stop rendering the grid for the region
	 * renderBorder: Set to false to stop rendering the border around the region
	 * renderDimensionsAbove: Set to false to stop rendering the num x num x num above the region
	 */
	public boolean renderGrid = true, renderBorder = true, renderDimensionsAbove = true;

	public float boxAlpha = 0.25F, gridAlpha = 0.65F;

	public boolean useCustomColors = false;

	public int colorBorder = -1, colorGrid = -1;

	private int glIndex = -1;

	private Object object;

	private IShape shape;

	private World world;

	public RenderShape(final World world)
	{
		this.world = world;
	}

	public RenderShape(final IShape shape)
	{
		this.object = shape;
		this.shape = shape;
	}

	public void renderFully(final World world, final float partialTicks)
	{
		GlStateManager.pushMatrix();

		final int color = this.useCustomColors ? this.colorGrid : 0xFFFFFF;

		final float red = (color >> 16 & 0xff) / 255F;
		final float green = (color >> 8 & 0xff) / 255F;
		final float blue = (color & 0xff) / 255F;

		this.glIndex = GlStateManager.glGenLists(1);
		GlStateManager.glNewList(this.glIndex, GL11.GL_COMPILE);

		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableCull();
		GlStateManager.disableLighting();

		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.disableTexture2D();
		GlStateManager.depthMask(false);

		GlStateManager.color(red, green, blue, this.boxAlpha);

		buffer.begin(7, DefaultVertexFormats.POSITION);

		for (final BlockPos pos : this.shape.createShapeData(world))
		{
			this.renderBox(pos, partialTicks);
		}

		Tessellator.getInstance().draw();

		buffer.setTranslation(0, 0, 0);
		GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
		GlStateManager.enableDepth();
		GlStateManager.enableLighting();

		GlStateManager.depthMask(true);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.glEndList();

		this.doGlobalRendering(world, partialTicks);

		GlStateManager.popMatrix();
	}

	public void renderBox(final BlockPos pos, final float partialTicks)
	{
		final int minX = pos.getX();
		final int minY = pos.getY();
		final int minZ = pos.getZ();

		final int maxX = pos.getX() + 1;
		final int maxY = pos.getY() + 1;
		final int maxZ = pos.getZ() + 1;

		final float stretch = 0.01F;

		final AxisAlignedBB bounds = new AxisAlignedBB(minX - stretch, minY - stretch, minZ - stretch, maxX + stretch, maxY + stretch, maxZ + stretch);

		if (!this.shape.contains(minX - 1, minY, minZ))
		{
			buffer.pos(bounds.minX, bounds.minY, bounds.minZ).endVertex();
			buffer.pos(bounds.minX, bounds.minY, bounds.maxZ).endVertex();

			buffer.pos(bounds.minX, bounds.maxY, bounds.minZ).endVertex();
			buffer.pos(bounds.minX, bounds.maxY, bounds.maxZ).endVertex();

			buffer.pos(bounds.minX, bounds.minY, bounds.maxZ).endVertex();
			buffer.pos(bounds.minX, bounds.minY, bounds.minZ).endVertex();

			buffer.pos(bounds.minX, bounds.maxY, bounds.maxZ).endVertex();
			buffer.pos(bounds.minX, bounds.maxY, bounds.minZ).endVertex();
		}

		if (!this.shape.contains(minX + 1, minY, minZ))
		{
			buffer.pos(bounds.maxX, bounds.minY, bounds.maxZ).endVertex();
			buffer.pos(bounds.maxX, bounds.minY, bounds.minZ).endVertex();

			buffer.pos(bounds.maxX, bounds.maxY, bounds.maxZ).endVertex();
			buffer.pos(bounds.maxX, bounds.maxY, bounds.minZ).endVertex();

			buffer.pos(bounds.maxX, bounds.minY, bounds.minZ).endVertex();
			buffer.pos(bounds.maxX, bounds.minY, bounds.maxZ).endVertex();

			buffer.pos(bounds.maxX, bounds.maxY, bounds.minZ).endVertex();
			buffer.pos(bounds.maxX, bounds.maxY, bounds.maxZ).endVertex();
		}

		if (!this.shape.contains(minX, minY, minZ - 1))
		{
			buffer.pos(bounds.minX, bounds.minY, bounds.minZ).endVertex();
			buffer.pos(bounds.maxX, bounds.minY, bounds.minZ).endVertex();

			buffer.pos(bounds.minX, bounds.maxY, bounds.minZ).endVertex();
			buffer.pos(bounds.maxX, bounds.maxY, bounds.minZ).endVertex();

			buffer.pos(bounds.maxX, bounds.minY, bounds.minZ).endVertex();
			buffer.pos(bounds.minX, bounds.minY, bounds.minZ).endVertex();

			buffer.pos(bounds.maxX, bounds.maxY, bounds.minZ).endVertex();
			buffer.pos(bounds.minX, bounds.maxY, bounds.minZ).endVertex();
		}

		if (!this.shape.contains(minX, minY, minZ + 1))
		{
			buffer.pos(bounds.minX, bounds.minY, bounds.maxZ).endVertex();
			buffer.pos(bounds.maxX, bounds.minY, bounds.maxZ).endVertex();

			buffer.pos(bounds.minX, bounds.maxY, bounds.maxZ).endVertex();
			buffer.pos(bounds.maxX, bounds.maxY, bounds.maxZ).endVertex();

			buffer.pos(bounds.maxX, bounds.minY, bounds.maxZ).endVertex();
			buffer.pos(bounds.minX, bounds.minY, bounds.maxZ).endVertex();

			buffer.pos(bounds.maxX, bounds.maxY, bounds.maxZ).endVertex();
			buffer.pos(bounds.minX, bounds.maxY, bounds.maxZ).endVertex();
		}

		if (!this.shape.contains(minX, minY - 1, minZ))
		{
			buffer.pos(bounds.minX, bounds.minY, bounds.minZ).endVertex();
			buffer.pos(bounds.maxX, bounds.minY, bounds.minZ).endVertex();

			buffer.pos(bounds.minX, bounds.minY, bounds.maxZ).endVertex();
			buffer.pos(bounds.maxX, bounds.minY, bounds.maxZ).endVertex();

			buffer.pos(bounds.maxX, bounds.minY, bounds.minZ).endVertex();
			buffer.pos(bounds.minX, bounds.minY, bounds.minZ).endVertex();

			buffer.pos(bounds.maxX, bounds.minY, bounds.maxZ).endVertex();
			buffer.pos(bounds.minX, bounds.minY, bounds.maxZ).endVertex();
		}

		if (!this.shape.contains(minX, minY + 1, minZ))
		{
			buffer.pos(bounds.minX, bounds.maxY, bounds.minZ).endVertex();
			buffer.pos(bounds.maxX, bounds.maxY, bounds.minZ).endVertex();

			buffer.pos(bounds.minX, bounds.maxY, bounds.maxZ).endVertex();
			buffer.pos(bounds.maxX, bounds.maxY, bounds.maxZ).endVertex();

			buffer.pos(bounds.maxX, bounds.maxY, bounds.minZ).endVertex();
			buffer.pos(bounds.minX, bounds.maxY, bounds.minZ).endVertex();

			buffer.pos(bounds.maxX, bounds.maxY, bounds.maxZ).endVertex();
			buffer.pos(bounds.minX, bounds.maxY, bounds.maxZ).endVertex();
		}
	}

	@Override
	public Object getRenderedObject()
	{
		return this.object;
	}

	@Override
	public void render(final World world, final BlockPos pos, final float partialTicks)
	{
	}

	@Override
	public IRegion getBoundingBox()
	{
		return this.shape.getBoundingBox();
	}

	@Override
	public void startRendering(final World world, final float partialTicks)
	{
	}

	@Override
	public void finishRendering(final World world)
	{

	}

	@Override
	public List<IWorldRenderer> getSubRenderers(final World world)
	{
		return subRenderers;
	}

	@Override
	public void doGlobalRendering(final World world, final float partialTicks)
	{
		if (this.glIndex == -1)
		{
			this.renderFully(world, partialTicks);
			return;
		}

		final double offsetPlayerX = this.mc.player.lastTickPosX + (this.mc.player.posX - this.mc.player.lastTickPosX) * partialTicks;
		final double offsetPlayerY = this.mc.player.lastTickPosY + (this.mc.player.posY - this.mc.player.lastTickPosY) * partialTicks;
		final double offsetPlayerZ = this.mc.player.lastTickPosZ + (this.mc.player.posZ - this.mc.player.lastTickPosZ) * partialTicks;

		GlStateManager.pushMatrix();

		GlStateManager.translate(-offsetPlayerX, -offsetPlayerY, -offsetPlayerZ);
		GlStateManager.callList(this.glIndex);
		GlStateManager.translate(0, 0, 0);

		GlStateManager.popMatrix();

		if (this.renderDimensionsAbove)
		{
			RenderUtil.renderDimensionsAbove(this.shape.getBoundingBox(), partialTicks);
		}
	}

	@Override
	public void prepare(final World world)
	{
	}

	@Override
	public void onRemoved()
	{
		if (this.glIndex != -1)
		{
			GlStateManager.glDeleteLists(this.glIndex, 1);
		}
	}

	@Override
	public void write(final NBTTagCompound tag)
	{

	}

	@Override
	public void read(final NBTTagCompound tag)
	{

	}
}
