package conecta2.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import conecta2.transfer.TransferOferta;

@Entity
@Table(name = "ofertas")
@NamedQuery(
    name = "Oferta.findOfertasParticularInscrito",
    //query = "SELECT o FROM Oferta o INNER JOIN o.particulares parts WHERE parts."
    query ="SELECT o FROM Oferta o WHERE ?1 MEMBER OF o.particularesInscritos"
)
/**
 * Entidad / Objeto de Negocio de Oferta
 * Se utiliza para persistir la información de la oferta
 */
public class Oferta {

	/**
	 * Id que genera la base de datos automáticamente, no se debe asignar manualmente
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotEmpty
	@Length(max = 50)
	private String nombre;
	
	@NotNull
	private JornadaLaboral jornada;
	
	@NotNull
	private Contrato contrato;
	
	@NotNull
	private Integer vacantes;
	
	private Double salario;

	private String ciudad;
	
	@Length(max = 1000)
	private String descripcion;
	
	private boolean activo;
	
	private boolean finalizada;
	
	private String tecnologias;
	
	private int aniosExperiencia;
	
	@ManyToMany(mappedBy="ofertasInscritos", fetch=FetchType.EAGER)
	private List<Particular> particularesInscritos;

	@ManyToMany(mappedBy="ofertasSeleccionados")
	private List<Particular> particularesSeleccionados;
	
	@ManyToOne (fetch=FetchType.EAGER)
	private Empresa empresa;

	/**
	 * Constructora sin argumentos necesaria para JPA
	 */
	public Oferta() {}
	
	public Oferta(String nombre, JornadaLaboral jornada, Contrato contrato, Integer vacantes, Double salario, String ciudad, String descripcion, boolean activo, boolean finalizada, Empresa empresa, List<Particular> particulares, String tecnologias, int aniosExperiencia) {
		this.nombre = nombre;
		this.jornada = jornada;
		this.contrato = contrato;
		this.vacantes = vacantes;
		this.salario = salario;
		this.ciudad = ciudad;
		this.descripcion = descripcion;
		this.activo = activo;
		this.finalizada = finalizada;
		this.empresa = empresa;
		this.tecnologias = tecnologias;
		this.aniosExperiencia = aniosExperiencia;
		
		this.particularesSeleccionados = new ArrayList<Particular>();
		
		if(this.particularesInscritos == null || particulares == null) 
			this.particularesInscritos = new ArrayList<Particular>();
		else
			this.particularesInscritos = particulares;
	}
	
	public Oferta(Oferta oferta) {
		this.nombre = oferta.getNombre();
		this.jornada = oferta.getJornadaLaboral();
		this.contrato = oferta.getContrato();
		this.vacantes = oferta.getVacantes();
		this.salario = oferta.getSalario();
		this.ciudad = oferta.getCiudad();
		this.descripcion = oferta.getDescripcion();
		this.activo = oferta.getActivo();
		this.id = oferta.getId();
		this.finalizada = oferta.getFinalizada();
		this.empresa = oferta.getEmpresa();
		this.particularesInscritos= oferta.getParticularesInscritos();
		this.tecnologias = oferta.getTecnologias();
		this.aniosExperiencia = oferta.getAniosExperiencia();
	}

	public static Oferta TranferToEntity(TransferOferta transferOferta, int idOferta) {
		Oferta oferta = new Oferta(
				transferOferta.getNombre(),
				transferOferta.getJornada(),
				transferOferta.getContrato(),
				transferOferta.getVacantes(),
				transferOferta.getSalario(),
				transferOferta.getCiudad(),
				transferOferta.getDescripcion(),
				transferOferta.getActivo(),
				transferOferta.getFinalizada(),
				transferOferta.getEmpresa(),
				transferOferta.getParticulares(),
				transferOferta.getTecnologias(),
				transferOferta.getAniosExperiencia()
				);
		oferta.setId(idOferta);
		return oferta;
	}
	
