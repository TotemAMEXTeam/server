package net.twilightstudios.amex.util.persistence;

public interface TransactionManager {
	
	public void beginTransactionOnCurrentSession();
	
	public void commitOnCurrentSession();
	
	public void rollbackOnCurrentSession();

}
