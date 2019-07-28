package br.com.dominio.servicosapi.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.dominio.servicosapi.dto.UsuarioNewDTO;
import br.com.dominio.servicosapi.model.Usuario;
import br.com.dominio.servicosapi.repository.Usuarios;
import br.com.dominio.servicosapi.resources.exception.FieldMessage;

public class UsuarioInsertValidator implements ConstraintValidator<UsuarioInsert, UsuarioNewDTO> {

	@Autowired
	private Usuarios repository;
	
	@Override
	public void initialize(UsuarioInsert ann) {
	}
	
	@Override
	public boolean isValid(UsuarioNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		Optional<Usuario> optional = repository.findByEmail(objDto.getEmail());
		if (optional.isPresent()) {
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		
		return list.isEmpty();
	}
	
}
