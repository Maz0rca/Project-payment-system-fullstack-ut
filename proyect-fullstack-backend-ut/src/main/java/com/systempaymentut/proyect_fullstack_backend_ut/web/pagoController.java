package com.systempaymentut.proyect_fullstack_backend_ut.web;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; //permite trabajar inyeccion de dependencias
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin; //permite que la API sea accesible desde cualquier dominio
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; //para parametrizar uno //buscar datos especificos
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam; // para parametrizar varios // cuando se trata de varios datos para actores diferentes
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.systempaymentut.proyect_fullstack_backend_ut.entities.Estudiante;
import com.systempaymentut.proyect_fullstack_backend_ut.entities.Pago;
import com.systempaymentut.proyect_fullstack_backend_ut.enums.PagoStatus;
import com.systempaymentut.proyect_fullstack_backend_ut.enums.TypePago;
import com.systempaymentut.proyect_fullstack_backend_ut.repository.EstudianteRepository;
import com.systempaymentut.proyect_fullstack_backend_ut.repository.PagoRepository;
import com.systempaymentut.proyect_fullstack_backend_ut.services.PagoService;

//defina la clase como un controlador REST
@RestController
@CrossOrigin("*") // permite que esta API sea accesible desde cualquier dominio //asterisco en
                  // ("*") significa TODOS.
public class pagoController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PagoService pagoService;

    // --METODOS PARA EL MANEJO DE ESTUDIANTES--

    // Metodo que me devuelva la lista con todos los estudiantes
    @GetMapping("/estudiantes")
    public List<Estudiante> listarEstudiantes() {
        return estudianteRepository.findAll();// Retorna todos los estudiantes des de la base de datos
    }

    // Metodo que devuelva un estudiante en especifico por su codigo
    @GetMapping("/estudiantes/{codigo}")
    public Estudiante listarEstudiantePorCodigo(@PathVariable String codigo) {
        return estudianteRepository.findByCodigo(codigo);// Busca estudiante por su codigo
    }

    // Metodo para listar estudiantes segun el programa academico
    @GetMapping("/estudiantesPorPrograma")
    public List<Estudiante> listarEstudiantesPorPrograma(@RequestParam String programaId) {
        return estudianteRepository.findByProgramaId(programaId);

    }

    // METODOS PARA EL MANEJO DE PAGOS

    // METODO QUE DEVUELVA LA LISTA CON TODOS LOS PAGOS REGISTRADOS
    @GetMapping("/pagos")
    public List<Pago> listarPagos() {
        return pagoRepository.findAll();
    }

    // Metodo que devuelva un pago en especifico segun su id
    @GetMapping("/pagos/{id}")
    public Pago listarPagoPorId(@PathVariable Long id) {
        return pagoRepository.findById(id).get();// Busca un pago por su ID
    }

    // MEtodo que lista los pagos hechos por un estudiante en especifico segun su
    // codigo
    @GetMapping("/estudiantes/{codigo}/pagos")
    public List<Pago> listarPagosPorCodigoEstudiante(@PathVariable String codigo) {
        return pagoRepository.findByEstudianteCodigo(codigo);
    }

    // Metodo que liste los pagos segun su estado(CREADO; VALIDADO; RECHAZADO)
    @GetMapping("pagosPorStatus")
    public List<Pago> listarPagosPorStatus(@RequestParam PagoStatus status) {
        return pagoRepository.findByStatus(status);
    }

    // Metodo que nos permita listar los pagos segun su tipo (EFECTIVO, CHEQUE,
    // TRANSFERENCIA, DESPOSITO)
    @GetMapping("pagos/porTipo")
    public List<Pago> listarPagosPorStatus(@RequestParam TypePago type) {
        return pagoRepository.findByType(type);

    }

    // MEtodo para actualizar un estado de pago
    @PutMapping("pagos/{pagoId}/actualizarPago")
    public Pago actualizarStatusDePago(@RequestParam PagoStatus status, @PathVariable Long pagoId) {
        return pagoService.actualizarPagoPorStatus(status, pagoId);// llama al servicio actualizar el estado de pago
    }

    // Metodo para registrar un pago con archivo adjunto pdf como comprobante
    @PostMapping(path = "/pagos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Pago guardarPago(
            @RequestParam("/file") MultipartFile file, // archivo adjunto
            double cantidad,
            TypePago type,
            LocalDate date,
            String codigoEstudiante) throws IOException {
        return pagoService.savePago(file, cantidad, type, date, codigoEstudiante);// guarda el pago en la base de datos
    }

    // Metodo que devuelve un archivo asociado a un pago en formato pdf
    @GetMapping(value = "/pagoFile/{pagoId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] listarArchivoPorId(@PathVariable Long pagoId) throws IOException {
        return pagoService.getArchivoPorId(pagoId);
    }

}