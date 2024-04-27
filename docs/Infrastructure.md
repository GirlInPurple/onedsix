# Infrastructure

The entire server infrastructure relys on at least one server being alive at all times, preferably one on the whitelist.

The Whitelist is a list of addresses that is sent to all connected servers and clients. 
That list then gets added to the server/client's list and replicated to all the other connected servers.
This chain reaction means a new server popping up means that it can be used almost immediately, as soon as it propagates across the network.

In a similar vain, the system also has some monitoring and self-healing capabilities, as the users on a disconnected or unstable server are moved to another server instead.
Clients are also able to continue playing as this switch is happening due to everything necessary for gameplay being cached on the client, and the switch going to the strongest server first, then trickling down.