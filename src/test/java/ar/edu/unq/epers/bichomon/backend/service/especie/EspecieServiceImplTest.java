package ar.edu.unq.epers.bichomon.backend.service.especie;

import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateBichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.condicion.Condicion;
import ar.edu.unq.epers.bichomon.backend.model.condicion.CondicionCompuesta;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.bicho.BichoServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class EspecieServiceImplTest {

    private EspecieServiceImpl service;
    private HibernateEspecieDAO hibernateEspecieDAO;
    private Condicion condicion;
    private BichoServiceImpl bichoService;

    private Especie pikachu;

    private Especie raichu;

    private Especie charmander;

    private Especie charmeleon;

    private Especie charizard;

    private Especie squirtle;

    private Especie wartortle;

    private Especie blastoise;

    private Especie onix;

    private Especie articuno;

    private Especie zapdos;

    private Especie moltres;

    private Especie lugia;

    private Especie mewtow;

    private ArrayList<Especie> especies;

    private Entrenador entrenador;

    @Before
    public void setUp() {
        bichoService = new BichoServiceImpl(new HibernateBichoDAO());
        hibernateEspecieDAO = new HibernateEspecieDAO();
        service = new EspecieServiceImpl(hibernateEspecieDAO);
        condicion = new CondicionCompuesta();

        pikachu = new Especie("Pikachu", TipoBicho.ELECTRICIDAD, condicion,55,99,100);
        raichu = new Especie(pikachu,2,condicion,"Raichu",80,110,300);

        charmander = new Especie("Charmander", TipoBicho.FUEGO, condicion, 55, 75, 110);
        charmeleon = new Especie(charmander,2,condicion,"Charmeleon",88,100,300);
        charizard = new Especie(charmander,3,condicion,"Charizard",120,220,750);

        squirtle = new Especie("Squirtle", TipoBicho.AGUA, condicion, 55, 56, 115 );
        wartortle = new Especie(squirtle,2,condicion,"Wartortle",70,81,279);
        blastoise = new Especie(squirtle,3,condicion,"Blastoise",101,110,554);

        onix = new Especie("Onix",TipoBicho.CHOCOLATE,condicion,257,300,446);
        articuno = new Especie("Articuno",TipoBicho.CHOCOLATE,condicion,110,85,1551);
        zapdos = new Especie("Zapdos",TipoBicho.ELECTRICIDAD,condicion,115,71,1551);
        moltres = new Especie("Moltres",TipoBicho.FUEGO,condicion,105,88,1551);
        lugia = new Especie("Lugia",TipoBicho.AGUA,condicion,120,91,2641);
        mewtow = new Especie("MewTwo",TipoBicho.CHOCOLATE,condicion,100,70,9999);

        especies = new ArrayList<Especie>();

        service.crearEspecie(pikachu);
        service.crearEspecie(raichu);
        service.crearEspecie(charmander);
        service.crearEspecie(charmeleon);
        service.crearEspecie(charizard);
        service.crearEspecie(squirtle);
        service.crearEspecie(wartortle);
        service.crearEspecie(blastoise);
        service.crearEspecie(onix);
        service.crearEspecie(articuno);
        service.crearEspecie(zapdos);
        service.crearEspecie(moltres);
        service.crearEspecie(lugia);
        service.crearEspecie(mewtow);

        /*---------------------------------------------------------------------------*/
        Bicho bicho1 = service.crearBicho("Pikachu", entrenador);
        bicho1.serAbandonado();
        bichoService.crearBicho(bicho1);
        Bicho bicho2 = service.crearBicho("Pikachu", entrenador);
        bichoService.crearBicho(bicho2);
        Bicho bicho3 = service.crearBicho("Pikachu", entrenador);
        bichoService.crearBicho(bicho3);
        Bicho bicho4 = service.crearBicho("Pikachu", entrenador);
        bichoService.crearBicho(bicho4);
        Bicho bicho5 = service.crearBicho("Pikachu", entrenador);
        bichoService.crearBicho(bicho5);
        Bicho bicho6 = service.crearBicho("Pikachu", entrenador);
        bicho6.serAbandonado();
        bichoService.crearBicho(bicho6);
        Bicho bicho7 = service.crearBicho("Pikachu", entrenador);
        bichoService.crearBicho(bicho7);
        Bicho bicho8 = service.crearBicho("Pikachu", entrenador);
        bichoService.crearBicho(bicho8);
        Bicho bicho9 = service.crearBicho("Pikachu", entrenador);
        bichoService.crearBicho(bicho9);
        Bicho bicho10 = service.crearBicho("Pikachu", entrenador);
        bichoService.crearBicho(bicho10);
        Bicho bicho11 = service.crearBicho("Pikachu", entrenador);
        bichoService.crearBicho(bicho11);
        Bicho bicho12 = service.crearBicho("Pikachu", entrenador);
        bichoService.crearBicho(bicho12);
        Bicho bicho13 = service.crearBicho("Pikachu", entrenador);
        bichoService.crearBicho(bicho13);
        Bicho bicho14 = service.crearBicho("Pikachu", entrenador);
        bichoService.crearBicho(bicho14);
        Bicho bicho15 = service.crearBicho("Pikachu", entrenador);
        bichoService.crearBicho(bicho15);

        Bicho bicho16 = service.crearBicho("Raichu", entrenador);
        bichoService.crearBicho(bicho16);

        /*---------------------------------------------------------------------------*/
        Bicho bicho17 = service.crearBicho("Charmander", entrenador);
        bicho17.serAbandonado();
        bichoService.crearBicho(bicho17);
        Bicho bicho18 = service.crearBicho("Charmander", entrenador);
        bicho18.serAbandonado();
        bichoService.crearBicho(bicho18);
        Bicho bicho19 = service.crearBicho("Charmander", entrenador);
        bichoService.crearBicho(bicho19);
        Bicho bicho20 = service.crearBicho("Charmander", entrenador);
        bichoService.crearBicho(bicho20);
        Bicho bicho21 = service.crearBicho("Charmander", entrenador);
        bichoService.crearBicho(bicho21);
        Bicho bicho22 = service.crearBicho("Charmander", entrenador);
        bichoService.crearBicho(bicho22);
        Bicho bicho23 = service.crearBicho("Charmander", entrenador);
        bichoService.crearBicho(bicho23);
        Bicho bicho24 = service.crearBicho("Charmander", entrenador);
        bicho24.serAbandonado();
        bichoService.crearBicho(bicho24);

        Bicho bicho25 = service.crearBicho("Charmeleon", entrenador);
        bichoService.crearBicho(bicho25);
        Bicho bicho26 = service.crearBicho("Charmeleon", entrenador);
        bichoService.crearBicho(bicho26);
        Bicho bicho27 = service.crearBicho("Charmeleon", entrenador);
        bichoService.crearBicho(bicho27);
        Bicho bicho28 = service.crearBicho("Charmeleon", entrenador);
        bichoService.crearBicho(bicho28);

        Bicho bicho29 = service.crearBicho("Charizard", entrenador);
        bichoService.crearBicho(bicho29);
        Bicho bicho30 = service.crearBicho("Charizard", entrenador);
        bichoService.crearBicho(bicho30);
        Bicho bicho31 = service.crearBicho("Charizard", entrenador);
        bichoService.crearBicho(bicho31);
        Bicho bicho32 = service.crearBicho("Charizard", entrenador);
        bichoService.crearBicho(bicho32);
        Bicho bicho33 = service.crearBicho("Charizard", entrenador);
        bichoService.crearBicho(bicho33);
        Bicho bicho34 = service.crearBicho("Charizard", entrenador);
        bichoService.crearBicho(bicho34);
        Bicho bicho35 = service.crearBicho("Charizard", entrenador);
        bichoService.crearBicho(bicho35);
        Bicho bicho36 = service.crearBicho("Charizard", entrenador);
        bichoService.crearBicho(bicho36);
        Bicho bicho37 = service.crearBicho("Charizard", entrenador);
        bichoService.crearBicho(bicho37);
        Bicho bicho38 = service.crearBicho("Charizard", entrenador);
        bichoService.crearBicho(bicho38);

        /*---------------------------------------------------------------------------*/
        Bicho bicho39 = service.crearBicho("Squirtle", entrenador);
        bicho39.serAbandonado();
        bichoService.crearBicho(bicho39);

        Bicho bicho40 = service.crearBicho("Wartortle", entrenador);
        bichoService.crearBicho(bicho40);
        Bicho bicho41 = service.crearBicho("Wartortle", entrenador);
        bichoService.crearBicho(bicho41);
        Bicho bicho42 = service.crearBicho("Wartortle", entrenador);
        bichoService.crearBicho(bicho42);
        Bicho bicho43 = service.crearBicho("Wartortle", entrenador);
        bichoService.crearBicho(bicho43);
        Bicho bicho44 = service.crearBicho("Wartortle", entrenador);
        bichoService.crearBicho(bicho44);
        Bicho bicho45 = service.crearBicho("Wartortle", entrenador);
        bichoService.crearBicho(bicho45);

        Bicho bicho46 = service.crearBicho("Blastoise", entrenador);
        bichoService.crearBicho(bicho46);
        Bicho bicho47 = service.crearBicho("Blastoise", entrenador);
        bichoService.crearBicho(bicho47);
        Bicho bicho48 = service.crearBicho("Blastoise", entrenador);
        bichoService.crearBicho(bicho48);
        Bicho bicho49 = service.crearBicho("Blastoise", entrenador);
        bichoService.crearBicho(bicho49);
        Bicho bicho50 = service.crearBicho("Blastoise", entrenador);
        bichoService.crearBicho(bicho50);
        Bicho bicho51 = service.crearBicho("Blastoise", entrenador);
        bichoService.crearBicho(bicho51);
        Bicho bicho52 = service.crearBicho("Blastoise", entrenador);
        bichoService.crearBicho(bicho52);
        Bicho bicho53 = service.crearBicho("Blastoise", entrenador);
        bichoService.crearBicho(bicho53);
        Bicho bicho54 = service.crearBicho("Blastoise", entrenador);
        bichoService.crearBicho(bicho54);
        Bicho bicho55 = service.crearBicho("Blastoise", entrenador);
        bichoService.crearBicho(bicho55);
        Bicho bicho56 = service.crearBicho("Blastoise", entrenador);
        bichoService.crearBicho(bicho56);
        /*---------------------------------------------------------------------------*/
        Bicho bicho57 = service.crearBicho("Articuno", entrenador);
        bichoService.crearBicho(bicho57);
        Bicho bicho58 = service.crearBicho("Zapdos", entrenador);
        bichoService.crearBicho(bicho58);
        Bicho bicho59 = service.crearBicho("Zapdos", entrenador);
        bichoService.crearBicho(bicho59);
        Bicho bicho60 = service.crearBicho("Moltres", entrenador);
        bichoService.crearBicho(bicho60);
        Bicho bicho61 = service.crearBicho("Moltres", entrenador);
        bichoService.crearBicho(bicho61);
        Bicho bicho62 = service.crearBicho("Moltres", entrenador);
        bichoService.crearBicho(bicho62);
        Bicho bicho63 = service.crearBicho("Lugia", entrenador);
        bicho63.serAbandonado();
        bichoService.crearBicho(bicho63);
        /*---------------------------------------------------------------------------*/
        Bicho bicho64 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho64);
        Bicho bicho65 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho65);
        Bicho bicho66 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho66);
        Bicho bicho67 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho67);
        Bicho bicho68 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho68);
        Bicho bicho69 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho69);
        Bicho bicho70 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho70);
        Bicho bicho71 = service.crearBicho("Onix", entrenador);
        bicho71.serAbandonado();
        bichoService.crearBicho(bicho71);
        Bicho bicho72 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho72);
        Bicho bicho73 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho73);
        Bicho bicho74 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho74);
        Bicho bicho75 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho75);
        Bicho bicho76 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho76);
        Bicho bicho77 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho77);
        Bicho bicho78 = service.crearBicho("Onix", entrenador);
        bicho78.serAbandonado();
        bichoService.crearBicho(bicho78);
        Bicho bicho79 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho79);
        Bicho bicho80 = service.crearBicho("Onix", entrenador);
        bicho80.serAbandonado();
        bichoService.crearBicho(bicho80);
        Bicho bicho81 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho81);
        Bicho bicho82 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho82);
        Bicho bicho83 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho83);
        Bicho bicho84 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho84);
        Bicho bicho85 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho85);
        Bicho bicho86 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho86);
        Bicho bicho87 = service.crearBicho("Onix", entrenador);
        bicho87.serAbandonado();
        bichoService.crearBicho(bicho87);
        Bicho bicho88 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho88);
        Bicho bicho89 = service.crearBicho("Onix", entrenador);
        bicho89.serAbandonado();
        bichoService.crearBicho(bicho89);
        Bicho bicho90 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho90);
        Bicho bicho91 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho91);
        Bicho bicho92 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho92);
        Bicho bicho93 = service.crearBicho("Onix", entrenador);
        bichoService.crearBicho(bicho93);
        /*---------------------------------------------------------------------------*/
        Bicho bicho94 = service.crearBicho("MewTwo", entrenador);
        bichoService.crearBicho(bicho94);

        service.decrementarPopularidad("Pikachu");
        service.decrementarPopularidad("Pikachu");
        service.decrementarPopularidad("Charmander");
        service.decrementarPopularidad("Charmander");
        service.decrementarPopularidad("Charmander");
        service.decrementarPopularidad("Squirtle");
        service.decrementarPopularidad("Lugia");
        service.decrementarPopularidad("Onix");
        service.decrementarPopularidad("Onix");
        service.decrementarPopularidad("Onix");
        service.decrementarPopularidad("Onix");
        service.decrementarPopularidad("Onix");
    }

    @After
    public void cleanUp(){
            //Destroy cierra la session factory y fuerza a que, la proxima vez, una nueva tenga
            //que ser creada.
            //
            //Al tener hibernate configurado con esto <property name="hibernate.hbm2ddl.auto">create-drop</property>
            //al crearse una nueva session factory todo el schema será destruido y creado desde cero.
            SessionFactoryProvider.destroy();
    }

    @Test
    public void testCrearEspecieYGetEspecie() {
        assertEquals(pikachu.getNombre(), service.getEspecie("Pikachu").getNombre());
    }

    @Test(expected = EspecieNoExistente.class)
    public void testEspecieNoExistenteException(){
        assertEquals("PapaFrita", service.getEspecie("PapaFrita").getNombre());
    }

    @Test
    public void getAllEspecies() {
        especies.add(pikachu);
        especies.add(raichu);
        especies.add(charmander);
        especies.add(charmeleon);
        especies.add(charizard);
        especies.add(squirtle);
        especies.add(wartortle);
        especies.add(blastoise);
        especies.add(onix);
        especies.add(articuno);
        especies.add(zapdos);
        especies.add(moltres);
        especies.add(lugia);
        especies.add(mewtow);

        assertEquals(especies.size(), service.getAllEspecies().size());
    }

    @Test
    public void crearBicho() {
        assertEquals(30, service.getEspecie("Onix").getCantidadBichos(),0);
    }

    @Test
    public void actualizarAltura(){
        Especie especie = service.getEspecie("Pikachu");
        especie.setAltura(60);
        service.actualizar(especie);

        assertEquals(60,service.getEspecie("Pikachu").getAltura(),0);
    }

    @Test
    public void populares(){
        assertEquals(10, service.populares().size());
        assertEquals(25, service.populares().get(0).getPopularidad());
        assertEquals(13, service.populares().get(1).getPopularidad());
        assertEquals(11, service.populares().get(2).getPopularidad());
        assertEquals(10, service.populares().get(3).getPopularidad());
        assertEquals(6, service.populares().get(4).getPopularidad());
        assertEquals(5, service.populares().get(5).getPopularidad());
        assertEquals(4, service.populares().get(6).getPopularidad());
        assertEquals(3, service.populares().get(7).getPopularidad());
        assertEquals(2, service.populares().get(8).getPopularidad());
        assertEquals(1, service.populares().get(9).getPopularidad());
    }

    @Test
    public void impopulares(){
        assertEquals(10, service.populares().size());
        assertEquals(0, service.impopulares().get(0).getPopularidad());
        assertEquals(0, service.impopulares().get(1).getPopularidad());
        assertEquals(1, service.impopulares().get(2).getPopularidad());
        assertEquals(1, service.impopulares().get(3).getPopularidad());
        assertEquals(1, service.impopulares().get(4).getPopularidad());
        assertEquals(2, service.impopulares().get(5).getPopularidad());
        assertEquals(3, service.impopulares().get(6).getPopularidad());
        assertEquals(4, service.impopulares().get(7).getPopularidad());
        assertEquals(5, service.impopulares().get(8).getPopularidad());
        assertEquals(6, service.impopulares().get(9).getPopularidad());
    }
}