Regresión Lineal (LinearRegression):
Utiliza una fórmula matemática exacta (mínimos cuadrados) para encontrar directamente los mejores coeficientes que minimizan el error.

Perceptrón Lineal:
Utiliza un algoritmo iterativo que ajusta pesos paso a paso en función del error de predicción, simulando cómo aprende una neurona artificial.

# Comparativa entre Regresión Lineal y Perceptrón Lineal

| 🧩 Característica               | 🎯 Regresión Lineal (`LinearRegression`) | 🤖 Perceptrón Lineal                   |
|:---------------------------------|:-----------------------------------------|:---------------------------------------|
| **¿Cómo aprende?**               | Fórmula exacta (mínimos cuadrados)       | Ajuste iterativo de pesos (Regla Delta) |
| **Velocidad**                    | Muy rápida                              | Más lenta                              |
| **Precisión**                    | Muy alta (exacta en datos lineales)      | Aproximada (depende de parámetros)     |
| **Entrenamiento**                | Sin iteraciones                         | Iterativo (épocas y learning rate)     |
| **Complejidad**                  | Baja                                    | Moderada                               |
| **Ideal para**                   | Relaciones lineales simples             | Simular procesos de aprendizaje        |
| **¿Puede fallar?**               | Difícilmente (en datos lineales)         | Sí (si parámetros no son adecuados)    |
| **Uso típico**                   | Modelado estadístico clásico            | Base para redes neuronales             |

