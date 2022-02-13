package net.id.incubus_core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.id.incubus_core.dev.DevInit;
import net.id.incubus_core.render.HardBloomShaderManager;
import net.id.incubus_core.render.RenderTestBlockEntityRenderer;
import net.id.incubus_core.render.SoftBloomShaderManager;
import net.minecraft.client.render.RenderLayer;

public class IncubusCoreClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HardBloomShaderManager.init();
        SoftBloomShaderManager.init();

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            BlockEntityRendererRegistry.register(DevInit.RENDER_TEST_BLOCK_ENTITY_TYPE, context -> new RenderTestBlockEntityRenderer());
            BlockRenderLayerMap.INSTANCE.putBlock(DevInit.RENDER_TEST_BLOCK, RenderLayer.getTranslucent());
        }
    }
}