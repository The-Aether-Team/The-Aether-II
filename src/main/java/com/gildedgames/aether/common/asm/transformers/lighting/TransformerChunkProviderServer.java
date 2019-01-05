package com.gildedgames.aether.common.asm.transformers.lighting;

import com.gildedgames.aether.common.asm.util.ASMUtil;
import com.gildedgames.aether.common.asm.util.MethodSignature;
import com.gildedgames.aether.common.asm.util.MethodTransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class TransformerChunkProviderServer implements IClassTransformer
{
	private class SaveChunksTransformer extends MethodTransformer
	{
		@Override
		public boolean transformMethod(ClassNode classNode, MethodNode methodNode)
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

			return true;
		}

		@Override
		protected MethodSignature getMethodSignature()
		{
			return new MethodSignature("saveChunks", "(Z)Z");
		}
	}

	private class TickTransformer extends MethodTransformer
	{
		@Override
		public boolean transformMethod(ClassNode classNode, MethodNode methodNode)
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

					return true;
				}
			}

			return false;
		}

		@Override
		protected MethodSignature getMethodSignature()
		{
			return new MethodSignature("tick", "()Z");
		}
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		ClassNode classNode = ASMUtil.readClass(basicClass);

		ASMUtil.transformClassMethods(classNode, new MethodTransformer[] {
				new TickTransformer(),
				new SaveChunksTransformer()
		});

		return ASMUtil.writeClass(classNode);
	}
}
