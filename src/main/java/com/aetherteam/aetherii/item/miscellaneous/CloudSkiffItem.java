package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.entity.vehicle.CloudSkiff;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class CloudSkiffItem extends BoatItem {
    private final EntityType<? extends CloudSkiff> entityType;

    public CloudSkiffItem(EntityType<? extends CloudSkiff> entityType, Item.Properties properties) {
        super(entityType, properties);
        this.entityType = entityType;
    }

    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        HitResult result = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
        if (result.getType() == HitResult.Type.MISS) {
            return InteractionResult.PASS;
        } else {
            Vec3 viewVec = player.getViewVector(1.0F);
            List<Entity> entities = level.getEntities(player, player.getBoundingBox().expandTowards(viewVec.scale(5.0F)).inflate(1.0F), EntitySelector.CAN_BE_PICKED);
            if (!entities.isEmpty()) {
                Vec3 eyePos = player.getEyePosition();
                for (Entity entity : entities) {
                    AABB bounds = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if (bounds.contains(eyePos)) {
                        return InteractionResult.PASS;
                    }
                }
            }

            if (result.getType() == HitResult.Type.BLOCK) {
                CloudSkiff skiff = this.getBoat(level, result, stack, player);
                if (skiff == null) {
                    return InteractionResult.FAIL;
                } else {
                    skiff.setYRot(player.getYRot() - 90);
                    if (!level.noCollision(skiff, skiff.getBoundingBox())) {
                        return InteractionResult.FAIL;
                    } else {
                        if (!level.isClientSide()) {
                            level.addFreshEntity(skiff);
                            level.gameEvent(player, GameEvent.ENTITY_PLACE, result.getLocation());
                            stack.consume(1, player);
                        }
                        player.awardStat(Stats.ITEM_USED.get(this));
                        return InteractionResult.SUCCESS;
                    }
                }
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    @Nullable
    private CloudSkiff getBoat(Level level, HitResult hitResult, ItemStack stack, Player player) {
        CloudSkiff skiff = this.entityType.create(level, EntitySpawnReason.SPAWN_ITEM_USE);
        if (skiff != null) {
            Vec3 hitPos = hitResult.getLocation();
            skiff.setInitialPos(hitPos.x, hitPos.y, hitPos.z);
            skiff.setAnimateUnfold(true);
            if (level instanceof ServerLevel serverlevel) {
                EntityType.createDefaultStackConfig(serverlevel, stack, player).accept(skiff);
            }
        }
        return skiff;
    }
}
