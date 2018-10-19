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
            Bicho bichoEncontrado = entrenador1.ubicacion().buscar(entrenador1, nivelService.getNivelManager());

            if(bichoEncontrado != null) {
                bichoEncontrado.serAdoptado(entrenador1);
                entrenadorDAO.agregarBicho(entrenador1.nombre(), bichoEncontrado);
                bichoEncontrado.getEspecie().incrementarPopularidad();
                bichoDAO.actualizar(bichoEncontrado);

                /* Se aumenta la experiencia a ambos entrenadores */
                entrenadorDAO.aumentarExperiencia(entrenador, experienciaDAO.obtenerExperiencia("Captura"));

                return bichoEncontrado;
            }
            else {
                return null;
            }

        });
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
                    bicho.getEspecie().decrementarPopularidad();
                    bichoDAO.actualizar(bicho);
                }
            }
            else {
                throw new UbicacionIncorrectaException(entrenador.ubicacion().getNombre(), "guarderia");
            }
            return null;
        });

    }

    @Override
    public ResultadoCombate duelo(String entrenador, int bicho) {
        return Runner.runInSession(() -> {
           Entrenador ent = entrenadorDAO.recuperar(entrenador);
           if(ent.ubicacion().esDojo()){
               Bicho bichoRetador = bichoDAO.recuperar(bicho);
               Ubicacion dojo = ent.ubicacion();
               Duelo duelo = new Duelo(dojo, bichoRetador);
               ResultadoCombate resultado = duelo.pelear();
               /* Se aumenta la experiencia a ambos entrenadores */
               entrenadorDAO.aumentarExperiencia(ent.nombre(), experienciaDAO.obtenerExperiencia("Combate"));
               entrenadorDAO.aumentarExperiencia(ent.ubicacion().getCampeon().getBicho().getEntrenador().nombre(), experienciaDAO.obtenerExperiencia("Combate"));

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
            return bicho.puedeEvolucionar(nivelService.getNivelManager()) && this.tieneSiguienteEvolucion(bicho.getEspecie());
        });
    }

    private boolean tieneSiguienteEvolucion(Especie especie){
        return especieDAO.siguienteEvolucion(especie) != null;
    }

    @Override
    public Bicho evolucionar(String entrenador, int idBicho) {
        return Runner.runInSession(() -> {
            Bicho bicho = bichoDAO.recuperar(idBicho);
            Especie especie = especieDAO.recuperar(bicho.getEspecie().getNombre());
            if(puedeEvolucionar(entrenador, bicho.getID())){
                especie.decrementarPopularidad();
                bicho.evolucionar(especieDAO.siguienteEvolucion(bicho.getEspecie()));
                bicho.getEspecie().incrementarPopularidad();
                bichoDAO.actualizar(bicho);
                especieDAO.actualizar(especie);
                /* Se aumenta la experiencia a ambos entrenadores */
                entrenadorDAO.aumentarExperiencia(entrenador, experienciaDAO.obtenerExperiencia("Evolucion"));
            }else{
                throw new BichoNoPuedeEvolucionarException(bicho.getEspecie().getNombre());
            }
            return bicho;
        });
    }

}
