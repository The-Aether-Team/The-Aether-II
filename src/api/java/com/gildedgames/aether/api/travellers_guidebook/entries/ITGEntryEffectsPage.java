package com.gildedgames.aether.api.travellers_guidebook.entries;

import com.gildedgames.aether.api.player.IPlayerAether;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ITGEntryEffectsPage extends ITGEntryEffects
{

    @SideOnly(Side.CLIENT)
    ResourceLocation getDisplayTexture();

    @SideOnly(Side.CLIENT)
    ResourceLocation getSlotTexture();

    String getUnlocalizedDescription();

    String getLocalizedEffectName();

    /**
     * Whether or not the player has killed or fed the beast.
     * @param playerAether
     * @return Whether or not the player has killed or fed the beast.
     */
    boolean isUnderstood(IPlayerAether playerAether);

    /**
     * The complete overview of the bestiary page includes
     * the description of the creature as well as a visible
     * pixel art portrait of the creature.
     *
     * @return Whether or not the complete overview has been unlocked.
     */
    boolean hasUnlockedCompleteOverview(IPlayerAether playerAether);
}
