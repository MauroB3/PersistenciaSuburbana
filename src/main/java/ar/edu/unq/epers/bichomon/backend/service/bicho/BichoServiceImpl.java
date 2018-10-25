package ar.edu.unq.epers.bichomon.backend.service.bicho;

import ar.edu.unq.epers.bichomon.backend.dao.NivelDAO;
import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.*;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.Duelo;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.nivel.NivelManager;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieService;
import ar.edu.unq.epers.bichomon.backend.service.nivel.NivelServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.UbicacionIncorrectaException;

public class BichoServiceImpl implements BichoService{

    private HibernateBichoDAO bichoDAO;
    private HibernateEntrenadorDAO entrenadorDAO;
    private HibernateEspecieDAO especieDAO;
    private NivelServiceImpl nivelService;
    private HibernateUbicacionDAO ubicacionDAO;
    private HibernateExperienciaDAO experienciaDAO;

    public BichoServiceImpl(HibernateBichoDAO bichoDAO, HibernateEntrenadorDAO entrenadorDAO, HibernateEspecieDAO especieDAO, NivelServiceImpl nivelService, HibernateUbicacionDAO ubicacionDAO, HibernateExperienciaDAO experienciaDAO) {
        this.bichoDAO = bichoDAO;
        this.entrenadorDAO = entrenadorDAO;
        this.especieDAO = especieDAO;
        this.nivelService = nivelService;
        this.ubicacionDAO = ubicacionDAO;
        this.experienciaDAO = experienciaDAO;
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
        return Runner.runInSession( () -> {
            Entrenador entrenador1 = entrenadorDAO.recuperar(entrenador);
            Bicho bichoEncontrado = entrenador1.ubicacion().buscar(entrenador1, nivelService.getNivelManager(), experienciaDAO.obtenerExperiencia("Captura"));
            return bichoEncontrado;
        });
    }

    //El entrenador tiene que tener si o si al bicho
    @Override
    public void abandonar(String nombreEntrenador, int nroBicho) {
        Runner.runInSession( () -> {
            Bicho bicho = this.bichoDAO.recuperar(nroBicho);
            Entrenador entrenador = this.entrenadorDAO.recuperar(nombreEntrenador);
            entrenador.abandonarBicho(bicho);
            return null;
        });

    }

    @Override
    public ResultadoCombate duelo(String entrenador, int bicho) {
        return Runner.runInSession(() -> {
           Entrenador ent = entrenadorDAO.recuperar(entrenador);
           if(ent.ubicacion().esDojo()){
               Entrenador entrenadorCampeon = entrenadorDAO.recuperar(ent.ubicacion().getCampeon().getBicho().getEntrenador().nombre());
               ent.agregarExperiencia(experienciaDAO.obtenerExperiencia("Combate"));
               entrenadorCampeon.agregarExperiencia(experienciaDAO.obtenerExperiencia("Combate"));
               Bicho bichoRetador = bichoDAO.recuperar(bicho);
               Ubicacion dojo = ent.ubicacion();
               Duelo duelo = new Duelo(dojo, bichoRetador);

               entrenadorDAO.actualizar(ent);
               entrenadorDAO.actualizar(entrenadorCampeon);

               ResultadoCombate resultado = duelo.pelear();
               ubicacionDAO.actualizarCampeon(dojo, resultado.getGanador());
               return resultado;
           }else{
               throw new UbicacionIncorrectaException(ent.ubicacion().getNombre(), "Dojo");
           }
        });
    }

    public boolean puedeEvolucionar(String entrenador, int idBicho) {
        return Runner.runInSession(() -> {
            Bicho bicho = bichoDAO.recuperar(idBicho);
            return bicho.puedeEvolucionar(nivelService.getNivelManager());
        });
    }

    @Override
    public Bicho evolucionar(String entrenador, int idBicho) {
        return Runner.runInSession(() -> {
            Bicho bicho = bichoDAO.recuperar(idBicho);
            bicho.evolucionar(especieDAO.siguienteEvolucion(bicho.getEspecie()), nivelService.getNivelManager());
            /* si no logra pasar el if del modelo no le incrementa la experiencia al entrenador */
            Entrenador entrenadorBicho = entrenadorDAO.recuperar(entrenador);
            entrenadorBicho.agregarExperiencia(experienciaDAO.obtenerExperiencia("Evolucion"));
            return bicho;
        });
    }

}
