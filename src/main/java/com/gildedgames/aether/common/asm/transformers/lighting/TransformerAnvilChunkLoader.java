package com.gildedgames.aether.common.asm.transformers.lighting;

import net.minecraft.launchwrapper.IClassTransformer;

public class TransformerAnvilChunkLoader implements IClassTransformer
{
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{

		return basicClass;
	}
}
