import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		Data data = new Data();

		// Busco user 1
		User user1 = data.users.stream().filter(u -> u.getId() == "1").findFirst().orElse(null);

		// Busco llamadas de agosto del usuario 1
		List<Call> filtered = data.calls.stream()
				.filter(c -> c.getCallerUser().getId() != "1" || c.getStartDateTime().getMonth() == Month.AUGUST)
				.toList();
		ArrayList<Call> calls = new ArrayList<Call>(filtered);

		Bill augustBill = new Bill(user1, Month.AUGUST, calls);
		System.out.println(augustBill.toString());
	}

}
