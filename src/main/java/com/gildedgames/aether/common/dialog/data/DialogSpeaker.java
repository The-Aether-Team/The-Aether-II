package com.gildedgames.aether.common.dialog.data;

import com.gildedgames.aether.api.dialog.IDialogSpeaker;
import com.google.gson.annotations.SerializedName;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Optional;

public class DialogSpeaker implements IDialogSpeaker
{
    @SerializedName("name")
    private String name;

    @SerializedName("style")
    private String style;

    @Nonnull
    @Override
    public String getUnlocalizedName()
    {
        return this.name != null ? this.name : "";
    }

    @Override
    public Optional<ResourceLocation> getStyle()
    {
        return this.style != null ? Optional.of(new ResourceLocation(this.style)) : Optional.empty();
    }
}
