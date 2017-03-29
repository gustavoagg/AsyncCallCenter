# AsyncCallCenter
Proceso de atención de llamadas con manejo Asincrono, empleando Spring-Boot, ThreadPoolTaskExecutor (Java), H2,  AngularJS and Boostrap. 
 
 ## Requerimientos
  - Git (opcional)
  - Maven (opcional)
  - Java 1.8
 
 ## Para ejecutar con Git y Maven
 1. Ejecutar en consola: `git clone https://github.com/gustavoagg/AsyncCallCenter.git` 
 2. ingrese a la carpeta correspondiente: `cd AsyncCallCenter\AsyncCallCenter`
 3. luego ejecute: `mvn clean spring-boot:run`
 4. Una vez levantado el servidor, buscar el navegador la siguiente ruta:  http://localhost:8080/ 
 
  ## Para ejecutar sin Git y sin Maven
 1. Descarga el proyecto de esta ruta : `https://github.com/gustavoagg/AsyncCallCenter` (Boton clonar o Descargar > Descargar ZIP)
 2. Descomprimir el archivo descargado
 2. abrir una consola y buscar la ruta : `<directorio descargado>\AsyncCallCenter\AsyncCallCenter`
 3. Ejecuta: `mvnw clean spring-boot:run`
 4. Una vez levantado el servidor, buscar el navegador la siguiente ruta:  http://localhost:8080/ 
 
 ## Como funciona
 Una vez ejecutandose la aplicacion presenta un campo de formulario numerico para indicar el numero de llamadas que se desean generar, 
 y al presionar el boton "Generar Llamadas" se agregan estas a la cola de espera, e inmediatamente se puede observar en la parte inferior como 
 se van atendiendo las llamadas en vivo, se puede visualizar que operador las esta atendiendo, el tiempo que le resta a cada una y cuantas 
 llamadas a atendido cada uno. 
 
 Para mayor información sobre la logica, se puede revisar en el repositorio la carpeta UML, donde se encuentra el diagrama de clases y
 algunos diagramas de secuencia con notas explicativas.
 
 NOTA: Se puede consultar el estado de la tabla de los trabajadores accesando de la siguiente forma: 
 - http://localhost:8080/h2-console/ 
 - Colocando el siguiente JDBC URL: `jdbc:h2:mem:testdb` 
 - User Name: `sa` y presionando "Connect"
 
 ## Extras Solicitados
 - Cuando no hay empleado libre: para esto se implementó que automaticamente todas las llamadas al generarse pasan a una lista de espera y 
 una vez que haya una linea disponible esta es asignada por DispatchWorker al proximo empleado disponible
 - Cunado hay mas de 10 llamadas: de igual forma entran en la lista de espera, para ser luego asignadas. 
 
 ## Mejoras Adicionales
 - A parte de la prioridad que se da a los trabajadores de acuerdo a su cargo para atender las llamadas, 
 se considera tambien el numero de llamadas atendidas con el fin de balancear la carga de llamadas de cada trabajador
 - Para facilitar el registro de operadores, supervisores y directores, estos se almacenan en una base de datos, actualmente se usa H2 
 por la facilidad de pruebas, pero se podria configurar a cualquier otra BD

 
