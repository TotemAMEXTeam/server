package net.twilightstudios.amex.util.persistence.hibernate.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

public class MySqlDateTypeWithTime implements UserType, Serializable {

	@Override
	public int[] sqlTypes() {
		return new int[]{Types.DATE};
	}

	@Override
	public Class returnedClass() {
		return Date.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return (x == y)  || (x != null && y != null && (x.equals(y)));
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
			return new Date((rs.getTimestamp(names[0])).getTime());
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws HibernateException, SQLException {
		Date dat = (Date)value;
		Timestamp sq = new Timestamp(dat.getTime());
		st.setTimestamp(index, sq);
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		if (value == null) return null;
        return new Date(((Date)value).getTime());
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return deepCopy(cached);
	}

	@Override
	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return deepCopy(original);
	}

}
