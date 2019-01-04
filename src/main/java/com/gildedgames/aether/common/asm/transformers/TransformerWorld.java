package com.gildedgames.aether.common.asm.transformers;

import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class TransformerWorld implements IClassTransformer
{
	private final Logger logger = LogManager.getLogger(this);

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);

		// Add lightingEngine field
		classNode.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL,
				"lightingEngine",
				"Lcom/gildedgames/aether/api/world/lighting/ILightingEngine;",
				null, null);

		boolean modifiedCheckLightFor = false;
		boolean modifiedConstructor = false;

		for (MethodNode methodNode : classNode.methods)
		{
			if (methodNode.name.equals("checkLightFor") && methodNode.desc.equals("(Lnet/minecraft/world/EnumSkyBlock;Lnet/minecraft/util/math/BlockPos;)Z"))
			{
				methodNode.instructions.clear();

				InsnList injection = new InsnList();
				injection.add(new VarInsnNode(Opcodes.ALOAD, 0));
				injection.add(new VarInsnNode(Opcodes.ALOAD, 1));
				injection.add(new VarInsnNode(Opcodes.ALOAD, 2));
				injection.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
						"com/gildedgames/aether/common/world/lighting/LightingHooks",
						"onWorldCheckLightFor",
						"(Lnet/minecraft/world/World;Lnet/minecraft/world/EnumSkyBlock;Lnet/minecraft/util/math/BlockPos;)Z",
						false));
				injection.add(new InsnNode(Opcodes.IRETURN));

				methodNode.instructions.add(injection);

				modifiedCheckLightFor = true;
			}
			else if (methodNode.name.equals("<init>") && methodNode.desc.equals("(Lnet/minecraft/world/storage/ISaveHandler;Lnet/minecraft/world/storage/WorldInfo;Lnet/minecraft/world/WorldProvider;Lnet/minecraft/profiler/Profiler;Z)V"))
			{
				Iterator<AbstractInsnNode> abstractInsnNodeIterator = methodNode.instructions.iterator();

				while (abstractInsnNodeIterator.hasNext())
				{
					AbstractInsnNode abstractInsnNode = abstractInsnNodeIterator.next();

					if (abstractInsnNode.getOpcode() == Opcodes.RETURN)
					{
						InsnList injection = new InsnList();
						injection.add(new VarInsnNode(Opcodes.ALOAD, 0));
						injection.add(new TypeInsnNode(Opcodes.NEW,
								"com/gildedgames/aether/common/world/lighting/LightingEngine"));
						injection.add(new InsnNode(Opcodes.DUP));
						injection.add(new VarInsnNode(Opcodes.ALOAD, 0));
						injection.add(new MethodInsnNode(Opcodes.INVOKESPECIAL,
								"com/gildedgames/aether/common/world/lighting/LightingEngine",
								"<init>",
								"(Lnet/minecraft/world/World;)V",
								false));
						injection.add(new FieldInsnNode(Opcodes.PUTFIELD,
								"net/minecraft/world/World",
								"lightingEngine",
								"Lcom/gildedgames/aether/api/world/lighting/ILightingEngine;"));

						methodNode.instructions.insertBefore(abstractInsnNode, injection);

						modifiedConstructor = true;

						break;
					}
				}
			}

			if (modifiedCheckLightFor && modifiedConstructor)
			{
				break;
			}
		}

		if (!modifiedCheckLightFor)
		{
			throw new RuntimeException("Couldn't patch checkLightFor in World");
		}

		if (!modifiedConstructor)
		{
			throw new RuntimeException("Couldn't patch constructor in World");
		}

		this.logger.info("Applied patch to World successfully!");

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);

		return writer.toByteArray();
	}
}
