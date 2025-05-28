import requests
import numpy as np
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression
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

# Obtener los datos
X, y, nombres, trabajos, paises = obtener_datos()
X = X.reshape(-1, 1)

# Crear el modelo y entrenarlo
modelo = LinearRegression()
modelo.fit(X, y)

# Función para mostrar la gráfica
def mostrar_grafica():
    y_pred = modelo.predict(X)
    
    plt.scatter(X, y, color='blue', label='Datos reales')
    plt.plot(X, y_pred, color='red', label='Línea de regresión')
    plt.xlabel('Edad')
    plt.ylabel('Salario (USD)')
    plt.title('Regresión Lineal - Edad vs Salario')
    plt.legend()
    plt.grid(True)
    
    pendiente = modelo.coef_[0]
    interseccion = modelo.intercept_
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
    
    # Insertar los datos
    for edad, salario, nombre, trabajo, pais in zip(X.flatten(), y, nombres, trabajos, paises):
        tree.insert("", tk.END, values=(nombre, edad, f"${salario:,.2f}", trabajo, pais))
    
    tree.pack(expand=True, fill='both')

# Crear ventana principal
ventana = tk.Tk()
ventana.title("Regresión Lineal con Datos Reales")
ventana.geometry("320x220")

label_info = tk.Label(ventana, text="Edad vs Salario\n(datos de dummyjson)")
label_info.pack(pady=10)

btn_grafica = tk.Button(ventana, text="Mostrar gráfica", command=mostrar_grafica)
btn_grafica.pack(pady=5)

btn_listado = tk.Button(ventana, text="Mostrar listado de datos", command=mostrar_listado)
btn_listado.pack(pady=5)

ventana.mainloop()
