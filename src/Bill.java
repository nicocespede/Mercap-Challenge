import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Bill {
	private LocalDate date;
	private double basicCharge;
	private User user;
	private Month month;
	private int year;
	private ArrayList<Call> localCalls;
	private ArrayList<Call> nationalCalls;
	private ArrayList<Call> internationalCalls;
	private Data data;

	public Bill(User user, Month month, int year, ArrayList<Call> calls) {
		this.date = LocalDate.now();
		this.basicCharge = 500.0;
		this.user = user;
		this.month = month;
		this.year = year;

		// filtro llamadas locales
		List<Call> localCalls = calls.stream()
				.filter(c -> c.getCallerUser().getResidingCountry().equals(c.getReceiverUser().getResidingCountry())
						&& c.getCallerUser().getResidingLocality().equals(c.getReceiverUser().getResidingLocality()))
				.toList();
		this.localCalls = new ArrayList<Call>(localCalls);

		// filtro llamadas nacionales
		List<Call> nationalCalls = calls.stream()
				.filter(c -> !c.getCallerUser().getResidingLocality().equals(c.getReceiverUser().getResidingLocality())
						&& c.getCallerUser().getResidingCountry().equals(c.getReceiverUser().getResidingCountry()))
				.toList();
		this.nationalCalls = new ArrayList<Call>(nationalCalls);

		// filtro llamadas internacionales
		List<Call> internationalCalls = calls.stream()
				.filter(c -> !c.getCallerUser().getResidingCountry().equals(c.getReceiverUser().getResidingCountry()))
				.toList();
		this.internationalCalls = new ArrayList<Call>(internationalCalls);

		this.data = new Data();
		this.data.fillCosts();
	}

	public User getUser() {
		return this.user;
	}

	public Month getMonth() {
		return this.month;
	}

	public ArrayList<Call> getLocalCalls() {
		return this.localCalls;
	}

	public ArrayList<Call> getNationalCalls() {
		return this.nationalCalls;
	}

	public ArrayList<Call> getInternationalCalls() {
		return this.internationalCalls;
	}

	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dateString = "Fecha de facturación: " + formatter.format(this.date).toString() + "\n";
		String idString = "Número de cliente: " + this.user.getId() + "\n";
		String nameString = "Nombre de cliente: " + this.user.getName() + "\n";
		String monthString = "Período: " + translateMonth(this.month) + "/" + this.year + "\n\n";
		String detailTitle = "DETALLE:\n\n";
		String basicPaymentString = "Abono básico ---------- $ " + this.basicCharge + "\n\n";
		String localCallsString = getCallsDetails(this.localCalls, "locales");
		String nationalCallsString = getCallsDetails(this.nationalCalls, "nacionales");
		String internationalCallsString = getCallsDetails(this.internationalCalls, "internacionales");
		String totalCharge = "TOTAL A PAGAR ---------- $ " + (this.basicCharge + calculateLocalCallsTotalCharge()
				+ calculateNationalCallsTotalCharge() + calculateInternationalCallsTotalCharge());

		return dateString + idString + nameString + monthString + detailTitle + basicPaymentString + localCallsString
				+ nationalCallsString + internationalCallsString + totalCharge;
	}

	private String getCallsDetails(ArrayList<Call> calls, String type) {
		if (calls.size() == 0)
			return "";
		String ret = "Llamadas " + type + " (" + calls.size() + "):\n";
		DecimalFormat df = new DecimalFormat("#.##");
		for (Call c : calls) {
			if (type == "locales") {
				ret += "  • Llamada '" + c.getCallerUser().getResidingLocality() + "' - '"
						+ c.getReceiverUser().getResidingLocality() + "' (" + c.getDurationMinutes()
						+ " mins) ---------- $ " + df.format(calculateLocalCallCharge(c)) + "\n";
			} else if (type == "nacionales") {
				ret += "  • Llamada '" + c.getCallerUser().getResidingLocality() + "' - '"
						+ c.getReceiverUser().getResidingLocality() + "' (" + c.getDurationMinutes()
						+ " mins) ---------- $ " + df.format(calculateNationalCallCharge(c)) + "\n";
			} else {
				ret += "  • Llamada '" + c.getCallerUser().getResidingCountry() + "' - '"
						+ c.getReceiverUser().getResidingCountry() + "' (" + c.getDurationMinutes()
						+ " mins) ---------- $ " + df.format(calculateInternationalCallCharge(c)) + "\n";
			}
		}
		ret += "\n";
		return ret;
	}

	private String translateMonth(Month month) {
		String[] months = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
				"Octubre", "Noviembre", "Diciembre" };
		return months[month.getValue() - 1];
	}

	private double calculateLocalCallCharge(Call call) {
		double charge;
		if (call.getDay().equals(DayOfWeek.SATURDAY) || call.getDay().equals(DayOfWeek.SUNDAY)) {
			charge = call.getDurationMinutes() * 0.1;
		} else {
			charge = call.getStartDateTime().getHour() >= 8 && call.getStartDateTime().getHour() <= 20
					? call.getDurationMinutes() * 0.2
					: call.getDurationMinutes() * 0.1;
		}
		return charge;
	}

	private double calculateNationalCallCharge(Call call) {
		double localityCost = data.nationalCost.get(call.getReceiverUser().getResidingLocality());
		return call.getDurationMinutes() * localityCost;
	}

	private double calculateInternationalCallCharge(Call call) {
		double countryCost = data.internationalCost.get(call.getReceiverUser().getResidingCountry());
		return call.getDurationMinutes() * countryCost;
	}

	private double calculateLocalCallsTotalCharge() {
		double charge = 0.0;
		for (Call c : this.localCalls) {
			charge += calculateLocalCallCharge(c);
		}
		return charge;
	}

	private double calculateNationalCallsTotalCharge() {
		double charge = 0.0;
		for (Call c : this.nationalCalls) {
			charge += calculateNationalCallCharge(c);
		}
		return charge;
	}

	private double calculateInternationalCallsTotalCharge() {
		double charge = 0.0;
		for (Call c : this.internationalCalls) {
			charge += calculateInternationalCallCharge(c);
		}
		return charge;
	}

}
