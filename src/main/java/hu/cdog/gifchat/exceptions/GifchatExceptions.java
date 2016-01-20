package hu.cdog.gifchat.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(inherited = true, rollback = false)
public class GifchatExceptions extends Exception {

	private static final long serialVersionUID = -8221646437376348005L;

	public GifchatExceptions(String msg) {
		super(msg);
	}

}
