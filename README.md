# 0. Introducción

En este repositorio muestro la antesala de un proceso de registro e iniciar sesión de una aplicación web que estoy haciendo.

En primer lugar, podréis ver el [backend](/APP%20WEB/__springboot__produccio__/) que he hecho con `springboot` (siendo ésta la parte más importante de este proyecto y aquello en lo que el lector pasará más tiempo inspeccionando). En segundo lugar,  podréis vislumbrar [el front end](/APP%20WEB/__frontend__produccio__/landingPage/) de la aplicación: que he desarrollado con HTML5, CSS y javascript puro (sin frameworks) y, finalmente, una [base de datos](/___BBDD___/estructuraTaules/pirApp.sql) hecha con mySQL que contiene dos tablas de usuarios -a la que por ahora, y en este ejemplo, solo tocaremos una (la tabla "usuari")-.

La gracia de la aplicación web que contiene este repositorio es que mediante el back-end se ha intentado (y, en mi opinión, conseguido) hacer una réplica de la lógica de negocio que sigue Netflix cuando un usuario, con el objetivo de introducir los datos en el sistema por primera vez,  envía su correo a través del formulario de registro que aparece nada más entrar en la "landing-page". Cuando este manda su correo en ese formulario, sea en Netflix o en nuestra web, va a entrar en la antesala de un sistema preparado para brindarle, con la máxima sencillez posible, la inscripción a un servicio por suscripción: en este caso de uso, el sistema evalúa cuál es el estado actual del usuario en la base de datos: en función de ello el sistema le mostrará a éste un camino u otro. Si el usuario está registrado o no lo está, o bien si al estar registrado está pagando por servicios o no, son distintos escenarios que generarán también por parte del back-end distintas redirecciones del usuario a distintas páginas de acuerdo a lo que se especifica en el [diagrama de flujo](#Diagrama-de-flujo) que mostraremos en el siguiente apartado. 

Además, la comunicación entre front-end y back-end la he realizado en todo caso mediante API (usaremos la interfaz nativa del navegador fetch-api en el front-end para que este pueda "consumir" los endpoints que nos proporciona la rest-API del back-end). En todo caso se ha seguido en la medida de lo posible los principios REST (véase nota al pie[^1]). Así pues, debe quedar claro que el back-end y el front-end son dos proyectos separados que estan en comunicación entre ellos y que el back-end, además, se comunicará con la base de datos, que será un tercer proyecto. Así pues, la ventaja de hacerlo así es que, por ejemplo, se podría reescribir por completo el proyecto front-end en otra tecnología basada en componentes (por ejemplo Angular, React o Vue) sin tener que hacer modificación alguna en el back-end, o incluso podría usarse este back-end para permitir que sus endpoints sean consumidos por otras aplicaciones además del front-end web: por ejemplo, una aplicación en android.

# 1. Objetivo

Por un lado, mostrar una pequeña porción del software de un proyecto mio (que es software propietario, así que no está mostrado aquí en su totalidad) para demostrar que he adquirido un conocimiento generalista del framework Springboot; y, por el otro, mostrar información relevante de los primeros pasos que hay quedar para que desarrollador que quiera empezar a desarrollar back-end con este framework de Java tenga una guía para poder a empezar a dar sus primeros pasos al preparar la antesala de un sistema de registro. 

Para enseñar como funciona Springboot voy a describir de forma pormenorizada la parte del  árbol de decisión o diagrama de flujo que está marcada en color rosa en el apartado [diagrama de flujo](#Diagrama-de-flujo). De este modo, explicaré a grandes rasgos como se estructura un proyecto en springboot en sus distintas partes (modelo, repositorio, servicio y controlador). Además, también quisiera tratar de demostrar como podemos utilizar este framework para traducir el código en resultados tangibles que nos ayuden con una lógica de negocio enfocada a captar clientes ya desde sus primeras interacciones.

Finalmente, mencionar que no se comentarán aspectos de los estilos del frontend aunque si el lector tiene preguntas puede escribirme por linkedin y preguntar lo que desee. Asimismo,aspectos como la generación de tokens JWT o gestión de cookies no se tratan en este repositorio.


# 2. Recomendaciones para ejecutar el proyecto

Se recomienda al lector que, si quiere probar y ver la aplicación web con todas sus características y en su totalidad, haga un clone del repositorio y abra cada uno de los tres proyectos con su editor correspondiente. De este modo cada editor expondrá una URL y un puerto en el mismo equipo[^2]:

- **[el front-end](/APP%20WEB/__frontend__produccio__/landingPage/)**: se recomienda abrirlo con el editor `vscode`, y ejecutar la página [pas1_landingSignUp.html](/APP%20WEB/__frontend__produccio__/landingPage/pas1_landingSignUp.html) mediante la extensión "live server".
- **[la base de datos](/___BBDD___/estructuraTaules/pirApp.sql)**: se aconseja abrirla y definir una conexión con la misma mediante el editor `mysql workbench`.
-  **[el back-end](/APP%20WEB/__springboot__produccio__/)**: se propone ejecturarlo mediante el editor de Java `IntelliJ idea`. Para hacerlo hay que buscar el archivo `AppApplication.java`, donde se situa la función main que en ser llamada activa todo el proyecto, y correrlo mediante "ctrl + shift + F10".  
    - >Las dependencias necesarias para el proyecto springboot pueden ser consultadas en el archivo [pom.xml](/APP%20WEB/__springboot__produccio__/app/pom.xml)

Como se ha comentado, cada uno de los tres proyectos necesita su propio puerto para poder vivir en el localhost. Para que los tres proyectos funcionen directamente sin hacer cambios en la configuración del front, back o sistema de gestión de la base de datos recomiendo al usuario que se asegure que:

- 1. el frontend corre en el puerto por defecto de Vscode, el **5500**,  en la dirección IP loopback 127.0.0.1. (es decir en `localhost:5500`).
- 2. la base de datos corre en el puerto por defecto de mysql workbench, **3306** de la mencionada dirección.
- 3. El backend de springboot corra en el puerto por defecto **8080**.

Es indispensable que se respeten estos puertos porque en las distintas fetch-APIs del front-end apuntamos al puerto 8080. Asimismo, en los controladores de springboot permitimos consumir las APIs de orígenes cruzados solamente desde el puerto 5500 y, finalmente, dentro del archivo [application.properties](/APP%20WEB/__springboot__produccio__/app/src/main/resources/application.properties) del backend especificamos que la base de datos está en el puerto 8080.

# 3. Diagrama de flujo

Como se ha comentado anteriormente, este sistema de registro y de iniciar sesión ha sido inspirado mediante un proceso de desarrollo inverso observando el funcionamiento de la página de Netflix. Por ello, el comportamiento de la aplicación que describimos en este diagrama de flujo es aplicable por partida doble: tanto para la aplicación que he creado como para la página oficial de Netflix.

El diagrama de flujo es el siguiente (está en catalán dado que los nombres de archivos los he escrito en catalán):

![esquema app no cargó](/img/esquemaApp.jpeg)

Este se puede entender del siguiente modo:

1. Cada paralelogramo de color naranja es una página estática html de nuestro proyecto.
2. Cada rombo de fondo amarillo es una decisión que se hará dentro del backend de springboot, dado que requiere hacer consultas a la BBDD y contiene datos sensibles.
3. Los rombos de fondo azul se decidirán en el front-end en tanto que sus decisiones no requieran consultar información personal en la base de datos y no requerirán, por lo tanto, de uso del back-end.
4. El paréntesis que incluye la extensión de una URL debajo de cada paralelogramo naranja es cada página de netflix cuyo comportamiento y, en menor medida, aspecto, se ha intentado replicar en el archivo html del rombo que está contiguo. Por ejemplo el archivo html `pas2A_infoBenvinguda.html` de mi proyecto es una réplica de la página especificada en el paréntesis `netflix.com/signup/registration` y el usuario llegará a ella gracias a aplicar una lógica de backend similar a la que usa Netflix. 


>⚠️ **NOTA**: es muy importante hacer notar que las consultas de datos personales a la base de datos -correos y contraseñas- no se deben hacer desde el frontend, porque los archivos del frontend son públicos para el usuario y podrían exponer los datos de los usuarios en la base de datos a vulnerabilidades.


## 3.1 Página inicial (landing page)

La página inicial de netflix, a la que denominamos, `pas1_landingSignUp.html` nos muestra la siguiente "landing page": 

![pagina inicial netflix no cargó](https://github.com/blackcub3s/miApp/blob/9d06a71d4e7966cfe74a9e770beeb251e6a7bb50/SISTEMA%20LOGIN%20REPLICAT%20DE%20NETFLIX/pas1_landingSignUp.PNG)

Nuestra página replicada [pas1_landingSignUp_.html](/img/pas1_landingSignUp_.png) tiene este aspecto:

![replica pagina inicial netflix no cargó](https://github.com/blackcub3s/miApp/blob/609d440db083e853ea9a6000fa0ba83d4d0905b9/imatges%20replica/pas1_landingSignUp_.png)

Lo más importante que tiene es el botón de iniciar sesión en la parte superior derecha y el formulario para mandar el correo y registrarse en caso que un usuario no lo esté. En este repositorio nos centraremos **únicamente** en este último aspecto: el formulario de registro. Con ello será suficiente para hacer una pequeña, pero exhaustiva, primera aproximación al framework Springboot.

Desgranaré, en los subapartados 3.1.1 a 3.1.4, los distintos tipos de clases que hay que escribir en un proyecto Springboot para conseguir obtener por parte del back-end el comportamiento de una decisión concreta del [diagrama de flujo](#Diagrama-de-flujo) que describe el comportamiento de la aplicación. En estos apartados describiré como se efectua en el back-end la decisión mostrada en el rombo "Existeix correu" o "existe correo" (es decir, en este árbol de decisión que emana de "existe correo" se está comprobando si un usuario existe en el sistema o no). Para ello, deberemos tener en mente el siguiente esquema de estructura del proyecto y el concepto de "inyección de dependencias" que también explicaremos en los próximos apartados y que es fundamental para entender como se pasa información de una clase de java a la otra dentro del mismo proyecto Springboot sin necesidad de instanciar un nuevo objeto dentro del constructor de cada clase que depende de otra. Si al usuario le interesa por qué hemos escogido este tipo de organización (`by feature y by layer` -por característica y capa-, en lugar del esquema `by feature`a secas o `by layer` a secas puede consultar el [Anexo](#Anexo)).

![esquema estructura byFeature byLayer no ha carregat](/img/byFeatureByLayer.jpeg)

Es importante que el lector entienda que cuando se programa un back-end en Springboot primero vamos a programar la clase Usuari.java, luego UsuariRepositori.java, luego UsuariServei.java y luego UsuariControlador.java. Sin embargo, en cada uno de los cuatro subapartados siguientes se desgrana cada una de estas clases en orden inverso (empezando por el último, el controller). Bajo mi punto de vista, es más sencillo de entender si al lector se le presenta la información de este modo:


### 3.1.1 del front-end al back-end y viceversa: definición de endpoints (clase "controller"), puertos y conexión con bbdd

Cuando un usuario introduce su correo en el formulario de registro de la landing page y le da al botón de registro se va a mandar el correo desde el front-end a un endpoint de la API REST del backend -en este caso, el endpoint está en la línea 171 y es esta URL: `'http://localhost:8080/api/usuariExisteix'`- transmitiéndose a través del _cuerpo_ o body de la solicitud POST mediante formato JSON; en este caso particular, si el correo introducido es `asd@ijk.com` entonces el mensaje transmitido será un javascript object con esta forma: `{"email":"asd@ijk.com"}`:

https://github.com/blackcub3s/miApp/blob/9d06a71d4e7966cfe74a9e770beeb251e6a7bb50/APP%20WEB/__frontend__produccio__/landingPage/pas1_landingSignUp.html#L171-L177

Asimismo, dentro del back-end de springboot queremos exponer al front-end el *endpoint* correspondiente para recibir el cuerpo de esta solicitud. Los distintos endpoints en springboot (y probablemente en otros frameworks de back-end) van a ser definidos dentro de lo que llaman una clase "controller" o controlador. En este caso, la clase controlador que nos ocupa está en el archivo `UsuariControlador.java`, en donde tenemos especificado que -por seguridad- SOLO se permite el acceso cruzado desde la IP loopback y el puerto 5500 (básicamente, el front-end de vscode). 

Siguiendo con el ejemplo anterior, la solicitud POST (que lleva `{"email":"asd@ijk.com"}` en el cuerpo) hecha desde el front-end será recogida en formato JSON por el endpoint del back-end (`localhost/api/usuariExisteix`). Acto seguido, ésta será resuelta con una petición de vuelta, también en formato JSON, por la función `verificarUsuari` con información en el cuerpo de la respuesta de la solicitud POST: es decir, ahora el cliente recibirá en nuestro código javascript un  `{existeixUsuari: false}`, ya que el usuario de correo `asd@ijk.com` no existe en la base de datos de usuarios-. Fijaros bien en la anotación @CrossOrigin de la línea 33, en donde definimos la IP del front-end desde donde se permite que nos manden datos:

https://github.com/blackcub3s/miApp/blob/9d06a71d4e7966cfe74a9e770beeb251e6a7bb50/APP%20WEB/__springboot__produccio__/app/src/main/java/pirapp/app/Usuaris/controlador/UsuariControlador.java#L31-L52

Finalmente hay que mencionar que la base de datos también se especifica en el proyecto. Especificamos su puerto e ip dentro del archivo application.properties (esto será de relevancia para entender luego como vinculamos la información de la base de datos con la clase Usuari).

https://github.com/blackcub3s/miApp/blob/f2321cda760d75436d16658b72a4507e5701f507/APP%20WEB/__springboot__produccio__/app/src/main/resources/application.properties#L4-L8


### 3.1.2 La clase "service" (la lógica de negocio)

Si nos hemos fijado en el subapartado anterior, habremos visto que la clase controlador llamaba a una función de un objecto "serveiUPP", de tipo "UsuariServei":

https://github.com/blackcub3s/miApp/blob/9d06a71d4e7966cfe74a9e770beeb251e6a7bb50/APP%20WEB/__springboot__produccio__/app/src/main/java/pirapp/app/Usuaris/controlador/UsuariControlador.java#L39

Vamos ahora a ver el cuerpo de esta función en la clase usuariServei (clase "service" o "servicio") que en esencia lo que hace es implementar la lógica de negocio de nuestra app. Cómo vamos a obtener el booleano que nos permite determinar si un usuario está registrado? Pues así:

https://github.com/blackcub3s/miApp/blob/9d06a71d4e7966cfe74a9e770beeb251e6a7bb50/APP%20WEB/__springboot__produccio__/app/src/main/java/pirapp/app/Usuaris/servei/UsuariServei.java#L35-L41

En esencia lo que hacemos en la función `usuariRegistrat(String eMail)` es pasar como parámetro el mail que hemos obtenido desde el controlador (que de hecho proviene en primera instancia del formulario de registro que un usuario web acabe de mandar) y ver si este mail se encuentra en la base de datos de usuarios. Si se encuentra en la bbdd -es decir, el usuario ya estaba registrado en la tabla de usuarios- la variable `mailUsuari` tendrá un string dentro. En caso que no -usuario no registrado-, la consulta a la base de datos no devolverá ningún resultado (en cuyo caso el tipo "Optional", que envuelve el tipo string, permitirá evitar errores. Finalemnte, el juicio de si existe o no existe un usuario, en base a esta lógica lo efectuamos en el return (con `mailUsuari.isPresent(`)) que es una función va en tandem con el tipo Optional<String>.

Bien, y después de esto nos preguntaremos... ¿Y dónde están las consultas a la base de datos? Porque bien que estamos haciendo consultas en una base de datos... ¿verdad? Pues esto nos lleva a hablar en el siguiente subapartado de la clase repositiorio y llegamos a ella ya que en la función de la clase "service" de la que hemos hablado tenemos una llamada al objeto "repoUsuari" que pertenece a la clase UsuariRepositori.java:


https://github.com/blackcub3s/miApp/blob/9d06a71d4e7966cfe74a9e770beeb251e6a7bb50/APP%20WEB/__springboot__produccio__/app/src/main/java/pirapp/app/Usuaris/servei/UsuariServei.java#L38


### 3.1.3 definición de la clase "repository" (las consultas a la BBDD)

La clase `UsuariRepositori.java` (en el título, "repository" del inglés) implementa una interfaz que hereta las funciones de la clase JpaRepository (de ahí la palabra `extends`). Es en `UsuariRepositori.java` en donde escribiremos las consultas con lenguaje mySQL que queramos lanzar sobre la tabla de usuarios (si no queremos escribir estas consultas, en determinados casos, podremos evitarlo porque ya hay funciones predefinidas dentro de JpaRepository que permiten solucionar peticiones frecuentes en bbdd). Estas queries se especifican mediante la anotación Java `@Query` encima de cada una de las cabeceras de las funciones que escribamos. Cada una de estas funciones no va a tener cuerpo en realidad, y su cabecera va a actuar como un sistema para pasar valores por parámetro a la query mysql, por un lado; y un sistema para devolver el resultado de la query o consulta con el tipo de datos que especifiquemos para el return, por el otro.

Así las cosas, aquí tenemos la función `trobaStringUsuariPerCorreu(eMail)` del "repository" , de la que hemos hablado al final del apartado anterior donde se puede apreciar la cabecera de la función con la anotación @Query por encima.

https://github.com/blackcub3s/miApp/blob/9d06a71d4e7966cfe74a9e770beeb251e6a7bb50/APP%20WEB/__springboot__produccio__/app/src/main/java/pirapp/app/Usuaris/repositori/UsuariRepositori.java#L27-L28

Si prestáis antención, veréis que existe otra anotación denominada `@Param` y que esta anotación toma el parámetro "emailete". Asimismo, el valor que se pasa en la query mysql es el parámetro anterior con dos puntos delante (":emailete") y no tiene directamente el nombre "eMail" que proviene del parámetro de entrada de la función. Esto es porque hacer la consulta mysql a través de la anotación @Param ayuda a protegernos contra ciberataques como, por ejemplo, la inyección de mysql -no queremos que ningún usuario ponga consultas sql en donde deberían haber valores, verdad?-.

### 3.1.4 definición de la clase "Usuari" (con el ORM o mapeado de objeto java a entidad de bbdd)

Una de las cosas que implementa spring boot es la posibilidad de mapear una clase de Java con una tabla mysql mediante el uso de la anotación `@Entity`. Vamos a comparar el DDL[^3] de la tabla "usuari" de mySQL con la clase anotada con `@Entity`, que mapea a esa tabla.

Aquí tenemos la tabla usuari en mySQL:

https://github.com/blackcub3s/miApp/blob/f9ada4be41a84beed2ae312fd33dbe4e6102975c/___BBDD___/estructuraTaules/pirApp.sql#L5-L13

Aquí tenemos la clase Java que mapea a la tabla usuari de mySQL: 

https://github.com/blackcub3s/miApp/blob/f9ada4be41a84beed2ae312fd33dbe4e6102975c/APP%20WEB/__springboot__produccio__/app/src/main/java/miApp/app/Usuaris/model/Usuari.java#L20-L42

Es fácil ver que cada atributo java simplemente está mapeando cada columna de la tabla. Por ello, si hacemos una instancia de esa clase Java, tendremos simplemente una fila de la tabla mysql usuari recogida en ese objeto.

Sin embargo, no todo el monte es orégano: Hay que especificar muchos detalles en el código Java y ser muy preciso para que el mapeo entre ambos funcione correctamente: 

* en primer lugar, cada Atributo Java requiere la anotación `@Column` en la que se va a especificar el nombre del campo en la base de datos dentro de la misma. 
* En segundo lugar, en el DDL de mySql es importante seguir la notación `underscore_case` (nombre_variable), que es la que se pondrá como parámetro dentro de `@Column` (por un lado); y en los atributos Java de la clase mapeada del model hay que usar la nomenclatura `camelCase` (nombreVariable). En caso contrario dará errores.
* En tercer lugar, si la columna de la tabla mySQL es NOT NULL, hay que poner el parámetro `nullable = false`
* En cuarto lugar, hay que vigilar con las correspondencias de tipos de datos de mySQL y tipos de datos reconocidos en Java y en JPA. El tipo de datos mySQL "tinyInt" usado en el campo `pla_suscripcio_anual` de la tabla usuari no existe con este mismo nombre en Java. Sin embargo, dado que un tinyInt ocupa 1 byte con esta cantidad de información podemos representar lo que queremos: numeros desde el 0 a 7[^4]; así que en este caso para mapear a "tinyInt" debemos usar el tipo de datos de Java denomindao "Byte". Las otras opciones de tipos recomendados por JPA realmente fallaron.
* En quinto lugar, las relaciones entre tablas se pueden definir dentro de Java pero, en mi caso, han dado problemas así que las dejaré definidas dentro del código mysql y en la lógica de programación deberé gestionar plenamente de forma manual la inserción de datos en cada tabla.
* En sexto lugar, si tenemos una clave primaria hay que usar la anotación @Id en java, porque si no lo hacemos al implementar la interfaz repository como una clase que herede de la clase JpaRepository nos dará error. Esto pasará porque JpaRepository nos pide especificar el tipo de datos de la clave primaria de la Entity en cuestión, en este caso la entity Usuari -y java debe saber también cual es esa clave primaria-. La especificación se hace en la negrita: _public interface UsuariRepositori extends JpaRepository<Usuari, **Integer**>_



En este caso parte de los problemas derivados de la definición de la clase `Usuari.java` vienen derivados a una decisión que he tomado como desarrollador: la decisión de escribir mi propio código mySQL y validarlo luego con JPA y Hibernate en lugar de dejar que estas librerías de java creen las bases de datos por sí mismas. A menudo este es el comportamiento que se busca con JPA e Hibernate; sin embargo, considero que este quita control al desarrollador. Es cierto que el trabajo es doble al definir tanto el DDL en una base de datos como la clase Java con la anotación @Entity, pero asó no solo se gana en control, sino también en libertad al poder poder virar a otra plataforma si en un futuro así lo decide el desarrollador.

Para poder hacer que JPA no cree las tablas sino que valide que correspondan a la perfección con la clase del model debemos asegurarnos que tenemos una línea en el `application.properties` donde se especifique que el automatismo del DDL que ofrece JPA esté configurado en "validate" en lugar de permitir que Java pueda crear o eliminar tablas, tal que así:

https://github.com/blackcub3s/miApp/blob/c25ef5ba7e742f38300430c72d3f8f8357bb0ddf/APP%20WEB/__springboot__produccio__/app/src/main/resources/application.properties#L14

> NOTA: Ya para terminar con el "model" es imporante especificar que las clases de java del model (es decir, las que tienen @Entity) nos permiten evitar escribir constructores con y sin parámetros, getters, setters, etc .(que es lo que hay que hacer cuando creamos una clase en java y que es tan mecánico que incluso editores como netbeans permiten generarlos automáticamente). En este caso, empero, tenemos una librería en springboot que se denomina `Lombok`. Esta librería no solo nos asiste como lo haría netbeans, sino mejor. De hecho permite escribir unas anotaciones que crean los constructores (@NoArgsConstructor, @AllArgsConstructor), y una sola anotación (@Data) que crea los getters y setters -y otros- sin que nosotros los veamos escritos, actualizándolos incluso si cambiamos nombres de atributos. Así se reduce escribir el código mecánico que caracteriza las clases de java (menos boilerplate es mejor) y, lo más importante, que nos olvidamos de mantenerlo también: si hacemos cambios en el nombre de un atributo java, por ejemplo, ya no tendremos que preocuparnos sobre cambiar el nombre del getter y setter para ese atributo (se hará de forma automática por parte de Lombok).


### 3.1.5 La inyección de dependencias

Antes hemos visto que en la la clase controlador (UsuariControlador) llamamos una función de la clase servicio (UsuariServei). De forma análoga, dentro de la clase UsuariServei llamamos a una función de la clase UsuariRepositori. Por lo tanto, para conseguir hacer esto podríamos haber hecho nuestro programa de dos formas:

- **FORMA 1. instanciar objetos**: este es el método menos óptimo y requiere:
    - A. Instanciar dentro de la clase `UsuariControlador` una instancia u objeto de la clase `UsuariServei`.
    - B. instanciar dentro de la clase `UsuariServei` un objeto de la clase `UsuariRepositori`.

- **FORMA 2. inyectar dependencias**: Esta es la forma más correcta de desarrollar nuestra aplicación, porque facilita los tests unitarios y los cambios en la clase dependientes sin tener que modificar código en la clases en donde se inyectan las dependencias en cuestión. Este método requiere, para el caso que nos ocupa:

    - A. Vamos a la clase controlador e inyectamos un atributo de tipo "UsuariServei" (clase servicio) y, acto seguido, lo pasamos por parámetro por el constructor de la clase controlador con la anotación @Autowired. 
    - B. Vamos a la clase servicio e inyectamos un atributo de tipo "UsuariRepositori". Luego lo pasamos por parámetro por el constructor de la clase servicio con la anotación `@Autowired`. Por ejemplo, en este último caso lo que hemos hecho ha sido esto:
    https://github.com/blackcub3s/miApp/blob/2f730ce5c1b1c23a573ff47b9dc6b5fe194b09d8/APP%20WEB/__springboot__produccio__/app/src/main/java/miApp/app/Usuaris/servei/UsuariServei.java#L22-L33

Para más información 
https://stackoverflow.com/questions/3386889/difference-between-creating-new-object-and-dependency-injection

>NOTA: Existe la inyección de dependencias por campo en lugar de la inyección de dependencias por constructor (pondríamos autowired directamente encima del atributo de la clase). Sin embargo, esta es menos recomendada.


## 3.2 Existe correo de usuario y usuario tiene acceso a recursos

Pongamos por caso que en el controlador del que hablamos en el apartado 3.1.1 recibimos via API REST un JSON del estilo `{"email":"acces@gmail.com"}`, ya que en la landing page el usuario ha introducido esto en el formulario:

![no se mostro iniciar sesión landing](/img/correuAmbAccesLanding.png)

Pongamos por caso también que ese correo existe en la tabla usuari y que, además, el usuario que controla esta cuenta de correo tiene acceso a recursos de nuestra aplicación. Si este es el caso el back-end aplicará la lógica de negocio previamente mencionada en el apartado 3.1 y subapartados 3.1.1 a 3.1.4 y conseguirá que nuestro front-end reciba del servidor `{"existeixUsuari": true, "teAccesArecursos": true}` (si el lector quiere probar la aplicación verá como se imprime la consulta al endpoint con una alerta -esto en producción no se imprimiría-).

Al obtener este objeto javascript en el frontend (que previamente era un Hashmap en el back-end de java por cierto, pero la API REST y JSON permite que pasemos información entre distintos lenguajes aprovechando que tienen tipos de objetos  similares) permitirá que la aplicación dictamine que el usuario se ha "confundido" poniendo su correo en el campo de registro y, para permitir reconducirle, le mandará automática y directamente a la página de login o iniciar sesion (la misma a la que se accede a la parte superior derecha). 

Para que el usuario no se moleste en reintroducir el correo electrónico en el campo de correo de la pantalla de [iniciar sesión](/APP%20WEB/__frontend__produccio__/landingPage/pas2C_login.html), este correo pasará por el front-end via localstorage, como vemos en el código javascript:

https://github.com/blackcub3s/miApp/blob/9d06a71d4e7966cfe74a9e770beeb251e6a7bb50/APP%20WEB/__frontend__produccio__/landingPage/pas1_landingSignUp.html#L191-L196

Y se recogerá en la página de [iniciar sesión](/APP%20WEB/__frontend__produccio__/landingPage/pas2C_login.html) en la siguientes líneas de código:

https://github.com/blackcub3s/miApp/blob/9d06a71d4e7966cfe74a9e770beeb251e6a7bb50/APP%20WEB/__frontend__produccio__/landingPage/pas2C_login.html#L27-L37

Mostrándose así:

![no se mostró imagen iniciar sesion](/img/correuAmbAccesLogin.png)





## 3.3 Existe correo de usuario y usuario MOT tiene acceso a recursos

Posar link a `pas2B_introduirContrasneya.html` 

## 3.4 No existe correo (no existe usuario)

Posar link a `pas2A_infoBenvinguda.html`



# Anexo

Existen `tres` formas de organizar un proyecto Springboot, cada una con sus ventajas y sus desventajas. Las desgranaremos poniendo un ejemplo con la estructura que habría tomado este pequeño proyecto con cada una de ellas:


* `By Feature` (por característica de la app): Si se hubiese escogido esta forma de estructurar el proyecto habría tenido el aspecto que tiene la imagen siguiente. Este tipo de organización permite que cuando hay pocos archivos estos se encuentren rápidamente. Sin embargo, si una característica requiere múltiples clases en el model, también requerirá múltiples repositorios, múltiples servicios... La gracia de organizar por característica es que estén juntos solamente los archivos de distintas capas que funcionen juntos (y esto no se consigue cuando la aplicación crece).

![esquema estructura byFeature no ha carregat](/img/byFeature.jpeg)

* `By layer` (por capa): Hemos comentado en este readme que un proyecto springboot se organiza en distintas capas (modelo, repositorio, servicio y controlador). Si separamos en carpetas -"packages, en java"- cada una de ellas encontraremos por ejemplo todas las definiciones de base de datos mediante ORM en una sola carpeta (model); todas las clases de definiciones de consultas de bases de datos en otra (repository); todas las clases para definir endpoints en otra (controlador)... Esto puede ser útil cuando el proyecto es pequeño, y cuando crece en mi opinión es más claro usar este enfoque que usar el enfoque *by feature*; sin embargo, si crece mucho tendremos múltiples "entities" dentro de los archivos de definición de "model" y éstas van a apuntar a características muy diferentes. Por ejemplo, imaginemos la tabla de usuarios con sus datos (entity usuaris) y una tabla de base de datos donde guardásemos los distintos paises a los que pertenecen las IPs entrantes que se conectan a nuestra aplicación. Si en este caso no nos importase mapear a qué usuario pertenece cada IP, eso supondría que cada una de las Entities (clases java dentro del model) mapearía a una tabla de BBDD que pertenecería o apuntaría a una caracteristica distinta de la aplicación. Entonces estas dos clases no tendría sentido que estuviesen juntas aunque ambas sean clases de capa.

![esquema estructura byLayer no ha carregat](/img/byLayer.jpeg)

* `By Feature and by layer` (por característica y por capa): Esta forma de estructurar es la que **ha sido escogida para este proyecto**: dado que el proyecto se prevé que crezca bastante en el futuro, este tipo de organización es el más robusto para para mantener las clases organizadas. Lo que se hace es organizar por capa DENTRO de cada característica:

![esquema estructura byFeature byLayer no ha carregat](/img/byFeatureByLayer.jpeg)


[^1]: La forma como yo entiendo estos principios es que hay que conseguir que cada solicitud al servidor mediante la API sea suficiente en sí misma y sin estado que requiera persistencia en servidor, que los datos que se pasen a través de la solicitud de la fetch-API a los endpoints de la API del backend se hagan mediante JSON con uno o varios pares de claves valor donde la clave explique claramente a qué se destina el valor, que se utilicen métodos HTTP que apunten a endpoints con estructura URL, etc.

[^2]: no es necesario configurar un servidor para correr los proyectos sino que podemos usar nuestro propio ordenador como si fuese un servidor -cuya IP será la de loopback y cada proyecto se expondrá en un puerto distinto que permitirá su comunicación con los demás.

[^3]: DDL: Data Definition Language. Cuando tenemos una sentencia mySQL con create, forma parte del DDL.

[^4]: En un byte tenemos tres bits y, con ellos, podremos representar el número 0 (**000**), el 1 (**001**), el 2 (**010**)... y hasta el 7 (**111**).
