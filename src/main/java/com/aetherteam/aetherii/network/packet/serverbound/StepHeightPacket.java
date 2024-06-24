package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.passive.MountableAnimal;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Called by mounts to sync their step height modifier to the server. This fixes a movement bug where step height occasionally would not work otherwise.
 */
public record StepHeightPacket(int entityID) implements CustomPacketPayload {
    public static final Type<StepHeightPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "sync_step_height"));

    public static final StreamCodec<RegistryFriendlyByteBuf, StepHeightPacket> STREAM_CODEC = CustomPacketPayload.codec(
            StepHeightPacket::write,
            StepHeightPacket::decode);

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeInt(this.entityID());
    }

    public static StepHeightPacket decode(RegistryFriendlyByteBuf buf) {
        int entityID = buf.readInt();
        return new StepHeightPacket(entityID);
    }

    @Override
    public Type<StepHeightPacket> type() {
        return TYPE;
    }

    public static void execute(StepHeightPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity.level().getEntity(payload.entityID()) instanceof MountableAnimal mountableAnimal) {
            AttributeInstance stepHeight = mountableAnimal.getAttribute(Attributes.STEP_HEIGHT);
            if (stepHeight != null) {
                if (stepHeight.hasModifier(mountableAnimal.getDefaultStepHeightModifier().id())) {
                    stepHeight.removeModifier(mountableAnimal.getDefaultStepHeightModifier().id());
                }
                if (!stepHeight.hasModifier(mountableAnimal.getMountStepHeightModifier().id())) {
                    stepHeight.addTransientModifier(mountableAnimal.getMountStepHeightModifier());
                }
            }
        }
    }
}
