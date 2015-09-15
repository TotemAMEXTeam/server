package net.twilightstudios.amex.util.persistence;

import org.hibernate.Session;

public interface SessionManager extends TransactionManager {
	
	public Session getCurrentSession();

}
