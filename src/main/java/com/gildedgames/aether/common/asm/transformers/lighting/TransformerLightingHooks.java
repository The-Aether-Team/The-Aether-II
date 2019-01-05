package com.gildedgames.aether.common.asm.transformers.lighting;

import com.gildedgames.aether.common.asm.util.ASMUtil;
import com.gildedgames.aether.common.asm.util.MethodSignature;
import com.gildedgames.aether.common.asm.util.MethodTransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

// Transforming our own classes?! You bet.
public class TransformerLightingHooks implements IClassTransformer
{
	private class GetNeighborLightChecksTransformer extends MethodTransformer
	{
		@Override
		public boolean transformMethod(ClassNode classNode, MethodNode methodNode)
		{
			InsnList injection = new InsnList();
			injection.add(new VarInsnNode(Opcodes.ALOAD, 0));
			injection.add(new FieldInsnNode(Opcodes.GETFIELD,
					"net/minecraft/world/chunk/Chunk",
					"neighborLightChecks",
					"[S"));
			injection.add(new InsnNode(Opcodes.ARETURN));

			methodNode.instructions.clear();
			methodNode.instructions.add(injection);

			return true;
		}

		@Override
		protected MethodSignature getMethodSignature()
		{
			return new MethodSignature("getNeighborLightChecks", "(Lnet/minecraft/world/chunk/Chunk;)[S");
		}
	}

	private class SetNeighborLightChecksTransformer extends MethodTransformer
	{
		@Override
		public boolean transformMethod(ClassNode classNode, MethodNode methodNode)
		{
			InsnList injection = new InsnList();
			injection.add(new VarInsnNode(Opcodes.ALOAD, 0));
			injection.add(new VarInsnNode(Opcodes.ALOAD, 1));
			injection.add(new FieldInsnNode(Opcodes.PUTFIELD, "net/minecraft/world/chunk/Chunk", "neighborLightChecks", "[S"));
			injection.add(new InsnNode(Opcodes.RETURN));

			methodNode.instructions.clear();
			methodNode.instructions.add(injection);

			return true;
		}

		@Override
		protected MethodSignature getMethodSignature()
		{
			return new MethodSignature("setNeighborLightChecks", "(Lnet/minecraft/world/chunk/Chunk;[S)V");
		}
	}

	private class GetCachedLightForTransformer extends MethodTransformer
	{
		@Override
		public boolean transformMethod(ClassNode classNode, MethodNode methodNode)
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

			methodNode.instructions.clear();
			methodNode.instructions.add(injection);

			return true;
		}

		@Override
		protected MethodSignature getMethodSignature()
		{
			return new MethodSignature("getCachedLightFor", "(Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/world/EnumSkyBlock;Lnet/minecraft/util/math/BlockPos;)I");
		}
	}

	private class GetLightingEngineTransformer extends MethodTransformer
	{
		@Override
		public boolean transformMethod(ClassNode classNode, MethodNode methodNode)
		{
			InsnList injection = new InsnList();
			injection.add(new VarInsnNode(Opcodes.ALOAD, 0));
			injection.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/world/World", "lightingEngine", "Lcom/gildedgames/aether/common/world/lighting/LightingEngine;"));
			injection.add(new InsnNode(Opcodes.ARETURN));

			methodNode.instructions.clear();
			methodNode.instructions.add(injection);

			return true;
		}

		@Override
		protected MethodSignature getMethodSignature()
		{
			return new MethodSignature("getLightingEngine", "(Lnet/minecraft/world/World;)Lcom/gildedgames/aether/common/world/lighting/LightingEngine;");
		}
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		ClassNode classNode = ASMUtil.readClass(basicClass);

		ASMUtil.transformClassMethods(classNode, new MethodTransformer[] {
				new GetNeighborLightChecksTransformer(),
				new SetNeighborLightChecksTransformer(),
				new GetCachedLightForTransformer(),
				new GetLightingEngineTransformer()
		});

		return ASMUtil.writeClass(classNode);
	}
}
