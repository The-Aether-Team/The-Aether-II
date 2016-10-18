package com.gildedgames.aether.api.loot;

import net.minecraft.item.ItemStack;

import java.util.Random;

public interface Loot
{

	ItemStack create(Random random);

	ItemStack getCloningSource();

}
