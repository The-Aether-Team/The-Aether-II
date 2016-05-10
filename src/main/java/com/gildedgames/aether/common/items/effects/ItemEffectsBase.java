package com.gildedgames.aether.common.items.effects;

import com.gildedgames.aether.common.entities.effects.EffectInstance;
import com.gildedgames.aether.common.entities.effects.EffectProcessor;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface ItemEffectsBase
{
	List<Pair<EffectProcessor, EffectInstance>> getEffectPairs();
}
