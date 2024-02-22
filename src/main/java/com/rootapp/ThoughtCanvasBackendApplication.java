package com.rootapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rootapp.config.AppConstants;
import com.rootapp.entities.Role;
import com.rootapp.repositories.RoleRepository;

@SpringBootApplication
public class ThoughtCanvasBackendApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(ThoughtCanvasBackendApplication.class, args);
	}

	// execute when application will start
	@Override
	public void run(String... args) throws Exception {

		Role role1 = Role.builder().id(AppConstants.ADMIN_USER).name("ADMIN_USER").build();
		Role role2 = Role.builder().id(AppConstants.NORMAL_USER).name("NORMAL_USER").build();

		List<Role> savedRoles = this.roleRepository.saveAll(List.of(role1, role2));

		savedRoles.stream().forEach(role -> System.out.println(role.getName()));
	}

}
