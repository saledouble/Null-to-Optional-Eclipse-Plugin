package p;

public class A {

	A a = new Object();
	A b = new Object();
	A nullControl = new Object();
	A control = new Object();

	void fieldAssignmentTest() {

		/** should seed: { "a", "nullControl"}
		 * 	should propagate: {{"a","b"},{"nullControl"}}
		 */
		A.this.a = null;
		A.this.b = A.this.a;
		A.this.nullControl = null;
	}
}
