package sprint1_IntegrationTests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import conecta2.C2Aplicacion;
import conecta2.modelo.Empresa;
import conecta2.servicioAplicacion.SAEmpresa;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = C2Aplicacion.class)
@DataJpaTest
@ComponentScan(basePackages ="conecta2")
public class HU06ModificarPerfilEmpresaTest {

	@Autowired
	public SAEmpresa saEmpresa;
	
	@Test
	public void testEmpresaModifyName() {
		
		Empresa empresa = new Empresa("empresaPruebaNombre", "A28599033", "123456789", "empresaPruebaEmail@empresaPruebaEmail.com", "Abc1111", "", 0, true, null, null);
		
		empresa = saEmpresa.save(empresa);
		
		Empresa empresaGuardada = new Empresa(empresa);
		
		empresa.setNombreEmpresa("empresaPruebaModificado");
		
		empresa = saEmpresa.save(empresa);
		
		assertNotEquals(empresa, empresaGuardada);
	}

}
