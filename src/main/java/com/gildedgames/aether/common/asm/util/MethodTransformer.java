package com.gildedgames.aether.common.asm.util;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import javax.annotation.Nullable;

public abstract class MethodTransformer
{
	private MethodSignature signature;

	public abstract boolean transformMethod(ClassNode classNode, MethodNode methodNode);

	protected abstract MethodSignature getMethodSignature();

	@Nullable
	public boolean canTransformMethod(MethodNode methodNode)
	{
		if (this.signature == null)
		{
			this.signature = this.getMethodSignature();

			if (this.signature == null)
			{
				throw new IllegalArgumentException("Method signature must not be null");
			}
		}

		return this.signature.matches(methodNode);
	}
}
