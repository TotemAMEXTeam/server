package net.twilightstudios.amex.flight.service.aena;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.twilightstudios.amex.flight.entity.Flight;
import net.twilightstudios.amex.flight.entity.FlightStatus;
import net.twilightstudios.amex.flight.entity.Status;
import net.twilightstudios.amex.flight.service.FlightService;

public class AenaHTMLFlightService implements FlightService {
	
	private String sourceCharset;
	private String htmlTableRows;
	private String htmlTableCells;
	
	public String getHtmlTableCells() {
		return htmlTableCells;
	}


	public void setHtmlTableCells(String htmlTableCells) {
		this.htmlTableCells = htmlTableCells;
	}

	private String url;
	private String flightCompanyParameter;
	private String flightIdParameter;
	private String flightDateParameter;
	
	private String urlDailyFlights;
	private String airportCodeParameter;
	private String movParameter;
	
	private String responseFlightDataDivId;
	private String responseFlightDataTdDepartureDate;
	private String responseFlightDataTdDepartureTime;
	private String responseFlightDataTdDepartureTerminal;
	private String responseFlightDataTdDepartureDesk;
	private String responseFlightDataTdDepartureGate;
	private String responseFlightDataTdState;
	private String responseFlightDataSpanRoute;
	private String responseFlightDataTdArrivalDate;
	private String responseFlightDataTdArrivalTime;
	private String responseFlightDataTdArrivalTerminal;
	private String responseFlightDataTdArrivalHall;
	private String responseFlightDataTdArrivalBelt;
	private String responseFlightDataTdArrivalState;
	private String responseStatusScheduledData;
	private String responseStatusArrivalData;
	private String responseStatusOnFlightData;
	private String responseStatusLandedData;
	private String responseStatusCanceledData;
	private String responseCompanyDataDivId;
	private String responseCompanyDataSpanName;
	
	private String responseFlightsListDivId;
	private String responseFlightsListsTableClass;
	private String responseFlightsListTdDepartureTime;
	private String responseFlightsListTdDepartureFlight;
	private String responseFlightsListTdDepartureDestiny;
	private String responseFlightsListTdDepartureCompany;
	private String responseFlightsListTdDepartureOrigin;
	private String responseFlightsListTdDepartureDate;
	private String responseFlightsListTdDepartureOriginSub;
	private String responseFlightsListTdDepartureOriginSubData;
	private String responseFlightsListPageRegExp;
	private String responseFlightsListReducedPageRegExp;
	private List<String> postParametersList;
	private String httpSessionIdValuedParameter;
	private String scriptSessionIdValuedParameter;
	private String airportValuedParameter;
	private String pageValuedParameter;
	private String urlPagedValueParameter;
	
	
	public String getUrlPagedValueParameter() {
		return urlPagedValueParameter;
	}


	public void setUrlPagedValueParameter(String urlPagedValueParameter) {
		this.urlPagedValueParameter = urlPagedValueParameter;
	}


	public String getHttpSessionIdValuedParameter() {
		return httpSessionIdValuedParameter;
	}


	public void setHttpSessionIdValuedParameter(String httpSessionIdValuedParameter) {
		this.httpSessionIdValuedParameter = httpSessionIdValuedParameter;
	}


	public String getScriptSessionIdValuedParameter() {
		return scriptSessionIdValuedParameter;
	}


	public void setScriptSessionIdValuedParameter(
			String scriptSessionIdValuedParameter) {
		this.scriptSessionIdValuedParameter = scriptSessionIdValuedParameter;
	}


	public String getAirportValuedParameter() {
		return airportValuedParameter;
	}


	public void setAirportValuedParameter(String airportValuedParameter) {
		this.airportValuedParameter = airportValuedParameter;
	}


	public String getPageValuedParameter() {
		return pageValuedParameter;
	}


	public void setPageValuedParameter(String pageValuedParameter) {
		this.pageValuedParameter = pageValuedParameter;
	}


	public List<String> getPostParametersList() {
		return postParametersList;
	}


	public void setPostParametersList(List<String> postParametersList) {
		this.postParametersList = postParametersList;
	}


