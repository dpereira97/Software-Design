package i18n;

aspect pt_PT {
	before() : execution(* main.Main.main(..)) {
		I18N.setInstance(new I18N("pt","PT"));
		System.err.println("This product speaks portuguese.");
	}
}

