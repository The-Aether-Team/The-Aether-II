package com.gildedgames.aether.client.renderer.tiles;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.entities.tiles.TileEntitySkyrootChest;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.model.ChestModel;
import net.minecraft.client.renderer.tileentity.model.LargeChestModel;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;

public class TileEntitySkyrootChestRenderer extends TileEntityRenderer<TileEntitySkyrootChest>
{
	private static final ResourceLocation TEXTURE_SINGLE = AetherCore.getResource("textures/tile_entities/skyroot_chest_large.png");

	private static final ResourceLocation TEXTURE_DOUBLE = AetherCore.getResource("textures/tile_entities/skyroot_chest.png");

	// This is a stupid hack. See TileEntityItemStackRenderer.
	private static final TileEntitySkyrootChest nullChest = new TileEntitySkyrootChest();

	private final ChestModel simpleChest = new ChestModel();

	private final ChestModel largeChest = new LargeChestModel();

	@Override
	public void render(TileEntitySkyrootChest entity, final double x, final double y, final double z, final float partialTicks, final int destroyStage)
	{
		GlStateManager.enableDepthTest();
		GlStateManager.depthFunc(515);
		GlStateManager.depthMask(true);

		BlockState state = entity.hasWorld() ? entity.getBlockState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);

		ChestType type = state.has(ChestBlock.TYPE) ? state.get(ChestBlock.TYPE) : ChestType.SINGLE;

		if (type != ChestType.LEFT)
		{
			boolean flag = type != ChestType.SINGLE;

			ChestModel model = this.prepareChestModel(entity, flag);

			if (destroyStage >= 0)
			{
				GlStateManager.matrixMode(5890);
				GlStateManager.pushMatrix();
				GlStateManager.scalef(flag ? 8.0F : 4.0F, 4.0F, 1.0F);
				GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
				GlStateManager.matrixMode(5888);
			}
			else
			{
				GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			}

			GlStateManager.pushMatrix();
			GlStateManager.enableRescaleNormal();
			GlStateManager.translatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
			GlStateManager.scalef(1.0F, -1.0F, -1.0F);

			float f = state.get(ChestBlock.FACING).getHorizontalAngle();

			if ((double) Math.abs(f) > 1.0E-5D)
			{
				GlStateManager.translatef(0.5F, 0.5F, 0.5F);
				GlStateManager.rotatef(f, 0.0F, 1.0F, 0.0F);
				GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
			}

			this.applyLidRotation(entity, partialTicks, model);

			model.renderAll();

			GlStateManager.disableRescaleNormal();
			GlStateManager.popMatrix();
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

			if (destroyStage >= 0)
			{
				GlStateManager.matrixMode(5890);
				GlStateManager.popMatrix();
				GlStateManager.matrixMode(5888);
			}

		}
	}

	private ChestModel prepareChestModel(TileEntitySkyrootChest te, boolean isDoubleChest)
	{
		ResourceLocation resourcelocation = isDoubleChest ? TEXTURE_DOUBLE : TEXTURE_SINGLE;

		this.bindTexture(resourcelocation);
		return isDoubleChest ? this.largeChest : this.simpleChest;
	}

	private void applyLidRotation(TileEntitySkyrootChest te, float partialTicks, ChestModel model)
	{
		float angle = ((IChestLid) te).getLidAngle(partialTicks);
		angle = 1.0F - angle;
		angle = 1.0F - angle * angle * angle;

		model.getLid().rotateAngleX = -(angle * ((float) Math.PI / 2F));
	}
}
