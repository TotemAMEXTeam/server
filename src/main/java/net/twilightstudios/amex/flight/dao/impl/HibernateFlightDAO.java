package net.twilightstudios.amex.flight.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import net.twilightstudios.amex.flight.dao.FlightDAO;
import net.twilightstudios.amex.flight.entity.Flight;
import net.twilightstudios.amex.util.persistence.SessionManager;

public class HibernateFlightDAO implements FlightDAO {
	
	private SessionManager manager;
	
	public SessionManager getManager() {
		return manager;
	}

	public void setManager(SessionManager manager) {
		this.manager = manager;
	}

	@Override
	public void insert(Flight flight) {
		Session session = manager.getCurrentSession();
		session.save(flight);
	}

	@Override
	public List<Flight> getFlights() {
		Session session = manager.getCurrentSession();
		Criteria criteria = session.createCriteria(Flight.class);
		return (List<Flight>)criteria.list();
	}

	@Override
	public void deleteFlight(Flight flight) {
		Session session = manager.getCurrentSession();
		session.delete(flight);
	}

	@Override
	public void update(Flight flight) {
		Session session = manager.getCurrentSession();
		session.update(flight);
	}

	@Override
	public List<Flight> getFlights(String flightId) {
		Session session = manager.getCurrentSession();
		Criteria criteria = session.createCriteria(Flight.class);
		criteria.add(Restrictions.ilike("flightNumber", flightId, MatchMode.START));
		return (List<Flight>)criteria.list();
	}

	@Override
	public void deleteAll() {
		Session session = manager.getCurrentSession();
		Query query = session.createQuery("delete Flight f");
		query.executeUpdate();
	}

}
