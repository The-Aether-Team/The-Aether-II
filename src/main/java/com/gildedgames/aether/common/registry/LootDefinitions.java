package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.api.loot.Loot;
import com.gildedgames.aether.api.loot.LootPool;
import com.gildedgames.aether.api.loot.selectors.ChanceLoot;
import com.gildedgames.aether.api.loot.selectors.RangedLoot;
import com.gildedgames.aether.api.loot.selectors.SingleStackLoot;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.living.mobs.EntitySwet;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBoltType;
import net.minecraft.item.ItemStack;

public class LootDefinitions
{

	private LootDefinitions()
	{
	}

	public static final LootPool LABYRINTH_TRASH = new LootPool()
	{

		Loot[] loot = new Loot[]
				{
						new SingleStackLoot(new ItemStack(ItemsAether.shard_of_life)),
						new RangedLoot(new ItemStack(ItemsAether.icestone_poprocks), 2, 5),
						new RangedLoot(new ItemStack(ItemsAether.orange_lollipop), 1, 3),
						new RangedLoot(new ItemStack(ItemsAether.blueberry_lollipop), 1, 3),
						new RangedLoot(new ItemStack(ItemsAether.jelly_pumpkin), 1, 3),
						new RangedLoot(new ItemStack(ItemsAether.candy_corn), 3, 5),
						new RangedLoot(new ItemStack(ItemsAether.wrapped_chocolates), 1, 4),
						new RangedLoot(new ItemStack(ItemsAether.cocoatrice), 1, 2),
						new ChanceLoot(new ItemStack(ItemsAether.gummy_swet, 1, EntitySwet.Type.BLUE.ordinal()), 0.2F),
						new ChanceLoot(new ItemStack(ItemsAether.gummy_swet, 1, EntitySwet.Type.DARK.ordinal()), 0.2F),
						new ChanceLoot(new ItemStack(ItemsAether.gummy_swet, 1, EntitySwet.Type.LIGHT.ordinal()), 0.2F),
						new ChanceLoot(new ItemStack(ItemsAether.gummy_swet, 1, EntitySwet.Type.GOLDEN.ordinal()), 0.2F),
						new RangedLoot(new ItemStack(BlocksAether.ambrosium_torch), 3, 9),
						new ChanceLoot(new ItemStack(ItemsAether.skyroot_sword), 0.2F),
						new ChanceLoot(new ItemStack(ItemsAether.skyroot_shield), 0.2F),
						new RangedLoot(new ItemStack(ItemsAether.dart), 1, 6),
						new RangedLoot(new ItemStack(ItemsAether.bolt, 1, ItemBoltType.SKYROOT.ordinal()), 1, 6),
						new RangedLoot(new ItemStack(ItemsAether.bolt, 1, ItemBoltType.HOLYSTONE.ordinal()), 1, 6),
						new ChanceLoot(new ItemStack(ItemsAether.aerwhale_music_disc), 0.05F),
						new ChanceLoot(new ItemStack(ItemsAether.moa_music_disc), 0.05F),
						new ChanceLoot(new ItemStack(ItemsAether.labyrinth_music_disc), 0.05F),
						new ChanceLoot(new ItemStack(ItemsAether.valkyrie_music_disc), 0.05F),
						new ChanceLoot(new ItemStack(ItemsAether.chain_gloves), 0.05F),
						new RangedLoot(new ItemStack(ItemsAether.swet_jelly, 1, EntitySwet.Type.BLUE.ordinal()), 1, 4),
						new RangedLoot(new ItemStack(ItemsAether.swet_jelly, 1, EntitySwet.Type.DARK.ordinal()), 1, 4),
						new RangedLoot(new ItemStack(ItemsAether.swet_jelly, 1, EntitySwet.Type.LIGHT.ordinal()), 1, 4),
						new RangedLoot(new ItemStack(ItemsAether.swet_jelly, 1, EntitySwet.Type.GOLDEN.ordinal()), 1, 4),
						new ChanceLoot(new ItemStack(ItemsAether.irradiated_charm), 0.10F),
						new ChanceLoot(new ItemStack(ItemsAether.irradiated_neckwear), 0.05F),
						new ChanceLoot(new ItemStack(ItemsAether.irradiated_ring), 0.05F),
						new ChanceLoot(new ItemStack(ItemsAether.irradiated_tool), 0.05F),
						new ChanceLoot(new ItemStack(ItemsAether.irradiated_armor), 0.05F),
						new ChanceLoot(new ItemStack(ItemsAether.irradiated_sword), 0.05F),
						new ChanceLoot(new ItemStack(ItemsAether.irradiated_chunk), 0.15F)
				};

		@Override
		public Loot[] getPossibleLoot()
		{
			return this.loot;
		}

	};

}
