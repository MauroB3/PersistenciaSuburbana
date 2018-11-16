package ar.edu.unq.epers.bichomon.backend.dao.impl;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.CostoCamino;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.UbicacionMuyLejanaException;
import org.neo4j.driver.v1.*;

import java.util.Comparator;
import java.util.List;

public class UbicacionNeo4JDAO {

    private Driver driver;

    public UbicacionNeo4JDAO() {
        this.driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "1k3R1" ) );
    }

    public void crearUbicacion(Ubicacion ubicacion) {
        Session session = this.driver.session();

        try {
            String query = "MERGE (n:Ubicacion {nombre: {nombre}}) ";
            session.run(query, Values.parameters("nombre", ubicacion.getNombre()));
        } finally {
            session.close();
        }
    }

    public void conectar(String origen, String destino, String tipoCamino) {
        Session session = this.driver.session();

        try {
            String query = "MATCH (origen:Ubicacion {nombre: {nombreOrigen}}) " +
                    "MATCH (destino:Ubicacion {nombre: {nombreDestino}}) " +
                    "MERGE (origen)-[r:Camino {medio: {medio}, costo: {costo}}]->(destino) ";
            session.run(query, Values.parameters("nombreOrigen", origen,
                    "nombreDestino", destino,
                    "medio", tipoCamino,
                    "costo", CostoCamino.valueOf(tipoCamino).getValue()));
        } finally {
            session.close();
        }
    }

    public List<String> conectados(String ubicacion, String tipoCamino){
        Session session = this.driver.session();

        try {
            String query = "match (n:Ubicacion {nombre: {nombreOrigen}})" +
                    "match (n)-[r:Camino]->(x)" +
                    "where r.medio = {medio}" +
                    "return x";
            StatementResult result = session.run(query, Values.parameters("nombreOrigen", ubicacion,
                    "medio", tipoCamino));

            return result.list(record -> {
                Value hijo = record.get(0);
                String nombre = hijo.get("nombre").asString();
                return nombre;
            });
        } finally {
            session.close();
        }
    }

    public int getCostoEntreUbicacionesLindantes(String origen, String destino) {
        Session session = this.driver.session();

        try {
            String query = 	"MATCH (o:Ubicacion {nombre: {nombreOrigen}})" +
                    "MATCH (d:Ubicacion {nombre: {nombreDestino}})" +
                    "MATCH (o)-[r:Camino]->(d)" +
                    "RETURN r.costo as costo";

            StatementResult result = session.run(query, Values.parameters("nombreOrigen",  origen, "nombreDestino", destino));

            if(!result.hasNext()) {
                throw new UbicacionMuyLejanaException(origen, destino);
            }
            else {
                int costo = result.next().get("costo").asInt();
                for(Record record : result.list()) {
                    if(record.get("costo").asInt() < costo) { costo = record.get("costo").asInt(); }
                }
                return costo;
            }
        }
        finally {
            session.close();
        }
    }

    public int getCostoCaminoMasCorto(String origen, String destino){
        Session session = this.driver.session();

        try{
            String query = "MATCH (o:Ubicacion {nombre: {nombreOrigen}}) " +
                    "MATCH (d:Ubicacion {nombre: {nombreDestino}}) " +
                    "MATCH p = shortestPath((o)-[Camino*]->(d)) " +
                    "WITH REDUCE(costo = 0, camino in rels(p) | costo + toInt(camino.costo)) " +
                    "AS sumaCosto " +
                    "RETURN sumaCosto";

            StatementResult result = session.run(query, Values.parameters("nombreOrigen", origen, "nombreDestino", destino));

            return result.peek().get("sumaCosto").asInt();

        } finally {
            session.close();
        }
    }

    public void destroy() {
        Session session = this.driver.session();

        try {
            String query = "match (n) detach delete (n)";
            session.run(query);
        } finally {
            session.close();
        }
    }


}
