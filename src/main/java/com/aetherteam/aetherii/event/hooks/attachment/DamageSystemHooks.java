package com.aetherteam.aetherii.event.hooks.attachment;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.living.DamageSystemAttachment;
import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageInflictions;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageResistances;
import com.aetherteam.aetherii.entity.AetherIIAttributes;
import com.aetherteam.aetherii.item.AetherIIDataComponents;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.ReinforcementTier;
import com.aetherteam.aetherii.item.combat.AetherIIShieldItem;
import com.aetherteam.aetherii.item.combat.abilities.UniqueDamage;
import com.aetherteam.aetherii.network.packet.clientbound.DamageTypeParticlePacket;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.network.PacketDistributor;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

public class DamageSystemHooks {
    public static void trackCriticalHitValue(Player player, float value) {
        player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM.get()).setCriticalDamageModifier(value);
    }

    public static float getDamageTypeModifiedValue(Entity target, DamageSource source, double damage) {
        if (source.typeHolder().is(AetherIITags.DamageTypes.TYPED)) {
            RegistryAccess registryAccess = target.level().registryAccess();
            Entity sourceEntity = source.getDirectEntity();
            ItemStack sourceStack = ItemStack.EMPTY;

            if (sourceEntity instanceof LivingEntity livingSource) {
                sourceStack = livingSource.getMainHandItem();
            } else if (sourceEntity instanceof Projectile && sourceEntity instanceof ItemSupplier itemSupplier) {
                sourceStack = itemSupplier.getItem();
            }

            if (!sourceStack.isEmpty()) {
                double slashDamage = AetherIIDamageInflictions.getSlashDamage(registryAccess, sourceStack);
                double impactDamage = AetherIIDamageInflictions.getImpactDamage(registryAccess, sourceStack);
                double pierceDamage = AetherIIDamageInflictions.getPierceDamage(registryAccess, sourceStack);

                double slashDefense = AetherIIDamageResistances.getSlashDefense(registryAccess, target);
                double impactDefense = AetherIIDamageResistances.getImpactDefense(registryAccess, target);
                double pierceDefense = AetherIIDamageResistances.getPierceDefense(registryAccess, target);

                if (AetherIIDamageResistances.hasEntity(registryAccess, target)) {
                    if (AetherIIDamageInflictions.hasItem(registryAccess, sourceStack)) {
                        if (sourceStack.getItem() instanceof UniqueDamage uniqueDamage) {
                            Triple<Double, Double, Double> damages = uniqueDamage.getUniqueDamage(sourceStack, slashDamage, impactDamage, pierceDamage);
                            slashDamage += damages.getLeft();
                            impactDamage += damages.getMiddle();
                            pierceDamage += damages.getRight();
                        }

                        createSoundsAndParticles(sourceEntity, target, slashDamage, slashDefense, AetherIIParticleTypes.SLASH_ATTACK.get(), AetherIISoundEvents.PLAYER_SLASH_DAMAGE_CORRECT.get(), AetherIISoundEvents.PLAYER_SLASH_DAMAGE_INCORRECT.get());
                        createSoundsAndParticles(sourceEntity, target, impactDamage, impactDefense, AetherIIParticleTypes.IMPACT_ATTACK.get(), AetherIISoundEvents.PLAYER_IMPACT_DAMAGE_CORRECT.get(), AetherIISoundEvents.PLAYER_IMPACT_DAMAGE_INCORRECT.get());
                        createSoundsAndParticles(sourceEntity, target, pierceDamage, pierceDefense, AetherIIParticleTypes.PIERCE_ATTACK.get(), AetherIISoundEvents.PLAYER_PIERCE_DAMAGE_CORRECT.get(), AetherIISoundEvents.PLAYER_PIERCE_DAMAGE_INCORRECT.get());

                        double slashCalculation = slashDamage > 0.0 ? Math.max(slashDamage - slashDefense, 0.0) : 0.0;
                        double impactCalculation = impactDamage > 0.0 ? Math.max(impactDamage - impactDefense, 0.0) : 0.0;
                        double pierceCalculation = pierceDamage > 0.0 ? Math.max(pierceDamage - pierceDefense, 0.0) : 0.0;

                        damage = Math.max(slashCalculation + impactCalculation + pierceCalculation, 1.0);

                        if (sourceEntity instanceof Player player) {
                            damage *= player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM).getCriticalDamageModifier();
                            damage *= player.getAttackStrengthScale(0.5F);

                            player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM).setCriticalDamageModifier(1.0F);
                        }
                    } else {
                        double resistance = Math.max(slashDefense, Math.max(impactDefense, pierceDefense));
                        damage = Math.max(damage - resistance, 1.0F);
                    }
                }
            }
        }
        return (float) damage;
    }

    private static void createSoundsAndParticles(Entity source, Entity target, double damage, double defense, SimpleParticleType particleType, SoundEvent correct, SoundEvent incorrect) {
        if (damage > 0) {
            if (defense > 0) {
                source.level().playSound(null, source.getX(), source.getY(), source.getZ(), incorrect, source.getSoundSource(), 1.0F, 1.0F);
            } else if (defense < 0) {
                if (source.level() instanceof ServerLevel serverLevel) {
                    PacketDistributor.sendToPlayersNear(serverLevel, null, source.getX(), source.getY(), source.getZ(), 15,  new DamageTypeParticlePacket(target.getId(), particleType));
                }
                source.level().playSound(null, source.getX(), source.getY(), source.getZ(), correct, source.getSoundSource(), 1.0F, 1.0F);
            }
        }
    }

    public static void addAbilityTooltips(ItemStack stack, List<Component> components) {
        for (int i = 1; i <= 5; i++) {
            String string = stack.getDescriptionId() + "." + AetherII.MODID + ".ability.tooltip." + i;
            if (I18n.exists(string)) {
                components.add(i, Component.translatable(string));
            }
        }
    }

    public static void addDamageTypeTooltips(Player player, List<Component> components, ItemStack stack) {
        if (player != null) {
            RegistryAccess registryAccess = player.level().registryAccess();

            int position = components.size();
            Component damageText = Component.translatable(Attributes.ATTACK_DAMAGE.value().getDescriptionId());
            for (int i = 0; i < position; i++) {
                Component component = components.get(i);
                if (component.getString().contains(damageText.getString())) {
                    position = i + 1;
                    break;
                }
            }
            double slashDamage = AetherIIDamageInflictions.getSlashDamage(registryAccess, stack);
            double impactDamage = AetherIIDamageInflictions.getImpactDamage(registryAccess, stack);
            double pierceDamage = AetherIIDamageInflictions.getPierceDamage(registryAccess, stack);

            addDamageTypeTooltip(components, position, slashDamage, "slash");
            addDamageTypeTooltip(components, position, impactDamage, "impact");
            addDamageTypeTooltip(components, position, pierceDamage, "pierce");
        }
    }

    private static void addDamageTypeTooltip(List<Component> components, int position, double value, String name) {
        if (value > 0.0) {
            components.remove(position - 1);
            components.add(position, CommonComponents.space().append(Component.translatable("attribute.modifier.equals.0", ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(value), Component.translatable("aether_ii.tooltip.item.damage." + name)).withStyle(AetherIIItems.WEAPON_TOOLTIP_COLOR)));
        }
    }

    public static void addBonusDamageTypeTooltips(Player player, List<Component> components, ItemStack stack) {
        if (player != null) {
            RegistryAccess registryAccess = player.level().registryAccess();
            if (stack.getItem() instanceof UniqueDamage uniqueDamage) {
                double slashDamage = AetherIIDamageInflictions.getSlashDamage(registryAccess, stack) - 1;
                double impactDamage = AetherIIDamageInflictions.getImpactDamage(registryAccess, stack) - 1;
                double pierceDamage = AetherIIDamageInflictions.getPierceDamage(registryAccess, stack) - 1;

                Triple<Double, Double, Double> damages = uniqueDamage.getUniqueDamage(stack, slashDamage, impactDamage, pierceDamage);
                slashDamage = damages.getLeft();
                impactDamage = damages.getMiddle();
                pierceDamage = damages.getRight();

                addBonusDamageTypeTooltip(components, slashDamage, "slash");
                addBonusDamageTypeTooltip(components, impactDamage, "impact");
                addBonusDamageTypeTooltip(components, pierceDamage, "pierce");
            }
        }
    }

    private static void addBonusDamageTypeTooltip(List<Component> components, double value, String name) {
        if (value > 0.0) {
            int removePosition = components.size() - 1;
            int addPosition = components.size() - 1;
            Component damageText = Component.translatable(Attributes.ATTACK_DAMAGE.value().getDescriptionId());
            Component damageTypeText = Component.translatable("aether_ii.tooltip.item.damage." + name);
            for (int i = components.size() - 1; i >= 0; i--) {
                Component component = components.get(i);
                if (component.getString().contains(damageText.getString())) {
                    removePosition = i;
                }
                if (component.getString().contains(damageTypeText.getString())) {
                    addPosition = i + 1;
                }
            }
            components.remove(removePosition);
            components.add(addPosition, Component.literal("").append(Component.translatable("attribute.modifier.plus.0", ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(value), damageTypeText).withStyle(ChatFormatting.BLUE)));
        }
    }

    public static void addReinforcingTooltip(ItemStack stack, List<Component> components) {
        ReinforcementTier tier = stack.get(AetherIIDataComponents.REINFORCEMENT_TIER);
        if (tier != null) {
            int position = 1;
            String text = "ability.tooltip";
            for (int i = 1; i < components.size(); i++) {
                Component component = components.get(i);
                if (component.toString().contains(text)) {
                    position = i + 1;
                    break;
                }
            }
            components.add(position, Component.literal("Reinforcement ").append(Component.translatable("enchantment.level." + tier.getTier())).withColor(14408667));
        }
    }

    public static void buildUpShieldStun(LivingEntity entity, DamageSource source) {
        if (entity instanceof Player player && player.getUseItem().is(Tags.Items.TOOLS_SHIELD)) {
            if (source.getEntity() != null) { //todo check for aether hostile mobs only or something idk
                DamageSystemAttachment attachment = player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM);
                int rate = DamageSystemAttachment.MAX_SHIELD_STAMINA / 2; //todo balance
                if (entity.getUseItem().getItem() instanceof AetherIIShieldItem shield) {
                    rate = (int) player.getAttribute(AetherIIAttributes.SHIELD_STAMINA_RESTORATION).getValue();
                }
                attachment.setSynched(player.getId(), INBTSynchable.Direction.CLIENT, "setShieldStamina", Math.max(0, attachment.getShieldStamina() - rate));
                if (attachment.getShieldStamina() <= 0) {
                    player.level().registryAccess().registryOrThrow(Registries.ITEM).getTagOrEmpty(Tags.Items.TOOLS_SHIELD).forEach((item) -> player.getCooldowns().addCooldown(item.value(), 300));
                    player.stopUsingItem();
                }
            }
        }
    }

    public static void restoreShieldStamina(Player player) {
        if (!player.level().isClientSide()) {
            DamageSystemAttachment attachment = player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM);
            if (player.tickCount % 5 == 0) {
                if (attachment.getShieldStamina() < DamageSystemAttachment.MAX_SHIELD_STAMINA && attachment.getShieldStamina() > 0) { //todo balance
                    int restore = (int) player.getAttributeValue(AetherIIAttributes.SHIELD_STAMINA_RESTORATION);
                    if (player.isBlocking()) {
                        restore /= 4;
                    }
                    attachment.setSynched(player.getId(), INBTSynchable.Direction.CLIENT, "setShieldStamina", Math.min(500, attachment.getShieldStamina() + restore));
                }
            }
        }
    }
}
