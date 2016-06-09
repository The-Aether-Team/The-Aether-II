package com.gildedgames.aether.client.renderer.tile_entities;

import com.gildedgames.aether.client.models.entities.tile.ModelLabyrinthDoor;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.tile_entities.TileEntityLabyrinthDoor;
import com.gildedgames.aether.common.tile_entities.TileEntityLabyrinthTotem;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityLabyrinthDoorRenderer extends TileEntitySpecialRenderer<TileEntityLabyrinthDoor>
{
	private static final ResourceLocation TEXTURE_DOOR = AetherCore.getResource("textures/tile_entities/labyrinth_door.png"),
				TEXTURE_DOOR_GLOW = AetherCore.getResource("textures/tile_entities/labyrinth_door_glow.png");

	private static final ModelLabyrinthDoor MODEL = new ModelLabyrinthDoor();


	@Override
	public void renderTileEntityAt(TileEntityLabyrinthDoor door, double d, double d1, double d2, float f, int destroyStage)
	{
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		GL11.glTranslatef((float) d + 0.5F, (float) d1 + 1.5F, (float) d2 + 0.5F);
		GL11.glRotatef(180f, 1f, 0f, 1f);


		switch (door.getFacing())
		{
		case NORTH:
		{
			GL11.glRotatef(270f, 0f, 1f, 0f);
			break;
		}
		case EAST:
		{
			break;
		}
		case SOUTH:
		{
			GL11.glRotatef(90f, 0f, 1f, 0f);
			break;
		}
		case WEST:
		{
			GL11.glRotatef(180f, 0f, 1f, 0f);
			break;
		}
		}

		this.bindTexture(TEXTURE_DOOR);

		this.MODEL.renderAll(0.0625F);

		this.bindTexture(TEXTURE_DOOR_GLOW);

		float var4 = 1.0F;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
		GL11.glTranslatef(0F, 0F, -0.001F);

		char var5 = 61680;
		int var6 = var5 % 65536;
		int var7 = var5 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) var6 / 1.0F, (float) var7 / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, var4);

		this.MODEL.renderAll(0.0625F);

		GL11.glDisable(GL11.GL_BLEND);

		GL11.glPopMatrix();
	}

}
