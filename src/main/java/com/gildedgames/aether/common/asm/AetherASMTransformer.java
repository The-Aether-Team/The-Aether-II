package com.gildedgames.aether.common.asm;

import com.gildedgames.aether.common.asm.transformers.*;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class AetherASMTransformer implements IClassTransformer
{
	private final Logger logger = LogManager.getLogger(this);

	private final HashMap<String, IClassTransformer> transformers = new HashMap<>();

	public AetherASMTransformer()
	{
		this.registerTransformerForNames(new TransformerChunkPacket(), "net.minecraft.network.play.server.SPacketChunkData");
		this.registerTransformerForNames(new TransformerAnvilChunkLoader(), "net.minecraft.world.chunk.storage.AnvilChunkLoader");
		this.registerTransformerForNames(new TransformerChunkProviderServer(), "net.minecraft.world.gen.ChunkProviderServer");
		this.registerTransformerForNames(new TransformerWorld(), "net.minecraft.world.World");
		this.registerTransformerForNames(new TransformerMinecraft(), "net.minecraft.client.Minecraft");
		this.registerTransformerForNames(new TransformerChunk(), "net.minecraft.world.chunk.Chunk");

		this.registerTransformerForNames(new TransformerLightingHooks(), "com.gildedgames.aether.common.world.lighting.LightingHooks");
	}

	private void registerTransformerForNames(IClassTransformer transformer, String... names)
	{
		for (String name : names)
		{
			this.transformers.put(name, transformer);
		}
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		IClassTransformer transformer = this.transformers.get(name);

		if (transformer == null)
		{
			return basicClass;
		}

		this.logger.info("Injecting! Transforming class {}", transformedName);

		try
		{
			return transformer.transform(name, transformedName, basicClass);
		}
		catch (Exception e)
		{
			this.logger.error("Caught exception while transforming class... this is not going to end well.", e);

			throw e;
		}
	}
}
