package cram.pack.dedicatedserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CRAMTheServer extends Thread
{
	public CRAMTheServer(ServerConfigurationManager cfg) throws IOException
	{
		config=cfg;
		manager = new ConnectionManager(this);
	}
	private ConnectionManager manager;
	private ServerConfigurationManager config = null;
	public ConnectionManager getConnectionManager(){return manager;}
	public ServerConfigurationManager getConfiguration(){return this.config;}
	private List<NetServerHandler> players = new ArrayList<NetServerHandler>();
	public void run()
	{
		while(!ShutdownServer)
		{
			handleIncomingConnections();
			tickPlayers();
			tickWorlds();
			tickRateManage();
		}
	}
	private void tickPlayers()
	{
		Iterator<NetServerHandler> nshI = players.iterator();
		while(nshI.hasNext())
		{
			NetServerHandler nsh = nshI.next();
			nsh.tick();
			if(nsh.disconnected)
				nshI.remove();
		}
	}
	private void handleIncomingConnections()
	{
		NetServerHandler nsh = pendingConnections.poll();
		if(nsh==null)
			return;
		Iterator<NetServerHandler> nshI = players.iterator();
		while(nshI.hasNext())
			if(nshI.next().username.equals(nsh.username))
				nshI.remove();
		players.add(nsh);
	}
	public final boolean ShutdownServer = false;
	public final int targetFPS = 35;
	public final int tickDelay = (int)(((float)1000/targetFPS));
	private long lastTick = -1;
	void tickRateManage()
	{
		if(lastTick==-1)
			lastTick = System.currentTimeMillis();
		long timeDifference = System.currentTimeMillis()-lastTick;
		try
		{
			int delayRate = (int) (tickDelay-timeDifference);
			if(delayRate>0)
			{
				Thread.currentThread();
				Thread.sleep(delayRate);
			}
			else
			{
				throw new Exception("Server is overloaded");
			}
		}
		catch(Exception e){}
	}
	private ArrayList<World> worlds = new ArrayList<World>(1);
	private void tickWorlds()
	{
		for(World w : worlds)
			w.tick();
	}
	public static ConcurrentLinkedQueue<NetServerHandler> pendingConnections = new ConcurrentLinkedQueue<NetServerHandler>(); 
}
