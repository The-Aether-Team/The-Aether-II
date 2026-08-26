package com.aetherteam.aetherii.mixin.mixins.client.optifine;

//@Mixin(BossHealthOverlay.class) //TODO crash
//public class BossHealthOverlayMixin {
//    /**
//     * Cancels the {@link CustomizeGuiOverlayEvent.BossEventProgress} GUI event after the event hook has been called for it.
//     * Made as a workaround for Jade's boss bar pushdown.<br>
//     * This modifies the assignment of the {@link CustomizeGuiOverlayEvent.BossEventProgress} event variable.
//     *
//     * @param event The original {@link CustomizeGuiOverlayEvent.BossEventProgress} parameter value.
//     * @return The modified {@link CustomizeGuiOverlayEvent.BossEventProgress} parameter value.
//     */
//    @ModifyVariable(at = @At(value = "STORE"), method = "render(Lnet/minecraft/client/gui/GuiGraphics;)V", index = 10)
//    @SuppressWarnings({"MixinAnnotationTarget", "InvalidInjectorMethodSignature"})
//    private Object event(Object event) {
//        CustomizeGuiOverlayEvent.BossEventProgress e = (CustomizeGuiOverlayEvent.BossEventProgress) event;
//        if (Minecraft.getInstance().level != null &&
//                RenderHooks.BOSS_EVENTS.containsKey(e.getBossEvent().getId()) &&
//                Minecraft.getInstance().level.getEntity(RenderHooks.BOSS_EVENTS.get(e.getBossEvent().getId())) instanceof AetherBossMob<?>) {
//            e.setCanceled(true);
//        }
//        return event;
//    }
//}

