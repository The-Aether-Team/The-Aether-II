package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public record DamageTypeParticlePacket(int entityID, SimpleParticleType particleType) implements BasePacket {
    public static final ResourceLocation ID = new ResourceLocation(AetherII.MODID, "spawn_damage_type_particle");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID());
        buf.writeResourceLocation(BuiltInRegistries.PARTICLE_TYPE.getKey(this.particleType()));
    }

    public static DamageTypeParticlePacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        SimpleParticleType particleType = (SimpleParticleType) BuiltInRegistries.PARTICLE_TYPE.get(buf.readResourceLocation());
        return new DamageTypeParticlePacket(entityID, particleType);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity entity = Minecraft.getInstance().player.level().getEntity(this.entityID());
            if (entity != null) {
                Minecraft.getInstance().particleEngine.createTrackingEmitter(entity, this.particleType(), 1);
            }
        }
    }
}
