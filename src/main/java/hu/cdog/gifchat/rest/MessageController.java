package hu.cdog.gifchat.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import hu.cdog.gifchat.memdb.MemDb;
import hu.cdog.gifchat.model.GifMessage;

@Path("messages")
public class MessageController {

	@Inject
	MemDb db;
	
	@GET
	@Path("valami")
	public List<GifMessage> getValami() {
		return db.getAll();
	}
}
