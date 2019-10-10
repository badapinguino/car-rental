package app.servlets;

import app.DTO.BuonoScontoDTO;
import app.services.BuoniScontoService;

import javax.persistence.PersistenceException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CodiciScontoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<BuonoScontoDTO> listaBuoniSconto = BuoniScontoService.getBuoniScontoService().selezionaTuttiBuoniSconto();
        req.setAttribute("listaBuoniSconto", listaBuoniSconto);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/codiciSconto.jsp");
        requestDispatcher.forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String buonoScontoDaEliminare = req.getParameter("buonoScontoDaEliminare");
        if(buonoScontoDaEliminare!=null && buonoScontoDaEliminare.equals("true")){
            String codiceScontoDaEliminare = req.getParameter("codiceScontoDaEliminare");
            try {
                BuonoScontoDTO buonoScontoDTOEliminato = BuoniScontoService.getBuoniScontoService().eliminaBuonoScontoByCodice(codiceScontoDaEliminare);
                req.setAttribute("buonoScontoDTOEliminato", buonoScontoDTOEliminato);
            }catch (PersistenceException pe){
                req.setAttribute("erroreChiaveEsterna", "Errore. Impossibile eliminare il buono sconto in quanto è già stato utilizzato in almeno una prenotazione.");
            }
        }
        doGet(req, resp);
    }
}