	public String getResponseFlightsListReducedPageRegExp() {
		return responseFlightsListReducedPageRegExp;
	}


	public void setResponseFlightsListReducedPageRegExp(
			String responseFlightsListReducedPageRegExp) {
		this.responseFlightsListReducedPageRegExp = responseFlightsListReducedPageRegExp;
	}


	public String getResponseFlightsListPageRegExp() {
		return responseFlightsListPageRegExp;
	}


	public void setResponseFlightsListPageRegExp(
			String responseFlightsListPageRegExp) {
		this.responseFlightsListPageRegExp = responseFlightsListPageRegExp;
	}


	public String getResponseFlightsListTdDepartureOriginSubData() {
		return responseFlightsListTdDepartureOriginSubData;
	}


	public void setResponseFlightsListTdDepartureOriginSubData(
			String responseFlightsListTdDepartureOriginSubData) {
		this.responseFlightsListTdDepartureOriginSubData = responseFlightsListTdDepartureOriginSubData;
	}


	public String getResponseFlightsListTdDepartureOriginSub() {
		return responseFlightsListTdDepartureOriginSub;
	}


	public void setResponseFlightsListTdDepartureOriginSub(
			String responseFlightsListTdDepartureOriginSub) {
		this.responseFlightsListTdDepartureOriginSub = responseFlightsListTdDepartureOriginSub;
	}


	public String getResponseFlightsListTdDepartureDate() {
		return responseFlightsListTdDepartureDate;
	}


	public void setResponseFlightsListTdDepartureDate(
			String responseFlightsListTdDepartureDate) {
		this.responseFlightsListTdDepartureDate = responseFlightsListTdDepartureDate;
	}


	public String getResponseFlightsListTdDepartureOrigin() {
		return responseFlightsListTdDepartureOrigin;
	}


	public void setResponseFlightsListTdDepartureOrigin(
			String responseFlightsListTdDepartureOrigin) {
		this.responseFlightsListTdDepartureOrigin = responseFlightsListTdDepartureOrigin;
	}


	public String getResponseFlightsListTdDepartureTime() {
		return responseFlightsListTdDepartureTime;
	}


	public void setResponseFlightsListTdDepartureTime(
			String responseFlightsListTdDepartureTime) {
		this.responseFlightsListTdDepartureTime = responseFlightsListTdDepartureTime;
	}


	public String getResponseFlightsListTdDepartureFlight() {
		return responseFlightsListTdDepartureFlight;
	}


	public void setResponseFlightsListTdDepartureFlight(
			String responseFlightsListTdDepartureFlight) {
		this.responseFlightsListTdDepartureFlight = responseFlightsListTdDepartureFlight;
	}


	public String getResponseFlightsListTdDepartureDestiny() {
		return responseFlightsListTdDepartureDestiny;
	}


	public void setResponseFlightsListTdDepartureDestiny(
			String responseFlightsListTdDepartureDestiny) {
		this.responseFlightsListTdDepartureDestiny = responseFlightsListTdDepartureDestiny;
	}


	public String getResponseFlightsListTdDepartureCompany() {
		return responseFlightsListTdDepartureCompany;
	}


	public void setResponseFlightsListTdDepartureCompany(
			String responseFlightsListTdDepartureCompany) {
		this.responseFlightsListTdDepartureCompany = responseFlightsListTdDepartureCompany;
	}


	public String getHtmlTableRows() {
		return htmlTableRows;
	}


	public void setHtmlTableRows(String htmlTableRows) {
		this.htmlTableRows = htmlTableRows;
	}


	public String getResponseFlightsListsTableClass() {
		return responseFlightsListsTableClass;
	}


	public void setResponseFlightsListsTableClass(
			String responseFlightsListsTableClass) {
		this.responseFlightsListsTableClass = responseFlightsListsTableClass;
	}

	public String getResponseFlightsListDivId() {
		return responseFlightsListDivId;
	}


	public void setResponseFlightsListDivId(String responseFlightsListDivId) {
		this.responseFlightsListDivId = responseFlightsListDivId;
	}


	public String getAirportCodeParameter() {
		return airportCodeParameter;
	}


