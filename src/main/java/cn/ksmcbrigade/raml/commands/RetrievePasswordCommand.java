package cn.ksmcbrigade.raml.commands;

import cn.ksmcbrigade.raml.RegisteredPlayersJson;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.function.Supplier;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class RetrievePasswordCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("retrievepassword")
                .then(argument("playerName", StringArgumentType.word())
                    .then(argument("newPassword", StringArgumentType.word())
                        .executes(ctx -> {
                            String password = StringArgumentType.getString(ctx, "newPassword");
                            //ServerPlayerEntity player = ctx.getSource().getPlayer();
                            //String username = player.getEntityName();
                            String username = StringArgumentType.getString(ctx, "playerName");
                            if (ctx.getSource().getEntity() != null) {
                                ctx.getSource().sendFeedback(() -> Text.of("This command can only be executed on server console"),false);
                                return -1;
                            }
                            RegisteredPlayersJson.retrieve(username,password);

                            return 1;
        }))));
    }
}
