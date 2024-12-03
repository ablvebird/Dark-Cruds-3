# 🛠️ Dark Cruds 3 - Ejercicios de Gestión de Datos XML en Java

![Dark Souls 3 Banner](resources/DALL·E%202024-11-29%2017.42.05.webp)
_(thanks Dall-E :P)_

## 📜 Resumen

Dark Cruds 3 es una aplicación Java originada en una serie de ejercicios para la asignatura de Acceso a Datos del ciclo Desarrollo de Aplicaciones Multiplataforma que tiene como objetivo demostrar el funcionamiento de la gestión de datos con XML a través de diferentes métodos (usando como temática mi obsesión personal) a través de 6 bloques de ejercicios:

1. **🗂️ File Manager**: Este bloque se enfoca en la gestión de archivos y directorios. La clase `FileManager` proporciona métodos para crear, eliminar y copiar archivos y directorios, así como para leer y escribir datos de jefes en archivos binarios (`.dat`). Esto permite a los usuarios persistir y manejar los datos de los jefes a través del sistema de archivos.

2. **🌳 DOM**: El bloque DOM se centra en el uso de la API DOM (Document Object Model) para procesar y manipular datos XML. La clase `DomManager` implementa métodos para analizar documentos XML, extraer información sobre los jefes, generar nuevos documentos XML basados en criterios específicos y más.

3. **📜 SAX**: Este bloque introduce el uso del enfoque SAX (Simple API for XML) para analizar documentos XML. La clase `SaxManager` demuestra cómo procesar los datos XML mediante el uso de eventos SAX, lo que puede ser más eficiente que el enfoque DOM en algunos casos.

4. **🤖 JDOM**: El bloque JDOM muestra cómo utilizar la biblioteca JDOM para trabajar con datos XML. La clase `JDomManager` proporciona métodos para leer, analizar y generar archivos XML, complementando las funcionalidades proporcionadas por las clases basadas en DOM y SAX.

5. **🌐 XSLT**: Este bloque se enfoca en la transformación de datos XML a HTML utilizando hojas de estilo XSLT. La clase `XMLTransformer` proporciona una utilidad para aplicar la transformación XSLT, lo que permite a los usuarios visualizar los datos de los jefes en un formato más amigable.

6. **🗃️ SQLite**: El bloque SQLite demuestra la integración de la aplicación con una base de datos SQLite. Las clases `SQLiteConnector` y `SQLiteCRUD` proporcionan funcionalidades para establecer conexiones a la base de datos, realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) en la tabla de jefes y realizar consultas avanzadas.

Cada uno de estos bloques de ejercicios se ha diseñado para ilustrar diferentes aspectos y enfoques en la gestión de datos XML, lo que permite a los usuarios explorar y comprender las diversas herramientas y técnicas disponibles en Java para este tipo de tareas.

## 🗂️ Estructura del Proyecto

El proyecto Dark Cruds 3 está organizado en los siguientes paquetes y clases principales:

1. **Entities**:
   - `Boss`: Representa una entidad de jefe con diversas propiedades (ID, nombre, ubicación, estadísticas, información de recompensas, etc.).
   - `BossManager`: Gestiona una lista de entidades de jefes y proporciona operaciones para agregar, modificar y eliminar jefes.

2. **FileManager**:
   - `FileManager`: Maneja las operaciones relacionadas con los archivos, como crear, eliminar y copiar archivos y directorios, así como leer y escribir datos de jefes en archivos binarios (`.dat`).

3. **SQLite**:
   - `SQLiteConnector`: Proporciona una utilidad para establecer una conexión a una base de datos SQLite.
   - `SQLiteCRUD`: Implementa operaciones CRUD para la entidad Boss en la base de datos SQLite.

4. **XMLManagers**:
   - `DomManager`: Gestiona los datos XML utilizando la API DOM (Document Object Model), proporcionando métodos para analizar, analizar y manipular documentos XML.
   - `JDomManager`: Gestiona los datos XML utilizando la biblioteca JDOM, proporcionando métodos para leer, analizar y generar archivos XML.
   - `SaxManager`: Maneja los datos XML utilizando el enfoque de análisis SAX (Simple API for XML).
   - `XMLTransformer`: Proporciona una utilidad para transformar los datos XML en HTML utilizando hojas de estilo XSLT.

El proyecto también incluye una clase `Main` que sirve como punto de entrada de la aplicación, demostrando el uso de las diversas funciones y componentes.

## 🚀 Uso

Para utilizar la aplicación Dark Cruds 3, sigue estos pasos:

1. Clona el repositorio en tu máquina local.
2. Abre el proyecto en tu IDE Java preferido (por ejemplo, IntelliJ IDEA, Eclipse).
3. Explora los diversos paquetes y clases para comprender las diferentes funcionalidades proporcionadas por la aplicación.
4. Ejecuta la clase `Main`, descomenta el método de testeo del bloque de ejercicios deseado para ver la aplicación en acción y revisar el log y output de la ejecución.

## 🤔 Dudas y Contribuciones

El origen de esta aplicación es un trabajo de clase con el que se pretende ilustrar el funcionamiento de una serie de librerías y herramientas más que para ser una aplicación funcional.

Cualquier nota al respecto para mejorarla será más que agradecida, e igualmente si hay alguna duda que pueda resolver al respecto de cualquiera de los aspectos de la aplicación estaré encantado de intentar resolverla.