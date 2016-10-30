package com.gildedgames.aether.client.renderer.tile_entities;

import com.gildedgames.aether.client.models.entities.tile.ModelLabyrinthChest;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.containers.BlockLabyrinthChest;
import com.gildedgames.aether.common.tiles.TileEntityLabyrinthChest;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityLabyrinthChestRenderer extends TileEntitySpecialRenderer<TileEntityLabyrinthChest>
{
	private static final ResourceLocation textureSingle = AetherCore.getResource("textures/tile_entities/labyrinth_chest.png");

	// This is a stupid hack. See TileEntityItemStackRenderer.
	private static final TileEntityLabyrinthChest nullChest = new TileEntityLabyrinthChest();

	private ModelLabyrinthChest simpleChest = new ModelLabyrinthChest();

	@Override
	public void renderTileEntityAt(TileEntityLabyrinthChest chest, double x, double y, double z, float partialTicks, int destroyStage)
	{
		int metadata;

		if (chest == null)
		{
			chest = nullChest;
		}

		if (!chest.hasWorldObj())
		{
			metadata = 0;
		}
		else
		{
			Block block = chest.getBlockType();
			metadata = chest.getBlockMetadata();

			if (block instanceof BlockLabyrinthChest && metadata == 0)
			{
				metadata = chest.getBlockMetadata();
			}
		}

		ModelLabyrinthChest modelchest = this.simpleChest;
		this.bindTexture(textureSingle);

		if (destroyStage >= 0)
		{
			this.bindTexture(DESTROY_STAGES[destroyStage]);

			GlStateManager.matrixMode(5890);
			GlStateManager.pushMatrix();
			GlStateManager.scale(4.0F, 4.0F, 1.0F);
			GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
			GlStateManager.matrixMode(5888);
		}

		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();

		if (destroyStage < 0)
		{
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		}

		GlStateManager.translate((float) x, (float) y, (float) z);
		GlStateManager.scale(1.0F, -1.0F, -1.0F);

		float angle = 0;

		switch (metadata)
		{
		case 2:
			angle = 180;
			break;
		case 3:
			angle = 0;
			break;
		case 4:
			angle = 90;
			break;
		case 5:
			angle = -90;
			break;
		}

		GlStateManager.translate(0.5F, -0.5F, -0.5F);
		GlStateManager.rotate(angle, 0.0F, 1.0F, 0.0F);

		float lidAngle = chest.prevLidAngle + (chest.lidAngle - chest.prevLidAngle) * partialTicks;

		lidAngle = 1.0F - lidAngle;
		lidAngle = 1.0F - lidAngle * lidAngle * lidAngle;

			/*
	    ModelRenderer Main_Top;
    ModelRenderer Main_Bottom;
    ModelRenderer Hinge_Left;
    ModelRenderer Hinge_Right;
    ModelRenderer Corner_Bottom_Front_Right;
    ModelRenderer Corner_Bottom_Front_Left;
    ModelRenderer Corner_Bottom_Back_Left;
    ModelRenderer Corner_Bottom_Back_Right;
    ModelRenderer Corner_Top_Back_Left;
    ModelRenderer Corner_Top_Back_Right;
    ModelRenderer Corner_Top_Front_Left;
    ModelRenderer Corner_Top_Front_Right;

	 */

		float rotateAngleX = -(lidAngle * (float) Math.PI / 2.0F);

		//modelchest.chestLid.rotateAngleX = -(lidAngle * (float) Math.PI / 2.0F);
		modelchest.renderAll();

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		if (destroyStage >= 0)
		{
			GlStateManager.matrixMode(GL11.GL_TEXTURE);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(GL11.GL_MODELVIEW);
		}
	}
}
