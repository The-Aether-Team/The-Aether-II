package com.gildedgames.orbis.client.renderers;

import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis.IWorldRenderer;
import com.gildedgames.aether.common.util.helpers.BlockUtil;
import com.gildedgames.orbis.client.renderers.blueprint.BlueprintRenderCache;
import com.gildedgames.orbis_core.util.OrbisTuple;
import com.gildedgames.orbis_core.util.RotationHelp;
import com.gildedgames.orbis_core.world_objects.Blueprint;
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
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.Rotation;
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

	private final TileEntityRendererDispatcher tileEntityRenderer;

	//----------------------------------------------------------------------

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

	private BlockPos lastMin;

	private float rotval = 0.0f;

	private int glIndex = -1;

	private Iterable<BlockPos.MutableBlockPos> shapeData;

	private Iterable<OrbisTuple<BlockPos, BlockPos>> rotatedData;

	private Rotation lastRotation;

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

		this.glIndex = GLAllocation.generateDisplayLists(1);
		GlStateManager.glNewList(this.glIndex, GL11.GL_COMPILE);

		GlStateManager.color(1, 1, 1, 1);

		final TextureManager textureManager = Minecraft.getMinecraft().renderEngine;
		textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		buffer.begin(7, DefaultVertexFormats.BLOCK);

		if (this.rotatedData != null)
		{
			for (final OrbisTuple<BlockPos, BlockPos> tuple : this.rotatedData)
			{
				final BlockPos beforeRot = tuple.getFirst();
				final BlockPos rotated = tuple.getSecond();

				this.renderPos(rotated, beforeRot, partialTicks);
			}
		}
		else
		{
			for (final BlockPos pos : this.shapeData)
			{
				this.renderPos(pos, pos, partialTicks);
			}
		}

		Tessellator.getInstance().draw();

		/** TODO: Temp disabled te rendering because of strange bug when holding a blueprint and loading a world first time**/
		/*final int pass = net.minecraftforge.client.MinecraftForgeClient.getRenderPass();

		TileEntityRendererDispatcher.instance.preDrawBatch();

		*//** Render tile entities separately since they're done on their own
	 * draw call with the tesselator **//*
		for (final BlockPos pos : this.shapeData)
		{
			this.renderTileEntityIfPossible(pos, partialTicks, pass);
		}

		TileEntityRendererDispatcher.instance.drawBatch(pass);*/

		GlStateManager.glEndList();

		GlStateManager.popMatrix();

		this.doGlobalRendering(world, partialTicks);
	}

	private void renderTileEntityIfPossible(final BlockPos pos, final float partialTicks, final int pass)
	{
		if (!this.renderBlocks)
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

				if (!tileEntity.shouldRenderInPass(pass))
				{
					TileEntityRendererDispatcher.instance.renderTileEntityAt(tileEntity, pos.getX(), pos.getY(), pos.getZ(), partialTicks);
				}
			}
		}
	}

	private void renderPos(final BlockPos renderPos, final BlockPos containerPos, final float partialTicks)
	{
		if (!this.renderBlocks)
		{
			return;
		}

		final IBlockState state = this.cache.getBlockState(containerPos);

		if (state != null && !BlockUtil.isAir(state) && !BlockUtil.isVoid(state) && state.getRenderType() != EnumBlockRenderType.INVISIBLE
				&& state.getRenderType() != EnumBlockRenderType.ENTITYBLOCK_ANIMATED)
		{
			//Thank you Ivorius for the rendering of blocks code <3333
			final IBakedModel modelBaked = this.blockRenderer.getModelForState(state);

			final BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
			blockrendererdispatcher.getBlockModelRenderer().renderModel(this.cache, modelBaked, state, renderPos, buffer, true);
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
		if (this.lastMin == null)
		{
			this.lastMin = this.blueprint.getMin();
			this.shapeData = this.blueprint.createShapeData();
		}

		if (this.lastRotation != this.blueprint.getRotation())
		{
			this.lastRotation = this.blueprint.getRotation();

			final int rotAmount = Math.abs(RotationHelp.getRotationAmount(this.blueprint.getRotation(), Rotation.NONE));

			if (rotAmount != 0)
			{
				this.rotatedData = RotationHelp.getAllInBoxRotated(this.blueprint.getMin(), this.blueprint.getMax(), this.blueprint.getRotation());
			}
			else
			{
				this.rotatedData = null;
			}
		}

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
			if (!this.lastMin.equals(this.blueprint.getMin()))
			{
				GlStateManager.translate(this.blueprint.getMin().getX() - this.lastMin.getX(),
						this.blueprint.getMin().getY() - this.lastMin.getY(),
						this.blueprint.getMin().getZ() - this.lastMin.getZ());
			}

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

		GlStateManager.disableLighting();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.enableCull();

		GlStateManager.callList(this.glIndex);

		GlStateManager.enableLighting();
		//GlStateManager.enableCull();

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
			GLAllocation.deleteDisplayLists(this.glIndex, 1);
			this.glIndex = -1;
		}
	}
}
