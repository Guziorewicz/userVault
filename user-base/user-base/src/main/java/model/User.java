package model;

public class User {
	public static int ID_COUNTER = 1;
	public int id;
	public String name;
	public String email;
	public int age;

	public User(String name, String email, int age) {
		this.id = ID_COUNTER++;
		this.name = name;
		this.email = email;
		this.age = age;
	}

	public String toString() {
		return id + ": " + name + " (" + age + ") " + email;
	}

	public String toJSON() {
		return String.format("{\"id\":%d,\"name\":\"%s\",\"email\":\"%s\",\"age\":%d}", 
								id, name, email, age);
	}

	public static User fromJSON(String json) {
		try {

			String[] parts = json.replaceAll("[{}]", "").replace("\"", "").split(",");

			int id = 0, age = 0;
			String name = "", email = "";

			for (String part : parts) {
				String[] kv = part.split(":");

				if (kv.length != 2) throw new IllegalArgumentException("Malformed key-value pair in JSON" + part);

				switch (kv[0]) {
					case "id" -> id = Integer.parseInt(kv[1]);
					case "name" -> name = kv[1];
					case "email" -> email = kv[1];
					case "age" -> age = Integer.parseInt(kv[1]);
				}

			}
			User u = new User(name, email, age);

			u.id = id;

			ID_COUNTER = Math.max(ID_COUNTER, id + 1);
			return u;
		} catch (Exception e) {
			if (service.FileService.testMode) {
				throw new RuntimeException("Failed parse JSON" + json, e);
			}
			return null;
		}	
	}
}
