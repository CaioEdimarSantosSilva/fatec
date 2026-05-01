import pandas as pd  
import streamlit as st 

dados = [ 
        {"nome": "Caio Silva","curso": "DSM","semestre": 3,"ano": 2026,"local": "Fatec PG"},
        {"nome": "Xamis Silva","curso": "ADM","semestre": 2,"ano": 2026,"local": "FGV"}            
       ]

# Converter para DataFrame
df = pd.DataFrame(dados)

# Título
st.title("📊 Dashboard de Alunos")

# Mostrar tabela
st.subheader("📋 Dados")
st.dataframe(df)

# Filtro por curso
curso = st.selectbox("Filtrar por curso", df["curso"].unique())

df_filtrado = df[df["curso"] == curso]

st.subheader("📌 Dados filtrados")
st.dataframe(df_filtrado)

# Métricas
st.subheader("📈 Métricas")

col1, col2 = st.columns(2)

col1.metric("Total de Alunos", len(df))
col2.metric("Semestre Médio", round(df["semestre"].mean(), 1))

# Gráfico
st.subheader("📊 Distribuição por Curso")
st.bar_chart(df["curso"].value_counts())

# Gráfico por local
st.subheader("🏫 Alunos por Instituição")
st.bar_chart(df["local"].value_counts())

