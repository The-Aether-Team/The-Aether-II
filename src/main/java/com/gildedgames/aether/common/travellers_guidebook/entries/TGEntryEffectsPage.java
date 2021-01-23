package com.gildedgames.aether.common.travellers_guidebook.entries;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.player.conditions.IPlayerCondition;
import com.gildedgames.aether.api.travellers_guidebook.entries.ITGEntryEffectsPage;
import com.gildedgames.aether.common.travellers_guidebook.TGEntryBase;
import com.google.common.collect.Lists;
import com.google.gson.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;
import java.util.Collection;

public class TGEntryEffectsPage extends TGEntryBase implements ITGEntryEffectsPage
{
    private final String effectName;

    private final String tag;

    private final ResourceLocation displayTexture, slotTexture;

    private final String unlocalizedDescription;

    protected TGEntryEffectsPage(final String tag, final String effectName, final String unlocalizedDescription,
                                  final ResourceLocation displayTexture, final ResourceLocation slotTexture)
    {
        this.tag = tag;

        this.effectName = effectName;

        this.unlocalizedDescription = unlocalizedDescription;

        this.displayTexture = displayTexture;
        this.slotTexture = slotTexture;
    }

    @Override
    public Collection<IPlayerCondition> providePlayerConditions()
    {
        return Lists.newArrayList();
    }

    @Override
    public String getEffectName()
    {
        return this.effectName;
    }

    @Override
    public ResourceLocation getDisplayTexture()
    {
        return this.displayTexture;
    }

    @Override
    public ResourceLocation getSlotTexture()
    {
        return this.slotTexture;
    }

    @Override
    public String getUnlocalizedDescription()
    {
        return this.unlocalizedDescription;
    }

    @Override
    public String getLocalizedEffectName()
    {
        return I18n.format("effect.aether." + this.getEffectName());
    }

    @Override
    public boolean isUnderstood(final IPlayerAether playerAether)
    {
        return true;
    }

    @Override
    public boolean hasUnlockedCompleteOverview(final IPlayerAether playerAether)
    {
        return this.isUnderstood(playerAether); // TODO: Need to have conditions for each move
    }

    @Override
    public String getTag()
    {
        return this.tag;
    }

    public static class Deserializer implements JsonDeserializer<TGEntryEffectsPage>
    {
        @Override
        public TGEntryEffectsPage deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
                throws JsonParseException
        {
            final JsonObject obj = json.getAsJsonObject();

            return new TGEntryEffectsPage(obj.get("tag").getAsString(),
                    obj.get("effectName").getAsString(),
                    obj.get("description").getAsString(),
                    new ResourceLocation(obj.get("displayTexture").getAsString()),
                    new ResourceLocation(obj.get("slotTexture").getAsString()));
        }
    }
}
