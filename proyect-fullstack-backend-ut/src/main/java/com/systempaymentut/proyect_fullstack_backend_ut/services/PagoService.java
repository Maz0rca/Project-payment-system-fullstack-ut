package com.systempaymentut.proyect_fullstack_backend_ut.services;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.systempaymentut.proyect_fullstack_backend_ut.entities.Estudiante;
import com.systempaymentut.proyect_fullstack_backend_ut.entities.Pago;
import com.systempaymentut.proyect_fullstack_backend_ut.enums.PagoStatus;
import com.systempaymentut.proyect_fullstack_backend_ut.enums.TypePago;
import com.systempaymentut.proyect_fullstack_backend_ut.repository.EstudianteRepository;
import com.systempaymentut.proyect_fullstack_backend_ut.repository.PagoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional // para asegurar que los metodos de la clase se ejecuten dentro de una
               // transaccion
public class PagoService {

    // inyeccion de dependencias de PagoRepository para interactuar con la bd (base
    // de datos) de pagos
    @Autowired
    private PagoRepository pagoRepository;

    // inyeccion de dependencias de EstudianteRepository para obtener informacion de
    // los estudiantes desde la bd
    @Autowired
    private EstudianteRepository estudianteRepository;

    /**
     * metodo para guardar el pago en la bd y almacenar un archivo pdf en el
     * servidor
     * 
     * @param file             archivo pdf que se subira al servidor
     * @param cantidad         para conocer el monto del pago realizado
     * @param type             tipo de pago EFECTIVO, CHEQUE, TRANSFERENCIA,
     *                         DEPOSITO
     * @param date             fecha en que se realiza el pago
     * @param codigoEstudiante codigo del estudiante que realiza el pago
     * @return objeto Pago guardado en la bd
     * @throws IOExecption execpcion lanzada si ocurre un error al manejar el file
     *                     pdf
     */
    public Pago savePago(MultipartFile file, double cantidad, TypePago type, LocalDate date, String codigoEstudiante)
            throws IOException {

        /**
         * conturir la ruta donde se guardara el archivo dentro del sistema
         * System.getProperty("user.home"): obtinee la ruta del directorio personal del
         * usuario del actual SO
         * Paths.get : contruir una ruta dentro del directorio personal en la carpeta
         * "enset-data/pagos"
         */
        Path folderPath = Paths.get(System.getProperty("user.home"), "enset-data", "pagos");

        // verificar si la carpeta ya existe si no, la debe crear
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        // generamos un nombre unico para el archivo usando UUID(indetificador unico
        // universal)
        String fileName = UUID.randomUUID().toString();

        // Construimos la ruta completa del archivo agregando la extension .pdf
        Path filePath = Paths.get(System.getProperty("user.home"), "enset-data", "pagos", fileName + ".pdf");

        // guardamos el archivo recibido en la ubicacion especificada dentro del sistema
        // de archivos
        Files.copy(file.getInputStream(), filePath);

        // buscamos el estudiante que realiza el pago con su codigo
        Estudiante estudiante = estudianteRepository.findByCodigo(codigoEstudiante);

        // creamos un nuevo objeto Pago utilizando el patron de diseño "builder"
        Pago pago = Pago.builder()
                .type(type)
                .status(PagoStatus.CREADO)// estado inicial del pago
                .fecha(date)
                .estudiante(estudiante)
                .cantidad(cantidad)
                .file(filePath.toUri().toString())// ruta del archivo pdf almacenado
                .build();// construccion final del objeto Pago

        return pagoRepository.save(pago);

    }

    public byte[] getArchivoPorId(Long pagoId) throws IOException {

        // busca un objeto Pago en la bd por su ID
        Pago pago = pagoRepository.findById(pagoId).get();

        /*
         * pago.getFile: obtiene la URI del archivo guardado como una cadena de texto
         * URI.created: convierte la cadena de texto en un objeto URI
         * pathOf: convierte la URI en un path para poder acceder al archivo
         * Files.readAllBytes: lee el contenido del archivo y lo va devolver en un array
         * vector de bytes
         * esto permite obtener el contenido del archivo para su posterior uso por
         * ejemplo
         * descargarlo
         */

        return Files.readAllBytes(Path.of(URI.create(pago.getFile())));
    }

    public Pago actualizarPagoPorStatus(PagoStatus status, Long id) {

        // busca un objeto Pago en la bd por su ID
        Pago pago = pagoRepository.findById(id).get();

        // Actualiza el estado del pago (validado o rechazado)
        pago.setStatus(status);

        // guarda el objeto pago actualizado en la bd y lo devuelve
        return pagoRepository.save(pago);

    }

}
