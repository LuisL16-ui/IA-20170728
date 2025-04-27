import requests
import numpy as np
import matplotlib.pyplot as plt
import tkinter as tk
from tkinter import ttk

# Función para obtener datos de la API
def obtener_datos():
    url = "https://dummyjson.com/users?limit=100"
    response = requests.get(url)
    data = response.json()
    
    edades = []
    salarios = []
    nombres = []
    trabajos = []
    paises = []
    
    for usuario in data['users']:
        edad = usuario['age']
        nombre = f"{usuario['firstName']} {usuario['lastName']}"
        trabajo = usuario['company']['title']
        pais = usuario['address']['country']
        
        # Salario simulado
        salario = edad * 1000 + np.random.randint(-5000, 5000)
        
        edades.append(edad)
        salarios.append(salario)
        nombres.append(nombre)
        trabajos.append(trabajo)
        paises.append(pais)
    
    return np.array(edades), np.array(salarios), nombres, trabajos, paises

# Clase de Perceptrón Lineal
class PerceptronLineal:
    def __init__(self, lr=0.000001, n_iter=1000):
        self.lr = lr  # tasa de aprendizaje
        self.n_iter = n_iter  # número de iteraciones
#
#   bias es un valor adicional que se agrega a la combinación lineal de las características de entrada para ajustar la salida    del modelo. Su propósito es ayudar a que el modelo sea más flexible y pueda ajustarse mejor a los datos.

    def fit(self, X, y):
        self.weights = np.zeros(X.shape[1] + 1)  # +1 por el bias
        for _ in range(self.n_iter):
            for xi, target in zip(X, y):
                output = self.predict(xi)
                error = target - output
                self.weights[1:] += self.lr * error * xi
                self.weights[0] += self.lr * error  # Bias
    
    def net_input(self, X):
        return np.dot(X, self.weights[1:]) + self.weights[0]
    
    def predict(self, X):
        return self.net_input(X)

# Obtener los datos
X, y, nombres, trabajos, paises = obtener_datos()
X = X.reshape(-1, 1)  # Solo una característica: edad

# Crear el modelo y entrenarlo
modelo = PerceptronLineal(lr=0.000001, n_iter=50)
modelo.fit(X, y)

# Función para mostrar la gráfica
def mostrar_grafica():
    y_pred = modelo.predict(X)
    
    plt.scatter(X, y, color='blue', label='Datos reales')
    plt.plot(X, y_pred, color='red', label='Línea del perceptrón lineal')
    plt.xlabel('Edad')
    plt.ylabel('Salario (USD)')
    plt.title('Perceptrón Lineal - Edad vs Salario')
    plt.legend()
    plt.grid(True)
    
    # Mostrar ecuación
    pendiente = modelo.weights[1]
    interseccion = modelo.weights[0]
    plt.text(min(X), max(y), f"y = {pendiente:.2f}x + {interseccion:.2f}", fontsize=10, color='green')
    
    plt.show()

# Función para mostrar el listado
def mostrar_listado():
    ventana_listado = tk.Toplevel(ventana)
    ventana_listado.title("Listado de Datos")
    ventana_listado.geometry("800x500")
    
    tree = ttk.Treeview(ventana_listado, columns=("Nombre", "Edad", "Salario", "Trabajo", "País"), show="headings")
    
    tree.heading("Nombre", text="Nombre")
    tree.heading("Edad", text="Edad")
    tree.heading("Salario", text="Salario (USD)")
    tree.heading("Trabajo", text="Trabajo")
    tree.heading("País", text="País")
    
    for edad, salario, nombre, trabajo, pais in zip(X.flatten(), y, nombres, trabajos, paises):
        tree.insert("", tk.END, values=(nombre, edad, f"${salario:,.2f}", trabajo, pais))
    
    tree.pack(expand=True, fill='both')

# Crear ventana principal
ventana = tk.Tk()
ventana.title("Perceptrón Lineal con Datos Reales")
ventana.geometry("320x220")

label_info = tk.Label(ventana, text="Edad vs Salario\n(datos de dummyjson)")
label_info.pack(pady=10)

btn_grafica = tk.Button(ventana, text="Mostrar gráfica", command=mostrar_grafica)
btn_grafica.pack(pady=5)

btn_listado = tk.Button(ventana, text="Mostrar listado de datos", command=mostrar_listado)
btn_listado.pack(pady=5)

ventana.mainloop()
