package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.network.packet.serverbound.DiscardEntityPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.UUID;
import java.util.function.Supplier;

public class CompanionItem extends Item {
    private final Holder<EntityType<?>> companionType;

    public CompanionItem(Holder<EntityType<?>> companionType, Item.Properties properties) {
        super(properties);
        this.companionType = companionType;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (interactionTarget.getType() == this.getCompanionType()
                && interactionTarget instanceof OwnableEntity owned && owned.getOwner() instanceof Player owner && owner.getUUID().equals(player.getUUID())
                && ((!interactionTarget.getData(AetherIIDataAttachments.COMPANION) && stack.get(AetherIIDataComponents.COMPANION_UUID) == null) || UUIDsMatch(stack, interactionTarget))) {
            stack.set(AetherIIDataComponents.COMPANION_UUID, interactionTarget.getUUID());
            player.setItemInHand(usedHand, stack);
            interactionTarget.discard();
            return InteractionResult.SUCCESS_SERVER;
        }
        return super.interactLivingEntity(stack, player, interactionTarget, usedHand);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        BlockPos pos = context.getClickedPos().above();

        UUID companionUUID = stack.get(AetherIIDataComponents.COMPANION_UUID);
        CompoundTag companionNBT = stack.get(AetherIIDataComponents.COMPANION_NBT);

        if (player != null && companionUUID != null && companionNBT != null) {
            if (player.level() instanceof ServerLevel serverLevel && serverLevel.getEntity(companionUUID) == null) {
                try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(player.problemPath(), AetherII.LOGGER)) {
                    ValueInput value = TagValueInput.create(reporter, player.registryAccess(), companionNBT);
                    EntityType.create(value, serverLevel, EntitySpawnReason.MOB_SUMMONED).ifPresent((entity) -> {
                        if (entity instanceof LivingEntity living && living.getHealth() <= 0) {
                            living.setHealth(living.getMaxHealth());
                            living.removeAllEffects();
                            living.clearFire();
                            living.clearFreeze();
                        }
                        entity.snapTo(pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, 0.0F, 0.0F);
                        serverLevel.addFreshEntityWithPassengers(entity);
                        entity.setData(AetherIIDataAttachments.COMPANION, true);
                        stack.remove(AetherIIDataComponents.COMPANION_NBT);
                    });
                    return InteractionResult.SUCCESS_SERVER;
                }
            }
        }
        return super.useOn(context);
    }

    public static void entityPostTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        if (entity instanceof OwnableEntity owned && owned.getOwner() instanceof Player owner && entity.getData(AetherIIDataAttachments.COMPANION)) {
            if (owner.level().isClientSide()) {
                if (getMatchingStack(owner, entity).isEmpty()) {
                    ClientPacketDistributor.sendToServer(new DiscardEntityPacket(entity.getId()));
                }
            }
        }
    }

    public static void entityLeaveLevel(EntityLeaveLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player player) {
            InventoryMenu menu = player.inventoryMenu;
            for (ItemStack inventoryStack : menu.getItems()) {
                UUID thisUUID = inventoryStack.get(AetherIIDataComponents.COMPANION_UUID);
                if (thisUUID != null && player.level().getEntity(thisUUID) instanceof LivingEntity companion) {
                    companion.discard();
                }
            }
        } else {
            if (entity instanceof LivingEntity living && entity instanceof OwnableEntity owned && owned.getOwner() instanceof Player owner) {
                ItemStack inventoryStack = getMatchingStack(owner, entity);
                if (!inventoryStack.isEmpty()) {
                    try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(entity.problemPath(), AetherII.LOGGER)) {
                        TagValueOutput value = TagValueOutput.createWithContext(reporter, entity.registryAccess());
                        String id = entity.getEncodeId();
                        if (id != null) {
                            value.putString("id", id);
                        }
                        entity.saveWithoutId(value);
                        CompoundTag tag = value.buildResult();
                        inventoryStack.set(AetherIIDataComponents.COMPANION_NBT, tag);
                        if (living.isDeadOrDying()) {
                            owner.getCooldowns().addCooldown(inventoryStack, 1000);
                        }
                    }
                }
            }
        }
    }

    public static void entityDeath(LivingDeathEvent event) {
        LivingEntity living = event.getEntity();
        if (living instanceof OwnableEntity owned && owned.getOwner() instanceof Player owner) {
            ItemStack inventoryStack = getMatchingStack(owner, living);
            if (!inventoryStack.isEmpty()) {
                living.discard();
                if (living.level() instanceof ServerLevel serverLevel && serverLevel.getGameRules().get(GameRules.SHOW_DEATH_MESSAGES) && owner instanceof ServerPlayer serverPlayer) {
                    serverPlayer.sendSystemMessage(Component.translatable("death.attack.aether_ii.retreat", living.getDisplayName()));
                }
                event.setCanceled(true);
            }
        }
    }

    private static boolean UUIDsMatch(ItemStack stack, Entity entity) {
        UUID thisUUID = stack.get(AetherIIDataComponents.COMPANION_UUID);
        if (thisUUID != null) {
            return thisUUID.equals(entity.getUUID());
        }
        return false;
    }

    private static ItemStack getMatchingStack(Player player, Entity entity) {
        InventoryMenu menu = player.inventoryMenu;
        ItemStack carriedStack = menu.getCarried();
        if (UUIDsMatch(carriedStack, entity)) {
            return carriedStack;
        }
        for (ItemStack inventoryStack : menu.getItems()) {
            if (UUIDsMatch(inventoryStack, entity)) {
                return inventoryStack;
            }
        }
        return ItemStack.EMPTY;
    }

    public EntityType<?> getCompanionType() {
        return this.companionType.value();
    }
}
