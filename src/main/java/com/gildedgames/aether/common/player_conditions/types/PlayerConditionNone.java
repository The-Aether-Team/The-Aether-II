package com.gildedgames.aether.common.player_conditions.types;

import com.gildedgames.aether.common.player_conditions.PlayerConditionBase;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraftforge.common.MinecraftForge;

import java.lang.reflect.Type;

public class PlayerConditionNone extends PlayerConditionBase
{
    public PlayerConditionNone()
    {

    }

    @Override
    public void onTracked()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onUntracked()
    {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public static class Deserializer implements JsonDeserializer<PlayerConditionNone>
    {
        @Override
        public PlayerConditionNone deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
                throws JsonParseException
        {
            return new PlayerConditionNone();
        }
    }
}
