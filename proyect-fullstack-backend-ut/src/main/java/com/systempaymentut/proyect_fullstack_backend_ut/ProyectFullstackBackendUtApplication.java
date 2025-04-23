package com.systempaymentut.proyect_fullstack_backend_ut;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.systempaymentut.proyect_fullstack_backend_ut.entities.Estudiante;
import com.systempaymentut.proyect_fullstack_backend_ut.entities.Pago;
import com.systempaymentut.proyect_fullstack_backend_ut.enums.PagoStatus;
import com.systempaymentut.proyect_fullstack_backend_ut.enums.TypePago;
import com.systempaymentut.proyect_fullstack_backend_ut.repository.EstudianteRepository;
import com.systempaymentut.proyect_fullstack_backend_ut.repository.PagoRepository;

@SpringBootApplication
public class ProyectFullstackBackendUtApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectFullstackBackendUtApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(EstudianteRepository estudianteRepository, PagoRepository pagoRepository) {
		return args -> {
			estudianteRepository.save(Estudiante.builder()
					.id(UUID.randomUUID().toString())
					.nombre("Melissa")
					.apellido("Gordillo")
					.codigo("1234")
					.programaId("IST123")
					.build());

			estudianteRepository.save(Estudiante.builder()
					.id(UUID.randomUUID().toString())
					.nombre("Carlos")
					.apellido("Ramirez")
					.codigo("2345")
					.programaId("IST123")
					.build());

			estudianteRepository.save(Estudiante.builder()
					.id(UUID.randomUUID().toString())
					.nombre("Ana")
					.apellido("Martinez")
					.codigo("3456")
					.programaId("IST123")
					.build());

			estudianteRepository.save(Estudiante.builder()
					.id(UUID.randomUUID().toString())
					.nombre("Juan")
					.apellido("Lopez")
					.codigo("4567")
					.programaId("IST123")
					.build());

			estudianteRepository.save(Estudiante.builder()
					.id(UUID.randomUUID().toString())
					.nombre("Maria")
					.apellido("Garcia")
					.codigo("5678")
					.programaId("IST123")
					.build());

			estudianteRepository.save(Estudiante.builder()
					.id(UUID.randomUUID().toString())
					.nombre("Pedro")
					.apellido("Sanchez")
					.codigo("6789")
					.programaId("IST123")
					.build());

			estudianteRepository.save(Estudiante.builder()
					.id(UUID.randomUUID().toString())
					.nombre("Laura")
					.apellido("Fernandez")
					.codigo("7890")
					.programaId("IST123")
					.build());

			// obtener tipos de pagos diferentes para cada estudiante
			TypePago tiposPago[] = TypePago.values();

			Random random = new Random();

			estudianteRepository.findAll().forEach(estudiante -> {
				for (int i = 0; i < 10; i++) {
					int index = random.nextInt(tiposPago.length);

					// contruir un objeto Pago con valores aleatorios

					Pago pago = Pago.builder()
							.cantidad(1000 + (int) (Math.random() * 20000))
							.type(tiposPago[index])
							.status(PagoStatus.CREADO)
							.fecha(LocalDate.now())
							.estudiante(estudiante)
							.build();

					pagoRepository.save(pago);
				}
			});
		};
	}
}
