# **Mercap Challenge**

El ejercicio se realizó en Java, utilizando Eclipse IDE.

### Estructura:

• `Bill`: **clase** que modela una factura.
    
• `Call`: **clase** que modela una llamada.

• `Data`: **clase** exclusivamente dedicada a la construcción de los datos utilizados para generar las facturas. Esto incluye una lista de usuarios de clientes y otra de llamadas simulando una base de datos, y los costos de llamada a distintas localidades y países.

• `Main`: **clase ejecutable** encargada de llamar a los métodos necesarios para iniciar el programa.

• `User`: **clase** que modela un usuario (cliente).

## **Clase Main en detalle:**

En la clase `Main` se implementó el método `generateBill(String userId, int monthInt, int yearInt)`, que contiene la lógica necesaria para la generación de una factura nueva a partir de un ID de usuario y dos enteros que representan el mes y el año correspondientes al período que se quiere facturar.

Este método busca si el usuario existe en la lista de usuarios (que simula una base de datos, salvando las diferencias), y luego filtra la lista de todas las llamadas, dejando únicamente las llamadas correspondientes a dicho usuario y a dicho período. Si no encuentra ningún usuario con el ID indicado, o bien el mes y/o año indicados son inválidos, arroja una excepción.

Finalmente, utiliza el objeto `User` encontrado, el mes, el año y la lista filtrada de llamadas, para crear y retornar un nuevo objeto `Bill` que representa la factura del período pedido correspondiente al usuario pedido, la cual será impresa en consola utilizando su método `toString()`.

## **Ejemplos de facturas:**

• **Factura del usuario con ID 1 del período 08/21** (`Bill bill = generateBill("1", 8, 2021);`):

Este usuario presenta 3 llamadas locales, 1 nacionales y 2 internacionales durante este período.

```
Fecha de facturación: 06/10/2022
Número de cliente: 1
Nombre de cliente: Nicolás Céspede
Período: Agosto/2021

DETALLE:

Abono básico ---------- $ 500.0

Llamadas locales (3):
  • Llamada 'moreno' - 'moreno' (30.0 mins) ---------- $ 6
  • Llamada 'moreno' - 'moreno' (30.0 mins) ---------- $ 3
  • Llamada 'moreno' - 'moreno' (30.0 mins) ---------- $ 3

Llamadas nacionales (1):
  • Llamada 'moreno' - 'bella vista' (30.0 mins) ---------- $ 12

Llamadas internacionales (2):
  • Llamada 'arg' - 'bra' (30.0 mins) ---------- $ 18
  • Llamada 'arg' - 'bol' (50.0 mins) ---------- $ 27,5

TOTAL A PAGAR ---------- $ 569.5
```

• **Factura del usuario con ID 1 del período 09/21** (`Bill bill = generateBill("1", 9, 2021);`):

Este usuario presenta 1 llamada local durante este período.

```
Fecha de facturación: 06/10/2022
Número de cliente: 1
Nombre de cliente: Nicolás Céspede
Período: Septiembre/2021

DETALLE:

Abono básico ---------- $ 500.0

Llamadas locales (1):
  • Llamada 'moreno' - 'moreno' (30.0 mins) ---------- $ 3

TOTAL A PAGAR ---------- $ 503.0
```

• **Factura del usuario con ID 2 del período 08/21** (`Bill bill = generateBill("2", 8, 2021);`):

Este usuario no presenta ninguna llamada durante este período, sólo se le cobra el abono básico.

```
Fecha de facturación: 06/10/2022
Número de cliente: 2
Nombre de cliente: Emanuel Tenca
Período: Agosto/2021

DETALLE:

Abono básico ---------- $ 500.0

TOTAL A PAGAR ---------- $ 500.0
```

## **Comentarios extra, asunciones y decisiones tomadas:**

• Se asume que la llamada se cobra según su estampa de inicio, es decir, que si, por ejemplo, la llamada comienza a las 19 y termina a las 21 un día hábil se cobrarán $0,2 por minuto. Se consideró realizar el cálculo de cuántos minutos deberían cobrarse $0,1 y cuántos $0,2, pero para no complejizar el problema se optó por realizar esta asunción.

• Se asume que la llamada se le cobra sólo al usuario que la realiza, y no al que la recibe.

• Se asume que el enunciado con "0,10 centavos" se refiere a "0,10 pesos", es decir, "10 centavos"; y lo mismo con "0,20 centavos".

• Se asume que el abono básico es el mismo para todos los clientes, el cuál se optó por que sea de $500.

• Se asume que el costo de llamada a una determinada localidad y a un determinado país son fijos, es decir, que no depende de la localidad o el país desde el que provenga la llamada. Por ejemplo, si una llamada proviene de Argentina con destino Brasil, se cobrará (por minuto) lo mismo que una llamada proveniente de Bolivia con destino Brasil. La misma lógica aplica para las localidades.

• Se optó por que las propiedades de la clase `Data` sean públicas por una cuestión de poder acceder fácilmente a ellas (sin utilizar un getter).

• El constructor de la clase `Bill` realiza un trabajo de filtrado de las llamadas antes de asignarle valores a ciertas propiedades. Se desconoce si esto es una buena práctica o no, pero al no ser considerada una operación crítica para la construcción del objeto, se decidió mantenerlo así.

• Inicialmente los países tenían localidades con diferentes costos de llamada, pero para no complejizar el problema (y porque el enunciado no lo especificaba) se decidió quitarlas.

• Inicialmente los países y localidades de origen y destino de una llamada eran propiedades en la clase `Call`, pero se optó porque cada `User` tenga registrado su país y localidad en sus propiedades.

• En la clase `Call`, el tipo de llamada (local, nacional o internacional) podría haber sido una propiedad de la clase, pero se decidió que el mismo se pueda inferir a partir de las propiedades (país y localidad) de los usuarios que la misma contiene.

• Si se desea probar los distintos ejemplos, "descomentar" la línea correspondiente al ejemplo deseado en la clase `Main`. Si se desea probar ejemplos nuevos, modificar a gusto los parámetros en el llamado a la función `generateBill()` de la línea 16 de la clase `Main`.