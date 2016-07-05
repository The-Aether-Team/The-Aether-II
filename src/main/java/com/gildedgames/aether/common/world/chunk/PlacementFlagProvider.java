package com.gildedgames.aether.common.world.chunk;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.util.modules.chunk.api.hook.BlockBitFlagChunkHook;
import com.gildedgames.util.modules.chunk.api.hook.IChunkHookProvider;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class PlacementFlagProvider implements IChunkHookProvider<BlockBitFlagChunkHook>
{
	@Override
	public BlockBitFlagChunkHook createHook(World world, NBTTagCompound tag)
	{
		BlockBitFlagChunkHook data = new BlockBitFlagChunkHook();
		data.read(tag);

		return data;
	}

	@Override
	public ResourceLocation getID()
	{
		return AetherCore.getResource("placement_flags");
	}
}
