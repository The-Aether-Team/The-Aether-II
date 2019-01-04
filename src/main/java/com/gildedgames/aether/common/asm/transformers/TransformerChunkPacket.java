package com.gildedgames.aether.common.asm.transformers;

import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class TransformerChunkPacket implements IClassTransformer
{
	private final Logger logger = LogManager.getLogger(this);

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);

		boolean modified = false;

		for (MethodNode methodNode : classNode.methods)
		{
			if (methodNode.name.equals("<init>") && methodNode.desc.equals("(Lnet/minecraft/world/chunk/Chunk;I)V"))
			{
				Iterator<AbstractInsnNode> instructionIterator = methodNode.instructions.iterator();

				AbstractInsnNode found = null;

				while (instructionIterator.hasNext())
				{
					AbstractInsnNode insnNode = instructionIterator.next();

					if (insnNode.getOpcode() == Opcodes.INVOKESPECIAL)
					{
						MethodInsnNode invokeDynamicInsnNode = (MethodInsnNode) insnNode;

						if (invokeDynamicInsnNode.owner.equals("java/lang/Object"))
						{
							found = insnNode;

							break;
						}
					}
				}

				if (found != null)
				{
					InsnList injection = new InsnList();
					injection.add(new VarInsnNode(Opcodes.ALOAD, 1));
					injection.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/gildedgames/aether/common/world/lighting/LightingHooks", "onChunkPacketCreated", "(Lnet/minecraft/world/chunk/Chunk;)V", false));

					methodNode.instructions.insert(found, injection);

					modified = true;

					break;
				}
			}
		}

		if (!modified)
		{
			throw new RuntimeException("Couldn't find Chunk constructor in SPacketChunkData");
		}

		this.logger.info("Applied patch to SPacketChunkData successfully!");

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);

		return writer.toByteArray();
	}
}
