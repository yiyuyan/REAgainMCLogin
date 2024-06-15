package cn.ksmcbrigade.raml.commands;

import cn.ksmcbrigade.raml.PlayerLogin;
import cn.ksmcbrigade.raml.REAgainMCLogin;
import cn.ksmcbrigade.raml.RegisteredPlayersJson;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.function.Supplier;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class RegisterCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("register")
                .then(argument("newPassword", StringArgumentType.word())
                    .then(argument("confirmPassword", StringArgumentType.word())
                        .executes(ctx -> {
                            String password = StringArgumentType.getString(ctx, "newPassword");
                            ServerPlayerEntity player = ctx.getSource().getPlayer();
                            String username = player.getEntityName();
                            if (RegisteredPlayersJson.isPlayerRegistered(username)) {
                                ctx.getSource().sendFeedback(() -> Text.of("§cYou're already registered! Use /login instead."), false);
                                return 1;
                            }
                            if (!password.equals(StringArgumentType.getString(ctx, "confirmPassword"))) {
                                ctx.getSource().sendFeedback(() -> Text.of("§cPasswords don't match! Repeat it correctly."), false);
                                return 1;
                            }
                            String uuid = ctx.getSource().getPlayer().getUuidAsString();
                            RegisteredPlayersJson.save(uuid, username, password);
                            PlayerLogin playerLogin = REAgainMCLogin.getPlayer(ctx.getSource().getPlayer());
                            playerLogin.setLoggedIn(true);
                            player.setInvulnerable(false);
                            ctx.getSource().sendFeedback(() -> Text.of("§aSuccessfully registered."), false);
                            return 1;
        }))));
    }
}
