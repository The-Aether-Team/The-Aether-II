package com.gildedgames.aether.common.asm.transformers.lighting;

import com.gildedgames.aether.common.asm.util.ASMUtil;
import com.gildedgames.aether.common.asm.util.MethodSignature;
import com.gildedgames.aether.common.asm.util.MethodTransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class TransformerWorld implements IClassTransformer
{
	private class ConstructorTransformer extends MethodTransformer
	{
		@Override
		public boolean transformMethod(ClassNode classNode, MethodNode methodNode)
		{
			AbstractInsnNode injectionSite = ASMUtil.getFirstNode(methodNode, Opcodes.RETURN);

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
					"Lcom/gildedgames/aether/common/world/lighting/LightingEngine;"));

			methodNode.instructions.insertBefore(injectionSite, injection);

			return true;
		}

		@Override
		protected MethodSignature getMethodSignature()
		{
			return new MethodSignature("<init>", "(Lnet/minecraft/world/storage/ISaveHandler;Lnet/minecraft/world/storage/WorldInfo;Lnet/minecraft/world/WorldProvider;Lnet/minecraft/profiler/Profiler;Z)V");
		}
	}

	private class CheckLightForTransformer extends MethodTransformer
	{
		@Override
		public boolean transformMethod(ClassNode classNode, MethodNode methodNode)
		{
			methodNode.instructions.clear();

			InsnList injection = new InsnList();
			injection.add(new VarInsnNode(Opcodes.ALOAD, 0)); // 0: World (this)
			injection.add(new VarInsnNode(Opcodes.ALOAD, 1)); // 1: EnumSkyBlock
			injection.add(new VarInsnNode(Opcodes.ALOAD, 2)); // 2: BlockPos
			injection.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
					"com/gildedgames/aether/common/world/lighting/LightingHooks",
					"onWorldCheckLightFor",
					"(Lnet/minecraft/world/World;Lnet/minecraft/world/EnumSkyBlock;Lnet/minecraft/util/math/BlockPos;)Z",
					false));
			injection.add(new InsnNode(Opcodes.IRETURN));

			methodNode.instructions.add(injection);

			return true;
		}

		@Override
		protected MethodSignature getMethodSignature()
		{
			return new MethodSignature("checkLightFor", "(Lnet/minecraft/world/EnumSkyBlock;Lnet/minecraft/util/math/BlockPos;)Z");
		}
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		ClassNode classNode = ASMUtil.readClass(basicClass);

		// public final LightingEngine lightingEngine;
		classNode.fields.add(new FieldNode(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL,
				"lightingEngine",
				"Lcom/gildedgames/aether/common/world/lighting/LightingEngine;",
				null, null));

		ASMUtil.transformClassMethods(classNode, new MethodTransformer[] {
				new ConstructorTransformer(),
				new CheckLightForTransformer()
		});

		return ASMUtil.writeClass(classNode);
	}
}
