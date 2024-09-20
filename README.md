# 1. Introducción

En este respositorio muestra una aplicación web que estoy haciendo para una idea de negocio. Se ha anonimizado el concepto en sí mismo para ser lo más generalista posible. Podréis ver [el front end](/APP%20WEB/__frontend__produccio__/landingPage/) hecho con HTML5, CSS y javascript puro (sin frameworks), por un lado; así como el [backend](/APP%20WEB/__springboot__produccio__/) hecho con `springboot` (framework de backend de java) y una [base de datos](/___BBDD___/estructuraTaules/pirApp.sql) hecha con mySQL que contiene dos tablas de usuarios -a la que por ahora solo usamos una-.

La gracia de la aplicación web que contiene este proyecto es que mediante el back-end se ha intentado (y, en mi opinión, conseguido) hacer una réplica de la lógica de negocio que sigue Netflix cuando un usuario se registra en su web. Cuando un usuario se registra en netflix o en nuestra web, va a entrar en la antesala de un sistema preparado para brindarle, con la máxima sencillez posible, la posibilidad de inscribirse a un servico por suscripción. Esto se consigue en función del estado actual del usuario en nuestra base de datos: al usuario simplemente se le redigirá a distintos archivos estáticos de la página web dependiendo de si este está registrado o no en la tabla de usuarios y, también, en función de de si este está pagando por servicios o no, de acuerdo al [diagrama de flujo](#Diagrama-de-flujo) que mostraremos en el siguiente apartado. 

Además, la comunicación entre front-end y back-end la he realizado en todo caso mediante API (usaremos la interfaz nativa del navegador fetch-api en el front-end para que este pueda "consumir" los endpoints que nos proporciona la rest-api del backend). En todo caso se ha seguido en la medida de lo posible los principios REST (véase nota al pie[^1]). Así pues, debe quedar claro que el back-end, el front-end son dos proyectos separados que estan en comunicación entre ellos y que el backend, además, se comunicará con la base de datos que será un tercer proyecto. Así pues podría reescribirse el proyecto frontend y pasarlo a otra tecnología basada en componentes (por ejemplo Angular, React o Vue) sin tener que modificar el backend, o incluso podría usarse el mismo backend para permitir que los endpoints sean consumidos por otra aplicación además del front-end: por ejemplo, una aplicación móvil.

# 2. Objetivo

Mostrar como se se ha efectuado el árbol de decisión que está marcado en color lila en el apartado[diagrama de flujo](#Diagrama-de-flujo) y explicar a grandes rasgos la lógica usada en springboot y cómo se estructura un proyecto en springboot en sus distintas partes (modelo, repositorio, servicio y controlador). No se comentarán aspectos de los estilos del frontend aunque si el lector tiene preguntas puede escribirme por linkedin y preguntar lo que desee.

# 3. Recomendaciones para ejecutar el proyecto

Se recomienda al lector que, si quiere probar y ver la aplicación web con todas sus característica y en su totalidad, haga un clone del repositorio y abra cada uno de los tres proyectos con el editor correspondiente. De este modo cada uno expondrá una URL y un puerto en su propio equipo (no es necesario configurar un servidor para correr los proyectos sino que podemos usar nuestro propio ordenador como si fuese un servidor -cuya IP será la de loopback- con tres puertos que se comunican entre sí). Los tres proyectos, pues, se deben tratar de distinto modo:

- [el frontend](/APP%20WEB/__frontend__produccio__/landingPage/) se recomienda abrirlo con el editor **vscode**, y ejecutar mediante la extensión live server la página [pas1_landingSignUp.html](/APP%20WEB/__frontend__produccio__/landingPage/pas1_landingSignUp.html).
- [la base de datos](/___BBDD___/estructuraTaules/pirApp.sql) se aconseja abrirla y definir una conexión con la misma mediante el editor **mysql workbench**.
-  [el backend](/APP%20WEB/__springboot__produccio__/) se propone ejecturarlo mediante el editor de Java **IntelliJ idea**. Para hacerlo hay que buscar el archivo AppApplication.java, donde se situa la función main que activa todo el proyecto, y correrlo mediante "ctrl + shift + F10"

Como se ha comentado cada uno de los tres proyectos necesita su propio puerto para poder vivir en el localhost. Para que las los tres proyectos funcionen directamente sin hacer cambios en la configuración del front, back o sistema de gestión de la base de datos recomiendo al usuario que se asegure que:

- 1. el frontend corre en el puerto por defecto de Vscode, el **5500**,  en la dirección IP loopback 127.0.0.1. (es decir en `localhost:5500`).
- 2. la base de datos corre en el puerto por defecto de mysql workbench, **3306** de la mencionada dirección.
- 3. El backend de springboot corra en el puerto por defecto **8080**.

En primer lugar, es importante que se respeten estos puertos porque en las distintas fetch-APIs del front-end apuntamos al puerto 8080. Por poner un ejemplo, esto lo podemos ver en la landing page cuando un usuario introduce su correo en el formulario y le da al botón de registro: esto va a mandar el correo desde el frontend al endpoint del backend -el endpoint está en la línea 172- mediante un json del estilo `{"email":"asd@ijk.com"}`:

https://github.com/blackcub3s/pirApp/blob/77203daad85c9e100e6fb91e42babe769ec8eecd/APP%20WEB/__frontend__produccio__/landingPage/pas1_landingSignUp.html#L172-L178

Asimismo, y en segundo lugar, dentro del back-end de springboot queremos exponerle al front-end distintos *endpoints*. Estos los definimos dentro de `UsuariControlador.java`, una clase controlador de Java en donde tenemos especificado que se permite el acceso cruzado desde la IP loopback y el puerto 5500 del front-end de vscode. Por ejemplo, la solicitud POST anterior será recogida por el endpoint del backend `localhost/api/usuariExisteix` y luego resuelta con una petición de vuelta con información en el cuerpo de la solicitud POST[^2] (que explicaremos más en detalle en los siguientes apartados). Fijaros que solo se permitirá que el servidor acepte la petición de esa IP y ese puerto dado que en la anotación @CrossOrigin de dentro del controlador del back-end así lo especificamos (véase la línea 33):

https://github.com/blackcub3s/pirApp/blob/77203daad85c9e100e6fb91e42babe769ec8eecd/APP%20WEB/__springboot__produccio__/app/src/main/java/pirapp/app/Usuaris/controlador/UsuariControlador.java#L31-L35

Finalmente, y en tercer lugar, dentro del archivo de configuración de springboot `application.properties` tenemos definida la conexión con la base de datos (línea 5) donde especificamos la IP y el puerto que nos da workbench para que Java se comunique con la base de datos, así como las credenciales de acceso a la misma:

https://github.com/blackcub3s/pirApp/blob/d1638151838ee86981aaee82d9f4e0f3afe31bc5/APP%20WEB/__springboot__produccio__/app/src/main/resources/application.properties#L4-L8


> NOTA: Esta página es software propietario mio que no es de dominio público en tanto que forma parte de una idea de negocio, con lo cual solamente voy a mostrar una pequeña porción del mismo para demostrar un conocimiento generalista del framework springboot.

# 3. Diagrama de flujo

Como se ha comentado anteriormente, este sistema de registr y de iniciar sesión sido inspirado mediante un proceso de desarrollo inverso observando el funcionamiento de la página de netflix. Así pues el comportamiento que describimos aquí es tanto aplicable a la página que he creado como a la página de netflix.

El diagrama de flujo del proyecto de este repositiorio es el siguiente:

TO DO: POSAR IMATGE DIAGRAMA

Este se puede entender del siguiente modo:

1. Cada paralelogramo de color naranja es una página estática html de nuestro proyecto.
2. Cada rombo de fondo amarillo es una decisión que se hará dentro del backend de springboot, dado que requiere hacer consultas a la BBDD y contiene datos sensibles.
3. Los rombos de fondo azul se decidirán en el front-end en tanto que sus decisiones no requieran consultar información personal en la base de datos y no requerirán, por lo tanto, de uso del back-end.
4. El paréntesis que incluye la extensión de una URL debajo de cada paralelogramo naranja es cada página de netflix cuyo comportamiento y, en menor medida, aspecto, se ha intentado replicar en el archivo html del rombo que está contiguo. Por ejemplo el archivo html `pas2A_infoBenvinguda.html` de mi proyecto es una réplica de la página especificada en el paréntesis `netflix.com/signup/registration` y el usuario llegará a ella gracias a hacer una misma lógica de backend que la que usa netflix. 

>⚠️ **NOTA**: es muy importante hacer notar que las consultas de datos personales a la base de datos -correos y contraseñas- no se deben hacer desde el frontend, porque los archivos del frontend son públicos para el usuario y podrían exponer los datos de los usuarios en la base de datos a vulnerabilidades.


## 3.1 Página inicial (landing page)

La página inicial `pas1_landingSignUp.html` nos muestra una "landing page". 

![pagina inicial netflix no cargó](https://github.com/blackcub3s/pirApp/blob/62e05ecc660f37a103c6e3e23c413e0132cd56a0/SISTEMA%20LOGIN%20REPLICAT%20DE%20NETFLIX/pas1_landingSignUp.PNG)

Nuestra página replicada tiene este aspecto:

**INSERTAR IMATGE**

Lo más importante que tiene es el botón de iniciar sesión en la parte superior derecha y el formulario para mandar el correo y registrarse en caso que un usuario no lo esté. Lo importante es que los datos que se mandarán desde el cliente, con javascript desde aquí:

Hasta el backend hasta aquí (en el controlador):

Provienen de este formulario.

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

[^2]: En este caso particular que acabamos de mostrar, en el cuerpo de la respuesta de la solicitud POST se mandará del backend al frontend otro paquete de información en formato JSON (que, en este caso, será `{existeixUsuari: false}` ya que el usuario de correo `asd@ijk.com` no existe en la base de datos de usuarios):