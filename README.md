# 1. Introducción

En este repositorio muestro una aplicación web que estoy haciendo. Se ha anonimizado el concepto en sí mismo para ser lo más generalista posible. En primer lugar, podréis ver [el front end](/APP%20WEB/__frontend__produccio__/landingPage/) hecho con HTML5, CSS y javascript puro (sin frameworks). En segundo lugar, podréis ver el [backend](/APP%20WEB/__springboot__produccio__/) hecho con `springboot` (framework de backend de java) y, finalmente, una [base de datos](/___BBDD___/estructuraTaules/pirApp.sql) hecha con mySQL que contiene dos tablas de usuarios -a la que por ahora solo usamos una-.

La gracia de la aplicación web que contiene este repositorio es que mediante el back-end se ha intentado (y, en mi opinión, conseguido) hacer una réplica de la lógica de negocio que sigue Netflix cuando un usuario se registra en su web. Cuando este se registra en Netflix o en nuestra web, va a entrar en la antesala de un sistema preparado para brindarle, con la máxima sencillez posible, la posibilidad de inscribirse a un servicio por suscripción. Cuando el usuario introduce su e-mail en el formulario de la landing page, el sistema evalúa cuál es el estado actual del usuario en nuestra base de datos: en función de ello el sistema mostrará un camino u otro. Si el uduaruo está registrado o no està registrado, o si al edtar registrado está pagando por servicios o no, generarán que el back-end redirija al usuario a distintas paginas de acuerdo al que se especifica en el [diagrama de flujo](#Diagrama-de-flujo) que mostraremos en el siguiente apartado. 

Además, la comunicación entre front-end y back-end la he realizado en todo caso mediante API (usaremos la interfaz nativa del navegador fetch-api en el front-end para que este pueda "consumir" los endpoints que nos proporciona la rest-api del backend). En todo caso se ha seguido en la medida de lo posible los principios REST (véase nota al pie[^1]). Así pues, debe quedar claro que el back-end, el front-end son dos proyectos separados que estan en comunicación entre ellos y que el back-end, además, se comunicará con la base de datos que será un tercer proyecto. Así pues podría reescribirse el proyecto front-end y pasarlo a otra tecnología basada en componentes (por ejemplo Angular, React o Vue) sin tener que modificar el backend, o incluso podría usarse el mismo backend para permitir que los endpoints sean consumidos por otra aplicación además del front-end: por ejemplo, una aplicación móvil.

# 2. Objetivo

Mostrar como se se ha efectuado el árbol de decisión que está marcado en color lila en el apartado [diagrama de flujo](#Diagrama-de-flujo) y explicar a grandes rasgos como se estructura un proyecto en springboot en sus distintas partes (modelo, repositorio, servicio y controlador). Además también queremos demostrar como podemos utilizar este framework para traducir el código en resultados tangibles y ordenados para la lógica de negocio. No se comentarán aspectos de los estilos del frontend aunque si el lector tiene preguntas puede escribirme por linkedin y preguntar lo que desee.

# 3. Recomendaciones para ejecutar el proyecto

Se recomienda al lector que, si quiere probar y ver la aplicación web con todas sus característica y en su totalidad, haga un clone del repositorio y abra cada uno de los tres proyectos con su editor correspondiente. De este modo cada editor expondrá una URL y un puerto en el mismo equipo[^2]. Los tres proyectos, pues, se recomiendan tratar con los siguientes editores:

- **[el front-end](/APP%20WEB/__frontend__produccio__/landingPage/)**: se recomienda abrirlo con el editor `vscode`, y ejecutar la página [pas1_landingSignUp.html](/APP%20WEB/__frontend__produccio__/landingPage/pas1_landingSignUp.html) mediante la extensión "live server".
- **[la base de datos](/___BBDD___/estructuraTaules/pirApp.sql)**: se aconseja abrirla y definir una conexión con la misma mediante el editor `mysql workbench`.
-  **[el back-end](/APP%20WEB/__springboot__produccio__/)**: se propone ejecturarlo mediante el editor de Java `IntelliJ idea`. Para hacerlo hay que buscar el archivo AppApplication.java, donde se situa la función main que activa todo el proyecto, y correrlo mediante "ctrl + shift + F10"

Como se ha comentado cada uno de los tres proyectos necesita su propio puerto para poder vivir en el localhost. Para que las los tres proyectos funcionen directamente sin hacer cambios en la configuración del front, back o sistema de gestión de la base de datos recomiendo al usuario que se asegure que:

- 1. el frontend corre en el puerto por defecto de Vscode, el **5500**,  en la dirección IP loopback 127.0.0.1. (es decir en `localhost:5500`).
- 2. la base de datos corre en el puerto por defecto de mysql workbench, **3306** de la mencionada dirección.
- 3. El backend de springboot corra en el puerto por defecto **8080**.

En primer lugar, es importante que se respeten estos puertos porque en las distintas fetch-APIs del front-end apuntamos al puerto 8080. Asimismo, en los controladores de springboot permitimos consumir las APIs de orígenes cruzados solamente desde el puerto 5500. Finalmente, dentro del archivo [aplication/properties](APP WEB/__springboot__produccio__/app/src/main/resources/application.properties) del backend especificamos que la base de datos está en el puerto 8080. En el apartado [Ir a la sección 3.1 Página inicial (landing page)](#31-página-inicial-landing-page) explicamos concretamente qué es lo que pasa realmente.


> NOTA: Esta página es software propietario mio que no es de dominio público en tanto que forma parte de una idea de negocio, con lo cual solamente voy a mostrar una pequeña porción del mismo para demostrar un conocimiento generalista del framework springboot.

# 3. Diagrama de flujo

Como se ha comentado anteriormente, este sistema de registro y de iniciar sesión ha sido inspirado mediante un proceso de desarrollo inverso observando el funcionamiento de la página de netflix. Así pues, el comportamiento que describimos aquí es tanto aplicable a la página que he creado como a la página de netflix.

El diagrama de flujo del proyecto de este repositiorio es el siguiente:

TO DO: POSAR IMATGE DIAGRAMA

Este se puede entender del siguiente modo:

1. Cada paralelogramo de color naranja es una página estática html de nuestro proyecto.
2. Cada rombo de fondo amarillo es una decisión que se hará dentro del backend de springboot, dado que requiere hacer consultas a la BBDD y contiene datos sensibles.
3. Los rombos de fondo azul se decidirán en el front-end en tanto que sus decisiones no requieran consultar información personal en la base de datos y no requerirán, por lo tanto, de uso del back-end.
4. El paréntesis que incluye la extensión de una URL debajo de cada paralelogramo naranja es cada página de netflix cuyo comportamiento y, en menor medida, aspecto, se ha intentado replicar en el archivo html del rombo que está contiguo. Por ejemplo el archivo html `pas2A_infoBenvinguda.html` de mi proyecto es una réplica de la página especificada en el paréntesis `netflix.com/signup/registration` y el usuario llegará a ella gracias a hacer una misma lógica de backend que la que usa netflix. 


>⚠️ **NOTA**: es muy importante hacer notar que las consultas de datos personales a la base de datos -correos y contraseñas- no se deben hacer desde el frontend, porque los archivos del frontend son públicos para el usuario y podrían exponer los datos de los usuarios en la base de datos a vulnerabilidades.


## 3.1 Página inicial (landing page)

La página inicial de netflix, a la que denominamos, `pas1_landingSignUp.html` nos muestra la siguiente "landing page": 

![pagina inicial netflix no cargó](https://github.com/blackcub3s/miApp/blob/9d06a71d4e7966cfe74a9e770beeb251e6a7bb50/SISTEMA%20LOGIN%20REPLICAT%20DE%20NETFLIX/pas1_landingSignUp.PNG)

Nuestra página replicada [pas1_landingSignUp_.html](/imatges%20replica/pas1_landingSignUp_.png) tiene este aspecto:

![replica pagina inicial netflix no cargó](https://github.com/blackcub3s/miApp/blob/609d440db083e853ea9a6000fa0ba83d4d0905b9/imatges%20replica/pas1_landingSignUp_.png)

Lo más importante que tiene es el botón de iniciar sesión en la parte superior derecha y el formulario para mandar el correo y registrarse en caso que un usuario no lo esté. En este repositorio nos centraremos **únicamente** en este último aspecto: el formulario de registro. Con ello será suficiente para hacer una pequeña, pero exhaustiva, primera aproximación al framework Springboot.

Desgranaré, en los subapartados 3.1.1 a 3.1.4, los distintos tipos de clases que hay que escribir en un proyecto springboot. Para ello, deberemos tener en mente el siguiente esquema y el concepto de "inyección de dependencias" que también explicaremos después y que es fundamental para entender como pasa información de una clase de java a la otra dentro del mismo proyecto Springboot sin necesidad de instanciar un nuevo objeto dentro del constructor.

>**INSERTAR IMATGE D'ESQUEMA COMPLETAT**

Es importante que el lector entienda que cuando se programa un backend en springboot primero vamos a programar la clase Usuari.java, luego UsuariRepositori.java, luego UsuariService.java y luego UsuariController.java. Sin embargo, en cada uno de los cuatro subapartados siguientes se desgrana cada una de estas clases en orden inverso (empezando por el último, el controller). Bajo mi punto de vista, es más sencillo de entender si al lector se le presenta la información de este modo:


## 3.1.1 del front-end al back-end: definición de endpoints (clase "controller"), puertos y conexión con bbdd

Cuando un usuario introduce su correo en el formulario de registro y le da al botón de registro esto va a mandar el correo desde el front-end a un endpoint de la API REST del backend -en este caso, el endpoint está en la línea 171-, pasándose este dentro del _cuerpo_ o body de la solicitud POST mediante formato JSON; en este caso particular, pongamos que será así: `{"email":"asd@ijk.com"}`:

https://github.com/blackcub3s/miApp/blob/9d06a71d4e7966cfe74a9e770beeb251e6a7bb50/APP%20WEB/__frontend__produccio__/landingPage/pas1_landingSignUp.html#L171-L177

Asimismo, dentro del back-end de springboot queremos exponer al front-end el *endpoint* correspondiente para recibir el cuerpo de esta solicitud. Los distintos endpoints en springboot (y probablemente en otros frameworks de back-end) van a ser definidos dentro de lo que llaman una clase "controller" o controlador. En este caso, la clase controlador que nos ocupa está en el archivo `UsuariControlador.java`, en donde tenemos especificado que -por seguridad- SOLO se permite el acceso cruzado desde la IP loopback y el puerto 5500 (básicamente, el front-end de vscode). 

Siguiendo con el ejemplo anterior, la solicitud POST (que lleva `{"email":"asd@ijk.com"}` en el cuerpo) hecha desde el frontend será recogida en formato JSON por el endpoint del backend (`localhost/api/usuariExisteix`). Acto seguido, ésta será resuelta con una petición de vuelta, también en formato JSON, por la función `verificarUsuari` con información en el cuerpo de la respuesta de la solicitud POST: es decir, ahora el cliente recibirá en nuestro código javascript un  `{existeixUsuari: false}`, ya que el usuario de correo `asd@ijk.com` no existe en la base de datos de usuarios-. Fijaros bien en la anotación @CrossOrigin de la línea 33, en donde definimos la IP del front-end desde donde se permite que nos manden datos:

https://github.com/blackcub3s/miApp/blob/9d06a71d4e7966cfe74a9e770beeb251e6a7bb50/APP%20WEB/__springboot__produccio__/app/src/main/java/pirapp/app/Usuaris/controlador/UsuariControlador.java#L31-L52


Finalmente hay que mencionar que la base de datos también se especifica en el proyecto. Especificamos su puerto e ip dentro del archivo application.properties (esto será de relevancia para entender luego como vinculamos la información de la base de datos con la clase Usuari).

https://github.com/blackcub3s/miApp/blob/f2321cda760d75436d16658b72a4507e5701f507/APP%20WEB/__springboot__produccio__/app/src/main/resources/application.properties#L4-L8


## 3.1.2 definición de la clase "service" (la lógica de negocio)

**TO DO: Aqui parlar de la injeccio de dependencies i posar aquest link. Aixi el mateix objecte es comparteix per diferents instancies en comptes de haver d'instanciar de nou:**
https://stackoverflow.com/questions/3386889/difference-between-creating-new-object-and-dependency-injection

## 3.1.3 definición de la clase "Respositori" (las consultas a la BBDD)

## 3.1.4 definición de la clase "Usuari" (con el ORM o mapeado de objeto java a entidad de bbdd)

















 Si el usuario ha introducido un correo y el correo tiene una estructura válida se va a hacer una primera comprobación en el back-end y la base de datos mediante la API rest. Entonces se mirará si existe el correo.

# 3.2 Existe correo (existe usuario)

Si el correo existe ahora miramos (asterisco 2)

## 3.2.1 Usuario sí tiene acceso a recursos

Posar link a `pas2C_login.html`

## 3.2.2 Usuario no tiene acceso a recursos

Posar link a `pas2B_introduirContrasneya.html` 

# 3.3 No existe correo (no existe usuario)

Posar link a `pas2A_infoBenvinguda.html`







[^1]: La forma como yo entiendo estos principios es que hay que conseguir que cada solicitud al servidor mediante la API sea suficiente en sí misma y sin estado que requiera persistencia en servidor, que los datos que se pasen a través de la solicitud de la fetch-API a los endpoints de la API del backend se hagan mediante JSON con uno o varios pares de claves valor donde la clave explique claramente a qué se destina el valor, que se utilicen métodos HTTP que apunten a endpoints con estructura URL, etc.

[^2]: no es necesario configurar un servidor para correr los proyectos sino que podemos usar nuestro propio ordenador como si fuese un servidor -cuya IP será la de loopback y cada proyecto se expondrá en un puerto distinto que permitirá su comunicación con los demás.

