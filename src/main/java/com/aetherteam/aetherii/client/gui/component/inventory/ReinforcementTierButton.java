package com.aetherteam.aetherii.client.gui.component.inventory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.screen.inventory.ArkeniumForgeScreen;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import com.aetherteam.aetherii.client.gui.component.AetherIIImageButton;
import com.aetherteam.aetherii.client.gui.component.AetherIIWidgetSprites;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ReinforcementTierButton extends AetherIIImageButton {
    private static final AetherIIWidgetSprites TIER_BUTTON_SPRITE = new AetherIIWidgetSprites(
            new ResourceLocation(AetherII.MODID, "container/arkenium_forge/tier_button"),
            new ResourceLocation(AetherII.MODID, "container/arkenium_forge/tier_button_disabled"),
            new ResourceLocation(AetherII.MODID, "container/arkenium_forge/tier_button_selected"));
    private static final ResourceLocation TIER_COMPLETED_SPRITE = new ResourceLocation(AetherII.MODID, "container/arkenium_forge/tier_completed");
    private final ArkeniumForgeScreen parentScreen;
    private final ReinforcementTier tier;

    public ReinforcementTierButton(ArkeniumForgeScreen parentScreen, ReinforcementTier tier, int x, int y, int width, int height, OnPress onPress) {
        super(x, y, width, height, TIER_BUTTON_SPRITE, onPress);
        this.parentScreen = parentScreen;
        this.tier = tier;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        ResourceLocation tierSprite = ArkeniumForgeScreen.TIER_LOCATIONS.get(this.getTier().getTierNumber() - 1);

        com.aetherteam.aetherii.client.gui.AetherIIGuiGraphics.blitSprite(guiGraphics, tierSprite, this.getX() + 2, this.getY() + 2, 16, 16);

        if (this.isCompleted()) {
            com.aetherteam.aetherii.client.gui.AetherIIGuiGraphics.blitSprite(guiGraphics, TIER_COMPLETED_SPRITE, this.getX() + 2, this.getY() + 2, 16, 16);
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
        ReinforcementTier reinforcementTier = AetherIIDataComponents.get(input, AetherIIDataComponents.REINFORCEMENT_TIER);
        return reinforcementTier != null && this.getTier().getTierNumber() <= reinforcementTier.getTierNumber();
    }

    public ReinforcementTier getTier() {
        return this.tier;
    }
}


