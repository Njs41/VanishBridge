package no.runsafe.vanishbridge;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.hook.IPlayerDataProvider;
import no.runsafe.framework.api.hook.IPlayerVisibility;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import org.bukkit.entity.Player;
import org.kitteh.vanish.VanishManager;
import org.kitteh.vanish.VanishPlugin;

import java.util.HashMap;

public class PlayerVanishManager implements IPlayerDataProvider, IPlayerVisibility
{
	public PlayerVanishManager(VanishEvents hook, IServer server)
	{
		VanishPlugin plugin = server.getPlugin("VanishNoPacket");
		vanishNoPacket = plugin.getManager();
		plugin.getHookManager().registerHook("runsafe", hook);
	}

	@Override
	public HashMap<String, String> GetPlayerData(IPlayer player)
	{
		Player bukkitPlayer = ObjectUnwrapper.convert(player);
		if (bukkitPlayer != null && vanishNoPacket.isVanished(bukkitPlayer))
		{
			HashMap<String, String> response = new HashMap<String, String>();
			response.put("vanished", "true");
			return response;
		}
		return null;
	}

	@Override
	public boolean isPlayerHidden(IPlayer viewer, IPlayer target)
	{
		return vanishNoPacket.isVanished((Player) ObjectUnwrapper.convert(viewer)) && !viewer.hasPermission("vanish.see");
	}

	@Override
	public boolean isPlayerVanished(IPlayer player)
	{
		Player bukkitPlayer = ObjectUnwrapper.convert(player);
		return bukkitPlayer != null && vanishNoPacket.isVanished(bukkitPlayer);
	}

	public void setVanished(IPlayer player, boolean vanished)
	{
		Player bukkitPlayer = ObjectUnwrapper.convert(player);
		if (vanishNoPacket.isVanished(bukkitPlayer) != vanished)
			vanishNoPacket.toggleVanish(bukkitPlayer);
	}

	private final VanishManager vanishNoPacket;
}
