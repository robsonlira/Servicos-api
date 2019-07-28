package br.com.dominio.servicosapi.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.dominio.servicosapi.dto.UsuarioDTO;
import br.com.dominio.servicosapi.model.Usuario;
import br.com.dominio.servicosapi.repository.Usuarios;
import br.com.dominio.servicosapi.resources.exception.FieldMessage;

public class UsuarioUpdateValidator implements ConstraintValidator<UsuarioUpdate, UsuarioDTO> {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private Usuarios repository;
	
	@Override
	public void initialize(UsuarioUpdate ann) {
	}
	
	@Override
	public boolean isValid(UsuarioDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Long uriId = Long.parseLong((map.get("id")));
		
		List<FieldMessage> list = new ArrayList<>();
		
		Optional<Usuario> optional = repository.findByEmail(objDto.getEmail());
		if (optional.isPresent() && !optional.get().getId().equals(uriId)  ) {
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
