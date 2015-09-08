package net.twilightstudios.amex.flight.service.aena;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.twilightstudios.amex.flight.entity.FlightDate;
import net.twilightstudios.amex.flight.entity.FlightStatus;
import net.twilightstudios.amex.flight.entity.Status;
import net.twilightstudios.amex.flight.service.FlightService;

public class AenaHTMLFlightService implements FlightService {
	
	private String flightCompanyParameter;
	private String flightIdParameter;
	private String flightDateParameter;
	private String url;
	private String sourceCharset;
	private String responseFlightDataDivId;
	private String responseFlightDataTdDate;
	private String responseFlightDataTdDeparture;
	private String responseFlightDataTdTerminal;
	private String responseFlightDataTdDesk;
	private String responseFlightDataTdGate;
	private String responseFlightDataTdState;
	private String responseFlightDataSpanRoute;
	
	
	public String getFlightCompanyParameter() {
		return flightCompanyParameter;
	}


	public void setFlightCompanyParameter(String flightCompanyParameter) {
		this.flightCompanyParameter = flightCompanyParameter;
	}


	public String getFlightIdParameter() {
		return flightIdParameter;
	}


	public void setFlightIdParameter(String flightIdParameter) {
		this.flightIdParameter = flightIdParameter;
	}


	public String getFlightDateParameter() {
		return flightDateParameter;
	}


