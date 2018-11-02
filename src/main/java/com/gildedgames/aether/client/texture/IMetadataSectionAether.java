package com.gildedgames.aether.client.texture;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSectionSerializer;

import java.lang.reflect.Type;

public interface IMetadataSectionAether extends IMetadataSection
{
    String NAME = "aether";

    int getGlowAmount();

    class MetadataSerializer implements IMetadataSectionSerializer<IMetadataSectionAether>
    {
        @Override
        public String getSectionName()
        {
            return NAME;
        }

        @Override
        public IMetadataSectionAether deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            if (json != null && json.isJsonObject())
            {
                JsonObject obj = json.getAsJsonObject();
                if (obj.has("glow"))
                {
                    int glow = obj.get("glow").getAsInt();
                    return () -> glow;
                }
            }
            return null;
        }
    }
}
