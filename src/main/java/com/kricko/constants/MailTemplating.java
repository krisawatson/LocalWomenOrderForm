package com.kricko.constants;

public class MailTemplating
{
	// Email template folders
	public static final String TMPL_FOLDER = "templates/email/";
	public static final String TMPL_ORDER_FOLDER = TMPL_FOLDER + "/order";
	
	// Email template files
	public static final String TMPL_ORDER_CONFIRMATION = "confirmation.html";
	public static final String TMPL_COPYRIGHT = "copyright.html";
	public static final String TMPL_FOOTER = "footer.html";
	public static final String TMPL_TERMS_AND_CONDITIONS = "terms-conditions.html";
	
	// Email template string values
    public static final String DISCLAIMER = "This is an automated mail, please do not reply to this";
    public static final String COPYRIGHT = "Copyright &copy; ${year} Local Women Magazine. Local Women Magazine is a trade mark of Marc Media Ltd.";
}
