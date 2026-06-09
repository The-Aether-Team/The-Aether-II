package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.network.packet.serverbound.DiscardEntityPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
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
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CompanionItem extends Item {
    private final Holder<EntityType<?>> companionType;
    private final Holder<SoundEvent> sound;

    public CompanionItem(Holder<EntityType<?>> companionType, Holder<SoundEvent> sound, Item.Properties properties) {
        super(properties.stacksTo(1));
        this.companionType = companionType;
        this.sound = sound;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (interactionTarget.getType() == this.getCompanionType()
                && interactionTarget instanceof OwnableEntity owned && owned.getOwner() instanceof Player owner && owner.getUUID().equals(player.getUUID())
                && ((!interactionTarget.getData(AetherIIDataAttachments.COMPANION) && stack.get(AetherIIDataComponents.COMPANION_UUID) == null) || UUIDsMatch(stack, interactionTarget))) {
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), this.sound, SoundSource.NEUTRAL, 1.0F, 1.0F);
            stack.set(AetherIIDataComponents.COMPANION_UUID, interactionTarget.getUUID());
            player.setItemInHand(usedHand, stack);
            removeCompanion(interactionTarget, player, stack);
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

        if (player != null && companionUUID != null) {
            if (companionNBT != null) {
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), this.sound, SoundSource.NEUTRAL, 1.0F, 1.0F);
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
            } else {
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), this.sound, SoundSource.NEUTRAL, 1.0F, 1.0F);
                if (player.level() instanceof ServerLevel serverLevel) {
                    Entity companion = serverLevel.getEntity(companionUUID);
                    if (companion instanceof LivingEntity living) {
                        removeCompanion(living, player, stack);
                        return InteractionResult.SUCCESS_SERVER;
                    }
                }
            }
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        Player player = context.player();
        MutableComponent status = Component.translatable("aether_ii.tooltip.item.companion.status.empty");
        if (itemStack.has(AetherIIDataComponents.COMPANION_NBT)) {
            if (player != null && player.getCooldowns().isOnCooldown(itemStack)) {
                status = Component.translatable("aether_ii.tooltip.item.companion.status.recovering");
            } else {
                status = Component.translatable("aether_ii.tooltip.item.companion.status.stored");
            }
        } else if (itemStack.has(AetherIIDataComponents.COMPANION_UUID)) {
            status = Component.translatable("aether_ii.tooltip.item.companion.status.active");
        }
        MutableComponent combined = Component.translatable("aether_ii.tooltip.item.companion.status", status).withStyle(ChatFormatting.GRAY);
        builder.accept(combined);
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return itemStack.has(AetherIIDataComponents.COMPANION_UUID) && !itemStack.has(AetherIIDataComponents.COMPANION_NBT) || super.isFoil(itemStack);
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
                    removeCompanion(companion, player, inventoryStack);
                }
            }
        }
    }

    public static void entityDeath(LivingDeathEvent event) {
        LivingEntity living = event.getEntity();
        if (living instanceof OwnableEntity owned && owned.getOwner() instanceof Player owner) {
            ItemStack inventoryStack = getMatchingStack(owner, living);
            if (!inventoryStack.isEmpty()) {
                removeCompanion(living, owner, inventoryStack);
                if (living.level() instanceof ServerLevel serverLevel && serverLevel.getGameRules().get(GameRules.SHOW_DEATH_MESSAGES) && owner instanceof ServerPlayer serverPlayer) {
                    serverPlayer.sendSystemMessage(Component.translatable("death.attack.aether_ii.retreat", living.getDisplayName()));
                }
                event.setCanceled(true);
            }
        }
    }

    public static void removeCompanion(LivingEntity companion, Player owner, ItemStack stack) {
        try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(companion.problemPath(), AetherII.LOGGER)) {
            TagValueOutput value = TagValueOutput.createWithContext(reporter, companion.registryAccess());
            String id = companion.getEncodeId();
            if (id != null) {
                value.putString("id", id);
            }
            companion.saveWithoutId(value);
            CompoundTag tag = value.buildResult();
            stack.set(AetherIIDataComponents.COMPANION_NBT, tag);
            if (companion.isDeadOrDying()) {
                owner.getCooldowns().addCooldown(stack, 1000);
            }
            companion.discard();
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
