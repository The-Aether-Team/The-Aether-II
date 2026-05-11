package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.ItemReinforcement;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.api.styles.StyleDesign;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.Charms;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;
import java.util.Optional;

public class AetherIIItemReinforcements {
    public static void bootstrap(BootstrapContext<ItemReinforcement> context) {
        HolderGetter<Block> registrationLookup = context.lookup(Registries.BLOCK);

        register(context, AetherIIItems.SKYROOT_PICKAXE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 1)),
                        new ItemReinforcement.Add(DataComponents.MAX_DAMAGE, 50)
                ),
                new ItemReinforcement.Upgrade(
                        new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 2), new ItemStackTemplate(AetherIIItems.CORROBONITE_CRYSTAL, 1)),
                        new ItemReinforcement.Add(DataComponents.MAX_DAMAGE, 100)
                ),
                new ItemReinforcement.Upgrade(
                        new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 4), new ItemStackTemplate(AetherIIItems.CORROBONITE_CRYSTAL, 3)),
                        new ItemReinforcement.Add(DataComponents.MAX_DAMAGE, 150),
                        new ItemReinforcement.Set(DataComponents.TOOL, new Tool(List.of(
                                Tool.Rule.deniesDrops(registrationLookup.getOrThrow(BlockTags.INCORRECT_FOR_STONE_TOOL)),
                                Tool.Rule.minesAndDrops(registrationLookup.getOrThrow(BlockTags.MINEABLE_WITH_PICKAXE), 4.0F)
                        ), 1.0F, 1, true)),
                        new ItemReinforcement.Set(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, 1.0F + 1.0F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                                .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, -2.8F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                                .build()
                        ),
                        new ItemReinforcement.Set(AetherIIDataComponents.CHARMS.get(), new Charms(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE))),
                        new ItemReinforcement.Set(DataComponents.RARITY, AetherIIItems.AETHER_II_UPGRADED)
                )
        ));
        register(context, AetherIIItems.SKYROOT_AXE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 1)),
                        new ItemReinforcement.Add(DataComponents.MAX_DAMAGE, 50)
                ),
                new ItemReinforcement.Upgrade(
                        new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 2), new ItemStackTemplate(AetherIIItems.CORROBONITE_CRYSTAL, 1)),
                        new ItemReinforcement.Add(DataComponents.MAX_DAMAGE, 100)
                ),
                new ItemReinforcement.Upgrade(
                        new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 4), new ItemStackTemplate(AetherIIItems.CORROBONITE_CRYSTAL, 3)),
                        new ItemReinforcement.Add(DataComponents.MAX_DAMAGE, 150),
                        new ItemReinforcement.Set(DataComponents.TOOL, new Tool(List.of(
                                Tool.Rule.deniesDrops(registrationLookup.getOrThrow(BlockTags.INCORRECT_FOR_STONE_TOOL)),
                                Tool.Rule.minesAndDrops(registrationLookup.getOrThrow(BlockTags.MINEABLE_WITH_AXE), 4.0F)
                        ), 1.0F, 1, true)),
                        new ItemReinforcement.Set(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, 1.5F + 1.0F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                                .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, -3.2F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                                .build()
                        ),
                        new ItemReinforcement.Set(AetherIIDataComponents.CHARMS.get(), new Charms(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE))),
                        new ItemReinforcement.Set(DataComponents.RARITY, AetherIIItems.AETHER_II_UPGRADED)
                )
        ));
        register(context, AetherIIItems.SKYROOT_SHOVEL, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 1)),
                        new ItemReinforcement.Add(DataComponents.MAX_DAMAGE, 50)
                ),
                new ItemReinforcement.Upgrade(
                        new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 2), new ItemStackTemplate(AetherIIItems.CORROBONITE_CRYSTAL, 1)),
                        new ItemReinforcement.Add(DataComponents.MAX_DAMAGE, 100)
                ),
                new ItemReinforcement.Upgrade(
                        new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 4), new ItemStackTemplate(AetherIIItems.CORROBONITE_CRYSTAL, 3)),
                        new ItemReinforcement.Add(DataComponents.MAX_DAMAGE, 150),
                        new ItemReinforcement.Set(DataComponents.TOOL, new Tool(List.of(
                                Tool.Rule.deniesDrops(registrationLookup.getOrThrow(BlockTags.INCORRECT_FOR_STONE_TOOL)),
                                Tool.Rule.minesAndDrops(registrationLookup.getOrThrow(BlockTags.MINEABLE_WITH_SHOVEL), 4.0F)
                        ), 1.0F, 1, true)),
                        new ItemReinforcement.Set(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, 1.5F + 1.0F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                                .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, -3.0F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                                .build()
                        ),
                        new ItemReinforcement.Set(AetherIIDataComponents.CHARMS.get(), new Charms(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE))),
                        new ItemReinforcement.Set(DataComponents.RARITY, AetherIIItems.AETHER_II_UPGRADED)
                )
        ));
        register(context, AetherIIItems.SKYROOT_TROWEL, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 1)),
                        new ItemReinforcement.Add(DataComponents.MAX_DAMAGE, 50)
                ),
                new ItemReinforcement.Upgrade(
                        new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 2), new ItemStackTemplate(AetherIIItems.CORROBONITE_CRYSTAL, 1)),
                        new ItemReinforcement.Add(DataComponents.MAX_DAMAGE, 100)
                ),
                new ItemReinforcement.Upgrade(
                        new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 4), new ItemStackTemplate(AetherIIItems.CORROBONITE_CRYSTAL, 3)),
                        new ItemReinforcement.Add(DataComponents.MAX_DAMAGE, 150),
                        new ItemReinforcement.Set(DataComponents.TOOL, new Tool(List.of(
                                Tool.Rule.deniesDrops(registrationLookup.getOrThrow(BlockTags.INCORRECT_FOR_STONE_TOOL)),
                                Tool.Rule.minesAndDrops(registrationLookup.getOrThrow(BlockTags.MINEABLE_WITH_HOE), 4.0F)
                        ), 1.0F, 1, true)),
                        new ItemReinforcement.Set(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, 0.5F + 1.0F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                                .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, -2.5F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                                .build()
                        ),
                        new ItemReinforcement.Set(AetherIIDataComponents.CHARMS.get(), new Charms(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE))),
                        new ItemReinforcement.Set(DataComponents.RARITY, AetherIIItems.AETHER_II_UPGRADED)
                )
        ));
    }

    public static void register(BootstrapContext<ItemReinforcement> context, DeferredItem<?> key, ItemReinforcement itemReinforcement) {
        ItemReinforcement design = itemReinforcement;
        context.register(ResourceKey.create(AetherIIRegistries.ITEM_REINFORCEMENT, key.getId()), design);
    }

    public static Registry<ItemReinforcement> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(AetherIIRegistries.ITEM_REINFORCEMENT);
    }

    public static Optional<ItemReinforcement> get(RegistryAccess registryAccess, ItemStack stack) {
        return getRegistry(registryAccess).getOptional(stack.typeHolder().getKey().identifier());
    }
}
