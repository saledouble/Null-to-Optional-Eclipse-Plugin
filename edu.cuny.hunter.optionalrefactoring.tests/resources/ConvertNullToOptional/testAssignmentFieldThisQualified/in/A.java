package p;

public class A {

	Object a = new Object();
	Object b = new Object();
	Object nullControl = new Object();
	Object control = new Object();

	void fieldAssignmentTest() {

		/** should seed: { "a", "nullControl"}
		 * 	should propagate: {{"a","b"},{"nullControl"}}
		 */
		A.this.a = null;
		A.this.b = A.this.a;
		A.this.nullControl = null;
	}
}
