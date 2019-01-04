package com.gildedgames.aether.common.asm.transformers;

import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

// Transforming our own classes?! You bet.
public class TransformerLightingHooks implements IClassTransformer
{
	private final Logger logger = LogManager.getLogger(this);

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);

		boolean modifiedGetNeighborLightChecks = false;
		boolean modifiedSetNeighborLightChecks = false;

		boolean modifiedGetCachedLightFor = false;
		boolean modifiedGetLightingEngine = false;

		for (MethodNode methodNode : classNode.methods)
		{
			if (methodNode.name.equals("getNeighborLightChecks"))
			{
				InsnList injection = new InsnList();
				injection.add(new VarInsnNode(Opcodes.ALOAD, 0));
				injection.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/world/chunk/Chunk", "neighborLightChecks", "[S"));
				injection.add(new InsnNode(Opcodes.ARETURN));

				methodNode.instructions = injection;

				modifiedGetNeighborLightChecks = true;

				continue;
			}

			if (methodNode.name.equals("setNeighborLightChecks"))
			{
				InsnList injection = new InsnList();
				injection.add(new VarInsnNode(Opcodes.ALOAD, 0));
				injection.add(new VarInsnNode(Opcodes.ALOAD, 1));
				injection.add(new FieldInsnNode(Opcodes.PUTFIELD, "net/minecraft/world/chunk/Chunk", "neighborLightChecks", "[S"));
				injection.add(new InsnNode(Opcodes.RETURN));

				methodNode.instructions = injection;

				modifiedSetNeighborLightChecks = true;

				continue;
			}

			if (methodNode.name.equals("getCachedLightFor"))
			{
				InsnList injection = new InsnList();
				injection.add(new VarInsnNode(Opcodes.ALOAD, 0));
				injection.add(new VarInsnNode(Opcodes.ALOAD, 1));
				injection.add(new VarInsnNode(Opcodes.ALOAD, 2));
				injection.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
						"net/minecraft/world/chunk/Chunk",
						"getCachedLightFor",
						"(Lnet/minecraft/world/EnumSkyBlock;Lnet/minecraft/util/math/BlockPos;)I",
						false));
				injection.add(new InsnNode(Opcodes.IRETURN));

				methodNode.instructions = injection;

				modifiedGetCachedLightFor = true;

				continue;
			}

			if (methodNode.name.equals("getLightingEngine"))
			{
				InsnList injection = new InsnList();
				injection.add(new VarInsnNode(Opcodes.ALOAD, 0));
				injection.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/world/World", "lightingEngine", "Lcom/gildedgames/aether/api/world/lighting/ILightingEngine;"));
				injection.add(new InsnNode(Opcodes.ARETURN));

				methodNode.instructions = injection;

				modifiedGetLightingEngine = true;

				continue;
			}

			if (modifiedGetNeighborLightChecks && modifiedSetNeighborLightChecks && modifiedGetCachedLightFor && modifiedGetLightingEngine)
			{
				break;
			}
		}

		if (!modifiedGetNeighborLightChecks)
		{
			throw new RuntimeException("Couldn't find getNeighborLightChecks in LightingHooks");
		}

		if (!modifiedSetNeighborLightChecks)
		{
			throw new RuntimeException("Couldn't find setNeighborLightChecks in LightingHooks");
		}

		if (!modifiedGetCachedLightFor)
		{
			throw new RuntimeException("Couldn't find getCachedLightFor in LightingHooks");
		}

		if (!modifiedGetLightingEngine)
		{
			throw new RuntimeException("Couldn't find getLightingEngine in LightingHooks");
		}

		this.logger.info("Applied patch to LightingHooks successfully!");

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);

		return writer.toByteArray();
	}
}
