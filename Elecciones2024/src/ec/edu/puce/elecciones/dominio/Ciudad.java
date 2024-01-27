package ec.edu.puce.elecciones.dominio;

import java.util.ArrayList;

public class Ciudad
{
    // instance variables - replace the example below with your own
    private String ciudad;
    private ArrayList<VotosCiudad> votosCandidatos;
    private Provincia provincia;
    private int votosCiudad;

    /**
     * Constructor for objects of class Ciudad
     */
    public Ciudad(String ciudad, Provincia provincia)
    {
        this.ciudad = ciudad;
        this.votosCandidatos = new ArrayList<VotosCiudad>();
        this.votosCiudad = 0;
        this.provincia = provincia;
        
    }
    
    public void agregarCandidato(VotosCiudad voto)
    {
        this.votosCandidatos.add(voto);
    }
    
    public int getVotos(String nombre){
        /*for (VotosCiudad voto : votosCandidatos){
            if(nombre == voto.getNombreCandidato()){
                return voto.votosCandidatoCiudad();
            }
        }*/
        return 0;
    }
    
    public int votosTotales(){
        votosCiudad = 0;
        /*for (VotosCiudad voto : votosCandidatos){
            this.votosCiudad+= voto.votosCandidatoCiudad();
        }*/
        return votosCiudad;
    }

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public int getVotosCiudad() {
		return votosCiudad;
	}

	public void setVotosCiudad(int votosCiudad) {
		this.votosCiudad = votosCiudad;
	}

	public ArrayList<VotosCiudad> getVotosCandidatos() {
		return votosCandidatos;
	}

	public void setVotosCandidatos(ArrayList<VotosCiudad> votosCandidatos) {
		this.votosCandidatos = votosCandidatos;
	}
    
	
    
}
