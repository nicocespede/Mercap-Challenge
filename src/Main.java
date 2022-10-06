import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {

	private static Data data;

	public static void main(String[] args) {
		data = new Data();
		data.fillLists();

		try {
			Bill bill = generateBill("1", 8, 2021); // valida
			// Bill bill = generateBill("1", 9, 2021); // valida
			// Bill bill = generateBill("2", 8, 2021); // valida
			// Bill bill = generateBill("6", 10, 2021); // usuario inexistente
			// Bill bill = generateBill("1", 11, 2022); // periodo invalido
			// Bill bill = generateBill("2", 10, 2023); // periodo invalido

			System.out.println(bill.toString());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static Bill generateBill(String userId, int monthInt, int yearInt) throws Exception {
		User user = data.users.stream().filter(u -> u.getId() == userId).findFirst().orElse(null);

		if (Objects.isNull(user))
			throw new NullPointerException("Error in Main.generateBill(): User not found");

		Month month = getMonthByInt(monthInt);

		if (Objects.isNull(month))
			throw new NullPointerException(
					"Error in Main.generateBill(): monthInt has to be an integer between 1 and 12");

		LocalDate today = LocalDate.now();
		if (yearInt > today.getYear() || (yearInt == today.getYear() && month.compareTo(today.getMonth()) == 1))
			throw new Exception("Error in Main.generateBill(): Period has to be less than or equals to "
					+ today.getMonthValue() + "/" + today.getYear());

		List<Call> filtered = data.calls.stream().filter(c -> c.getCallerUser().getId() == userId
				&& c.getStartDateTime().getMonth() == month && c.getStartDateTime().getYear() == yearInt).toList();
		ArrayList<Call> calls = new ArrayList<Call>(filtered);

		return new Bill(user, month, yearInt, calls);
	}

	private static Month getMonthByInt(int monthInt) {
		if (monthInt < 1 || monthInt > 12)
			return null;
		Month[] months = { Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY, Month.JUNE, Month.JULY,
				Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER };
		return months[monthInt - 1];
	}

}
