package org.solstice.rollingStones.client.content.entity.renderer;

import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.random.Random;

public class RollingStoneRenderer implements BlockEntityRenderer<PistonBlockEntity> {

	protected final BlockRenderManager manager;

	public RollingStoneRenderer(BlockEntityRendererFactory.Context ctx) {
		this.manager = ctx.getRenderManager();
	}

	@Override
	public void render(PistonBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
//		this.manager.getModelRenderer().render(world, this.manager.getModel(state), state, pos, matrices, vertexConsumer, cull, Random.create(), state.getRenderingSeed(pos), overlay);
	}

}
