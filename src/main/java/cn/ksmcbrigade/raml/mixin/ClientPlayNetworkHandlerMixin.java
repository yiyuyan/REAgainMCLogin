package cn.ksmcbrigade.raml.mixin;

import cn.ksmcbrigade.raml.client.REAgainMCLoginClient;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onGameJoin",at = @At("TAIL"))
    public void sendMods(GameJoinS2CPacket packet, CallbackInfo ci){
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(REAgainMCLoginClient.mods.toString());
        ClientPlayNetworking.send(REAgainMCLoginClient.channel, buf);
    }
}
