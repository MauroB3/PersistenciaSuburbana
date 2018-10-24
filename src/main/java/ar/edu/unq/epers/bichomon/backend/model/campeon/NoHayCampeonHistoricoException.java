package ar.edu.unq.epers.bichomon.backend.model.campeon;

public class NoHayCampeonHistoricoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NoHayCampeonHistoricoException( ) {
        super("No existe ningun campeon historico.");
    }

}
