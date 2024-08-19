package com.aetherteam.aetherii.event.hooks.attachment;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.inventory.AetherIISlotHandling;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.living.DamageSystemAttachment;
import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.data.resources.maps.DamageInfliction;
import com.aetherteam.aetherii.data.resources.maps.DamageResistance;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDataMaps;
import com.aetherteam.aetherii.entity.AetherIIAttributes;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShieldItem;
import com.aetherteam.aetherii.item.equipment.armor.GlovesItem;
import com.aetherteam.aetherii.item.equipment.weapons.abilities.UniqueDamage;
import com.aetherteam.aetherii.network.packet.clientbound.DamageTypeParticlePacket;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import io.wispforest.accessories.api.AccessoriesAPI;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
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
            Entity sourceEntity = source.getDirectEntity();
            ItemStack sourceStack = ItemStack.EMPTY;

            if (sourceEntity instanceof LivingEntity livingSource) {
                sourceStack = livingSource.getMainHandItem();
            } else if (sourceEntity instanceof Projectile && sourceEntity instanceof ItemSupplier itemSupplier) {
                sourceStack = itemSupplier.getItem();
            }

            if (!sourceStack.isEmpty()) {
                DamageInfliction infliction = BuiltInRegistries.ITEM.wrapAsHolder(sourceStack.getItem()).getData(AetherIIDataMaps.DAMAGE_INFLICTION);
                DamageResistance resistance = BuiltInRegistries.ENTITY_TYPE.wrapAsHolder(target.getType()).getData(AetherIIDataMaps.DAMAGE_RESISTANCE);

                if (resistance != null) {
                    double slashDefense = resistance.slashValue();
                    double impactDefense = resistance.impactValue();
                    double pierceDefense = resistance.pierceValue();

                    if (infliction != null) {
                        double slashDamage =  infliction.slashValue();
                        double impactDamage = infliction.impactValue();
                        double pierceDamage = infliction.pierceValue();

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
                        double defense = Math.max(slashDefense, Math.max(impactDefense, pierceDefense));
                        damage = Math.max(damage - defense, 1.0F);
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

    public static void addAbilityTooltips(Player player, ItemStack stack, List<Component> components) {
        for (int i = 1; i <= 5; i++) {
            String string = stack.getDescriptionId() + "." + AetherII.MODID + ".ability.tooltip." + i;
            if (I18n.exists(string)) {
                if (player != null && (stack.getItem() instanceof ArmorItem || stack.getItem() instanceof GlovesItem) && Component.translatable(string).getString().contains("%s")) {
                    Holder<ArmorMaterial> material = null;
                    if (stack.getItem() instanceof ArmorItem armorItem) {
                        material = armorItem.getMaterial();
                    } else if (stack.getItem() instanceof GlovesItem glovesItem) {
                        material = glovesItem.getMaterial();
                    }
                    if (material != null) {
                        int currentEquipmentCount = EquipmentUtil.getArmorCount(player, material);
                        Component component;
                        if (currentEquipmentCount >= 3) {
                            component = Component.literal("3/3").withStyle(ChatFormatting.WHITE);
                        } else {
                            component = Component.literal(currentEquipmentCount + "/3").withStyle(ChatFormatting.GRAY);
                        }
                        components.add(i, Component.translatable(string, component));
                    }
                } else {
                    components.add(i, Component.translatable(string));
                }
            }
        }
    }

    public static void addDamageTypeTooltips(Player player, List<Component> components, ItemStack stack) {
        DamageInfliction infliction = BuiltInRegistries.ITEM.wrapAsHolder(stack.getItem()).getData(AetherIIDataMaps.DAMAGE_INFLICTION);
        if (player != null && infliction != null) {
            int position = components.size();
            Component damageText = Component.translatable(Attributes.ATTACK_DAMAGE.value().getDescriptionId());
            for (int i = 0; i < position; i++) {
                Component component = components.get(i);
                if (component.getString().contains(damageText.getString())) {
                    position = i + 1;
                    break;
                }
            }
            double slashDamage =  infliction.slashValue();
            double impactDamage = infliction.impactValue();
            double pierceDamage = infliction.pierceValue();

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
        DamageInfliction infliction = BuiltInRegistries.ITEM.wrapAsHolder(stack.getItem()).getData(AetherIIDataMaps.DAMAGE_INFLICTION);
        if (player != null && infliction != null) {
            RegistryAccess registryAccess = player.level().registryAccess();
            if (stack.getItem() instanceof UniqueDamage uniqueDamage) {
                double slashDamage =  infliction.slashValue() - 1;
                double impactDamage = infliction.impactValue() - 1;
                double pierceDamage = infliction.pierceValue() - 1;

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

    public static void addShieldTooltips(List<Component> components, ItemStack stack) {
        if (stack.getItem() instanceof TieredShieldItem) {
            int useTooltip = components.size() - 1;
            int attributeTooltip = components.size() - 1;

            Component useText = Component.translatable("item.modifiers." + EquipmentSlotGroup.HAND.getSerializedName());
            Component attributeText = Component.translatable(AetherIIAttributes.SHIELD_STAMINA_REDUCTION.value().getDescriptionId());

            for (int i = components.size() - 1; i >= 0; i--) {
                Component component = components.get(i);
                if (component.getString().contains(useText.getString())) {
                    useTooltip = i;
                }
                if (component.getString().contains(attributeText.getString())) {
                    attributeTooltip = i;
                }
            }
            int value = 0;
            for (ItemAttributeModifiers.Entry entry : stack.getAttributeModifiers().modifiers()) {
                if (entry.modifier().is(TieredShieldItem.BASE_SHIELD_STAMINA_REDUCTION_ID)) {
                    value = (int) ((entry.modifier().amount() / DamageSystemAttachment.MAX_SHIELD_STAMINA) * 100);
                }
            }
            components.remove(useTooltip);
            components.add(useTooltip, Component.translatable("aether_ii.tooltip.item.modifiers.blocking").withStyle(ChatFormatting.GRAY));
            components.remove(attributeTooltip);
            components.add(attributeTooltip, CommonComponents.space().append(Component.translatable("attribute.modifier.equals.0", value + "%", Component.translatable(AetherIIAttributes.SHIELD_STAMINA_REDUCTION.value().getDescriptionId())).withStyle(AetherIIItems.WEAPON_TOOLTIP_COLOR)));
        }
    }

    public static void addGloveTooltips(Player player, List<Component> components, ItemStack stack) {
        if (stack.getItem() instanceof GlovesItem) {
            int attributeTooltip = components.size() - 1;

            Component attributeText = Component.translatable(AetherIIAttributes.SHIELD_STAMINA_RESTORATION.value().getDescriptionId());

            for (int i = components.size() - 1; i >= 0; i--) {
                Component component = components.get(i);
                if (component.getString().contains(attributeText.getString())) {
                    attributeTooltip = i;
                }
            }

            int value = 0;
            for (AttributeModifier entry : AccessoriesAPI.getAttributeModifiers(stack, player, AetherIISlotHandling.getHandwearSlotType().slotName(), 0).getAttributeModifiers(false).values()) {
                if (entry.id().getPath().contains(GlovesItem.BASE_GLOVES_STAMINA_RESTORATION_ID.getNamespace())) {
                    value = (int) ((entry.amount() / DamageSystemAttachment.MAX_SHIELD_STAMINA) * 100);
                }
            }
            components.remove(attributeTooltip);
            components.add(attributeTooltip, Component.empty().append(Component.translatable("attribute.modifier.equals.0", "+" + value + "%", Component.translatable(AetherIIAttributes.SHIELD_STAMINA_RESTORATION.value().getDescriptionId())).withStyle(AetherIIItems.WEAPON_TOOLTIP_COLOR)));
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
            if (source.getEntity() != null && source.getEntity().getType().is(AetherIITags.Entities.AETHER_MOBS)) {
                DamageSystemAttachment attachment = player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM);
                int rate = DamageSystemAttachment.MAX_SHIELD_STAMINA / 2; //todo balance
                if (entity.getUseItem().getItem() instanceof TieredShieldItem) {
                    rate = (int) player.getAttributeValue(AetherIIAttributes.SHIELD_STAMINA_REDUCTION);
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
                    if (!player.isBlocking()) {
                        int restore = (int) player.getAttributeValue(AetherIIAttributes.SHIELD_STAMINA_RESTORATION);
                        attachment.setSynched(player.getId(), INBTSynchable.Direction.CLIENT, "setShieldStamina", Math.min(500, attachment.getShieldStamina() + restore));
                    }
                }
            }
        }
    }
}
