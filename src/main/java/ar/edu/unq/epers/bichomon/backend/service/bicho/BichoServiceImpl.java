package ar.edu.unq.epers.bichomon.backend.service.bicho;

import ar.edu.unq.epers.bichomon.backend.dao.NivelDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateBichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateNivelDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.nivel.NivelManager;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieService;
import ar.edu.unq.epers.bichomon.backend.service.nivel.NivelServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.UbicacionIncorrectaException;

public class BichoServiceImpl implements BichoService{

    private HibernateBichoDAO bichoDAO;
    private HibernateEntrenadorDAO entrenadorDAO;
    private HibernateEspecieDAO especieDAO;
    private NivelServiceImpl nivelService;

    public BichoServiceImpl(HibernateBichoDAO bichoDAO, HibernateEntrenadorDAO entrenadorDAO, HibernateEspecieDAO especieDAO, NivelServiceImpl nivelService) {
        this.bichoDAO = bichoDAO;
        this.entrenadorDAO = entrenadorDAO;
        this.especieDAO = especieDAO;
        this.nivelService = nivelService;
    }

    @Override
    public Bicho getBicho(int idBicho) {
        return Runner.runInSession( () -> {
           return bichoDAO.recuperar(idBicho);
        });

    }

    @Override
    public void crearBicho(Bicho bicho){
        Runner.runInSession( () -> {
            this.bichoDAO.guardar(bicho);
            return null;
        });
    }

    @Override
    public Bicho buscar(String entrenador) {
        return null;
    }

    //El entrenador tiene que tener si o si al bicho
    @Override
    public void abandonar(String nombreEntrenador, int nroBicho) {
        Runner.runInSession( () -> {
            Bicho bicho = this.bichoDAO.recuperar(nroBicho);
            Entrenador entrenador = this.entrenadorDAO.recuperar(nombreEntrenador);
            NivelManager nivelManager = nivelService.getNivelManager();
            if(entrenador.ubicacion().esGuarderia()) {
                if(entrenador.puedeAbandonarBicho()) {
                    entrenador.abandonarBicho(bicho);
                    entrenador.ubicacion().abandonarBicho(bicho);
                    entrenadorDAO.actualizar(entrenador);
                    bichoDAO.abandonarBicho(bicho);
                    especieDAO.decrementarPopularidad(bicho.getEspecie().getNombre());
                }
            }
            else {
                throw new UbicacionIncorrectaException(entrenador.ubicacion().getNombre(), "guarderia");
            }
            return null;
        });

    }

    @Override
    public ResultadoCombate duelo(String entrenador) {
        return null;
    }

    public boolean puedeEvolucionar(String entrenador, int idBicho) {
        return Runner.runInSession(() -> {
            return bichoDAO.recuperar(idBicho).puedeEvolucionar(nivelService.getNivelManager());
        });
    }

    @Override
    public Bicho evolucionar(String entrenador, int bicho) {
        return null;
    }
}
