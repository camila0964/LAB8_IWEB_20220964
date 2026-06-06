<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.veterinaria.beans.Mascota" %>
<%@ page import="org.example.veterinaria.beans.Especie" %>
<jsp:useBean id="listaMascotas" type="java.util.ArrayList<org.example.veterinaria.beans.Mascota>" scope="request" />
<jsp:useBean id="listaEspecies" type="java.util.ArrayList<org.example.veterinaria.beans.Especie>" scope="request" />

<!DOCTYPE html>
<html>
<head>
  <title>Lista de Mascotas</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">

<h1 class="my-4">Lista de Mascotas</h1>

<a class="btn btn-primary mb-4" href="<%=request.getContextPath()%>/inicio?action=nuevo">Nueva Mascota</a>

<form method="GET" action="<%=request.getContextPath()%>/inicio" class="row g-3 mb-4 align-items-center">
  <input type="hidden" name="action" value="filtrar">
  <div class="col-auto">
    <label for="idEspecie" class="col-form-label font-weight-bold">Filtrar por especie:</label>
  </div>
  <div class="col-auto">
    <%-- El onchange="this.form.submit()" envía el filtro automáticamente al seleccionar --%>
    <select name="idEspecie" id="idEspecie" class="form-select" onchange="this.form.submit()">
      <option value="todas">Todas las especies</option>
      <%
        String especieSeleccionada = request.getParameter("idEspecie");
        for (Especie e : listaEspecies) {
          boolean isSelected = especieSeleccionada != null && especieSeleccionada.equals(String.valueOf(e.getIdEspecie()));
      %>
      <option value="<%= e.getIdEspecie() %>" <%= isSelected ? "selected" : "" %>><%= e.getNombre() %></option>
      <% } %>
    </select>
  </div>
</form>

<table class="table table-striped table-bordered align-middle">
  <thead style="background-color: #1E8449; color: white;">
  <tr>
    <th>ID</th>
    <th>Nombre</th>
    <th>Edad</th>
    <th>Peso</th>
    <th>Especie</th>
    <th>Veterinario</th>
    <th>Dueño</th>
    <th>Acción</th>
  </tr>
  </thead>
  <tbody>
  <% if (listaMascotas != null && !listaMascotas.isEmpty()) { %>
  <% for (Mascota m : listaMascotas) { %>
  <tr>
    <td><%= m.getIdMascota() %></td>
    <td><%= m.getNombre() %></td>
    <td><%= m.getEdad() %></td>
    <%-- formateo a dos decimales para el peso --%>
    <td><%= String.format("%.2f", m.getPeso()) %></td>
    <%-- se invocan los métodos getNombre() de los beans asociados --%>
    <td><%= m.getEspecie().getNombre() %></td>
    <td><%= m.getVeterinario().getNombre() %></td>
    <td><%= m.getDueno().getNombre() %></td>
    <td>
      <%-- acción de borrado vinculada al ID de la mascota actual --%>
      <a class="btn btn-danger btn-sm"
         href="<%=request.getContextPath()%>/inicio?action=borrar&id=<%= m.getIdMascota() %>"
         onclick="return confirm('¿Está seguro de eliminar a esta mascota?')">Borrar</a>
    </td>
  </tr>
  <% } %>
  <% } else { %>
  <tr>
    <td colspan="8" class="text-center text-muted">No se encontraron mascotas registradas para este criterio.</td>
  </tr>
  <% } %>
  </tbody>
</table>

</body>
</html>