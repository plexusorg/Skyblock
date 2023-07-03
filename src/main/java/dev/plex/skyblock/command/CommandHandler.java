package dev.plex.skyblock.command;

import cloud.commandframework.CommandTree;
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.execution.FilteringCommandSuggestionProcessor;
import cloud.commandframework.paper.PaperCommandManager;
import dev.plex.skyblock.Skyblock;
import org.bukkit.command.CommandSender;

import java.util.function.Function;

/**
 * @author Taah
 * @project plex-skyblock
 * @since 10:32 PM [02-07-2023]
 */
public class CommandHandler {
    private PaperCommandManager<CommandSender> commandManager;

    public void setup() throws Exception {
        final Function<CommandTree<CommandSender>, CommandExecutionCoordinator<CommandSender>> executionCoordinatorFunction =
                AsynchronousCommandExecutionCoordinator.<CommandSender>builder().build();
        final Function<CommandSender, CommandSender> mapperFunction = Function.identity();
        this.commandManager = new PaperCommandManager<>(
                Skyblock.plugin(),
                executionCoordinatorFunction,
                mapperFunction,
                mapperFunction
        );

        this.commandManager.commandSuggestionProcessor(new FilteringCommandSuggestionProcessor<>(
                FilteringCommandSuggestionProcessor.Filter.<CommandSender>contains(true).andTrimBeforeLastSpace()
        ));
        this.commandManager.registerBrigadier();
        this.commandManager.registerAsynchronousCompletions();


        WorldCommand.register(this.commandManager);
    }
}
