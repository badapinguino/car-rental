package app.servlets;

import app.DTO.VeicoloDTO;
import app.model.entities.Veicolo;
import app.services.VeicoliService;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.PersistenceException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ParcoAutoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<VeicoloDTO> listaVeicoli = VeicoliService.getVeicoliService().selezionaTuttiVeicoli();
        req.setAttribute("listaVeicoli", listaVeicoli);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/parcoAuto.jsp");
        requestDispatcher.forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String veicoloDaEliminare = req.getParameter("veicoloDaEliminare");
        if(veicoloDaEliminare!=null && veicoloDaEliminare.equals("true")){
            String codiceMezzoDaEliminare = req.getParameter("codiceMezzoDaEliminare");
            try {
                VeicoloDTO veicoloDTOEliminato = VeicoliService.getVeicoliService().eliminaVeicoloByCodice(codiceMezzoDaEliminare);
                req.setAttribute("veicoloDTOEliminato", veicoloDTOEliminato);
            }catch (PersistenceException pe){
                req.setAttribute("erroreChiaveEsterna", "Errore. Impossibile eliminare il veicolo in quanto è già registrato in almeno una prenotazione.");
            }
        }
        doGet(req, resp);
    }
}
