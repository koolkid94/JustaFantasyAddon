package net.kuwulkid94.kuwulkid.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.kuwulkid94.kuwulkid.JustaFantasyAddon;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CrudeAltarScreen extends HandledScreen<CrudeAltarScreenHandler> {
    private static final Identifier TEXTURE =
            new Identifier(JustaFantasyAddon.MOD_ID, "textures/gui/crude_altar_gui.png");
    //top left hand corner pixel is the coordinate

    public CrudeAltarScreen(CrudeAltarScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    protected void init() {
        super.init();
        titleX = (backgroundHeight + textRenderer.getWidth(title) / 2);
    }
    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        renderProgressArrow(matrices, x, y);
    }

    private void renderProgressArrow(MatrixStack matrices, int x, int y) {
        if(handler.isCrafting()) {
            drawTexture(matrices, x + 105, y + 33, 176, 0, 8, handler.getScaledProgress());
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
