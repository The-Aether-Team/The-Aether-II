package com.gildedgames.aether.common.dialog.data;

import com.gildedgames.aether.api.dialog.IDialogSlide;
import com.gildedgames.aether.api.dialog.IDialogSpeaker;
import com.google.gson.annotations.SerializedName;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DialogSpeaker implements IDialogSpeaker
{
    @SerializedName("name")
    private String name;

    @SerializedName("slides")
    private Map<String, IDialogSlide> slides;

    @Nonnull
    @Override
    public String getUnlocalizedName()
    {
        return this.name != null ? this.name : "";
    }

    @Override
    public Optional<Map<String, IDialogSlide>> getSlides()
    {
        return this.slides != null ? Optional.of(this.slides) : Optional.empty();
    }
}
