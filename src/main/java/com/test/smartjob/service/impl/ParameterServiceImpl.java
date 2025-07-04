package com.test.smartjob.service.impl;

import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.smartjob.entity.ParameterEntity;
import com.test.smartjob.exception.BadDataException;
import com.test.smartjob.exception.BusinessException;
import com.test.smartjob.model.ParameterModel;
import com.test.smartjob.repository.ParameterRepository;
import com.test.smartjob.service.ParameterService;

@Service
public class ParameterServiceImpl implements ParameterService {

	private static final Logger log = LoggerFactory.getLogger(ParameterServiceImpl.class);

	@Autowired
	ParameterRepository parameterRepository;

	/**
	 * funcion encargada de realizar la operacion de actualizacion de un parametro
	 * 
	 * @param ParameterModel: POJO que contiene la informacion de un parametro
	 * @return ParameterModel: POJO que contiene la informacion de un parametro actualizado
	 */
	@Override
	public ParameterModel updateParameter(ParameterModel parameterModel) {

		if (validarExpresion(parameterModel.getValue())) {

			Optional<ParameterEntity> optionalParameter = parameterRepository.findByName(parameterModel.getName());

			if (optionalParameter.isPresent()) {

				ParameterEntity parameterEntity = optionalParameter.get();
				parameterEntity.setValueparameter(parameterModel.getValue());
				parameterRepository.save(parameterEntity);
				return parameterModel;

			}

			throw new BadDataException("No existe parametro para actualizar");
		}

		throw new BusinessException("expresion invalida");

	}

	/**
	 * funcion encargada de realizar la operacion de consulta de un parametro
	 * 
	 * @param name: String con el nombre del aparametro a consultar
	 * @return ParameterModel: POJO que contiene la informacion de un parametro actualizado
	 */
	public ParameterModel consultParameter(String name) {

		Optional<ParameterEntity> optionalParameter = parameterRepository.findByName(name);

		if (optionalParameter.isPresent()) {

			ParameterModel parameterModel = new ParameterModel();
			parameterModel.setName(optionalParameter.get().getName());
			parameterModel.setValue(optionalParameter.get().getValueparameter());
			return parameterModel;

		}

		throw new BadDataException("No existe parametro");
	}

	/**
	 * funcion encargada de realizar la operacion de validacion de nuevas expresiones a actualizar
	 * 
	 * @param expresion: String con la expresion a validar
	 * @return boolean: bandera que indica si la expresion es valida o no
	 */
	private boolean validarExpresion(String expresion) {
		try {
			Pattern.compile(expresion);
			return true;
		} catch (PatternSyntaxException e) {
			log.error("expresion invalida: " + e.getDescription());
			return false;
		}
	}

}
