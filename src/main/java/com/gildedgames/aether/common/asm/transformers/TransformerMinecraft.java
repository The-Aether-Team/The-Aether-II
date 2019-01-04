package com.gildedgames.aether.common.asm.transformers;

import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class TransformerMinecraft implements IClassTransformer
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
			if (methodNode.name.equals("runTick") && methodNode.desc.equals("()V"))
			{
				Iterator<AbstractInsnNode> abstractInsnNodeIterator = methodNode.instructions.iterator();

				AbstractInsnNode lastEntry = null;

				while (abstractInsnNodeIterator.hasNext())
				{
					AbstractInsnNode abstractInsnNode = abstractInsnNodeIterator.next();

					if (abstractInsnNode.getOpcode() == Opcodes.ALOAD)
					{
						lastEntry = abstractInsnNode;
					}

					if (lastEntry != null && abstractInsnNode.getOpcode() == Opcodes.LDC)
					{
						LdcInsnNode ldcInsnNode = (LdcInsnNode) abstractInsnNode;

						if (ldcInsnNode.cst instanceof String && ldcInsnNode.cst.equals("levelRenderer"))
						{
							InsnList injection = new InsnList();
							injection.add(new VarInsnNode(Opcodes.ALOAD, 0));
							injection.add(new FieldInsnNode(Opcodes.GETFIELD,
									"net/minecraft/client/Minecraft",
									"profiler",
									"Lnet/minecraft/profiler/Profiler;"));
							injection.add(new LdcInsnNode("lighting"));
							injection.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
									"net/minecraft/profiler/Profiler",
									"endStartSection",
									"(Ljava/lang/String;)V",
									false));
							injection.add(new VarInsnNode(Opcodes.ALOAD, 0));
							injection.add(new FieldInsnNode(Opcodes.GETFIELD,
									"net/minecraft/client/Minecraft",
									"world",
									"Lnet/minecraft/client/multiplayer/WorldClient;"));
							injection.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
									"com/gildedgames/aether/common/world/lighting/LightingHooks",
									"onMinecraftTick",
									"(Lnet/minecraft/world/World;)V",
									false));

							methodNode.instructions.insert(lastEntry, injection);

							modified = true;

							break;
						}
					}
				}
			}

			if (modified)
			{
				break;
			}
		}

		if (!modified)
		{
			throw new RuntimeException("Couldn't find injection point in Minecraft");
		}

		this.logger.info("Applied patch to Minecraft successfully!");

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);

		return writer.toByteArray();
	}
}
