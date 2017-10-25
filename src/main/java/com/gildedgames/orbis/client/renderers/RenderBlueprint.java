package com.gildedgames.orbis.client.renderers;

import com.gildedgames.aether.api.orbis.IWorldRenderer;
import com.gildedgames.aether.api.orbis.region.IRegion;
import com.gildedgames.aether.api.orbis.util.RegionHelp;
import com.gildedgames.aether.common.util.helpers.BlockUtil;
import com.gildedgames.orbis.client.renderers.blueprint.BlueprintRenderCache;
import com.gildedgames.orbis.common.world_objects.Blueprint;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.List;

public class RenderBlueprint implements IWorldRenderer
{
	private final static VertexBuffer buffer = Tessellator.getInstance().getBuffer();//Magic number, see Tessellator

	private final Minecraft mc = Minecraft.getMinecraft();

	//Setup the next parameters to vary the use of the renderer.
	//----------------------------------------------------------------------

	private final List<IWorldRenderer> subRenderers = Lists.newArrayList();

	private final BlockRendererDispatcher blockRenderer;

	private final Blueprint blueprint;

	private final BlueprintRenderCache cache;

	//----------------------------------------------------------------------

	private final TileEntityRendererDispatcher tileEntityRenderer;

	private final World worldIn;

	private final float scale = 1.0f;

	/**
	 * doesRecolor: Set to true if you want to recolor the blocks you paint.
	 * renderDimensions: Set to true to render the dimensions of the blueprint above it like x x y x z
	 * renderBlocks : Set to false to stop rendering the blocks inside the blueprint and only render the references inside it
	 */
	public boolean doesRecolor = false, renderDimensions = true, renderBlocks = true;

	/**
	 * useCamera: Set to false to render in a gui. TODO: Implement easier way
	 * renderGridReferences: Set to false to stop rendering the grid in the regions of the References
	 */
	public boolean useCamera = true, renderGridReferences = true;

	/**
	 * Number between 0.0f and 1.0f. Put lower to add transparency
	 */
	public float alpha = 0.0f;

	/**
	 * When doesRecolor == true, this is the color it'll recolor to.
	 */
	public int color = 0x0000FF;

	private float rotval = 0.0f;

	private int glIndex = -1;

	public RenderBlueprint(final Blueprint blueprint, final World world)
	{
		this.blockRenderer = this.mc.getBlockRendererDispatcher();
		this.blueprint = blueprint;
		this.cache = new BlueprintRenderCache(blueprint, world);
		this.tileEntityRenderer = TileEntityRendererDispatcher.instance;

		this.worldIn = world;
	}

	@Override
	public void write(final NBTTagCompound tag)
	{

	}

	@Override
	public void read(final NBTTagCompound tag)
	{

	}

	@Nullable
	@Override
	public Object getRenderedObject()
	{
		return this.blueprint;
	}

	private void renderFully(final World world, final float partialTicks)
	{
		if (!this.renderBlocks)
		{
			return;
		}

		GlStateManager.pushMatrix();

		this.glIndex = GlStateManager.glGenLists(1);
		GlStateManager.glNewList(this.glIndex, GL11.GL_COMPILE);

		GlStateManager.disableLighting();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableCull();

		GlStateManager.color(1, 1, 1, 1);

		final Iterable<BlockPos.MutableBlockPos> shapeData = this.blueprint.createShapeData(world);

		final TextureManager textureManager = Minecraft.getMinecraft().renderEngine;
		textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		buffer.begin(7, DefaultVertexFormats.BLOCK);

		for (final BlockPos pos : shapeData)
		{
			this.renderPos(pos, partialTicks);
		}

		Tessellator.getInstance().draw();

		/** Render tile entities separately since they're done on their own
		 * draw call with the tesselator **/
		for (final BlockPos pos : shapeData)
		{
			GlStateManager.pushMatrix();
			this.renderTileEntityIfPossible(pos, partialTicks);
			GlStateManager.popMatrix();
		}

		GlStateManager.enableLighting();
		GlStateManager.enableCull();

		GlStateManager.glEndList();

		this.doGlobalRendering(world, partialTicks);

		GlStateManager.popMatrix();
	}

	private void renderTileEntityIfPossible(final BlockPos pos, final float partialTicks)
	{
		if (!this.renderBlocks || !RegionHelp.contains(this.blueprint, pos))
		{
			return;
		}

		final IBlockState state = this.cache.getBlockState(pos);

		if (state != null && !BlockUtil.isAir(state))
		{
			final Block block = state.getBlock();

			if (block.hasTileEntity(state))
			{
				final TileEntity tileEntity = this.cache.getTileEntity(pos);

				if (tileEntity != null && this.tileEntityRenderer.getSpecialRenderer(tileEntity) != null)
				{
					this.tileEntityRenderer.renderTileEntityAt(tileEntity, pos.getX(), pos.getY(), pos.getZ(), partialTicks);//TODO: Partialticks?
				}
			}
		}
	}

