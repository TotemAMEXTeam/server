package net.twilightstudios.amex.flight.service.aena;

import static org.junit.Assert.*;
import net.twilightstudios.amex.flight.service.FlightService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.junit.Test;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/applicationContext.xml"})
public class TestAenaHTMLFlightService {
	
	@Autowired
	private FlightService flightService;

	@Test
	public void test() {
		System.out.println("Prueba");
	}

}
