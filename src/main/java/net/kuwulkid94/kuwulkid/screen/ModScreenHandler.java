package net.kuwulkid94.kuwulkid.screen;

import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandler {
    public static ScreenHandlerType<CrudeAltarScreenHandler> CRUDE_ALTAR_SCREEN_HANDLER;

    public static void registerALlScreenHandlers(){
        CRUDE_ALTAR_SCREEN_HANDLER = new ScreenHandlerType<>(CrudeAltarScreenHandler::new);
    }
}
