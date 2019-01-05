package com.gildedgames.aether.common.asm.transformers.lighting;

import com.gildedgames.aether.common.asm.util.ASMUtil;
import com.gildedgames.aether.common.asm.util.MethodSignature;
import com.gildedgames.aether.common.asm.util.MethodTransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class TransformerMinecraft implements IClassTransformer
{
	private class RunTickTransformer extends MethodTransformer
	{
		@Override
		public boolean transformMethod(ClassNode classNode, MethodNode methodNode)
		{
			Iterator<AbstractInsnNode> abstractInsnNodeIterator = methodNode.instructions.iterator();

			AbstractInsnNode injectionSite = null;

			while (abstractInsnNodeIterator.hasNext())
			{
				AbstractInsnNode abstractInsnNode = abstractInsnNodeIterator.next();

				if (abstractInsnNode.getOpcode() == Opcodes.ALOAD)
				{
					injectionSite = abstractInsnNode;
				}

				if (injectionSite != null && abstractInsnNode.getOpcode() == Opcodes.LDC)
				{
					LdcInsnNode ldcInsnNode = (LdcInsnNode) abstractInsnNode;

					if (ldcInsnNode.cst.equals("levelRenderer"))
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

						methodNode.instructions.insert(injectionSite, injection);

						return true;
					}
				}
			}

			return false;
		}

		@Override
		protected MethodSignature getMethodSignature()
		{
			return new MethodSignature("runTick", "()V");
		}
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		ClassNode classNode = ASMUtil.readClass(basicClass);

		ASMUtil.transformClassMethods(classNode, new MethodTransformer[] {
				new RunTickTransformer()
		});

		return ASMUtil.writeClass(classNode);
	}
}
