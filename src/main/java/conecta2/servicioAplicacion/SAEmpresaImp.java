package conecta2.servicioAplicacion;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import conecta2.modelo.Empresa;
import conecta2.repositorio.RepositorioEmpresa;
import conecta2.transfer.TransferEmpresa;

/**
 * Clase que implementa las funciones de la interfaz SAEmpresa
 * Clase que se desarrolla la funcionalidad de la entidad Empresa
 */
@Service //("SAEmpresa")
public class SAEmpresaImp implements SAEmpresa {
	
	/**
	 * Repositorio que proporciona el acceso a la base de datos
	 */
	@Autowired
	private RepositorioEmpresa repositorioEmpresa;
	
	/**
	 * Atributo que se utiliza para encriptar las contraseñas una vez el usuario se registra
	 */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Servicio de Aplicación para la autenticación por email
     */
    @Autowired
  	private SAEmail saEmail;
    

    /**
     * Método que recibe un TranferEmpresa y lo inserta en la base de datos
     */
    @Transactional
    @Override
	public void crearEmpresa(TransferEmpresa transferEmpresa) {
    	 
    	 Empresa empresa = Empresa.TranferToEntity(transferEmpresa);
         empresa.setPassword(bCryptPasswordEncoder.encode(transferEmpresa.getPassword()));
         empresa.setActivo(false);

         saEmail.enviarCorreo("Acceda al siguiente enlace para terminar el registro en Conecta2, ya casi está solo un paso más, ", "Alta cuenta en Conecta2", empresa.getEmail());;
        
         repositorioEmpresa.save(empresa); //Hace el save al repositorio (función interna de JPARepository)
         //Después de esto el usuario ya estaría guardado en la Base de Datos
	}

    /**
     * Método que recibe un email y busca a una empresa con el mismo en la base de datos
     */
	@Override
	public Empresa buscarPorEmail(String email) {
		return repositorioEmpresa.findByEmail(email);
	}

    /**
     * Método que recibe un cif y busca a una empresa con el mismo en la base de datos
     */
	@Override
	public Empresa buscarPorCif(String cif) {
		return repositorioEmpresa.findByCif(cif);
	}
	
	/**
     * Método que recibe un id y busca a una empresa con el mismo en la base de datos
     */
	@Override
	public Empresa buscarPorId(int id) {
		return repositorioEmpresa.findById(id);
	}
	
	/**
	 * Método que recibe un TransferEmpresa y si no existe lo guarda en la base de datos
	 * en caso contrario lo modifica
	 */
	@Transactional
	@Override
	public Empresa save(Empresa empresa) {

		if(empresa != null)
			empresa = repositorioEmpresa.save(empresa); //Hace el save al repositorio (función interna de JPARepository)
         
         return empresa;
	}

	@Override
	public Empresa cifraPass(Empresa empresa) {
		// TODO Auto-generated method stub
        empresa.setPassword(bCryptPasswordEncoder.encode(empresa.getPassword()));

		return save(empresa);
	}
}
