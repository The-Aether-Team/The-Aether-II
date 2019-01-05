package com.gildedgames.aether.common.asm.transformers.lighting;

import com.gildedgames.aether.common.asm.AetherASMTransformer;
import com.gildedgames.aether.common.asm.util.ASMUtil;
import com.gildedgames.aether.common.asm.util.MethodSignature;
import com.gildedgames.aether.common.asm.util.MethodTransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class TransformerChunk implements IClassTransformer
{
	private class ConstructorTransformer extends MethodTransformer
	{
		@Override
		public boolean transformMethod(ClassNode classNode, MethodNode methodNode)
		{
			AbstractInsnNode injectionSite = ASMUtil.getFirstNode(methodNode, Opcodes.RETURN);

			InsnList injection = new InsnList();
			injection.add(new VarInsnNode(Opcodes.ALOAD, 0));
			injection.add(new InsnNode(Opcodes.ACONST_NULL));
			injection.add(new FieldInsnNode(Opcodes.PUTFIELD,
					"net/minecraft/world/chunk/Chunk",
					"neighborLightChecks",
					"[S"));
			injection.add(new VarInsnNode(Opcodes.ALOAD, 0));
			injection.add(new VarInsnNode(Opcodes.ALOAD, 0));
			injection.add(new FieldInsnNode(Opcodes.GETFIELD,
					"net/minecraft/world/chunk/Chunk",
					"world",
					"Lnet/minecraft/world/World;"));
			injection.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
					"com/gildedgames/aether/common/world/lighting/LightingHooks",
					"getLightingEngine",
					"(Lnet/minecraft/world/World;)Lcom/gildedgames/aether/common/world/lighting/LightingEngine;",
					false));
			injection.add(new FieldInsnNode(Opcodes.PUTFIELD,
					"net/minecraft/world/chunk/Chunk",
					"lightingEngine",
					"Lcom/gildedgames/aether/common/world/lighting/LightingEngine;"));

			methodNode.instructions.insertBefore(injectionSite, injection);

			return true;
		}

		@Override
		protected MethodSignature getMethodSignature()
		{
			return new MethodSignature("<init>", "(Lnet/minecraft/world/World;II)V");
		}
	}

	private class GetLightSubtractedTransformer extends MethodTransformer
	{
		@Override
		public boolean transformMethod(ClassNode classNode, MethodNode methodNode)
		{
			// Insert hook before method executes
			InsnList insnList = new InsnList();
			insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
			insnList.add(new FieldInsnNode(Opcodes.GETFIELD,
					"net/minecraft/world/chunk/Chunk",
					"lightingEngine",
					"Lcom/gildedgames/aether/common/world/lighting/LightingEngine;"));
			insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
					"com/gildedgames/aether/common/world/lighting/LightingEngine",
					"procLightUpdates",
					"()V",
					false));

			methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), insnList);

			return true;
		}

		@Override
		protected MethodSignature getMethodSignature()
		{
			return new MethodSignature("getLightSubtracted", "(Lnet/minecraft/util/math/BlockPos;I)I");
		}
	}

	private class GetLightForTransformer extends MethodTransformer
	{
		@Override
		public boolean transformMethod(ClassNode classNode, MethodNode methodNode)
		{
			InsnList previous = new InsnList();
			previous.add(methodNode.instructions);

			InsnList insnList = new InsnList();
			insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
			insnList.add(new FieldInsnNode(Opcodes.GETFIELD,
					"net/minecraft/world/chunk/Chunk",
					"lightingEngine",
					"Lcom/gildedgames/aether/common/world/lighting/LightingEngine;"));
			insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
					"com/gildedgames/aether/common/world/lighting/LightingEngine",
					"procLightUpdates",
					"()V",
					false));

			insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
			insnList.add(new VarInsnNode(Opcodes.ALOAD, 1));
			insnList.add(new VarInsnNode(Opcodes.ALOAD, 2));
			insnList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
					"net/minecraft/world/chunk/Chunk",
					"getCachedLightFor",
					"(Lnet/minecraft/world/EnumSkyBlock;Lnet/minecraft/util/math/BlockPos;)I",
					false));
			insnList.add(new InsnNode(Opcodes.IRETURN));

			methodNode.instructions.clear();
			methodNode.instructions = insnList;

			classNode.methods.add(this.createGetCachedLightFor(previous));

			return true;
		}

		private MethodNode createGetCachedLightFor(InsnList previous)
		{
			MethodNode methodNode = new MethodNode(Opcodes.ACC_PUBLIC,
					"getCachedLightFor",
					"(Lnet/minecraft/world/EnumSkyBlock;Lnet/minecraft/util/math/BlockPos;)I",
					null,
					null);

			methodNode.instructions.add(previous);
			methodNode.instructions.resetLabels(); // These are all miserably out of date now, just trash it

			return methodNode;
		}

		@Override
		protected MethodSignature getMethodSignature()
		{
			return new MethodSignature("getLightFor", "(Lnet/minecraft/world/EnumSkyBlock;Lnet/minecraft/util/math/BlockPos;)I");
		}
	}

	private class RelightForTransformer extends MethodTransformer
	{

		@Override
		public boolean transformMethod(ClassNode classNode, MethodNode methodNode)
		{
			Iterator<AbstractInsnNode> abstractInsnNodeIterator = methodNode.instructions.iterator();

			InsnList instructions = new InsnList();

			while (abstractInsnNodeIterator.hasNext())
			{
				AbstractInsnNode abstractInsnNode = abstractInsnNodeIterator.next();

				if (abstractInsnNode.getType() == AbstractInsnNode.LINE)
				{
					LineNumberNode labelNode = (LineNumberNode) abstractInsnNode;

					// Delete line 411
					if (labelNode.line == 411)
					{
						// Safe to erase label here
						instructions.remove(labelNode.start);

						while (abstractInsnNodeIterator.hasNext())
						{
							abstractInsnNode = abstractInsnNodeIterator.next();

							if (abstractInsnNode.getType() == AbstractInsnNode.LINE)
							{
								break;
							}
						}

						continue;
					}
					// Replace line 418
					else if (labelNode.line == 418)
					{
						// Safe to erase label here
						instructions.remove(labelNode.start);

						instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
						instructions.add(new VarInsnNode(Opcodes.ILOAD, 1));
						instructions.add(new VarInsnNode(Opcodes.ILOAD, 3));
						instructions.add(new VarInsnNode(Opcodes.ILOAD, 4));
						instructions.add(new VarInsnNode(Opcodes.ILOAD, 5));
						instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
								"com/gildedgames/aether/common/world/lighting/LightingHooks",
								"relightSkylightColumn",
								"(Lnet/minecraft/world/World;Lnet/minecraft/world/chunk/Chunk;IIII)V",
								false));

						// Delete lines 419-470 (next label is at 473)
						// Safe to erase all labels in this section
						while (abstractInsnNodeIterator.hasNext())
						{
							abstractInsnNode = abstractInsnNodeIterator.next();

							if (abstractInsnNode.getType() == AbstractInsnNode.LINE)
							{
								labelNode = (LineNumberNode) abstractInsnNode;

								if (labelNode.line == 473)
								{
									break;
								}
							}
						}

						continue;
					}
					// Early exit on line 488
					else if (labelNode.line == 488)
					{
						// Keep the label line node, we're just returning instead
						instructions.add(abstractInsnNode);

						instructions.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
						instructions.add(new InsnNode(Opcodes.RETURN));

						continue;
					}
				}

				instructions.add(abstractInsnNode);
			}

			methodNode.instructions = instructions;

			return true;
		}

		@Override
		protected MethodSignature getMethodSignature()
		{
			return new MethodSignature("relightBlock", "(III)V");
		}
	}

	private class SetBlockStatePatch1Transformer extends MethodTransformer
	{
		@Override
		public boolean transformMethod(ClassNode classNode, MethodNode methodNode)
		{
			Iterator<AbstractInsnNode> abstractInsnNodeIterator = methodNode.instructions.iterator();

			LabelNode labelNode = null;

			// Find the label belonging to line 639
			while (abstractInsnNodeIterator.hasNext())
			{
				AbstractInsnNode abstractInsnNode = abstractInsnNodeIterator.next();

				if (abstractInsnNode.getType() == AbstractInsnNode.LINE)
				{
					LineNumberNode lineNumberNode = (LineNumberNode) abstractInsnNode;

					if (lineNumberNode.line == 639)
					{
						labelNode = lineNumberNode.start;
						break;
					}
				}
			}

			if (labelNode == null)
			{
				AetherASMTransformer.LOGGER.warn("Couldn't find label node on line 639 for class: {}", classNode.name);

				return false;
			}

			abstractInsnNodeIterator = methodNode.instructions.iterator();

			// Find line 633 and insert a GOTO command to the label we found
			while (abstractInsnNodeIterator.hasNext())
			{
				AbstractInsnNode abstractInsnNode = abstractInsnNodeIterator.next();

				if (abstractInsnNode.getType() == AbstractInsnNode.LINE)
				{
					LineNumberNode lineNumberNode = (LineNumberNode) abstractInsnNode;

					if (lineNumberNode.line == 633)
					{
						methodNode.instructions.insert(lineNumberNode, new JumpInsnNode(Opcodes.GOTO, labelNode));

						return true;
					}
				}
			}

			AetherASMTransformer.LOGGER.warn("Couldn't find line 633 for class: {}", classNode.name);

			return false;
		}

		@Override
		protected MethodSignature getMethodSignature()
		{
			return new MethodSignature("setBlockState",
					"(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;");
		}
	}

	private class SetBlockStatePatch2Transformer extends MethodTransformer
	{
		@Override
		public boolean transformMethod(ClassNode classNode, MethodNode methodNode)
		{
			Iterator<AbstractInsnNode> abstractInsnNodeIterator = methodNode.instructions.iterator();

			// Find the label belonging to line 639
			while (abstractInsnNodeIterator.hasNext())
			{
				AbstractInsnNode abstractInsnNode = abstractInsnNodeIterator.next();

				if (abstractInsnNode.getType() == AbstractInsnNode.LINE)
				{
					LineNumberNode lineNumberNode = (LineNumberNode) abstractInsnNode;

					if (lineNumberNode.line == 604)
					{
						while (abstractInsnNodeIterator.hasNext())
						{
							abstractInsnNode = abstractInsnNodeIterator.next();

							if (abstractInsnNode.getOpcode() == Opcodes.AASTORE)
							{
								InsnList injection = new InsnList();
								injection.add(new VarInsnNode(Opcodes.ALOAD, 0));
								injection.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
										"net/minecraft/world/chunk/Chunk",
										"getWorld", "()Lnet/minecraft/world/World;",
										false));
								injection.add(new VarInsnNode(Opcodes.ALOAD, 0));
								injection.add(new VarInsnNode(Opcodes.ALOAD, 12));
								injection.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
										"com/gildedgames/aether/common/world/lighting/LightingHooks",
										"initSkylightForSection",
										"(Lnet/minecraft/world/World;Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;)V",
										false));

								methodNode.instructions.insert(abstractInsnNode, injection);

								return true;
							}
						}
					}
				}
			}

			return false;
		}

		@Override
		protected MethodSignature getMethodSignature()
		{
			return new MethodSignature("setBlockState",
					"(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;");
		}
	}

	private class SetBlockStatePatch3Transformer extends MethodTransformer
	{
		@Override
		public boolean transformMethod(ClassNode classNode, MethodNode methodNode)
		{
			Iterator<AbstractInsnNode> abstractInsnNodeIterator = methodNode.instructions.iterator();

			LabelNode labelNode = null;

			// Find the label belonging to line 660
			while (abstractInsnNodeIterator.hasNext())
			{
				AbstractInsnNode abstractInsnNode = abstractInsnNodeIterator.next();

				if (abstractInsnNode.getType() == AbstractInsnNode.LINE)
				{
					LineNumberNode lineNumberNode = (LineNumberNode) abstractInsnNode;

					if (lineNumberNode.line == 660)
					{
						labelNode = lineNumberNode.start;

						break;
					}
				}
			}

			if (labelNode == null)
			{
				AetherASMTransformer.LOGGER.warn("Couldn't find label node on line 660 for class: {}", classNode.name);

				return false;
			}

			abstractInsnNodeIterator = methodNode.instructions.iterator();

			// Find line 653 and insert a GOTO command to the label we found
			while (abstractInsnNodeIterator.hasNext())
			{
				AbstractInsnNode abstractInsnNode = abstractInsnNodeIterator.next();

				if (abstractInsnNode.getType() == AbstractInsnNode.LINE)
				{
					LineNumberNode lineNumberNode = (LineNumberNode) abstractInsnNode;

					if (lineNumberNode.line == 653)
					{
						methodNode.instructions.insert(lineNumberNode, new JumpInsnNode(Opcodes.GOTO, labelNode));

						return true;
					}
				}
			}

			return false;
		}

		@Override
		protected MethodSignature getMethodSignature()
		{
			return new MethodSignature("setBlockState",
					"(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;");
		}
	}

	private class SetLightForTransformer extends MethodTransformer
	{
		@Override
		public boolean transformMethod(ClassNode classNode, MethodNode methodNode)
		{
			InsnList instructions = new InsnList();

			Iterator<AbstractInsnNode> abstractInsnNodeIterator = methodNode.instructions.iterator();

			while (abstractInsnNodeIterator.hasNext())
			{
				AbstractInsnNode abstractInsnNode = abstractInsnNodeIterator.next();

				if (abstractInsnNode.getType() == AbstractInsnNode.LINE)
				{
					LineNumberNode labelNode = (LineNumberNode) abstractInsnNode;

					// Replace line 719
					if (labelNode.line == 719)
					{
						InsnList insnList = new InsnList();
						insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
						insnList.add(new FieldInsnNode(Opcodes.GETFIELD,
								"net/minecraft/world/chunk/Chunk",
								"world",
								"Lnet/minecraft/world/World;"));
						insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
						insnList.add(new VarInsnNode(Opcodes.ALOAD, 7));
						insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
								"com/gildedgames/aether/common/world/lighting/LightingHooks",
								"initSkylightForSection",
								"(Lnet/minecraft/world/World;Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;)V",
								false));

						instructions.add(insnList);

						while (abstractInsnNodeIterator.hasNext())
						{
							abstractInsnNode = abstractInsnNodeIterator.next();

							if (abstractInsnNode.getType() == AbstractInsnNode.LINE)
							{
								instructions.add(((LineNumberNode) abstractInsnNode).start);
								instructions.add(abstractInsnNode);

								break;
							}
						}

						continue;
					}
				}

				instructions.add(abstractInsnNode);
			}

			methodNode.instructions = instructions;

			return true;
		}

		@Override
		protected MethodSignature getMethodSignature()
		{
			return new MethodSignature("setLightFor", "(Lnet/minecraft/world/EnumSkyBlock;Lnet/minecraft/util/math/BlockPos;I)V");
		}
	}

	private class OnLoadTransformer extends MethodTransformer
	{
		@Override
		public boolean transformMethod(ClassNode classNode, MethodNode methodNode)
		{
			AbstractInsnNode injectionSite = ASMUtil.getFirstNode(methodNode, Opcodes.RETURN);

			InsnList insnList = new InsnList();
			insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
			insnList.add(new FieldInsnNode(Opcodes.GETFIELD,
					"net/minecraft/world/chunk/Chunk",
					"world",
					"Lnet/minecraft/world/World;"));
			insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
			insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
					"com/gildedgames/aether/common/world/lighting/LightingHooks",
					"scheduleRelightChecksForChunkBoundaries",
					"(Lnet/minecraft/world/World;Lnet/minecraft/world/chunk/Chunk;)V",
					false));

			methodNode.instructions.insertBefore(injectionSite, insnList);

			return true;
		}

		@Override
		protected MethodSignature getMethodSignature()
		{
			return new MethodSignature("onLoad", "()V");
		}
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		ClassNode classNode = ASMUtil.readClass(basicClass);

		// public short[] neighborLightChecks;
		classNode.fields.add(new FieldNode(Opcodes.ACC_PUBLIC,
				"neighborLightChecks",
				"[S",
				null, null));

		// protected final LightingEngine lightingEngine;
		classNode.fields.add(new FieldNode(Opcodes.ACC_PROTECTED + Opcodes.ACC_FINAL,
				"lightingEngine",
				"Lcom/gildedgames/aether/common/world/lighting/LightingEngine;",
				null, null));

		ASMUtil.transformClassMethods(classNode, new MethodTransformer[] {
				new ConstructorTransformer(),
				new GetLightForTransformer(),
				new GetLightSubtractedTransformer(),
				new OnLoadTransformer(),
				new SetBlockStatePatch1Transformer(),
				new SetBlockStatePatch2Transformer(),
				new SetBlockStatePatch3Transformer(),
				new SetLightForTransformer(),
				new RelightForTransformer()
		});

		return ASMUtil.writeClass(classNode);
	}
}
