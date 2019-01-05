package com.gildedgames.aether.common.asm.transformers.lighting;

import com.gildedgames.aether.common.asm.util.ASMUtil;
import com.gildedgames.aether.common.asm.util.MethodSignature;
import com.gildedgames.aether.common.asm.util.MethodTransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class TransformerChunkPacket implements IClassTransformer
{
	private class ConstructorTransformer extends MethodTransformer
	{
		@Override
		public boolean transformMethod(ClassNode classNode, MethodNode methodNode)
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

				return true;
			}

			return false;
		}

		@Override
		protected MethodSignature getMethodSignature()
		{
			return new MethodSignature("<init>", "(Lnet/minecraft/world/chunk/Chunk;I)V");
		}
	}
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		ClassNode classNode = ASMUtil.readClass(basicClass);

		ASMUtil.transformClassMethods(classNode, new MethodTransformer[] {
				new ConstructorTransformer()
		});

		return ASMUtil.writeClass(classNode);
	}
}
