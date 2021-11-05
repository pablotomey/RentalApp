# RentalApp
Aplicación móvil diseñada para la gestión de reportes vehiculares o checklist de mantenimiento para la empresa Magallánica (Punta arenas, Chile) [Rentalea](http://rentalea.cl/).
Esta aplicación permite guardar los reportes vehiculares para posteriormente enviarlos a un administrador web. 

#### Manejo de datos y características:
- Selección del tipo de vehículo (Camioneta, maquinaria pesada, camión, equipos menores)
- Selección de patentes
- Calendarizar fecha del reporte y horario de trabajo del equipo.
- Seleccionar obra, empresa mandante, operador a cargo, kilometraje, horómetro, etc.
- Registrar la cantidad de viajes y tipo de material trasladado (en caso de las tolvas o bateas).
- Edición del reporte.
- Envío de reporte a un sistema web administrador.


## Tecnologías utilizadas
Esta aplicación fue creada para el sistema **Android** con el lenguaje de programación **Kotlin**. La elección de crear la aplicación de forma nativa para esta plataforma pasa por la gran cuota de mercado y por el tipo de usuario final, operadores y choferes a cargo de los vehículos a arrendar por la empresa.

#### Las tecnologías utilizadas para la creación de la aplicación son:

- Principales librerías de [Android Jetpack](https://developer.android.com/jetpack) como Room data base, Live data, Data binding, View model, Lifecycle, Navigation components.
- [Firebase Firestore](https://firebase.google.com/) Base de datos NoSql y backend.
- Inyección de dependencias con [Koin](https://insert-koin.io/)
- Kotlin Coroutines
- [Timber](https://github.com/JakeWharton/timber) logger para Android
- Material desing
