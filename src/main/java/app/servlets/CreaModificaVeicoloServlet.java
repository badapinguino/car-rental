package app.servlets;

import app.DTO.VeicoloDTO;
import app.exceptions.veicolo.*;
import app.services.UtentiService;
import app.services.VeicoliService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CreaModificaVeicoloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codiceMezzoDaModificare = req.getParameter("codiceMezzoDaModificare");
        HttpSession session = req.getSession();
        //controllo se sta cercando di modificare un superuser
        if(codiceMezzoDaModificare!=null){
            if(UtentiService.getUtentiService().controllaSeSuperuserByCF( (String) session.getAttribute("codiceFiscaleSessione")) ){
                VeicoloDTO vDTO = VeicoliService.getVeicoliService().selezionaVeicoloByCodice(codiceMezzoDaModificare);
                req.setAttribute("datiVeicoloDaModificare", vDTO);
            }
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/creaModificaVeicolo.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String casaCostruttriceDaInserire = req.getParameter("casaCostruttrice");
        String modelloDaInserire = req.getParameter("modello");
        String codiceMezzoDaInserire= req.getParameter("codiceMezzo");
        String targaDaInserire= req.getParameter("targa");
        int annoDaInserire= Integer.parseInt(req.getParameter("anno"));
        float prezzoGiornataDaInserire= Float.parseFloat(req.getParameter("prezzoGiornata"));
        String tipologiaDaInserire= req.getParameter("tipologia");
        try {
            VeicoloDTO veicoloCheInseriremo = VeicoliService.getVeicoliService().selezionaVeicoloByCodice(codiceMezzoDaInserire);
//            if(veicoloCheInseriremo.getCodiceMezzo() != null){ // in questo caso il veicolo esiste già e lo andiamo a modificare
            VeicoliService.getVeicoliService().salvaVeicolo(codiceMezzoDaInserire, targaDaInserire, casaCostruttriceDaInserire, modelloDaInserire, tipologiaDaInserire, annoDaInserire, prezzoGiornataDaInserire);
//            }
            req.setAttribute("veicoloInserito", true);
        }catch(CodiceMezzoTroppoLungoException cmtle){
            req.setAttribute("veicoloInserito", false);
            req.setAttribute("erroreCodiceMezzoLungo", true);
        }catch(TargaTroppoLungaException ttle){
            req.setAttribute("veicoloInserito", false);
            req.setAttribute("erroreTargaLunga", true);
        }catch(CasaCostruttriceTroppoLungaException cctle){
            req.setAttribute("veicoloInserito", false);
            req.setAttribute("erroreCasaCostruttriceLunga", true);
        }catch(ModelloTroppoLungoException mtle){
            req.setAttribute("veicoloInserito", false);
            req.setAttribute("erroreModelloLungo", true);
        }catch(AnnoTroppoPiccoloException atpe){
            req.setAttribute("veicoloInserito", false);
            req.setAttribute("erroreAnnoPiccolo", true);
        }catch(PrezzoGiornataTroppoBassoException pgtbe){
            req.setAttribute("veicoloInserito", false);
            req.setAttribute("errorePrezzoGiornataBasso", true);
        }catch(TipologiaTroppoLungaException tttle){
            req.setAttribute("veicoloInserito", false);
            req.setAttribute("erroreTipologiaLunga", true);
        }catch(Exception e){
            req.setAttribute("veicoloInserito", false);
            e.printStackTrace();
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/creaModificaVeicolo.jsp");
        requestDispatcher.forward(req, resp);
    }
}
