package cn.ksmcbrigade.raml.listeners;

import cn.ksmcbrigade.raml.REAgainMCLogin;
import cn.ksmcbrigade.raml.PlayerLogin;
import net.minecraft.server.network.ServerPlayerEntity;

public class OnPlayerLeave {
    public static void listen(ServerPlayerEntity player) {
        PlayerLogin playerLogin = REAgainMCLogin.getPlayer(player);
        playerLogin.setLoggedIn(false);
    }
}