	private void renderPos(final BlockPos pos, final float partialTicks)
	{
		if (!this.renderBlocks || !RegionHelp.contains(this.blueprint, pos))
		{
			return;
		}

		final IBlockState state = this.cache.getBlockState(pos);

		if (state != null && !BlockUtil.isAir(state) && !BlockUtil.isVoid(state))
		{
			//Thank you Ivorius for the rendering of blocks code <3333
			GlStateManager.pushMatrix();

			final IBakedModel modelBaked = this.blockRenderer.getModelForState(state);

			final BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
			blockrendererdispatcher.getBlockModelRenderer().renderModel(this.cache, modelBaked, state, pos, buffer, true);

			GlStateManager.popMatrix();
		}
	}

	@Override
	public void startRendering(final World world, final float partialTicks)
	{

	}

	@Override
	public void finishRendering(final World world)
	{

	}

	/**
	 * Draws a block from the blueprint in the world that is supposed to be at the given position
	 */
	@Override
	public void render(final World world, final BlockPos pos, final float partialTicks)
	{
		/*if (!this.renderBlocks || !RegionHelp.contains(this.blueprint, pos))
		{
			return;
		}

		final IBlockState state = this.cache.getBlockState(pos);

		if (state != null && !BlockUtil.isAir(state))
		{
			final Block block = state.getBlock();

			if (block.hasTileEntity(state))
			{
				final TileEntity tileEntity = this.cache.getTileEntity(pos);

				if (tileEntity != null && this.tileEntityRenderer.getSpecialRenderer(tileEntity) != null)
				{
					this.tileEntityRenderer.renderTileEntityAt(tileEntity, pos.getX(), pos.getY(), pos.getZ(), 1);//TODO: Partialticks?
				}
			}

			//Thank you Ivorius for the rendering of blocks code <3333
			GlStateManager.pushMatrix();

			final IBakedModel modelBaked = this.blockRenderer.getModelForState(state);

			final BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
			blockrendererdispatcher.getBlockModelRenderer().renderModel(this.cache, modelBaked, state, pos, buffer, true);

			GlStateManager.popMatrix();
		}*/
	}

	@Override
	public IRegion getBoundingBox()
	{
		return this.blueprint.getBoundingBox();
	}

	@Override
	public List<IWorldRenderer> getSubRenderers(final World world)
	{
		return this.subRenderers;
	}

	/**
	 * Looks through the references inside the blueprint and sets up InWorldRenderers for them.
	 * If it's a blueprint it's going to render, this call is continued recursively.
	 */
	@Override
	public void prepare(final World world)
	{

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

		if (this.useCamera)
		{
			GlStateManager.translate(-offsetPlayerX, -offsetPlayerY, -offsetPlayerZ);
		}
		else
		{
			this.rotval += partialTicks * 1;
			GlStateManager.translate(0.0D, 0.0D, 0.0D);
			int maxval = Math.max(this.blueprint.getWidth(), this.blueprint.getHeight());
			maxval = Math.max(this.blueprint.getLength(), maxval);
			final float scalefactor = Math.min(1, this.scale / maxval);

			GlStateManager.translate(47.5, 100, 100.0D);//TODO: Customize spot where you render the preview box here
			GlStateManager.scale(this.scale, this.scale, this.scale);
			GlStateManager.scale(scalefactor, scalefactor, scalefactor);
			//GL11.glTranslated(maxval/2, 0, 0);

			GlStateManager.rotate(-225.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(25.0F, 1.0F, 0.0F, 1.0F);

			GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
			RenderHelper.enableStandardItemLighting();
			GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.enableDepth();
			GlStateManager.depthMask(true);
			//GL11.glDepthRange(1, 0); // what am i even doing

			GlStateManager.disableCull();
			GlStateManager.rotate(-this.rotval, 0, 1, 0); // translate to center!
			GlStateManager.translate(-this.blueprint.getWidth() / 2.f, 0, -this.blueprint.getLength() / 2.f); //not centered at y
		}

		GlStateManager.callList(this.glIndex);

		if (this.useCamera)
		{
			GlStateManager.translate(0, 0, 0);
		}

		GlStateManager.popMatrix();
	}

	@Override
	public void onRemoved()
	{
		if (this.glIndex != -1)
		{
			GlStateManager.glDeleteLists(this.glIndex, 1);
		}
	}
}