	public void setFlightDateParameter(String flightDateParameter) {
		this.flightDateParameter = flightDateParameter;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getSourceCharset() {
		return sourceCharset;
	}


	public void setSourceCharset(String sourceCharset) {
		this.sourceCharset = sourceCharset;
	}


	public String getResponseFlightDataDivId() {
		return responseFlightDataDivId;
	}


	public void setResponseFlightDataDivId(String responseFlightDataDivId) {
		this.responseFlightDataDivId = responseFlightDataDivId;
	}


	public String getResponseFlightDataTdDate() {
		return responseFlightDataTdDate;
	}


	public void setResponseFlightDataTdDate(String responseFlightDataTdDate) {
		this.responseFlightDataTdDate = responseFlightDataTdDate;
	}


	public String getResponseFlightDataTdDeparture() {
		return responseFlightDataTdDeparture;
	}


	public void setResponseFlightDataTdDeparture(
			String responseFlightDataTdDeparture) {
		this.responseFlightDataTdDeparture = responseFlightDataTdDeparture;
	}


	public String getResponseFlightDataTdTerminal() {
		return responseFlightDataTdTerminal;
	}


	public void setResponseFlightDataTdTerminal(String responseFlightDataTdTerminal) {
		this.responseFlightDataTdTerminal = responseFlightDataTdTerminal;
	}


	public String getResponseFlightDataTdDesk() {
		return responseFlightDataTdDesk;
	}


	public void setResponseFlightDataTdDesk(String responseFlightDataTdDesk) {
		this.responseFlightDataTdDesk = responseFlightDataTdDesk;
	}


	public String getResponseFlightDataTdGate() {
		return responseFlightDataTdGate;
	}


	public void setResponseFlightDataTdGate(String responseFlightDataTdGate) {
		this.responseFlightDataTdGate = responseFlightDataTdGate;
	}


	public String getResponseFlightDataTdState() {
		return responseFlightDataTdState;
	}


	public void setResponseFlightDataTdState(String responseFlightDataTdState) {
		this.responseFlightDataTdState = responseFlightDataTdState;
	}


	public String getResponseFlightDataSpanRoute() {
		return responseFlightDataSpanRoute;
	}


	public void setResponseFlightDataSpanRoute(String responseFlightDataSpanRoute) {
		this.responseFlightDataSpanRoute = responseFlightDataSpanRoute;
	}

	
	
	public FlightStatus retrieveFlightStatus(String id, String date) throws IOException, ParseException {
		
		Pattern idPattern = Pattern.compile("([A-Z]+)(\\d+)");
		Matcher idMatcher = idPattern.matcher(id);
		String flightCompany = null;
		String flightNumber = null;
		if (idMatcher.find()) {
			flightCompany = idMatcher.group(1);
			flightNumber = idMatcher.group(2);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(url);
		sb.append('?');
		sb.append(flightCompanyParameter);
		sb.append('=');
		sb.append(flightCompany);
		sb.append('&');
		sb.append(flightIdParameter);
		sb.append('=');
		sb.append(flightNumber);
		sb.append('&');
		sb.append(flightDateParameter);
		sb.append('=');
		sb.append(URLEncoder.encode(date,sourceCharset));
		
		URL urlObject = new URL(sb.toString());
		HttpURLConnection connection = (HttpURLConnection)urlObject.openConnection();
		connection.setRequestMethod("GET");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.connect();
		
		Document document = Jsoup.parse(connection.getInputStream(), sourceCharset, "");

		Elements flightInfoElements = document.select(responseFlightDataDivId);
		Element fechaOrigen = flightInfoElements.select(responseFlightDataTdDate).first();
		Element horaOrigen = flightInfoElements.select(responseFlightDataTdDeparture).first();
		Element terminalOrigen = flightInfoElements.select(responseFlightDataTdTerminal).first();
		Element mostradorOrigen = flightInfoElements.select(responseFlightDataTdDesk).first();
		Element puertaOrigen = flightInfoElements.select(responseFlightDataTdGate).first();
		Element estadoOrigen = flightInfoElements.select(responseFlightDataTdState).first();
		Element route = flightInfoElements.select(responseFlightDataSpanRoute).first();
		
		FlightStatus status = new FlightStatus();
		
		if ((flightInfoElements == null) || (fechaOrigen == null) || (horaOrigen == null) ||
			(terminalOrigen == null) || (mostradorOrigen == null) || (puertaOrigen == null) ||
			(estadoOrigen == null) || (route == null)) {
			status = null;
		}
		else {
			status.setFlightNumber(id);
			status.setBoardingGate(puertaOrigen.text());
			status.setCheckInCounter(mostradorOrigen.text());
			status.setOriginTerminal(terminalOrigen.text());
			
			FlightDate scheduled = new FlightDate();
			String scheduledDateToProcess = fechaOrigen.text();
			String scheduledTimeToProcess = horaOrigen.text();

			Date scheduledDate = processScheduledDate(scheduledDateToProcess, scheduledTimeToProcess);
			scheduled.setDeparture(scheduledDate);
			status.setScheduled(scheduled);
			
			FlightDate estimated = new FlightDate();
			String statusToProcess = estadoOrigen.text();
			boolean cancelled = statusToProcess.contains("Cancelado");
			if (cancelled) {
				status.setStatus(Status.CANCELED);
			}
			else {
				Date estimatedDate = getEstimatedDate(statusToProcess, scheduledDateToProcess);
				estimated.setDeparture(estimatedDate);
				status.setEstimated(estimated);
				status.setStatus(getStatus(scheduledDate, estimatedDate));
			}
			
			Pattern routePattern = Pattern.compile("(.*\\(\\w+\\))\\s+(.*\\(\\w+\\))");
			Matcher routeMatcher = routePattern.matcher(route.text());
			if (routeMatcher.find()) {
				String origin = routeMatcher.group(1);
				String destiny = routeMatcher.group(2);
				status.setOrigin(origin);
				status.setDestiny(destiny);
			}
		
		}
		
		return status;
		
	}
	
	private Date processScheduledDate (String fechaOrigen, String horaOrigen) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat();
		StringBuilder sbDates = new StringBuilder();
		sbDates.append(fechaOrigen);
		sbDates.append(' ');
		StringBuilder sbTimes = new StringBuilder();
		sbTimes.append(horaOrigen);
		if (horaOrigen.length() < 8) {
			sbTimes.append(":00");
		}
		String scheduledDateString = sbDates.toString()+sbTimes.toString();
		Date scheduledDate = sdf.parse(scheduledDateString);
		return scheduledDate;
	}
	
	private Date getEstimatedDate (String statusToProcess, String fechaOrigen) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat();
		// Look at estimated time
		Pattern pattern = Pattern.compile("(\\d\\d:\\d\\d)");
		Matcher matcher = pattern.matcher(statusToProcess);
		String estimatedTimeToProcess = null;
		StringBuilder sbDates = new StringBuilder();
		if (matcher.find()) {
			estimatedTimeToProcess = matcher.group(1);
			sbDates.append(estimatedTimeToProcess);
			if (estimatedTimeToProcess.length() < 8) {
				sbDates.append(":00");
			}
		}
		String estimatedDateString = sbDates.toString();
		// Look at estimated date
		Pattern patternEstimated = Pattern.compile("(\\d\\d\\/\\d\\d\\/\\d\\d)");
		Matcher matcherEstimated = patternEstimated.matcher(statusToProcess);
		String departureDay = null;
		if (matcherEstimated.find()) { // Estimated day is not today
			departureDay = matcherEstimated.group(1);
		}
		else {
			departureDay = fechaOrigen;
		}
		StringBuilder estimatedDate = new StringBuilder();
		estimatedDate.append(departureDay);
		estimatedDate.append(' ');
		estimatedDate.append(estimatedDateString);
		Date estimated = sdf.parse(estimatedDate.toString());
		return estimated;
	}
	
	private Status getStatus (Date scheduled, Date estimated) {
		if (scheduled.equals(estimated)) {
			return Status.SCHEDULED;
		}
		else if (scheduled.before(estimated)) {
			return Status.DELAYED;
		}
		else {
			return null;
		}
	}
	
}
