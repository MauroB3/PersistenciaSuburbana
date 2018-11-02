package ar.edu.unq.epers.bichomon.backend.dao.impl;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.CostoCamino;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.neo4j.driver.v1.*;

import java.util.List;

public class UbicacionNeo4JDAO {

    private Driver driver;

    public UbicacionNeo4JDAO() {
        this.driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "tiranosaurio0" ) );
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
            String query = "MATCH (origen)-[r]->(destino) " +
                    "WHERE r.medio = {medio} " +
                    "RETURN destino";
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



}
