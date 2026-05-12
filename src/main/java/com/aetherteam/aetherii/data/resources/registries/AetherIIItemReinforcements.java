package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.api.ItemReinforcement;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.Charms;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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

public class AetherIIItemReinforcements {
    private static ItemReinforcement.Cost COST_TIER_1_MATERIAL_1 = new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 1));
    private static ItemReinforcement.Cost COST_TIER_2_MATERIAL_1 = new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 2), new ItemStackTemplate(AetherIIItems.CORROBONITE_CRYSTAL, 1));
    private static ItemReinforcement.Cost COST_TIER_3_MATERIAL_1 = new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 4), new ItemStackTemplate(AetherIIItems.CORROBONITE_CRYSTAL, 3));

    private static ItemReinforcement.Cost COST_TIER_1_MATERIAL_2 = new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 2));
    private static ItemReinforcement.Cost COST_TIER_2_MATERIAL_2 = new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 3), new ItemStackTemplate(AetherIIItems.CORROBONITE_CRYSTAL, 1));
    private static ItemReinforcement.Cost COST_TIER_3_MATERIAL_2 = new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 5), new ItemStackTemplate(AetherIIItems.CORROBONITE_CRYSTAL, 3));

    private static ItemReinforcement.Cost COST_TIER_1_MATERIAL_3 = new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 3));
    private static ItemReinforcement.Cost COST_TIER_2_MATERIAL_3 = new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 4), new ItemStackTemplate(AetherIIItems.CORROBONITE_CRYSTAL, 1));
    private static ItemReinforcement.Cost COST_TIER_3_MATERIAL_3 = new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 6), new ItemStackTemplate(AetherIIItems.CORROBONITE_CRYSTAL, 3));

    private static ItemReinforcement.Cost COST_TIER_4 = new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 6), new ItemStackTemplate(AetherIIItems.CORROBONITE_CRYSTAL, 4));

    public static void bootstrap(BootstrapContext<ItemReinforcement> context) {
        HolderGetter<Block> blockLookup = context.lookup(Registries.BLOCK);

        register(context, AetherIIItems.SKYROOT_PICKAXE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE))
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        toolComponent(blockLookup.getOrThrow(BlockTags.INCORRECT_FOR_STONE_TOOL), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_PICKAXE), 4.0F),
                        attributesComponent(1.0F, 1.0F, -2.8F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.SKYROOT_AXE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE))
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        toolComponent(blockLookup.getOrThrow(BlockTags.INCORRECT_FOR_STONE_TOOL), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_AXE), 4.0F),
                        attributesComponent(1.5F, 1.0F, -3.2F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.SKYROOT_SHOVEL, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE))
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        toolComponent(blockLookup.getOrThrow(BlockTags.INCORRECT_FOR_STONE_TOOL), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_SHOVEL), 4.0F),
                        attributesComponent(1.5F, 1.0F, -3.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.SKYROOT_TROWEL, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE))
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        toolComponent(blockLookup.getOrThrow(BlockTags.INCORRECT_FOR_STONE_TOOL), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_HOE), 4.0F),
                        attributesComponent(0.5F, 1.0F, -2.5F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));

        register(context, AetherIIItems.HOLYSTONE_PICKAXE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE))
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        toolComponent(blockLookup.getOrThrow(BlockTags.INCORRECT_FOR_IRON_TOOL), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_PICKAXE), 6.0F),
                        attributesComponent(1.0F, 2.0F, -2.8F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.HOLYSTONE_AXE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE))
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        toolComponent(blockLookup.getOrThrow(BlockTags.INCORRECT_FOR_IRON_TOOL), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_AXE), 6.0F),
                        attributesComponent(1.5F, 2.0F, -3.2F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.HOLYSTONE_SHOVEL, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE))
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        toolComponent(blockLookup.getOrThrow(BlockTags.INCORRECT_FOR_IRON_TOOL), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_SHOVEL), 6.0F),
                        attributesComponent(1.5F, 2.0F, -3.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.HOLYSTONE_TROWEL, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE))
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        toolComponent(blockLookup.getOrThrow(BlockTags.INCORRECT_FOR_IRON_TOOL), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_HOE), 6.0F),
                        attributesComponent(0.5F, 2.0F, -2.5F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));

        register(context, AetherIIItems.ZANITE_PICKAXE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE))
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        toolComponent(blockLookup.getOrThrow(BlockTags.INCORRECT_FOR_DIAMOND_TOOL), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_PICKAXE), 8.0F),
                        attributesComponent(1.0F, 3.0F, -2.8F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.ZANITE_AXE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE))
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        toolComponent(blockLookup.getOrThrow(BlockTags.INCORRECT_FOR_DIAMOND_TOOL), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_AXE), 8.0F),
                        attributesComponent(1.5F, 3.0F, -3.2F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.ZANITE_SHOVEL, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE))
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        toolComponent(blockLookup.getOrThrow(BlockTags.INCORRECT_FOR_DIAMOND_TOOL), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_SHOVEL), 8.0F),
                        attributesComponent(1.5F, 3.0F, -3.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.ZANITE_TROWEL, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE))
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        toolComponent(blockLookup.getOrThrow(BlockTags.INCORRECT_FOR_DIAMOND_TOOL), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_HOE), 8.0F),
                        attributesComponent(0.5F, 3.0F, -2.5F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));

        register(context, AetherIIItems.ARKENIUM_PICKAXE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.ONE)
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        tierComponent(ReinforcementTier.THIRD)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.TWO))
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_4,
                        durabilityComponent(150),
                        toolComponent(blockLookup.getOrThrow(BlockTags.INCORRECT_FOR_DIAMOND_TOOL), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_PICKAXE), 8.0F),
                        attributesComponent(1.0F, 3.0F, -2.8F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.TWO)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.FOURTH)
                )
        ));
        register(context, AetherIIItems.ARKENIUM_AXE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.ONE)
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        tierComponent(ReinforcementTier.THIRD)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.TWO))
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_4,
                        durabilityComponent(150),
                        toolComponent(blockLookup.getOrThrow(BlockTags.INCORRECT_FOR_DIAMOND_TOOL), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_AXE), 8.0F),
                        attributesComponent(1.5F, 3.0F, -3.2F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.TWO)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.FOURTH)
                )
        ));
        register(context, AetherIIItems.ARKENIUM_SHOVEL, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.ONE)
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        tierComponent(ReinforcementTier.THIRD)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.TWO))
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_4,
                        durabilityComponent(150),
                        toolComponent(blockLookup.getOrThrow(BlockTags.INCORRECT_FOR_DIAMOND_TOOL), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_SHOVEL), 8.0F),
                        attributesComponent(1.5F, 3.0F, -3.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.TWO)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.FOURTH)
                )
        ));
        register(context, AetherIIItems.ARKENIUM_TROWEL, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.ONE)
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        tierComponent(ReinforcementTier.THIRD)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.TWO))
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_4,
                        durabilityComponent(150),
                        toolComponent(blockLookup.getOrThrow(BlockTags.INCORRECT_FOR_DIAMOND_TOOL), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_HOE), 8.0F),
                        attributesComponent(0.5F, 3.0F, -2.5F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.TWO)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.FOURTH)
                )
        ));

        register(context, AetherIIItems.GRAVITITE_PICKAXE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_3,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_3,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE))
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_3,
                        durabilityComponent(150),
                        toolComponent(blockLookup.getOrThrow(BlockTags.INCORRECT_FOR_NETHERITE_TOOL), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_PICKAXE), 9.0F),
                        attributesComponent(1.0F, 4.0F, -2.8F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.GRAVITITE_AXE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_3,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_3,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE))
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_3,
                        durabilityComponent(150),
                        toolComponent(blockLookup.getOrThrow(BlockTags.INCORRECT_FOR_NETHERITE_TOOL), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_AXE), 9.0F),
                        attributesComponent(1.5F, 4.0F, -3.2F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.GRAVITITE_SHOVEL, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_3,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_3,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE))
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_3,
                        durabilityComponent(150),
                        toolComponent(blockLookup.getOrThrow(BlockTags.INCORRECT_FOR_NETHERITE_TOOL), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_SHOVEL), 9.0F),
                        attributesComponent(1.5F, 4.0F, -3.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.GRAVITITE_TROWEL, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_3,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_3,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE))
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_3,
                        durabilityComponent(150),
                        toolComponent(blockLookup.getOrThrow(BlockTags.INCORRECT_FOR_NETHERITE_TOOL), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_HOE), 9.0F),
                        attributesComponent(0.5F, 4.0F, -2.5F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
    }

    private static MutableComponent toolTierTooltip() {
        return Component.translatable("gui.aether_ii.arkenium_forge.tooltip.tier").withStyle(ChatFormatting.GRAY);
    }

    public static MutableComponent charmTooltip(int amount, Charms.Tier tier) {
        if (amount > 1) {
            return Component.translatable("gui.aether_ii.arkenium_forge.tooltip.charms", Component.literal(String.valueOf(amount)), Charms.createCharmTierComponent(tier)).withStyle(ChatFormatting.GRAY);
        } else {
            return Component.translatable("gui.aether_ii.arkenium_forge.tooltip.charm", Component.literal(String.valueOf(amount)), Charms.createCharmTierComponent(tier)).withStyle(ChatFormatting.GRAY);
        }
    }

    public static MutableComponent durabilityTooltip(int value) {
        return Component.translatable("gui.aether_ii.arkenium_forge.tooltip.durability", Component.literal(String.valueOf(value))).withStyle(ChatFormatting.GRAY);
    }

    private static ItemReinforcement.Add durabilityComponent(int amount) {
        return new ItemReinforcement.Add(DataComponents.MAX_DAMAGE, amount);
    }

    private static ItemReinforcement.Set toolComponent(HolderSet<Block> incorrectBlocksForDrops, HolderSet<Block> minesEfficiently, float speed) {
        return new ItemReinforcement.Set(DataComponents.TOOL, new Tool(List.of(
                Tool.Rule.deniesDrops(incorrectBlocksForDrops),
                Tool.Rule.minesAndDrops(minesEfficiently, speed)
        ), 1.0F, 1, true));
    }

    private static ItemReinforcement.Set attributesComponent(float attackDamageBaseline, float attackDamageBonus, float attackSpeedBaseline) {
        return new ItemReinforcement.Set(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, attackDamageBaseline + attackDamageBonus, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeedBaseline, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build()
        );
    }

    private static ItemReinforcement.Set charmsComponent(Charms.CharmHolder charmHolder) {
        return new ItemReinforcement.Set(AetherIIDataComponents.CHARMS.get(), new Charms(charmHolder));
    }

    private static ItemReinforcement.Set rarityComponent() {
        return new ItemReinforcement.Set(DataComponents.RARITY, AetherIIItems.AETHER_II_UPGRADED);
    }

    private static ItemReinforcement.Set tierComponent(ReinforcementTier tier) {
        return new ItemReinforcement.Set(AetherIIDataComponents.REINFORCEMENT_TIER.get(), tier);
    }

    public static void register(BootstrapContext<ItemReinforcement> context, DeferredItem<?> key, ItemReinforcement itemReinforcement) {
        ItemReinforcement design = itemReinforcement;
        context.register(ResourceKey.create(AetherIIRegistries.ITEM_REINFORCEMENT, key.getId()), design);
    }

    public static Registry<ItemReinforcement> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(AetherIIRegistries.ITEM_REINFORCEMENT);
    }

    public static ItemReinforcement get(RegistryAccess registryAccess, ItemStack stack) {
        return getRegistry(registryAccess).getOptional(stack.typeHolder().getKey().identifier()).orElse(null);
    }
}
