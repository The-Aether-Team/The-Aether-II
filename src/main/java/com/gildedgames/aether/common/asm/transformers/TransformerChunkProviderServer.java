package com.gildedgames.aether.common.asm.transformers;

import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class TransformerChunkProviderServer implements IClassTransformer
{
	private final Logger logger = LogManager.getLogger(this);

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);

		boolean modifiedSaveChunks = false, modifiedTick = false;

		for (MethodNode methodNode : classNode.methods)
		{
			if (methodNode.name.equals("saveChunks") && methodNode.desc.equals("(Z)Z"))
			{
				InsnList injection = new InsnList();
				injection.add(new VarInsnNode(Opcodes.ALOAD, 0));
				injection.add(new FieldInsnNode(Opcodes.GETFIELD,
						"net/minecraft/world/gen/ChunkProviderServer",
						"world",
						"Lnet/minecraft/world/WorldServer;"));
				injection.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
						"com/gildedgames/aether/common/world/lighting/LightingHooks",
						"onChunkProviderSaveChunks", "(Lnet/minecraft/world/World;)V",
						false));

				methodNode.instructions.insert(methodNode.instructions.getFirst(), injection);

				modifiedSaveChunks = true;

				continue;
			}

			if (methodNode.name.equals("tick") && methodNode.desc.equals("()Z"))
			{
				Iterator<AbstractInsnNode> abstractInsnNodeIterator = methodNode.instructions.iterator();

				while (abstractInsnNodeIterator.hasNext())
				{
					AbstractInsnNode abstractInsnNode = abstractInsnNodeIterator.next();

					if (abstractInsnNode.getOpcode() == Opcodes.IFNE)
					{
						InsnList injection = new InsnList();
						injection.add(new VarInsnNode(Opcodes.ALOAD, 0));
						injection.add(new FieldInsnNode(Opcodes.GETFIELD,
								"net/minecraft/world/gen/ChunkProviderServer",
								"world",
								"Lnet/minecraft/world/WorldServer;"));
						injection.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
								"com/gildedgames/aether/common/world/lighting/LightingHooks",
								"onChunkProviderSaveChunks",
								"(Lnet/minecraft/world/World;)V",
								false));

						methodNode.instructions.insert(abstractInsnNode, injection);

						modifiedTick = true;

						break;
					}
				}

				continue;
			}

			if (modifiedSaveChunks && modifiedTick)
			{
				break;
			}
		}

		if (!modifiedSaveChunks)
		{
			throw new RuntimeException("Couldn't find saveChunks in ChunkProviderServer");
		}

		if (!modifiedTick)
		{
			throw new RuntimeException("Couldn't find injection site in ChunkProviderServer#tick");
		}

		this.logger.info("Applied patch to ChunkProviderServer successfully!");

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);

		return writer.toByteArray();
	}
}
