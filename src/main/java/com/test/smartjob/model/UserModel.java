package com.test.smartjob.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserModel {

	private UUID id;

	@NotNull
	@NotEmpty
	@Size(min = 5, max = 30)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String name;

	@NotNull
	@NotEmpty
	@Size(min = 5, max = 30)
	@Email(message = "Correo no válido")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@dominio\\.cl$", message = "El correo debe tener el dominio: dominio.cl")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String email;

	@NotNull
	@NotEmpty
	@Size(min = 5, max = 30)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String password;

	private LocalDateTime modified;
	
	private LocalDateTime created;
	
	private LocalDateTime lastlogin;
	
	private String Token;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean isactive;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@NotNull
	@NotEmpty
	private List<PhoneModel> phones;

}
