package com.gildedgames.aether.client.renderer.tiles.items;

import com.gildedgames.aether.client.models.entities.tile.ModelPresent;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.items.blocks.ItemBlockPresent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityPresentItemRenderer extends TileEntityItemStackRenderer
{
    public static final ResourceLocation[] boxTextures = new ResourceLocation[16];

    public static final ResourceLocation[] bowTextures = new ResourceLocation[16];

    public static final String[] colors = new String[] { "black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray",
            "pink", "lime", "yellow", "light_blue", "magenta", "orange", "white" };

    static
    {
        for (int i = 0; i < 16; i++)
        {
            boxTextures[i] = AetherCore.getResource("textures/tile_entities/present/present_box_" + colors[i] + ".png");
            bowTextures[i] = AetherCore.getResource("textures/tile_entities/present/present_ribbon_" + colors[i] + ".png");
        }
    }

    private final ModelPresent model = new ModelPresent();

    @Override
    public void renderByItem(ItemStack itemStackIn)
    {
        GlStateManager.pushMatrix();

        ItemBlockPresent.PresentData data = ItemBlockPresent.getData(itemStackIn);

        byte boxColor = data.getDye().getBoxColor();
        byte bowColor = data.getDye().getBowColor();

        // Sanitize dye colors to prevent rogue presents!
        boxColor = (boxColor >= 15 ? 15 : (boxColor < 0 ? 0 : boxColor));
        bowColor = (bowColor >= 15 ? 15 : (bowColor < 0 ? 0 : bowColor));

        GlStateManager.enableRescaleNormal();

        GlStateManager.translate(0.5f, 0.0f, 0.5f);

        GlStateManager.rotate(180f, 1.0f, 0f, 0.4f);
        GlStateManager.scale(1.5F, 1.5F, 1.5F);

        Minecraft.getMinecraft().getTextureManager().bindTexture(boxTextures[boxColor]);
        this.model.renderBox(0.0625F);

        Minecraft.getMinecraft().getTextureManager().bindTexture(bowTextures[bowColor]);
        this.model.renderBow(0.0625F);
        this.model.renderBox(0.0625F);

        GlStateManager.disableRescaleNormal();

        GlStateManager.popMatrix();
    }
}
