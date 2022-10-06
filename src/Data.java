import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Data {
	public Map<String, Double> nationalCost;
	public Map<String, Double> internationalCost;
	public ArrayList<Call> calls;
	public ArrayList<User> users;

	public void fillLists() {
		// Construcción de usuarios
		this.users = new ArrayList<User>();

		User user1 = new User("1", "Nicolás Céspede", "arg", "moreno");
		User user2 = new User("2", "Emanuel Tenca", "arg", "moreno");
		User user3 = new User("3", "Soledad Spicuglia", "arg", "bella vista");
		User user4 = new User("4", "Fernando Moreira", "bra", "manaos");
		User user5 = new User("5", "Lucas Fernandez", "bol", "la paz");

		this.users.add(user1);
		this.users.add(user2);
		this.users.add(user3);
		this.users.add(user4);
		this.users.add(user5);

		// Construcción de historial de llamadas
		this.calls = new ArrayList<Call>();

		try {
			// llamada local de media hora un dia habil (jueves) entre las 8 y las 20 = $6
			this.calls.add(new Call(user1, user2, LocalDateTime.of(2021, Month.AUGUST, 5, 8, 25, 55),
					LocalDateTime.of(2021, Month.AUGUST, 5, 8, 55, 55)));

			// llamada local de media hora un dia habil (lunes) fuera de las 8 y las 20 = $3
			this.calls.add(new Call(user1, user2, LocalDateTime.of(2021, Month.AUGUST, 9, 21, 25, 55),
					LocalDateTime.of(2021, Month.AUGUST, 9, 21, 55, 55)));

			// llamada local de media hora un dia no habil (sabado) = $3
			this.calls.add(new Call(user1, user2, LocalDateTime.of(2021, Month.AUGUST, 14, 21, 25, 55),
					LocalDateTime.of(2021, Month.AUGUST, 14, 21, 55, 55)));

			// llamada nacional (desde moreno a bella vista) de media hora = $12
			this.calls.add(new Call(user1, user3, LocalDateTime.of(2021, Month.AUGUST, 18, 18, 25, 55),
					LocalDateTime.of(2021, Month.AUGUST, 18, 18, 55, 55)));

			// llamada internacional (desde argentina hasta brasil) de media hora = $18
			this.calls.add(new Call(user1, user4, LocalDateTime.of(2021, Month.AUGUST, 21, 19, 25, 55),
					LocalDateTime.of(2021, Month.AUGUST, 21, 19, 55, 55)));

			// llamada internacional (desde argentina hasta bolivia) de 50 minutos = $27,5
			this.calls.add(new Call(user1, user5, LocalDateTime.of(2021, Month.AUGUST, 28, 8, 0, 55),
					LocalDateTime.of(2021, Month.AUGUST, 28, 8, 50, 55)));

			// llamada local de 40 minutos un dia habil (lunes) fuera de las 8 y las 20 = $4
			// fuera del periodo de agosto
			this.calls.add(new Call(user1, user2, LocalDateTime.of(2021, Month.SEPTEMBER, 13, 22, 10, 55),
					LocalDateTime.of(2021, Month.SEPTEMBER, 13, 22, 50, 55)));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void fillCosts() {
		// Construcción de los costos por país y localidad
		this.nationalCost = new HashMap<String, Double>();

		this.nationalCost.put("moreno", Double.valueOf(0.25));
		this.nationalCost.put("jose clemente paz", Double.valueOf(0.3));
		this.nationalCost.put("san miguel", Double.valueOf(0.35));
		this.nationalCost.put("bella vista", Double.valueOf(0.4));

		// Costos de otros paises
		this.internationalCost = new HashMap<String, Double>();

		this.internationalCost.put("uru", Double.valueOf(0.45));
		this.internationalCost.put("par", Double.valueOf(0.5));
		this.internationalCost.put("bol", Double.valueOf(0.55));
		this.internationalCost.put("bra", Double.valueOf(0.6));
	}
}