	public void setAirportCodeParameter(String airportCodeParameter) {
		this.airportCodeParameter = airportCodeParameter;
	}


	public String getMovParameter() {
		return movParameter;
	}


	public void setMovParameter(String movParameter) {
		this.movParameter = movParameter;
	}


	public String getUrlDailyFlights() {
		return urlDailyFlights;
	}


	public void setUrlDailyFlights(String urlDailyFlights) {
		this.urlDailyFlights = urlDailyFlights;
	}


	public String getResponseCompanyDataSpanName() {
		return responseCompanyDataSpanName;
	}


	public void setResponseCompanyDataSpanName(String responseCompanyDataSpanName) {
		this.responseCompanyDataSpanName = responseCompanyDataSpanName;
	}


	public String getResponseCompanyDataDivId() {
		return responseCompanyDataDivId;
	}


	public void setResponseCompanyDataDivId(String responseCompanyDataDivId) {
		this.responseCompanyDataDivId = responseCompanyDataDivId;
	}


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
		return responseFlightDataTdDepartureDate;
	}


	public void setResponseFlightDataTdDate(String responseFlightDataTdDate) {
		this.responseFlightDataTdDepartureDate = responseFlightDataTdDate;
	}


	public String getResponseFlightDataTdDeparture() {
		return responseFlightDataTdDepartureTime;
	}


	public void setResponseFlightDataTdDeparture(
			String responseFlightDataTdDeparture) {
		this.responseFlightDataTdDepartureTime = responseFlightDataTdDeparture;
	}


	public String getResponseFlightDataTdTerminal() {
		return responseFlightDataTdDepartureTerminal;
	}


	public void setResponseFlightDataTdTerminal(String responseFlightDataTdTerminal) {
		this.responseFlightDataTdDepartureTerminal = responseFlightDataTdTerminal;
	}


	public String getResponseFlightDataTdDesk() {
		return responseFlightDataTdDepartureDesk;
	}


	public void setResponseFlightDataTdDesk(String responseFlightDataTdDesk) {
		this.responseFlightDataTdDepartureDesk = responseFlightDataTdDesk;
	}


	public String getResponseFlightDataTdGate() {
		return responseFlightDataTdDepartureGate;
	}


	public void setResponseFlightDataTdGate(String responseFlightDataTdGate) {
		this.responseFlightDataTdDepartureGate = responseFlightDataTdGate;
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

	
	public String getResponseFlightDataTdDepartureDate() {
		return responseFlightDataTdDepartureDate;
	}


	public void setResponseFlightDataTdDepartureDate(
			String responseFlightDataTdDepartureDate) {
		this.responseFlightDataTdDepartureDate = responseFlightDataTdDepartureDate;
	}


	public String getResponseFlightDataTdDepartureTime() {
		return responseFlightDataTdDepartureTime;
	}


	public void setResponseFlightDataTdDepartureTime(
			String responseFlightDataTdDepartureTime) {
		this.responseFlightDataTdDepartureTime = responseFlightDataTdDepartureTime;
	}


	public String getResponseFlightDataTdDepartureTerminal() {
		return responseFlightDataTdDepartureTerminal;
	}


	public void setResponseFlightDataTdDepartureTerminal(
			String responseFlightDataTdDepartureTerminal) {
		this.responseFlightDataTdDepartureTerminal = responseFlightDataTdDepartureTerminal;
	}


	public String getResponseFlightDataTdDepartureDesk() {
		return responseFlightDataTdDepartureDesk;
	}


	public void setResponseFlightDataTdDepartureDesk(
			String responseFlightDataTdDepartureDesk) {
		this.responseFlightDataTdDepartureDesk = responseFlightDataTdDepartureDesk;
	}


	public String getResponseFlightDataTdDepartureGate() {
		return responseFlightDataTdDepartureGate;
	}


	public void setResponseFlightDataTdDepartureGate(
			String responseFlightDataTdDepartureGate) {
		this.responseFlightDataTdDepartureGate = responseFlightDataTdDepartureGate;
	}


	public String getResponseFlightDataTdArrivalDate() {
		return responseFlightDataTdArrivalDate;
	}


	public void setResponseFlightDataTdArrivalDate(
			String responseFlightDataTdArrivalDate) {
		this.responseFlightDataTdArrivalDate = responseFlightDataTdArrivalDate;
	}


	public String getResponseFlightDataTdArrivalTime() {
		return responseFlightDataTdArrivalTime;
	}


	public void setResponseFlightDataTdArrivalTime(
			String responseFlightDataTdArrivalTime) {
		this.responseFlightDataTdArrivalTime = responseFlightDataTdArrivalTime;
	}


	public String getResponseFlightDataTdArrivalTerminal() {
		return responseFlightDataTdArrivalTerminal;
	}


	public void setResponseFlightDataTdArrivalTerminal(
			String responseFlightDataTdArrivalTerminal) {
		this.responseFlightDataTdArrivalTerminal = responseFlightDataTdArrivalTerminal;
	}


	public String getResponseFlightDataTdArrivalHall() {
		return responseFlightDataTdArrivalHall;
	}


	public void setResponseFlightDataTdArrivalHall(
			String responseFlightDataTdArrivalHall) {
		this.responseFlightDataTdArrivalHall = responseFlightDataTdArrivalHall;
	}


	public String getResponseFlightDataTdArrivalBelt() {
		return responseFlightDataTdArrivalBelt;
	}


	public void setResponseFlightDataTdArrivalBelt(
			String responseFlightDataTdArrivalBelt) {
		this.responseFlightDataTdArrivalBelt = responseFlightDataTdArrivalBelt;
	}


	public String getResponseFlightDataTdArrivalState() {
		return responseFlightDataTdArrivalState;
	}


	public void setResponseFlightDataTdArrivalState(
			String responseFlightDataTdArrivalState) {
		this.responseFlightDataTdArrivalState = responseFlightDataTdArrivalState;
	}

	

	public String getResponseStatusScheduledData() {
		return responseStatusScheduledData;
	}


	public void setResponseStatusScheduledData(String responseStatusScheduledData) {
		this.responseStatusScheduledData = responseStatusScheduledData;
	}


	public String getResponseStatusOnFlightData() {
		return responseStatusOnFlightData;
	}


	public void setResponseStatusOnFlightData(String responseStatusOnFlightData) {
		this.responseStatusOnFlightData = responseStatusOnFlightData;
	}


	public String getResponseStatusLandedData() {
		return responseStatusLandedData;
	}


	public void setResponseStatusLandedData(String responseStatusLandedData) {
		this.responseStatusLandedData = responseStatusLandedData;
	}

	public String getResponseStatusArrivalData() {
		return responseStatusArrivalData;
	}


	public void setResponseStatusArrivalData(String responseStatusArrivalData) {
		this.responseStatusArrivalData = responseStatusArrivalData;
	}


	public String getResponseStatusCanceledData() {
		return responseStatusCanceledData;
	}


	public void setResponseStatusCanceledData(String responseStatusCanceledData) {
		this.responseStatusCanceledData = responseStatusCanceledData;
	}

	/**
	 * Returns a flight status. 
	 * @param id Flight id
	 * @param date Departure date, format yyyy-MM-dd+HH:mm
	 * @return Current flight status. If flight covers whole route between AENA airports, result will have arrival information.
	 * 		   If not, it will only have departure information. Returns null if flight is not found.
	 * @throws IOException
	 * @throws ParseException
	 * @throws IllegalStateException
	 */
	@Override
	public FlightStatus retrieveFlightStatus(String id, String date) throws IOException, ParseException, IllegalStateException {
		
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
		Element fechaOrigen = flightInfoElements.select(responseFlightDataTdDepartureDate).first();
		Element horaOrigen = flightInfoElements.select(responseFlightDataTdDepartureTime).first();
		Element terminalOrigen = flightInfoElements.select(responseFlightDataTdDepartureTerminal).first();
		Element mostradorOrigen = flightInfoElements.select(responseFlightDataTdDepartureDesk).first();
		Element puertaOrigen = flightInfoElements.select(responseFlightDataTdDepartureGate).first();
		Element estadoOrigen = flightInfoElements.select(responseFlightDataTdState).first();
		Element route = flightInfoElements.select(responseFlightDataSpanRoute).first();
		Elements companyInfoElements = document.select(responseCompanyDataDivId);
		Element companyInfo = companyInfoElements.select(responseCompanyDataSpanName).first();
		
		FlightStatus status = new FlightStatus();
		
		if ((flightInfoElements == null) || (fechaOrigen == null) || (horaOrigen == null) ||
			(estadoOrigen == null) || (route == null)) {
			status = null;
		}
		else {
			Flight flight = new Flight();
			// Status on departure
			flight.setFlightNumber(id);
			status.setFlight(flight);
			if (puertaOrigen != null) {
				status.setBoardingGate(puertaOrigen.text());
			}
			if (mostradorOrigen != null) {
				status.setCheckInCounter(mostradorOrigen.text());
			}
			if (terminalOrigen != null) {
				status.setOriginTerminal(terminalOrigen.text());
			}
			
			String scheduledDateToProcess = fechaOrigen.text();
			String scheduledTimeToProcess = horaOrigen.text();

			Date scheduledDate = processScheduledDate(scheduledDateToProcess, scheduledTimeToProcess);
			flight.setScheduledDeparture(scheduledDate);
			
			String statusToProcess = estadoOrigen.text();
			
			Date estimatedDate = processEstimatedDate(statusToProcess, scheduledDateToProcess);
			status.setEstimatedDeparture(estimatedDate);
			
			Status flightCurrentStatus = processStatus(statusToProcess, scheduledDate, estimatedDate);
			if (flightCurrentStatus != null) {
				status.setStatus(flightCurrentStatus);
			}
			else {
				throw new IllegalStateException("State not valid");
			}
					
			Pattern routePattern = Pattern.compile("(.*\\(\\w+\\))\\s+(.*\\(\\w+\\))");
			Matcher routeMatcher = routePattern.matcher(route.text());
			if (routeMatcher.find()) {
				String origin = routeMatcher.group(1);
				String destiny = routeMatcher.group(2);
				flight.setOrigin(origin);
				flight.setDestiny(destiny);
			}
			
			if (companyInfo != null) {
				flight.setCompany(companyInfo.text());
			}
			
			// Status on arrival
			Element fechaDestino = flightInfoElements.select(responseFlightDataTdArrivalDate).first();
			Element horaDestino = flightInfoElements.select(responseFlightDataTdArrivalTime).first();
			Element terminalDestino = flightInfoElements.select(responseFlightDataTdArrivalTerminal).first();
			Element salaDestino = flightInfoElements.select(responseFlightDataTdArrivalHall).first();
			Element cintaDestino = flightInfoElements.select(responseFlightDataTdArrivalBelt).first();
			Element estadoDestino = flightInfoElements.select(responseFlightDataTdArrivalState).first();
			
			if (!((fechaDestino == null) && (horaDestino == null) &&
					(terminalDestino == null) && (salaDestino == null) && (cintaDestino == null) &&
					(estadoDestino == null))) {
				String arrivalDateToProcess = fechaDestino.text();
				String arrivalTimeToProcess = horaDestino.text();
				Date arrivalDate = processScheduledDate(arrivalDateToProcess, arrivalTimeToProcess);
				status.setScheduledArrival(arrivalDate);
				status.setDestinyTerminal(terminalDestino.text());
				status.setHall(salaDestino.text());
				status.setBelt(cintaDestino.text());
				
				Date estimatedArrival = processEstimatedDate(estadoDestino.text(), fechaDestino.text());
				status.setEstimatedArrival(estimatedArrival);
				
				Status statusArrival = processStatus(estadoDestino.text(), arrivalDate, estimatedArrival);
				if (statusArrival != null) {
					status.setStatus(statusArrival);
				}
				
			}	
		}
		
		return status;
		
	}
	
	private Date processScheduledDate (String fechaOrigen, String horaOrigen) throws ParseException {
		Pattern pattern = Pattern.compile("(\\d\\d\\/\\d\\d\\/\\d\\d\\d\\d)");
		Pattern pattern2 = Pattern.compile("(\\d\\d\\/\\d\\d\\/\\d\\d)");
		Matcher matcher = pattern.matcher(fechaOrigen);
		Matcher matcher2 = pattern2.matcher(fechaOrigen);
		String fecha = null;
		if (matcher.find()) {
			fecha = matcher.group(1);
			
		}
		else {
			if (matcher2.find()) {
				fecha = matcher2.group(1);
			}
			else {
				return null;
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		StringBuilder sbDates = new StringBuilder();
		sbDates.append(fecha);
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
	
	private Date processEstimatedDate (String statusToProcess, String fechaOrigen) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
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
		String dayLimitTime = "23:59:59";
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		Date dateCheck = df.parse(dayLimitTime);
		Date estimatedCheck = df.parse(estimatedDateString);
		String departureDay = null;
		if (estimatedCheck.after(dateCheck)) { // Estimated day is tomorrow
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(departureDay));
			c.add(Calendar.DATE, 1);
			departureDay = sdf.format(c.getTime());
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
	
	private Status processStatus (String statusToProcess, Date scheduled, Date estimated) throws IllegalArgumentException {
		Pattern pattern = Pattern.compile(responseStatusScheduledData);
		Matcher matcher = pattern.matcher(statusToProcess);
		if (matcher.find()) {
			if (scheduled.equals(estimated) || scheduled.after(estimated)) {
				return Status.SCHEDULED;
			}
			else {
				return Status.DELAYED;
			}
		}
		else {
			Pattern anotherPattern = Pattern.compile(responseStatusOnFlightData);
			Matcher anotherMatcher = anotherPattern.matcher(statusToProcess);
			if (anotherMatcher.find()) {
				return Status.ON_FLIGHT;
			}
			else {
				Pattern canceledPattern = Pattern.compile(responseStatusCanceledData);
				Matcher canceledMatcher = canceledPattern.matcher(statusToProcess);
				if (canceledMatcher.find()) {
					return Status.CANCELED;
				}
				else {
					Pattern landedPattern = Pattern.compile(responseStatusLandedData);
					Matcher landedMatcher = landedPattern.matcher(statusToProcess);
					if (landedMatcher.find()) {
						return Status.LANDED;
					}
					else {
						return null;
					}
				}
			}
		}
	}
	
	
	public List<Flight> retrieveDailyFlights (String airport) throws IOException, ParseException {
		
		List<Flight> result = new ArrayList<Flight>();
		
		StringBuilder sb = new StringBuilder();
		sb.append(url);
		sb.append('?');
		sb.append(airportCodeParameter);
		sb.append('=');
		sb.append(airport);
		sb.append('&');
		sb.append(movParameter);
		sb.append('=');
		sb.append('S');
		
		URL urlObject = new URL(sb.toString());
		HttpURLConnection connection = (HttpURLConnection)urlObject.openConnection();
		connection.setRequestMethod("GET");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.connect();
		
		CookieManager cm = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
		CookieHandler.setDefault(cm);
		String jSessionId ="";	
		List<HttpCookie> cookies = cm.getCookieStore().getCookies();
		for(HttpCookie cookie:cookies){	
			if(cookie.getName().equals("JSESSIONID")){			
				jSessionId = cookie.getValue();
			}
		}
		
		Document document = Jsoup.parse(connection.getInputStream(), sourceCharset, "");		
		List<Flight> res = new ArrayList<Flight>();
		
		Elements flightListElements = document.select(responseFlightsListDivId);
		Element table = flightListElements.select(responseFlightsListsTableClass).first();
		Elements rows = table.select(htmlTableRows);
		String date = flightListElements.select(responseFlightsListTdDepartureDate).first().text();
		Element origin = document.select(responseFlightsListTdDepartureOrigin).first();
		String originValue = origin.select(responseFlightsListTdDepartureOriginSub).first().text();
		StringBuilder stb = new StringBuilder();
		stb.append(responseFlightsListTdDepartureOriginSubData);
		stb.append("(.*)");
		Pattern pattern = Pattern.compile(stb.toString());
		Matcher matcher = pattern.matcher(originValue);
		String originAirport = null;
		if (matcher.find()) {
			StringBuilder sbo = new StringBuilder();
			sbo.append(matcher.group(1));
			sbo.append(" (");
			sbo.append(airport);
			sbo.append(')');
			originAirport = sbo.toString();
		}
		String acumDestino = "";
		String acumHora = "";
		for (int i=1; i<rows.size(); i++) {
			Element row = rows.get(i);
			Elements cols = row.select(htmlTableCells);
			String destino = cols.select(responseFlightsListTdDepartureDestiny).first().text();
			if (!destino.isEmpty()) {
				acumDestino = destino;
				acumHora = cols.select(responseFlightsListTdDepartureTime).first().text();
			}
			String id = cols.select(responseFlightsListTdDepartureFlight).first().text();
			String company = cols.select(responseFlightsListTdDepartureCompany).first().text();
			Flight flight = new Flight();
			flight.setCompany(company);
			flight.setDestiny(acumDestino);
			flight.setFlightNumber(id);
			flight.setScheduledDeparture(processScheduledDate(date, acumHora));
			flight.setOrigin(originAirport);
			res.add(flight);
		}
		result.addAll(res);
		
		int page = 1;
		boolean lastPage = false;
		while (!lastPage) {
			urlObject = new URL(urlDailyFlights);
			connection = (HttpURLConnection)urlObject.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.connect();
			
			try(PrintWriter writer = new PrintWriter(connection.getOutputStream())){	
				for (String s: postParametersList) {
					if (s.equals(httpSessionIdValuedParameter+"=")) {
						writer.println(s + jSessionId);
					}
					else if (s.equals(scriptSessionIdValuedParameter+"=")){
						writer.println(scriptSessionIdValuedParameter + "="+(int)(Math.random()));
					}
					else if (s.equals(airportValuedParameter)){
						writer.println(s + airport);
					}
					else if (s.equals(pageValuedParameter)){
						writer.println(s + page);
					}
					else if (s.contains(urlPagedValueParameter)) {
						writer.println("page="+urlPagedValueParameter+'?'+movParameter+"=S&"+airportCodeParameter+'=');
					}
					else {
						writer.println(s);
					}
				}			
			}
			
			try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))){		
				String line;
				String dest = null;
				Date dat = null;
				while((line = in.readLine()) != null){			
					if(line.contains("s0.siguiente=false")){					
						lastPage = true;
					}
					else{
						Pattern patternReg = Pattern.compile(responseFlightsListPageRegExp);
						Matcher matcherReg = patternReg.matcher(line);
						Flight flight = new Flight();
						if (matcherReg.matches()) {
							String compId = matcherReg.group(1);
							String flightId = matcherReg.group(6);
							StringBuilder stbl = new StringBuilder();
							stbl.append(compId);
							stbl.append(flightId);
							flight.setFlightNumber(stbl.toString());
							flight.setCompany(matcherReg.group(2));
							dest = matcherReg.group(3);
							flight.setDestiny(dest);
							flight.setOrigin(originAirport);
							DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							StringBuilder sbd = new StringBuilder();
							sbd.append(matcherReg.group(4));
							sbd.append(' ');
							sbd.append(matcherReg.group(5));
							sbd.append(":00");
							dat = sdf.parse(sbd.toString());
							flight.setScheduledDeparture(dat);
							result.add(flight);
						}
						else {
							Pattern patternRegExp = Pattern.compile(responseFlightsListReducedPageRegExp);
							Matcher matcherRegExp = patternRegExp.matcher(line);
							if (matcherRegExp.matches()) {
								flight.setScheduledDeparture(dat);
								flight.setDestiny(dest);
								flight.setOrigin(originAirport);
								flight.setCompany(matcherRegExp.group(2));
								StringBuilder sbld = new StringBuilder();
								sbld.append(matcherRegExp.group(1));
								sbld.append(matcherRegExp.group(3));
								flight.setFlightNumber(sbld.toString());
								result.add(flight);
							}						
						}
					}
				}
			}
			page++;
		}
		
		return result;
	}
	
}
