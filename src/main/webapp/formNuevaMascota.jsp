<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.veterinaria.beans.Especie" %>
<%@ page import="org.example.veterinaria.beans.Veterinario" %>
<%@ page import="org.example.veterinaria.beans.Dueno" %>
<jsp:useBean id="listaEspecies" type="java.util.ArrayList<org.example.veterinaria.beans.Especie>" scope="request" />
<jsp:useBean id="listaVeterinarios" type="java.util.ArrayList<org.example.veterinaria.beans.Veterinario>" scope="request" />
<jsp:useBean id="listaDuenos" type="java.util.ArrayList<org.example.veterinaria.beans.Dueno>" scope="request" />

<!DOCTYPE html>
<html>
<head>
  <title>Nueva Mascota</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4" style="max-width: 600px;">

<h2 class="my-4">Nueva Mascota</h2>
<hr>

<form method="POST" action="<%=request.getContextPath()%>/inicio?action=guardar">

  <div class="mb-3">
    <label class="form-label font-weight-bold">Nombre:</label>
    <input type="text" name="nombre" class="form-control" required>
  </div>

  <div class="mb-3">
    <label class="form-label font-weight-bold">Edad:</label>
    <input type="number" name="edad" class="form-control" min="0" required>
  </div>

  <div class="mb-3">
    <label class="form-label font-weight-bold">Peso (kg):</label>
    <input type="number" step="0.01" name="peso" class="form-control" min="0" required>
  </div>

  <div class="mb-3">
    <label class="form-label font-weight-bold">Especie:</label>
    <select name="especie_id" class="form-select" required>
      <option value="" disabled selected>Seleccione una especie</option>
      <% for (Especie e : listaEspecies) { %>
      <option value="<%= e.getIdEspecie() %>"><%= e.getNombre() %></option>
      <% } %>
    </select>
  </div>

  <div class="mb-3">
    <label class="form-label font-weight-bold">Veterinario:</label>
    <select name="veterinario_id" class="form-select" required>
      <option value="" disabled selected>Seleccione un veterinario</option>
      <% for (Veterinario v : listaVeterinarios) { %>
      <option value="<%= v.getIdVeterinario() %>"><%= v.getNombre() %></option>
      <% } %>
    </select>
  </div>

  <div class="mb-3">
    <label class="form-label font-weight-bold">Dueño:</label>
    <select name="dueno_id" class="form-select" required>
      <option value="" disabled selected>Seleccione un dueño</option>
      <% for (Dueno d : listaDuenos) { %>
      <option value="<%= d.getIdDueno() %>"><%= d.getNombre() %></option>
      <% } %>
    </select>
  </div>

  <div class="mt-4">
    <button type="submit" class="btn btn-success me-2">Guardar</button>
    <a href="<%=request.getContextPath()%>/inicio" class="btn btn-secondary">Cancelar</a>
  </div>
</form>

</body>
</html>