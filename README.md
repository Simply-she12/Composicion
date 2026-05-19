# Casa Domótica - Patrón Composite

Sistema de control domótico inteligente que demuestra la implementación del **patrón de diseño Composite** usando Java Swing.

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-GUI-blue)
![License](https://img.shields.io/badge/license-MIT-green)

## Descripción

Este proyecto implementa un sistema de control para una casa domótica donde los dispositivos pueden manejarse individualmente o en grupos jerárquicos. Demuestra visualmente la diferencia entre:

- **Sin patrón**: Control manual dispositivo por dispositivo
- **Con patrón Composite**: Control automático de estructuras completas

### ¿Qué es el Patrón Composite?

El patrón Composite permite componer objetos en estructuras de árbol para representar jerarquías. Permite que los clientes traten objetos individuales y composiciones de manera uniforme.

##  Características

-  **Interfaz gráfica moderna** con tema oscuro
-  **Control de dispositivos**: Luces y ventiladores
-  **Jerarquía de habitaciones**: Organización en estructura de árbol
-  **Consola en tiempo real**: Visualización de las operaciones
-  **Comparación visual**: Panel dividido mostrando ambos enfoques
-  **Operaciones en cascada**: Un clic controla múltiples dispositivos

## Arquitectura

### Estructura del Patrón Composite

```
IDispositivo (Interfaz)
├── LuzD [Leaf]
├── VentiladorD [Leaf]
└── HabitacionD [Composite]
    ├── dispositivos: List<IDispositivo>
    └── métodos: encender(), apagar()
```

### Jerarquía de la Casa

```
Casa Completa [Composite Raíz]
└── Sala [Composite]
    ├── Luz (Sala) [Leaf]
    └── Ventilador (Sala) [Leaf]
```

## Instalación y Uso

### Requisitos Previos

- Java JDK 8 o superior
- Un IDE compatible con Java (IntelliJ IDEA, Eclipse, VSCode, etc.) o simplemente el compilador `javac`

### Compilar y Ejecutar

#### Opción 1: Desde la terminal

```bash
# Navegar al directorio del proyecto
cd Composicion/Composite

# Compilar
javac CasaDomotica.java

# Ejecutar
java CasaDomotica
```

#### Opción 2: Desde un IDE

1. Abre el proyecto en tu IDE favorito
2. Navega a `Composite/CasaDomotica.java`
3. Click derecho → Run 'CasaDomotica.main()'

## Cómo Usar la Aplicación

### Panel Izquierdo - Sin Patrón
- Control **individual** de cada dispositivo
- Cada botón afecta solo a un dispositivo
- Requiere múltiples clics para apagar todo

### Panel Derecho - Con Patrón Composite
- **Botones "Sala"**: Controlan todos los dispositivos de la sala
- **Botones "Casa Completa"**: Controlan toda la jerarquía
- Un solo clic para operaciones masivas

### Consola de Salida
- Muestra en tiempo real las operaciones ejecutadas
- Visualiza el recorrido del árbol de dispositivos
- Útil para entender el flujo del patrón

## Código Clave

### Interfaz Componente
```java
interface IDispositivo {
    void encender();
    void apagar();
    boolean estaEncendido();
    String getNombre();
}
```

### Clase Composite
```java
class HabitacionD implements IDispositivo {
    private List<IDispositivo> dispositivos = new ArrayList<>();
    
    public void encender() {
        for (IDispositivo d : dispositivos) 
            d.encender();  // Propagación automática
    }
}
```

## Capturas de Pantalla

La aplicación muestra:
-  Estados visuales (encendido/apagado)
-  Colores dinámicos según el estado
-  Bordes que indican dispositivos apagados
-  Log detallado de operaciones

## Conceptos Demostrados

| Concepto | Implementación |
|----------|----------------|
| **Component** | `IDispositivo` (interfaz común) |
| **Leaf** | `LuzD`, `VentiladorD` (elementos finales) |
| **Composite** | `HabitacionD` (contenedor de dispositivos) |
| **Transparencia** | Misma interfaz para hojas y composites |
| **Recursión** | Operaciones se propagan por el árbol |

## Tecnologías Utilizadas

- **Java**: Lenguaje principal
- **Swing**: Framework para interfaz gráfica
- **AWT**: Manejo de eventos y gráficos
- **Patrón Composite**: Diseño estructural GoF

## Recursos de Aprendizaje

- [Gang of Four - Design Patterns](https://en.wikipedia.org/wiki/Design_Patterns)
- [Refactoring.Guru - Composite Pattern](https://refactoring.guru/design-patterns/composite)
- [Java Swing Tutorial](https://docs.oracle.com/javase/tutorial/uiswing/)

## Contribuciones

Este es un proyecto educativo. Si encuentras mejoras o bugs:

1. Haz fork del proyecto
2. Crea una rama para tu feature (`git checkout -b feature/mejora`)
3. Commit tus cambios (`git commit -m 'Añadir mejora'`)
4. Push a la rama (`git push origin feature/mejora`)
5. Abre un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## Autor

Proyecto desarrollado como ejercicio académico para demostrar el patrón de diseño Composite.

---

 **Si te sirvió este proyecto, dale una estrella en GitHub!**
