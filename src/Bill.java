import java.time.DayOfWeek;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Bill {
	private double basicCharge;
	private User user;
	private Month month;
	private ArrayList<Call> localCalls;
	private ArrayList<Call> nationalCalls;
	private ArrayList<Call> internationalCalls;
	private Data data;

	public Bill(User user, Month month, ArrayList<Call> calls) {
		this.basicCharge = 500.0;
		this.user = user;
		this.month = month;

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
		String nameString = "Nombre de cliente: '" + this.user.getName() + "'\n";
		String monthString = "Mes: '" + this.month.toString() + "'\n\n";
		String detailTitle = "DETALLE:\n\n";
		String basicPaymentString = "Abono básico: ---------- $ " + this.basicCharge + "\n\n";
		double localCallsCharge = calculateLocalCallsTotalCharge();
		double nationalCallsCharge = calculateNationalCallsTotalCharge();
		double internationalCallsCharge = calculateInternationalCallsTotalCharge();
		String localCallsString = getCallsDetails(this.localCalls, "locales");
		String nationalCallsString = getCallsDetails(this.nationalCalls, "nacionales");
		String internationalCallsString = getCallsDetails(this.internationalCalls, "internacionales");
		String totalCharge = "COSTO TOTAL ---------- $ "
				+ (this.basicCharge + localCallsCharge + nationalCallsCharge + internationalCallsCharge);

		return nameString + monthString + detailTitle + basicPaymentString + localCallsString + nationalCallsString
				+ internationalCallsString + totalCharge;
	}

	private String getCallsDetails(ArrayList<Call> calls, String type) {
		if (calls.size() == 0)
			return "";
		String ret = "Llamadas " + type + " (" + calls.size() + "):\n";
		for (Call c : calls) {
			if (type == "locales") {
				ret += "  • Llamada '" + c.getCallerUser().getResidingLocality() + "' - '"
						+ c.getReceiverUser().getResidingLocality() + "' (" + c.getDurationMinutes()
						+ " mins) ---------- $ " + calculateLocalCallCharge(c) + "\n";
			} else if (type == "nacionales") {
				ret += "  • Llamada '" + c.getCallerUser().getResidingLocality() + "' - '"
						+ c.getReceiverUser().getResidingLocality() + "' (" + c.getDurationMinutes()
						+ " mins) ---------- $ " + calculateNationalCallCharge(c) + "\n";
			} else {
				ret += "  • Llamada '" + c.getCallerUser().getResidingCountry() + "' - '"
						+ c.getReceiverUser().getResidingCountry() + "' (" + c.getDurationMinutes()
						+ " mins) ---------- $ " + calculateInternationalCallCharge(c) + "\n";
			}
		}
		ret += "\n";
		return ret;
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
