package com.aetherteam.aetherii.client.gui.component.inventory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.screen.inventory.ArkeniumForgeScreen;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class ReinforcementTierButton extends ImageButton {
    private static final WidgetSprites TIER_BUTTON_SPRITE = new WidgetSprites(
            Identifier.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/tier_button"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/tier_button_disabled"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/tier_button_selected"));
    private static final Identifier TIER_COMPLETED_SPRITE = Identifier.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/tier_completed");
    private final ArkeniumForgeScreen parentScreen;
    private final ReinforcementTier tier;

    public ReinforcementTierButton(ArkeniumForgeScreen parentScreen, ReinforcementTier tier, int x, int y, int width, int height, OnPress onPress) {
        super(x, y, width, height, TIER_BUTTON_SPRITE, onPress);
        this.parentScreen = parentScreen;
        this.tier = tier;
    }

    @Override
    public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractContents(guiGraphics, mouseX, mouseY, partialTick);
        Identifier tierSprite = ArkeniumForgeScreen.TIER_LOCATIONS.get(this.getTier().getTierNumber() - 1);

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, tierSprite, this.getX() + 2, this.getY() + 2, 16, 16);

        if (this.isCompleted()) {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, TIER_COMPLETED_SPRITE, this.getX() + 2, this.getY() + 2, 16, 16);
        }
    }

    @Override
    public boolean isActive() {
        if (this.isCompleted() || (Minecraft.getInstance().level != null && this.parentScreen.getMenu().getTierForMaterials(Minecraft.getInstance().level.registryAccess()) < this.tier.getTierNumber())) {
            return false;
        }
        return super.isActive();
    }

    public boolean isCompleted() {
        ItemStack input = this.parentScreen.getMenu().getInput();
        ReinforcementTier reinforcementTier = input.get(AetherIIDataComponents.REINFORCEMENT_TIER);
        return reinforcementTier != null && this.getTier().getTierNumber() <= reinforcementTier.getTierNumber();
    }

    public ReinforcementTier getTier() {
        return this.tier;
    }
}
