package org.foi.nwtis.ftocko.aplikacija_6.provjereautorizacije;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;

@WebFilter(urlPatterns = { "/app/*" })
public class FilterAutorizacije extends HttpFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private FilterConfig filterConfig = null;

	private UpraviteljSesije us = new UpraviteljSesije();

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void destroy() {

		this.filterConfig = null;
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		boolean prijavljen = us.provjeriSesiju(req, res);
		String putanja = "";

		try {
			HttpServletRequest hsr = (HttpServletRequest) req;
			putanja = hsr.getServletPath();

		}

		catch (Exception e) {
			System.out.println("Problem kod servleta!");
		}

		if (prijavljen) {

			switch (putanja) {
			case "/app/pregledKorisnika.xhtml":
				RequestDispatcher rdk = filterConfig.getServletContext()
						.getRequestDispatcher("/app/pregledKorisnika.xhtml");
				rdk.forward(req, res);
				break;
			case "/app/pregledJmsPoruka.xhtml":
				RequestDispatcher rdj = filterConfig.getServletContext()
						.getRequestDispatcher("/app/pregledJmsPoruka.xhtml");
				rdj.forward(req, res);
				break;
			case "/app/pregledAerodroma.xhtml":
				RequestDispatcher rda = filterConfig.getServletContext()
						.getRequestDispatcher("/app/pregledAerodroma.xhtml");
				rda.forward(req, res);
				break;
			}

		}

		else {
			RequestDispatcher rd = filterConfig.getServletContext()
					.getRequestDispatcher("/upravljanjePogreskama.xhtml");
			rd.forward(req, res);
		}

	}

}
