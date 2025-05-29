import tensorflow as tf
from tensorflow.keras import layers, models
import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import tkinter as tk
from tkinter import ttk
from sklearn.metrics import confusion_matrix
import seaborn as sns

# Cargar y preparar datos
(x_train, y_train), (x_test, y_test) = tf.keras.datasets.mnist.load_data()
x_train = x_train.reshape(-1, 28, 28, 1) / 255.0
x_test = x_test.reshape(-1, 28, 28, 1) / 255.0

# modelo secuencial de Keras para clasificación de imágenes con CNN
model = models.Sequential([
    # Capa convolucional: 32 filtros de 3x3
    layers.Conv2D(32, (3, 3), activation='relu', input_shape=(28, 28, 1)),
    # Capa de max pooling: reduce la dimensionalidad de la imagen
    layers.MaxPooling2D((2, 2)),
    # Segunda capa convolucional
    layers.Conv2D(64, (3, 3), activation='relu'),
    # Segunda capa de max pooling
    layers.MaxPooling2D((2, 2)),
    # Aplana la salida 2D a un vector 1D para conectarla a las capas densas
    layers.Flatten(),
    # Capa densa (totalmente conectada)
    layers.Dense(64, activation='relu'),
    # Capa de salida: 10 neuronas (una por clase)
    layers.Dense(10, activation='softmax')
])
model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])
model.fit(x_train, y_train, epochs=5, validation_split=0.1)
test_loss, test_acc = model.evaluate(x_test, y_test)
print(f"Precisión: {test_acc * 100:.2f}%")

# Predicciones
predicciones = model.predict(x_test)
pred_clases = np.argmax(predicciones, axis=1)

# Gráfica: Matriz de confusión
def graficar_matriz_confusion():
    matriz = confusion_matrix(y_test, pred_clases)
    plt.figure(figsize=(10, 8))
    sns.heatmap(matriz, annot=True, fmt="d", cmap="Blues", cbar=False, 
                xticklabels=range(10), yticklabels=range(10))
    plt.title("Matriz de Confusión")
    plt.xlabel("Predicción del Modelo")
    plt.ylabel("Etiqueta Real")
    plt.show()

# Tabla con conteo
def mostrar_cantidad_imagenes():
    conteo = [np.sum(y_test == i) for i in range(10)]
    top = tk.Toplevel()
    top.title("Cantidad de imágenes por número")
    tree = ttk.Treeview(top, columns=("Número", "Cantidad"), show='headings')
    tree.heading("Número", text="Número")
    tree.heading("Cantidad", text="Cantidad de Imágenes")
    for i in range(10):
        tree.insert("", "end", values=(i, conteo[i]))
    tree.pack(expand=True, fill='both')

# Interfaz
ventana = tk.Tk()
ventana.title("MNIST - Análisis con CNN")
ventana.geometry("400x200")

btn1 = tk.Button(ventana, text="Matriz de Confusión", command=graficar_matriz_confusion, height=2)
btn1.pack(pady=10)

btn2 = tk.Button(ventana, text="Tabla de Cantidad", command=mostrar_cantidad_imagenes, height=2)
btn2.pack(pady=10)

ventana.mainloop()
