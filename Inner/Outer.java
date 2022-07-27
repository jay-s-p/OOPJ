class Outer {
	static class InnerStatic {
		String name;

		void print(String s) {
			System.out.println(s);
		}
	}

	static InnerStatic createClass() {
		return new InnerStatic();
	}

	public static void main(String args[]) {
		// InnerStatic x = new InnerStatic();
		InnerStatic x = createClass();
		x.print("Hello");
	}
}