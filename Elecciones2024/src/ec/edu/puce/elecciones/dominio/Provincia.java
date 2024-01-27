package ec.edu.puce.elecciones.dominio;

import java.util.ArrayList;
import java.util.List;

public class Provincia
{
    // instance variables - replace the example below with your own
    private String nombre;
    private List<Prefecto> candidatos;
    private ArrayList<Ciudad> ciudades;
    private int votosTotales;

    
    public Provincia(String nombre, List<Prefecto> prefectos)
    {
        this.nombre = nombre;
        this.candidatos = prefectos;
        this.votosTotales = 0;
        this.ciudades = new ArrayList<Ciudad>();
    }

    public void agregarCandidato(Prefecto candidato)
    {
        this.candidatos.add(candidato);
    }
    
    public int votosProvincia(){
        votosTotales = 0;
        for (Prefecto candidato : candidatos){
            this.votosTotales+= candidato.getVotos();
        }
        return votosTotales;
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Prefecto> getCandidatos() {
		return candidatos;
	}

	public void setCandidatos(List<Prefecto> candidatos) {
		this.candidatos = candidatos;
	}

	public int getVotosTotales() {
		return votosTotales;
	}

	public void setVotosTotales(int votosTotales) {
		this.votosTotales = votosTotales;
	}

	public ArrayList<Ciudad> getCiudades() {
		return ciudades;
	}

	public void setCiudades(ArrayList<Ciudad> ciudades) {
		this.ciudades = ciudades;
	}
	
    
}