	public static Oferta TranferToEntity(TransferOferta transferOferta) {
		return new Oferta(
				transferOferta.getNombre(),
				transferOferta.getJornada(),
				transferOferta.getContrato(),
				transferOferta.getVacantes(),
				transferOferta.getSalario(),
				transferOferta.getCiudad(),
				transferOferta.getDescripcion(),
				transferOferta.getActivo(),
				transferOferta.getFinalizada(),
				transferOferta.getEmpresa(),
				transferOferta.getParticulares(),
				transferOferta.getTecnologias(),
				transferOferta.getAniosExperiencia()
				);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public JornadaLaboral getJornadaLaboral() {
		return jornada;
	}

	public void setJornadaLaboral(JornadaLaboral jornada) {
		this.jornada = jornada;
	}
	
	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	
	public Integer getVacantes() {
		return vacantes;
	}

	public void setVacantes(Integer vacantes) {
		this.vacantes = vacantes;
	}
	
	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}
	
	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	public boolean getFinalizada() {
		return finalizada;
	}

	public void setFinalizada(boolean finalizada) {
		this.finalizada = finalizada;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public String getTecnologias() {
		return tecnologias;
	}

	public void setTecnologias(String tecnologias) {
		this.tecnologias= tecnologias;
	}
	
	public int getAniosExperiencia() {
		return aniosExperiencia;
	}

	public void setAniosExperiencia(int aniosExperiencia) {
		this.aniosExperiencia = aniosExperiencia;
	}

	public List<Particular> getParticularesInscritos() {
		return particularesInscritos;
	}

	public void setParticularesInscritos(List<Particular> particularesInscritos) {
		this.particularesInscritos = particularesInscritos;
	}

	public List<Particular> getParticularesSeleccionados() {
		return particularesSeleccionados;
	}

	public void setParticularesSeleccionados(List<Particular> particularesSeleccionados) {
		this.particularesSeleccionados = particularesSeleccionados;
	}

	public boolean containsParticular(Particular p) {
		return this.getParticularesInscritos().contains(p);
	}
	
	public boolean containsJornada(String text) {
		
	    for (JornadaLaboral j : JornadaLaboral.values()) {
	        if (j.name().equals(text)) {
	            return true;
	        }
	    }

	    return false;
	}
	
	public boolean containsContrato(String text) {
		
	    for (Contrato j : Contrato.values()) {
	        if (j.name().equals(text)) {
	            return true;
	        }
	    }

	    return false;
	}
	
	public void inscribirParticular(Particular particular) {
		
		if(this.particularesInscritos == null)
			this.particularesInscritos = new ArrayList<Particular>();
		this.particularesInscritos.add(particular);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (activo ? 1231 : 1237);
		result = prime * result + ((ciudad == null) ? 0 : ciudad.hashCode());
		result = prime * result + ((contrato == null) ? 0 : contrato.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + (finalizada ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + ((jornada == null) ? 0 : jornada.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((particularesInscritos == null) ? 0 : particularesInscritos.hashCode());
		result = prime * result + ((salario == null) ? 0 : salario.hashCode());
		result = prime * result + ((vacantes == null) ? 0 : vacantes.hashCode());
		result = prime * result + ((tecnologias == null) ? 0 : tecnologias.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Oferta other = (Oferta) obj;
		if (activo != other.activo)
			return false;
		if (ciudad == null) {
			if (other.ciudad != null)
				return false;
		} else if (!ciudad.equals(other.ciudad))
			return false;
		if (contrato != other.contrato)
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (finalizada != other.finalizada)
			return false;
		if (id != other.id)
			return false;
		if (jornada != other.jornada)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (particularesInscritos == null) {
			if (other.particularesInscritos != null)
				return false;
		} else if (!particularesInscritos.equals(other.particularesInscritos))
			return false;
		if (salario == null) {
			if (other.salario != null)
				return false;
		} else if (!salario.equals(other.salario))
			return false;
		if (vacantes == null) {
			if (other.vacantes != null)
				return false;
		} else if (!vacantes.equals(other.vacantes))
			return false;
		if(particularesSeleccionados == null) {
			if(other.particularesSeleccionados != null)
				return false;
		}
		else if(!particularesSeleccionados.equals(other.particularesSeleccionados))
			return false;
		if(tecnologias == null) {
			if(other.tecnologias != null)
				return false;
		}
		else if(!tecnologias.equals(other.tecnologias))
			return false;
		return true;
	}
	
	
}
