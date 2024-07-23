package com.aetherteam.aetherii.attachment.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class DroppedItemAttachment {
    private int ownerID;
    @Nullable
    private Entity owner;

    public static final Codec<DroppedItemAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("owner_id").forGetter(o -> o.owner.getId())
    ).apply(instance, DroppedItemAttachment::new));

    public DroppedItemAttachment() {

    }

    private DroppedItemAttachment(int ownerID) {
        this.ownerID = ownerID;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    /**
     * @return The owner {@link Entity} of the dropped item.
     */
    @Nullable
    public Entity getOwner(Level level) {
        if (this.owner == null) {
            this.owner = level.getEntity(this.ownerID);
        }
        return this.owner;
    }
}
