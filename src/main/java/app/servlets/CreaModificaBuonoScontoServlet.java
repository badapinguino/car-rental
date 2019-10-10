package app.servlets;

import app.DTO.BuonoScontoDTO;
import app.exceptions.buonoSconto.CodiceScontoTroppoLungoException;
import app.exceptions.buonoSconto.ValoreNegativoOZeroException;
import app.exceptions.buonoSconto.ValorePercentualeOltreCentoException;
import app.services.BuoniScontoService;
import app.services.UtentiService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CreaModificaBuonoScontoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codiceScontoDaModificare = req.getParameter("codiceScontoDaModificare");
        HttpSession session = req.getSession();
        //controllo se sta cercando di modificare un superuser
        if(codiceScontoDaModificare!=null){
            if(UtentiService.getUtentiService().controllaSeSuperuserByCF( (String) session.getAttribute("codiceFiscaleSessione")) ){
                BuonoScontoDTO bsDTO = BuoniScontoService.getBuoniScontoService().selezionaBuonoScontoByCodice(codiceScontoDaModificare);
                req.setAttribute("datiBuonoScontoDaModificare", bsDTO);
            }
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/creaModificaBuonoSconto.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codiceSconto = req.getParameter("codiceSconto");
        float valore = Float.parseFloat(req.getParameter("valore"));
        boolean percentuale = Boolean.parseBoolean(req.getParameter("percentuale"));
        try {
            BuonoScontoDTO buonoScontoCheInseriremo = BuoniScontoService.getBuoniScontoService().selezionaBuonoScontoByCodice(codiceSconto);
            BuoniScontoService.getBuoniScontoService().salvaBuonoSconto(codiceSconto, valore, percentuale);
            req.setAttribute("buonoScontoInserito", true);
        }catch(CodiceScontoTroppoLungoException cstle){
            req.setAttribute("buonoScontoInserito", false);
            req.setAttribute("erroreCodiceScontoLungo", true);
        }catch(ValoreNegativoOZeroException vnoze){
            req.setAttribute("buonoScontoInserito", false);
            req.setAttribute("valoreNegativoOZero", true);
        }catch(ValorePercentualeOltreCentoException vpoce){
            req.setAttribute("buonoScontoInserito", false);
            req.setAttribute("valorePercentualeOltreCento", true);
        }catch(Exception e){
            req.setAttribute("buonoScontoInserito", false);
            e.printStackTrace();
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/creaModificaBuonoSconto.jsp");
        requestDispatcher.forward(req, resp);
    }
}
