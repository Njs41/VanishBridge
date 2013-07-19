package no.runsafe.vanishbridge.command;

import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerJoinEvent;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import no.runsafe.vanishbridge.PlayerVanishManager;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Map;

public class FakeJoin extends PlayerCommand
{
	public FakeJoin(PlayerVanishManager playerVanishManager)
	{
		super("fakejoin", "Unvanishes and fakes a join event", "runsafe.vanish.fakequit");
		manager = playerVanishManager;
	}

	@Override
	public String OnExecute(RunsafePlayer player, Map<String, String> stringStringHashMap)
	{
		manager.setVanished(player, false);
		RunsafePlayerJoinEvent fake = new RunsafePlayerJoinEvent(new PlayerJoinEvent(player.getRawPlayer(), null));
		fake.Fire();
		RunsafeServer.Instance.broadcastMessage(fake.getJoinMessage());
		return null;
	}

	final PlayerVanishManager manager;
}
