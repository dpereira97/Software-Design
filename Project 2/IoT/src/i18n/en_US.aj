package i18n;

public aspect en_US {
	before() : execution(* main.Main.main(..)) {
		I18N.setInstance(new I18N("en","US"));
		System.err.println("This product speaks english.");
	}
}

