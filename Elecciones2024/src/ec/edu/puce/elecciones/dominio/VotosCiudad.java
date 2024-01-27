package ec.edu.puce.elecciones.dominio;

public class VotosCiudad {
	private Prefecto candidato;
    private int votos;

    /**
     * Constructor for objects of class Voto
     */
    public VotosCiudad(Prefecto candidato)
    {
        this.candidato = candidato;
        this.votos = 0;
    }
    
    public void aumentarVotos()
    {
        this.votos++;
    }
    
    public int votosCandidatoCiudad()
    {
        return this.votos;
    }
    
    public String getNombreCandidato(){
        return this.candidato.getNombre();
    }

}
